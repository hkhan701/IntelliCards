package comp3350.intellicards.tests.objects;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

import java.util.UUID;

import comp3350.intellicards.Objects.Flashcard;
import comp3350.intellicards.Objects.FlashcardSet;

public class FlashcardSetTest {

    private FlashcardSet cardSet;

    @Before
    public void setUp() {
        cardSet = new FlashcardSet();
    }

    /*
     * Constructor Tests
     */
    @Test
    public void testSetConstructorWithName() {
        String setName = "COMP3350";
        FlashcardSet namedCardSet = new FlashcardSet(setName);

        assertEquals("Adding string to constructor parameters should change the value of flashcardSetName",
                setName, namedCardSet.getFlashcardSetName());
    }

    /*
     * Test getUUID()
     */
    @Test
    public void testGetFlashCardSetUuid() {
        assertNotNull("UUID should be assigned automatically after construction",
                cardSet.getUUID());
    }

    /*
     * Test addFlashCard()
     */
    @Test
    public void testAddFlashCard() {
        Flashcard flashcard = new Flashcard("Generic Answer", "Generic Question", "Generic Hint");
        cardSet.addFlashCard(flashcard);

        assertEquals("There should be one flashcard in the set",
                1, cardSet.size());
        assertEquals("The flashcard should be added into the flashcards list",
                flashcard, cardSet.getIndex(0));
    }

    @Test
    public void testAddFlashcardWithData() {
        Flashcard flashcard = new Flashcard("Generic Answer", "Generic Question", "Generic Hint");
        flashcard.setQuestion("Magic 8 Ball, What is the meaning of life?");
        flashcard.setAnswer("Probably");
        cardSet.addFlashCard(flashcard);
        Flashcard flashcardGet = cardSet.getIndex(0);

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
        Flashcard flashcard1 = new Flashcard("Less Generic Answer", "Less Generic Question", null);
        Flashcard flashcard2 = new Flashcard("Even Less Generic Answer", "Even Less Generic Question", "Need Hint");
        cardSet.addFlashCard(flashcard1);
        cardSet.addFlashCard(flashcard2);

        assertEquals("The first card in the set should be the card that was added in first",
                flashcard1, cardSet.getActiveFlashcards().getIndex(0));

        assertEquals("The second card in the set should be the card that was added in second",
                flashcard2, cardSet.getActiveFlashcards().getIndex(1));

        assertEquals("The list of active flashcards should return the amount of flashcards added",
                2, cardSet.getActiveCount());
    }

    @Test
    public void testGetActiveFlashcardsDoesNotReturnDeleted() {
        Flashcard flashcard1 = new Flashcard("Less Generic Answer", "Less Generic Question", null);
        Flashcard flashcard2 = new Flashcard("Even Less Generic Answer", "Even Less Generic Question", "Need Hint");
        cardSet.addFlashCard(flashcard1);
        cardSet.addFlashCard(flashcard2);

        flashcard2.markDeleted();
        Flashcard activeCard = cardSet.getActiveFlashcards().getIndex(0);

        assertEquals("If the card wasn't marked deleted, it should stay active in the set",
                flashcard1, activeCard);

        assertEquals("A deleted flashcard should be removed from the active flashcard list",
                1, cardSet.getActiveCount());
    }

    /*
     * Test getFlashcardById()
     */
    @Test
    public void testGetFlashCardById() {
        Flashcard flashcard1 = new Flashcard("Less Generic Answer", "Less Generic Question", null);
        Flashcard flashcard2 = new Flashcard("Even Less Generic Answer", "Even Less Generic Question", "Need Hint");
        cardSet.addFlashCard(flashcard1);
        cardSet.addFlashCard(flashcard2);
        String uuid = flashcard2.getUUID();

        assertEquals("Given a card's UUID, we can find it in the set",
                flashcard2, cardSet.getFlashCardById(uuid));
    }

    @Test
    public void testGetFlashCardByIdCardIsNotInSet() {
        Flashcard flashcard1 = new Flashcard("Less Generic Answer", "Less Generic Question", null);
        Flashcard flashcard2 = new Flashcard("Even Less Generic Answer", "Even Less Generic Question", "Need Hint");
        cardSet.addFlashCard(flashcard1);
        cardSet.addFlashCard(flashcard2);
        String uuid = UUID.randomUUID().toString();

        assertNull("The get flashcard by ID method will return null if the card is not in the set",
                cardSet.getFlashCardById(uuid));
    }

    @Test
    public void testGetFlashcardByIdWhereNoCardsInSet() {
        String uuid = UUID.randomUUID().toString();

        assertNull("Trying to get a card from a set with no flashcards will return null",
                cardSet.getFlashCardById(uuid));
    }

    @After
    public void tearDown() {

    }


}

