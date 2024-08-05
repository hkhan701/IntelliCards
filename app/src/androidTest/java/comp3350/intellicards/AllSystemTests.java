package comp3350.intellicards;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import comp3350.intellicards.Notification.NotificationTest;
import comp3350.intellicards.Search.SearchTest;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        NotificationTest.class,
        SearchTest.class
})

public class AllSystemTests {
}
