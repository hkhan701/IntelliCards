package comp3350.intellicards.Presentation;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageButton;

import java.util.ArrayList;
import java.util.List;

import comp3350.intellicards.Application.Services;
import comp3350.intellicards.Application.UserSession;
import comp3350.intellicards.Business.FlashcardManager;
import comp3350.intellicards.Business.FlashcardSetManager;
import comp3350.intellicards.Objects.Flashcard;
import comp3350.intellicards.Objects.FlashcardSet;
import comp3350.intellicards.R;

public class EditFlashcardActivity extends Activity {

    private FlashcardManager flashcardManager;
    private FlashcardSetManager flashcardSetManager;
    private EditText questionEditText;
    private EditText answerEditText;
    private EditText hintEditText;
    private AppCompatImageButton editButton;
    private AppCompatImageButton cancelButton;
    private Spinner flashcardSetSpinner;
    private Flashcard currentFlashcard;
    private FlashcardSet currentFlashcardSet;
    private List<FlashcardSet> allFlashcardSets;
    private String username;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_flashcard);

        username = UserSession.getInstance(this).getUsername();

        initializeManagers();
        initializeViews();
        retrieveIntentData();
        fetchFlashcard();
        fetchAllFlashcardSetsForUser();
        setUpFlashcardSetSpinner();
        setUpListeners();
    }

    private void initializeManagers() {
        flashcardManager = new FlashcardManager(Services.getFlashcardPersistence());
        flashcardSetManager = new FlashcardSetManager(Services.getFlashcardSetPersistence());
    }

    private void initializeViews() {
        questionEditText = findViewById(R.id.question);
        answerEditText = findViewById(R.id.answer);
        hintEditText = findViewById(R.id.hint);
        editButton = findViewById(R.id.editFlashcard);
        cancelButton = findViewById(R.id.cancelButton);
        flashcardSetSpinner = findViewById(R.id.flashcardSetSpinner);
    }

    private void retrieveIntentData() {
        String flashcardUUID = getIntent().getStringExtra("flashcardUUID");
        String flashcardSetUUID = getIntent().getStringExtra("flashcardSetUUID");
        currentFlashcard = flashcardManager.getFlashcard(flashcardUUID);
        currentFlashcardSet = flashcardSetManager.getFlashcardSet(flashcardSetUUID);
    }

    private void fetchFlashcard() {
        if (currentFlashcard != null) {
            populateFlashcardDetails();
        } else {
            Toast.makeText(this, "Flashcard not found", Toast.LENGTH_LONG).show();
            finish();
        }
    }

    private void fetchAllFlashcardSetsForUser() {
        allFlashcardSets = flashcardSetManager.getFlashcardSetsByUsername(username);
    }

    private void populateFlashcardDetails() {
        questionEditText.setText(currentFlashcard.getQuestion());
        answerEditText.setText(currentFlashcard.getAnswer());
        hintEditText.setText(currentFlashcard.getHint());
    }

    private void setUpFlashcardSetSpinner() {
        List<String> flashcardSetNames = new ArrayList<>();
        for (FlashcardSet set : allFlashcardSets) {
            flashcardSetNames.add(set.getFlashcardSetName());
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, flashcardSetNames);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        flashcardSetSpinner.setAdapter(adapter);

        int currentPosition = flashcardSetNames.indexOf(currentFlashcardSet.getFlashcardSetName());
        flashcardSetSpinner.setSelection(currentPosition);
    }

    private void setUpListeners() {
        setUpConfirmEditButtonListener();
        setUpCancelButtonListener();
    }

    private void setUpConfirmEditButtonListener() {
        editButton.setOnClickListener(v -> {
            String newQuestion = questionEditText.getText().toString().trim();
            String newAnswer = answerEditText.getText().toString().trim();
            String newHint = hintEditText.getText().toString().trim();

            FlashcardSet selectedSet = getSelectedFlashcardSet();
            flashcardManager.updateFlashcard(currentFlashcard, selectedSet, newQuestion, newAnswer, newHint);

            showSuccessMessage();
            sendResultAndFinish();
        });
    }

    private FlashcardSet getSelectedFlashcardSet() {
        int selectedPosition = flashcardSetSpinner.getSelectedItemPosition();
        return allFlashcardSets.get(selectedPosition);
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
