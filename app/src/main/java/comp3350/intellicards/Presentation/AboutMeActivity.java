package comp3350.intellicards.Presentation;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;



import comp3350.intellicards.Application.UserSession;
import comp3350.intellicards.Business.FlashcardSetManager;
import comp3350.intellicards.R;

public class AboutMeActivity extends Activity {
    private FlashcardSetManager flashcardSetManager;
    private String userName;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_me);

        // Initialize flashcard set persistence
        flashcardSetManager = new FlashcardSetManager();

        // Get the user session
        userName = UserSession.getInstance().getUsername();

        //set up views
        setUpBackButton();


    }

    private void setUpBackButton() {
        Button backButton = findViewById(R.id.backButton);

        backButton.setOnClickListener(v -> {
            Intent intent = new Intent(comp3350.intellicards.Presentation.AboutMeActivity.this, ProfileActivity.class);
            startActivity(intent);
        });
    }

}

