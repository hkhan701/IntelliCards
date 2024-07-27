package comp3350.intellicards.Presentation;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

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
    private String usernameFromNotification;
    private TextView flashcardSetTitle;
    private AppCompatImageButton backButton;
    private AppCompatImageButton addFlashcardButton;
    private Button testButton;
    private Button addReminderButton;
    private NotificationManager notificationManager;
    private ImageButton searchButton;
    private EditText searchEditText;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flashcard_set);

        flashcardSetManager = new FlashcardSetManager(Services.getFlashcardSetPersistence());
        flashcardManager = new FlashcardManager(Services.getFlashcardPersistence());
        username = UserSession.getInstance(this).getUsername(); // Get the username from the UserSession singleton
        notificationManager = new NotificationManager(this);

        retrieveIntentData();
        checkUserLoginFromNotification();
        initializeViews();
        setupListeners();
        loadFlashcardSet();
    }

    private void retrieveIntentData() {
        flashcardSetUUID = getIntent().getStringExtra("flashcardSetUUID");
        usernameFromNotification = getIntent().getStringExtra("username");
    }

    private void checkUserLoginFromNotification() {
        // Check if the user is logged in from notification
        if (username == null || (usernameFromNotification != null && !username.equals(usernameFromNotification))) {
            // User is not logged in, redirect to AuthActivity in the case where we click the notification after logging out
            Toast.makeText(this, "You are not logged in the correct user. Please login to the correct user.", Toast.LENGTH_LONG).show();
            Intent intent = new Intent(this, AuthActivity.class);
            startActivity(intent);
            finish(); // Destroy current activity;
        }
    }

    private void initializeViews() {
        flashcardSetTitle = findViewById(R.id.flashcardSetTitle);
        flashcardsRecyclerView = findViewById(R.id.flashcardsRecyclerView);
        flashcardsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        backButton = findViewById(R.id.backButton);
        addFlashcardButton = findViewById(R.id.addFlashcardButton);
        testButton = findViewById(R.id.testButton);
        addReminderButton = findViewById(R.id.addReminderButton);
        searchButton = findViewById(R.id.searchButton);
        searchEditText = findViewById(R.id.search);
    }

    private void setupListeners() {
        setupBackButtonListener();
        setupAddFlashcardButtonListener();
        setupTestButtonListener();
        setupAddReminderButtonListener();
        setupSearchButtonListener();
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

    private void setupSearchButtonListener() {
        searchButton.setOnClickListener(v -> handleSearchButtonClick());
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
        if (UserSession.getInstance(this).isGuest(username)) {
            Toast.makeText(FlashcardSetActivity.this, "Guests cannot take tests. Please log in.", Toast.LENGTH_LONG).show();
        } else if (isFlashcardSetEmpty()) {
            Toast.makeText(FlashcardSetActivity.this, "There are no active flashcards in this set. Please add a flashcard.", Toast.LENGTH_LONG).show();
        } else {
            navigateToFlashcardTestActivity();
        }
    }

    private void handleSearchButtonClick() {
        String searchKey = searchEditText.getText().toString().trim();
        if(searchKey.isEmpty()) {
            loadFlashcardSet();
            Toast.makeText(this, "Displaying all flashcards", Toast.LENGTH_SHORT).show();
        }
        else {
            List<Flashcard> searchedFlashcards = flashcardManager.getFlashcardsByKey(searchKey);
            flashcardsRecyclerView.setAdapter(new CardViewAdapter(flashcardSetManager.getSearchedFlashcards(flashcardSetUUID, searchedFlashcards)));
            Toast.makeText(this, "Displaying search results for \"" + searchKey + "\"", Toast.LENGTH_SHORT).show();
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
        String updatedFlashcardUUID = data.getStringExtra("flashcardUUID");
        FlashcardSet flashcardSet = flashcardSetManager.getActiveFlashcardSet(flashcardSetUUID);
        Flashcard updatedFlashcard = flashcardManager.getFlashcard(updatedFlashcardUUID);
        if (updatedFlashcard != null) {
            flashcardsRecyclerView.setAdapter(new CardViewAdapter(flashcardSet));
        }
    }

    private void setupAddReminderButtonListener() {
        addReminderButton.setOnClickListener(v -> {
            if (notificationManager.checkNotificationPermission()) {
                notificationManager.showDateTimePicker(this, flashcardSetUUID);
            } else {
                notificationManager.requestNotificationPermission(this);
            }
        });
    }

    // Handle the permission request result for the notification permission
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == NotificationManager.NOTIFICATION_PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                notificationManager.showDateTimePicker(this, flashcardSetUUID);
            } else {
                Toast.makeText(this, "Notification permission denied. Unable to set reminder.", Toast.LENGTH_SHORT).show();
            }
        }
    }

}
