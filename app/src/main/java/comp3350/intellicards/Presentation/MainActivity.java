package comp3350.intellicards.Presentation;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import comp3350.intellicards.Objects.Flashcard;
import comp3350.intellicards.Objects.FlashcardSet;
import comp3350.intellicards.Persistence.FlashcardSetPersistenceStub;
import comp3350.intellicards.R;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //set layout
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        // Create a new FlashCardSet object
        // Stub Database
        if(!FlashcardSetPersistenceStub.isInstantiated())
        {
            FlashcardSetPersistenceStub.initializeStubData();
        }

        FlashcardSet flashcardSet = FlashcardSetPersistenceStub.getFlashcardSet();

        // Create view in UI for them
        printViewList(flashcardSet.getFlashcards());

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
                // Set the FlashCard question and answer from the EditText inputs
                Flashcard flashcard = new Flashcard(answerTextBox.getText().toString(),questionTextBox.getText().toString());

                // Clear the EditText inputs
                questionTextBox.setText("");
                answerTextBox.setText("");

                flashcardSet.addFlashCard(flashcard); // Add the FlashCard to the FlashCardSet

                printViewList(flashcardSet.getFlashcards());
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


    public void printViewList(FlashcardSet flashcardSet)
    {
        RecyclerView recyclerView;
        CardViewAdapter cardViewAdapter;
        RecyclerView.LayoutManager layoutManager;

        recyclerView = findViewById(R.id.recycleView);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        cardViewAdapter = new CardViewAdapter(flashcardSet);
        recyclerView.setAdapter(cardViewAdapter);
    }


}