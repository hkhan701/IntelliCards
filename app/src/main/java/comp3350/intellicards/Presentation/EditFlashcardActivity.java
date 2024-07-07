package comp3350.intellicards.Presentation;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.Nullable;

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
    private Button editButton;
    private Button cancelButton;
    private Spinner flashcardSetSpinner;
    private Flashcard currentFlashcard;
    private FlashcardSet currentFlashcardSet;
    private List<FlashcardSet> allFlashcardSets;
    private String username;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_flashcard);

        username = UserSession.getInstance().getUsername(); // Get the username from the UserSession singleton

        initializeManagers();
        initializeViews();
        fetchFlashcard();
        fetchAllFlashcardSetsForUser();
        setUpSpinner();
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

    private void fetchFlashcard() {
        String flashcardUUID = getIntent().getStringExtra("flashcardUUID");
        String flashcardSetUUID = getIntent().getStringExtra("flashcardSetUUID");
        currentFlashcard = flashcardManager.getFlashcard(flashcardUUID);
        if (currentFlashcard != null) {
            currentFlashcardSet = flashcardSetManager.getFlashcardSet(flashcardSetUUID);
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

    private void setUpSpinner() {
        List<String> flashcardSetNames = new ArrayList<>();
        for (FlashcardSet set : allFlashcardSets) {
            flashcardSetNames.add(set.getFlashcardSetName());
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, flashcardSetNames);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        flashcardSetSpinner.setAdapter(adapter);

        // Set the spinner to the current flashcard's set
        int currentPosition = flashcardSetNames.indexOf(currentFlashcardSet.getFlashcardSetName());
        flashcardSetSpinner.setSelection(currentPosition);
    }

    private void setUpListeners() {
        setUpEditButtonListener();
        setUpCancelButtonListener();
    }

    private void setUpEditButtonListener() {
        editButton.setOnClickListener(v -> {
            String newQuestion = questionEditText.getText().toString().trim();
            String newAnswer = answerEditText.getText().toString().trim();
            String newHint = hintEditText.getText().toString().trim();

            // If the selected flashcard set is different from the current flashcard set, move the flashcard to the new set
            FlashcardSet selectedSet = getSelectedFlashcardSet();
            if (!selectedSet.getUUID().equals(currentFlashcardSet.getUUID())) {
                moveFlashcardToNewSet(selectedSet, newQuestion, newAnswer, newHint);
            } else {
                // Update the flashcard if it stays in the same set
                updateFlashcardDetails(newQuestion, newAnswer, newHint);
                flashcardManager.updateFlashcard(currentFlashcard);
            }

            showSuccessMessage();
            sendResultAndFinishEditFlashcardActivity();
        });
    }

    private FlashcardSet getSelectedFlashcardSet() {
        int selectedPosition = flashcardSetSpinner.getSelectedItemPosition();
        return allFlashcardSets.get(selectedPosition);
    }

    private void moveFlashcardToNewSet(FlashcardSet newSet, String newQuestion, String newAnswer, String newHint) {
        // Mark the current flashcard as deleted
        flashcardManager.markFlashcardAsDeleted(currentFlashcard.getUUID());

        // Create a new flashcard with the updated details for the new set
        Flashcard newFlashcard = new Flashcard(newSet.getUUID(), newQuestion, newAnswer, newHint);
        flashcardManager.insertFlashcard(newFlashcard);
        flashcardSetManager.addFlashcardToFlashcardSet(newSet.getUUID(), newFlashcard);

        Toast.makeText(this, "Flashcard moved to new set", Toast.LENGTH_LONG).show();
    }

    private void updateFlashcardDetails(String newQuestion, String newAnswer, String newHint) {
        currentFlashcard.setQuestion(newQuestion);
        currentFlashcard.setAnswer(newAnswer);
        currentFlashcard.setHint(newHint);
    }

    private void showSuccessMessage() {
        Toast.makeText(this, "Successfully updated flashcard", Toast.LENGTH_LONG).show();
    }

    private void sendResultAndFinishEditFlashcardActivity() {
        Intent resultIntent = new Intent();
        resultIntent.putExtra("flashcardUUID", currentFlashcard.getUUID());
        setResult(RESULT_OK, resultIntent);
        finish();
    }

    private void setUpCancelButtonListener() {
        cancelButton.setOnClickListener(v -> finish());
    }
}
