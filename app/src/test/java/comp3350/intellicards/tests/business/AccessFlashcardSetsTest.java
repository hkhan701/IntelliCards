package comp3350.intellicards.tests.business;

import static org.junit.Assert.*;
import static org.junit.Assume.assumeNotNull;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;

import comp3350.intellicards.Application.Configuration;
import comp3350.intellicards.Business.FlashcardSetManager;
import comp3350.intellicards.Objects.FlashcardSet;
import comp3350.intellicards.Persistence.hsqldb.FlashcardSetPersistenceHSQLDB;
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

        assertNotNull("Flashcard set can be retrieved from database", flashcardSet);
    }

    @Test
    public void testGetFlashcardSetRetrievesFlashcards() {
        FlashcardSet flashcardSet = manager.getFlashcardSet("set2");

        assumeNotNull(flashcardSet);
        assertNotNull("Associated flashcards will be attached when retrieving a flashcard set from the database",
                flashcardSet.getFlashcardById("fc3"));
    }


    /*
     * getActiveFlashcardSet
     */

    /*
     * getDeletedFlashcardSet
     */

    /*
     * getAllFlashcardSets
     */

    /*
     * insertFlashcardSet
     */

    /*
     * addFlashcardToFlashcardSet
     */

    /*
     * randomizeFlashcardSet
     */

    /*
     * getFlashcardSetsByUsername
     */

    @After
    public void tearDown() {
        tempDB.delete();
    }
}
