package comp3350.intellicards.Presentation;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.ViewFlipper;

import androidx.annotation.Nullable;

import comp3350.intellicards.Business.FlashcardManager;
import comp3350.intellicards.Objects.Flashcard;
import comp3350.intellicards.Objects.FlashcardSet;
import comp3350.intellicards.Business.FlashcardSetManager;
import comp3350.intellicards.Business.StubManager;
import comp3350.intellicards.R;

public class FlashcardTestActivity extends Activity {

    private FlashcardSetManager flashcardSetManager = new FlashcardSetManager(StubManager.getFlashcardSetPersistence());
    private FlashcardManager flashcardManager = new FlashcardManager(StubManager.getFlashcardPersistence());


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        // Get the flashcard set UUID from the intent
        String flashcardSetUUID = getIntent().getStringExtra("flashcardSetUUID");
        // Get the flashcard set
        FlashcardSet flashcardSet = flashcardSetManager.getActiveFlashcardSet(flashcardSetUUID);

        setUpBackButton(flashcardSetUUID);
        setUpViewFlipper(flashcardSet);

    }

    private void setUpBackButton(String flashcardSetUUID)
    {
        Button backButton = findViewById(R.id.backButton);
        backButton.setOnClickListener(v -> {
            Intent intent = new Intent(FlashcardTestActivity.this, FlashcardSetActivity.class);
            intent.putExtra("flashcardSetUUID", flashcardSetUUID);
            startActivity(intent);
        });
    }

    private void setUpViewFlipper(FlashcardSet flashcardSet)
    {
        LayoutInflater inflater = LayoutInflater.from(this);
        ViewFlipper viewFlipper = findViewById(R.id.viewFlipper);
        FlashcardSet activeFlashcards = flashcardSet.getActiveFlashcards();
        for(int i = 0; i < activeFlashcards.size(); i++)
        {
            viewFlipper.addView(createView(activeFlashcards.getIndex(i), inflater, viewFlipper));
        }
    }

    private View createView(Flashcard flashcard, LayoutInflater inflater, ViewFlipper viewFlipper)
    {
        View view = inflater.inflate(R.layout.flashcard_test_view, viewFlipper, false);
        // Define click listener for the ViewHolder's View
        TextView flashcardTextRecycle = view.findViewById(R.id.flashcardText);
        CheckBox correctBox = view.findViewById(R.id.correctCheckbox);
        CheckBox incorrectBox = view.findViewById(R.id.incorrectCheckbox);
        Button nextCardButton = view.findViewById(R.id.nextCardButton);

        flashcardTextRecycle.setText(flashcard.toString());

        return view;
    }


}
