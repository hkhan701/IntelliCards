package comp3350.intellicards.Business;

public class TempTestResult {

    private int correctAnswers;
    private int attemptedAnswers;
    public TempTestResult() {
        correctAnswers = 0;
        attemptedAnswers = 0;
    }

    public String generateTestStats() {
        return "This tests accuracy, Correct: " + correctAnswers + " / " + attemptedAnswers
                + "\nThat is " + Math.round(correctAnswers * 100 / (double) attemptedAnswers) + "% correct\n\n";
    }

    public void incrementCorrectAndAttempted(){
        correctAnswers++;
        attemptedAnswers++;
    }

    public void incrementAttempted(){
        attemptedAnswers++;
    }
}
