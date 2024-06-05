package comp3350.intellicards.tests.objects;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

import java.util.UUID;

import comp3350.intellicards.Objects.Flashcard;
import comp3350.intellicards.Objects.FlashcardSet;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class FlashcardSetTest {

    private FlashcardSet cardSet;

    @Before
    public void setUp(){
        cardSet = new FlashcardSet();
    }

    @Test
    public void testSetConstructorWithName() {
        String setName = "COMP3350";
        FlashcardSet namedCardSet = new FlashcardSet(setName);
        assertEquals(setName, namedCardSet.getFlashCardSetName());
    }

    @Test
    public void testGetFlashCardSetUuid() {
        assertNotNull(cardSet.getUUID());
    }

    @Test
    public void testAddFlashCard() {
        Flashcard flashcard = new Flashcard();
        cardSet.addFlashCard(flashcard);
        assertEquals(1, cardSet.size());
        assertEquals(flashcard, cardSet.getIndex(0));
    }

    @Test
    public void testAddFlashCardGivenNull() {
        // A null should not be added to the set
        cardSet.addFlashCard(null);
        assertEquals(0, cardSet.size());
    }

    @Test
    public void testAddFlashcardWithData() {
        Flashcard flashcard = new Flashcard();
        flashcard.setQuestion("Magic 8 Ball, What is the meaning of life?");
        flashcard.setAnswer("Probably");

        cardSet.addFlashCard(flashcard);
        Flashcard flashcardGet = cardSet.getIndex(0);

        assertEquals(flashcard.getQuestion(), flashcardGet.getQuestion());
        assertEquals(flashcard.getAnswer(), flashcardGet.getAnswer());
    }

    @Test
    public void testGetActiveFlashcards() {
        Flashcard flashcard1 = new Flashcard();
        Flashcard flashcard2 = new Flashcard();
        cardSet.addFlashCard(flashcard1);
        cardSet.addFlashCard(flashcard2);
        assertEquals(2, cardSet.getActiveFlashcards().size());
    }

    @Test
    public void testGetActiveFlashcardsDoesNotReturnDeleted() {
        Flashcard flashcard1 = new Flashcard();
        Flashcard flashcard2 = new Flashcard();
        cardSet.addFlashCard(flashcard1);
        cardSet.addFlashCard(flashcard2);
        flashcard2.markDeleted();

        assertEquals(1, cardSet.getActiveFlashcards().size());

        Flashcard activeCard = cardSet.getActiveFlashcards().getIndex(0);
        assertEquals(activeCard, flashcard1);
    }

    @Test
    public void testGetDeletedFlashCards() {
        Flashcard flashcard1 = new Flashcard();
        Flashcard flashcard2 = new Flashcard();
        flashcard2.markDeleted();
        cardSet.addFlashCard(flashcard1);
        cardSet.addFlashCard(flashcard2);
        assertEquals(1, cardSet.getDeletedFlashCards().size());
        assertEquals(flashcard2, cardSet.getDeletedFlashCards().getIndex(0));
    }


    @Test
    public void testGetFlashCardById() {
        Flashcard flashcard1 = new Flashcard();
        Flashcard flashcard2 = new Flashcard();
        cardSet.addFlashCard(flashcard1);
        cardSet.addFlashCard(flashcard2);
        UUID uuid = flashcard2.getUUID();
        assertEquals(flashcard2, cardSet.getFlashCardById(uuid));
    }

    @Test
    public void testGetFlashCardByIdCardIsNotInSet() {
        Flashcard flashcard1 = new Flashcard();
        Flashcard flashcard2 = new Flashcard();
        cardSet.addFlashCard(flashcard1);
        cardSet.addFlashCard(flashcard2);
        UUID uuid = UUID.randomUUID();
        assertNull(cardSet.getFlashCardById(uuid));
    }
    @Test
    public void testGetFlashcardByIdWhereNoCardsInSet() {
        UUID uuid = UUID.randomUUID();
        assertEquals(null, cardSet.getFlashCardById(uuid));
    }

    @Test
    public void testToString() {
        Flashcard flashcard1 = new Flashcard();
        Flashcard flashcard2 = new Flashcard();
        cardSet.addFlashCard(flashcard1);
        cardSet.addFlashCard(flashcard2);

        String expectedString = "FlashCardSet{uuid=" + cardSet.getUUID() + ", flashcardSetName='null', flashcards=[uuid='" + flashcard1.getUUID() + "'\n" +
                ", question='No question set'" + "\n" +
                ", answer='No answer set'" + "\n" +
                ", hint = 'No hint set'" + "\n" +
                ", uuid='" + flashcard2.getUUID() + "'\n" +
                ", question='No question set'" + "\n" +
                ", answer='No answer set'" + "\n" +
                ", hint = 'No hint set'" + "\n" +
                "]}";
        assertEquals(expectedString, cardSet.toString());
    }


    @After
    public void tearDown(){

    }


}

