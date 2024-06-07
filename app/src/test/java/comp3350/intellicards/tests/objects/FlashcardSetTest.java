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
    public void setUp(){
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
     */
    @Test
    public void testGetActiveFlashcards() {
        Flashcard flashcard1 = new Flashcard("Less Generic Answer", "Less Generic Question", null);
        Flashcard flashcard2 = new Flashcard("Even Less Generic Answer", "Even Less Generic Question", "Need Hint");
        cardSet.addFlashCard(flashcard1);
        cardSet.addFlashCard(flashcard2);

        assertEquals("The list of active flashcards should return the amount of flashcards added",
                2, cardSet.getActiveFlashcards().size());
    }

    @Test
    public void testGetActiveFlashcardsDoesNotReturnDeleted() {
        Flashcard flashcard1 = new Flashcard("Less Generic Answer", "Less Generic Question", null);
        Flashcard flashcard2 = new Flashcard("Even Less Generic Answer", "Even Less Generic Question", "Need Hint");
        cardSet.addFlashCard(flashcard1);
        cardSet.addFlashCard(flashcard2);

        flashcard2.markDeleted();
        Flashcard activeCard = cardSet.getActiveFlashcards().getIndex(0);

        assertEquals("A deleted flashcard should be removed from the active flashcard list",
                1, cardSet.getActiveFlashcards().size());

        assertEquals("The active set should only contain the active card",
                activeCard, flashcard1);
    }

    /*
     * Test getDeletedFlashCards()
     */
    @Test
    public void testGetDeletedFlashCards() {
        Flashcard flashcard1 = new Flashcard("Less Generic Answer", "Less Generic Question", null);
        Flashcard flashcard2 = new Flashcard("Even Less Generic Answer", "Even Less Generic Question", "Need Hint");
        flashcard2.markDeleted();
        cardSet.addFlashCard(flashcard1);
        cardSet.addFlashCard(flashcard2);

        assertEquals("There should be one deleted card",
                1, cardSet.getDeletedFlashCards().size());
        assertEquals("A card marked as deleted will be in the list of deleted cards",
                flashcard2, cardSet.getDeletedFlashCards().getIndex(0));
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

    /*
     * Test toString()
     */
    @Test
    public void testToString() {
        Flashcard flashcard1 = new Flashcard("Less Generic Answer", "Less Generic Question", null);
        Flashcard flashcard2 = new Flashcard("Even Less Generic Answer", "Even Less Generic Question", "Need Hint");
        cardSet.addFlashCard(flashcard1);
        cardSet.addFlashCard(flashcard2);

        String expectedString = "FlashCardSet{uuid=" + cardSet.getUUID() + ", flashcardSetName='null', flashcards=[uuid='" + flashcard1.getUUID() + "'\n" +
                ", question='Less Generic Question'" + "\n" +
                ", answer='Less Generic Answer'" + "\n" +
                ", uuid='" + flashcard2.getUUID() + "'\n" +
                ", question='Even Less Generic Question'" + "\n" +
                ", answer='Even Less Generic Answer'" + "\n" +
                ", hint = 'Need Hint'" + "\n" +
                "]}";

        assertEquals("Using the toString() method on a flashcard set will give info on the set and the cards within",
                expectedString, cardSet.toString());
    }

    @After
    public void tearDown(){

    }


}

