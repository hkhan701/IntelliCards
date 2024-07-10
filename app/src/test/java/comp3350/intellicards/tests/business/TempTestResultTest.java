package comp3350.intellicards.tests.business;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import comp3350.intellicards.Business.TempTestResult;

public class TempTestResultTest {

    private TempTestResult tempTestResult;

    @Before
    public void setUp() {
        tempTestResult = new TempTestResult();
    }

    /*
     * Test getAttemptedAnswers()
     */
    @Test
    public void testGetAttemptedAnswers() {
        assertEquals("TempTestResult can retrieve the value of attemptedAnswers",
                0, tempTestResult.getAttemptedAnswers());
    }

    /*
     * Test getCorrectAnswers()
     */
    @Test
    public void testGetCorrectAnswers() {
        assertEquals("TempTestResult can retrieve the value of correctAnswers",
                0, tempTestResult.getCorrectAnswers());
    }

    /*
     * Test incrementCorrectAndAttempted()
     */
    @Test
    public void testIncrementCorrectAndAttempted() {
        tempTestResult.incrementCorrectAndAttempted();
        assertEquals("TempTestResult can have it's attempted value incremented by using incrementCorrectAndAttempted()",
                1, tempTestResult.getAttemptedAnswers());
        assertEquals("TempTestResult can have it's correct value incremented by using incrementCorrectAndAttempted()",
                1, tempTestResult.getCorrectAnswers());
    }

    /*
     * Test incrementAttempted()
     */
    @Test
    public void testIncrementAttempted() {
        tempTestResult.incrementAttempted();
        assertEquals("TempTestResult can have it's attempted value incremented by using incrementAttempted()",
                1, tempTestResult.getAttemptedAnswers());
    }

    /*
     * Test generateTestStats()
     */
    @Test
    public void testGenerateTestStats() {
        tempTestResult.incrementCorrectAndAttempted();
        tempTestResult.incrementAttempted();
        tempTestResult.incrementCorrectAndAttempted();

        assertEquals("TempTestResult can generate a string containing the test statistics",
                "This tests accuracy, Correct: 2 / 3\nThat is 67% correct\n\n", tempTestResult.generateTestStats());
    }

    @Test
    public void testGenerateTestStatsNoAttempts() {
        assertEquals("TempTestResult can generate a string containing the test statistics if there has been no data collected",
                "This tests accuracy, Correct: 0 / 0\nThat is 0% correct\n\n", tempTestResult.generateTestStats());
    }
}
