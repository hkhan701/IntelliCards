package comp3350.intellicards.tests.objects;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

import comp3350.intellicards.Objects.Flashcard;

public class FlashcardTest {

    private Flashcard flashcard;

    @Before
    public void setUp() {
        flashcard = new Flashcard("Generic Answer", "Generic Question", "Generic Hint");
    }

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
     * Test getHint()
     */
    @Test
    public void testGetHint() {
        assertEquals("We can retrieve a card's hint",
                "Generic Hint", flashcard.getHint());
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
     *  Test setHint()
     */
    @Test
    public void testSetHint() {
        flashcard.setHint("Test hint");
        assertEquals("Changing the hint of a card overwrites the previous hint",
                "Test hint", flashcard.getHint());
    }

    @Test
    public void testSetHintNull() {
        flashcard.setHint(null);
        assertNull("Changing the hint to null should not cause any problems - it is acceptable", flashcard.getHint());
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

    @After
    public void tearDown() {
    }

}
