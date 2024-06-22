package comp3350.intellicards.Presentation;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import comp3350.intellicards.R;

public class ProfileActivity extends Activity {

    private String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        username = getIntent().getStringExtra("username"); // Get the username from the intent
        setupBackButton();
        setupRecoveryButton();
        populateUsername();
    }

    private void setupBackButton() {
        Button backButton = findViewById(R.id.backButton);
        backButton.setOnClickListener(v -> {
            Intent intent = new Intent(ProfileActivity.this, MainActivity.class);
            intent.putExtra("username", username);
            startActivity(intent);
        });
    }

    private void setupRecoveryButton() {
        Button recoveryButton = findViewById(R.id.recoveryButton);
        recoveryButton.setOnClickListener(v -> {
            Intent intent = new Intent(ProfileActivity.this, RecoverFlashcardsActivity.class);
            startActivity(intent);
        });
    }

    private void populateUsername() {
        TextView usernameTextView = findViewById(R.id.usernameText);
        if (username != null) {
            usernameTextView.setText(username);
        }
    }
}
