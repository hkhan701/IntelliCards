package comp3350.intellicards.Persistence.stubs;

import java.util.ArrayList;
import java.util.List;

import comp3350.intellicards.Objects.User;
import comp3350.intellicards.Persistence.UserPersistence;

public class UserPersistenceStub implements UserPersistence{
    private List<User> users;

    public UserPersistenceStub() {
        users = new ArrayList<User>();

        User user1 = new User("user1", "password1");
        User user2 = new User("user2", "password2");
        User user3 = new User("user3", "password3");

        users.add(user1);
        users.add(user2);
        users.add(user3);
    }

    @Override
    public User getUserByUsername(String username) {
        for (User user : users) {
            if (user.getUsername().equals(username)) {
                return user;
            }
        }
        return null;
    }

    @Override
    public void addUser(User user) {
        users.add(user);
    }

    @Override
    public void deleteUser(User user) {
        users.removeIf(u -> u.getUsername().equals(user.getUsername()));
    }
}
