package comp3350.intellicards.Presentation;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.widget.AppCompatImageButton;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import comp3350.intellicards.Application.Services;
import comp3350.intellicards.Application.UserSession;
import comp3350.intellicards.Business.FlashcardSetManager;
import comp3350.intellicards.Objects.Flashcard;
import comp3350.intellicards.R;

public class RecoverFlashcardsActivity extends Activity {

    private FlashcardSetManager flashcardSetManager;
    private String userName;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recovery);

        // Initialize flashcard set persistence
        flashcardSetManager = new FlashcardSetManager(Services.getFlashcardSetPersistence());

        // Get the user session
        userName = UserSession.getInstance(this).getUsername();

        // Print the recovered list on the UI
        printRecoverList();
        backButtonListener();
    }

    private void backButtonListener() {
        AppCompatImageButton backButton = findViewById(R.id.backButton);
        backButton.setOnClickListener(v -> {
            Intent intent = new Intent(RecoverFlashcardsActivity.this, ProfileActivity.class);
            startActivity(intent);

        });
    }

    private void printRecoverList() {
        List<Flashcard> deletedFlashcards = flashcardSetManager.getAllDeletedFlashcards(userName);
        RecyclerView recyclerRecoverView = findViewById(R.id.recycleView);
        recyclerRecoverView.setLayoutManager(new LinearLayoutManager(this));
        recyclerRecoverView.setAdapter(new CardRecoverAdapter(deletedFlashcards));
    }

}
