package comp3350.intellicards.Application;

public class UserSession {
    private static UserSession instance;
    private String username;

    public static final String GUEST_USERNAME = "guest";

    private UserSession() {
        // Private constructor to prevent instantiation
    }

    public static synchronized UserSession getInstance() {
        if (instance == null) {
            instance = new UserSession();
        }
        return instance;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public boolean isGuest(String username) {
        return username.equals(GUEST_USERNAME);
    }

    public void setGuest() {
        username = GUEST_USERNAME;
    }

}