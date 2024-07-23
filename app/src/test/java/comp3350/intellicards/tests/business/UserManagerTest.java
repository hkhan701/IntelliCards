package comp3350.intellicards.tests.business;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import org.junit.Before;
import org.junit.Test;

import comp3350.intellicards.Business.UserManager;
import comp3350.intellicards.Objects.User;
import comp3350.intellicards.Persistence.UserPersistence;

public class UserManagerTest {
    private UserManager userManager;

    private UserPersistence userPersistenceMock;
    private User userMock;

    @Before
    public void setUp() {
        userPersistenceMock = mock(UserPersistence.class);
        userMock = mock(User.class);
        userManager = new UserManager(userPersistenceMock);
    }

    /*
     * Test registerUser()
     */
    @Test
    public void addNewUser() {
        assertTrue("UserManager will allow registration of brand new user",
                userManager.registerUser("Test1", "Pass1"));
    }

    @Test
    public void addDuplicateUser() {
        when(userPersistenceMock.getUserByUsername("Test1")).thenReturn(userMock);

        assertFalse("UserManager will not allow for registration of duplicate users",
                userManager.registerUser("Test1", "Pass2"));
    }

    @Test
    public void addGuestUser() {
        assertFalse("UserManager will not allow for registration of user titled 'guest'",
                userManager.registerUser("guest", "guestPass"));
    }

    /*
     * Test loginUser()
     */
    @Test
    public void loginUserSuccess() {
        when(userMock.getPassword()).thenReturn("Pass1");
        when(userPersistenceMock.getUserByUsername("Test1")).thenReturn(userMock);

        assertNotNull("UserManager should login if username and password match",
                userManager.loginUser("Test1", "Pass1"));
    }

    @Test
    public void loginUserFailure() {
        when(userMock.getPassword()).thenReturn("Pass1");
        when(userPersistenceMock.getUserByUsername("Test1")).thenReturn(userMock);

        assertNull("UserManager should not login if username and password do not match",
                userManager.loginUser("Test1", "IAmHacker"));
    }

    @Test
    public void loginUserDoesNotExist() {
        assertNull("UserManager does not allow login if user does not exist",
                userManager.loginUser("Test1", "Pass1"));
    }

    /*
     * Test deleteUser()
     */
    @Test
    public void deleteUserExists() {
        when(userPersistenceMock.getUserByUsername("Test1")).thenReturn(userMock);

        assertTrue("UserManager will delete a user if it exists",
                userManager.deleteUser("Test1"));
    }

    @Test
    public void deleteUserDoesNotExist() {
        assertFalse("UserManager will not attempt a delete of a user that does not exist",
                userManager.deleteUser("Test1"));
    }

    @Test
    public void deleteGuest() {
        when(userPersistenceMock.getUserByUsername(any())).thenReturn(userMock);

        assertFalse("UserManager will never delete the guest user",
                userManager.deleteUser("guest"));
    }

    /*
     * Test incrementLoginCount()
     */
    @Test
    public void incrementLoginCount() {
        when(userPersistenceMock.getUserByUsername("Test")).thenReturn(userMock);

        userManager.incrementLoginCount("Test");

        // UserManager can increment the login value of a user
        verify(userPersistenceMock, times(1)).incrementLoginCount(userMock);
    }

    @Test
    public void incrementLoginCountNotManaged() {
        when(userPersistenceMock.getUserByUsername("Test")).thenReturn(null);

        userManager.incrementLoginCount("Test");

        // UserManager cannot increment the login value of a non-managed user
        verify(userPersistenceMock, times(0)).incrementLoginCount(userMock);
    }


    /*
     * Test getUserLoginCount()
     */
    @Test
    public void getUserLoginCount() {
        when(userPersistenceMock.getUserByUsername(any())).thenReturn(userMock);
        when(userMock.getLoginCount()).thenReturn(3);

        assertEquals("UserManager can retrieve the login count of a managed user",
                3, userManager.getUserLoginCount("Test"));
    }

    @Test
    public void getUserLoginCountNotManaged() {
        when(userPersistenceMock.getUserByUsername(any())).thenReturn(null);

        assertEquals("UserManager cannot retrieve the login count of a non-managed user",
                -1, userManager.getUserLoginCount("Test"));
    }
}
