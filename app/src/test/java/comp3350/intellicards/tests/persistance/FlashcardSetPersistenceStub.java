package comp3350.intellicards.tests.persistance;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import comp3350.intellicards.Objects.Flashcard;
import comp3350.intellicards.Objects.FlashcardSet;
import comp3350.intellicards.Persistence.FlashcardSetPersistence;

public class FlashcardSetPersistenceStub implements FlashcardSetPersistence {

    private Map<String, FlashcardSet> flashcardSets;
    private boolean mockInitialized;

    public FlashcardSetPersistenceStub() {
        flashcardSets = new LinkedHashMap<>();
        mockInitialized = false;
    }

    public void mockData() {
        FlashcardSet set1 = new FlashcardSet("set1", "user1", "Math");
        FlashcardSet set2 = new FlashcardSet("set2", "user1", "Science");
        FlashcardSet set3 = new FlashcardSet("set3", "user2", "History");
        FlashcardSet set4 = new FlashcardSet("set4", "user3", "Geography");

        flashcardSets.put(set1.getUUID(), set1);
        flashcardSets.put(set2.getUUID(), set2);
        flashcardSets.put(set3.getUUID(), set3);
        flashcardSets.put(set4.getUUID(), set4);

        mockInitialized = true;
    }

    public boolean isMockInitialized() {
        return mockInitialized;
    }

    @Override
    public FlashcardSet getFlashcardSet(String uuid) {
        return flashcardSets.get(uuid);
    }

    @Override
    public FlashcardSet getActiveFlashcardSet(String uuid) {
        FlashcardSet set = flashcardSets.get(uuid);
        if (set != null) {
            return set.getActiveFlashcards();
        }

        return null;
    }

    @Override
    public FlashcardSet getDeletedFlashcardSet(String uuid) {
        FlashcardSet set = flashcardSets.get(uuid);
        if (set != null) {
            return set.getDeletedFlashcards();
        }

        return null;
    }

    @Override
    public List<FlashcardSet> getAllFlashcardSets() {
        return new ArrayList<>(flashcardSets.values());
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