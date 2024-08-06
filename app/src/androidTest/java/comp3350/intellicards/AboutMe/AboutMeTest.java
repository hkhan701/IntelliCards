package comp3350.intellicards.AboutMe;

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
public class AboutMeTest {
    @Rule
    public ActivityScenarioRule<AuthActivity> activityScenarioRule = new ActivityScenarioRule<>(AuthActivity.class);

    private String USERNAME;
    private String PASSWORD;

    @Before
    public void verifyDataSource() throws WrongDatabaseException {
        TestUtils.checkDatabase();
    }

    @Test
    public void totalFlashcardsTest() {
        USERNAME = "user71";
        PASSWORD = "pass71";

        TestUtils.loginUserFromAuthPage(USERNAME, PASSWORD);

        onView(withId(R.id.headerTitle)).check(matches(allOf(isDisplayed(), ViewMatchers.withText("IntelliCards"))));

        onView(withId(R.id.profileButton)).perform(click());
        onView(withId(R.id.aboutMeButton)).perform(click());

        //parse informationText

        onView(withId(R.id.backButton)).perform(click());
        onView(withId(R.id.backButton)).perform(click());

        //select set

        //add flashcard

        onView(withId(R.id.profileButton)).perform(click());
        onView(withId(R.id.aboutMeButton)).perform(click());

        //parse informationText

        TestUtils.logoutUserFromAboutMePage();
    }

    @Test
    public void totalFlashcardSetsTest() {
        USERNAME = "user72";
        PASSWORD = "pass72";

        TestUtils.loginUserFromAuthPage(USERNAME, PASSWORD);

        onView(withId(R.id.headerTitle)).check(matches(allOf(isDisplayed(), ViewMatchers.withText("IntelliCards"))));

        onView(withId(R.id.profileButton)).perform(click());
        onView(withId(R.id.aboutMeButton)).perform(click());

        //parse informationText

        onView(withId(R.id.backButton)).perform(click());
        onView(withId(R.id.backButton)).perform(click());

        //add flashcard set

        onView(withId(R.id.profileButton)).perform(click());
        onView(withId(R.id.aboutMeButton)).perform(click());

        //parse informationText

        TestUtils.logoutUserFromAboutMePage();
    }

    @Test
    public void loginCountTest() {
        USERNAME = "user73";
        PASSWORD = "pass73";

        TestUtils.loginUserFromAuthPage(USERNAME, PASSWORD);

        onView(withId(R.id.headerTitle)).check(matches(allOf(isDisplayed(), ViewMatchers.withText("IntelliCards"))));

        onView(withId(R.id.profileButton)).perform(click());
        onView(withId(R.id.aboutMeButton)).perform(click());

        //parse informationText

        TestUtils.logoutUserFromAboutMePage();

        TestUtils.loginUserFromAuthPage(USERNAME, PASSWORD);

        onView(withId(R.id.headerTitle)).check(matches(allOf(isDisplayed(), ViewMatchers.withText("IntelliCards"))));

        onView(withId(R.id.profileButton)).perform(click());
        onView(withId(R.id.aboutMeButton)).perform(click());

        //parse informationText

        TestUtils.logoutUserFromAboutMePage();
    }

    @Test
    public void accuracyReportTest() {
        USERNAME = "user74";
        PASSWORD = "pass74";

        TestUtils.loginUserFromAuthPage(USERNAME, PASSWORD);

        onView(withId(R.id.headerTitle)).check(matches(allOf(isDisplayed(), ViewMatchers.withText("IntelliCards"))));

        onView(withId(R.id.profileButton)).perform(click());
        onView(withId(R.id.aboutMeButton)).perform(click());

        //parse informationText

        onView(withId(R.id.backButton)).perform(click());
        onView(withId(R.id.backButton)).perform(click());

        //select set

        //do a test

        onView(withId(R.id.profileButton)).perform(click());
        onView(withId(R.id.aboutMeButton)).perform(click());

        //parse informationText

        TestUtils.logoutUserFromAboutMePage();
    }
}
