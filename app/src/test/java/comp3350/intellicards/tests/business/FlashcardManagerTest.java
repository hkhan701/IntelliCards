package comp3350.intellicards.tests.business;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Test;
import org.junit.Before;
import org.junit.runner.notification.Failure;

import java.util.UUID;

import comp3350.intellicards.Business.FlashcardManager;
import comp3350.intellicards.Business.FlashcardSetManager;
import comp3350.intellicards.Objects.Flashcard;
import comp3350.intellicards.Objects.FlashcardSet;
import comp3350.intellicards.tests.persistance.FlashcardPersistenceStub;
import comp3350.intellicards.tests.persistance.FlashcardSetPersistenceStub;

public class FlashcardManagerTest {

    private FlashcardManager flashcardManager;

    private FlashcardPersistenceStub flashcardData;
    private FlashcardSetPersistenceStub flashcardSetData;
    private FlashcardSetManager flashcardSetManager;

    @Before
    public void setUp() {
        flashcardSetData = new FlashcardSetPersistenceStub();
        flashcardSetManager = new FlashcardSetManager(flashcardSetData);

        flashcardData = new FlashcardPersistenceStub(flashcardSetManager);
        flashcardManager = new FlashcardManager(flashcardData);
    }

    /*
     * Test getAllActiveFlashcards()
     */
//    @Test
//    public void testGetAllActiveFlashcardsOnlyReturnsActiveOnes() {
//        FlashcardSet testCardSet = new FlashcardSet("testUser", "Test Card Set");
//        Flashcard flashcard1 = new Flashcard(testCardSet.getUUID(), "Test Question 1", "Test Answer 1", null);
//        Flashcard flashcard2 = new Flashcard(testCardSet.getUUID(), "Test Question 2", "Test Answer 2", null);
//        String flashcard2UUID = flashcard2.getUUID();
//
//        flashcardManager.insertFlashcard(flashcard1);
//        flashcardManager.insertFlashcard(flashcard2);
//
//        flashcardManager.markFlashcardAsDeleted(flashcard2UUID);
//
//        assertTrue("An active flashcard should be returned when calling getAllActiveFlashcards()",
//                flashcardManager.getAllActiveFlashcards(testCardSet.getUUID()).contains(flashcard1));
//        assertFalse("A deleted flashcard should not be returned when calling getAllActiveFlashcards()",
//                flashcardManager.getAllActiveFlashcards(testCardSet.getUUID()).contains(flashcard2));
//    }

    /*
     * Test getAllDeletedFlashcards()
     */
//    @Test
//    public void testGetAllDeletedFlashcardsOnlyReturnsDeletedOnes() {
//        FlashcardSet testCardSet = new FlashcardSet("testUser", "Test Card Set");
//        Flashcard flashcard1 = new Flashcard(testCardSet.getUUID(), "Test Question 1", "Test Answer 1", null);
//        Flashcard flashcard2 = new Flashcard(testCardSet.getUUID(), "Test Question 2", "Test Answer 2", null);
//        String flashcard2UUID = flashcard2.getUUID();
//
//        flashcardManager.insertFlashcard(flashcard1);
//        flashcardManager.insertFlashcard(flashcard2);
//
//        flashcardManager.markFlashcardAsDeleted(flashcard2UUID);
//
//        assertFalse("An active flashcard should not be returned when calling getAllDeletedFlashcards()",
//                flashcardManager.getAllDeletedFlashcards().contains(flashcard1));
//        assertTrue("A deleted flashcard should be returned when calling getAllDeletedFlashcards()",
//                flashcardManager.getAllDeletedFlashcards().contains(flashcard2));
//    }

    /*
     * Test getFlashcard()
     */
    @Test
    public void testGetFlashcardThatExistsInManager() {
        FlashcardSet testCardSet = new FlashcardSet("testUser", "Test Card Set");
        Flashcard flashcard = new Flashcard(testCardSet.getUUID(), "Test Question", "Test Answer", null);
        String flashcardId = flashcard.getUUID();
        flashcardManager.insertFlashcard(flashcard);

        assertEquals("You should be able to retrieve a flashcard from the manager if you have its UUID",
                flashcard.toString(), flashcardManager.getFlashcard(flashcardId).toString());
    }

    @Test
    public void testGetFlashcardNotInManager() {
        String randomUUID = UUID.randomUUID().toString();

        assertNull("If a flashcard is not in the flashcardManager, getFlashcard() should return null",
                flashcardManager.getFlashcard(randomUUID));
    }

    /*
     * Test updateFlashcard()
     */
    @Test
    public void testUpdateFlashcardWillReviseChangedFields() {
        FlashcardSet testCardSet = new FlashcardSet("testUser", "Test Card Set");
        Flashcard flashcard = new Flashcard(testCardSet.getUUID(), "Test Question", "Test Answer", null);
        flashcardManager.insertFlashcard(flashcard);

        flashcard.setHint("This is the stage where you talk to clients and assess their needs");
        flashcardManager.updateFlashcard(flashcard);

        assertEquals("You can update a flashcard with new parameters in the manager",
                flashcard.toString(), flashcardManager.getFlashcard(flashcard.getUUID()).toString());
    }

    /*
     * Test restoreFlashcard()
     */
//    @Test
//    public void testRestoredFlashcardOnlyShowsUpInActiveList() {
//        FlashcardSet testCardSet = new FlashcardSet("testUser", "Test Card Set");
//        Flashcard flashcard = new Flashcard(testCardSet.getUUID(), "Test Question", "Test Answer", null);
//        String flashcardUUID = flashcard.getUUID();
//
//        flashcardManager.insertFlashcard(flashcard);
//
//        flashcardManager.markFlashcardAsDeleted(flashcardUUID);
//        flashcardManager.restoreFlashcard(flashcardUUID);
//
//        assertTrue("A flashcard that has been restored should show up in the active list",
//                flashcardManager.getAllActiveFlashcards(testCardSet.getUUID()).contains(flashcard));
//        assertFalse("A flashcard that has been restored should not show up in the deleted list",
//                flashcardManager.getAllDeletedFlashcards().contains(flashcard));
//    }

    /*
     * Test markAttempted()
     */
    @Test
    public void TestMarkAttempted() {
        FlashcardSet testCardSet = new FlashcardSet("testUser", "Test Card Set");
        Flashcard flashcard1 = new Flashcard(testCardSet.getUUID(), "Test Question 1", "Test Answer 1", null);
        flashcardManager.insertFlashcard(flashcard1);

        flashcardManager.markAttempted(flashcard1.getUUID());
        assertEquals("FlashcardManager can mark a flashcard as attempted via the markAttempted() method",
                1, flashcardManager.getFlashcard(flashcard1.getUUID()).getAttempted());
    }

    @Test
    public void TestMarkAttemptedNonExistingCard() {
        try {
            flashcardManager.markAttempted("nonExistingUUID");
        } catch (Exception e) {
            fail("FlashcardManager will not throw an exception when calling markAttempted() if the flashcard does not exist");
        }
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
        assertEquals("FlashcardManager can mark a flashcard as attempted via the markAttemptedAndCorrect() method",
                1, flashcardManager.getFlashcard(flashcard1.getUUID()).getAttempted());
        assertEquals("FlashcardManager will mark a flashcard as correct via the markAttemptedAndCorrect() method",
                1, flashcardManager.getFlashcard(flashcard1.getUUID()).getCorrect());
    }

    @Test
    public void TestMarkAttemptedAndCorrectNonExistingCard() {
        try {
            flashcardManager.markAttemptedAndCorrect("nonExistingUUID");
        } catch (Exception e) {
            fail("FlashcardManager will not throw an exception when calling markAttemptedAndCorrect() if the flashcard does not exist");
        }
    }


    @After
    public void tearDown() {
    }
}
