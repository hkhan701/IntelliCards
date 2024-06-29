package comp3350.intellicards.Persistence;

import java.util.List;

import comp3350.intellicards.Objects.User;

public interface UserPersistence {
    User getUserByUsername(String username);
    void addUser(User user);
    void deleteUser(String username);
}
