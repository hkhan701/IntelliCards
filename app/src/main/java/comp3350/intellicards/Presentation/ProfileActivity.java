package comp3350.intellicards.Presentation;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.widget.AppCompatImageButton;

import comp3350.intellicards.Application.UserSession;
import comp3350.intellicards.R;

public class ProfileActivity extends Activity {

    private AppCompatImageButton backButton;
    private Button recoveryButton;
    private TextView usernameTextView;
    private Button logoutButton;
    private Button aboutMeButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        initializeViews();
        setupListeners();
        populateUsername(UserSession.getInstance(this).getUsername()); // Get the username from the UserSession singleton class
    }

    private void initializeViews() {
        usernameTextView = findViewById(R.id.usernameText);
        recoveryButton = findViewById(R.id.recoveryButton);
        backButton = findViewById(R.id.backButton);
        logoutButton = findViewById(R.id.logoutButton);
        aboutMeButton = findViewById(R.id.aboutMeButton);
    }

    private void setupListeners() {
        setupBackButtonListener();
        setupRecoveryButtonListener();
        setupLogoutButton();
        setUpAboutMeButtonListener();
    }

    private void setUpAboutMeButtonListener(){
        aboutMeButton.setOnClickListener(v -> navigateToAboutMeActivity());
    }

    private void setupBackButtonListener() {
        backButton.setOnClickListener(v -> navigateToMainActivity());
    }

    private void setupRecoveryButtonListener() {
        recoveryButton.setOnClickListener(v -> navigateToRecoverFlashcardsActivity());
    }

    private void setupLogoutButton() {
        logoutButton.setOnClickListener(v -> navigateToAuthActivity());
    }

    private void navigateToAboutMeActivity() {
        Intent intent = new Intent(ProfileActivity.this, AboutMeActivity.class);
        startActivity(intent);
    }

    private void navigateToMainActivity() {
        Intent intent = new Intent(ProfileActivity.this, MainActivity.class);
        startActivity(intent);
    }

    private void navigateToRecoverFlashcardsActivity() {
        Intent intent = new Intent(ProfileActivity.this, RecoverFlashcardsActivity.class);
        startActivity(intent);
    }

    private void navigateToAuthActivity() {
        UserSession.getInstance(this).logout();
        Intent intent = new Intent(ProfileActivity.this, AuthActivity.class);
        startActivity(intent);
    }

    private void populateUsername(String username) {
        if (username != null) {
            usernameTextView.setText(username);
        }
    }
}
