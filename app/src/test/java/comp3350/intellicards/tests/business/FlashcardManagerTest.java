package comp3350.intellicards.tests.business;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.mock;
//import static org.mockito.Mockito.times;
//import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Test;
import org.junit.Before;

import comp3350.intellicards.Business.FlashcardManager;
import comp3350.intellicards.Objects.Flashcard;
import comp3350.intellicards.Persistence.FlashcardPersistence;

public class FlashcardManagerTest {

    private FlashcardPersistence flashcardData;
    private FlashcardManager flashcardManager;

    @Before
    public void setUp() {
        flashcardData = mock(FlashcardPersistence.class);
        flashcardManager = new FlashcardManager(flashcardData);
    }

    /*
     * Test getFlashcard()
     */
    @Test
    public void testGetFlashcard() {
        Flashcard managed = mock(Flashcard.class);

        when(flashcardData.getFlashcard("uuid")).thenReturn(managed);

        flashcardManager.insertFlashcard(managed);

        assertEquals("FlashcardManager can retrieve managed flashcard given its UUID",
                managed, flashcardManager.getFlashcard("uuid"));
    }

    @Test
    public void testGetFlashcardNotInManaged() {
        when(flashcardManager.getFlashcard(any())).thenReturn(null);

        assertNull("FlashcardManager cannot retrieve non managed flashcard",
                flashcardManager.getFlashcard("TestCardUUID"));
    }

    @Test
    public void testGetFlashcardNull() {
        assertNull("FlashcardManager cannot retrieve a flashcard with a null uuid",
                flashcardManager.getFlashcard(null));
    }


    /*
     * Test updateFlashcard(Flashcard)
     */
    @Test
    public void testUpdateFlashcard() {
        Flashcard managed = mock(Flashcard.class);
        Flashcard updateFlashcard = mock(Flashcard.class);

        when(managed.getUUID()).thenReturn("uuid");
        when(updateFlashcard.getSetUUID()).thenReturn("EditSetUUID");
        when(updateFlashcard.getQuestion()).thenReturn("Edit Question");
        when(updateFlashcard.getAnswer()).thenReturn("Edit Answer");
        when(updateFlashcard.getHint()).thenReturn("Edit Hint");
        when(updateFlashcard.getAttempted()).thenReturn(6);
        when(updateFlashcard.getCorrect()).thenReturn(1);
        when(updateFlashcard.isDeleted()).thenReturn(true);

        flashcardManager.insertFlashcard(managed);
        flashcardManager.updateFlashcard(updateFlashcard);
        Flashcard updated = flashcardManager.getFlashcard(managed.getUUID());

        assertEquals("FlashcardManager can update a flashcard's setUUID",
                updateFlashcard.getSetUUID(), updated.getSetUUID());
        assertEquals("FlashcardManager can update a flashcard's question",
                updateFlashcard.getQuestion(), updated.getQuestion());
        assertEquals("FlashcardManager can update a flashcard's answer",
                updateFlashcard.getAnswer(), updated.getAnswer());
        assertEquals("FlashcardManager can update a flashcard's hint",
                updateFlashcard.getHint(), updated.getHint());
        assertEquals("FlashcardManager can update a flashcard's attempted count",
                updateFlashcard.getAttempted(), updated.getAttempted());
        assertEquals("FlashcardManager can update a flashcard's correct count",
                updateFlashcard.getCorrect(), updated.getCorrect());
        assertEquals("FlashcardManager can update a flashcard's deleted state",
                updateFlashcard.isDeleted(), updated.isDeleted());
    }

    @Test
    public void testUpdateFlashcardNotManaged() {
        Flashcard unmanaged = mock(Flashcard.class);

        when(unmanaged.getUUID()).thenReturn("TestUUID");

        flashcardManager.updateFlashcard(unmanaged);
        Flashcard updated = flashcardManager.getFlashcard(unmanaged.getUUID());

        assertNotNull("FlashcardManager will add unmanaged flashcard via updateFlashcard",
                flashcardManager.getFlashcard(updated.getUUID()));
    }

    /*
     * Test updateFlashcardDetails()
     */

    @Test
    public void testUpdateFlashcardDetails() {
        //Flashcard flashcard = new Flashcard(testCardSet.getUUID(), "Test Question", "Test Answer", null);
        Flashcard managed = mock(Flashcard.class);

        when(managed.getQuestion()).thenReturn("Test Question");
        when(managed.getAnswer()).thenReturn("Test Answer");
        when(managed.getHint()).thenReturn(null);

        flashcardManager.insertFlashcard(managed);
        flashcardManager.updateFlashcardDetails(managed, "Test Question Update", "Test Answer Update", "Test Hint Update");

        assertEquals("FlashcardManager will update the original instance of flashcard's question if given",
                "Test Question Update", managed.getQuestion());
        assertEquals("FlashcardManager will update the original instance of flashcard's answer if given",
                "Test Answer Update", managed.getAnswer());
        assertEquals("FlashcardManager will update the original instance of flashcard's hint if given",
                "Test Hint Update", managed.getHint());
    }

    @Test
    public void testUpdateFlashcardDetailsNotManaged() {
        //Flashcard flashcard = new Flashcard("TestSetUUID", "Test Question", "Test Answer", null);
        Flashcard unmanaged = mock(Flashcard.class);

        when(unmanaged.getUUID()).thenReturn("TestCardUUID");
        when(unmanaged.getQuestion()).thenReturn("Test Question");
        when(unmanaged.getAnswer()).thenReturn("Test Answer");
        when(unmanaged.getHint()).thenReturn(null);

        flashcardManager.updateFlashcardDetails(unmanaged, "Test Question Update", "Test Answer Update", "Test Hint Update");

        assertNotNull("FlashcardManager will add unmanaged flashcard via updateFlashcard",
                flashcardManager.getFlashcard(unmanaged.getUUID()));

        assertEquals("FlashcardManager will update the original instance of flashcard's question if given",
                "Test Question Update", unmanaged.getQuestion());
        assertEquals("FlashcardManager will update the original instance of flashcard's answer if given",
                "Test Answer Update", unmanaged.getAnswer());
        assertEquals("FlashcardManager will update the original instance of flashcard's hint if given",
                "Test Hint Update", unmanaged.getHint());
    }

    /*
     * Test markFlashcardAsDeleted()
     */
    @Test
    public void testMarkFlashcardAsDeleted() {
        //Flashcard flashcard = new Flashcard(testCardSet.getUUID(), "Test Question", "Test Answer", null);
        Flashcard managed = mock(Flashcard.class);

        when(managed.getUUID()).thenReturn("uuid");

        flashcardManager.insertFlashcard(managed);
        flashcardManager.markFlashcardAsDeleted(managed.getUUID());
        Flashcard updated = flashcardManager.getFlashcard(managed.getUUID());

        assertTrue("FlashcardManager can mark a managed flashcard as deleted",
                flashcardManager.getFlashcard(updated.getUUID()).isDeleted());
    }

    @Test
    public void testMarkFlashcardAsDeletedNotManaged() {
        //Flashcard flashcard = new Flashcard(testCardSet.getUUID(), "Test Question", "Test Answer", null);
        Flashcard unmanaged = mock(Flashcard.class);

        when(unmanaged.getUUID()).thenReturn("TestCardUUID");

        flashcardManager.markFlashcardAsDeleted(unmanaged.getUUID());

        assertNull("FlashcardManager will not add a non-managed flashcard when calling the markFlashcardAsDeleted method",
                flashcardManager.getFlashcard(unmanaged.getUUID()));

        assertFalse("FlashcardManager cannot mark a non managed flashcard as deleted",
                unmanaged.isDeleted());
    }


    /*
     * Test restoreFlashcard()
     */
    @Test
    public void testRestoreFlashcard() {
        //Flashcard flashcard = new Flashcard(testCardSet.getUUID(), "Test Question", "Test Answer", null);
        Flashcard managed = mock(Flashcard.class);

        when(managed.getUUID()).thenReturn("uuid");

        flashcardManager.insertFlashcard(managed);

        flashcardManager.markFlashcardAsDeleted(managed.getUUID());
        flashcardManager.restoreFlashcard(managed.getUUID());
        Flashcard updated = flashcardManager.getFlashcard(managed.getUUID());

        assertFalse("FlashcardManager can mark a managed flashcard as restored",
                flashcardManager.getFlashcard(updated.getUUID()).isDeleted());
    }

    @Test
    public void testRestoreFlashcardNotManaged() {
        //Flashcard flashcard = new Flashcard("TestSet", "Test Question", "Test Answer", null);
        Flashcard unmanaged = mock(Flashcard.class);

        when(unmanaged.getUUID()).thenReturn("TestCardUUID");
        when(unmanaged.isDeleted()).thenReturn(true);

        flashcardManager.restoreFlashcard(unmanaged.getUUID());

        assertNull("FlashcardManager will not add a non-managed flashcard when calling the restore function",
                flashcardManager.getFlashcard(unmanaged.getUUID()));

        assertTrue("FlashcardManager cannot mark a non managed flashcard as restored",
                unmanaged.isDeleted());
    }

    /*
     * Test markAttempted()
     */
    @Test
    public void TestMarkAttempted() {
        //Flashcard flashcard = new Flashcard(testCardSet.getUUID(), "Test Question 1", "Test Answer 1", null);
        Flashcard managed = mock(Flashcard.class);

        when(managed.getUUID()).thenReturn("uuid");

        flashcardManager.insertFlashcard(managed);

        flashcardManager.markAttempted(managed.getUUID());
        Flashcard updated = flashcardManager.getFlashcard(managed.getUUID());

        assertEquals("FlashcardManager can mark a flashcard as attempted via the markAttempted() method",
                1, flashcardManager.getFlashcard(updated.getUUID()).getAttempted());
    }

    @Test
    public void TestMarkAttemptedNotManaged() {
        //Flashcard flashcard = new Flashcard(testCardSet.getUUID(), "Test Question 1", "Test Answer 1", null);
        Flashcard unmanaged = mock(Flashcard.class);

        flashcardManager.markAttempted(unmanaged.getUUID());

        assertNull("FlashcardManager will not add a non-managed flashcard when calling the markAttempted method",
                flashcardManager.getFlashcard(unmanaged.getUUID()));

        assertEquals("FlashcardManager cannot mark a non managed flashcard as attempted via the markAttempted() method",
                0, unmanaged.getAttempted());
    }

    /*
     * Test markAttemptedAndCorrect()
     */
    @Test
    public void TestMarkAttemptedAndCorrect() {
        //Flashcard flashcard = new Flashcard(testCardSet.getUUID(), "Test Question 1", "Test Answer 1", null);
        Flashcard managed = mock(Flashcard.class);

        when(managed.getUUID()).thenReturn("uuid");

        flashcardManager.insertFlashcard(managed);

        flashcardManager.markAttemptedAndCorrect(managed.getUUID());
        Flashcard updated = flashcardManager.getFlashcard(managed.getUUID());

        assertEquals("FlashcardManager can mark a flashcard as attempted via the markAttemptedAndCorrect() method",
                1, flashcardManager.getFlashcard(updated.getUUID()).getAttempted());
        assertEquals("FlashcardManager can mark a flashcard as correct via the markAttemptedAndCorrect() method",
                1, flashcardManager.getFlashcard(updated.getUUID()).getCorrect());
    }

    @Test
    public void TestMarkAttemptedAndCorrectNotManaged() {
        //Flashcard flashcard = new Flashcard("TestCardUUID", "TestSetUUID", "Test Question 1", "Test Answer 1", null, false, 0, 0);
        Flashcard unmanaged = mock(Flashcard.class);

        when(unmanaged.getUUID()).thenReturn("TestCardUUID");
        when(unmanaged.getAttempted()).thenReturn(0);
        when(unmanaged.getCorrect()).thenReturn(0);

        flashcardManager.markAttemptedAndCorrect(unmanaged.getUUID());

        assertNull("FlashcardManager will not add a non-managed flashcard when calling the markAttemptedAndCorrect method",
                flashcardManager.getFlashcard(unmanaged.getUUID()));

        assertEquals("FlashcardManager cannot mark a non managed flashcard as attempted via the markAttemptedAndCorrect() method",
                0, unmanaged.getAttempted());
        assertEquals("FlashcardManager cannot mark a non managed flashcard as correct via the markAttemptedAndCorrect() method",
                0, unmanaged.getCorrect());
    }
}
