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
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

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
                printList(flashCardSet.getFlashcards());

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


    public void printList(FlashCardSet flashCardSet)
    {
        RecyclerView recyclerView;
        DataDisplayer dataDisplayer;
        RecyclerView.LayoutManager layoutManager;

        recyclerView = findViewById(R.id.recycleView);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        dataDisplayer = new DataDisplayer(flashCardSet);
        recyclerView.setAdapter(dataDisplayer);
    }

//    private String[] arrayListToStringList(List<FlashCard> flashCards)
//    {
//        int size = flashCards.size();
//        String[] list = new String[size];
//        for(int i = 0; i < size; i++)
//        {
//            list[i] = flashCards.get(i).toString();
//        }
//        return list;
//    }
}