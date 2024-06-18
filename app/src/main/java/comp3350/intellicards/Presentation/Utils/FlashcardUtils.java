package comp3350.intellicards.Presentation.Utils;

import android.widget.TextView;

import comp3350.intellicards.Objects.Flashcard;

public class FlashcardUtils {

    public static void toggleFlip(Flashcard flashcard, TextView flashcardTextRecycle, boolean isFront) {
        if (isFront) {
            flashcardTextRecycle.setText(flashcard.getAnswer());
        } else {
            String flashcardTextQuestion = flashcard.getQuestion();
            if (flashcard.getHint() != null && !flashcard.getHint().isEmpty()) {
                flashcardTextQuestion += " \nHint: " + flashcard.getHint();
            }
            flashcardTextRecycle.setText(flashcardTextQuestion);
        }
    }

    // Returns the question text with a hint if it exists
    public static String getFlashcardQuestionWithHintText(Flashcard flashcard) {
        String questionText = flashcard.getQuestion();
        if (flashcard.getHint() != null && !flashcard.getHint().isEmpty()) {
            questionText += " \nHint: " + flashcard.getHint();
        }
        return questionText;
    }
}
