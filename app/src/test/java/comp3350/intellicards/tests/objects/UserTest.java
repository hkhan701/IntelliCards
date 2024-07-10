package comp3350.intellicards.tests.objects;

import static org.junit.Assert.assertEquals;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import comp3350.intellicards.Objects.User;

public class UserTest {

    User testUser;
    @Before
    public void setUp() {
        testUser = new User("TestUser", "TestPass");
    }

    /*
     * Test getUsername()
     */

    @Test
    public void getUsername() {
        assertEquals("Username can be retrieved from User",
                "TestUser", testUser.getUsername());
    }

    /*
     * Test getPassword()
     */

    @Test
    public void getPassword() {
        assertEquals("Password can be retrieved from User",
                "TestPass", testUser.getPassword());
    }

    /*
     * Test setUsername()
     */

    @Test
    public void changeUsername() {
        testUser.setUsername("User1");
        assertEquals("User can change their username",
                "User1", testUser.getUsername());
    }

    /*
     * Test setPassword()
     */

    @Test
    public void changePassword() {
        testUser.setPassword("123");
        assertEquals("User can change their password",
                "123", testUser.getPassword());
    }
}
