package comp3350.intellicards.tests.business;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Test;
import org.junit.Before;

import java.util.UUID;

import comp3350.intellicards.Business.FlashcardSetManager;
import comp3350.intellicards.Objects.Flashcard;
import comp3350.intellicards.Objects.FlashcardSet;
import comp3350.intellicards.Persistence.stubs.FlashcardSetPersistenceStub;

public class FlashcardSetManagerTest {

    private FlashcardSetManager flashcardSetManager;

    @Before
    public void setUp() { flashcardSetManager = new FlashcardSetManager(new FlashcardSetPersistenceStub()); }

    /**
     * Test Constructors()
     */

    @Test
    public void nullConstructorInitializesWithData() {
        FlashcardSetManager flashcardSetManagerNullConstructor = new FlashcardSetManager();

        assertNotEquals(0, flashcardSetManagerNullConstructor.getAllFlashcardSets().size());
    }

    /**
     * Test getFlashcardSet()
     */

    @Test
    public void getFlashcardSetThatExistsInPersistence() {
        FlashcardSet flashcardSet = new FlashcardSet("COMP 3350");
        String flashcardUUID = flashcardSet.getUUID();
        
        flashcardSetManager.insertFlashcardSet(flashcardSet);

        assertEquals(flashcardSet.toString(), flashcardSetManager.getFlashcardSet(flashcardUUID).toString());
    }

    @Test
    public void getFlashcardSetThatDoesNotExistInPersistence() {
        String flashcardUUID = UUID.randomUUID().toString();

        assertNull(flashcardSetManager.getFlashcardSet(flashcardUUID));
    }
    
    /**
     * Test addFlashcardToFlashcardSet()
     */

    @Test
    public void addFlashcardToExistingFlashcardSet() {
        FlashcardSet flashcardSet = new FlashcardSet("COMP 3350");
        Flashcard flashcard = new Flashcard("Analysis/Requirements", "What is the first stage of the software development lifecycle?", null);
        String flashcardSetUUID = flashcardSet.getUUID();

        flashcardSetManager.insertFlashcardSet(flashcardSet);
        assertTrue(flashcardSetManager.addFlashCardToFlashcardSet(flashcardSet, flashcard));

        assertEquals(1, flashcardSetManager.getFlashcardSet(flashcardSetUUID).getActiveFlashcards().size());
    }

    @Test
    public void addFlashcardToNonExistingFlashcardSet() {
        FlashcardSet flashcardSet = new FlashcardSet("COMP 3350");
        Flashcard flashcard = new Flashcard("Analysis/Requirements", "What is the first stage of the software development lifecycle?", null);

        assertFalse(flashcardSetManager.addFlashCardToFlashcardSet(flashcardSet, flashcard));
    }

    @After
    public void tearDown() {}
}
