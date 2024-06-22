package comp3350.intellicards.Business;

import java.util.List;

import comp3350.intellicards.Objects.User;
import comp3350.intellicards.Persistence.UserPersistence;
import comp3350.intellicards.Persistence.stubs.UserPersistenceStub;

public class UserManager {
    private UserPersistence userPersistence;

    public UserManager() {
        userPersistence = new UserPersistenceStub();
    }

    public List<User> getAllUsers() {
        return userPersistence.getAllUsers();
    }

    public User getUser(String uuid) {
        return userPersistence.getUser(uuid);
    }

    public void addUser(User user) {
        userPersistence.addUser(user);
    }

    public void updateUser(User user) {
        userPersistence.updateUser(user);
    }

    public void deleteUser(User user) {
        userPersistence.deleteUser(user);
    }
}
