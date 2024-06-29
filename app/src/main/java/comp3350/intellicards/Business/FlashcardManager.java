package comp3350.intellicards.Business;

import java.util.List;

import comp3350.intellicards.Application.Services;
import comp3350.intellicards.Objects.Flashcard;
import comp3350.intellicards.Persistence.FlashcardPersistence;
import comp3350.intellicards.Persistence.stubs.FlashcardPersistenceStub;

public class FlashcardManager {
    private FlashcardPersistence flashcardPersistence;

    public FlashcardManager() {
        flashcardPersistence = Services.getFlashcardPersistence();
    }

    public List<Flashcard> getAllActiveFlashcards(String setUUID) {
        return this.flashcardPersistence.getAllActiveFlashcards(setUUID);
    }

    public List<Flashcard> getAllDeletedFlashcards() {
        return this.flashcardPersistence.getAllDeletedFlashcards();
    }

    public Flashcard getFlashcard(String uuid) {
        return this.flashcardPersistence.getFlashcard(uuid);
    }

    public Flashcard insertFlashcard(Flashcard currFlashcard) {
        return this.flashcardPersistence.insertFlashcard(currFlashcard);
    }

    public Flashcard updateFlashcard(Flashcard currFlashcard) {
        return this.flashcardPersistence.updateFlashcard(currFlashcard);
    }

    public boolean markFlashcardAsDeleted(String uuid) {
        return this.flashcardPersistence.markFlashcardAsDeleted(uuid);
    }

    public boolean restoreFlashcard(String uuid) {
        return this.flashcardPersistence.restoreFlashcard(uuid);
    }

    public void markAttempted(String uuid) {
        this.flashcardPersistence.markAttempted(uuid);
    }

    public void markAttemptedAndCorrect(String uuid) {
        this.flashcardPersistence.markAttemptedAndCorrect(uuid);
    }

}