package comp3350.intellicards.Presentation;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import comp3350.intellicards.R;

public class ProfileActivity extends Activity {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);

        Button backButton = findViewById(R.id.backButton);
        Button recoveryButton = findViewById(R.id.recoveryButton);
        backButton.setOnClickListener(v -> {
            Intent intent = new Intent(ProfileActivity.this, MainActivity.class);
            startActivity(intent);
        });

        recoveryButton.setOnClickListener(v -> {
            Intent intent = new Intent(ProfileActivity.this, RecoverFlashcardsActivity.class);
            startActivity(intent);
        });
    }

}
