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
    public List<Flashcard> getFlashcardsByKey(String key) {
        List<Flashcard> flashcards = new ArrayList<>();

        try (Connection c = connection()) {
            final PreparedStatement st = c.prepareStatement("SELECT * FROM FLASHCARDS WHERE deleted = FALSE AND (LOWER(question) LIKE LOWER(?) OR LOWER(answer) LIKE LOWER(?) OR LOWER(hint) LIKE LOWER(?))");
            st.setString(1, "%" + key + "%");
            st.setString(2, "%" + key + "%");
            st.setString(3, "%" + key + "%");

            final ResultSet rs = st.executeQuery();

            while (rs.next()) {
                final Flashcard flashcard = getFlashcard(rs.getString("cardUUID"));
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
    public void insertFlashcard(@NonNull Flashcard currentFlashcard) {
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
        } catch (final SQLException e) {
            throw new PersistenceException(e);
        }
    }

    @Override
    public void updateFlashcard(Flashcard currentFlashcard) {
        try (final Connection c = connection()) {
            final PreparedStatement st = c.prepareStatement("UPDATE FLASHCARDS SET setUUID = ?, question = ?, answer = ?, hint = ? WHERE cardUUID = ?");
            st.setString(1, currentFlashcard.getSetUUID());
            st.setString(2, currentFlashcard.getQuestion());
            st.setString(3, currentFlashcard.getAnswer());
            st.setString(4, currentFlashcard.getHint());
            st.setString(5, currentFlashcard.getUUID());

            st.executeUpdate();
        } catch (SQLException e) {
            throw new PersistenceException(e);
        }
    }

    @Override
    public void markFlashcardAsDeleted(String uuid) {

        try (final Connection c = connection()) {
            final PreparedStatement st = c.prepareStatement("UPDATE FLASHCARDS SET deleted = TRUE WHERE cardUUID = ?");
            st.setString(1, uuid);

            st.executeUpdate();
        } catch (SQLException e) {
            throw new PersistenceException(e);
        }
    }

    @Override
    public void restoreFlashcard(String uuid) {
        try (final Connection c = connection()) {
            final PreparedStatement st = c.prepareStatement("UPDATE FLASHCARDS SET deleted = FALSE WHERE cardUUID = ?");
            st.setString(1, uuid);

            st.executeUpdate();
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
