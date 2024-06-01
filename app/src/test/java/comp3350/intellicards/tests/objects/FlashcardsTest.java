package comp3350.intellicards.tests.objects;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

import comp3350.intellicards.Objects.Flashcard;
import comp3350.intellicards.Objects.FlashcardSet;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class FlashcardsTest {

    private FlashcardSet cardSet;
    private Flashcard flashCard;

    @Before
    public void setUp(){
        cardSet = new FlashcardSet();
        flashCard = new Flashcard();
    }
    @Test
    public void testGetUuid() {
        assertNotNull(flashCard.getUuid());
    }

    @Test
    public void testGetAnswer() {
        assertNull(flashCard.getAnswer());
    }

    @Test
    public void testGetQuestion() {
        assertNull(flashCard.getQuestion());
    }

    @Test
    public void testSetAnswer() {
        flashCard.setAnswer("Test answer");
        assertEquals("Test answer", flashCard.getAnswer());
    }

    @Test
    public void testSetQuestion() {
        flashCard.setQuestion("Test question");
        assertEquals("Test question", flashCard.getQuestion());
    }

    @Test
    public void testIsDeleted() {
        assertFalse(flashCard.isDeleted());
    }

    @Test
    public void testMarkDeleted() {
        flashCard.markDeleted();
        assertTrue(flashCard.isDeleted());
    }

    @Test
    public void testMarkRecovered() {
        flashCard.markDeleted();
        flashCard.markRecovered();
        assertFalse(flashCard.isDeleted());
    }

    @Test
    public void testToString() {
        String expectedString = "uuid='" + flashCard.getUuid() + "'\n" +
                ", answer='Test answer'\n" +
                ", question='Test question'\n";
        flashCard.setAnswer("Test answer");
        flashCard.setQuestion("Test question");
        assertEquals(expectedString, flashCard.toString());
    }

    @Test
    public void testGetFlashCardSetUuid() {
        assertNotNull(cardSet.getUuid());
    }

    @Test
    public void testAddFlashCard() {
        Flashcard flashCard = new Flashcard();
        cardSet.addFlashCard(flashCard);
        assertEquals(1, cardSet.size());
        assertEquals(flashCard, cardSet.getIndex(0));
    }

    @Test
    public void testGetFlashcards() {
        Flashcard flashcard1 = new Flashcard();
        Flashcard flashcard2 = new Flashcard();
        cardSet.addFlashCard(flashcard1);
        cardSet.addFlashCard(flashcard2);
        assertEquals(2, cardSet.getFlashcards().size());
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
        assertEquals(1, cardSet.getFlashcards().size());
    }


    @Test
    public void testGetFlashCardById() {
        Flashcard flashcard1 = new Flashcard();
        Flashcard flashcard2 = new Flashcard();
        cardSet.addFlashCard(flashcard1);
        cardSet.addFlashCard(flashcard2);
        String uuid = flashcard2.getUuid();
        assertEquals(flashcard2, cardSet.getFlashCardById(uuid));
    }




    @After
    public void tearDown(){

    }


}
