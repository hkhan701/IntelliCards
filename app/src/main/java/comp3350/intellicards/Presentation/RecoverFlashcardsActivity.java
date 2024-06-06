package comp3350.intellicards.Presentation;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import comp3350.intellicards.Objects.Flashcard;
import comp3350.intellicards.Business.FlashcardManager;
import comp3350.intellicards.Business.StubManager;
import comp3350.intellicards.R;

public class RecoverFlashcardsActivity extends Activity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recovery);

        // Initialize flashcard persistence
        FlashcardManager flashcardManager = new FlashcardManager(StubManager.getFlashcardPersistence());

        // Retrieve deleted flashcards
        List<Flashcard> deletedFlashcards = flashcardManager.getAllDeletedFlashcards();

        // Print the recovered list on the UI
        printRecoverList(deletedFlashcards);

        Button backButton = findViewById(R.id.backButton);

        backButton.setOnClickListener(v -> {
            Intent intent = new Intent(RecoverFlashcardsActivity.this, ProfileActivity.class);
            startActivity(intent);
        });

    }

    private void printRecoverList(List<Flashcard> flashcards) {
        RecyclerView recyclerRecoverView = findViewById(R.id.recycleView);
        recyclerRecoverView.setLayoutManager(new LinearLayoutManager(this));
        recyclerRecoverView.setAdapter(new CardRecoverAdapter(flashcards));
    }

}
