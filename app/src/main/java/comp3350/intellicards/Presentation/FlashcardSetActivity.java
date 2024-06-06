package comp3350.intellicards.Presentation;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import comp3350.intellicards.Objects.Flashcard;
import comp3350.intellicards.Objects.FlashcardSet;
import comp3350.intellicards.Persistence.FlashcardSetPersistence;
import comp3350.intellicards.Persistence.InitializePersistence;
import comp3350.intellicards.R;

public class FlashcardSetActivity extends Activity {

    private FlashcardSetPersistence flashcardSetPersistence;
    private RecyclerView flashcardsRecyclerView;
    private TextView flashcardSetTitle;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flashcard_set);

        flashcardSetPersistence = InitializePersistence.getFlashcardSetPersistence();

        flashcardSetTitle = findViewById(R.id.flashcardSetTitle);
        flashcardsRecyclerView = findViewById(R.id.flashcardsRecyclerView);
        flashcardsRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Get the flashcard set UUID from the intent
        String flashcardSetId = getIntent().getStringExtra("flashcardSetId");

        if (flashcardSetId != null) {
            FlashcardSet flashcardSet = flashcardSetPersistence.getActiveFlashcardSet(flashcardSetId);

            if (flashcardSet != null) {
                flashcardSetTitle.setText(flashcardSet.getFlashcardSetName());
                // Set up the RecyclerView with flashcards
                flashcardsRecyclerView.setAdapter(new CardViewAdapter(flashcardSet));
            }
        }


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1 && resultCode == RESULT_OK && data != null) {
            // Retrieve the updated flashcard ID from the result
            String updatedFlashcardID = data.getStringExtra("flashcardID");

            // Update the TextView in your ViewHolder based on the updated flashcard
            // Get the flashcard set UUID from the intent
            String flashcardSetId = getIntent().getStringExtra("flashcardSetId");
            FlashcardSet flashcardSet = flashcardSetPersistence.getActiveFlashcardSet(flashcardSetId);
            Flashcard updatedFlashcard = flashcardSet.getFlashCardById(updatedFlashcardID);
            if (updatedFlashcard != null) {
                flashcardsRecyclerView.setAdapter(new CardViewAdapter(flashcardSet));
            }
        }
    }
}
