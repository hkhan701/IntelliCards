package comp3350.intellicards.Persistence.stubs;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import comp3350.intellicards.Objects.Flashcard;
import comp3350.intellicards.Objects.FlashcardSet;
import comp3350.intellicards.Persistence.FlashcardSetPersistence;

public class FlashcardSetPersistenceStub implements FlashcardSetPersistence {

    private Map<String, FlashcardSet> flashcardSets;

    public FlashcardSetPersistenceStub() {
        flashcardSets = new LinkedHashMap<>();

        FlashcardSet set1 = new FlashcardSet("0", "user1", "Set 1");
        FlashcardSet set2 = new FlashcardSet("1", "user1", "Set 2");
        FlashcardSet set3 = new FlashcardSet("2", "user1", "Set 3");

        flashcardSets.put(set1.getUUID(), set1);
        flashcardSets.put(set2.getUUID(), set2);
        flashcardSets.put(set3.getUUID(), set3);
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

    @Override
    public void randomizeFlashcardSet(FlashcardSet set) {
        if (set != null) {
            set.randomizeSet();
        }
    }
}