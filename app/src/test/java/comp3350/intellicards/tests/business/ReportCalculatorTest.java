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

    private FlashcardSet flashcardSetMock;
    private Flashcard flashcardMock;
    private ReportCalculator reportCalculator;

    @Before
    public void setUp() {
        flashcardSetMock = mock(FlashcardSet.class);
        flashcardMock = mock(Flashcard.class);
        reportCalculator = new ReportCalculator(flashcardSetMock);
    }

    @Test
    public void test() {
        assertTrue(true);
    }

    /*
     * Test Constructor/collectStats()
     */
    @Test
    public void collectStats() {
        when(flashcardSetMock.size()).thenReturn(3);
        when(flashcardSetMock.getIndex(anyInt())).thenReturn(flashcardMock);

        when(flashcardMock.isDeleted()).thenReturn(false);
        when(flashcardMock.getAttempted()).thenReturn(4, 2, 3);
        when(flashcardMock.getCorrect()).thenReturn(3, 2, 3);

        reportCalculator = new ReportCalculator(flashcardSetMock);

        assertEquals("ReportCalculator will accurately sum the attempted amount of all cards in the flashcard set upon creation",
                9, reportCalculator.getTotalAttempted());
        assertEquals("ReportCalculator will accurately sum the correct amount of all cards in the flashcard set upon creation",
                8, reportCalculator.getTotalCorrect());
    }

    @Test
    public void collectStatsWithDeletedCards() {
        when(flashcardSetMock.size()).thenReturn(4);
        when(flashcardSetMock.getIndex(anyInt())).thenReturn(flashcardMock);

        when(flashcardMock.isDeleted()).thenReturn(false, false, true, false);
        when(flashcardMock.getAttempted()).thenReturn(4, 2, 3, 8);
        when(flashcardMock.getCorrect()).thenReturn(3, 2, 3, 8);

        reportCalculator = new ReportCalculator(flashcardSetMock);

        assertEquals("ReportCalculator will not include the attempted count of deleted cards when the flashcard set is created",
                9, reportCalculator.getTotalAttempted());
        assertEquals("ReportCalculator will not include the correct count of deleted cards when the flashcard set is created",
                8, reportCalculator.getTotalCorrect());
    }

    @Test
    public void collectStatsEmptyFlashcardSet() {
        when(flashcardSetMock.size()).thenReturn(0);

        reportCalculator = new ReportCalculator(flashcardSetMock);

        assertEquals("ReportManager will set attempted count to 0 if there are no cards in the given flashcardSet",
                0, reportCalculator.getTotalAttempted());
        assertEquals("ReportManager will set correct count to 0 if there are no cards in the given flashcardSet",
                0, reportCalculator.getTotalCorrect());
    }

    /*
     * Test getUserInformation()
     */


    /*
     * Test getAllTimeFlashcardCounts()
     */

    /*
     * Test getAllTimeAccuracy()
     */

    /*
     * Test reportSetAccuracy()
     */

    /*
     * Test getTotalAttempted()
     */

    /*
     * Test getTotalCorrect
     */
}
