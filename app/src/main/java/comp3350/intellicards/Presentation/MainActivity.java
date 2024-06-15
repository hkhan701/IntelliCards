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

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initializePersistence();
        profilePageButtonOnClick();
        createNewSetButtonOnClick();
    }

    private void initializePersistence() {
        if (!StubManager.isInitialized()) {
            StubManager.initializeStubData();
        }

        flashcardSetManager = new FlashcardSetManager(StubManager.getFlashcardSetPersistence());

        gridLayout = findViewById(R.id.gridLayout);

        loadFlashcardSets();
    }

    // Load all Flashcard Sets from the database
    private void loadFlashcardSets() {
        gridLayout.removeAllViews();

        List<FlashcardSet> flashcardSets = flashcardSetManager.getAllFlashcardSets();
        for (FlashcardSet set : flashcardSets) {
            Button FlashcardSetButton = new Button(this);
            String title = set.getFlashcardSetName() + " (" + set.getActiveCount() + ")";
            FlashcardSetButton.setText(title);
            FlashcardSetButton.setLayoutParams(new GridLayout.LayoutParams(GridLayout.spec(GridLayout.UNDEFINED, 1f), GridLayout.spec(GridLayout.UNDEFINED, 1f)));
            FlashcardSetButton.setPadding(16, 16, 16, 16);
            FlashcardSetButton.setOnClickListener(v -> {
                Intent intent = new Intent(MainActivity.this, FlashcardSetActivity.class);
                intent.putExtra("flashcardSetUUID", set.getUUID());
                startActivity(intent);
            });
            gridLayout.addView(FlashcardSetButton);
        }
    }

    // Show a dialog to create a new Flashcard Set
    private void showCreateNewSetDialog() {
        EditText newSetNameInput = new EditText(this);
        new android.app.AlertDialog.Builder(this).setTitle("Create New Flashcard Set").setMessage("Enter the name for the new Flashcard Set:").setView(newSetNameInput).setPositiveButton("Create", (dialog, whichButton) -> {
            String newSetName = newSetNameInput.getText().toString().trim();
            if (!newSetName.isEmpty()) {
                FlashcardSet newFlashcardSet = new FlashcardSet(newSetName);
                flashcardSetManager.insertFlashcardSet(newFlashcardSet);
                loadFlashcardSets(); // Refresh the list of Flashcard Sets
            } else {
                dialog.dismiss(); // Dismiss the dialog if the user didn't enter a name
            }
        }).setNegativeButton("Cancel", null).show();
    }

    private void profilePageButtonOnClick() {
        Button profilePageButton = findViewById(R.id.profileButton);
        profilePageButton.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, ProfileActivity.class);
            startActivity(intent);
        });
    }

    private void createNewSetButtonOnClick() {
        Button createNewSetButton = findViewById(R.id.createNewSetButton);
        createNewSetButton.setOnClickListener(v -> {
            // Open a dialog to enter the name for the new FlashcardSet
            showCreateNewSetDialog();
        });
    }

}
