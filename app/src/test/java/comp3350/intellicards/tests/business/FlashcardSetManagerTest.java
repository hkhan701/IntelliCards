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
     * Test getActiveFlashcardSet()
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
        assertEquals("The active flashcard should be in the active set",
                flashcard1, activeSet.getIndex(0));
    }

    @Test
    public void testGetActiveFlashcardSetNotManaged() {
        assertNull("If the flashcardSetManager receives a set that is not managed, there should be nothing returned",
                flashcardSetManager.getActiveFlashcardSet("TestSetNotExist"));
    }

    /*
     * Test getDeletedFlashcardSet()
     */

//    @Test
//    public void testGetDeletedFlashcardSet() {
//        FlashcardSet flashcardSet = new FlashcardSet("testUser", "Test Set");
//        Flashcard flashcard1 = new Flashcard(flashcardSet.getUUID(), "Test Question 1", "Test Answer 1", "Test Hint 1");
//        Flashcard flashcard2 = new Flashcard(flashcardSet.getUUID(), "Test Question 2", "Test Answer 2", "Test Hint 2");
//
//        flashcardSetManager.insertFlashcardSet(flashcardSet);
//        flashcardSetManager.addFlashcardToFlashcardSet(flashcardSet.getUUID(), flashcard1);
//        flashcardSetManager.addFlashcardToFlashcardSet(flashcardSet.getUUID(), flashcard2);
//
//        flashcard2.markDeleted();
//
//        FlashcardSet deletedSet = flashcardSetManager.getDeletedFlashcardSet(flashcardSet.getUUID());
//
//        assertEquals("The set should only contain one item since one of the two is marked as deleted",
//                1, deletedSet.size());
//        assertEquals("The deleted flashcard should be in the deleted set",
//                flashcard2, deletedSet.getIndex(0));
//    }

//    @Test
//    public void testGetDeletedFlashcardSetNotManaged() {
//        assertNull("If the flashcardSetManager received a set that is not managed, there should be nothing returned",
//                flashcardSetManager.getDeletedFlashcardSet("TestSetNotExist"));
//    }

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

    /*
     * Test shuffleFlashcardSet()
     */

    @Test
    public void shuffleFlashcards() {
        FlashcardSet flashcardSet = new FlashcardSet("TestUser", "TestSetName");

        Flashcard flashcard1 = new Flashcard(flashcardSet.getUUID(), "Less Generic Question", "Less Generic Answer", null);
        Flashcard flashcard2 = new Flashcard(flashcardSet.getUUID(), "Even Less Generic Question", "Even Less Generic Answer", "Need Hint");
        Flashcard flashcard3 = new Flashcard(flashcardSet.getUUID(), "Question", "Answer", null);
        Flashcard flashcard4 = new Flashcard(flashcardSet.getUUID(), "Generic Question", "Generic Answer", null);

        flashcardSet.addFlashcard(flashcard1);
        flashcardSet.addFlashcard(flashcard2);
        flashcardSet.addFlashcard(flashcard3);
        flashcardSet.addFlashcard(flashcard4);

        FlashcardSet randomizedCardSet = new FlashcardSet("TestUUIDRandom", "TestUsernameRandom", "TestNameRandom");
        randomizedCardSet.addFlashcard(flashcard1);
        randomizedCardSet.addFlashcard(flashcard2);
        randomizedCardSet.addFlashcard(flashcard3);
        randomizedCardSet.addFlashcard(flashcard4);

        flashcardSetManager.insertFlashcardSet(randomizedCardSet);
        flashcardSetManager.shuffleFlashcardSet(randomizedCardSet);

        boolean shuffled = false;
        for (int i = 0; i < flashcardSet.size() && !shuffled; i++) {
            if (flashcardSet.getIndex(i) != randomizedCardSet.getIndex(i)) {
                shuffled = true;
            }
        }

        assertTrue("Shuffling a set should change the order of cards:\n" +
                        "\tPlease note that this test may fail due to the random nature of the method." +
                        "\tIf it does fail, re-run it a few times before determining that this method is not working as intended",
                shuffled);
    }

    @Test
    public void shuffleNonManagedFlashcardSet() {
        FlashcardSet flashcardSet = new FlashcardSet("TestUser", "TestSetName");

        Flashcard flashcard1 = new Flashcard(flashcardSet.getUUID(), "Less Generic Question", "Less Generic Answer", null);
        Flashcard flashcard2 = new Flashcard(flashcardSet.getUUID(), "Even Less Generic Question", "Even Less Generic Answer", "Need Hint");
        Flashcard flashcard3 = new Flashcard(flashcardSet.getUUID(), "Question", "Answer", null);
        Flashcard flashcard4 = new Flashcard(flashcardSet.getUUID(), "Generic Question", "Generic Answer", null);

        flashcardSet.addFlashcard(flashcard1);
        flashcardSet.addFlashcard(flashcard2);
        flashcardSet.addFlashcard(flashcard3);
        flashcardSet.addFlashcard(flashcard4);

        FlashcardSet randomizedCardSet = new FlashcardSet("TestUUIDRandom", "TestUsernameRandom", "TestNameRandom");
        randomizedCardSet.addFlashcard(flashcard1);
        randomizedCardSet.addFlashcard(flashcard2);
        randomizedCardSet.addFlashcard(flashcard3);
        randomizedCardSet.addFlashcard(flashcard4);

        flashcardSetManager.shuffleFlashcardSet(randomizedCardSet);

        boolean shuffled = false;
        for (int i = 0; i < flashcardSet.size() && !shuffled; i++) {
            if (flashcardSet.getIndex(i) != randomizedCardSet.getIndex(i)) {
                shuffled = true;
            }
        }

        assertFalse("Attempting to shuffle a non-managed set should not change the order of cards",
                shuffled);
    }

    /*
     * Test getFlashcardSetsByUsername()
     */

    // user exists
    @Test
    public void getFlashcardSetsByUsername() {
        FlashcardSet flashcardSet = new FlashcardSet("TestUser", "TestSetName");
        flashcardSetManager.insertFlashcardSet(flashcardSet);

        assertEquals("FlashcardManager can retrieve sets based on username",
                flashcardSet, flashcardSetManager.getFlashcardSetsByUsername("TestUser").get(0));
    }

    @Test
    public void getFlashcardSetByUserMixed() {
        FlashcardSet flashcardSet1 = new FlashcardSet("TestUser1", "TestSetName");
        FlashcardSet flashcardSet2 = new FlashcardSet("TestUser2", "TestSetName");

        flashcardSetManager.insertFlashcardSet(flashcardSet1);
        flashcardSetManager.insertFlashcardSet(flashcardSet2);

        assertEquals("FlashcardManager will not retrieve other people's sets when calling getFlashcardSetsByUsername()",
                1, flashcardSetManager.getFlashcardSetsByUsername("TestUser1").size());

        assertEquals("FlashcardManager will not retrieve other people's sets when calling getFlashcardSetsByUsername()",
                flashcardSet1, flashcardSetManager.getFlashcardSetsByUsername("TestUser1").get(0));
    }

    @Test
    public void getFlashcardSetsByUsernameNoSets() {
        try {
            flashcardSetManager.getFlashcardSetsByUsername("TestUser");
        }
        catch (Exception e) {
            fail("Attempting to retrieve a set from a flashcardSetManager with no sets should not throw an error");
        }
    }

    @After
    public void tearDown() {
    }
}
