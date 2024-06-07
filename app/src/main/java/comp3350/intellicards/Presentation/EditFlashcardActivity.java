package comp3350.intellicards.Presentation;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.util.List;

import comp3350.intellicards.Business.FlashcardManager;
import comp3350.intellicards.Business.FlashcardSetManager;
import comp3350.intellicards.Business.StubManager;
import comp3350.intellicards.Objects.Flashcard;
import comp3350.intellicards.Objects.FlashcardSet;
import comp3350.intellicards.R;

public class EditFlashcardActivity extends Activity {

    private TextView flashcardSetTitle;
    private FlashcardManager flashcardManager = new FlashcardManager(StubManager.getFlashcardPersistence());
    private FlashcardSetManager flashcardSetManager = new FlashcardSetManager(StubManager.getFlashcardSetPersistence());

    private FlashcardSet selectedFlashcardSet;
    private List<FlashcardSet> flashcardSets;
    private ArrayAdapter<String> adapter;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_flashcard);
        List<Flashcard> flashcardPersistence = flashcardManager.getAllActiveFlashcards();
        List<FlashcardSet> flashcardSetPersistence = flashcardSetManager.getAllFlashcardSets();
        // Get the flashcard set UUID from the intent
        String flashcardSetId = getIntent().getStringExtra("flashcardSetID");
        String flashcardID = getIntent().getStringExtra("flashcardID");
        System.out.println(flashcardSetId);
        System.out.println(flashcardID);

        Flashcard currFlashcard = flashcardManager.getFlashcard(flashcardID);
        FlashcardSet currFlashcardSet = flashcardSetManager.getFlashcardSet(flashcardSetId);
        System.out.println(currFlashcard.toString());
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

            Intent resultIntent = new Intent();
            resultIntent.putExtra("flashcardID", currFlashcard.getUUID()); // Pass the updated flashcard ID
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
