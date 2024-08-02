package comp3350.intellicards.tests;


import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import comp3350.intellicards.tests.persistance.AccessFlashcardSetsTest;

@RunWith(Suite.class)
@Suite.SuiteClasses({
    AccessFlashcardSetsTest.class
})

public class AllIntegrationTests {
}
