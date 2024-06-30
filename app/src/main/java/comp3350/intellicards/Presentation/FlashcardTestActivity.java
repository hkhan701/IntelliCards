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
import comp3350.intellicards.Presentation.Utils.FlashcardUtils;
import comp3350.intellicards.R;

public class FlashcardTestActivity extends Activity {

    private FlashcardSetManager flashcardSetManager = new FlashcardSetManager();
    private FlashcardManager flashcardManager = new FlashcardManager();
    private int correct = 0;
    private int attempted = 0;
    ViewFlipper viewFlipper;
    Button backButton;
    TextView resultTextBox;
    Button finishButton;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        // Get the flashcard set UUID from the intent
        String flashcardSetUUID = getIntent().getStringExtra("flashcardSetUUID");
        // Get the flashcard set
        FlashcardSet flashcardSet = flashcardSetManager.getActiveFlashcardSet(flashcardSetUUID);
        // Shuffle the cards
        flashcardSetManager.shuffleFlashcardSet(flashcardSet);
        // get viewFlipper

        backButton = findViewById(R.id.backButton);
        finishButton = findViewById(R.id.finishButton);
        viewFlipper = findViewById(R.id.viewFlipper);
        resultTextBox = findViewById(R.id.resultTextBox);
        setUpBackButton(flashcardSetUUID);
        setUpFinishButton(flashcardSet);
        setUpViewFlipper(flashcardSet);

    }

    private void setUpBackButton(String flashcardSetUUID)
    {
        backButton.setVisibility(View.INVISIBLE);
        backButton.setOnClickListener(v -> {
            Intent intent = new Intent(FlashcardTestActivity.this, FlashcardSetActivity.class);
            intent.putExtra("flashcardSetUUID", flashcardSetUUID);
            startActivity(intent);
        });
    }

    private void setUpFinishButton(FlashcardSet flashcardSet)
    {
        finishButton.setOnClickListener(v -> {
            finishTest(flashcardSet);
        });
    }


    private void setUpViewFlipper(FlashcardSet flashcardSet)
    {
        LayoutInflater inflater = LayoutInflater.from(this);
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

    private void finishTest(FlashcardSet flashcardSet)
    {
        backButton.setVisibility(View.VISIBLE);
        finishButton.setVisibility(View.INVISIBLE);
        viewFlipper.setVisibility(View.INVISIBLE);
        // print out the stats of the current flashcard set test
        // and overall stats

        // re-get the flashcard set from the driver so we have the update attempts and correct stats
        FlashcardSet currSet = flashcardSetManager.getActiveFlashcardSet(flashcardSet.getUUID());
        ReportCalculator reportCalculator = new ReportCalculator(currSet);
        String totalReport = calculateStats() + reportCalculator.report();
        setUpResultTextBox(totalReport);
    }

    private String calculateStats(){
        return "This tests accuracy, Correct: " + correct + " / " + attempted
                + "\nThat is " + Math.round(correct * 100 / (double)attempted) + "% correct\n\n";
    }

    private void setUpResultTextBox(String string)
    {
        resultTextBox.setText(string);
    }

    // private class to handle the card views in test mode
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
            flashcardTextView.setText(FlashcardUtils.getFlashcardQuestionWithHintText(flashcard));
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
                        flashcardManager.markAttemptedAndCorrect(flashcard.getUUID());
                        correct++;
                        attempted++;
                    } else
                    {
                        flashcardManager.markAttempted(flashcard.getUUID());
                        attempted++;
                    }

                    if (currentCardIndex == totalCardCount - 1)
                    {
                        finishTest(flashcardSet);
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

    }

}
