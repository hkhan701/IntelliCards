package comp3350.intellicards.Persistence.hsqldb;

import androidx.annotation.NonNull;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
        final String uuid = rs.getString("cardUUID");
        final String setUUID = rs.getString("setUUID");
        final String question = rs.getString("question");
        final String answer = rs.getString("answer");
        final String hint = rs.getString("hint");
        final boolean deleted = rs.getBoolean("deleted");
        final int attempted = rs.getInt("attempts");
        final int correct = rs.getInt("correct");
        return new Flashcard(uuid, setUUID, question, answer, hint, deleted, attempted, correct);
    }

    @Override
    public List<Flashcard> getAllActiveFlashcards(String setUUID) {
        List<Flashcard> flashcards = new ArrayList<>();

        try (Connection c = connection()) {
            final PreparedStatement st = c.prepareStatement("SELECT * FROM FLASHCARDS WHERE setUUID = ? AND deleted = FALSE");
            st.setString(1, setUUID);

            final ResultSet rs = st.executeQuery();

            while (rs.next()) {
                final Flashcard flashcard = fromResultSet(rs);
                flashcards.add(flashcard);
            }
            rs.close();
            st.close();

            return flashcards;
        } catch (SQLException e) {
            throw new PersistenceException(e);
        }
    }

    @Override
    public List<Flashcard> getAllDeletedFlashcards() {
        List<Flashcard> flashcards = new ArrayList<>();

        try (Connection c = connection()) {
            final PreparedStatement st = c.prepareStatement("SELECT * FROM FLASHCARDS WHERE deleted = TRUE");
            final ResultSet rs = st.executeQuery();

            while (rs.next()) {
                final Flashcard flashcard = fromResultSet(rs);
                flashcards.add(flashcard);
            }
            rs.close();
            st.close();

            return flashcards;
        } catch (SQLException e) {
            throw new PersistenceException(e);
        }
    }

    @Override
    public Flashcard getFlashcard(String uuid) {
        try (Connection c = connection()) {
            final PreparedStatement st = c.prepareStatement("SELECT * FROM FLASHCARDS WHERE cardUUID = ?");
            st.setString(1, uuid);

            final ResultSet rs = st.executeQuery();

            Flashcard flashcard = null;

            if(rs.next())
                flashcard = fromResultSet(rs);

            rs.close();
            st.close();

            return flashcard;
        } catch (SQLException e) {
            throw new PersistenceException(e);
        }
    }

    @Override
    public Flashcard insertFlashcard(@NonNull Flashcard currentFlashcard) {
        try (final Connection c = connection()) {
            final PreparedStatement st = c.prepareStatement("INSERT INTO FLASHCARDS VALUES(?, ?, ?, ?, ?, ?, ?, ?)");
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
            throw new PersistenceException(e);
        }
    }

    @Override
    public Flashcard updateFlashcard(Flashcard currentFlashcard) {
        try (final Connection c = connection()) {
            final PreparedStatement st = c.prepareStatement("UPDATE FLASHCARDS SET setUUID = ?, question = ?, answer = ?, hint = ? WHERE cardUUID = ?");
            st.setString(1, currentFlashcard.getSetUUID());
            st.setString(2, currentFlashcard.getQuestion());
            st.setString(3, currentFlashcard.getAnswer());
            st.setString(4, currentFlashcard.getHint());
            st.setString(5, currentFlashcard.getUUID());

            st.executeUpdate();

            return currentFlashcard;
        } catch (SQLException e) {
            throw new PersistenceException(e);
        }
    }

    @Override
    public boolean markFlashcardAsDeleted(String uuid) {

        try (final Connection c = connection()) {
            final PreparedStatement st = c.prepareStatement("UPDATE FLASHCARDS SET deleted = TRUE WHERE cardUUID = ?");
            st.setString(1, uuid);

            st.executeUpdate();

            return true;
        } catch (SQLException e) {
            throw new PersistenceException(e);
        }
    }

    @Override
    public boolean restoreFlashcard(String uuid) {
        try (final Connection c = connection()) {
            final PreparedStatement st = c.prepareStatement("UPDATE FLASHCARDS SET deleted = FALSE WHERE cardUUID = ?");
            st.setString(1, uuid);

            st.executeUpdate();

            return true;
        } catch (SQLException e) {
            throw new PersistenceException(e);
        }
    }

    @Override
    public void markAttempted(String uuid) {
        try (final Connection c = connection()) {
            final PreparedStatement st = c.prepareStatement("UPDATE FLASHCARDS SET attempts = attempts + 1 WHERE cardUUID = ?");
            st.setString(1, uuid);

            st.executeUpdate();
        } catch (SQLException e) {
            throw new PersistenceException(e);
        }
    }

    @Override
    public void markAttemptedAndCorrect(String uuid) {
        try (final Connection c = connection()) {
            final PreparedStatement st = c.prepareStatement("UPDATE FLASHCARDS SET attempts = attempts + 1, correct = correct + 1 WHERE cardUUID = ?");
            st.setString(1, uuid);

            st.executeUpdate();
        } catch (SQLException e) {
            throw new PersistenceException(e);
        }
    }
}
