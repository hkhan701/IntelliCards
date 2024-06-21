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

public class LoginActivity extends Activity {
    private UserManager userManager;
    private Button logInButton;
    private Button signUpButton;
    private Button tempButton;
    private EditText username;
    private EditText password;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        userManager = new UserManager();
        initializeViews();
        setUpListeners();
    }

    private void initializeViews() {
        logInButton = findViewById(R.id.logInButton);
        signUpButton = findViewById(R.id.signUpButton);
        tempButton = findViewById(R.id.tempButton);
        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
    }

    private void setUpListeners() {
        setUpLogInButtonListener();
        setUpSignUpButtonListener();
        setUpTempButtonListener();
    }

    private void setUpSignUpButtonListener() {
        signUpButton.setOnClickListener(v -> {
            if(verifySignUp() && verifyInput()) {
                new User(username.getText().toString(), password.getText().toString());

                //add the new user in the database
                userManager.addUser(new User(username.getText().toString(), password.getText().toString()));

                //users will have to log in again after signing up
                //sign up successful, ask the user to log in
                Toast.makeText(this, "Sign up successful! Please log in.", Toast.LENGTH_LONG).show();
            }
            else {
                //sign up is not valid, ask the user to try again
                Toast.makeText(this, "Inputted login information already exists or invalid! Please try again.", Toast.LENGTH_LONG).show();
            }
        });

    }

    private void setUpLogInButtonListener() {
        logInButton.setOnClickListener(v -> {
            if(verifyLogIn() && verifyInput()) {
                Toast.makeText(this, "Log in successful!", Toast.LENGTH_LONG).show();

                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(intent);
            }
            else {
                //login is not valid, ask the user to try again
                Toast.makeText(this, "Invalid login information! Please try again.", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void setUpTempButtonListener() {
            tempButton.setOnClickListener(v -> {
            //create a temporary user just for this session
            new User();

            //temporarily store the user in the database

            Toast.makeText(this, "logging in", Toast.LENGTH_LONG).show();

            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(intent);
        });
    }

    private boolean verifyLogIn() {
        boolean valid = false;

        //the login is NOT valid if the username and password does not exist in the database
        for(User user : userManager.getAllUsers()) {
            if(user.getUsername().equals(username.getText().toString())
                    && user.getPassword().equals(password.getText().toString())) {
                valid = true;
                break;
            }
        }

        return valid;
    }

    private boolean verifySignUp() {
        boolean valid = true;

        //the login info is NOT valid if the username and password already exists in the database
        for(User user : userManager.getAllUsers()) {
            if(user.getUsername().equals(username.getText().toString())
                    && user.getPassword().equals(password.getText().toString())) {
                valid = false;
                break;
            }
        }

        return valid;
    }

    private boolean verifyInput() {
        boolean valid = true;

        //password and username are valid if they are not empty or null
        if(username.getText().toString().isEmpty() || username.getText() == null
                || password.getText().toString().isEmpty() || password.getText() == null) {
            valid = false;
        }

        return valid;
    }

}
