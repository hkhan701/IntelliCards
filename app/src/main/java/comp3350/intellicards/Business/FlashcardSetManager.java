package comp3350.intellicards.Business;
import java.util.List;

import comp3350.intellicards.Objects.Flashcard;
import comp3350.intellicards.Objects.FlashcardSet;
import comp3350.intellicards.Persistence.FlashcardSetPersistence;
import comp3350.intellicards.Persistence.stubs.FlashcardSetPersistenceStub;
import comp3350.intellicards.R;

public class FlashcardSetManager {
    private FlashcardSetPersistence flashcardSetPersistence;

    public FlashcardSetManager() {
        flashcardSetPersistence = new FlashcardSetPersistenceStub();
    }

    public FlashcardSetManager(FlashcardSetPersistence flashcardSetPersistence) {
        this.flashcardSetPersistence = flashcardSetPersistence;
    }

    FlashcardSet getFlashcardSet(String id) {
        return this.flashcardSetPersistence.getFlashcardSet(id);
    }

    FlashcardSet getActiveFlashcardSet(String id) {
        return this.flashcardSetPersistence.getActiveFlashcardSet(id);
    }

    List<FlashcardSet> getAllFlashcardSets() {
        return this.flashcardSetPersistence.getAllFlashcardSets();
    }

    boolean insertFlashcardSet(FlashcardSet newFlashcardSet) {
        return this.flashcardSetPersistence.insertFlashcardSet(newFlashcardSet);
    }

    boolean addFlashCardToFlashcardSet(FlashcardSet flashcardSet, Flashcard flashcard) {
        return this.addFlashCardToFlashcardSet(flashcardSet, flashcard);
    }
}
