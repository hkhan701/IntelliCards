package comp3350.intellicards.Business;

import java.util.List;

import comp3350.intellicards.Application.Services;
import comp3350.intellicards.Objects.User;
import comp3350.intellicards.Persistence.UserPersistence;
import comp3350.intellicards.Persistence.stubs.UserPersistenceStub;

public class UserManager {
    private UserPersistence userPersistence;

    public UserManager() {
        userPersistence = Services.getUserPersistence();
    }

    public boolean registerUser(String username, String password) {
        if (userPersistence.getUserByUsername(username) != null) {
            return false; // Username already exists Should throw some sort of exception in future
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

    public void deleteUser(User user) {
        userPersistence.deleteUser(user);
    }
}
