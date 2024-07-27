package comp3350.intellicards.Persistence.stubs;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import comp3350.intellicards.Objects.Flashcard;
import comp3350.intellicards.Objects.FlashcardSet;
import comp3350.intellicards.Persistence.FlashcardSetPersistence;
import comp3350.intellicards.Persistence.hsqldb.PersistenceException;

public class FlashcardSetPersistenceStub implements FlashcardSetPersistence {

    private Map<String, FlashcardSet> flashcardSets;

    public FlashcardSetPersistenceStub() {
        flashcardSets = new LinkedHashMap<>();

        FlashcardSet set1 = new FlashcardSet("set1", "user1", "Math");
        FlashcardSet set2 = new FlashcardSet("set2", "user1", "Science");
        FlashcardSet set3 = new FlashcardSet("set3", "user2", "History");
        FlashcardSet set4 = new FlashcardSet("set4", "user3", "Geography");

        flashcardSets.put(set1.getUUID(), set1);
        flashcardSets.put(set2.getUUID(), set2);
        flashcardSets.put(set3.getUUID(), set3);
        flashcardSets.put(set4.getUUID(), set4);
    }

    @Override
    public FlashcardSet getFlashcardSet(String uuid) {
        return flashcardSets.get(uuid);
    }

    @Override
    public List<FlashcardSet> getAllFlashcardSets() {
        return new ArrayList<>(flashcardSets.values());
    }

    @Override
    public List<FlashcardSet> getFlashcardSetsByKey(String key) {
        List<FlashcardSet> searchedFlashcardSets = new ArrayList<>();

        for(FlashcardSet set : flashcardSets.values()) {
            if(set.getFlashcardSetName().contains(key)) {
                searchedFlashcardSets.add(set);
            }
        }

        return searchedFlashcardSets;
    }

    public boolean addFlashcardToFlashcardSet(String setUUID, Flashcard flashcard) {
        FlashcardSet set = flashcardSets.get(setUUID);
        if (set != null) {
            set.addFlashcard(flashcard);
            return true;
        }
        return false;
    }

    @Override
    public void insertFlashcardSet(FlashcardSet newFlashcardSet) {
        flashcardSets.put(newFlashcardSet.getUUID(), newFlashcardSet);
    }

}