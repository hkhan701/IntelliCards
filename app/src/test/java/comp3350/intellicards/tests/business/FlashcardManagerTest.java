package comp3350.intellicards.tests.business;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Test;
import org.junit.Before;

import java.util.UUID;

import comp3350.intellicards.Business.FlashcardManager;
import comp3350.intellicards.Objects.Flashcard;
import comp3350.intellicards.Persistence.stubs.FlashcardPersistenceStub;

public class FlashcardManagerTest {

    private FlashcardManager flashcardManager;

    @Before
    public void setUp() {
        flashcardManager = new FlashcardManager(new FlashcardPersistenceStub());
    }

    /*
     * Test Constructors()
     */
    @Test
    public void testNullConstructorInitializesWithData() {
        FlashcardManager flashcardManagerNullConstructor = new FlashcardManager();

        assertNotEquals("There should be data contained in the flashcardManager if the null constructor is called",
                0, flashcardManagerNullConstructor.getAllActiveFlashcards().size());
    }

    /*
     * Test getAllActiveFlashcards()
     */
    @Test
    public void testGetAllActiveFlashcardsOnlyReturnsActiveOnes() {
        Flashcard flashcard1 = new Flashcard("Analysis/Requirements", "What is the first stage of the software development lifecycle?", null);
        Flashcard flashcard2 = new Flashcard("Design", "What is the second stage of the software development lifecycle?", null);
        String flashcard2UUID = flashcard2.getUUID();

        flashcardManager.insertFlashcard(flashcard1);
        flashcardManager.insertFlashcard(flashcard2);

        flashcardManager.markFlashcardAsDeleted(flashcard2UUID);

        assertTrue("An active flashcard should be returned when calling getAllActiveFlashcards()",
                flashcardManager.getAllActiveFlashcards().contains(flashcard1));
        assertFalse("A deleted flashcard should not be returned when calling getAllActiveFlashcards()",
                flashcardManager.getAllActiveFlashcards().contains(flashcard2));
    }

    /*
     * Test getAllDeletedFlashcards()
     */
    @Test
    public void testGetAllDeletedFlashcardsOnlyReturnsDeletedOnes() {
        Flashcard flashcard1 = new Flashcard("Analysis/Requirements", "What is the first stage of the software development lifecycle?", null);
        Flashcard flashcard2 = new Flashcard("Design", "What is the second stage of the software development lifecycle?", null);
        String flashcard2UUID = flashcard2.getUUID();

        flashcardManager.insertFlashcard(flashcard1);
        flashcardManager.insertFlashcard(flashcard2);

        flashcardManager.markFlashcardAsDeleted(flashcard2UUID);

        assertFalse("An active flashcard should not be returned when calling getAllDeletedFlashcards()",
                flashcardManager.getAllDeletedFlashcards().contains(flashcard1));
        assertTrue("A deleted flashcard should be returned when calling getAllDeletedFlashcards()",
                flashcardManager.getAllDeletedFlashcards().contains(flashcard2));
    }

    /*
     * Test getFlashcard()
     */
    @Test
    public void testGetFlashcardThatExistsInManager() {
        Flashcard flashcard = new Flashcard("Analysis/Requirements", "What is the first stage of the software development lifecycle?", null);
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
        Flashcard flashcard = new Flashcard("Analysis/Requirements", "What is the first stage of the software development lifecycle?", null);
        flashcardManager.insertFlashcard(flashcard);

        flashcard.setHint("This is the stage where you talk to clients and assess their needs");
        flashcardManager.updateFlashcard(flashcard);

        assertEquals("You can update a flashcard with new parameters in the manager",
                flashcard.toString(), flashcardManager.getFlashcard(flashcard.getUUID()).toString());
    }

    /*
     * Test restoreFlashcard()
     */
    @Test
    public void testRestoredFlashcardOnlyShowsUpInActiveList() {
        Flashcard flashcard = new Flashcard("Analysis/Requirements", "What is the first stage of the software development lifecycle?", null);
        String flashcardUUID = flashcard.getUUID();

        flashcardManager.insertFlashcard(flashcard);

        flashcardManager.markFlashcardAsDeleted(flashcardUUID);
        flashcardManager.restoreFlashcard(flashcardUUID);

        assertTrue("A flashcard that has been restored should show up in the active list",
                flashcardManager.getAllActiveFlashcards().contains(flashcard));
        assertFalse("A flashcard that has been restored should not show up in the deleted list",
                flashcardManager.getAllDeletedFlashcards().contains(flashcard));
    }

    @After
    public void tearDown() {
    }
}
