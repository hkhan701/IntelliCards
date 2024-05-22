package comp3350.srsys.tests.business;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;

import comp3350.srsys.application.Main;
import comp3350.srsys.business.AccessCourses;
import comp3350.srsys.objects.Course;
import comp3350.srsys.tests.utils.TestUtils;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class AccessCoursesIT {
    private AccessCourses accessCourses;
    private File tempDB;


    @Before
    public void setUp() throws IOException {
        this.tempDB = TestUtils.copyDB();
        this.accessCourses = new AccessCourses();
    }

    @Test
    public void testListCourses() {
        final Course course;

        course = accessCourses.getSequential();
        assertNotNull("first sequential course should not be null", course);
        assertTrue("COMP3010".equals(course.getCourseID()));

        System.out.println("Finished test AccessCourses");
    }

    @After
    public void tearDown() {
        // reset DB
        this.tempDB.delete();
    }
}
