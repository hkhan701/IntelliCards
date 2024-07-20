package comp3350.intellicards.Business;

import java.util.List;

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
        collectStats();
    }

    public static String getUserInformation(List<FlashcardSet> flashcardSetList)
    {
        int totalSets = flashcardSetList.size();
        int[] counts = getAllTimeFlashcardCounts(flashcardSetList);

        return          "Total Flashcard Sets: "
                + totalSets + "\nFlashcard count: "
                + counts[0] + "\nActive Flashcard count: "
                + counts[1]
                + "\nDeleted Flashcard count: "
                + counts[2] + "\n\n\n"
                + getAllTimeAccuracy(flashcardSetList);
    }

    //where index 0 is the all time count
    //where index 1 is the active count
    //where index 2 is the deleted count
    public static int[] getAllTimeFlashcardCounts(List<FlashcardSet> flashcardSetList)
    {
        int totalSets = flashcardSetList.size();
        int[] counts = {0, 0, 0};
        for(int i = 0; i < totalSets; i++)
        {
            counts[0] += flashcardSetList.get(i).size();
            counts[1] += flashcardSetList.get(i).getActiveFlashcards().size();
            counts[2] += flashcardSetList.get(i).getDeletedFlashcards().size();
        }
        return counts;
    }

    public static String getAllTimeAccuracy(List<FlashcardSet> flashcardSetList)
    {
        int allTimeCorrect = 0;
        int allTimeAttempted = 0;
        for(int i = 0; i < flashcardSetList.size(); i++)
        {
            FlashcardSet flashcardSet = flashcardSetList.get(i);
            ReportCalculator reportCalculator = new ReportCalculator(flashcardSet);
            allTimeCorrect += reportCalculator.getTotalCorrect();
            allTimeAttempted += reportCalculator.getTotalAttempted();
        }
        return reportSetAccuracy(allTimeCorrect, allTimeAttempted);

    }


    private static String reportSetAccuracy(int correct, int attempted){
        return "ALL TIME TOTAL ACCURACY\nCorrect: " + correct + " / " + attempted
                + "\nThat is " + Math.round(correct * 100 / (double) attempted) + "% correct: ";
    }

    public String reportSetAccuracy() {
        return reportSetAccuracy(totalCorrect, totalAttempted);
    }

    public int getTotalAttempted() {
        return totalAttempted;
    }

    public int getTotalCorrect() {
        return totalCorrect;
    }


    private void collectStats() {
        for (int i = 0; i < flashcardSet.size(); i++) {
            Flashcard flashcard = flashcardSet.getIndex(i);
            if (!flashcard.isDeleted()) {
                totalAttempted += flashcard.getAttempted();
                totalCorrect += flashcard.getCorrect();
            }

        }
    }

}
