package comp3350.intellicards.Presentation;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.EditText;

import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import comp3350.intellicards.Objects.FlashCard;
import comp3350.intellicards.Objects.FlashCardSet;
import comp3350.intellicards.R;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //set layout
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Create a new FlashCardSet object
        FlashCardSet flashCardSet = new FlashCardSet("New Set");

        // get all the page views in variables
        TextView questionTextBox = findViewById(R.id.question);
        TextView answerTextBox = findViewById(R.id.answer);
        Button submitTextButton = findViewById(R.id.submitFlashcard);
        Button profilePageButton = findViewById(R.id.profileButton);

        questionTextBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                questionTextBox.setText("");
            }
        });

        answerTextBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                answerTextBox.setText("");
            }
        });

        submitTextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create a new FlashCard object
                FlashCard flashCard = new FlashCard();

                // Set the FlashCard question and answer from the EditText inputs
                flashCard.setQuestion(questionTextBox.getText().toString());
                flashCard.setAnswer(answerTextBox.getText().toString());
                // Clear the EditText inputs
                questionTextBox.setText("");
                answerTextBox.setText("");

                flashCardSet.addFlashCard(flashCard); // Add the FlashCard to the FlashCardSet

                printViewList(flashCardSet.getFlashcards());
                printRecoverList(flashCardSet.getDeletedFlashCards());
            }
        });

        profilePageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ProfileActivity.class);
                startActivity(intent);
            }
        });


    }


    public void printViewList(FlashCardSet flashCardSet)
    {
        RecyclerView recyclerView;
        CardViewAdapter cardViewAdapter;
        RecyclerView.LayoutManager layoutManager;

        recyclerView = findViewById(R.id.recycleView);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        cardViewAdapter = new CardViewAdapter(flashCardSet);
        recyclerView.setAdapter(cardViewAdapter);
    }

    public void printRecoverList(FlashCardSet flashCardSet)
    {
        RecyclerView recyclerRecoverView;
        CardRecoverAdapter recoverAdapter;
        RecyclerView.LayoutManager layoutManager;

        recyclerRecoverView = findViewById(R.id.recycleRecoverView);
        layoutManager = new LinearLayoutManager(this);
        recyclerRecoverView.setLayoutManager(layoutManager);

        recoverAdapter = new CardRecoverAdapter(flashCardSet);
        recyclerRecoverView.setAdapter(recoverAdapter);
    }

}