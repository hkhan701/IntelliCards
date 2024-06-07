package comp3350.intellicards.tests.business;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Test;
import org.junit.Before;

import comp3350.intellicards.Business.StubManager;

public class StubManagerTest {

    private StubManager stubManager;

    @Before
    public void setUp() { stubManager = new StubManager(); }

    /**
     * Test initializeStubData()
     */
    @Test
    public void initializingStubDataFillsWithMockedData() {
        StubManager.initializeStubData();

        assertTrue(StubManager.isInitialized());
        assertNotEquals(0, StubManager.getFlashcardPersistence().getAllActiveFlashcards().size());
        assertNotEquals(0, StubManager.getFlashcardSetPersistence().getAllFlashcardSets().size());
    }

    @After
    public void tearDown() {}
}
