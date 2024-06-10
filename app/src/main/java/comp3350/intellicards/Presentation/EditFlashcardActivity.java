package comp3350.intellicards.Presentation;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;


import comp3350.intellicards.Business.FlashcardManager;
import comp3350.intellicards.Business.StubManager;
import comp3350.intellicards.Objects.Flashcard;
import comp3350.intellicards.R;

public class EditFlashcardActivity extends Activity {
    private FlashcardManager flashcardManager = new FlashcardManager(StubManager.getFlashcardPersistence());

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_flashcard);
        // Get the flashcard UUID from the intent then get the corresponding flashcard
        String flashcardUUID = getIntent().getStringExtra("flashcardUUID");
        Flashcard currFlashcard = flashcardManager.getFlashcard(flashcardUUID);

        //set up the buttons
        setUpEditButton(currFlashcard);
        setUpCancelButton();


    }

    protected void setUpEditButton(Flashcard currFlashcard) {
        TextView questionTextBox = findViewById(R.id.question);
        TextView answerTextBox = findViewById(R.id.answer);
        TextView hintTextBox = findViewById(R.id.hint);
        Button editFlashButton = findViewById(R.id.editFlashcard);

        questionTextBox.setText(currFlashcard.getQuestion());
        answerTextBox.setText(currFlashcard.getAnswer());
        hintTextBox.setText(currFlashcard.getHint());


        editFlashButton.setOnClickListener(v -> {

            currFlashcard.setQuestion(questionTextBox.getText().toString());
            currFlashcard.setAnswer(answerTextBox.getText().toString());
            currFlashcard.setHint(hintTextBox.getText().toString());
            Toast.makeText(this, "Successfully updated flashcard", Toast.LENGTH_LONG).show();

            // send result back to FlashCardSetActivity so that the recycler view can be updated
            Intent resultIntent = new Intent(); 
            resultIntent.putExtra("flashcardUUID", currFlashcard.getUUID()); // Pass the updated flashcard ID
            setResult(RESULT_OK, resultIntent);
            finish();
        });

    }

    protected void setUpCancelButton() {
        Button cancelButton = findViewById(R.id.cancelButton);
        cancelButton.setOnClickListener(v -> {
            finish();
        });
    }


}
