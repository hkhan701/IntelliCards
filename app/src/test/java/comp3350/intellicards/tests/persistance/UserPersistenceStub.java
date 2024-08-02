package comp3350.intellicards.tests.persistance;

import java.util.ArrayList;
import java.util.List;

import comp3350.intellicards.Application.UserSession;
import comp3350.intellicards.Objects.User;
import comp3350.intellicards.Persistence.UserPersistence;

abstract public class UserPersistenceStub implements UserPersistence {
    private List<User> users;
    private boolean mockInitialized;

    public UserPersistenceStub() {
        users = new ArrayList<>();
        mockInitialized = false;
    }

    public void mockData() {
        User user1 = new User(UserSession.GUEST_USERNAME, "");
        User user2 = new User("user1", "pass1");
        User user3 = new User("user2", "pass2");
        User user4 = new User("user3", "pass3");

        users.add(user1);
        users.add(user2);
        users.add(user3);
        users.add(user4);

        mockInitialized = true;
    }

    public boolean isMockInitialized() {
        return mockInitialized;
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
    public void deleteUser(String username) {
        users.removeIf(u -> u.getUsername().equals(username));
    }
}
