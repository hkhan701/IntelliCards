package comp3350.intellicards.Presentation;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import androidx.annotation.Nullable;

import comp3350.intellicards.Business.FlashcardManager;
import comp3350.intellicards.Business.ReportCalculator;
import comp3350.intellicards.Objects.Flashcard;
import comp3350.intellicards.Objects.FlashcardSet;
import comp3350.intellicards.Business.FlashcardSetManager;
import comp3350.intellicards.Business.StubManager;
import comp3350.intellicards.Presentation.Utils.FlashcardUtils;
import comp3350.intellicards.R;

public class FlashcardTestActivity extends Activity {

    private FlashcardSetManager flashcardSetManager = new FlashcardSetManager(StubManager.getFlashcardSetPersistence());
    private FlashcardManager flashcardManager = new FlashcardManager(StubManager.getFlashcardPersistence());
    private int correct = 0;

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
            viewFlipper.addView(createView(flashcardSet, activeFlashcards.getIndex(i), inflater, viewFlipper));
        }
    }

    private View createView(FlashcardSet flashcardSet, Flashcard flashcard, LayoutInflater inflater, ViewFlipper viewFlipper)
    {
        View view = inflater.inflate(R.layout.flashcard_test_view, viewFlipper, false);
        TestCardView testCardView = new TestCardView(view, viewFlipper, flashcard, flashcardSet);
        return testCardView.getTestCardView();
    }

    private class TestCardView {
        //the view that we are creating
        private View view;
        //the flipper that we are adding too
        private ViewFlipper viewFlipper;
        //the flashcard information
        private Flashcard flashcard;
        private FlashcardSet flashcardSet;

        //the associated views
        private TextView flashcardTextView;
        private CheckBox correctBox;
        private CheckBox incorrectBox;
        private Button nextCardButton;
        private Button flipButton;
        //check if the card should be flipped up
        private boolean isFrontVisible;

        public TestCardView(View view, ViewFlipper viewFlipper, Flashcard flashcard, FlashcardSet flashcardSet)
        {
            this.view = view;
            this.viewFlipper = viewFlipper;
            this.flashcard = flashcard;
            this.flashcardSet = flashcardSet;
            this.flashcardTextView = view.findViewById(R.id.flashcardText);
            this.correctBox = view.findViewById(R.id.correctCheckbox);
            this.incorrectBox = view.findViewById(R.id.incorrectCheckbox);
            this.nextCardButton = view.findViewById(R.id.nextCardButton);
            this.flipButton = view.findViewById(R.id.flipButton);
            this.isFrontVisible = true;
        }

        private View getTestCardView()
        {
            setUp();
            return view;
        }


        private void setUp()
        {
            setUpText();
            setUpListeners();
        }

        private void setUpText()
        {
            flashcardTextView.setText(flashcard.getDataFormatted());
        }

        private void setUpListeners()
        {
            setUpCheckBoxes();
            setUpNextCardButton();
            setUpFlipButton();
        }

        private void setUpFlipButton()
        {
            flipButton.setOnClickListener(v -> {
                flipFlashcard(flashcard);
            });
        }


        private void setUpCheckBoxes()
        {
            // so they can't be both checked at once
            correctBox.setOnClickListener(v -> {
                if (correctBox.isChecked()) {
                    incorrectBox.setChecked(false);
                }
            });

            incorrectBox.setOnClickListener(v -> {
                if (incorrectBox.isChecked()) {
                    correctBox.setChecked(false);
                }
            });
        }

        private void setUpNextCardButton()
        {
            nextCardButton.setOnClickListener(v -> {

                int currentCardIndex = viewFlipper.getDisplayedChild();
                int totalCardCount = viewFlipper.getChildCount();

                if (!correctBox.isChecked() && !incorrectBox.isChecked())
                {
                    Toast.makeText(FlashcardTestActivity.this, "Please check the correct or incorrect checkbox", Toast.LENGTH_SHORT).show();
                } else
                {
                    if (correctBox.isChecked())
                    {
                        flashcardManager.markAttemptedAndCorrect(flashcard);
                        correct++;
                    } else
                    {
                        flashcardManager.markAttempted(flashcard);
                    }

                    if (currentCardIndex == totalCardCount - 1)
                    {
                        // print out the stats of the current flashcard set test
                        // and overall stats
                        viewFlipper.setVisibility(View.INVISIBLE);
                        ReportCalculator reportCalculator = new ReportCalculator(flashcardSet);
                        String totalReport = calculateStats(viewFlipper) + reportCalculator.report();
                        setUpResultTextBox(totalReport);
                    } else
                    {
                        viewFlipper.showNext();
                    }
                }
            });
        }

        private void flipFlashcard(Flashcard flashcard) {
            if (flashcard != null) {
                FlashcardUtils.toggleFlip(flashcard, flashcardTextView, isFrontVisible);
                isFrontVisible = !isFrontVisible;
            }
        }

        private String calculateStats(ViewFlipper viewFlipper){
            int total = viewFlipper.getChildCount();
            return "This tests accuracy, Correct: " + correct + " / " + total
                    + "\nThat is " + Math.round(correct * 100 / (double)total) + "% correct\n\n";
        }

        private void setUpResultTextBox(String string)
        {
            TextView resultTextBox = findViewById(R.id.resultTextBox);
            resultTextBox.setText(string);
        }
    }




}
