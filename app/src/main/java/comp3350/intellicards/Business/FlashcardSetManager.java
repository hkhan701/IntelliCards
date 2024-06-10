package comp3350.intellicards.Business;

import androidx.annotation.NonNull;

import java.util.List;

import comp3350.intellicards.Objects.Flashcard;
import comp3350.intellicards.Objects.FlashcardSet;
import comp3350.intellicards.Persistence.FlashcardSetPersistence;
import comp3350.intellicards.Persistence.stubs.FlashcardSetPersistenceStub;

public class FlashcardSetManager {
    private FlashcardSetPersistence flashcardSetPersistence;

    public FlashcardSetManager() {
        flashcardSetPersistence = new FlashcardSetPersistenceStub();

        // Create flashcard sets
        FlashcardSet set1 = new FlashcardSet("Geography");
        FlashcardSet set2 = new FlashcardSet("History");
        FlashcardSet set3 = new FlashcardSet("Science");
        FlashcardSet set4 = new FlashcardSet("Math");
        FlashcardSet set5 = new FlashcardSet("English");

        // Add flashcard sets to persistence
        flashcardSetPersistence.insertFlashcardSet(set1);
        flashcardSetPersistence.insertFlashcardSet(set2);
        flashcardSetPersistence.insertFlashcardSet(set3);
        flashcardSetPersistence.insertFlashcardSet(set4);
        flashcardSetPersistence.insertFlashcardSet(set5);
    }

    public FlashcardSetManager(FlashcardSetPersistence flashcardSetPersistence) {
        this.flashcardSetPersistence = flashcardSetPersistence;
    }

    public FlashcardSet getFlashcardSet(String uuid) {
        return this.flashcardSetPersistence.getFlashcardSet(uuid);
    }

    public FlashcardSet getActiveFlashcardSet(String uuid) {
        return this.flashcardSetPersistence.getActiveFlashcardSet(uuid);
    }

    public List<FlashcardSet> getAllFlashcardSets() {
        return this.flashcardSetPersistence.getAllFlashcardSets();
    }

    public void insertFlashcardSet(FlashcardSet newFlashcardSet) {
        this.flashcardSetPersistence.insertFlashcardSet(newFlashcardSet);
    }

    public boolean addFlashcardToFlashcardSet(@NonNull FlashcardSet flashcardSet, @NonNull Flashcard flashcard) {
        return this.flashcardSetPersistence.addFlashcardToFlashcardSet(flashcardSet, flashcard);
    }
}
