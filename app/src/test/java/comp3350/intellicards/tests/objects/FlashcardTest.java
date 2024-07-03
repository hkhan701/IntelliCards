package comp3350.intellicards.tests.objects;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

import comp3350.intellicards.Objects.Flashcard;
import comp3350.intellicards.Objects.FlashcardSet;
import comp3350.intellicards.Objects.User;

public class FlashcardTest {

    private User testUser;
    private FlashcardSet testCardSet;
    private Flashcard testFlashcard;

    @Before
    public void setUp() {
        testUser = new User("testUser", "testPassword");
        testCardSet = new FlashcardSet("testUser", "Test Card Set");
        testFlashcard = new Flashcard(testCardSet.getUUID(), "Generic Answer", "Generic Question", "Generic Hint");
    }

    /*
     * Test getUuid()
     */
    @Test
    public void testGetUuid() {
        assertNotNull("The flashcard's UUID is assigned automatically upon creation",
                testFlashcard.getUUID());
    }

    /*
     * Test getAnswer()
     */
    @Test
    public void testGetAnswer() {
        assertNotNull("We can retrieve a card's answer",
                testFlashcard.getAnswer());
    }

    /*
     * Test getQuestion()
     */
    @Test
    public void testGetQuestion() {
        assertNotNull("We can retrieve a card's question",
                testFlashcard.getQuestion());
    }

    /*
     * Test getHint()
     */
    @Test
    public void testGetHint() {
        assertEquals("We can retrieve a card's hint",
                "Generic Hint", testFlashcard.getHint());
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

    @After
    public void tearDown() {
    }

}
