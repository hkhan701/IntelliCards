package com.example.intellicards;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Create a new FlashCardSet object
        FlashCardSet flashCardSet = new FlashCardSet("New Set");


        Button submitTextButton = findViewById(R.id.submitFlashcard);
        submitTextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create a new FlashCard object
                FlashCard flashCard = new FlashCard();

                // Find the EditText views in the layout
                EditText questionInput = findViewById(R.id.question);
                EditText answerInput = findViewById(R.id.answer);

                // Set the FlashCard question and answer from the EditText inputs
                flashCard.setQuestion(questionInput.getText().toString());
                flashCard.setAnswer(answerInput.getText().toString());

                // Find the TextView elements in the layout
                TextView questionTextView = findViewById(R.id.question_text_view);
                TextView answerTextView = findViewById(R.id.answer_text_view);

                flashCardSet.addFlashCard(flashCard); // Add the FlashCard to the FlashCardSet

                // Update the TextViews with the FlashCard data
                questionTextView.setText(flashCard.getQuestion());
                answerTextView.setText(flashCard.getAnswer());

                // Clear the EditText inputs
                questionInput.setText("");
                answerInput.setText("");
                System.out.println("Current FlashCardSet: " + flashCardSet);


            }
        });


    }
}