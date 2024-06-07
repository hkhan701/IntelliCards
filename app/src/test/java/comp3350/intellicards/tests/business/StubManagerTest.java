package comp3350.intellicards.tests.business;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Test;
import org.junit.Before;

import comp3350.intellicards.Business.StubManager;

public class StubManagerTest {

    @Before
    public void setUp() {}

    /*
     * Test Stub Manager Initialization
     */
    @Test
    public void initializingStubDataFillsWithMockedData() {
        StubManager.initializeStubData();

        assertTrue("After Initializing the stub manager, isInitialized should be true",
                StubManager.isInitialized());
        assertNotEquals("After Initializing the stub manager, there should be mocked data in the flashcardManager",
                0, StubManager.getFlashcardPersistence().getAllActiveFlashcards().size());
        assertNotEquals("After Initializing the stub manager, there should be mocked data in the flashcardSetManager",
                0, StubManager.getFlashcardSetPersistence().getAllFlashcardSets().size());
    }

    @After
    public void tearDown() {}
}
