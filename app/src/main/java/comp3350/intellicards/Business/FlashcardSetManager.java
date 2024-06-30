package comp3350.intellicards.Business;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

import comp3350.intellicards.Application.Services;
import comp3350.intellicards.Objects.Flashcard;
import comp3350.intellicards.Objects.FlashcardSet;
import comp3350.intellicards.Persistence.FlashcardSetPersistence;

public class FlashcardSetManager {
    private FlashcardSetPersistence flashcardSetPersistence;

    public FlashcardSetManager() {
        flashcardSetPersistence = Services.getFlashcardSetPersistence();
    }

    public FlashcardSet getFlashcardSet(String uuid) {
        return this.flashcardSetPersistence.getFlashcardSet(uuid);
    }

    public FlashcardSet getActiveFlashcardSet(String uuid) {
        return this.flashcardSetPersistence.getActiveFlashcardSet(uuid);
    }

    public FlashcardSet getDeletedFlashcardSet(String uuid) {
        return this.flashcardSetPersistence.getDeletedFlashcardSet(uuid);
    }

    public List<FlashcardSet> getAllFlashcardSets() {
        return this.flashcardSetPersistence.getAllFlashcardSets();
    }

    public void insertFlashcardSet(FlashcardSet newFlashcardSet) {
        this.flashcardSetPersistence.insertFlashcardSet(newFlashcardSet);
    }

    public boolean addFlashcardToFlashcardSet(@NonNull String setUUID, @NonNull Flashcard flashcard) {
        return this.flashcardSetPersistence.addFlashcardToFlashcardSet(setUUID, flashcard);
    }

    public void shuffleFlashcardSet(FlashcardSet flashcardSet) {
        this.flashcardSetPersistence.randomizeFlashcardSet(flashcardSet);
    }

    public List<FlashcardSet> getFlashcardSetsByUsername(String username) {
        List<FlashcardSet> flashcardSets = this.flashcardSetPersistence.getAllFlashcardSets();
        List<FlashcardSet> userSets = new ArrayList<>();

        for (FlashcardSet flashcardSet : flashcardSets) {
            if (flashcardSet.getUsername().equals(username)) {
                userSets.add(flashcardSet);
            }
        }

        return userSets;
    }

}

