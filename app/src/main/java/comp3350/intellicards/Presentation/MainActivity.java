package comp3350.intellicards.Presentation;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

import comp3350.intellicards.Objects.Flashcard;
import comp3350.intellicards.Objects.FlashcardSet;

import comp3350.intellicards.Business.FlashcardManager;
import comp3350.intellicards.Business.FlashcardSetManager;

import comp3350.intellicards.Business.StubManager;

import comp3350.intellicards.R;

public class MainActivity extends Activity {

    private StubManager stubManager;

    //private FlashcardSetPersistence flashcardSetPersistence;
    private FlashcardSetManager flashcardSetManager;
    private GridLayout gridLayout;
    private Button createNewSetButton;
    private Button profileButton;

    // This can be moved to when we create the flashcard page
    // Anything related to creating the flashcard should be moved from this main activity
    private FlashcardSet selectedFlashcardSet;
    private List<FlashcardSet> flashcardSets;
    private ArrayAdapter<String> adapter;
    //private FlashcardPersistence flashcardPersistence;
    private FlashcardManager flashcardManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initializePersistence();

        setupFlashcardSetSpinner();
        setupSubmitButton();
        setupProfileButton();
        setupCreateNewSetButton();
//        setupEditButton();

    }

    private void initializePersistence() {
        if (!StubManager.isInitialized()) {
            StubManager.initializeStubData();
        }

        flashcardManager = new FlashcardManager(StubManager.getFlashcardPersistence());
        flashcardSetManager = new FlashcardSetManager(StubManager.getFlashcardSetPersistence());

        setupFlashcardSetSpinner();
        setupSubmitButton();

        gridLayout = findViewById(R.id.gridLayout);
        createNewSetButton = findViewById(R.id.createNewSetButton);
        profileButton = findViewById(R.id.profileButton);
        createNewSetButton = findViewById(R.id.createNewSetButton);

        createNewSetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showCreateNewSetDialog();
            }
        });

        profileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ProfileActivity.class);
                startActivity(intent);
            }
        });

        loadFlashcardSets();
    }

    // Load all Flashcard Sets from the database
    private void loadFlashcardSets() {
        gridLayout.removeAllViews();

        List<FlashcardSet> flashcardSets = flashcardSetManager.getAllFlashcardSets();
        for (FlashcardSet set : flashcardSets) {
            Button button = new Button(this);
            button.setText(set.getFlashcardSetName());
            button.setLayoutParams(new GridLayout.LayoutParams(
                    GridLayout.spec(GridLayout.UNDEFINED, 1f),
                    GridLayout.spec(GridLayout.UNDEFINED, 1f)
            ));
            button.setPadding(16, 16, 16, 16);
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(MainActivity.this, FlashcardSetActivity.class);
                    intent.putExtra("flashcardSetId", set.getUUID());
                    startActivity(intent);
                }
            });
            gridLayout.addView(button);
        }
    }

    // Show a dialog to create a new Flashcard Set
    private void showCreateNewSetDialog() {
        EditText newSetNameInput = new EditText(this);
        new android.app.AlertDialog.Builder(this)
                .setTitle("Create New Flashcard Set")
                .setMessage("Enter the name for the new Flashcard Set:")
                .setView(newSetNameInput)
                .setPositiveButton("Create", (dialog, whichButton) -> {
                    String newSetName = newSetNameInput.getText().toString().trim();
                    if (!newSetName.isEmpty()) {
                        FlashcardSet newFlashcardSet = new FlashcardSet(newSetName);
                        flashcardSetManager.insertFlashcardSet(newFlashcardSet);
                        loadFlashcardSets(); // Refresh the list of Flashcard Sets
                    } else {
                        dialog.dismiss(); // Dismiss the dialog if the user didn't enter a name
                    }
                })
                .setNegativeButton("Cancel", null)
                .show();
    }

    private void setupFlashcardSetSpinner() {
        Spinner flashcardSetSpinner = findViewById(R.id.flashcardSetSpinner);
        flashcardSets = flashcardSetManager.getAllFlashcardSets();
        List<String> flashcardSetNames = new ArrayList<>();

        for (FlashcardSet set : flashcardSets) {
            flashcardSetNames.add(set.getFlashcardSetName());
        }

        adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, flashcardSetNames);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        flashcardSetSpinner.setAdapter(adapter);

        flashcardSetSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedFlashcardSet = flashcardSets.get(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                selectedFlashcardSet = null;
            }
        });
    }

    private void setupSubmitButton() {
        TextView questionTextBox = findViewById(R.id.question);
        TextView answerTextBox = findViewById(R.id.answer);
        TextView hintTextBox = findViewById(R.id.hint);
        Button submitTextButton = findViewById(R.id.submitFlashcard);

        questionTextBox.setOnClickListener(v -> questionTextBox.setText(""));
        answerTextBox.setOnClickListener(v -> answerTextBox.setText(""));
        hintTextBox.setOnClickListener(v -> hintTextBox.setText(""));

        submitTextButton.setOnClickListener(v -> {
            Flashcard flashcard = new Flashcard(answerTextBox.getText().toString(),
                    questionTextBox.getText().toString(), hintTextBox.getText().toString());

            questionTextBox.setText("");
            answerTextBox.setText("");
            hintTextBox.setText("");

            flashcardManager.insertFlashcard(flashcard);
            flashcardSetManager.addFlashCardToFlashcardSet(selectedFlashcardSet, flashcard);
        });
    }
    private void setupEditButton() {
//        TextView questionTextBox = findViewById(R.id.question);
        TextView answerTextBox = findViewById(R.id.answer);
        TextView hintTextBox = findViewById(R.id.hint);
        //Button submitTextButton = findViewById(R.id.submitFlashcard);
        Button editFlashButton = findViewById(R.id.editButton);
        //TextView flashcardToEdit = findViewById(R.id.flashcardTextRecycle);

        editFlashButton.setOnClickListener(v -> {
            //Flashcard editFlashcard = selectedFlashcardSet.getFlashCardById((UUID)editFlashButton.getTag());

//            questionTextBox.setText("");
            answerTextBox.setText("");
            hintTextBox.setText("");
        });
    }

    private void setupProfileButton() {
        Button profilePageButton = findViewById(R.id.profileButton);
        profilePageButton.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, ProfileActivity.class);
            startActivity(intent);

        });
    }

    private void setupCreateNewSetButton() {
        Button createNewSetButton = findViewById(R.id.createNewSetButton);
        createNewSetButton.setOnClickListener(v -> {
            // Open a dialog to enter the name for the new FlashcardSet
            showCreateNewSetDialog();
        });
    }

}
