package comp3350.srsys.tests.business;

import org.junit.Before;
import org.junit.Test;

import comp3350.srsys.business.AccessCourses;

import comp3350.srsys.objects.Course;

import comp3350.srsys.tests.persistence.CoursePersistenceStub;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;


public class AccessCoursesTest
{
	private AccessCourses accessCourses;

	@Before
    public void setUp() {
	    accessCourses = new AccessCourses(new CoursePersistenceStub());
    }

    @Test
	public void test1()
	{
		final Course course;

		System.out.println("\nStarting test AccessCourses");

		course = accessCourses.getSequential();
		assertNotNull("first sequential course should not be null", course);
		assertTrue("COMP3010".equals(course.getCourseID()));

		System.out.println("Finished test AccessCourses");
	}
}