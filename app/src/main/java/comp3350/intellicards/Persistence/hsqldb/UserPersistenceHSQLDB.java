package comp3350.intellicards.Persistence.hsqldb;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import comp3350.intellicards.Objects.User;
import comp3350.intellicards.Persistence.UserPersistence;

public class UserPersistenceHSQLDB implements UserPersistence {
    private final String dbPath;

    public UserPersistenceHSQLDB(final String dbPath) {
        this.dbPath = dbPath;
    }

    private Connection connection() throws SQLException {
        return DriverManager.getConnection("jdbc:hsqldb:file:" + dbPath + ";shutdown=true", "SA", "");
    }

    private User fromResultSet(final ResultSet rs) throws SQLException {
        final String username = rs.getString("username");
        final String password = rs.getString("password");

        return new User(username, password);
    }

    @Override
    public User getUserByUsername(String username) {
        try (final Connection c = connection()) {
            final PreparedStatement st = c.prepareStatement("SELECT * FROM USERS WHERE username = ?");
            st.setString(1, username);

            final ResultSet rs = st.executeQuery();
            final User user = fromResultSet(rs);

            rs.close();
            st.close();

            return user;
        } catch (final SQLException e) {
            System.out.println("Error getting user");
            //throw new PersistenceException(e);
        }

        //Delete this return statement after implementing exception
        return null;
    }

    @Override
    public void addUser(User user) {
        try (final Connection c = connection()) {
            final PreparedStatement st = c.prepareStatement("INSERT INTO USERS VALUES(?, ?)");
            st.setString(1, user.getUsername());
            st.setString(2, user.getPassword());

            st.executeUpdate();
        } catch (final SQLException e) {
            System.out.println("Error adding user");
            //throw new PersistenceException(e);
        }
    }

    @Override
    public void deleteUser(String username) {
        try (final Connection c = connection()) {
            final PreparedStatement st = c.prepareStatement("DELETE FROM USERS WHERE username = ?");
            st.setString(1, username);

            st.executeUpdate();
        }
        catch (final SQLException e) {
            System.out.println("Error deleting user");
            //throw new PersistenceException(e);
        }
    }
}
