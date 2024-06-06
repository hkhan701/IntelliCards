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
public class FlashcardTest {

    private FlashcardSet cardSet;
    private Flashcard flashcard;

    @Before
    public void setUp() {
        cardSet = new FlashcardSet();
        flashcard = new Flashcard();
    }

    @Test
    public void testGetUuid() {
        assertNotNull(flashcard.getUUID());
    }

    //
    @Test
    public void testGetAnswer() {
        assertNotNull(flashcard.getAnswer());
    }

    @Test
    public void testGetQuestion() {
        assertNotNull(flashcard.getQuestion());
    }

    @Test
    public void testSetAnswer() {
        flashcard.setAnswer("Test answer");
        assertEquals("Test answer", flashcard.getAnswer());
    }

    @Test
    public void testSetQuestion() {
        flashcard.setQuestion("Test question");
        assertEquals("Test question", flashcard.getQuestion());
    }

    @Test
    public void testIsDeleted() {
        assertFalse(flashcard.isDeleted());
    }

    @Test
    public void testMarkDeleted() {
        flashcard.markDeleted();
        assertTrue(flashcard.isDeleted());
    }

    @Test
    public void testMarkRecovered() {
        flashcard.markDeleted();
        flashcard.markRecovered();
        assertFalse(flashcard.isDeleted());
    }

    @Test
    public void testToStringWithHint() {
        String expectedString = "uuid='" + flashcard.getUUID() + "'\n" +
                ", question='Test question'\n" +
                ", answer='Test answer'\n" +
                ", hint = 'Test hint'\n";
        flashcard.setAnswer("Test answer");
        flashcard.setQuestion("Test question");
        flashcard.setHint("Test hint");
        assertEquals(expectedString, flashcard.toString());
    }

    @Test
    public void testToStringNullHint() {
        String expectedString = "uuid='" + flashcard.getUUID() + "'\n" +
                ", question='Test question'\n" +
                ", answer='Test answer'\n";
        flashcard.setAnswer("Test answer");
        flashcard.setQuestion("Test question");
        flashcard.setHint(null);
        assertEquals(expectedString, flashcard.toString());
    }

    @Test
    public void testToStringShortEmptyHint() {
        String expectedString = "uuid='" + flashcard.getUUID() + "'\n" +
                ", question='Test question'\n" +
                ", answer='Test answer'\n";
        flashcard.setAnswer("Test answer");
        flashcard.setQuestion("Test question");
        flashcard.setHint("");
        assertEquals(expectedString, flashcard.toString());
    }

    @Test
    public void testToStringLongEmptyHint() {
        String expectedString = "uuid='" + flashcard.getUUID() + "'\n" +
                ", question='Test question'\n" +
                ", answer='Test answer'\n";
        flashcard.setAnswer("Test answer");
        flashcard.setQuestion("Test question");
        flashcard.setHint("            ");
        assertEquals(expectedString, flashcard.toString());
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
    public void testGetFlashcards() {
        Flashcard flashcard1 = new Flashcard();
        Flashcard flashcard2 = new Flashcard();
        cardSet.addFlashCard(flashcard1);
        cardSet.addFlashCard(flashcard2);
        assertEquals(2, cardSet.getActiveFlashcards().size());
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
        assertEquals(1, cardSet.getActiveFlashcards().size());
    }


    @Test
    public void testGetFlashCardById() {
        Flashcard flashcard1 = new Flashcard();
        Flashcard flashcard2 = new Flashcard();
        cardSet.addFlashCard(flashcard1);
        cardSet.addFlashCard(flashcard2);
        String uuid = flashcard2.getUUID();
        assertEquals(flashcard2, cardSet.getFlashCardById(uuid));
    }


    @After
    public void tearDown() {

    }


}
