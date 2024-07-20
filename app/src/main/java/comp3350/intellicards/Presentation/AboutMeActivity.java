package comp3350.intellicards.Presentation;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;


import java.util.List;

import comp3350.intellicards.Application.UserSession;
import comp3350.intellicards.Business.FlashcardSetManager;
import comp3350.intellicards.Business.ReportCalculator;
import comp3350.intellicards.Objects.FlashcardSet;
import comp3350.intellicards.R;

public class AboutMeActivity extends Activity {
    private FlashcardSetManager flashcardSetManager;
    private String userName;

    private Button backButton;

    private TextView informationText;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_me);

        // Initialize flashcard set persistence
        flashcardSetManager = new FlashcardSetManager();

        // Get the user session
        userName = UserSession.getInstance().getUsername();

        //set up views
        initializeViews();
        setUpListeners();
        setUpViews();

    }

    private void initializeViews() {
        backButton = findViewById(R.id.backButton);
        informationText = findViewById(R.id.informationText);
    }

    private void setUpListeners(){
        setUpBackButtonListener();
        setUpInformationTextListener();
    }

    private void setUpViews(){
        setUpInformationTextListener();
    }

    private void setUpBackButtonListener() {
        backButton.setOnClickListener(v -> {
            Intent intent = new Intent(comp3350.intellicards.Presentation.AboutMeActivity.this, ProfileActivity.class);
            startActivity(intent);
        });
    }

    private void setUpInformationTextListener() {
        List<FlashcardSet> flashcardSetList = flashcardSetManager.getFlashcardSetsByUsername(userName);
        String userInformationString = ReportCalculator.getUserInformation(flashcardSetList);
        informationText.setText(userInformationString);
    }


}

