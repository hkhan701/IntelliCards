package comp3350.intellicards.Business;

import comp3350.intellicards.Application.Services;
import comp3350.intellicards.Objects.User;
import comp3350.intellicards.Persistence.UserPersistence;

public class UserManager {
    private UserPersistence userPersistence;

    public UserManager() {
        userPersistence = Services.getUserPersistence();
    }

    public boolean registerUser(String username, String password) {
        if (userPersistence.getUserByUsername(username) != null || username.equals("guest")) {
            return false; // Username already exists Should throw some sort of exception in future
        }
        userPersistence.addUser(new User(username, password));
        return true;
    }

    public User loginUser(String username, String password) {
        User user = userPersistence.getUserByUsername(username);
        System.out.println(user.getUsername());
        System.out.println(user.getPassword());

        if (user != null && user.getPassword().equals(password)) {

            return user;
        }
        return null;
    }

    public void deleteUser(String username) {
        userPersistence.deleteUser(username);
    }
}
