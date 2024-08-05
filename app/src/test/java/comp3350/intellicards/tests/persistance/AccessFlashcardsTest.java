package comp3350.intellicards.tests.persistance;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assume.assumeNotNull;
import static org.junit.Assume.assumeTrue;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;

import comp3350.intellicards.Application.Configuration;
import comp3350.intellicards.Business.FlashcardManager;
import comp3350.intellicards.Objects.Flashcard;
import comp3350.intellicards.Persistence.hsqldb.FlashcardPersistenceHSQLDB;
import comp3350.intellicards.tests.utils.TestUtils;

public class AccessFlashcardsTest {

    private static File tempDB;
    private static FlashcardPersistenceHSQLDB persistence;
    private static FlashcardManager manager;

    @Before
    public void setUp() throws IOException {
        tempDB = TestUtils.copyTestDB(false);

        persistence = new FlashcardPersistenceHSQLDB(Configuration.getDBPathName());
        manager = new FlashcardManager(persistence);
    }

    /*
     * Test getFlashcard()
     */
    @Test
    public void testGetFlashcard() {
        Flashcard flashcard = manager.getFlashcard("fc1");

        assertNotNull("Flashcard can be retrieved from database",
                flashcard);
    }

    @Test
    public void testGetFlashcardNotExist() {
        assertNull("Flashcard cannot retrieve data that does not exist",
                manager.getFlashcard("TestCard"));
    }

    /*
     * Test insertFlashcard()
     */
    @Test
    public void testInsertFlashcard() {

    }

    /*
     * Test moveFlashcardToNewSet()
     */
    @Test
    public void testMoveFlashcardToNewSet() {

    }

    /*
     * Test updateFlashcard()
     */
    @Test
    public void testUpdateFlashcard() {

    }

    /*
     * Test updateFlashcardDetails()
     */
    @Test
    public void testUpdateFlashcardDetails() {

    }

    /*
     * Test markFlashcardAsDeleted()
     */
    @Test
    public void testMarkFlashcardAsDeleted() {

    }

    /*
     * Test restoreFlashcard()
     */
    @Test
    public void testRestoreFlashcard() {

    }

    /*
     * Test markAttempted()
     */
    @Test
    public void testMarkAttempted() {

    }

    /*
     * Test markAttemptedAndCorrect()
     */
    @Test
    public void testMarkAttemptedAndCorrect() {

    }

    @After
    public void tearDown() { tempDB.delete(); }
}
