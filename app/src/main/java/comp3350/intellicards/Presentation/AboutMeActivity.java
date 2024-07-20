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
import comp3350.intellicards.Business.UserManager;
import comp3350.intellicards.Objects.FlashcardSet;
import comp3350.intellicards.Objects.User;
import comp3350.intellicards.R;

public class AboutMeActivity extends Activity {
    private FlashcardSetManager flashcardSetManager;
    private UserManager userManager;

    private String userName;

    private Button backButton;

    private TextView informationText;

    private TextView usernameText;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_me);

        // Initialize flashcard set persistence
        flashcardSetManager = new FlashcardSetManager();

        // Initialize user persisitence
        userManager = new UserManager();

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
        usernameText = findViewById(R.id.usernameText);
    }

    private void setUpListeners(){
        setUpBackButtonListener();
    }

    private void setUpViews(){
        setUpInformationTextView();
        setUpUsernameTextView();
    }

    private void setUpBackButtonListener() {
        backButton.setOnClickListener(v -> {
            Intent intent = new Intent(comp3350.intellicards.Presentation.AboutMeActivity.this, ProfileActivity.class);
            startActivity(intent);
        });
    }

    private void setUpInformationTextView() {
        String userInformationString = getInformationText();
        informationText.setText(userInformationString);
    }

    private String getInformationText()
    {
        List<FlashcardSet> flashcardSetList = flashcardSetManager.getFlashcardSetsByUsername(userName);
        String userSetInformationString = ReportCalculator.getUserInformation(flashcardSetList);
        // now get the user login count
        User user = userManager.getUserByUsername(userName);
        int count = user.getLoginCount();

        return userSetInformationString
                +"\n\nLogin Count: " + count;
    }

    private void setUpUsernameTextView(){
        usernameText.setText(userName);
    }


}

