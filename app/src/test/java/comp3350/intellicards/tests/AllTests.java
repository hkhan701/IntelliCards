package comp3350.intellicards.tests;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import comp3350.intellicards.tests.business.FlashcardManagerTest;
import comp3350.intellicards.tests.business.FlashcardSetManagerTest;
import comp3350.intellicards.tests.business.ReportCalculatorTest;
import comp3350.intellicards.tests.business.UserManagerTest;
import comp3350.intellicards.tests.objects.FlashcardSetTest;
import comp3350.intellicards.tests.objects.FlashcardTest;
import comp3350.intellicards.tests.objects.UserTest;


@RunWith(Suite.class)
@Suite.SuiteClasses({
        FlashcardTest.class,
        FlashcardSetTest.class,
        UserTest.class,
        FlashcardManagerTest.class,
        FlashcardSetManagerTest.class,
        UserManagerTest.class,
        ReportCalculatorTest.class,
})

public class AllTests {
}
