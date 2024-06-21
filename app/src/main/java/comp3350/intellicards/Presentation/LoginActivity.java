package comp3350.intellicards.Presentation;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;

import comp3350.intellicards.Objects.User;
import comp3350.intellicards.R;

public class LoginActivity extends Activity {

    private Button signUpButton;
    private EditText username;
    private EditText password;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initializeViews();
        setUpListeners();
    }

    private void initializeViews() {
        signUpButton = findViewById(R.id.signUpButton);
        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
    }

    private void setUpListeners() {
        setUpSignUpButtonListener();
    }


    private void setUpSignUpButtonListener() {
        signUpButton.setOnClickListener(v -> {
            if(verifySignUp() && verifyInput()) {
                new User(username.getText().toString(), password.getText().toString());

                //store the new user in the database

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

    private boolean verifySignUp() {
        boolean valid = true;

        //the login info is NOT valid if the username and password already exists in the database

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
