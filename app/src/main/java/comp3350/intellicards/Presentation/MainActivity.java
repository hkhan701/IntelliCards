package comp3350.intellicards.Presentation;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputFilter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageButton;

import java.util.List;

import comp3350.intellicards.Application.Services;
import comp3350.intellicards.Application.UserSession;
import comp3350.intellicards.Business.FlashcardManager;
import comp3350.intellicards.Objects.FlashcardSet;
import comp3350.intellicards.Business.FlashcardSetManager;
import comp3350.intellicards.R;

public class MainActivity extends Activity {

    private FlashcardSetManager flashcardSetManager;
    private String username;
    private GridLayout flashcardSetGridLayout;
    private AppCompatImageButton profilePageButton;
    private Button createNewSetButton;
    private ImageButton searchButton;
    private EditText searchEditText;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        username = UserSession.getInstance(this).getUsername(); // Get the username from the UserSession singleton
        flashcardSetManager = new FlashcardSetManager(Services.getFlashcardSetPersistence());
        FlashcardManager flashcardManager = new FlashcardManager(Services.getFlashcardPersistence());

        initializeViews();
        setupListeners();
        loadFlashcardSets();
    }

    private void initializeViews() {
        flashcardSetGridLayout = findViewById(R.id.gridLayout);
        profilePageButton = findViewById(R.id.profileButton);
        createNewSetButton = findViewById(R.id.createNewSetButton);
        searchButton = findViewById(R.id.searchButton);
        searchEditText = findViewById(R.id.search);
    }

    // Load all Flashcard Sets from the database
    private void loadFlashcardSets() {
        flashcardSetGridLayout.removeAllViews();

        List<FlashcardSet> flashcardSets = flashcardSetManager.getFlashcardSetsByUsername(username);
        for (FlashcardSet set : flashcardSets) {
            Button flashcardSetButton = createFlashcardSetButton(set);
            flashcardSetGridLayout.addView(flashcardSetButton);
        }
    }

    private Button createFlashcardSetButton(FlashcardSet set) {
        Button flashcardSetButton = new Button(this);
        String title = set.getFlashcardSetName() + " (" + set.getActiveCount() + ") ";
        flashcardSetButton.setText(title);
        flashcardSetButton.setLayoutParams(new GridLayout.LayoutParams(
                GridLayout.spec(GridLayout.UNDEFINED, 1f),
                GridLayout.spec(GridLayout.UNDEFINED, 1f)
        ));
        flashcardSetButton.setPadding(16, 16, 16, 16);
        flashcardSetButton.setOnClickListener(v -> navigateToFlashcardSetActivity(set.getUUID()));
        flashcardSetButton.setHeight(350);
        flashcardSetButton.setWidth(55);
        flashcardSetButton.setTextSize(25);
        flashcardSetButton.setBackgroundColor(0xFFFFFFFD);
        return flashcardSetButton;
    }

    private void showCreateNewSetDialog() {
        final int MAX_NAME_LENGTH = 25;

        EditText newSetNameInput = new EditText(this);
        newSetNameInput.setFilters(new InputFilter[]{new InputFilter.LengthFilter(MAX_NAME_LENGTH)});

        new android.app.AlertDialog.Builder(this)
                .setTitle("Create New Flashcard Set")
                .setMessage("Enter the name for the new Flashcard Set:")
                .setView(newSetNameInput)
                .setPositiveButton("Create", (dialog, whichButton) -> {
                    String newSetName = newSetNameInput.getText().toString().trim();
                    if (!newSetName.isEmpty()) {
                        createNewFlashcardSet(newSetName);
                    } else {
                        dialog.dismiss(); // Dismiss the dialog if the user didn't enter a name
                    }
                })
                .setNegativeButton("Cancel", null)
                .show();
    }

    private void createNewFlashcardSet(String newSetName) {
        FlashcardSet newFlashcardSet = new FlashcardSet(username, newSetName);
        flashcardSetManager.insertFlashcardSet(newFlashcardSet);
        loadFlashcardSets(); // Refresh the list of Flashcard Sets
    }

    private void setupListeners() {
        setupProfilePageButtonListener();
        setupCreateNewSetButtonListener();
        setupSearchButtonListener();
    }

    private void setupProfilePageButtonListener() {
        profilePageButton.setOnClickListener(v -> navigateToProfileActivity());
    }

    private void setupCreateNewSetButtonListener() {
        createNewSetButton.setOnClickListener(v -> showCreateNewSetDialog());
    }

    private void navigateToProfileActivity() {
        Intent intent = new Intent(MainActivity.this, ProfileActivity.class);
        startActivity(intent);
    }

    private void navigateToFlashcardSetActivity(String flashcardSetUUID) {
        Intent intent = new Intent(MainActivity.this, FlashcardSetActivity.class);
        intent.putExtra("flashcardSetUUID", flashcardSetUUID);
        startActivity(intent);
    }

    private void setupSearchButtonListener() {
        searchButton.setOnClickListener(v -> handleSearchButtonClick());
    }

    private void handleSearchButtonClick() {
        String searchKey = searchEditText.getText().toString().trim();
        if(searchKey.isEmpty()) {
            loadFlashcardSets();
            Toast.makeText(this, "Displaying all Flashcard Sets", Toast.LENGTH_SHORT).show();
        }
        else {
            flashcardSetGridLayout.removeAllViews();

            List<FlashcardSet> flashcardSets = flashcardSetManager.getFlashcardSetsByKey(username, searchKey);
            for (FlashcardSet set : flashcardSets) {
                Button flashcardSetButton = createFlashcardSetButton(set);
                flashcardSetGridLayout.addView(flashcardSetButton);
            }
            Toast.makeText(this, "Displaying search results for \"" + searchKey + "\"", Toast.LENGTH_SHORT).show();
        }
    }

}
