package comp3350.intellicards.tests;


import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import comp3350.intellicards.tests.persistance.AccessFlashcardSetsTest;
import comp3350.intellicards.tests.persistance.AccessFlashcardsTest;
import comp3350.intellicards.tests.persistance.AccessUsersTest;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        AccessFlashcardSetsTest.class,
        AccessFlashcardsTest.class,
        AccessUsersTest.class
})

public class AllIntegrationTests {
}
