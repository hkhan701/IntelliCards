package comp3350.intellicards.tests.objects;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

import comp3350.intellicards.Objects.Flashcard;

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
        assertEquals("Flashcard's UUID can be retrieved",
                "uuid", testFlashcard.getUUID());
    }

    /*
     * Test getAnswer()
     */
    @Test
    public void testGetAnswer() {
        assertEquals("Flashcard's answer can be retrieved",
                "Answer", testFlashcard.getAnswer());
    }

    /*
     * Test getQuestion()
     */
    @Test
    public void testGetQuestion() {
        assertEquals("Flashcard's question can be retrieved",
                "Question", testFlashcard.getQuestion());
    }

    /*
     * Test getHint()
     */
    @Test
    public void testGetHint() {
        assertEquals("Flashcard's hint can be retrieved",
                "Hint", testFlashcard.getHint());
    }

    /*
     * Test getSetUUID()
     */
    @Test
    public void testGetSetUUID() {
        assertEquals("Flashcard's setUUID can be retrieved",
                "setUUID", testFlashcard.getSetUUID());
    }

    /*
     * Test getAttempted()
     */
    @Test
    public void testGetAttempted() {
        assertEquals("Flashcard's attempted value can be retrieved",
                1, testFlashcard.getAttempted());
    }

    /*
     * Test getCorrect()
     */
    @Test
    public void testGetCorrect() {
        assertEquals("Flashcard's correct value can be retrieved",
                0, testFlashcard.getCorrect());
    }

    /*
     * Test setAnswer()
     */
    @Test
    public void testSetAnswer() {
        testFlashcard.setAnswer("Test answer");
        assertEquals("Flashcard's answer can be changed",
                "Test answer", testFlashcard.getAnswer());
    }

    /*
     * Test setQuestion()
     */
    @Test
    public void testSetQuestion() {
        testFlashcard.setQuestion("Test question");
        assertEquals("Flashcard's question can be changed",
                "Test question", testFlashcard.getQuestion());
    }

    /*
     *  Test setHint()
     */
    @Test
    public void testSetHint() {
        testFlashcard.setHint("Test hint");
        assertEquals("Flashcard's hint can be changed",
                "Test hint", testFlashcard.getHint());
    }

    @Test
    public void testSetHintNull() {
        testFlashcard.setHint(null);
        assertNull("Flashcard's hint can be changed to null", testFlashcard.getHint());
    }

    /*
     * Test isDeleted()
     */
    @Test
    public void testIsDeleted() {
        assertFalse("Flashcard's deleted status can be retrieved",
                testFlashcard.isDeleted());
    }

    /*
     * Test markDeleted()
     */
    @Test
    public void testMarkDeleted() {
        testFlashcard.markDeleted();
        assertTrue("Flashcard can be marked as deleted",
                testFlashcard.isDeleted());
    }

    @Test
    public void testMarkDeletedMultiple() {
        testFlashcard.markDeleted();
        testFlashcard.markDeleted();

        assertTrue("Flashcard will still be marked as deleted if the method is called more than once",
                testFlashcard.isDeleted());
    }

    /*
     * Test markRecovered()
     */
    @Test
    public void testMarkRecovered() {
        testFlashcard.markDeleted();
        testFlashcard.markRecovered();
        assertFalse("Flashcard can be marked as recovered after being deleted",
                testFlashcard.isDeleted());
    }

    @Test
    public void testMarkRecoveredMultiple() {
        testFlashcard.markDeleted();
        testFlashcard.markRecovered();
        testFlashcard.markRecovered();
        assertFalse("Flashcard will still be marked as recovered if the method is called more than once",
                testFlashcard.isDeleted());
    }

    /*
     * Test markAttempted()
     */
    @Test
    public void testMarkAttempted() {
        testFlashcard.markAttempted();
        assertEquals("Flashcard can have attempted value increased using the markAttempted() method",
                2, testFlashcard.getAttempted());
    }

    /*
     * Test markAttemptedAndCorrect()
     */
    @Test
    public void testMarkAttemptedAndCorrect() {
        testFlashcard.markAttemptedAndCorrect();

        assertEquals("Flashcard can have attempted value increased using the markAttemptedAndCorrect() method",
                2, testFlashcard.getAttempted());
        assertEquals("Flashcard can have correct value increased using the markAttemptedAndCorrect() method",
                1, testFlashcard.getCorrect());
    }

}
