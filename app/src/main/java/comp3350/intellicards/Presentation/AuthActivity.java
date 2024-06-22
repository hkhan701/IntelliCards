package comp3350.intellicards.Presentation;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;

import comp3350.intellicards.Objects.User;
import comp3350.intellicards.Business.UserManager;
import comp3350.intellicards.R;

public class AuthActivity extends Activity {
    private UserManager userManager;
    private Button logInButton;
    private Button registerButton;
    private Button guestButton;
    private EditText usernameEditText;
    private EditText passwordEditText;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);

        userManager = new UserManager();
        initializeViews();
        setUpListeners();
    }

    private void initializeViews() {
        logInButton = findViewById(R.id.logInButton);
        registerButton = findViewById(R.id.registerButton);
        guestButton = findViewById(R.id.guestButton);
        usernameEditText = findViewById(R.id.username);
        passwordEditText = findViewById(R.id.password);
    }

    private void setUpListeners() {
        setUpLogInButtonListener();
        setUpRegisterButtonListener();
        setUpGuestButtonListener();
    }

    private void setUpRegisterButtonListener() {
        registerButton.setOnClickListener(v -> {
            if (verifyInput()) {
                String username = usernameEditText.getText().toString();
                String password = passwordEditText.getText().toString();
                if (userManager.registerUser(username, password)) {
                    Toast.makeText(this, "Sign up successful! Please log in.", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(this, "Username already exists! Please try again.", Toast.LENGTH_LONG).show();
                }
            } else {
                Toast.makeText(this, "Invalid input! Please try again.", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void setUpLogInButtonListener() {
        logInButton.setOnClickListener(v -> {
            if (verifyInput()) {
                String username = usernameEditText.getText().toString();
                String password = passwordEditText.getText().toString();
                User user = userManager.loginUser(username, password);
                if (user != null) {
                    Toast.makeText(this, "Log in successful!", Toast.LENGTH_LONG).show();
                    navigateToMainActivity(user.getUsername());
                } else {
                    Toast.makeText(this, "Invalid login information! Please try again.", Toast.LENGTH_LONG).show();
                }
            } else {
                Toast.makeText(this, "Invalid input! Please try again.", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void setUpGuestButtonListener() {
        guestButton.setOnClickListener(v -> navigateToMainActivity(null));
    }

    private boolean verifyInput() {
        String username = usernameEditText.getText().toString();
        String password = passwordEditText.getText().toString();
        return !username.isEmpty() && !password.isEmpty();
    }

    private void navigateToMainActivity(String username) {
        Intent intent = new Intent(AuthActivity.this, MainActivity.class);
        // Adding the username to the intent will allow us to determine which features they can use
        intent.putExtra("username", username);
        startActivity(intent);
        finish();
    }
}
