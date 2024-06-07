package comp3350.intellicards.tests.business;

import java.util.List;

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
    public void setUp() {
        flashcardSetManager = new FlashcardSetManager(new FlashcardSetPersistenceStub());
    }

    /*
     * Test Constructors()
     */
    @Test
    public void testNullConstructorInitializesWithData() {
        FlashcardSetManager flashcardSetManagerNullConstructor = new FlashcardSetManager();

        assertNotEquals("There should be data contained in the flashcardSetManager if the null constructor is called",
                0, flashcardSetManagerNullConstructor.getAllFlashcardSets().size());
    }

    /*
     * Test getFlashcardSet()
     */
    @Test
    public void testGetFlashcardSetThatExistsInPersistence() {
        FlashcardSet flashcardSet = new FlashcardSet("COMP 3350");
        String flashcardUUID = flashcardSet.getUUID();

        flashcardSetManager.insertFlashcardSet(flashcardSet);

        assertEquals("You should be able to retrieve a flashcard set from the manager if you have its UUID",
                flashcardSet, flashcardSetManager.getFlashcardSet(flashcardUUID));
    }

    @Test
    public void testGetFlashcardSetThatDoesNotExistInPersistence() {
        String flashcardUUID = UUID.randomUUID().toString();

        assertNull("If a flashcard set is not in the flashcardSetManager, getFlashcardSet() should return null",
                flashcardSetManager.getFlashcardSet(flashcardUUID));
    }

    /*
     * Test addFlashcardToFlashcardSet()
     */
    @Test
    public void testAddFlashcardToExistingFlashcardSet() {
        FlashcardSet flashcardSet = new FlashcardSet("COMP 3350");
        Flashcard flashcard = new Flashcard("Analysis/Requirements", "What is the first stage of the software development lifecycle?", null);
        String flashcardSetUUID = flashcardSet.getUUID();

        flashcardSetManager.insertFlashcardSet(flashcardSet);
        assertTrue(flashcardSetManager.addFlashCardToFlashcardSet(flashcardSet, flashcard));

        assertEquals("You can add a flashcard to a flashcard set from the flashcardSetManager",
                1, flashcardSetManager.getFlashcardSet(flashcardSetUUID).getActiveFlashcards().size());
    }

    @Test
    public void testAddFlashcardToNonExistingFlashcardSet() {
        FlashcardSet flashcardSet = new FlashcardSet("COMP 3350");
        Flashcard flashcard = new Flashcard("Analysis/Requirements", "What is the first stage of the software development lifecycle?", null);

        assertFalse("You cannot add a flashcard to flashcard set via the flashcardSetManager if the flashcard set is not managed",
                flashcardSetManager.addFlashCardToFlashcardSet(flashcardSet, flashcard));
    }

    /*
     * Test getAllFlashcardSets
     */
    @Test
    public void testGetAllFlashcardSets() {
        FlashcardSet flashcardSet1 = new FlashcardSet("COMP 3350");
        FlashcardSet flashcardSet2 = new FlashcardSet("COMP 4620");
        FlashcardSet flashcardSet3 = new FlashcardSet("COMP 3010");

        flashcardSetManager.insertFlashcardSet(flashcardSet1);
        flashcardSetManager.insertFlashcardSet(flashcardSet2);
        flashcardSetManager.insertFlashcardSet(flashcardSet3);

        List<FlashcardSet> flashcardSets = flashcardSetManager.getAllFlashcardSets();


        assertEquals("The number of flashcard sets should match the number of sets we created",
                3, flashcardSets.size());

        assertEquals("The inserted flashcard set should exist", flashcardSet1, flashcardSetManager.getFlashcardSet(flashcardSet1.getUUID()));

        assertNotEquals("The index of the inserted flashcard sets should not be -1 (i.e., doesn't exist)", -1, flashcardSets.indexOf(flashcardSet1));
        assertNotEquals("The index of the inserted flashcard sets should not be -1 (i.e., doesn't exist)", -1, flashcardSets.indexOf(flashcardSet2));
        assertNotEquals("The index of the inserted flashcard sets should not be -1 (i.e., doesn't exist)", -1, flashcardSets.indexOf(flashcardSet3));
    }

    @After
    public void tearDown() {
    }
}
