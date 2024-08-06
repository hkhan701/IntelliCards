package comp3350.intellicards.tests.persistance;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;
import static org.junit.Assume.assumeNotNull;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;

import comp3350.intellicards.Application.Configuration;
import comp3350.intellicards.Business.FlashcardManager;
import comp3350.intellicards.Objects.Flashcard;
import comp3350.intellicards.Persistence.hsqldb.FlashcardPersistenceHSQLDB;
import comp3350.intellicards.Persistence.hsqldb.PersistenceException;
import comp3350.intellicards.tests.utils.TestUtils;

public class AccessFlashcardsTest {

    private static File tempDB;
    private static FlashcardPersistenceHSQLDB persistence;
    private static FlashcardManager manager;

    @Before
    public void setUp() throws IOException {
        tempDB = TestUtils.copyTestDB();

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
        assertNull("Flashcard cannot be retrieved if does not exist in the database",
                manager.getFlashcard("TestCard"));
    }

    @Test
    public void testGetFlashcardNull() {
        assertNull("Flashcard cannot be retrieved if the UUID is null",
                manager.getFlashcard(null));
    }

    /*
     * Test insertFlashcard()
     */
    @Test
    public void testInsertFlashcard() {
        Flashcard flashcard = new Flashcard("TestID", "set1", "question", "answer", "hint", false, 0, 0);

        manager.insertFlashcard(flashcard);
        Flashcard retrieved = manager.getFlashcard("TestID");

        assertNotNull("New flashcard can be inserted into the database",
                retrieved);
    }

    @Test
    public void testInsertFlashcardInvalidSet() {
        Flashcard flashcard = new Flashcard("TestID", "TestSet", "question", "answer", "hint", false, 0, 0);

        assertThrows("New flashcard cannot be inserted into the database if the set has not been persisted",
                PersistenceException.class, () -> manager.insertFlashcard(flashcard));
    }

    @Test
    public void testInsertFlashcardAssociatedData() {
        Flashcard flashcard = new Flashcard("TestID", "set1", "question", "answer", "hint", false, 0, 0);

        manager.insertFlashcard(flashcard);
        Flashcard retrieved = manager.getFlashcard("TestID");

        assumeNotNull(flashcard);

        assertEquals("Inserting a flashcard will persist the given UUID",
                "TestID", retrieved.getUUID());
        assertEquals("Inserting a flashcard will persist the given set UUID",
                "set1", retrieved.getSetUUID());
        assertEquals("Inserting a flashcard will persist the given question",
                "question", retrieved.getQuestion());
        assertEquals("Inserting a flashcard will persist the given answer",
                "answer", retrieved.getAnswer());
        assertEquals("Inserting a flashcard will persist the given hint",
                "hint", retrieved.getHint());
        assertEquals("Inserting a flashcard will persist the given attempted count",
                0, retrieved.getAttempted());
        assertEquals("Inserting a flashcard will persist the given correct count",
                0, retrieved.getCorrect());
        assertFalse("Inserting a flashcard will persist the given deleted state",
                retrieved.isDeleted());
    }

    /*
     * Test updateFlashcard()
     */
    @Test
    public void testUpdateFlashcard() {
        Flashcard flashcard = new Flashcard("TestID", "set1", "q", "a", null, false, 0, 0);
        Flashcard edited = new Flashcard("TestID", "set2", "Edit q", "Edit a", "Edit h", false, 0, 0);

        manager.insertFlashcard(flashcard);
        manager.updateFlashcard(edited);
        Flashcard updated = manager.getFlashcard("TestID");

        assumeNotNull(edited);

        assertEquals("Updating a flashcard will persist the given set UUID",
                "set2", updated.getSetUUID());
        assertEquals("Updating a flashcard will persist the given question",
                "Edit q", updated.getQuestion());
        assertEquals("Updating a flashcard will persist the given answer",
                "Edit a", updated.getAnswer());
        assertEquals("Updating a flashcard will persist the given hint",
                "Edit h", updated.getHint());
    }

    @Test
    public void testUpdateFlashcardNotPersist() {
        Flashcard flashcard = new Flashcard("TestID", "set1", "q", "a", null, false, 0, 0);

        manager.updateFlashcard(flashcard);

        assertNull("Non-persisted flashcards are not added to the database upon update",
                manager.getFlashcard("TestID"));
    }

    /*
     * Test markFlashcardAsDeleted()
     */
    @Test
    public void testMarkFlashcardAsDeleted() {
        Flashcard flashcard = new Flashcard("TestID", "set1", "q", "a", null, false, 0, 0);

        manager.insertFlashcard(flashcard);
        manager.markFlashcardAsDeleted("TestID");

        assertTrue("Persisted flashcards can be marked as deleted",
                manager.getFlashcard("TestID").isDeleted());
    }

    @Test
    public void testMarkFlashcardAsDeletedNotPersist() {
        Flashcard flashcard = new Flashcard("TestID", "set1", "q", "a", null, false, 0, 0);

        manager.markFlashcardAsDeleted("TestID");

        assertNull("Non-persisted flashcards are not added to the database upon markFlashcardAsDeleted",
                manager.getFlashcard("TestID"));
        assertFalse("Non-persisted flashcards are not marked as deleted",
                flashcard.isDeleted());
    }

    /*
     * Test restoreFlashcard()
     */
    @Test
    public void testRestoreFlashcard() {
        Flashcard flashcard = new Flashcard("TestID", "set1", "q", "a", null, true, 0, 0);

        manager.insertFlashcard(flashcard);
        manager.restoreFlashcard("TestID");
        Flashcard retrieved = manager.getFlashcard("TestID");

        assertFalse("Persisted flashcards can be restored",
                retrieved.isDeleted());
    }

    @Test
    public void testRestoreFlashcardNotPersist() {
        Flashcard flashcard = new Flashcard("TestID", "set1", "q", "a", null, true, 0, 0);

        manager.restoreFlashcard("TestID");

        assertNull("Non-persisted flashcards are not added to the database upon restoreFlashcard",
                manager.getFlashcard("TestID"));
        assertTrue("Non-persisted flashcards cannot be restored",
                flashcard.isDeleted());
    }

    /*
     * Test markAttempted()
     */
    @Test
    public void testMarkAttempted() {
        Flashcard flashcard = new Flashcard("TestID", "set1", "q", "a", null, false, 0, 0);

        manager.insertFlashcard(flashcard);
        manager.markAttempted("TestID");
        Flashcard retrieved = manager.getFlashcard("TestID");

        assertEquals("Persisted flashcards can be marked as attempted",
                1, retrieved.getAttempted());
    }

    @Test
    public void testMarkAttemptedNotPersist() {
        Flashcard flashcard = new Flashcard("TestID", "set1", "q", "a", null, false, 0, 0);

        manager.markAttempted("TestID");

        assertNull("Non-persisted flashcards are not added to the database upon markAttempted",
                manager.getFlashcard("TestID"));
        assertEquals("Non-persisted flashcards cannot be marked as attempted",
                0, flashcard.getAttempted());
    }

    /*
     * Test markAttemptedAndCorrect()
     */
    @Test
    public void testMarkAttemptedAndCorrect() {
        Flashcard flashcard = new Flashcard("TestID", "set1", "q", "a", null, false, 0, 0);

        manager.insertFlashcard(flashcard);
        manager.markAttemptedAndCorrect("TestID");
        Flashcard retrieved = manager.getFlashcard("TestID");

        assertEquals("Persisted flashcards can be marked as attempted",
                1, retrieved.getAttempted());
        assertEquals("Persisted flashcards can be marked as correct",
                1, retrieved.getCorrect());
    }

    @Test
    public void testMarkAttemptedAndCorrectNotPersist() {
        Flashcard flashcard = new Flashcard("TestID", "set1", "q", "a", null, false, 0, 0);

        manager.markAttemptedAndCorrect("TestID");

        assertNull("Non-persisted flashcards cannot be added to the database upon markAttemptedAndCorrected",
                manager.getFlashcard("TestID"));
        assertEquals("Non-persisted flashcards cannot be marked as attempted",
                0, flashcard.getAttempted());
        assertEquals("Non-persisted flashcards cannot be marked as correct",
                0, flashcard.getCorrect());
    }

    @After
    public void tearDown() { tempDB.delete(); }
}
