package comp3350.srsys;

import static androidx.test.espresso.Espresso.closeSoftKeyboard;
import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.Espresso.pressBack;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import comp3350.srsys.presentation.HomeActivity;


import static org.hamcrest.CoreMatchers.anything;

import androidx.test.filters.LargeTest;
import androidx.test.rule.ActivityTestRule;
import androidx.test.runner.AndroidJUnit4;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class CoursesTest {
    @Rule
    public ActivityTestRule<HomeActivity> activityRule = new ActivityTestRule<>(HomeActivity.class);

    @Test
    public void createCourse() {
        onView(withId(R.id.buttonCourses)).perform(click());

        // add the new course
        onView(withId(R.id.editCourseID)).perform(typeText("4050"));
        onView(withId(R.id.editCourseName)).perform(typeText("Project Management"));
        onView(withId(R.id.buttonCourseCreate)).perform(click());
        closeSoftKeyboard();

        // verify that it was added
        pressBack();
        onView(withId(R.id.buttonCourses)).perform(click());
        onData(anything()).inAdapterView(withId(R.id.listCourses)).atPosition(0).perform(click());
        onView(withId(R.id.editCourseName)).check(matches(withText("Project Management")));
    }
}
