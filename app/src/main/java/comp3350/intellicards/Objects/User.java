package comp3350.intellicards.Objects;

import androidx.annotation.NonNull;

public class User {

    private String username;
    private String password;

    public User(@NonNull String username, @NonNull String password) {
        this.username = username;
        this.password = password;
    }

    public void setUsername(@NonNull String username) {
        this.username = username;
    }

    public String getUsername() {
        return this.username;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) { this.password = password; }
}
