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
import comp3350.intellicards.tests.persistance.FlashcardSetPersistenceStub;

public class FlashcardSetManagerTest {

    private FlashcardSetPersistenceStub flashcardSetData;
    private FlashcardSetManager flashcardSetManager;

    @Before
    public void setUp() {
        flashcardSetData = new FlashcardSetPersistenceStub();
        flashcardSetManager = new FlashcardSetManager(flashcardSetData);
    }

    /*
     * Test Constructors()
     */
//    @Test
//    public void testNullConstructorInitializesWithData() {
//        FlashcardSetManager flashcardSetManagerNullConstructor = new FlashcardSetManager();
//
//        assertNotEquals("There should be data contained in the flashcardSetManager if the null constructor is called",
//                0, flashcardSetManagerNullConstructor.getAllFlashcardSets().size());
//    }

    /*
     * Test getFlashcardSet()
     */
    @Test
    public void testGetFlashcardSetThatExistsInPersistence() {
        FlashcardSet flashcardSet = new FlashcardSet("Test User", "Test Set");
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
     * Test getActiveFlashcardSet
     */
    @Test
    public void testGetActiveFlashcardSet() {
        FlashcardSet flashcardSet = new FlashcardSet("testUser", "Test Set");
        Flashcard flashcard1 = new Flashcard(flashcardSet.getUUID(), "Test Question 1", "Test Answer 1", "Test Hint 1");
        Flashcard flashcard2 = new Flashcard(flashcardSet.getUUID(), "Test Question 2", "Test Answer 2", "Test Hint 2");

        flashcardSetManager.insertFlashcardSet(flashcardSet);
        flashcardSetManager.addFlashcardToFlashcardSet(flashcardSet.getUUID(), flashcard1);
        flashcardSetManager.addFlashcardToFlashcardSet(flashcardSet.getUUID(), flashcard2);

        flashcard2.markDeleted();

        FlashcardSet activeSet = flashcardSetManager.getActiveFlashcardSet(flashcardSet.getUUID());

        assertEquals("The set should only contain one item since one of the two is marked as deleted",
                1, activeSet.size());
    }


    /*
     * Test addFlashcardToFlashcardSet()
     */
    @Test
    public void testAddFlashcardToExistingFlashcardSet() {
        FlashcardSet flashcardSet = new FlashcardSet("testUser","Test Set");
        String flashcardSetUUID = flashcardSet.getUUID();

        Flashcard flashcard = new Flashcard(flashcardSetUUID, "Test Question", "Test Answer", "Test Hint");

        flashcardSetManager.insertFlashcardSet(flashcardSet);
        assertTrue(flashcardSetManager.addFlashcardToFlashcardSet(flashcardSetUUID, flashcard));

        assertEquals("You can add a flashcard to a flashcard set from the flashcardSetManager",
                1, flashcardSetManager.getFlashcardSet(flashcardSetUUID).getActiveFlashcards().size());
    }

    @Test
    public void testAddFlashcardToNonExistingFlashcardSet() {
        FlashcardSet flashcardSet = new FlashcardSet("testUser", "Test Set");
        Flashcard flashcard = new Flashcard(flashcardSet.getUUID(), "Test Question 1", "Test Answer 2", null);

        assertFalse("You cannot add a flashcard to flashcard set via the flashcardSetManager if the flashcard set is not managed",
                flashcardSetManager.addFlashcardToFlashcardSet(flashcardSet.getUUID(), flashcard));
    }

    /*
     * Test getAllFlashcardSets
     */
    @Test
    public void testGetAllFlashcardSets() {
        FlashcardSet flashcardSet1 = new FlashcardSet("testUser1", "testSet1");
        FlashcardSet flashcardSet2 = new FlashcardSet("testUser2", "testSet2");
        FlashcardSet flashcardSet3 = new FlashcardSet("testUser3", "testSet3");

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
