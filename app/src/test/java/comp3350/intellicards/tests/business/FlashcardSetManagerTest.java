package comp3350.intellicards.tests.business;

import org.junit.After;
import org.junit.Test;
import org.junit.Before;

import comp3350.intellicards.Business.FlashcardSetManager;
import comp3350.intellicards.Persistence.stubs.FlashcardSetPersistenceStub;

public class FlashcardSetManagerTest {

    private FlashcardSetManager flashcardSetManager;

    @Before
    public void setUp() { flashcardSetManager = new FlashcardSetManager(new FlashcardSetPersistenceStub()); }

    /**
     * Test Constructors()
     */

    /**
     * Test getFlashcardSet()
     */

    /**
     * Test getActiveFlashcardSet()
     */

    /**
     * Test getAllFlashcardSets()
     */

    /**
     * Test insertFlashcardSet()
     */

    /**
     * Test addFlashcardToFlashcardSet()
     */

    @After
    public void tearDown() {}
}
