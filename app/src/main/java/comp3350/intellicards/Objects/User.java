package comp3350.intellicards.Objects;

import androidx.annotation.NonNull;

public class User {

    private int loginCount;
    private String username;
    private String password;

    public User(@NonNull String username, @NonNull String password) {
        this.username = username;
        this.password = password;
        this.loginCount = 0;

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

    public void setLoginCount(int count)
    {
        this.loginCount = count;
    }

    public void incrementLoginCount() {
        this.loginCount++;
    }

    public int getLoginCount() {
        return this.loginCount;
    }


}
