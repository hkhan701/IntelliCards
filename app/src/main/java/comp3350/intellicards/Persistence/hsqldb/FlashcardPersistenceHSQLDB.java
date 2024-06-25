package comp3350.intellicards.Persistence.hsqldb;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
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
        final String setID = rs.getString("setID");
        final String answer = rs.getString("answer");
        final String question = rs.getString("question");
        final String hint = rs.getString("hint");

        return new Flashcard(setID, answer, question, hint);
    }

    @Override
    public List<Flashcard> getAllActiveFlashcards(String setUUID) {
        return null;
    }

    @Override
    public List<Flashcard> getAllDeletedFlashcards() {
        return null;
    }

    @Override
    public Flashcard getFlashcard(String uuid) {
        return null;
    }

    @Override
    public Flashcard insertFlashcard(Flashcard currentFlashcard) {
        return null;
    }

    @Override
    public Flashcard updateFlashcard(Flashcard currentFlashcard) {
        return null;
    }

    @Override
    public boolean markFlashcardAsDeleted(String uuid) {
        return false;
    }

    @Override
    public boolean restoreFlashcard(String uuid) {
        return false;
    }

    @Override
    public void markAttempted(Flashcard currentFlashcard) {

    }

    @Override
    public void markAttemptedAndCorrect(Flashcard currentFlashcard) {

    }
}
