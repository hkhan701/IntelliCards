package comp3350.intellicards.Presentation;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import comp3350.intellicards.Application.Services;
import comp3350.intellicards.Application.UserSession;
import comp3350.intellicards.Business.FlashcardManager;
import comp3350.intellicards.Objects.Flashcard;
import comp3350.intellicards.Objects.FlashcardSet;
import comp3350.intellicards.Business.FlashcardSetManager;
import comp3350.intellicards.R;

public class FlashcardSetActivity extends Activity {

    private RecyclerView flashcardsRecyclerView;
    private FlashcardSetManager flashcardSetManager;
    private FlashcardManager flashcardManager;
    private String username;
    private String flashcardSetUUID;
    private TextView flashcardSetTitle;
    private AppCompatImageButton backButton;
    private AppCompatImageButton addFlashcardButton;
    private Button testButton;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flashcard_set);

        flashcardSetManager = new FlashcardSetManager(Services.getFlashcardSetPersistence());
        flashcardManager = new FlashcardManager(Services.getFlashcardPersistence());
        username = UserSession.getInstance().getUsername(); // Get the username from the UserSession singleton

        retrieveIntentData();
        initializeViews();
        setupListeners();
        loadFlashcardSet();
    }

    private void retrieveIntentData() {
        flashcardSetUUID = getIntent().getStringExtra("flashcardSetUUID");
    }

    private void initializeViews() {
        flashcardSetTitle = findViewById(R.id.flashcardSetTitle);
        flashcardsRecyclerView = findViewById(R.id.flashcardsRecyclerView);
        flashcardsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        backButton = findViewById(R.id.backButton);
        addFlashcardButton = findViewById(R.id.addFlashcardButton);
        testButton = findViewById(R.id.testButton);
    }

    private void setupListeners() {
        setupBackButtonListener();
        setupAddFlashcardButtonListener();
        setupTestButtonListener();
    }

    private void setupBackButtonListener() {
        backButton.setOnClickListener(v -> navigateToMainActivity());
    }

    private void setupAddFlashcardButtonListener() {
        addFlashcardButton.setOnClickListener(v -> navigateToCreateFlashcardActivity());
    }

    private void setupTestButtonListener() {
        testButton.setOnClickListener(v -> handleTestButtonClick());
    }

    private void loadFlashcardSet() {
        if (flashcardSetUUID != null) {
            FlashcardSet flashcardSet = flashcardSetManager.getActiveFlashcardSet(flashcardSetUUID);
            if (flashcardSet != null) {
                flashcardSetTitle.setText(flashcardSet.getFlashcardSetName());
                flashcardsRecyclerView.setAdapter(new CardViewAdapter(flashcardSet)); // Pass the flashcardSet to the adapter
            }
        }
    }

    private void navigateToMainActivity() {
        Intent intent = new Intent(FlashcardSetActivity.this, MainActivity.class);
        startActivity(intent);
    }

    private void navigateToCreateFlashcardActivity() {
        Intent intent = new Intent(FlashcardSetActivity.this, CreateFlashcardActivity.class);
        intent.putExtra("flashcardSetUUID", flashcardSetUUID);
        startActivityForResult(intent, 1);
    }

    private void handleTestButtonClick() {
        if (UserSession.getInstance().isGuest(username)) {
            Toast.makeText(FlashcardSetActivity.this, "Guests cannot take tests. Please log in.", Toast.LENGTH_LONG).show();
        } else if (isFlashcardSetEmpty()) {
            Toast.makeText(FlashcardSetActivity.this, "There are no active flashcards in this set. Please add a flashcard.", Toast.LENGTH_LONG).show();
        } else {
            navigateToFlashcardTestActivity();
        }
    }

    private boolean isFlashcardSetEmpty() {
        FlashcardSet flashcardSet = flashcardSetManager.getActiveFlashcardSet(flashcardSetUUID);
        return flashcardSet != null && flashcardSet.getActiveCount() == 0;
    }

    private void navigateToFlashcardTestActivity() {
        Intent intent = new Intent(FlashcardSetActivity.this, FlashcardTestActivity.class);
        intent.putExtra("flashcardSetUUID", flashcardSetUUID);
        startActivity(intent);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1 && resultCode == RESULT_OK && data != null) {
            handleActivityResult(data);
        }
    }

    private void handleActivityResult(Intent data) {
        String updatedFlashcardID = data.getStringExtra("flashcardUUID");
        FlashcardSet flashcardSet = flashcardSetManager.getActiveFlashcardSet(flashcardSetUUID);
        Flashcard updatedFlashcard = flashcardManager.getFlashcard(updatedFlashcardID);
        if (updatedFlashcard != null) {
            flashcardsRecyclerView.setAdapter(new CardViewAdapter(flashcardSet));
        }
    }
}
