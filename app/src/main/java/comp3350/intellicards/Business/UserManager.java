package comp3350.intellicards.Business;

import comp3350.intellicards.Application.Services;
import comp3350.intellicards.Application.UserSession;
import comp3350.intellicards.Objects.User;
import comp3350.intellicards.Persistence.UserPersistence;

public class UserManager {
    private UserPersistence userPersistence;

    public UserManager() {
        userPersistence = Services.getUserPersistence();
    }

    public UserManager(UserPersistence persistence) {
        userPersistence = persistence;
    }

    public boolean registerUser(String username, String password) {
        if (userPersistence.getUserByUsername(username) != null || username.equals(UserSession.GUEST_USERNAME)) {
            return false; // Username already exists or it equals 'guest' which is reserved for guest mode
        }
        userPersistence.addUser(new User(username, password));
        return true;
    }

    public User loginUser(String username, String password) {
        User user = userPersistence.getUserByUsername(username);
        if (user != null && user.getPassword().equals(password)) {
            return user;
        }
        return null;
    }

    public boolean deleteUser(String username) {
        if (userPersistence.getUserByUsername(username) != null && !username.equals("guest")) {
            userPersistence.deleteUser(username);
            return true;
        }
        return false;
    }

    public User getUserByUsername(String username) {
        return userPersistence.getUserByUsername(username);
    }
    public void incrementLoginCount(String username)
    {
        User user = userPersistence.getUserByUsername(username);
        if(user != null)
        {
            userPersistence.incrementLoginCount(user);
        }
    }

    public int getUserLoginCount(String username){
        User user = userPersistence.getUserByUsername(username);
        if(user != null)
        {
            return user.getLoginCount();
        }
        return -1;
    }




}
