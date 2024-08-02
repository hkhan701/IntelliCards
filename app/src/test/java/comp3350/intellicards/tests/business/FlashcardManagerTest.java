package comp3350.intellicards.tests.business;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Test;
import org.junit.Before;

import comp3350.intellicards.Business.FlashcardManager;
import comp3350.intellicards.Objects.Flashcard;
import comp3350.intellicards.Persistence.FlashcardPersistence;

public class FlashcardManagerTest {

    private FlashcardPersistence flashcardData;
    private Flashcard flashcard;
    private FlashcardManager flashcardManager;

    @Before
    public void setUp() {
        flashcard = mock(Flashcard.class);
        when(flashcard.getUUID()).thenReturn("uuid");
        flashcardData = mock(FlashcardPersistence.class);
        flashcardManager = new FlashcardManager(flashcardData);
    }

    /*
     * Test getFlashcard()
     */
    @Test
    public void testGetFlashcard() {
        flashcardManager.getFlashcard(flashcard.getUUID());

        // FlashcardManager can call getFlashcard() for the persistence from its own method of the same name
        verify(flashcardData).getFlashcard(flashcard.getUUID());
    }

    /*
     * Test getFlashcardByKey()
     */
    @Test
    public void testGetFlashcardByKey() {
        flashcardManager.getFlashcardsByKey("key");

        // FlashcardManager can call getFlashcardByKey() for the persistence from its own method of the same name
        verify(flashcardData).getFlashcardsByKey("key");
    }

    /*
     * Test insertFlashcard()
     */
    @Test
    public void testInsertFlashcard() {
        flashcardManager.insertFlashcard(flashcard);

        // FlashcardManager can call insertFlashcard() for the persistence from its own method of the same name
        verify(flashcardData).insertFlashcard(flashcard);
    }

    /*
     * Test updateFlashcard(Flashcard)
     */
    @Test
    public void testUpdateFlashcard() {
        flashcardManager.updateFlashcard(flashcard);

        // FlashcardManager can call updateFlashcard() for the persistence from its own method of the same name
        verify(flashcardData).updateFlashcard(flashcard);
    }

    /*
     * Test updateFlashcardDetails()
     */
    @Test
    public void testUpdateFlashcardDetails() {
        flashcardManager.updateFlashcardDetails(flashcard, "Test Question Update", "Test Answer Update", "Test Hint Update");

        // FlashcardManager will update the original instance of flashcard's question if given
        verify(flashcard).setQuestion("Test Question Update");

        // FlashcardManager will update the original instance of flashcard's answer if given
        verify(flashcard).setAnswer("Test Answer Update");

        // FlashcardManager will update the original instance of flashcard's hint if given
        verify(flashcard).setHint("Test Hint Update");
    }

    /*
     * Test markFlashcardAsDeleted()
     */
    @Test
    public void testMarkFlashcardAsDeleted() {
        flashcardManager.markFlashcardAsDeleted(flashcard.getUUID());

        // FlashcardManager can call markFlashcardAsDeleted() for the persistence from its own method of the same name
        verify(flashcardData).markFlashcardAsDeleted(flashcard.getUUID());
    }

    /*
     * Test restoreFlashcard()
     */
    @Test
    public void testRestoreFlashcard() {
        flashcardManager.restoreFlashcard(flashcard.getUUID());

        // FlashcardManager can call restoreFlashcard() for the persistence from its own method of the same name
        verify(flashcardData).restoreFlashcard(flashcard.getUUID());
    }

    /*
     * Test markAttempted()
     */
    @Test
    public void TestMarkAttempted() {
        flashcardManager.markAttempted(flashcard.getUUID());

        // FlashcardManager can call markAttempted() for the persistence from its own method of the same name
        verify(flashcardData).markAttempted(flashcard.getUUID());
    }

    /*
     * Test markAttemptedAndCorrect()
     */
    @Test
    public void TestMarkAttemptedAndCorrect() {
        flashcardManager.markAttemptedAndCorrect(flashcard.getUUID());

        // FlashcardManager can call markAttemptedAndCorrect() for the persistence from its own method of the same name
        verify(flashcardData).markAttemptedAndCorrect(flashcard.getUUID());
    }
}
