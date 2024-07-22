package comp3350.intellicards.Application;

import android.content.Context;
import android.content.SharedPreferences;

public class UserSession {
    private static UserSession instance;
    private String username;
    private SharedPreferences sharedPreferences;

    public static final String GUEST_USERNAME = "guest";
    private static final String PREF_NAME = "user_session";
    private static final String KEY_USERNAME = "username";

    private UserSession(Context context) {
        sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        username = sharedPreferences.getString(KEY_USERNAME, null);
    }

    public static synchronized UserSession getInstance(Context context) {
        if (instance == null) {
            instance = new UserSession(context);
        }
        return instance;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(KEY_USERNAME, username);
        editor.apply();
    }

    public void logout() {
        username = null;
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove(KEY_USERNAME);
        editor.apply();
    }

    public boolean isGuest(String username) {
        return username.equals(GUEST_USERNAME);
    }

    public void setGuest() {
        setUsername(GUEST_USERNAME);
    }
}
