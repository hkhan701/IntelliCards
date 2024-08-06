package comp3350.intellicards.tests.business;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import comp3350.intellicards.Business.ReportCalculator;
import comp3350.intellicards.Objects.Flashcard;
import comp3350.intellicards.Objects.FlashcardSet;

public class ReportCalculatorTest {

    private FlashcardSet flashcardSetMock;
    private Flashcard flashcardMock;
    private ReportCalculator reportCalculator;

    private FlashcardSet flashcardSetMock1;
    private FlashcardSet flashcardSetMock2;

    private Flashcard flashcardMock1;
    private Flashcard flashcardMock2;

    @Before
    public void setUp() {
        flashcardSetMock = mock(FlashcardSet.class);
        flashcardMock = mock(Flashcard.class);
        reportCalculator = new ReportCalculator(flashcardSetMock);

        flashcardSetMock1 = mock(FlashcardSet.class);
        flashcardSetMock2 = mock(FlashcardSet.class);

        flashcardMock1 = mock(Flashcard.class);
        flashcardMock2 = mock(Flashcard.class);
    }

    @Test
    public void test() { assertTrue(true);}

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
    @Test
    public void testGetUserInformation() {
        List<FlashcardSet> flashcardSetList = mock(List.class);

        // == this flashcard set will have 3 correct, 5 attempted
        when(flashcardMock1.getAttempted()).thenReturn(5);
        when(flashcardMock1.getCorrect()).thenReturn(3);
        when(flashcardMock1.isDeleted()).thenReturn(false);

        // == this flashcard set will have 6 correct, 10 attempted
        when(flashcardMock2.getAttempted()).thenReturn(10);
        when(flashcardMock2.getCorrect()).thenReturn(6);
        when(flashcardMock2.isDeleted()).thenReturn(false);

        when(flashcardSetMock1.size()).thenReturn(1);
        when(flashcardSetMock1.getActiveCount()).thenReturn(1);
        when(flashcardSetMock1.getDeletedCount()).thenReturn(0);
        when(flashcardSetMock1.getIndex(anyInt())).thenReturn(flashcardMock1);

        when(flashcardSetMock2.size()).thenReturn(1);
        when(flashcardSetMock2.getActiveCount()).thenReturn(1);
        when(flashcardSetMock2.getDeletedCount()).thenReturn(0);
        when(flashcardSetMock2.getIndex(anyInt())).thenReturn(flashcardMock2);

        when(flashcardSetList.size()).thenReturn(2);
        when(flashcardSetList.get(0)).thenReturn(flashcardSetMock1);
        when(flashcardSetList.get(1)).thenReturn(flashcardSetMock2);

        assertEquals("ReportCalculator will give an accurate result of how much flashcards there are, how many, active, deleted, and each accuracy of flashcards.",
                "Total Flashcard Sets: 2\nFlashcard count: 2\nActive Flashcard count: 2\nDeleted Flashcard count: 0" +
                        "\n\n\nALL TIME TOTAL ACCURACY\nCorrect: 9 / 15\nThat is 60% correct: ",
                ReportCalculator.getUserInformation(flashcardSetList));
    }

    @Test
    public void testGetUserInformationWithDeletedFlashcards() {
        flashcardMock1 = new Flashcard("setUUID1", "Question1", "Answer1", null);
        flashcardMock2 = new Flashcard("setUUID2", "Question2", "Answer2", null);

        flashcardSetMock1 = new FlashcardSet("Test1", "Test1");
        flashcardSetMock2 = new FlashcardSet("Test2", "Test2");

        flashcardSetMock1.addFlashcard(flashcardMock1);
        flashcardMock1.markAttemptedAndCorrect();
        flashcardMock1.markAttemptedAndCorrect();
        flashcardMock1.markAttemptedAndCorrect();
        flashcardMock1.markAttempted();
        flashcardMock1.markAttempted();
        flashcardMock1.markDeleted();

        flashcardSetMock2.addFlashcard(flashcardMock2);
        flashcardMock2.markAttemptedAndCorrect();
        flashcardMock2.markAttemptedAndCorrect();
        flashcardMock2.markAttemptedAndCorrect();
        flashcardMock2.markAttemptedAndCorrect();
        flashcardMock2.markAttemptedAndCorrect();
        flashcardMock2.markAttemptedAndCorrect();
        flashcardMock2.markAttempted();
        flashcardMock2.markAttempted();
        flashcardMock2.markAttempted();
        flashcardMock2.markAttempted();
        // == this flashcard set will have 6 correct, 10 attempted

        List<FlashcardSet> flashcardSetList = Arrays.asList(flashcardSetMock1, flashcardSetMock2);

        assertEquals("ReportCalculator will display accurate information even when there are flashcards deleted in a set.",
                "Total Flashcard Sets: 2\nFlashcard count: 2\nActive Flashcard count: 1\nDeleted Flashcard count: 1" +
                        "\n\n\nALL TIME TOTAL ACCURACY\nCorrect: 6 / 10\nThat is 60% correct: ",
                ReportCalculator.getUserInformation(flashcardSetList));
    }

    @Test
    public void testGetUserInformationWithNoFlashcards() {
        flashcardMock1 = new Flashcard("setUUID1", "Question1", "Answer1", null);

        flashcardSetMock1 = new FlashcardSet("Test1", "Test1");
        flashcardSetMock2 = new FlashcardSet("Test2", "Test2");

        flashcardSetMock1.addFlashcard(flashcardMock1);
        flashcardMock1.markAttemptedAndCorrect();
        flashcardMock1.markAttemptedAndCorrect();
        flashcardMock1.markAttemptedAndCorrect();
        flashcardMock1.markAttempted();
        flashcardMock1.markAttempted();
        // == this flashcard set will have 6 correct, 10 attempted

        List<FlashcardSet> flashcardSetList = Arrays.asList(flashcardSetMock1, flashcardSetMock2);

        assertEquals("ReportCalculator will display accurate information even when there are no flashcards in a set.",
                "Total Flashcard Sets: 2\nFlashcard count: 1\nActive Flashcard count: 1\nDeleted Flashcard count: 0" +
                        "\n\n\nALL TIME TOTAL ACCURACY\nCorrect: 3 / 5\nThat is 60% correct: ",
                ReportCalculator.getUserInformation(flashcardSetList));
    }

    /*
     * Test getAllTimeFlashcardCounts()
     */
    @Test
    public void testGetAllFlashcardCounts() {
        List<FlashcardSet> flashcardSetList = mock(List.class);
        when(flashcardSetList.size()).thenReturn(2);

        // Setup flashcardSetMock1
        when(flashcardSetList.get(0)).thenReturn(flashcardSetMock1);
        when(flashcardSetMock1.size()).thenReturn(6);
        when(flashcardSetMock1.getActiveCount()).thenReturn(4);
        when(flashcardSetMock1.getDeletedCount()).thenReturn(2);

        // Setup flashcardSetMock2
        when(flashcardSetList.get(1)).thenReturn(flashcardSetMock2);
        when(flashcardSetMock2.size()).thenReturn(6);
        when(flashcardSetMock2.getActiveCount()).thenReturn(5);
        when(flashcardSetMock2.getDeletedCount()).thenReturn(1);

        // Testing
        assertArrayEquals("ReportCalculator accurately gets the number of total, active and deleted flashcards when both sets have a size greater than 0.",
                new int[]{12, 9, 3}, ReportCalculator.getAllTimeFlashcardCounts(flashcardSetList));
    }

    @Test
    public void testGetAllFlashcardCountNoFlashcards() {
        List<FlashcardSet> flashcardSetList = mock(List.class);
        when(flashcardSetList.size()).thenReturn(2);

        // Setup flashcardSetMock1
        when(flashcardSetList.get(0)).thenReturn(flashcardSetMock1);
        when(flashcardSetMock1.size()).thenReturn(0);
        when(flashcardSetMock1.getActiveCount()).thenReturn(0);
        when(flashcardSetMock1.getDeletedCount()).thenReturn(0);

        // Setup flashcardSetMock2
        when(flashcardSetList.get(1)).thenReturn(flashcardSetMock2);
        when(flashcardSetMock2.size()).thenReturn(6);
        when(flashcardSetMock2.getActiveCount()).thenReturn(5);
        when(flashcardSetMock2.getDeletedCount()).thenReturn(1);

        assertArrayEquals("ReportCalculator accurately gets the number of total, active, and deleted flashcards even if one card does not have any flashcards.",
                new int[]{6, 5, 1}, ReportCalculator.getAllTimeFlashcardCounts(flashcardSetList));
    }

    /*
     * Test getAllTimeAccuracy()
     */
    @Test
    public void testGetAllTimeAccuracy() {
        flashcardMock1 = new Flashcard("setUUID1", "Question1", "Answer1", null);
        flashcardMock2 = new Flashcard("setUUID2", "Question2", "Answer2", null);

        flashcardSetMock1 = new FlashcardSet("Test1", "Test1");
        flashcardSetMock2 = new FlashcardSet("Test2", "Test2");

        flashcardSetMock1.addFlashcard(flashcardMock1);
        flashcardMock1.markAttemptedAndCorrect();
        flashcardMock1.markAttemptedAndCorrect();
        flashcardMock1.markAttemptedAndCorrect();
        flashcardMock1.markAttempted();
        flashcardMock1.markAttempted();
        // == this flashcard set will have 3 correct, 5 attempted

        flashcardSetMock2.addFlashcard(flashcardMock2);
        flashcardMock2.markAttemptedAndCorrect();
        flashcardMock2.markAttemptedAndCorrect();
        flashcardMock2.markAttemptedAndCorrect();
        flashcardMock2.markAttemptedAndCorrect();
        flashcardMock2.markAttemptedAndCorrect();
        flashcardMock2.markAttemptedAndCorrect();
        flashcardMock2.markAttempted();
        flashcardMock2.markAttempted();
        flashcardMock2.markAttempted();
        flashcardMock2.markAttempted();
        // == this flashcard set will have 6 correct, 10 attempted

        List<FlashcardSet> flashcardSetList = Arrays.asList(flashcardSetMock1, flashcardSetMock2);

        assertEquals("ReportCalculator will accurately calculate the number of correct and attempt of each flashcard.",
                "ALL TIME TOTAL ACCURACY\nCorrect: 9 / 15\nThat is 60% correct: ",
                ReportCalculator.getAllTimeAccuracy(flashcardSetList));
    }

    @Test
    public void getAllTimeAccuracyWithDeletedFlashcards() {
        flashcardMock1 = new Flashcard("setUUID1", "Question1", "Answer1", null);
        flashcardMock2 = new Flashcard("setUUID2", "Question2", "Answer2", null);

        flashcardSetMock1 = new FlashcardSet("Test1", "Test1");
        flashcardSetMock2 = new FlashcardSet("Test2", "Test2");

        flashcardSetMock1.addFlashcard(flashcardMock1);
        flashcardMock1.markAttemptedAndCorrect();
        flashcardMock1.markAttemptedAndCorrect();
        flashcardMock1.markAttemptedAndCorrect();
        flashcardMock1.markAttempted();
        flashcardMock1.markAttempted();
        flashcardMock1.markDeleted();

        flashcardSetMock2.addFlashcard(flashcardMock2);
        flashcardMock2.markAttemptedAndCorrect();
        flashcardMock2.markAttemptedAndCorrect();
        flashcardMock2.markAttemptedAndCorrect();
        flashcardMock2.markAttemptedAndCorrect();
        flashcardMock2.markAttemptedAndCorrect();
        flashcardMock2.markAttemptedAndCorrect();
        flashcardMock2.markAttempted();
        flashcardMock2.markAttempted();
        flashcardMock2.markAttempted();
        flashcardMock2.markAttempted();
        // == this flashcard set will have 6 correct, 10 attempted

        List<FlashcardSet> flashcardSetList = Arrays.asList(flashcardSetMock1, flashcardSetMock2);

        assertEquals("ReportCalculator will accurately calculate the right number of total correctness and attempted even if a deleted flashcard is detected.",
                "ALL TIME TOTAL ACCURACY\nCorrect: 6 / 10\nThat is 60% correct: ",
                ReportCalculator.getAllTimeAccuracy(flashcardSetList));
    }

    @Test
    public void getAllTimeAccuracyWithNoFlashcards() {
        flashcardSetMock1 = new FlashcardSet("Test1", "Test1");
        flashcardSetMock2 = new FlashcardSet("Test2", "Test2");

        List<FlashcardSet> flashcardSetList = Arrays.asList(flashcardSetMock1, flashcardSetMock2);

        assertEquals("ReportCalculator will accurately calculate the right number of total correctness and attempted even if there are no flashcards detected.",
                "ALL TIME TOTAL ACCURACY\nCorrect: 0 / 0\nThat is 0% correct: ",
                ReportCalculator.getAllTimeAccuracy(flashcardSetList));
    }

    /*
     * Test reportSetAccuracy()
     */
    @Test
    public void testReportSetAccurancy() {
        when(flashcardSetMock.size()).thenReturn(3);
        when(flashcardSetMock.getIndex(anyInt())).thenReturn(flashcardMock);

        when(flashcardMock.isDeleted()).thenReturn(false);
        when(flashcardMock.getAttempted()).thenReturn(4, 3, 3);
        when(flashcardMock.getCorrect()).thenReturn(3, 2, 3);

        reportCalculator = new ReportCalculator(flashcardSetMock);

        assertEquals("ReportCalculator will produce the correct acurrancy calculation of total correct and total attempted.",
                "ALL TIME TOTAL ACCURACY\nCorrect: 8 / 10\nThat is 80% correct: ",
                reportCalculator.reportSetAccuracy());
    }

    @Test
    public void testSetAccuracyWithDeletedCards() {
        when(flashcardSetMock.size()).thenReturn(3);
        when(flashcardSetMock.getIndex(anyInt())).thenReturn(flashcardMock);

        when(flashcardMock.isDeleted()).thenReturn(false, false, true);
        when(flashcardMock.getAttempted()).thenReturn(4, 3, 3);
        when(flashcardMock.getCorrect()).thenReturn(3, 2, 3);

        reportCalculator = new ReportCalculator(flashcardSetMock);

        assertEquals("Report Calculator will produce the correct acurrancy calculation of total correct and total attempted with deleted flashcards detected.",
                "ALL TIME TOTAL ACCURACY\nCorrect: 5 / 7\nThat is 71% correct: ",
                reportCalculator.reportSetAccuracy());
    }

    @Test
    public void testSetAccuracyWithNoCards() {
        reportCalculator = new ReportCalculator(flashcardSetMock);

        assertEquals("Report Calculator will produce the correct acurrancy calculation of total correct and total attempted even if no cards are detected.",
                "ALL TIME TOTAL ACCURACY\nCorrect: 0 / 0\nThat is 0% correct: ",
                reportCalculator.reportSetAccuracy());
    }

    /*
     * Test getTotalAttempted()
     */
    @Test
    public void testGetTotalAttempted() {
        when(flashcardSetMock.size()).thenReturn(3);
        when(flashcardSetMock.getIndex(anyInt())).thenReturn(flashcardMock);

        when(flashcardMock.isDeleted()).thenReturn(false, false, true);
        when(flashcardMock.getAttempted()).thenReturn(4, 2, 3);

        reportCalculator = new ReportCalculator(flashcardSetMock);

        assertEquals("Report Calculator will produce the accurate total attempts and not include deleted flashcard into report.",
                6, reportCalculator.getTotalAttempted());
    }

    /*
     * Test getTotalCorrect
     */
    @Test
    public void testGetTotalCorrect() {
        when(flashcardSetMock.size()).thenReturn(3);
        when(flashcardSetMock.getIndex(anyInt())).thenReturn(flashcardMock);

        when(flashcardMock.isDeleted()).thenReturn(false, false, true);
        when(flashcardMock.getCorrect()).thenReturn(3, 2, 3);

        reportCalculator = new ReportCalculator(flashcardSetMock);

        assertEquals("Report calculator will produce the accurate total correct and not include deleted flashcards into report.",
                5, reportCalculator.getTotalCorrect());
    }
}
