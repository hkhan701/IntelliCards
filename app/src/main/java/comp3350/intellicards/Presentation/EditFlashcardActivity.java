package comp3350.intellicards.Presentation;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;

import comp3350.intellicards.Business.FlashcardManager;
import comp3350.intellicards.Business.StubManager;
import comp3350.intellicards.Objects.Flashcard;
import comp3350.intellicards.R;

public class EditFlashcardActivity extends Activity {

    private FlashcardManager flashcardManager;
    private EditText questionEditText;
    private EditText answerEditText;
    private EditText hintEditText;
    private Button editButton;
    private Button cancelButton;
    private Flashcard currentFlashcard;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_flashcard);

        initializeManager();
        initializeViews();
        fetchFlashcard();
        setUpListeners();
    }

    private void initializeManager() {
        flashcardManager = new FlashcardManager(StubManager.getFlashcardPersistence());
    }

    private void initializeViews() {
        questionEditText = findViewById(R.id.question);
        answerEditText = findViewById(R.id.answer);
        hintEditText = findViewById(R.id.hint);
        editButton = findViewById(R.id.editFlashcard);
        cancelButton = findViewById(R.id.cancelButton);
    }

    private void fetchFlashcard() {
        String flashcardUUID = getIntent().getStringExtra("flashcardUUID");
        currentFlashcard = flashcardManager.getFlashcard(flashcardUUID);
        if (currentFlashcard != null) {
            populateFlashcardDetails();
        } else {
            Toast.makeText(this, "Flashcard not found", Toast.LENGTH_LONG).show();
            finish();
        }
    }

    private void populateFlashcardDetails() {
        questionEditText.setText(currentFlashcard.getQuestion());
        answerEditText.setText(currentFlashcard.getAnswer());
        hintEditText.setText(currentFlashcard.getHint());
    }

    private void setUpListeners() {
        setUpEditButtonListener();
        setUpCancelButtonListener();
    }

    private void setUpEditButtonListener() {
        editButton.setOnClickListener(v -> {
            updateFlashcardDetails();
            flashcardManager.updateFlashcard(currentFlashcard);
            showSuccessMessage();
            sendResultAndFinish();
        });
    }

    private void updateFlashcardDetails() {
        currentFlashcard.setQuestion(questionEditText.getText().toString());
        currentFlashcard.setAnswer(answerEditText.getText().toString());
        currentFlashcard.setHint(hintEditText.getText().toString());
    }

    private void showSuccessMessage() {
        Toast.makeText(this, "Successfully updated flashcard", Toast.LENGTH_LONG).show();
    }

    private void sendResultAndFinish() {
        Intent resultIntent = new Intent();
        resultIntent.putExtra("flashcardUUID", currentFlashcard.getUUID());
        setResult(RESULT_OK, resultIntent);
        finish();
    }

    private void setUpCancelButtonListener() {
        cancelButton.setOnClickListener(v -> finish());
    }
}
