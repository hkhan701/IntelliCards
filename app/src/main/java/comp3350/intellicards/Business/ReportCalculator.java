package comp3350.intellicards.Business;

import comp3350.intellicards.Objects.Flashcard;
import comp3350.intellicards.Objects.FlashcardSet;

public class ReportCalculator {

    private int totalAttempted;
    private int totalCorrect;

    private FlashcardSet flashcardSet;


    public ReportCalculator(FlashcardSet flashcardSet) {
        this.flashcardSet = flashcardSet;
        totalAttempted = 0;
        totalCorrect = 0;
    }

    public String report()
    {
        collectStats();
        return "ALL TIME TOTAL ACCURACY\nCorrect: " + totalCorrect + " / " + totalAttempted
                + "\nThat is " + Math.round(totalCorrect * 100 / (double)totalAttempted) + "% correct: ";
    }

    private void collectStats()
    {
        for(int i = 0; i < flashcardSet.size(); i++)
        {
            Flashcard flashcard = flashcardSet.getIndex(i);
            if(!flashcard.isDeleted())
            {
                totalAttempted += flashcard.getAttempted();
                totalCorrect += flashcard.getCorrect();
            }

        }
    }

}
