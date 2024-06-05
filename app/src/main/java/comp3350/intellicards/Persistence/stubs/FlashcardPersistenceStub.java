package comp3350.intellicards.Persistence.stubs;

import comp3350.intellicards.Objects.Flashcard;
import comp3350.intellicards.Persistence.FlashcardPersistence;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FlashcardPersistenceStub implements FlashcardPersistence {

    private Map<String, Flashcard> flashcards;

    public FlashcardPersistenceStub() {
        flashcards = new HashMap<>();
    }

    // Can be used to get all active flashcards for searching capability
    @Override
    public List<Flashcard> getAllActiveFlashcards() {
        return Collections.unmodifiableList(new ArrayList<>(flashcards.values()));
    }

    // Can be used to get all deleted flashcards for the recover view
    @Override
    public List<Flashcard> getAllDeletedFlashcards() {
        List<Flashcard> deletedFlashcards = new ArrayList<>();
        for (Flashcard flashcard : flashcards.values()) {
            if (flashcard.isDeleted()) {
                deletedFlashcards.add(flashcard);
            }
        }
        return Collections.unmodifiableList(deletedFlashcards);
    }

    @Override
    public Flashcard getFlashcard(Flashcard currentFlashcard) {
        return flashcards.get(currentFlashcard.getUUID());
    }

    @Override
    public Flashcard insertFlashcard(Flashcard currentFlashcard) {
        flashcards.put(currentFlashcard.getUUID(), currentFlashcard);
        return currentFlashcard;
    }

    @Override
    public Flashcard updateFlashcard(Flashcard currentFlashcard) {
        flashcards.put(currentFlashcard.getUUID(), currentFlashcard);
        return currentFlashcard;
    }

    @Override
    public boolean markFlashcardAsDeleted(Flashcard currentFlashcard) {
        Flashcard flashcard = flashcards.get(currentFlashcard.getUUID());
        if (flashcard != null) {
            flashcard.markDeleted();
            return true;
        }
        return false;
    }

    @Override
    public boolean restoreFlashcard(Flashcard currentFlashcard) {
        Flashcard flashcard = flashcards.get(currentFlashcard.getUUID());
        if (flashcard != null) {
            flashcard.markRecovered();
            return true;
        }
        return false;
    }

}