package comp3350.intellicards.Presentation;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import comp3350.intellicards.Objects.Flashcard;
import comp3350.intellicards.Persistence.FlashcardPersistence;
import comp3350.intellicards.Persistence.InitializePersistence;
import comp3350.intellicards.R;

public class RecoverFlashcardsActivity extends Activity {
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recovery);

        // Initialize flashcard persistence
        FlashcardPersistence flashcardPersistence = InitializePersistence.getFlashcardPersistence();

        // Retrieve deleted flashcards
        List<Flashcard> deletedFlashcards = flashcardPersistence.getAllDeletedFlashcards();

        // Print the recovered list on the UI
        printRecoverList(deletedFlashcards);

        Button backButton = findViewById(R.id.backButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RecoverFlashcardsActivity.this, ProfileActivity.class);
                startActivity(intent);
            }
        });

    }

    private void printRecoverList(List<Flashcard> flashcards) {
        RecyclerView recyclerRecoverView;
        CardRecoverAdapter recoverAdapter;
        RecyclerView.LayoutManager layoutManager;

        recyclerRecoverView = findViewById(R.id.recycleView);
        layoutManager = new LinearLayoutManager(this);
        recyclerRecoverView.setLayoutManager(layoutManager);

        recoverAdapter = new CardRecoverAdapter(flashcards);
        recyclerRecoverView.setAdapter(recoverAdapter);
    }

}
