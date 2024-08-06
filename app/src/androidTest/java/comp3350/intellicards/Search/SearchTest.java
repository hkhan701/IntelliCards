package comp3350.intellicards.Search;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.doesNotExist;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;

import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import comp3350.intellicards.Presentation.AuthActivity;
import comp3350.intellicards.R;
import comp3350.intellicards.TestUtils.TestUtils;
import comp3350.intellicards.TestUtils.WrongDatabaseException;

@RunWith(AndroidJUnit4.class)
public class SearchTest {
    @Rule
    public ActivityScenarioRule<AuthActivity> activityScenarioRule = new ActivityScenarioRule<>(AuthActivity.class);

    private String USERNAME;
    private String PASSWORD;

    @Before
    public void verifyDatasource() throws WrongDatabaseException {
        TestUtils.checkDatabase();
    }

    @Test
    public void searchFlashcardSetsTest() {
        USERNAME = "user91";
        PASSWORD = "pass91";

        TestUtils.loginUserFromAuthPage(USERNAME, PASSWORD);

        onView(withId(R.id.headerTitle)).check(matches(allOf(isDisplayed(), ViewMatchers.withText("IntelliCards"))));

        onView(withId(R.id.search)).perform(typeText("Math"), closeSoftKeyboard());

        onView(withId(R.id.searchButton)).perform(click());

        onView(allOf(withId(-1), withText("Math (2) "))).check(matches(isDisplayed()));
        onView(allOf(withId(-1), withText("Science (2) "))).check(doesNotExist());

        TestUtils.logoutUserFromMainPage();
    }

    @Test
    public void searchFlashcardsTest() {
        USERNAME = "user92";
        PASSWORD = "pass92";

        TestUtils.loginUserFromAuthPage(USERNAME, PASSWORD);

        onView(withId(R.id.headerTitle)).check(matches(allOf(isDisplayed(), ViewMatchers.withText("IntelliCards"))));

        onView(allOf(withId(-1), withText("Math (2) "))).perform(click());

        onView(withId(R.id.search)).perform(typeText("What is 2+2?"), closeSoftKeyboard());

        onView(withId(R.id.searchButton)).perform(click());

        onView(allOf(withId(R.id.flashcardTextRecycle), withText("What is 2+2? \nHint: Basic arithmetic"))).check(matches(isDisplayed()));
        onView(allOf(withId(R.id.flashcardTextRecycle), withText("What is the square root of 16? \nHint: Basic arithmetic"))).check(doesNotExist());

        onView(withId(R.id.backButton)).perform(click());
        TestUtils.logoutUserFromMainPage();
    }
}
