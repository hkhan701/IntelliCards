package comp3350.intellicards.Notification;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static org.junit.Assert.assertEquals;

import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import comp3350.intellicards.Presentation.AuthActivity;
import comp3350.intellicards.R;

@RunWith(AndroidJUnit4.class)
public class NotificationTest {
    @Rule
    public ActivityScenarioRule<AuthActivity> activityScenarioRule = new ActivityScenarioRule<>(AuthActivity.class);

    private final String USERNAME = "user1";
    private final String PASSWORD = "pass1";

    @Before
    public void setUp() {

    }

    public void loginUser(String username, String password) {
        onView(ViewMatchers.withId(R.id.username)).perform(typeText(username), closeSoftKeyboard());
        onView(withId(R.id.password)).perform(typeText(password), closeSoftKeyboard());

        onView(withId(R.id.logInButton)).perform(click());
    }

    @Test
    public void setNotification() {
        loginUser(USERNAME, PASSWORD);

        assertEquals(USERNAME, activityScenarioRule.getScenario().getResult().getResultData());
    }

//    public void receiveNotification() {
//
//    }

}
