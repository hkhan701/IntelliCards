package comp3350.intellicards.Notification;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.junit.Assert.assertTrue;

import android.os.SystemClock;

import androidx.test.espresso.assertion.ViewAssertions;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.uiautomator.By;
import androidx.test.uiautomator.UiDevice;
import androidx.test.uiautomator.UiObject;
import androidx.test.uiautomator.UiObjectNotFoundException;
import androidx.test.uiautomator.UiSelector;
import androidx.test.uiautomator.Until;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import comp3350.intellicards.Presentation.AuthActivity;
import comp3350.intellicards.R;
import comp3350.intellicards.TestUtils.TestUtils;
import comp3350.intellicards.TestUtils.WrongDatabaseException;

@RunWith(AndroidJUnit4.class)
public class NotificationTest {
    @Rule
    public ActivityScenarioRule<AuthActivity> activityScenarioRule = new ActivityScenarioRule<>(AuthActivity.class);

    private String USERNAME;
    private String PASSWORD;

    private final int WAIT_TIME = 5000;
    UiDevice device;

    public void enableNotificationsAfterPopUp() throws UiObjectNotFoundException {
        UiObject allowPermissionsIntellicards = device.findObject(new UiSelector().text("IntelliCards"));

        if (allowPermissionsIntellicards.exists()) {
            allowPermissionsIntellicards.click();
        }
        SystemClock.sleep(WAIT_TIME);

        UiObject allowPermissionsSlider = device.findObject(new UiSelector().text("Allow setting alarms and reminders"));
        if (allowPermissionsSlider.exists()) {
            allowPermissionsSlider.click();
        }
        SystemClock.sleep(WAIT_TIME);

        device.pressBack();
        SystemClock.sleep(WAIT_TIME);

        device.pressBack();
        SystemClock.sleep(WAIT_TIME);
    }

    public void setNotificationForNowAfterClickAddReminder() throws UiObjectNotFoundException {
        UiObject ok = device.findObject(new UiSelector().text("OK"));
        ok.click();
        SystemClock.sleep(WAIT_TIME);

        ok = device.findObject(new UiSelector().text("OK"));
        ok.click();
        SystemClock.sleep(WAIT_TIME);
    }

    @Before
    public void setup() throws WrongDatabaseException {
        device = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation());

        TestUtils.checkDatabase();
    }

    @Test
    public void setNotificationTest() throws UiObjectNotFoundException {
        USERNAME = "user81";
        PASSWORD = "pass81";

        TestUtils.loginUserFromAuthPage(USERNAME, PASSWORD);

        onView(withId(R.id.headerTitle)).check(ViewAssertions.matches(allOf(ViewMatchers.isDisplayed(), ViewMatchers.withText("IntelliCards"))));

        onView(allOf(withId(-1), withText("Math (2) "))).perform(click());
        onView(withId(R.id.addReminderButton)).perform(click());

        SystemClock.sleep(WAIT_TIME);

        // Deal with the case that notifications are not enabled
        UiObject allowPermissionsPopUp = device.findObject(new UiSelector().text("Allow"));
        if (allowPermissionsPopUp.exists()) {
            allowPermissionsPopUp.click();
            SystemClock.sleep(2 * WAIT_TIME);

            enableNotificationsAfterPopUp();

            onView(withId(R.id.addReminderButton)).perform(click());
        }

        SystemClock.sleep(WAIT_TIME);
        setNotificationForNowAfterClickAddReminder();

        assertTrue("Notification did not show up",
                device.wait(Until.hasObject(By.textContains("Flashcard Study Reminder")), 1000));

        onView(withId(R.id.backButton)).perform(click());
        TestUtils.logoutUserFromMainPage();
    }

    @Test
    public void clickOnNotificationTest() throws UiObjectNotFoundException {
        USERNAME = "user82";
        PASSWORD = "pass82";

        TestUtils.loginUserFromAuthPage(USERNAME, PASSWORD);

        onView(withId(R.id.headerTitle)).check(ViewAssertions.matches(allOf(ViewMatchers.isDisplayed(), ViewMatchers.withText("IntelliCards"))));

        onView(allOf(withId(-1), withText("Math (2) "))).perform(click());
        onView(withId(R.id.addReminderButton)).perform(click());

        SystemClock.sleep(WAIT_TIME);

        // Deal with the case that notifications are not enabled
        UiObject allowPermissionsPopUp = device.findObject(new UiSelector().text("Allow"));
        if (allowPermissionsPopUp.exists()) {
            allowPermissionsPopUp.click();
            SystemClock.sleep(2 * WAIT_TIME);

            enableNotificationsAfterPopUp();

            onView(withId(R.id.addReminderButton)).perform(click());
        }
        SystemClock.sleep(WAIT_TIME);

        setNotificationForNowAfterClickAddReminder();
        assertTrue("Notification did not show up",
                device.wait(Until.hasObject(By.textContains("Flashcard Study Reminder")), 1000));

        device.pressHome();
        SystemClock.sleep(WAIT_TIME);

        device.openNotification();
        SystemClock.sleep(WAIT_TIME);

        UiObject notification = device.findObject(new UiSelector().text("Flashcard Study Reminder"));
        if (notification.exists()) {
            notification.click();
        }

        onView(withId(R.id.flashcardSetTitle)).check(ViewAssertions.matches(allOf(ViewMatchers.isDisplayed(), ViewMatchers.withText("Math"))));

        onView(withId(R.id.backButton)).perform(click());
        TestUtils.logoutUserFromMainPage();
    }

    @After
    public void tearDown() {
        device.pressHome();
    }
}
