package comp3350.intellicards.tests;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import comp3350.intellicards.tests.objects.FlashcardSetTest;
import comp3350.intellicards.tests.objects.FlashcardTest;


@RunWith(Suite.class)
@Suite.SuiteClasses({
        FlashcardTest.class,
        FlashcardSetTest.class,
})

public class AllTests {

}
