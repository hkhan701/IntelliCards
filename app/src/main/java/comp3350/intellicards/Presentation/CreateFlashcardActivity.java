package comp3350.intellicards.Presentation;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import comp3350.intellicards.Application.Services;
import comp3350.intellicards.Business.FlashcardManager;
import comp3350.intellicards.Business.FlashcardSetManager;
import comp3350.intellicards.Objects.Flashcard;
import comp3350.intellicards.Objects.FlashcardSet;
import comp3350.intellicards.R;

public class CreateFlashcardActivity extends Activity {

    private FlashcardSetManager flashcardSetManager;
    private FlashcardManager flashcardManager;
    private EditText questionEditText;
    private EditText answerEditText;
    private EditText hintEditText;
    private Button submitButton;
    private Button cancelButton;
    private FlashcardSet currentFlashcardSet;
    private String flashcardSetUUID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_flashcard);

        initializeManagers();
        initializeViews();
        setUpListeners();
    }

    private void initializeManagers() {
        flashcardSetManager = new FlashcardSetManager(Services.getFlashcardSetPersistence());
        flashcardManager = new FlashcardManager(Services.getFlashcardPersistence());
    }

    private void initializeViews() {
        questionEditText = findViewById(R.id.question);
        answerEditText = findViewById(R.id.answer);
        hintEditText = findViewById(R.id.hint);
        submitButton = findViewById(R.id.createFlashcardButton);
        cancelButton = findViewById(R.id.cancelButton);

        // Get the flashcard set UUID from the intent and fetch the corresponding FlashcardSet
        flashcardSetUUID = getIntent().getStringExtra("flashcardSetUUID");
        currentFlashcardSet = flashcardSetManager.getFlashcardSet(flashcardSetUUID);
    }

    private void setUpListeners() {
        setUpSubmitButtonListener();
        setUpCancelButtonListener();
    }

    private void setUpSubmitButtonListener() {
        submitButton.setOnClickListener(v -> {
            Flashcard newFlashcard = createFlashcardFromInput();

            if (newFlashcard != null) {
                addFlashcardToSet(newFlashcard);
                clearInputFields();
                showSuccessMessage();
                sendResultAndFinish(newFlashcard);
            }
        });
    }

    private Flashcard createFlashcardFromInput() {
        String question = questionEditText.getText().toString();
        String answer = answerEditText.getText().toString();
        String hint = hintEditText.getText().toString();

        if (question.isEmpty() || answer.isEmpty()) {
            Toast.makeText(this, "Question and answer fields cannot be empty", Toast.LENGTH_LONG).show();
            return null;
        }

        return new Flashcard(flashcardSetUUID, question, answer,  hint);
    }

    private void addFlashcardToSet(Flashcard flashcard) {
        flashcardManager.insertFlashcard(flashcard);
        flashcardSetManager.addFlashcardToFlashcardSet(currentFlashcardSet.getUUID(), flashcard);
    }

    private void clearInputFields() {
        questionEditText.setText("");
        answerEditText.setText("");
        hintEditText.setText("");
    }

    private void showSuccessMessage() {
        Toast.makeText(this, "Flashcard added successfully", Toast.LENGTH_LONG).show();
    }

    private void sendResultAndFinish(Flashcard flashcard) {
        Intent resultIntent = new Intent();
        resultIntent.putExtra("flashcardUUID", flashcard.getUUID());
        setResult(Activity.RESULT_OK, resultIntent);
        finish();
    }

    private void setUpCancelButtonListener() {
        cancelButton.setOnClickListener(v -> finish());
    }
}
