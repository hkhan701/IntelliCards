package comp3350.intellicards.tests.business;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.Before;

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
     * Test insertFlashcardSet()
     * and getFlashcardSet()
     */
    @Test
    public void testGetFlashcardSet() {
        FlashcardSet flashcardSet = new FlashcardSet("Test User", "Test Set");
        String flashcardUUID = flashcardSet.getUUID();

        flashcardSetManager.insertFlashcardSet(flashcardSet);

        assertEquals("FlashcardSetManager can retrieve managed flashcardSet given its UUID",
                flashcardSet, flashcardSetManager.getFlashcardSet(flashcardUUID));
    }

    @Test
    public void testGetFlashcardSetNotManaged() {
        assertNull("FlashcardSetManager cannot retrieve a non managed set",
                flashcardSetManager.getFlashcardSet("TestUUID"));
    }

    @Test
    public void testGetFlashcardSetNull() {
        assertNull("FlashcardSetManager cannot retrieve a set if given a null",
                flashcardSetManager.getFlashcardSet(null));
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

        assertEquals("FlashcardSetManager can retrieve active cards from managed set",
                flashcard1, activeSet.getIndex(0));
    }

    @Test
    public void testGetActiveFlashcardSetNotManaged() {
        assertNull("FlashcardSetManager cannot retrieve active cards from non-managed set",
                flashcardSetManager.getActiveFlashcardSet("TestSetNotExist"));
    }

    /*
     * Test getAllDeletedFlashcards()
     */
    @Test
    public void testGetAllDeletedFlashcards() {
        FlashcardSet flashcardSet = new FlashcardSet("testUser", "Test Set");
        Flashcard flashcard1 = new Flashcard(flashcardSet.getUUID(), "Test Question 1", "Test Answer 1", "Test Hint 1");
        Flashcard flashcard2 = new Flashcard(flashcardSet.getUUID(), "Test Question 2", "Test Answer 2", "Test Hint 2");

        flashcardSetManager.insertFlashcardSet(flashcardSet);
        flashcardSetManager.addFlashcardToFlashcardSet(flashcardSet.getUUID(), flashcard1);
        flashcardSetManager.addFlashcardToFlashcardSet(flashcardSet.getUUID(), flashcard2);

        flashcard2.markDeleted();

        List<Flashcard> deletedSet = flashcardSetManager.getAllDeletedFlashcards("testUser");

        assertEquals("FlashcardSetManager can retrieve all deleted cards for single user",
                flashcard2, deletedSet.get(0));
    }

    @Test
    public void testGetAllDeletedFlashcardsNoDeleted() {
        FlashcardSet flashcardSet = new FlashcardSet("testUser", "Test Set");
        Flashcard flashcard1 = new Flashcard(flashcardSet.getUUID(), "Test Question 1", "Test Answer 1", "Test Hint 1");
        Flashcard flashcard2 = new Flashcard(flashcardSet.getUUID(), "Test Question 2", "Test Answer 2", "Test Hint 2");

        flashcardSetManager.insertFlashcardSet(flashcardSet);
        flashcardSetManager.addFlashcardToFlashcardSet(flashcardSet.getUUID(), flashcard1);
        flashcardSetManager.addFlashcardToFlashcardSet(flashcardSet.getUUID(), flashcard2);

        List<Flashcard> deletedSet = flashcardSetManager.getAllDeletedFlashcards("testUser");

        assertEquals("FlashcardSetManager cannot retrieve all deleted cards for a single user because there are none",
                0, deletedSet.size());
    }

    @Test
    public void testAllDeletedFlashcardsOtherUser() {
        FlashcardSet flashcardSetNeed = new FlashcardSet("testUser1", "Test Set");
        FlashcardSet flashcardSetOther = new FlashcardSet("testUser2", "Test Set");
        Flashcard flashcard1 = new Flashcard(flashcardSetNeed.getUUID(), "Test Question 1", "Test Answer 1", "Test Hint 1");
        Flashcard flashcard2 = new Flashcard(flashcardSetOther.getUUID(), "Test Question 2", "Test Answer 2", "Test Hint 2");

        flashcardSetManager.insertFlashcardSet(flashcardSetNeed);
        flashcardSetManager.insertFlashcardSet(flashcardSetOther);
        flashcardSetManager.addFlashcardToFlashcardSet(flashcardSetNeed.getUUID(), flashcard1);
        flashcardSetManager.addFlashcardToFlashcardSet(flashcardSetOther.getUUID(), flashcard2);

        flashcard1.markDeleted();
        flashcard2.markDeleted();

        List<Flashcard> deletedSet = flashcardSetManager.getAllDeletedFlashcards("testUser1");
        assertEquals("FlashcardSetManager cannot retrieve the deleted cards of a different user",
                flashcard1, deletedSet.get(0));
    }

    @Test
    public void testGetDeletedFlashcardSetNotManaged() {
        assertEquals("If the flashcardSetManager receives a user that is not managed, there should be nothing returned",
                0, flashcardSetManager.getAllDeletedFlashcards("TestUser").size());
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

        assertTrue("FlashcardSetManager verifies that the flashcard was added to the set with a return value",
                flashcardSetManager.addFlashcardToFlashcardSet(flashcardSetUUID, flashcard));

        assertEquals("You can add a flashcard to a flashcard set from the flashcardSetManager",
                1, flashcardSetManager.getFlashcardSet(flashcardSetUUID).getActiveFlashcards().size());
    }

    @Test
    public void testAddFlashcardToNonExistingFlashcardSet() {
        FlashcardSet flashcardSet = new FlashcardSet("testUser", "Test Set");
        Flashcard flashcard = new Flashcard(flashcardSet.getUUID(), "Test Question 1", "Test Answer 2", null);

        assertFalse("FlashcardSetManager cannot retrieve all deleted cards for single user if they have no managed sets",
                flashcardSetManager.addFlashcardToFlashcardSet(flashcardSet.getUUID(), flashcard));
    }

    /*
     * Test getAllFlashcardSets()
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

        assertNotEquals("FlashcardSetManager can retrieve all sets managed",
                -1, flashcardSets.indexOf(flashcardSet1));
        assertNotEquals("FlashcardSetManager can retrieve all sets managed",
                -1, flashcardSets.indexOf(flashcardSet2));
        assertNotEquals("FlashcardSetManager can retrieve all sets managed",
                -1, flashcardSets.indexOf(flashcardSet3));
    }

    /*
     * Test shuffleFlashcardSet()
     */
    @Test
    public void testShuffleFlashcards() {
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

        assertTrue("FlashcardSetManager can change the order of flashcards in a set:\n" +
                        "\tPlease note that this test may fail due to the random nature of the method." +
                        "\tIf it does fail, re-run it a few times before determining that this method is not working as intended",
                shuffled);
    }

    @Test
    public void testShuffleNonManagedFlashcardSet() {
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

        assertFalse("FlashcardSetManager cannot shuffle the flashcards of a non managed set",
                shuffled);
    }

    /*
     * Test getFlashcardSetsByUsername()
     */
    @Test
    public void testGetFlashcardSetsByUsername() {
        FlashcardSet flashcardSet = new FlashcardSet("TestUser", "TestSetName");
        flashcardSetManager.insertFlashcardSet(flashcardSet);

        assertEquals("FlashcardSetManager can retrieve sets based on username",
                flashcardSet, flashcardSetManager.getFlashcardSetsByUsername("TestUser").get(0));
    }

    @Test
    public void testGetFlashcardSetByUserMixed() {
        FlashcardSet flashcardSet1 = new FlashcardSet("TestUser1", "TestSetName");
        FlashcardSet flashcardSet2 = new FlashcardSet("TestUser2", "TestSetName");

        flashcardSetManager.insertFlashcardSet(flashcardSet1);
        flashcardSetManager.insertFlashcardSet(flashcardSet2);

        assertEquals("FlashcardSetManager will not retrieve other people's sets when calling getFlashcardSetsByUsername()",
                1, flashcardSetManager.getFlashcardSetsByUsername("TestUser1").size());

        assertEquals("FlashcardSetManager will not retrieve other people's sets when calling getFlashcardSetsByUsername()",
                flashcardSet1, flashcardSetManager.getFlashcardSetsByUsername("TestUser1").get(0));
    }

    @Test
    public void testGetFlashcardSetByUserNonManaged() {
        assertEquals("FlashcardSetManager will not retrieve non-managed sets from a user",
                new ArrayList<>(), flashcardSetManager.getFlashcardSetsByUsername("TestUser"));
    }

}
