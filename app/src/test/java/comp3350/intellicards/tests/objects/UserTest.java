package comp3350.intellicards.tests.objects;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import comp3350.intellicards.Objects.User;

public class UserTest {

    User testUser;
    @Before
    public void setUp() {
        testUser = new User("TestUser", "TestPass");
    }

    @Test
    public void changeUsername() {
        testUser.setUsername("User1");
        assertEquals("User can change their username", "User1", testUser.getUsername());
    }

    @Test
    public void changePassword() {
        testUser.setPassword("123");
        assertEquals("User can change their password", "123", testUser.getPassword());
    }

}
