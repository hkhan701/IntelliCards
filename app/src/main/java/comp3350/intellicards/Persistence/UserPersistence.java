package comp3350.intellicards.Persistence;

import comp3350.intellicards.Objects.User;

public interface UserPersistence {
    User getUserByUsername(String username);

    void addUser(User user);

    void deleteUser(String username);
    void incrementLoginCount(User user);
}
