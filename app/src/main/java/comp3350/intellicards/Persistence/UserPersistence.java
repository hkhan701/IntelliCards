package comp3350.intellicards.Persistence;

import java.util.List;

import comp3350.intellicards.Objects.User;

public interface UserPersistence {
    List<User> getAllUsers();
    User getUser(String uuid);
    void addUser(User user);
    void updateUser(User user);
    void deleteUser(User user);
}
