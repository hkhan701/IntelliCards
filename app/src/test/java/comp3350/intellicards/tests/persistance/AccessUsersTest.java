package comp3350.intellicards.tests.persistance;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assume.assumeNotNull;
import static org.junit.Assume.assumeTrue;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;

import comp3350.intellicards.Application.Configuration;
import comp3350.intellicards.Business.UserManager;
import comp3350.intellicards.Objects.User;
import comp3350.intellicards.Persistence.hsqldb.UserPersistenceHSQLDB;
import comp3350.intellicards.tests.utils.TestUtils;

public class AccessUsersTest {
    private static File tempDB;
    private static UserPersistenceHSQLDB persistence;
    private static UserManager manager;

    @Before
    public void setUp() throws IOException {
        tempDB = TestUtils.copyTestDB();

        persistence = new UserPersistenceHSQLDB(Configuration.getDBPathName());
        manager = new UserManager(persistence);
    }

    /*
     * Test registerUser()
     */
    @Test
    public void registerUser() {
        assertTrue("New user can be registered",
                manager.registerUser("TestUser", "TestPass"));
    }

    @Test
    public void registerUserDuplicate() {
        assertFalse("Cannot register new user if the wanted username is already taken",
                manager.registerUser("user1", "pass1"));
    }

    @Test
    public void registerUserGuest() {
        assertFalse("Cannot register user with the name 'guest'",
                manager.registerUser("guest", "guestPass"));
    }


    /*
     * Test loginUser()
     */
    @Test
    public void loginUserMatch() {
        User receivedUser = manager.loginUser("user1", "pass1");
        assertNotNull("LoginUser will return user upon successful login", receivedUser);
        assertEquals("LoginUser will return correct user upon successful login",
                "user1", receivedUser.getUsername());
    }

    @Test
    public void loginUserFail() {
        assertNull("LoginUser will not return user if username and password do not match records",
                manager.loginUser("user1", "passTest"));
    }

    @Test
    public void loginUserDoesNotExist() {
        assertNull("LoginUser will not return user if user is not persisted",
                manager.loginUser("testUser", "testPass"));
    }

    /*
     * Test incrementLoginCount()
     */
    @Test
    public void incrementLogin() {
        User retreivedUser = manager.loginUser("user1", "pass1");
        assumeNotNull(retreivedUser);
        assumeTrue(retreivedUser.getLoginCount() == 0);

        manager.incrementLoginCount("user1");

        retreivedUser = manager.loginUser("user1", "pass1");
        assumeNotNull(retreivedUser);
        assertEquals("User Manager can increase the login count of a user",
                1, retreivedUser.getLoginCount());
    }

    @Test
    public void incrementLoginUserNotExist() {
        manager.incrementLoginCount("testUser");
        assertEquals("User Manager cannot increment the login count of a non-persisted user",
                -1, manager.getUserLoginCount("testUser"));
    }

    /*
     * Test getUserLoginCount()
     */
    @Test
    public void getUserLoginCount() {
        assertEquals("User Manager can retrieve a user's login count",
                0, manager.getUserLoginCount("user1"));
    }

    @Test
    public void getUserLoginCountNotExist() {
        assertEquals("User Manager will return a placeholder integer if user is not persisted",
                -1, manager.getUserLoginCount("testUser"));
    }

    @After
    public void tearDown() {
        tempDB.delete();
    }
}
