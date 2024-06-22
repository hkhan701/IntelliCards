package comp3350.intellicards.Persistence.stubs;

import java.util.ArrayList;
import java.util.List;

import comp3350.intellicards.Objects.User;
import comp3350.intellicards.Persistence.UserPersistence;

public class UserPersistenceStub implements UserPersistence{
    private List<User> users;

    public UserPersistenceStub() {
        users = new ArrayList<User>();

        users.add(new User("user1", "password1"));
        users.add(new User("user2", "password2"));
        users.add(new User("user3", "password3"));
        users.add(new User("user4", "password4"));
        users.add(new User("user5", "password5"));
    }
    @Override
    public List<User> getAllUsers() {
        return users;
    }

    @Override
    public User getUser(String uuid) {
        User retUser = null;

        for(User user : users) {
            if(user.getUUID().equals(uuid)) {
                retUser = user;
            }
        }

        return retUser;
    }

    @Override
    public void addUser(User user) {
        users.add(user);
    }

    @Override
    public void updateUser(User user) {
        for(User u : users) {
            if(u.getUUID().equals(user.getUUID())) {
                u.setPassword(user.getUsername());
                u.setPassword(user.getPassword());
            }
        }
    }

    @Override
    public void deleteUser(User user) {
        users.removeIf(u -> u.getUUID().equals(user.getUUID()));
    }
}
