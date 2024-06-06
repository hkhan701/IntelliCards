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
import java.util.UUID;

import comp3350.intellicards.Objects.Flashcard;
import comp3350.intellicards.Objects.FlashcardSet;
import comp3350.intellicards.Persistence.FlashcardPersistence;
import comp3350.intellicards.Persistence.FlashcardSetPersistence;
import comp3350.intellicards.Persistence.InitializePersistence;
import comp3350.intellicards.R;

public class MainActivity extends Activity {

    private FlashcardSetPersistence flashcardSetPersistence;
    private GridLayout gridLayout;
    private Button createNewSetButton;
    private Button profileButton;

    // This can be moved to when we create the flashcard page
    // Anything related to creating the flashcard should be moved from this main activity
    private FlashcardSet selectedFlashcardSet;
    private List<FlashcardSet> flashcardSets;
    private ArrayAdapter<String> adapter;
    private FlashcardPersistence flashcardPersistence;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initializePersistence();

        flashcardPersistence = InitializePersistence.getFlashcardPersistence();
        flashcardSetPersistence = InitializePersistence.getFlashcardSetPersistence();

        setupFlashcardSetSpinner();
        setupSubmitButton();
        setupProfileButton();
        setupCreateNewSetButton();
//        setupEditButton();

        displayInitialFlashcards();
    }

    private void initializePersistence() {
        if (!InitializePersistence.isInitialized()) {
            InitializePersistence.initializeStubData();
        }

        flashcardSetPersistence = InitializePersistence.getFlashcardSetPersistence();
        flashcardPersistence = InitializePersistence.getFlashcardPersistence();

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

        List<FlashcardSet> flashcardSets = flashcardSetPersistence.getAllFlashcardSets();
        for (FlashcardSet set : flashcardSets) {
            Button button = new Button(this);
            button.setText(set.getFlashCardSetName());
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
                        flashcardSetPersistence.insertFlashcardSet(newFlashcardSet);
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
        flashcardSets = flashcardSetPersistence.getAllFlashcardSets();
        List<String> flashcardSetNames = new ArrayList<>();

        for (FlashcardSet set : flashcardSets) {
            flashcardSetNames.add(set.getFlashCardSetName());
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

            flashcardPersistence.insertFlashcard(flashcard);
            flashcardSetPersistence.addFlashCardToFlashcardSet(selectedFlashcardSet, flashcard);
        });
    }
    private void setupEditButton() {
        TextView questionTextBox = findViewById(R.id.question);
        TextView answerTextBox = findViewById(R.id.answer);
        TextView hintTextBox = findViewById(R.id.hint);
        //Button submitTextButton = findViewById(R.id.submitFlashcard);
        Button editFlashButton = findViewById(R.id.editButton);
        //TextView flashcardToEdit = findViewById(R.id.flashcardTextRecycle);

        editFlashButton.setOnClickListener(v -> {
            //Flashcard editFlashcard = selectedFlashcardSet.getFlashCardById((UUID)editFlashButton.getTag());

            questionTextBox.setText("");
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

    private void showCreateNewSetDialog() {
        // Show an input dialog to get the new set name
        EditText newSetNameInput = new EditText(this);
        new android.app.AlertDialog.Builder(this)
                .setTitle("Create New Flashcard Set")
                .setMessage("Enter the name for the new Flashcard Set:")
                .setView(newSetNameInput)
                .setPositiveButton("Create", (dialog, whichButton) -> {
                    String newSetName = newSetNameInput.getText().toString().trim();
                    if (!newSetName.isEmpty()) {
                        FlashcardSet newFlashcardSet = new FlashcardSet(newSetName);
                        flashcardSetPersistence.insertFlashcardSet(newFlashcardSet);

                        // Refresh the flashcard sets list and adapter
                        flashcardSets.add(newFlashcardSet);
                        adapter.add(newSetName);
                        adapter.notifyDataSetChanged();

                        // Set the new flashcard set as the selected set
                        selectedFlashcardSet = newFlashcardSet;

                        // Refresh the view to display the new flashcard set
                        printViewList(selectedFlashcardSet);
                    }
                })
                .setNegativeButton("Cancel", null)
                .show();
    }

    private void displayInitialFlashcards() {
        FlashcardSet initialFlashcardSet = flashcardSetPersistence.getAllFlashcardSets().get(0).getActiveFlashcards();
        printViewList(initialFlashcardSet);
    }

    public void printViewList(FlashcardSet flashcardSet) {
        RecyclerView recyclerView = findViewById(R.id.recycleView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new CardViewAdapter(flashcardSet));
    }
}
