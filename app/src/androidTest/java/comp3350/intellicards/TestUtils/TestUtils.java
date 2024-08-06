package comp3350.intellicards.TestUtils;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

import androidx.test.espresso.matcher.ViewMatchers;

import comp3350.intellicards.Application.Configuration;
import comp3350.intellicards.R;

public class TestUtils {

    public static void loginUserFromAuthPage(String username, String password) {
        onView(ViewMatchers.withId(R.id.username)).perform(typeText(username), closeSoftKeyboard());
        onView(withId(R.id.password)).perform(typeText(password), closeSoftKeyboard());

        onView(withId(R.id.logInButton)).perform(click());
    }

    public static void logoutUserFromMainPage() {
        onView(withId(R.id.profileButton)).perform(click());
        onView(withId(R.id.logoutButton)).perform(click());
    }

    public static void checkDatabase() {
        if (Configuration.getDatasource() == null || !Configuration.getDatasource().equals("testHsqldb")) {
            throw new WrongDatabaseException("Please go to the Configuration class and change the value of the variable 'datasource' to \"testHsqldb\"");
        }
    }
}
