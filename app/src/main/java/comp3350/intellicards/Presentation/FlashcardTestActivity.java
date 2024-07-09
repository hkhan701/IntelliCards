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
import androidx.appcompat.widget.AppCompatImageButton;

import comp3350.intellicards.Application.Services;
import comp3350.intellicards.Business.FlashcardManager;
import comp3350.intellicards.Business.ReportCalculator;
import comp3350.intellicards.Objects.Flashcard;
import comp3350.intellicards.Objects.FlashcardSet;
import comp3350.intellicards.Business.FlashcardSetManager;
import comp3350.intellicards.Presentation.Utils.FlashcardUtils;
import comp3350.intellicards.R;

public class FlashcardTestActivity extends Activity {

    private FlashcardSetManager flashcardSetManager;
    private FlashcardManager flashcardManager;
    private int correctAnswers = 0;
    private int attemptedAnswers = 0;
    private ViewFlipper viewFlipper;
    private AppCompatImageButton backButton;
    private Button finishButton;
    private TextView resultTextView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        flashcardSetManager = new FlashcardSetManager(Services.getFlashcardSetPersistence());
        flashcardManager = new FlashcardManager(Services.getFlashcardPersistence());

        String flashcardSetUUID = getIntent().getStringExtra("flashcardSetUUID");
        FlashcardSet flashcardSet = flashcardSetManager.getActiveFlashcardSet(flashcardSetUUID);
        flashcardSetManager.shuffleFlashcardSet(flashcardSet);

        backButton = findViewById(R.id.backButton);
        finishButton = findViewById(R.id.finishButton);
        viewFlipper = findViewById(R.id.viewFlipper);
        resultTextView = findViewById(R.id.resultTextBox);

        setUpBackButton(flashcardSetUUID);
        setUpFinishButton(flashcardSet);
        setUpViewFlipper(flashcardSet);
    }

    private void setUpBackButton(String flashcardSetUUID) {
        backButton.setVisibility(View.INVISIBLE);
        backButton.setOnClickListener(v -> navigateToFlashcardSetActivity(flashcardSetUUID));
    }

    private void navigateToFlashcardSetActivity(String flashcardSetUUID) {
        Intent intent = new Intent(FlashcardTestActivity.this, FlashcardSetActivity.class);
        intent.putExtra("flashcardSetUUID", flashcardSetUUID);
        startActivity(intent);
    }

    private void setUpFinishButton(FlashcardSet flashcardSet) {
        finishButton.setOnClickListener(v -> finishTest(flashcardSet));
    }

    private void setUpViewFlipper(FlashcardSet flashcardSet) {
        LayoutInflater inflater = LayoutInflater.from(this);
        FlashcardSet activeFlashcards = flashcardSet.getActiveFlashcards();
        for (int i = 0; i < activeFlashcards.size(); i++) {
            viewFlipper.addView(createFlashcardTestView(flashcardSet, activeFlashcards.getIndex(i), inflater));
        }
    }

    private View createFlashcardTestView(FlashcardSet flashcardSet, Flashcard flashcard, LayoutInflater inflater) {
        View view = inflater.inflate(R.layout.flashcard_test_view, viewFlipper, false);
        new FlashcardTestView(view, flashcardSet, flashcard);
        return view;
    }

    private void finishTest(FlashcardSet flashcardSet) {
        backButton.setVisibility(View.VISIBLE);
        finishButton.setVisibility(View.INVISIBLE);
        viewFlipper.setVisibility(View.INVISIBLE);

        FlashcardSet updatedFlashcardSet = flashcardSetManager.getActiveFlashcardSet(flashcardSet.getUUID());
        ReportCalculator reportCalculator = new ReportCalculator(updatedFlashcardSet);
        String report = generateTestStats() + reportCalculator.report();
        displayTestResults(report);
    }

    private String generateTestStats() {
        return "This tests accuracy, Correct: " + correctAnswers + " / " + attemptedAnswers
                + "\nThat is " + Math.round(correctAnswers * 100 / (double) attemptedAnswers) + "% correct\n\n";
    }

    private void displayTestResults(String report) {
        resultTextView.setText(report);
    }

    // private class to handle the card views in test mode
    private class FlashcardTestView {
        private final Flashcard flashcard;
        private final FlashcardSet flashcardSet;
        private final TextView flashcardTextView;
        private final CheckBox correctCheckBox;
        private final CheckBox incorrectCheckBox;
        private final AppCompatImageButton nextCardButton;
        private final Button flipCardButton;
        private boolean isFrontVisible;

        public FlashcardTestView(View view, FlashcardSet flashcardSet, Flashcard flashcard) {
            this.flashcardSet = flashcardSet;
            this.flashcard = flashcard;
            this.flashcardTextView = view.findViewById(R.id.flashcardText);
            this.correctCheckBox = view.findViewById(R.id.correctCheckbox);
            this.incorrectCheckBox = view.findViewById(R.id.incorrectCheckbox);
            this.nextCardButton = view.findViewById(R.id.nextCardButton);
            this.flipCardButton = view.findViewById(R.id.flipButton);
            this.isFrontVisible = true;
            setUpView();
        }

        private void setUpView() {
            setFlashcardText();
            setUpListeners();
        }

        private void setFlashcardText() {
            flashcardTextView.setText(FlashcardUtils.getFlashcardQuestionWithHintText(flashcard));
        }

        private void setUpListeners() {
            setUpCheckBoxListeners();
            setUpNextCardButtonListener();
            setUpFlipCardButtonListener();
        }

        private void setUpCheckBoxListeners() {
            correctCheckBox.setOnClickListener(v -> uncheckOtherCheckBox(incorrectCheckBox, correctCheckBox));
            incorrectCheckBox.setOnClickListener(v -> uncheckOtherCheckBox(correctCheckBox, incorrectCheckBox));
        }

        private void uncheckOtherCheckBox(CheckBox otherCheckBox, CheckBox checkBox) {
            if (checkBox.isChecked()) {
                otherCheckBox.setChecked(false);
            }
        }

        private void setUpNextCardButtonListener() {
            nextCardButton.setOnClickListener(v -> handleNextCardButtonClick());
        }

        private void handleNextCardButtonClick() {
            if (!correctCheckBox.isChecked() && !incorrectCheckBox.isChecked()) {
                Toast.makeText(FlashcardTestActivity.this, "Please check the correct or incorrect checkbox", Toast.LENGTH_SHORT).show();
                return;
            }

            if (correctCheckBox.isChecked()) {
                flashcardManager.markAttemptedAndCorrect(flashcard.getUUID());
                correctAnswers++;
            } else {
                flashcardManager.markAttempted(flashcard.getUUID());
            }
            attemptedAnswers++;

            if (isLastCard()) {
                finishTest(flashcardSet);
            } else {
                viewFlipper.showNext();
            }
        }

        private boolean isLastCard() {
            return viewFlipper.getDisplayedChild() == viewFlipper.getChildCount() - 1;
        }

        private void setUpFlipCardButtonListener() {
            flipCardButton.setOnClickListener(v -> flipFlashcard());
        }

        private void flipFlashcard() {
            FlashcardUtils.toggleFlip(flashcard, flashcardTextView, isFrontVisible);
            isFrontVisible = !isFrontVisible;
        }
    }

}
