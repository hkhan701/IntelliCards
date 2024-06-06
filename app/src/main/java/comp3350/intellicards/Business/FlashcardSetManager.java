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

    public FlashcardSet getFlashcardSet(String id) {
        return this.flashcardSetPersistence.getFlashcardSet(id);
    }

    public FlashcardSet getActiveFlashcardSet(String id) {
        return this.flashcardSetPersistence.getActiveFlashcardSet(id);
    }

    public List<FlashcardSet> getAllFlashcardSets() {
        return this.flashcardSetPersistence.getAllFlashcardSets();
    }

    public boolean insertFlashcardSet(FlashcardSet newFlashcardSet) {
        return this.flashcardSetPersistence.insertFlashcardSet(newFlashcardSet);
    }

    public boolean addFlashCardToFlashcardSet(FlashcardSet flashcardSet, Flashcard flashcard) {
        return this.addFlashCardToFlashcardSet(flashcardSet, flashcard);
    }
}
