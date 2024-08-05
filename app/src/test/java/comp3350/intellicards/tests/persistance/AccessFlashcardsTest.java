package comp3350.intellicards.tests.persistance;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
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
        assertThrows("Flashcard cannot be retrieved if does not exist in the database",
                PersistenceException.class, () -> manager.getFlashcard("TestCard"));
    }

    @Test
    public void testGetFlashcardNull() {
        assertThrows("Flashcard cannot be retrieved if the UUID is null",
                PersistenceException.class, () -> manager.getFlashcard(null));
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
    public void testInsertFlashcardDupe() {
        Flashcard flashcard = new Flashcard("fc1", "set1", "question", "answer", "hint", false, 0,0);

        assertThrows("New flashcard cannot be inserted into the database if the ID matches one that exists",
                PersistenceException.class, () -> manager.insertFlashcard(flashcard));
    }

    @Test
    public void testInsertFlashcardNull() {
        assertThrows("New flashcard cannot be inserted into the database if the card is null",
                PersistenceException.class, () -> manager.insertFlashcard(null));
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
        Flashcard edited = new Flashcard("TestID", "set2", "Edit q", "Edit a", "Edit h", true, 6, 1);

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
        assertEquals("Updating a flashcard will persist the given attempted count",
                6, updated.getAttempted());
        assertEquals("Updating a flashcard will persist the given correct count",
                1, updated.getCorrect());
        assertTrue("Updating a flashcard will persist the given deleted state",
                updated.isDeleted());
    }

    @Test
    public void testUpdateFlashcardNotPersist() {
        Flashcard flashcard = new Flashcard("TestID", "set1", "q", "a", null, false, 0, 0);

        assertThrows("Non-persisted flashcards cannot be updated",
                PersistenceException.class, () -> manager.updateFlashcard(flashcard));
    }

    @Test
    public void testUpdateFlashcardNull() {
        assertThrows("Null-object flashcards cannot be updated",
                PersistenceException.class, () -> manager.updateFlashcard(null));
    }

    /*
     * Test markFlashcardAsDeleted()
     */
    @Test
    public void testMarkFlashcardAsDeleted() {
        manager.markFlashcardAsDeleted("fc2");
        Flashcard retrieved = manager.getFlashcard("fc2");

        assertTrue("Persisted flashcards can be marked as deleted",
                retrieved.isDeleted());
    }

    @Test
    public void testMarkFlashcardAsDeletedNotExist() {
        assertThrows("Non-persisted flashcards cannot be marked as deleted",
                PersistenceException.class, () -> manager.markFlashcardAsDeleted("TestID"));
    }

    @Test
    public void testMarkFlashcardAsDeletedNull() {
        assertThrows("Null-object flashcards cannot be marked as deleted",
                PersistenceException.class, () -> manager.markFlashcardAsDeleted(null));
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
    public void testRestoreFlashcardNotExist() {
        assertThrows("Non-persisted flashcards cannot be restored",
                PersistenceException.class, () -> manager.restoreFlashcard("TestID"));
    }

    @Test
    public void testRestoreFlashcardNull() {
        assertThrows("Null-object cards cannot be restored",
                PersistenceException.class, () -> manager.restoreFlashcard(null));
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
    public void testMarkAttemptedNotExist() {
        assertThrows("Non-persisted flashcards cannot be marked as attempted",
                PersistenceException.class, () -> manager.markAttempted("TestID"));
    }

    @Test
    public void testMarkAttemptedNull() {
        assertThrows("Null-object cards cannot be marked as attempted",
                PersistenceException.class, () -> manager.markAttempted(null));
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
    public void testMarkAttemptedAndCorrectNotExist() {
        assertThrows("Non-persisted flashcards cannot be marked as attempted and correct",
                PersistenceException.class, () -> manager.markAttemptedAndCorrect("TestID"));
    }

    @Test
    public void testMarkAttemptedAndCorrectNull() {
        assertThrows("Null-object cards cannot be marked as attempted and correct",
                PersistenceException.class, () -> manager.markAttemptedAndCorrect(null));
    }

    @After
    public void tearDown() { tempDB.delete(); }
}
