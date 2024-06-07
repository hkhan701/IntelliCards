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
    public void setUp() { flashcardManager = new FlashcardManager(new FlashcardPersistenceStub()); }

    /**
     * Test Constructors()
     */
    @Test
    public void nullConstructorInitializesWithData() {
        FlashcardManager flashcardManagerNullConstructor = new FlashcardManager();

        assertNotEquals(0, flashcardManagerNullConstructor.getAllActiveFlashcards().size());
    }

    /**
     * Test getAllActiveFlashcards()
     */
    @Test
    public void getAllActiveFlashcardsOnlyReturnsActiveOnes() {
        Flashcard flashcard1 = new Flashcard("Analysis/Requirements", "What is the first stage of the software development lifecycle?", null);
        Flashcard flashcard2 = new Flashcard("Design", "What is the second stage of the software development lifecycle?", null);
        String flashcard2UUID = flashcard2.getUUID();

        flashcardManager.insertFlashcard(flashcard1);
        flashcardManager.insertFlashcard(flashcard2);

        flashcardManager.markFlashcardAsDeleted(flashcard2UUID);

        assertTrue(flashcardManager.getAllActiveFlashcards().contains(flashcard1));
        assertFalse(flashcardManager.getAllActiveFlashcards().contains(flashcard2));
    }

    /**
     * Test getAllDeletedFlashcards()
     */
    @Test
    public void getAllDeletedFlashcardsOnlyReturnsDeletedOnes() {
        Flashcard flashcard1 = new Flashcard("Analysis/Requirements", "What is the first stage of the software development lifecycle?", null);
        Flashcard flashcard2 = new Flashcard("Design", "What is the second stage of the software development lifecycle?", null);
        String flashcard2UUID = flashcard2.getUUID();

        flashcardManager.insertFlashcard(flashcard1);
        flashcardManager.insertFlashcard(flashcard2);

        flashcardManager.markFlashcardAsDeleted(flashcard2UUID);

        assertFalse(flashcardManager.getAllDeletedFlashcards().contains(flashcard1));
        assertTrue(flashcardManager.getAllDeletedFlashcards().contains(flashcard2));
    }

    /**
     * Test getFlashcard()
     */

    @Test
    public void getFlashcardThatExistsInManager() {
        Flashcard flashcard = new Flashcard("Analysis/Requirements", "What is the first stage of the software development lifecycle?", null);
        String flashcardId = flashcard.getUUID();
        flashcardManager.insertFlashcard(flashcard);

        assertEquals(flashcard.toString(), flashcardManager.getFlashcard(flashcardId).toString());
    }

    @Test
    public void getFlashcardNotInManager() {
        String randomUUID = UUID.randomUUID().toString();

        assertNull(flashcardManager.getFlashcard(randomUUID));
    }

    /**
     * Test updateFlashcard()
     */
    @Test
    public void updateFlashcardWillReviseChangedFields() {
        Flashcard flashcard = new Flashcard("Analysis/Requirements", "What is the first stage of the software development lifecycle?", null);
        flashcardManager.insertFlashcard(flashcard);

        flashcard.setHint("This is the stage where you talk to clients and assess their needs");
        flashcardManager.updateFlashcard(flashcard);

        assertEquals(flashcard.toString(), flashcardManager.getFlashcard(flashcard.getUUID()).toString());
    }

    /**
     * Test restoreFlashcard()
     */

    @Test
    public void restoredFlashcardOnlyShowsUpInActiveList() {
        Flashcard flashcard = new Flashcard("Analysis/Requirements", "What is the first stage of the software development lifecycle?", null);
        String flashcardUUID = flashcard.getUUID();

        flashcardManager.insertFlashcard(flashcard);

        flashcardManager.markFlashcardAsDeleted(flashcardUUID);
        flashcardManager.restoreFlashcard(flashcardUUID);

        assertTrue(flashcardManager.getAllActiveFlashcards().contains(flashcard));
        assertFalse(flashcardManager.getAllDeletedFlashcards().contains(flashcard));
    }

    @After
    public void tearDown() {}
}
