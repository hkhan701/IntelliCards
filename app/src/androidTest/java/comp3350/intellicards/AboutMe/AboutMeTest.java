package comp3350.intellicards.AboutMe;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.core.StringContains.containsString;

import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import comp3350.intellicards.Business.FlashcardSetManager;
import comp3350.intellicards.Business.ReportCalculator;
import comp3350.intellicards.Business.UserManager;
import comp3350.intellicards.Objects.FlashcardSet;
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

        onView(allOf(withId(-1), withText("set 7.1 (3) "))).perform(click());

        onView(allOf(withId(R.id.flashcardsRecyclerView), withText("q1"))).check(matches(isDisplayed()));
        onView(allOf(withId(R.id.flashcardsRecyclerView), withText("q2"))).check(matches(isDisplayed()));
        onView(allOf(withId(R.id.flashcardsRecyclerView), withText("q3"))).check(matches(isDisplayed()));

        onView(withId(R.id.backButton)).perform(click());
        onView(withId(R.id.profileButton)).perform(click());
        onView(withId(R.id.aboutMeButton)).perform(click());

        String verify = "Flashcard count: 3";
        onView(withId(R.id.informationText)).check(matches(withText(containsString(verify))));

        TestUtils.logoutUserFromAboutMePage();
    }

    @Test
    public void totalFlashcardSetsTest() {
        USERNAME = "user72";
        PASSWORD = "pass72";

        TestUtils.loginUserFromAuthPage(USERNAME, PASSWORD);

        onView(withId(R.id.headerTitle)).check(matches(allOf(isDisplayed(), ViewMatchers.withText("IntelliCards"))));

        onView(allOf(withId(-1), withText("set 7.2 A (0) "))).check(matches(isDisplayed()));
        onView(allOf(withId(-1), withText("set 7.2 B (0) "))).check(matches(isDisplayed()));

        onView(withId(R.id.profileButton)).perform(click());
        onView(withId(R.id.aboutMeButton)).perform(click());

        String verify = "Total Flashcard Sets: 2";
        onView(withId(R.id.informationText)).check(matches(withText(containsString(verify))));

        TestUtils.logoutUserFromAboutMePage();
    }

    @Test
    public void loginCountTest() {
        USERNAME = "user73";
        PASSWORD = "pass73";

        TestUtils.loginUserFromAuthPage(USERNAME, PASSWORD);

        UserManager userManager = new UserManager();
        int loginCount = userManager.getUserLoginCount("user73");

        onView(withId(R.id.headerTitle)).check(matches(allOf(isDisplayed(), ViewMatchers.withText("IntelliCards"))));

        onView(withId(R.id.profileButton)).perform(click());
        onView(withId(R.id.aboutMeButton)).perform(click());

        String verify = "Login Count: " + loginCount;
        onView(withId(R.id.informationText)).check(matches(withText(containsString(verify))));

        TestUtils.logoutUserFromAboutMePage();

        TestUtils.loginUserFromAuthPage(USERNAME, PASSWORD);

        onView(withId(R.id.headerTitle)).check(matches(allOf(isDisplayed(), ViewMatchers.withText("IntelliCards"))));

        onView(withId(R.id.profileButton)).perform(click());
        onView(withId(R.id.aboutMeButton)).perform(click());

        verify = "Login Count: " + (loginCount + 1);
        onView(withId(R.id.informationText)).check(matches(withText(containsString(verify))));

        TestUtils.logoutUserFromAboutMePage();
    }

    @Test
    public void accuracyReportTest() {
        USERNAME = "user74";
        PASSWORD = "pass74";

        FlashcardSetManager manager = new FlashcardSetManager();
        FlashcardSet set = manager.getFlashcardSet("set10");
        ReportCalculator calculator = new ReportCalculator(set);
        int attempted = calculator.getTotalAttempted();
        int correct = calculator.getTotalCorrect();

        TestUtils.loginUserFromAuthPage(USERNAME, PASSWORD);

        onView(withId(R.id.headerTitle)).check(matches(allOf(isDisplayed(), ViewMatchers.withText("IntelliCards"))));

        onView(withId(R.id.profileButton)).perform(click());
        onView(withId(R.id.aboutMeButton)).perform(click());

        String verify = "Correct: " + correct + " / " + attempted;
        onView(withId(R.id.informationText)).check(matches(withText(containsString(verify))));

        onView(withId(R.id.backButton)).perform(click());
        onView(withId(R.id.backButton)).perform(click());

        onView(allOf(withId(-1), withText("set 7.4 (2) "))).perform(click());

        onView(withId(R.id.testButton)).perform(click());
        onView(withId(R.id.correctCheckbox)).perform(click());
        onView(withId(R.id.nextCardButton)).perform(click());
        onView(withId(R.id.incorrectCheckbox)).perform(click());
        onView(withId(R.id.nextCardButton)).perform(click());

        onView(withId(R.id.backButton)).perform(click());
        onView(withId(R.id.backButton)).perform(click());

        onView(withId(R.id.profileButton)).perform(click());
        onView(withId(R.id.aboutMeButton)).perform(click());

        verify = "Correct: " + (correct+1) + " / " + (attempted + 2);
        onView(withId(R.id.informationText)).check(matches(withText(containsString(verify))));

        TestUtils.logoutUserFromAboutMePage();
    }
}
