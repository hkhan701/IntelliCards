package comp3350.intellicards.tests.objects;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

import java.util.UUID;

import comp3350.intellicards.Objects.Flashcard;
import comp3350.intellicards.Objects.FlashcardSet;

import comp3350.intellicards.Objects.User;

public class FlashcardSetTest {

    private FlashcardSet testCardSet;

    @Before
    public void setUp() {
        testCardSet = new FlashcardSet("TestUser", "TestSet");
    }

    /*
     * Test getUUID()
     */
    @Test
    public void testGetFlashcardSetUuid() {
        assertNotNull("UUID should be assigned automatically after construction",
                testCardSet.getUUID());
    }

    /*
     * Test addFlashcard()
     */
    @Test
    public void testAddFlashcard() {
        Flashcard flashcard = new Flashcard(testCardSet.getUUID(),"Generic Answer", "Generic Question", "Generic Hint");
        testCardSet.addFlashcard(flashcard);

        assertEquals("There should be one flashcard in the set",
                1, testCardSet.size());
        assertEquals("The flashcard should be added into the flashcards list",
                flashcard, testCardSet.getIndex(0));
    }

    @Test
    public void testAddFlashcardWithData() {
        Flashcard flashcard = new Flashcard(testCardSet.getUUID(), "Generic Answer", "Generic Question", "Generic Hint");
        flashcard.setQuestion("Magic 8 Ball, What is the meaning of life?");
        flashcard.setAnswer("Probably");
        testCardSet.addFlashcard(flashcard);
        Flashcard flashcardGet = testCardSet.getIndex(0);

        assertEquals("The question on the flashcard should not be overwritten when added to a set",
                flashcard.getQuestion(), flashcardGet.getQuestion());
        assertEquals("The answer on the flashcard should not be overwritten when added to a set",
                flashcard.getAnswer(), flashcardGet.getAnswer());
    }

    /*
     * Test getActiveFlashcards()
     * and getActiveCount()
     */
    @Test
    public void testGetActiveFlashcards() {
        Flashcard flashcard1 = new Flashcard(testCardSet.getUUID(), "Less Generic Answer", "Less Generic Question", null);
        Flashcard flashcard2 = new Flashcard(testCardSet.getUUID(), "Even Less Generic Answer", "Even Less Generic Question", "Need Hint");
        testCardSet.addFlashcard(flashcard1);
        testCardSet.addFlashcard(flashcard2);

        assertEquals("The first card in the set should be the card that was added in first",
                flashcard1, testCardSet.getActiveFlashcards().getIndex(0));

        assertEquals("The second card in the set should be the card that was added in second",
                flashcard2, testCardSet.getActiveFlashcards().getIndex(1));

        assertEquals("The list of active flashcards should return the amount of flashcards added",
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

        assertEquals("If the card wasn't marked deleted, it should stay active in the set",
                flashcard1, activeCard);

        assertEquals("A deleted flashcard should be removed from the active flashcard list",
                1, testCardSet.getActiveCount());
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

        assertEquals("Given a card's UUID, we can find it in the set",
                flashcard2, testCardSet.getFlashcardById(uuid));
    }

    @Test
    public void testGetFlashcardByIdCardIsNotInSet() {
        Flashcard flashcard1 = new Flashcard(testCardSet.getUUID(), "Less Generic Answer", "Less Generic Question", null);
        Flashcard flashcard2 = new Flashcard(testCardSet.getUUID(), "Even Less Generic Answer", "Even Less Generic Question", "Need Hint");
        testCardSet.addFlashcard(flashcard1);
        testCardSet.addFlashcard(flashcard2);
        String uuid = UUID.randomUUID().toString();

        assertNull("The get flashcard by ID method will return null if the card is not in the set",
                testCardSet.getFlashcardById(uuid));
    }

    @Test
    public void testGetFlashcardByIdWhereNoCardsInSet() {
        String uuid = UUID.randomUUID().toString();

        assertNull("Trying to get a card from a set with no flashcards will return null",
                testCardSet.getFlashcardById(uuid));
    }

    @After
    public void tearDown() {

    }


}

