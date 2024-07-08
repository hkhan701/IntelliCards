package comp3350.intellicards.Persistence.hsqldb;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
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
        final String setUUID = rs.getString("setUUID");
        final String username = rs.getString("username");
        final String setName = rs.getString("setName");
        return new FlashcardSet(setUUID, username, setName);
    }

    private void addFlashcardsToSet(String setUUID, Connection c, FlashcardSet flashcardSet) throws SQLException {
        final PreparedStatement stCards = c.prepareStatement("SELECT * FROM FLASHCARDS WHERE setUUID = ?");
        stCards.setString(1, setUUID);

        try (ResultSet rsCards = stCards.executeQuery()) {
            while (rsCards.next()) {
                Flashcard flashcard = new Flashcard(
                        rsCards.getString("cardUUID"),
                        rsCards.getString("setUUID"),
                        rsCards.getString("question"),
                        rsCards.getString("answer"),
                        rsCards.getString("hint"),
                        rsCards.getBoolean("deleted"),
                        rsCards.getInt("attempts"),
                        rsCards.getInt("correct")
                );
                flashcardSet.addFlashcard(flashcard);
            }
        } finally {
            stCards.close();
        }
    }
    @Override
    public FlashcardSet getFlashcardSet(String uuid) {
        try (final Connection c = connection()) {
            final PreparedStatement stSet = c.prepareStatement("SELECT * FROM FLASHCARDSETS WHERE setUUID = ?");
            stSet.setString(1, uuid);

            FlashcardSet flashcardSet = null;

            try (ResultSet rsSet = stSet.executeQuery()) {
                if (rsSet.next()) {
                    flashcardSet = fromResultSet(rsSet);
                    addFlashcardsToSet(uuid, c, flashcardSet);
                }
            } finally {
                stSet.close();
            }

            return flashcardSet;
        } catch (SQLException e) {
            throw new PersistenceException(e);
        }
    }

    @Override
    public List<FlashcardSet> getAllFlashcardSets() {
        List<FlashcardSet> flashcardSets = new ArrayList<>();

        try (final Connection c = connection()) {
            final PreparedStatement st = c.prepareStatement("SELECT SETUUID FROM FLASHCARDSETS");

            final ResultSet rs = st.executeQuery();
            while (rs.next()) {
                final FlashcardSet flashcardSet = getFlashcardSet(rs.getString("setUUID"));
                flashcardSets.add(flashcardSet);
            }

            rs.close();
            st.close();

            return flashcardSets;
        } catch (SQLException e) {
            throw new PersistenceException(e);
        }
    }

    @Override
    public void insertFlashcardSet(FlashcardSet newFlashcardSet) {
        try (final Connection c = connection()) {
            final PreparedStatement st = c.prepareStatement("INSERT INTO FLASHCARDSETS VALUES(?, ?, ?)");

            st.setString(1, newFlashcardSet.getUUID());
            st.setString(2, newFlashcardSet.getUsername());
            st.setString(3, newFlashcardSet.getFlashcardSetName());

            st.executeUpdate();
            st.close();
        } catch (SQLException e) {
            throw new PersistenceException(e);
        }
    }

    // this might need functinoality or taken out, we are inserting the flashcard
    // using the flashcard persistence, since we are adding the setUUID field there
    @Override
    public boolean addFlashcardToFlashcardSet(String setUUID, Flashcard flashcard) {
        try (final Connection c = connection()) {
            FlashcardSet flashcardSet = getFlashcardSet(setUUID);

            if (flashcardSet != null) {
                flashcardSet.addFlashcard(flashcard);
                return true;
            }
        } catch (SQLException e) {
            throw new PersistenceException(e);
        }
        return false;
    }

}
