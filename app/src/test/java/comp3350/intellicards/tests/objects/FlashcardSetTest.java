package comp3350.intellicards.tests.objects;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

import comp3350.intellicards.Objects.FlashCard;
import comp3350.intellicards.Objects.FlashCardSet;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class FlashcardSetTest {

    private FlashCardSet cardSet;

    @Before
    public void setUp(){
        cardSet = new FlashCardSet();
    }
    @Test
    public void testGetFlashCardSetUuid() {
        assertNotNull(cardSet.getUuid());
    }

    @Test
    public void testAddFlashCard() {
        FlashCard flashCard = new FlashCard();
        cardSet.addFlashCard(flashCard);
        assertEquals(1, cardSet.size());
        assertEquals(flashCard, cardSet.getIndex(0));
    }

    @Test
    public void testGetFlashcards() {
        FlashCard flashCard1 = new FlashCard();
        FlashCard flashCard2 = new FlashCard();
        cardSet.addFlashCard(flashCard1);
        cardSet.addFlashCard(flashCard2);
        assertEquals(2, cardSet.getFlashcards().size());
    }

    @Test
    public void testGetDeletedFlashCards() {
        FlashCard flashCard1 = new FlashCard();
        FlashCard flashCard2 = new FlashCard();
        flashCard2.markDeleted();
        cardSet.addFlashCard(flashCard1);
        cardSet.addFlashCard(flashCard2);
        assertEquals(1, cardSet.getDeletedFlashCards().size());
        assertEquals(flashCard2, cardSet.getDeletedFlashCards().getIndex(0));
        assertEquals(1, cardSet.getFlashcards().size());
    }


    @Test
    public void testGetFlashCardById() {
        FlashCard flashCard1 = new FlashCard();
        FlashCard flashCard2 = new FlashCard();
        cardSet.addFlashCard(flashCard1);
        cardSet.addFlashCard(flashCard2);
        String uuid = flashCard2.getUuid();
        assertEquals(flashCard2, cardSet.getFlashCardById(uuid));
    }




    @After
    public void tearDown(){

    }


}

