package comp3350.intellicards.tests.persistance;

import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;

import comp3350.intellicards.Application.Configuration;
import comp3350.intellicards.Business.UserManager;
import comp3350.intellicards.Persistence.hsqldb.UserPersistenceHSQLDB;
import comp3350.intellicards.tests.utils.TestUtils;

public class AccessUsersTest {
    private static File tempDB;
    private static UserPersistenceHSQLDB persistence;
    private static UserManager manager;

    @Before
    public void setUp() throws IOException {
        tempDB = TestUtils.copyTestDB(false);

        persistence = new UserPersistenceHSQLDB(Configuration.getDBPathName());
        manager = new UserManager(persistence);
    }

    /*
     * Test registerUser()
     */
    @Test
    public void registerUser() {}

    @Test
    public void registerUserDuplicate() {}

    @Test
    public void registerUserGuest() {}


    /*
     * Test loginUser()
     */
    @Test
    public void loginUserMatch() {}

    @Test
    public void loginUserFail() {}

    @Test
    public void loginUserDoesNotExist() {}


    /*
     * Test deleteUser()
     */
    @Test
    public void deleteUser() {}

    @Test
    public void deleteUserNotExist() {}

    @Test
    public void deleteGuest() {}

    /*
     * Test incrementLoginCount()
     */
    @Test
    public void incrementLogin() {}

    @Test
    public void incrementLoginUserNotExist() {}

    /*
     * Test getUserLoginCount()
     */
    @Test
    public void getUserLoginCount() {}

    @Test
    public void getUserLoginCountNotExist() {}
}
