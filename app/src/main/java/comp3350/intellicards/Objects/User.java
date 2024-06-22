package comp3350.intellicards.Objects;

import java.util.UUID;

public class User {

    private final String uuid;
    private String username;
    private String password;

    public User(String username, String password) {
        this.uuid = UUID.randomUUID().toString();
        this.username = username;
        this.password = password;
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
    public String getUUID() {
        return this.uuid;
    }
}
