package comp3350.intellicards.tests.objects;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;

import comp3350.intellicards.Objects.Flashcard;
import comp3350.intellicards.Objects.FlashcardSet;
import comp3350.intellicards.Objects.User;

public class FlashcardTest {

    private Flashcard testFlashcard;

    @Before
    public void setUp() {
        testFlashcard = new Flashcard("uuid", "setUUID", "Question", "Answer", "Hint", false, 1, 0);
    }

    /*
     * Test Constructors
     */
    @Test
    public void flashcardDefaultConstructor() {
        testFlashcard = new Flashcard("setUUID", "Question", "Answer", null);

        assertNotNull("Flashcard will have UUID automatically assigned if none is provided on creation",
                testFlashcard.getUUID());
        assertFalse("Flashcard will not be marked as deleted if no indicator is provided",
                testFlashcard.isDeleted());
        assertEquals("Flashcard will have 0 attempts if number is not provided",
                0, testFlashcard.getAttempted());
        assertEquals("Flashcard will have 0 correct if number is not provided",
                0, testFlashcard.getCorrect());
    }

    /*
     * Test getUuid()
     */
    @Test
    public void testGetUuid() {
        assertEquals("The flashcard's UUID can be retrieved",
                "uuid", testFlashcard.getUUID());
    }

    /*
     * Test getAnswer()
     */
    @Test
    public void testGetAnswer() {
        assertEquals("The flashcard's answer can be retrieved",
                "Answer", testFlashcard.getAnswer());
    }

    /*
     * Test getQuestion()
     */
    @Test
    public void testGetQuestion() {
        assertEquals("The flashcard's question can be retrieved",
                "Question", testFlashcard.getQuestion());
    }

    /*
     * Test getHint()
     */
    @Test
    public void testGetHint() {
        assertEquals("The flashcard's hint can be retrieved",
                "Hint", testFlashcard.getHint());
    }

    /*
     * Test getSetUUID()
     */
    @Test
    public void testGetSetUUID() {
        assertEquals("The flashcard's setUUID can be retrieved",
                "setUUID", testFlashcard.getSetUUID());
    }

    /*
     * Test getAttempted()
     */
    @Test
    public void testGetAttempted() {
        assertEquals("The flashcard's attempted value can be retrieved",
                1, testFlashcard.getAttempted());
    }

    /*
     * Test getCorrect()
     */
    @Test
    public void testGetCorrect() {
        assertEquals("The flashcard's correct value can be retrieved",
                0, testFlashcard.getCorrect());
    }

    /*
     * Test setAnswer()
     */
    @Test
    public void testSetAnswerOverwritesPreviousAnswer() {
        testFlashcard.setAnswer("Test answer");
        assertEquals("Changing the answer of a card overwrites the previous answer",
                "Test answer", testFlashcard.getAnswer());
    }

    /*
     * Test setQuestion()
     */
    @Test
    public void testSetQuestionOverwritesPreviousQuestion() {
        testFlashcard.setQuestion("Test question");
        assertEquals("Changing the question of a card overwrites the previous question",
                "Test question", testFlashcard.getQuestion());
    }

    /*
     *  Test setHint()
     */
    @Test
    public void testSetHint() {
        testFlashcard.setHint("Test hint");
        assertEquals("Changing the hint of a card overwrites the previous hint",
                "Test hint", testFlashcard.getHint());
    }

    @Test
    public void testSetHintNull() {
        testFlashcard.setHint(null);
        assertNull("Changing the hint to null should not cause any problems - it is acceptable", testFlashcard.getHint());
    }

    /*
     * Test isDeleted()
     */
    @Test
    public void testIsDeleted() {
        assertFalse("A card is not marked as deleted unless it was marked as such",
                testFlashcard.isDeleted());
    }

    /*
     * Test markDeleted()
     */
    @Test
    public void testMarkDeleted() {
        testFlashcard.markDeleted();
        assertTrue("A flashcard that was marked as deleted will reflect that",
                testFlashcard.isDeleted());
    }

    /*
     * Test markRecovered()
     */
    @Test
    public void testMarkRecovered() {
        testFlashcard.markDeleted();
        testFlashcard.markRecovered();
        assertFalse("A card can be recovered after being marked as deleted",
                testFlashcard.isDeleted());
    }

    /*
     * Test markAttempted()
     */
    @Test
    public void testMarkAttempted() {
        testFlashcard.markAttempted();
        assertEquals("Marking a flashcard as attempted will increase the attempted value by one",
                2, testFlashcard.getAttempted());
    }

    /*
     * Test markAttemptedAndCorrect()
     */
    @Test
    public void testMarkAttemptedAndCorrect() {
        testFlashcard.markAttemptedAndCorrect();

        assertEquals("Marking a flashcard as attempted and correct will increase the attempted value by one",
                2, testFlashcard.getAttempted());
        assertEquals("Marking a flashcard as attempted and correct will increase the correct value by one",
                1, testFlashcard.getCorrect());
    }

    @After
    public void tearDown() {
    }

}
