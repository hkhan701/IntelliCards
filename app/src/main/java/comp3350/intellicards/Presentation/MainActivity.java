package comp3350.intellicards.Presentation;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridLayout;

import androidx.annotation.Nullable;

import java.util.List;

import comp3350.intellicards.Objects.FlashcardSet;
import comp3350.intellicards.Business.FlashcardSetManager;
import comp3350.intellicards.Business.StubManager;
import comp3350.intellicards.R;

public class MainActivity extends Activity {

    private FlashcardSetManager flashcardSetManager;
    private GridLayout gridLayout;
    private String username;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        username = getIntent().getStringExtra("username"); // Get the username from the intent
        initializePersistence();
        setupButtons();
    }

    private void initializePersistence() {
        if (!StubManager.isInitialized()) {
            StubManager.initializeStubData();
        }

        flashcardSetManager = new FlashcardSetManager();
        gridLayout = findViewById(R.id.gridLayout);

        loadFlashcardSets();
    }

    // Load all Flashcard Sets from the database
    private void loadFlashcardSets() {
        gridLayout.removeAllViews();

        List<FlashcardSet> flashcardSets = flashcardSetManager.getFlashcardSetsByUsername(username);
        for (FlashcardSet set : flashcardSets) {
            Button flashcardSetButton = new Button(this);
            String title = set.getFlashcardSetName() + " (" + set.getActiveCount() + ")";
            flashcardSetButton.setText(title);
            flashcardSetButton.setLayoutParams(new GridLayout.LayoutParams(GridLayout.spec(GridLayout.UNDEFINED, 1f), GridLayout.spec(GridLayout.UNDEFINED, 1f)));
            flashcardSetButton.setPadding(16, 16, 16, 16);
            flashcardSetButton.setOnClickListener(v -> openFlashcardSetActivity(set.getUUID()));
            gridLayout.addView(flashcardSetButton);
        }
    }

    private void openFlashcardSetActivity(String flashcardSetUUID) {
        Intent intent = new Intent(MainActivity.this, FlashcardSetActivity.class);
        intent.putExtra("flashcardSetUUID", flashcardSetUUID);
        startActivity(intent);
    }

    private void showCreateNewSetDialog() {
        EditText newSetNameInput = new EditText(this);
        new android.app.AlertDialog.Builder(this)
                .setTitle("Create New Flashcard Set")
                .setMessage("Enter the name for the new Flashcard Set:")
                .setView(newSetNameInput)
                .setPositiveButton("Create", (dialog, whichButton) -> {
                    String newSetName = newSetNameInput.getText().toString().trim();
                    if (!newSetName.isEmpty()) {
                        FlashcardSet newFlashcardSet = new FlashcardSet(username, newSetName);
                        flashcardSetManager.insertFlashcardSet(newFlashcardSet);
                        loadFlashcardSets(); // Refresh the list of Flashcard Sets
                    } else {
                        dialog.dismiss(); // Dismiss the dialog if the user didn't enter a name
                    }
                })
                .setNegativeButton("Cancel", null)
                .show();
    }

    private void setupButtons() {
        setupProfilePageButton();
        setupCreateNewSetButton();
    }

    private void setupProfilePageButton() {
        Button profilePageButton = findViewById(R.id.profileButton);
        profilePageButton.setOnClickListener(v -> openProfileActivity());
    }

    private void openProfileActivity() {
        Intent intent = new Intent(MainActivity.this, ProfileActivity.class);
        intent.putExtra("username", username); // Pass the username to the profile page
        startActivity(intent);
    }

    private void setupCreateNewSetButton() {
        Button createNewSetButton = findViewById(R.id.createNewSetButton);
        createNewSetButton.setOnClickListener(v -> showCreateNewSetDialog());
    }
}
