package comp3350.intellicards.Persistence;

import java.util.List;

import comp3350.intellicards.Objects.User;

public interface UserPersistence {
    User getUser(String uuid);
    User getUserByUsername(String username);
    void addUser(User user);
    void updateUser(User user);
    void deleteUser(User user);
}
