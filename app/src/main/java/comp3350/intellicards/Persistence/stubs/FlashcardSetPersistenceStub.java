package comp3350.intellicards.Persistence.stubs;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import comp3350.intellicards.Objects.Flashcard;
import comp3350.intellicards.Objects.FlashcardSet;
import comp3350.intellicards.Persistence.FlashcardSetPersistence;

public class FlashcardSetPersistenceStub implements FlashcardSetPersistence {

    private Map<UUID, FlashcardSet> flashcardSets;

    public FlashcardSetPersistenceStub() {
        flashcardSets = new HashMap<>();
    }

    @Override
    public FlashcardSet getFlashcardSet(FlashcardSet currentFlashcardSet) {
        return flashcardSets.get(currentFlashcardSet.getUUID());
    }

    // return a flashcard set with flashcards marked as deleted removed
    public FlashcardSet getActiveFlashcardSet(FlashcardSet currentFlashcardSet) {

        FlashcardSet set = flashcardSets.get(currentFlashcardSet.getUUID());
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
    public FlashcardSet insertFlashcardSet(FlashcardSet newFlashcardSet) {
        flashcardSets.put(newFlashcardSet.getUUID(), newFlashcardSet);
        return newFlashcardSet;
    }
}