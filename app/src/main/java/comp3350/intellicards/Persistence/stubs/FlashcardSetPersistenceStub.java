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

    public boolean addFlashCardToFlashcardSet(FlashcardSet flashcardSet, Flashcard flashcard) {
        FlashcardSet set = flashcardSets.get(flashcardSet.getUUID());
        if (set != null) {
            set.addFlashCard(flashcard);
            return true;
        }
        return false;
    }

    @Override
    public boolean insertFlashcardSet(FlashcardSet newFlashcardSet) {
        boolean inserted = false;

        /* hashmap.put returns null if an item with a new key is inserted
            since each set will have a unique id, successful insertions will return null
         */
        if (flashcardSets.put(newFlashcardSet.getUUID(), newFlashcardSet) == null)
            inserted = true;

        return inserted;
    }
}