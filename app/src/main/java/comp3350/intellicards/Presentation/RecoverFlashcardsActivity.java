package comp3350.intellicards.Presentation;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.widget.AppCompatImageButton;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import comp3350.intellicards.Application.Services;
import comp3350.intellicards.Application.UserSession;
import comp3350.intellicards.Business.FlashcardSetManager;
import comp3350.intellicards.Objects.Flashcard;
import comp3350.intellicards.Objects.FlashcardSet;
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
        userName = UserSession.getInstance().getUsername();

        List<Flashcard> deletedFlashcards = new ArrayList<>();

        //change this when refactoring so that we only use the UUID's and the logic layer
        // to get the corresponding info
        // for deleteFlashcards, this should be changed into an arraylist of string UUIDS
        // also gonna have to change the cardAdapters so that it accepts a list of string uuids
        // and change it internally so it accesses the flashcards using the logic layer and the
        // UUID's instead of the reference to the flashcards directly using the flashcardSet
        // Retrieve deleted flashcards for the user
        List<FlashcardSet> userFlashcardSets = flashcardSetManager.getFlashcardSetsByUsername(userName);

        for (FlashcardSet flashcardSet : userFlashcardSets) {
            FlashcardSet deletedSet = flashcardSetManager.getDeletedFlashcardSet(flashcardSet.getUUID());
            for(int i = 0; i < deletedSet.size(); i++)
            {
                Flashcard flashcard = deletedSet.getIndex(i);
                deletedFlashcards.add(flashcard);
            }
        }

        // Print the recovered list on the UI
        printRecoverList(deletedFlashcards);

        AppCompatImageButton backButton = findViewById(R.id.backButton);

        backButton.setOnClickListener(v -> {
            onBackPressed();
        });

    }

    private void printRecoverList(List<Flashcard> flashcards) {
        RecyclerView recyclerRecoverView = findViewById(R.id.recycleView);
        recyclerRecoverView.setLayoutManager(new LinearLayoutManager(this));
        recyclerRecoverView.setAdapter(new CardRecoverAdapter(flashcards));
    }

}
