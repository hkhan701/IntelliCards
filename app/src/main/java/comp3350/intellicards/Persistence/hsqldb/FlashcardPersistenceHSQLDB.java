package comp3350.intellicards.Persistence.hsqldb;

import androidx.annotation.NonNull;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import comp3350.intellicards.Objects.Flashcard;
import comp3350.intellicards.Persistence.FlashcardPersistence;

public class FlashcardPersistenceHSQLDB implements FlashcardPersistence {
    private final String dbPath;

    public FlashcardPersistenceHSQLDB(final String dbPath) {
        this.dbPath = dbPath;
    }

    private Connection connection() throws SQLException {
        return DriverManager.getConnection("jdbc:hsqldb:file:" + dbPath + ";shutdown=true", "SA", "");
    }

    private Flashcard fromResultSet(final ResultSet rs) throws SQLException {
        final String setUUID = rs.getString("setUUID");
        final String answer = rs.getString("answer");
        final String question = rs.getString("question");
        final String hint = rs.getString("hint");

        return new Flashcard(setUUID, answer, question, hint);
    }

    @Override
    public List<Flashcard> getAllActiveFlashcards(String setUUID) {
        List<Flashcard> flashcards = new ArrayList<>();

        try (Connection c = connection()) {
            final PreparedStatement st = c.prepareStatement("SELECT * FROM FLASHCARDS WHERE setUUID = ? AND deleted = '0'");
            st.setString(1, setUUID);

            final ResultSet rs = st.executeQuery();

            while (rs.next()) {
                final Flashcard flashcard = fromResultSet(rs);
                flashcards.add(flashcard);
            }
            rs.close();
            st.close();

            return flashcards;
        }
        catch (SQLException e) {
            e.printStackTrace();
            //throw new PersistenceException(e);
        }

        //Delete this return statement after implementing exception
        return null;
    }

    @Override
    public List<Flashcard> getAllDeletedFlashcards() {
        List<Flashcard> flashcards = new ArrayList<>();

        try (Connection c = connection()) {
            final PreparedStatement st = c.prepareStatement("SELECT * FROM FLASHCARDS WHERE deleted = '1'");
            final ResultSet rs = st.executeQuery();

            while (rs.next()) {
                final Flashcard flashcard = fromResultSet(rs);
                flashcards.add(flashcard);
            }
            rs.close();
            st.close();

            return flashcards;
        }
        catch (SQLException e) {
            e.printStackTrace();
            //throw new PersistenceException(e);
        }
        //Delete this return statement after implementing exception
        return null;
    }

    @Override
    public Flashcard getFlashcard(String uuid) {
        try (Connection c = connection()) {
            final PreparedStatement st = c.prepareStatement("SELECT * FROM FLASHCARDS WHERE cardID = ?");
            st.setString(1, uuid);

            final ResultSet rs = st.executeQuery();

            final Flashcard flashcard = fromResultSet(rs);

            rs.close();
            st.close();

            return flashcard;
        }
        catch (SQLException e) {
            e.printStackTrace();
            //throw new PersistenceException(e);
        }

        //Delete this return statement after implementing exception
        return null;
    }

    @Override
    public Flashcard insertFlashcard(@NonNull Flashcard currentFlashcard) {
        try (final Connection c = connection()) {
            final PreparedStatement st = c.prepareStatement("INSERT INTO students VALUES(?, ?, ?, ?, ?, ?, ?, ?)");
            st.setString(1, currentFlashcard.getUUID());
            st.setString(2, currentFlashcard.getSetUUID());
            st.setString(3, currentFlashcard.getQuestion());
            st.setString(4, currentFlashcard.getAnswer());
            st.setString(5, currentFlashcard.getHint());
            st.setBoolean(6, currentFlashcard.isDeleted());
            st.setInt(7, currentFlashcard.getAttempted());
            st.setInt(8, currentFlashcard.getCorrect());

            st.executeUpdate();

            return currentFlashcard;
        } catch (final SQLException e) {
            e.printStackTrace();
            //throw new PersistenceException(e);
        }

        //Delete this return statement after implementing exception
        return null;
    }

    @Override
    public Flashcard updateFlashcard(Flashcard currentFlashcard) {
        try (final Connection c = connection()) {
            final PreparedStatement st = c.prepareStatement("UPDATE FLASHCARDS SET setUUID = ?, question = ?, answer = ?, hint = ? WHERE cardID = ?");
            st.setString(1, currentFlashcard.getSetUUID());
            st.setString(2, currentFlashcard.getQuestion());
            st.setString(3, currentFlashcard.getAnswer());
            st.setString(4, currentFlashcard.getHint());
            st.setString(5, currentFlashcard.getUUID());

            st.executeUpdate();

            return currentFlashcard;
        }
        catch (SQLException e) {
            e.printStackTrace();
            //throw new PersistenceException(e);
        }
        //Delete this return statement after implementing exception
        return null;
    }

    @Override
    public boolean markFlashcardAsDeleted(String uuid) {

        try (final Connection c = connection()) {
            final PreparedStatement st = c.prepareStatement("UPDATE FLASHCARDS SET deleted = true WHERE cardID = ?");
            st.setString(1, uuid);

            st.executeUpdate();

            return true;
        }
        catch (SQLException e) {
            e.printStackTrace();
            //throw new PersistenceException(e);
        }

        //Delete this return statement after implementing exception
        return false;
    }

    @Override
    public boolean restoreFlashcard(String uuid) {
        try (final Connection c = connection()) {
            final PreparedStatement st = c.prepareStatement("UPDATE FLASHCARDS SET deleted = false WHERE cardID = ?");
            st.setString(1, uuid);

            st.executeUpdate();

            return true;
        }
        catch (SQLException e) {
            e.printStackTrace();
            //throw new PersistenceException(e);
        }

        //Delete this return statement after implementing exception
        return false;
    }

    @Override
    public void markAttempted(String uuid) {
        try (final Connection c = connection()) {
            final PreparedStatement st = c.prepareStatement("UPDATE FLASHCARDS SET attempts = attempts + 1 WHERE cardID = ?");
            st.setString(1, uuid);

            st.executeUpdate();
        }
        catch (SQLException e) {
            e.printStackTrace();
            //throw new PersistenceException(e);
        }
    }

    @Override
    public void markAttemptedAndCorrect(String uuid) {
        try (final Connection c = connection()) {
            final PreparedStatement st = c.prepareStatement("UPDATE FLASHCARDS SET attempts = attempts + 1, correct = correct + 1 WHERE cardID = ?");
            st.setString(1, uuid);

            st.executeUpdate();
        }
        catch (SQLException e) {
            e.printStackTrace();
            //throw new PersistenceException(e);
        }
    }
}
