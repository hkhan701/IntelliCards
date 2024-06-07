package comp3350.intellicards.tests.objects;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

import comp3350.intellicards.Objects.Flashcard;

public class FlashcardTest {

    private Flashcard flashcard;

    @Before
    public void setUp() { flashcard = new Flashcard("Generic Answer", "Generic Question", "Generic Hint"); }

    /*
     * Test getUuid()
     */
    @Test
    public void testGetUuid() {
        assertNotNull("The flashcard's UUID is assigned automatically upon creation",
                flashcard.getUUID());
    }

    /*
     * Test getAnswer()
     */
    @Test
    public void testGetAnswer() {
        assertNotNull("We can retrieve a card's answer",
                flashcard.getAnswer());
    }

    /*
     * Test getQuestion()
     */
    @Test
    public void testGetQuestion() {
        assertNotNull("We can retrieve a card's question",
                flashcard.getQuestion());
    }

    /*
     * Test setAnswer()
     */
    @Test
    public void testSetAnswerOverwritesPreviousAnswer() {
        flashcard.setAnswer("Test answer");
        assertEquals("Changing the answer of a card overwrites the previous answer",
                "Test answer", flashcard.getAnswer());
    }

    /*
     * Test setQuestion()
     */
    @Test
    public void testSetQuestionOverwritesPreviousQuestion() {
        flashcard.setQuestion("Test question");
        assertEquals("Changing the question of a card overwrites the previous question",
                "Test question", flashcard.getQuestion());
    }

    /*
     * Test isDeleted()
     */
    @Test
    public void testIsDeleted() {
        assertFalse("A card is not marked as deleted unless it was marked as such",
                flashcard.isDeleted());
    }

    /*
     * Test markDeleted()
     */
    @Test
    public void testMarkDeleted() {
        flashcard.markDeleted();
        assertTrue("A flashcard that was marked as deleted will reflect that",
                flashcard.isDeleted());
    }

    /*
     * Test markRecovered()
     */
    @Test
    public void testMarkRecovered() {
        flashcard.markDeleted();
        flashcard.markRecovered();
        assertFalse("A card can be recovered after being marked as deleted",
                flashcard.isDeleted());
    }

    /*
     * Test toString()
     */
    @Test
    public void testToStringWithHint() {
        String expectedString = "uuid='" + flashcard.getUUID() + "'\n" +
                ", question='Test question'\n" +
                ", answer='Test answer'\n" +
                ", hint = 'Test hint'\n";
        flashcard.setAnswer("Test answer");
        flashcard.setQuestion("Test question");
        flashcard.setHint("Test hint");
        assertEquals("Calling toString() on a card with a hint will write all data stored in the card",
                expectedString, flashcard.toString());
    }

    @Test
    public void testToStringNullHint() {
        String expectedString = "uuid='" + flashcard.getUUID() + "'\n" +
                ", question='Test question'\n" +
                ", answer='Test answer'\n";
        flashcard.setAnswer("Test answer");
        flashcard.setQuestion("Test question");
        flashcard.setHint(null);
        assertEquals("Calling toString() on a card without a hint will not show the hint field",
                expectedString, flashcard.toString());
    }

    @Test
    public void testToStringShortEmptyHint() {
        String expectedString = "uuid='" + flashcard.getUUID() + "'\n" +
                ", question='Test question'\n" +
                ", answer='Test answer'\n";
        flashcard.setAnswer("Test answer");
        flashcard.setQuestion("Test question");
        flashcard.setHint("");
        assertEquals("Setting a hint with an empty string will cause it not to appear when toString() is called",
                expectedString, flashcard.toString());
    }

    @Test
    public void testToStringLongEmptyHint() {
        String expectedString = "uuid='" + flashcard.getUUID() + "'\n" +
                ", question='Test question'\n" +
                ", answer='Test answer'\n";
        flashcard.setAnswer("Test answer");
        flashcard.setQuestion("Test question");
        flashcard.setHint("            ");
        assertEquals("Setting a hint with a string only containing spaces will cause it not to appear when toString() is called",
                expectedString, flashcard.toString());
    }

    @After
    public void tearDown() {}

}
