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

import comp3350.intellicards.Business.UserManager;
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

        UserManager userManager = new UserManager();
        int loginCount = userManager.getUserLoginCount("user71") + 1;

        String report = "Total Flashcard Sets: 1"
                + "\nFlashcard count: 3"
                + "\nActive Flashcard count: 3"
                + "\nDeleted Flashcard count: 0\n\n\n"
                + "ALL TIME TOTAL ACCURACY\nCorrect: 0 / 0"
                + "\nThat is 0% correct: "
                + "\n\nLogin Count: " + loginCount;

        TestUtils.loginUserFromAuthPage(USERNAME, PASSWORD);

        onView(withId(R.id.headerTitle)).check(matches(allOf(isDisplayed(), ViewMatchers.withText("IntelliCards"))));

        onView(allOf(withId(-1), withText("set 7.1 (3) "))).perform(click());

        onView(allOf(withId(R.id.flashcardsRecyclerView), withText("q1"))).check(matches(isDisplayed()));
        onView(allOf(withId(R.id.flashcardsRecyclerView), withText("q2"))).check(matches(isDisplayed()));
        onView(allOf(withId(R.id.flashcardsRecyclerView), withText("q3"))).check(matches(isDisplayed()));

        onView(withId(R.id.backButton)).perform(click());
        onView(withId(R.id.profileButton)).perform(click());
        onView(withId(R.id.aboutMeButton)).perform(click());

        onView(withId(R.id.informationText)).check(matches(withText(report)));

        TestUtils.logoutUserFromAboutMePage();
    }

    @Test
    public void totalFlashcardSetsTest() {
        USERNAME = "user72";
        PASSWORD = "pass72";

        UserManager userManager = new UserManager();
        int loginCount = userManager.getUserLoginCount("user72") + 1;

        String report = "Total Flashcard Sets: 2"
                + "\nFlashcard count: 0"
                + "\nActive Flashcard count: 0"
                + "\nDeleted Flashcard count: 0\n\n\n"
                + "ALL TIME TOTAL ACCURACY\nCorrect: 0 / 0"
                + "\nThat is 0% correct: "
                + "\n\nLogin Count: " + loginCount;

        TestUtils.loginUserFromAuthPage(USERNAME, PASSWORD);

        onView(withId(R.id.headerTitle)).check(matches(allOf(isDisplayed(), ViewMatchers.withText("IntelliCards"))));

        onView(allOf(withId(-1), withText("set 7.2 A (0) "))).check(matches(isDisplayed()));
        onView(allOf(withId(-1), withText("set 7.2 B (0) "))).check(matches(isDisplayed()));

        onView(withId(R.id.profileButton)).perform(click());
        onView(withId(R.id.aboutMeButton)).perform(click());

        onView(withId(R.id.informationText)).check(matches(withText(report)));

        TestUtils.logoutUserFromAboutMePage();
    }

    @Test
    public void loginCountTest() {
        USERNAME = "user73";
        PASSWORD = "pass73";

        UserManager userManager = new UserManager();
        int loginCount = userManager.getUserLoginCount("user72") + 1;

        String priorReport = "Total Flashcard Sets: 2"
                + "\nFlashcard count: 0"
                + "\nActive Flashcard count: 0"
                + "\nDeleted Flashcard count: 0\n\n\n"
                + "ALL TIME TOTAL ACCURACY\nCorrect: 0 / 0"
                + "\nThat is 0% correct: "
                + "\n\nLogin Count: " + loginCount;

        String postReport = "Total Flashcard Sets: 2"
                + "\nFlashcard count: 0"
                + "\nActive Flashcard count: 0"
                + "\nDeleted Flashcard count: 0\n\n\n"
                + "ALL TIME TOTAL ACCURACY\nCorrect: 0 / 0"
                + "\nThat is 0% correct: "
                + "\n\nLogin Count: " + (loginCount + 1);

        TestUtils.loginUserFromAuthPage(USERNAME, PASSWORD);

        onView(withId(R.id.headerTitle)).check(matches(allOf(isDisplayed(), ViewMatchers.withText("IntelliCards"))));

        onView(withId(R.id.profileButton)).perform(click());
        onView(withId(R.id.aboutMeButton)).perform(click());

        onView(withId(R.id.informationText)).check(matches(withText(priorReport)));

        TestUtils.logoutUserFromAboutMePage();

        TestUtils.loginUserFromAuthPage(USERNAME, PASSWORD);

        onView(withId(R.id.headerTitle)).check(matches(allOf(isDisplayed(), ViewMatchers.withText("IntelliCards"))));

        onView(withId(R.id.profileButton)).perform(click());
        onView(withId(R.id.aboutMeButton)).perform(click());

        onView(withId(R.id.informationText)).check(matches(withText(postReport)));

        TestUtils.logoutUserFromAboutMePage();
    }

    @Test
    public void accuracyReportTest() {
        USERNAME = "user74";
        PASSWORD = "pass74";

        UserManager userManager = new UserManager();
        int loginCount = userManager.getUserLoginCount("user72") + 1;

        //get current counts from [somewhere]
        int attempted = 0;
        int correct = 0;

        String priorReport = "Total Flashcard Sets: 2"
                + "\nFlashcard count: 0"
                + "\nActive Flashcard count: 0"
                + "\nDeleted Flashcard count: 0\n\n\n"
                + "ALL TIME TOTAL ACCURACY\nCorrect: " + correct + " / " + attempted
                + "\nThat is " + Math.round(correct * 100 / (double) attempted) + "% correct: "
                + "\n\nLogin Count: " + loginCount;

        String postReport = "Total Flashcard Sets: 2"
                + "\nFlashcard count: 0"
                + "\nActive Flashcard count: 0"
                + "\nDeleted Flashcard count: 0\n\n\n"
                + "ALL TIME TOTAL ACCURACY\nCorrect: " + (correct+1) + " / " + (attempted + 2)
                + "\nThat is " + Math.round((correct + 1) * 100 / (double) (attempted + 2)) + "% correct: "
                + "\n\nLogin Count: " + loginCount;

        TestUtils.loginUserFromAuthPage(USERNAME, PASSWORD);

        onView(withId(R.id.headerTitle)).check(matches(allOf(isDisplayed(), ViewMatchers.withText("IntelliCards"))));

        onView(withId(R.id.profileButton)).perform(click());
        onView(withId(R.id.aboutMeButton)).perform(click());

        onView(withId(R.id.informationText)).check(matches(withText(priorReport)));

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

        onView(withId(R.id.informationText)).check(matches(withText(postReport)));

        TestUtils.logoutUserFromAboutMePage();
    }
}
