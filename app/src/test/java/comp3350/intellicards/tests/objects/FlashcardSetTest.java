package comp3350.intellicards.tests.objects;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

import java.util.UUID;

import comp3350.intellicards.Objects.Flashcard;
import comp3350.intellicards.Objects.FlashcardSet;

public class FlashcardSetTest {

    private FlashcardSet testCardSet;

    @Before
    public void setUp() {
        testCardSet = new FlashcardSet("TestUUID", "TestUser", "TestSet");
    }

    /*
     * test Constructor
     */
    @Test
    public void testAssignUUID() {
        testCardSet = new FlashcardSet("TestUser", "TestSet");
        assertNotNull("FlashcardSet's UUID should be assigned automatically after construction when using reduced constructor",
                testCardSet.getUUID());
    }

    /*
     * Test getUUID()
     */
    @Test
    public void testGetUUID() {
        assertEquals("FlashcardSet's UUID can be retrieved",
                "TestUUID", testCardSet.getUUID());
    }

    /*
     * Test getFlashcardSetName()
     */
    @Test
    public void testGetUsername() {
        assertEquals("FlashcardSet's username of owner can be retrieved",
                "TestUser", testCardSet.getUsername());
    }

    /*
     * Test getFlashcardSetName()
     */
    @Test
    public void testGetFlashcardSetName() {
        assertEquals("FlashcardSet's name can be retrieved",
                "TestSet", testCardSet.getFlashcardSetName());
    }

    /*
     * Test addFlashcard() and size()
     */
    @Test
    public void testAddFlashcard() {
        Flashcard flashcard = new Flashcard(testCardSet.getUUID(),"Generic Answer", "Generic Question", "Generic Hint");
        testCardSet.addFlashcard(flashcard);

        assertEquals("FlashcardSet can accept new flashcards",
                flashcard, testCardSet.getIndex(0));
    }

    @Test
    public void testAddFlashcardWithData() {
        Flashcard flashcard = new Flashcard(testCardSet.getUUID(), "Generic Answer", "Generic Question", "Generic Hint");
        flashcard.setQuestion("Magic 8 Ball, What is the meaning of life?");
        flashcard.setAnswer("Probably");
        testCardSet.addFlashcard(flashcard);
        Flashcard flashcardGet = testCardSet.getIndex(0);

        assertEquals("FlashcardSet can accept new flashcards with data",
                flashcard.getQuestion(), flashcardGet.getQuestion());
    }

    /*
     * Test getActiveFlashcards()
     * and getActiveCount()
     */
    @Test
    public void testGetActiveFlashcardsAndGetActiveCount() {
        Flashcard flashcard1 = new Flashcard(testCardSet.getUUID(), "Less Generic Answer", "Less Generic Question", null);
        Flashcard flashcard2 = new Flashcard(testCardSet.getUUID(), "Even Less Generic Answer", "Even Less Generic Question", "Need Hint");
        testCardSet.addFlashcard(flashcard1);
        testCardSet.addFlashcard(flashcard2);

        assertEquals("FlashcardSet can retrieve a subset containing all active flashcards in the set",
                flashcard1, testCardSet.getActiveFlashcards().getIndex(0));

        assertEquals("FlashcardSet can retrieve a subset containing all active flashcards in the set",
                flashcard2, testCardSet.getActiveFlashcards().getIndex(1));

        assertEquals("FlashcardSet can retrieve the amount of active flashcards in the set",
                2, testCardSet.getActiveCount());
    }

    @Test
    public void testGetActiveFlashcardsDoesNotReturnDeleted() {
        Flashcard flashcard1 = new Flashcard(testCardSet.getUUID(), "Less Generic Answer", "Less Generic Question", null);
        Flashcard flashcard2 = new Flashcard(testCardSet.getUUID(), "Even Less Generic Answer", "Even Less Generic Question", "Need Hint");
        testCardSet.addFlashcard(flashcard1);
        testCardSet.addFlashcard(flashcard2);

        flashcard2.markDeleted();
        Flashcard activeCard = testCardSet.getActiveFlashcards().getIndex(0);

        assertEquals("FlashcardSet cannot contain any deleted cards in the subset of active cards",
                flashcard1, activeCard);

        assertEquals("FlashcardSet cannot count any deleted cards in its active count",
                1, testCardSet.getActiveCount());
    }

    /*
     * Test getDeletedFlashcards()
     */
    @Test
    public void getDeletedFlashcards() {
        Flashcard flashcard1 = new Flashcard(testCardSet.getUUID(), "Less Generic Answer", "Less Generic Question", null);
        Flashcard flashcard2 = new Flashcard(testCardSet.getUUID(), "Even Less Generic Answer", "Even Less Generic Question", "Need Hint");

        flashcard1.markDeleted();
        testCardSet.addFlashcard(flashcard1);
        testCardSet.addFlashcard(flashcard2);
        flashcard2.markDeleted();

        FlashcardSet deletedSet = testCardSet.getDeletedFlashcards();

        assertEquals("FlashcardSet can retrieve a subset of itself that only contains deleted flashcards",
                2, deletedSet.size());
    }

    @Test
    public void testGetDeletedFlashcardsDoesNotReturnActive() {
        Flashcard flashcard1 = new Flashcard(testCardSet.getUUID(), "Less Generic Answer", "Less Generic Question", null);
        Flashcard flashcard2 = new Flashcard(testCardSet.getUUID(), "Even Less Generic Answer", "Even Less Generic Question", "Need Hint");

        flashcard1.markDeleted();
        testCardSet.addFlashcard(flashcard1);
        testCardSet.addFlashcard(flashcard2);

        FlashcardSet deletedSet = testCardSet.getDeletedFlashcards();

        assertEquals("FlashcardSet cannot have any active cards in its deleted count",
                flashcard1, deletedSet.getIndex(0));

        assertEquals("FlashcardSet cannot count any deleted cards in its active count",
                1, deletedSet.getDeletedCount());
    }

    /*
     * Test getFlashcardById()
     */
    @Test
    public void testGetFlashcardById() {
        Flashcard flashcard1 = new Flashcard(testCardSet.getUUID(), "Less Generic Answer", "Less Generic Question", null);
        Flashcard flashcard2 = new Flashcard(testCardSet.getUUID(), "Even Less Generic Answer", "Even Less Generic Question", "Need Hint");
        testCardSet.addFlashcard(flashcard1);
        testCardSet.addFlashcard(flashcard2);
        String uuid = flashcard2.getUUID();

        assertEquals("FlashcardSet can retrieve a flashcard contained in the set if given its UUID",
                flashcard2, testCardSet.getFlashcardById(uuid));
    }

    @Test
    public void testGetFlashcardByIdCardIsNotInSet() {
        Flashcard flashcard1 = new Flashcard(testCardSet.getUUID(), "Less Generic Answer", "Less Generic Question", null);
        Flashcard flashcard2 = new Flashcard(testCardSet.getUUID(), "Even Less Generic Answer", "Even Less Generic Question", "Need Hint");
        testCardSet.addFlashcard(flashcard1);
        testCardSet.addFlashcard(flashcard2);
        String uuid = UUID.randomUUID().toString();

        assertNull("FlashcardSet cannot retrieve a flashcard that is not added to the set",
                testCardSet.getFlashcardById(uuid));
    }

    @Test
    public void testGetFlashcardByIdWhereNoCardsInSet() {
        String uuid = UUID.randomUUID().toString();

        assertNull("FlashcardSet cannot retrieve a flashcard that does not exist",
                testCardSet.getFlashcardById(uuid));
    }

    /*
     * Test randomizeSet()
     */
    @Test
    public void testRandomizeSet() {
        Flashcard flashcard1 = new Flashcard(testCardSet.getUUID(), "Less Generic Question", "Less Generic Answer", null);
        Flashcard flashcard2 = new Flashcard(testCardSet.getUUID(), "Even Less Generic Question", "Even Less Generic Answer", "Need Hint");
        Flashcard flashcard3 = new Flashcard(testCardSet.getUUID(), "Question", "Answer", null);
        Flashcard flashcard4 = new Flashcard(testCardSet.getUUID(), "Generic Question", "Generic Answer", null);

        testCardSet.addFlashcard(flashcard1);
        testCardSet.addFlashcard(flashcard2);
        testCardSet.addFlashcard(flashcard3);
        testCardSet.addFlashcard(flashcard4);

        FlashcardSet randomizedCardSet = new FlashcardSet("TestUUIDRandom", "TestUsernameRandom", "TestNameRandom");
        randomizedCardSet.addFlashcard(flashcard1);
        randomizedCardSet.addFlashcard(flashcard2);
        randomizedCardSet.addFlashcard(flashcard3);
        randomizedCardSet.addFlashcard(flashcard4);
        randomizedCardSet.randomizeSet();

        boolean testResult = true;
        for (int i = 0; i < testCardSet.size() && testResult; i++) {
            if (testCardSet.getIndex(i) != randomizedCardSet.getIndex(i)) {
                testResult = false;
            }
        }

        assertFalse("FlashcardSet can be shuffled:\n" +
                "\tPlease note that this test may fail due to the random nature of the method." +
                "\tIf it does fail, re-run it a few times before determining that this method is not working as intended",
                testResult);
    }

    @Test
    public void testRandomizeNullSet() {
        try {
            testCardSet.randomizeSet();
        } catch (Exception e) {
            fail("Randomizing an empty set should not result in an exception");
        }
    }

    /*
     * Test toString()
     */
    @Test
    public void testToString() {
        Flashcard flashcard = new Flashcard(testCardSet.getUUID(),"Generic Answer", "Generic Question", "Generic Hint");
        testCardSet.addFlashcard(flashcard);
        assertEquals("FlashcardSet can be converted to string",
                "FlashcardSet{uuid=TestUUID, flashcardSetName='TestSet', flashcards=[" + flashcard + "]}", testCardSet.toString());
    }

    @Test
    public void testToStringEmptyFlashcards() {
        assertEquals("FlashcardSet can be converted to string with an empty set",
                "FlashcardSet{uuid=TestUUID, flashcardSetName='TestSet', flashcards=[]}", testCardSet.toString());
    }
}

