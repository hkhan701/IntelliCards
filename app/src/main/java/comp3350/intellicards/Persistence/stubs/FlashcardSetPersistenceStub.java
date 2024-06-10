package comp3350.intellicards.Persistence.stubs;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import comp3350.intellicards.Objects.Flashcard;
import comp3350.intellicards.Objects.FlashcardSet;
import comp3350.intellicards.Persistence.FlashcardSetPersistence;

public class FlashcardSetPersistenceStub implements FlashcardSetPersistence {

    private Map<String, FlashcardSet> flashcardSets;

    public FlashcardSetPersistenceStub() {
        flashcardSets = new HashMap<>();
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

    public boolean addFlashcardToFlashcardSet(FlashcardSet flashcardSet, Flashcard flashcard) {
        FlashcardSet set = flashcardSets.get(flashcardSet.getUUID());
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