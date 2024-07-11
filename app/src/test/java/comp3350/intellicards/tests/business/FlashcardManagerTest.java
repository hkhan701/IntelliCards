package comp3350.intellicards.tests.business;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.Before;

import comp3350.intellicards.Business.FlashcardManager;
import comp3350.intellicards.Objects.Flashcard;
import comp3350.intellicards.Objects.FlashcardSet;
import comp3350.intellicards.tests.persistance.FlashcardPersistenceStub;
import comp3350.intellicards.tests.persistance.FlashcardSetPersistenceStub;

public class FlashcardManagerTest {

    private FlashcardManager flashcardManager;

    private FlashcardPersistenceStub flashcardData;
    private FlashcardSetPersistenceStub flashcardSetData;

    @Before
    public void setUp() {
        flashcardSetData = new FlashcardSetPersistenceStub();
        flashcardData = new FlashcardPersistenceStub(flashcardSetData);
        flashcardManager = new FlashcardManager(flashcardData, flashcardSetData);
    }

    /*
     * Test getFlashcard()
     */
    @Test
    public void testGetFlashcard() {
        FlashcardSet testCardSet = new FlashcardSet("testUser", "Test Card Set");
        Flashcard flashcard = new Flashcard(testCardSet.getUUID(), "Test Question", "Test Answer", null);
        String flashcardId = flashcard.getUUID();
        flashcardManager.insertFlashcard(flashcard);

        assertEquals("FlashcardManager can retrieve managed flashcard given its UUID",
                flashcard.toString(), flashcardManager.getFlashcard(flashcardId).toString());
    }

    @Test
    public void testGetFlashcardNotInManaged() {
        assertNull("FlashcardManager cannot retrieve non managed flashcard",
                flashcardManager.getFlashcard("TestUUID"));
    }

    /*
     * Test moveFlashcardToNewSet()
     */


    /*
     * Test updateFlashcard(Flashcard)
     */
    @Test
    public void testUpdateFlashcard() {
        FlashcardSet testCardSet = new FlashcardSet("testUser", "Test Card Set");
        Flashcard flashcard = new Flashcard(testCardSet.getUUID(), "Test Question", "Test Answer", null);
        Flashcard updateFlashcard = new Flashcard(flashcard.getUUID(), "EditSetUUID", "Edit Question", "Edit Answer", "Edit Hint", true, 6, 1);
        flashcardManager.insertFlashcard(flashcard);

        flashcardManager.updateFlashcard(updateFlashcard);
        flashcard = flashcardManager.getFlashcard(flashcard.getUUID());

        assertEquals("FlashcardManager can update a flashcard's setUUID",
                updateFlashcard.getSetUUID(), flashcard.getSetUUID());
        assertEquals("FlashcardManager can update a flashcard's question",
                updateFlashcard.getQuestion(), flashcard.getQuestion());
        assertEquals("FlashcardManager can update a flashcard's answer",
                updateFlashcard.getAnswer(), flashcard.getAnswer());
        assertEquals("FlashcardManager can update a flashcard's hint",
                updateFlashcard.getHint(), flashcard.getHint());
        assertEquals("FlashcardManager can update a flashcard's attempted count",
                updateFlashcard.getAttempted(), flashcard.getAttempted());
        assertEquals("FlashcardManager can update a flashcard's correct count",
                updateFlashcard.getCorrect(), flashcard.getCorrect());
        assertEquals("FlashcardManager can update a flashcard's deleted state",
                updateFlashcard.isDeleted(), flashcard.isDeleted());
    }

    @Test
    public void testUpdateFlashcardNotManaged() {
        FlashcardSet testCardSet = new FlashcardSet("testUser", "Test Card Set");
        Flashcard flashcard = new Flashcard(testCardSet.getUUID(), "Test Question", "Test Answer", null);
        Flashcard updateFlashcard = new Flashcard(flashcard.getUUID(), "EditSetUUID", "Edit Question", "Edit Answer", "Edit Hint", true, 6, 1);

        flashcardManager.updateFlashcard(updateFlashcard);

        assertNotEquals("FlashcardManager cannot update a non managed flashcard's setUUID",
                updateFlashcard.getSetUUID(), flashcard.getSetUUID());
        assertNotEquals("FlashcardManager cannot update a non managed flashcard's question",
                updateFlashcard.getQuestion(), flashcard.getQuestion());
        assertNotEquals("FlashcardManager cannot update a non managed flashcard's answer",
                updateFlashcard.getAnswer(), flashcard.getAnswer());
        assertNotEquals("FlashcardManager can update a flashcard's hint",
                updateFlashcard.getHint(), flashcard.getHint());
        assertNotEquals("FlashcardManager cannot update a non managed flashcard's attempted count",
                updateFlashcard.getAttempted(), flashcard.getAttempted());
        assertNotEquals("FlashcardManager cannot update a non managed flashcard's correct count",
                updateFlashcard.getCorrect(), flashcard.getCorrect());
        assertNotEquals("FlashcardManager cannot update a non managed flashcard's deleted state",
                updateFlashcard.isDeleted(), flashcard.isDeleted());
    }

    /*
     * Test updateFlashcard(Flashcard, FlashcardSet, String, String, String)
     */

    @Test
    public void testUpdateFlashcardNewSet() {
        FlashcardSet testCardSet = new FlashcardSet("testUser", "Test Card Set");
        FlashcardSet testCardSetMove = new FlashcardSet("testUser", "Test Card Set Update");
        Flashcard flashcard = new Flashcard(testCardSet.getUUID(), "Test Question", "Test Answer", null);

        flashcardSetData.insertFlashcardSet(testCardSet);
        flashcardSetData.insertFlashcardSet(testCardSetMove);

        flashcardManager.updateFlashcard(flashcard, testCardSetMove, "Test Question", "Test Answer", null);
        testCardSetMove = flashcardSetData.getFlashcardSet(testCardSetMove.getUUID());

        assertNotNull("FlashcardManager can move a flashcard between sets",
                testCardSetMove.getActiveFlashcards().getIndex(0));

        assertFalse("FlashcardManager deletes old card when it is moved to new set",
                flashcard.isDeleted());
    }

    @Test
    public void testUpdateFlashcardDetails() {
        FlashcardSet testCardSet = new FlashcardSet("testUser", "Test Card Set");
        Flashcard flashcard = new Flashcard(testCardSet.getUUID(), "Test Question", "Test Answer", null);

        flashcardManager.updateFlashcard(flashcard, null, "Test Question Update", "Test Answer Update", "Test Hint Update");

        assertEquals("FlashcardManager will update flashcard's question if given",
                "Test Question Update", flashcard.getQuestion());
        assertEquals("FlashcardManager will update flashcard's answer if given",
                "Test Answer Update", flashcard.getAnswer());
        assertEquals("FlashcardManager will update flashcard's hint if given",
                "Test Hint Update", flashcard.getHint());
    }

    /*
     * Test markFlashcardAsDeleted()
     */
    @Test
    public void testMarkFlashcardAsDeleted() {
        FlashcardSet testCardSet = new FlashcardSet("testUser", "Test Card Set");
        Flashcard flashcard = new Flashcard(testCardSet.getUUID(), "Test Question", "Test Answer", null);
        String flashcardUUID = flashcard.getUUID();

        flashcardManager.insertFlashcard(flashcard);

        flashcardManager.markFlashcardAsDeleted(flashcardUUID);
        flashcard = flashcardManager.getFlashcard(flashcard.getUUID());

        assertTrue("FlashcardManager can mark a managed flashcard as deleted",
                flashcardManager.getFlashcard(flashcard.getUUID()).isDeleted());
    }

    @Test
    public void testMarkFlashcardAsDeletedNotManaged() {
        FlashcardSet testCardSet = new FlashcardSet("testUser", "Test Card Set");
        Flashcard flashcard = new Flashcard(testCardSet.getUUID(), "Test Question", "Test Answer", null);
        String flashcardUUID = flashcard.getUUID();

        flashcardManager.markFlashcardAsDeleted(flashcardUUID);

        assertFalse("FlashcardManager cannot mark a non managed flashcard as deleted",
                flashcard.isDeleted());
    }


    /*
     * Test restoreFlashcard()
     */
    @Test
    public void testRestoreFlashcard() {
        FlashcardSet testCardSet = new FlashcardSet("testUser", "Test Card Set");
        Flashcard flashcard = new Flashcard(testCardSet.getUUID(), "Test Question", "Test Answer", null);
        String flashcardUUID = flashcard.getUUID();

        flashcardManager.insertFlashcard(flashcard);

        flashcardManager.markFlashcardAsDeleted(flashcardUUID);
        flashcardManager.restoreFlashcard(flashcardUUID);
        flashcard = flashcardManager.getFlashcard(flashcard.getUUID());

        assertFalse("FlashcardManager can mark a managed flashcard as restored",
                flashcardManager.getFlashcard(flashcard.getUUID()).isDeleted());
    }

    @Test
    public void testRestoreFlashcardNotManaged() {
        Flashcard flashcard = new Flashcard("TestSet", "Test Question", "Test Answer", null);

        flashcard.markDeleted();
        flashcardManager.restoreFlashcard(flashcard.getUUID());

        assertTrue("FlashcardManager cannot mark a non managed flashcard as restored",
                flashcard.isDeleted());
    }

    /*
     * Test markAttempted()
     */
    @Test
    public void TestMarkAttempted() {
        FlashcardSet testCardSet = new FlashcardSet("testUser", "Test Card Set");
        Flashcard flashcard1 = new Flashcard(testCardSet.getUUID(), "Test Question 1", "Test Answer 1", null);
        flashcardManager.insertFlashcard(flashcard1);

        flashcardManager.markAttempted(flashcard1.getUUID());
        flashcard1 = flashcardManager.getFlashcard(flashcard1.getUUID());

        assertEquals("FlashcardManager can mark a flashcard as attempted via the markAttempted() method",
                1, flashcardManager.getFlashcard(flashcard1.getUUID()).getAttempted());
    }

    @Test
    public void TestMarkAttemptedNotManaged() {
        FlashcardSet testCardSet = new FlashcardSet("testUser", "Test Card Set");
        Flashcard flashcard1 = new Flashcard(testCardSet.getUUID(), "Test Question 1", "Test Answer 1", null);

        flashcardManager.markAttempted(flashcard1.getUUID());

        assertEquals("FlashcardManager cannot mark a non managed flashcard as attempted via the markAttempted() method",
                0, flashcard1.getAttempted());
    }

    /*
     * Test markAttemptedAndCorrect()
     */
    @Test
    public void TestMarkAttemptedAndCorrect() {
        FlashcardSet testCardSet = new FlashcardSet("testUser", "Test Card Set");
        Flashcard flashcard1 = new Flashcard(testCardSet.getUUID(), "Test Question 1", "Test Answer 1", null);
        flashcardManager.insertFlashcard(flashcard1);

        flashcardManager.markAttemptedAndCorrect(flashcard1.getUUID());
        flashcard1 = flashcardManager.getFlashcard(flashcard1.getUUID());

        assertEquals("FlashcardManager can mark a flashcard as attempted via the markAttemptedAndCorrect() method",
                1, flashcardManager.getFlashcard(flashcard1.getUUID()).getAttempted());
        assertEquals("FlashcardManager can mark a flashcard as correct via the markAttemptedAndCorrect() method",
                1, flashcardManager.getFlashcard(flashcard1.getUUID()).getCorrect());
    }

    @Test
    public void TestMarkAttemptedAndCorrectNotManaged() {
        Flashcard flashcard1 = new Flashcard("TestCardUUID", "TestSetUUID", "Test Question 1", "Test Answer 1", null, false, 0, 0);

        flashcardManager.markAttemptedAndCorrect(flashcard1.getUUID());

        assertEquals("FlashcardManager cannot mark a non managed flashcard as attempted via the markAttemptedAndCorrect() method",
                0, flashcard1.getAttempted());
        assertEquals("FlashcardManager cannot mark a non managed flashcard as correct via the markAttemptedAndCorrect() method",
                0, flashcard1.getCorrect());
    }
}
