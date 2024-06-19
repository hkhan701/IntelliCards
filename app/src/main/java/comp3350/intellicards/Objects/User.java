package comp3350.intellicards.Objects;

import java.util.UUID;

public class User {
    private static int userNum;

    private final String GUEST_USERNAME = "guestUser" + userNum;
    private final String uuid;
    private String username;
    private String password;


    public User() {
        this.uuid = UUID.randomUUID().toString();
        this.username = GUEST_USERNAME;
        this.password = null;

        userNum++;
    }

    public User(String username, String password) {
        this.uuid = UUID.randomUUID().toString();
        this.username = username;
        this.password = password;

        userNum++;
    }

    public void setUsername(String username) {
        this.username = username;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public String getUsername() {
        return this.username;
    }
    public String getPassword() {
        return this.password;
    }
}
