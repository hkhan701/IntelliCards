package comp3350.intellicards.tests.persistance;

import static org.junit.Assert.*;
import static org.junit.Assume.assumeNotNull;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.List;

import comp3350.intellicards.Application.Configuration;
import comp3350.intellicards.Business.FlashcardSetManager;
import comp3350.intellicards.Objects.Flashcard;
import comp3350.intellicards.Objects.FlashcardSet;
import comp3350.intellicards.Persistence.hsqldb.FlashcardSetPersistenceHSQLDB;
import comp3350.intellicards.Persistence.hsqldb.PersistenceException;
import comp3350.intellicards.tests.utils.TestUtils;

public class AccessFlashcardSetsTest {

    private static File tempDB;
    private static FlashcardSetPersistenceHSQLDB persistence;
    private static FlashcardSetManager manager;

    @Before
    public void setUp() throws IOException {
        tempDB = TestUtils.copyTestDB(false);

        persistence = new FlashcardSetPersistenceHSQLDB(Configuration.getDBPathName());
        manager = new FlashcardSetManager(persistence);
    }

    /*
     * getFlashcardSet
     */
    @Test
    public void testGetFlashcardSet() {
        FlashcardSet flashcardSet = manager.getFlashcardSet("set1");

        assertNotNull("Flashcard set can be retrieved from database",
                flashcardSet);
    }

    @Test
    public void testGetFlashcardSetNotExist() {
        assertNull("Flashcard set cannot retrieve data that does not exist" ,
                manager.getFlashcardSet("TestSet"));
    }

    @Test
    public void testGetFlashcardSetRetrievesFlashcards() {
        FlashcardSet flashcardSet = manager.getFlashcardSet("set2");

        assumeNotNull(flashcardSet);
        assertNotNull("Associated flashcards will be attached when retrieving a flashcard set from the database",
                flashcardSet.getFlashcardById("fc3"));
    }

    /*
     * getAllFlashcardSets
     */
    @Test
    public void getAllFlashcardSets() {
        List<FlashcardSet> flashcardSets = manager.getAllFlashcardSets();

        assertEquals("all flashcard sets currently persisted can be retrieved as a list",
                4, flashcardSets.size());
    }

    /*
     * insertFlashcardSet
     */
    @Test
    public void testInsertFlashcard() {
        FlashcardSet flashcardSet = new FlashcardSet("TestID", "user1","TestSubject");

        manager.insertFlashcardSet(flashcardSet);
        FlashcardSet retrievedSet = manager.getFlashcardSet("TestID");

        assertNotNull("New flashcard set can be inserted into the database",
                retrievedSet);
    }

    @Test
    public void testInsertFlashcardUserNotPersisted() {
        FlashcardSet flashcardSet = new FlashcardSet("TestID", "TestUser","TestSubject");

        assertThrows("New flashcard set cannot be inserted into the database if the username has not been persisted",
                PersistenceException.class, () -> manager.insertFlashcardSet(flashcardSet));
    }

    @Test
    public void testInsertFlashcardDupe() {
        FlashcardSet flashcardSet = new FlashcardSet("set1", "user1","TestSubject");

        assertThrows("New flashcard set cannot be inserted into the database if the username has not been persisted",
                PersistenceException.class, () -> manager.insertFlashcardSet(flashcardSet));
    }

    @Test
    public void testInsertFlashcardAssociatedData() {
        FlashcardSet flashcardSet = new FlashcardSet("TestID", "user1","TestSubject");

        manager.insertFlashcardSet(flashcardSet);
        FlashcardSet retrievedSet = manager.getFlashcardSet("TestID");

        assumeNotNull(flashcardSet);

        assertEquals("Inserting a flashcard will persist the given uuid",
                "TestID", retrievedSet.getUUID());
        assertEquals("Inserting a flashcard will persist the given username",
                "user1", retrievedSet.getUsername());
        assertEquals("Inserting a flashcard will persist the given flashcard set name",
                "TestSubject", retrievedSet.getFlashcardSetName());
    }

    @Test
    public void testInsertFlashcardSetAssociatedFlashcards() {
        FlashcardSet flashcardSet = new FlashcardSet("TestID", "user1","TestSubject");
        Flashcard flashcard = new Flashcard("testFlashcard1", "TestID", "question", "answer", "hint", false, 0, 0);

        flashcardSet.addFlashcard(flashcard);

        manager.insertFlashcardSet(flashcardSet);
        FlashcardSet retrievedSet = manager.getFlashcardSet("TestID");

        assumeNotNull(flashcardSet);
        assertNull("flashcard set will not persist associated flashcards, they should be persisted using the flashcardManager",
                retrievedSet.getFlashcardById("testFlashcard1"));
    }

    @After
    public void tearDown() {
        tempDB.delete();
    }
}
