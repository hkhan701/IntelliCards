package comp3350.intellicards.tests.business;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import org.junit.Before;
import org.junit.Test;

import comp3350.intellicards.Business.ReportCalculator;
import comp3350.intellicards.Objects.Flashcard;
import comp3350.intellicards.Objects.FlashcardSet;

public class ReportCalculatorTest {

    private FlashcardSet mockedSet;
    private Flashcard mockedFlashcard;

    private ReportCalculator reportCalculator;

    @Before
    public void setUp() {
        mockedSet = mock(FlashcardSet.class);
        mockedFlashcard = mock(Flashcard.class);
        reportCalculator = new ReportCalculator(mockedSet);
    }

    /*
     * report
     */

    @Test
    public void testReportGeneration() {
        when(mockedSet.size()).thenReturn(3);
        when(mockedSet.getIndex(anyInt())).thenReturn(mockedFlashcard);
        when(mockedFlashcard.getAttempted()).thenReturn(1, 1, 1);
        when(mockedFlashcard.getCorrect()).thenReturn(1, 0, 1);

        assertEquals("Report statistics are correct",
                "ALL TIME TOTAL ACCURACY\n" +
                        "Correct: 2 / 3\n" +
                        "That is 67% correct: ", reportCalculator.report());
    }

    @Test()
    public void emptyFlashcardSet() {
        assertEquals("Report is generated for case with 0 cards",
                "ALL TIME TOTAL ACCURACY\n" +
                        "Correct: 0 / 0\n" +
                        "That is 0% correct: ", reportCalculator.report());
    }

}
