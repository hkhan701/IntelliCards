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

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_flashcard);

        initializeManagers();
        initializeViews();
        fetchFlashcard();
        fetchAllFlashcardSets();
        setUpSpinner();
        setUpListeners();
    }

    private void initializeManagers() {
        flashcardManager = new FlashcardManager();
        flashcardSetManager = new FlashcardSetManager();
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

    private void fetchAllFlashcardSets() {
        allFlashcardSets = flashcardSetManager.getAllFlashcardSets();
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
        int currentPosition = allFlashcardSets.indexOf(currentFlashcardSet);
        flashcardSetSpinner.setSelection(currentPosition);
    }

    private void setUpListeners() {
        setUpEditButtonListener();
        setUpCancelButtonListener();
    }

    private void setUpEditButtonListener() {
        editButton.setOnClickListener(v -> {
            updateFlashcardDetails();

            // If the selected flashcard set is different from the current flashcard set, move the flashcard to the new set
            FlashcardSet selectedSet = getSelectedFlashcardSet();
            if (!selectedSet.equals(currentFlashcardSet)) {
                moveFlashcardToNewSet(selectedSet);
            }

            flashcardManager.updateFlashcard(currentFlashcard);

            showSuccessMessage();
            sendResultAndFinish();
        });
    }

    private FlashcardSet getSelectedFlashcardSet() {
        int selectedPosition = flashcardSetSpinner.getSelectedItemPosition();
        return allFlashcardSets.get(selectedPosition);
    }

    private void moveFlashcardToNewSet(FlashcardSet newSet) {
        flashcardManager.markFlashcardAsDeleted(currentFlashcard.getUUID());
        Flashcard newFlashcard = new Flashcard(currentFlashcard.getSetID(), currentFlashcard.getAnswer(), currentFlashcard.getQuestion(), currentFlashcard.getHint());

        flashcardManager.insertFlashcard(newFlashcard);
        flashcardSetManager.addFlashcardToFlashcardSet(newSet.getUUID(), newFlashcard);

        Toast.makeText(this, "Flashcard moved to new set", Toast.LENGTH_LONG).show();
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
