package comp3350.intellicards.tests.business;

import org.junit.After;
import org.junit.Before;

import java.io.File;
import java.io.IOException;

import comp3350.intellicards.Application.Main;
import comp3350.intellicards.Business.FlashcardSetManager;
import comp3350.intellicards.Persistence.hsqldb.FlashcardSetPersistenceHSQLDB;
import comp3350.intellicards.tests.utils.TestUtils;

public class AccessFlashcardSetsTest {

    private static File tempDB;
    private static FlashcardSetPersistenceHSQLDB persistence;
    private static FlashcardSetManager manager;

    @Before
    public void setUp() throws IOException {
        tempDB = TestUtils.copyTestDB(false);
        persistence = new FlashcardSetPersistenceHSQLDB(Main.getDBPathName());
        manager = new FlashcardSetManager(persistence);
    }

    /*
     * getFlashcardSet
     */

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
