package comp3350.intellicards.Persistence.hsqldb;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import comp3350.intellicards.Objects.Flashcard;
import comp3350.intellicards.Objects.FlashcardSet;
import comp3350.intellicards.Persistence.FlashcardSetPersistence;

public class FlashcardSetPersistenceHSQLDB implements FlashcardSetPersistence {
    private final String dbPath;

    public FlashcardSetPersistenceHSQLDB(final String dbPath) {
        this.dbPath = dbPath;
    }

    private Connection connection() throws SQLException {
        return DriverManager.getConnection("jdbc:hsqldb:file:" + dbPath + ";shutdown=true", "SA", "");
    }

    private FlashcardSet fromResultSet(final ResultSet rs) throws SQLException {
        final String setName = rs.getString("setName");

        return new FlashcardSet("", setName);
    }

    @Override
    public FlashcardSet getFlashcardSet(String flashcardSetUUID) {
        return null;
    }

    @Override
    public FlashcardSet getActiveFlashcardSet(String uuid) {
        return null;
    }

    @Override
    public List<FlashcardSet> getAllFlashcardSets() {
        return null;
    }

    @Override
    public void insertFlashcardSet(FlashcardSet newFlashcardSet) {

    }

    @Override
    public boolean addFlashcardToFlashcardSet(FlashcardSet flashcardSet, Flashcard flashcard) {
        return false;
    }

    @Override
    public void randomizeFlashcardSet(FlashcardSet flashcardSet) {

    }
}
