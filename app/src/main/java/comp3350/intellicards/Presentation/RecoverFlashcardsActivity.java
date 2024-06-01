package comp3350.intellicards.Presentation;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import comp3350.intellicards.Objects.FlashcardSet;
import comp3350.intellicards.Persistence.FlashcardSetPersistenceStub;
import comp3350.intellicards.R;

public class RecoverFlashcardsActivity extends Activity {
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recovery);


        //get the stub data
        FlashcardSet flashcardSet = FlashcardSetPersistenceStub.getFlashcardSet();
        //print the deleted cards on the UI
        printRecoverList(flashcardSet.getDeletedFlashCards());


        Button backButton = findViewById(R.id.backButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RecoverFlashcardsActivity.this, ProfileActivity.class);
                startActivity(intent);
            }
        });
    }

    public void printRecoverList(FlashcardSet flashcardSet)
    {
        RecyclerView recyclerRecoverView;
        CardRecoverAdapter recoverAdapter;
        RecyclerView.LayoutManager layoutManager;

        recyclerRecoverView = findViewById(R.id.recycleView);
        layoutManager = new LinearLayoutManager(this);
        recyclerRecoverView.setLayoutManager(layoutManager);

        recoverAdapter = new CardRecoverAdapter(flashcardSet);
        recyclerRecoverView.setAdapter(recoverAdapter);
    }
}
