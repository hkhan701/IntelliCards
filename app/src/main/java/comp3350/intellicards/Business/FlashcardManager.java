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

    public FlashcardManager(final FlashcardPersistence flashcardPersistence) {
        this.flashcardPersistence = flashcardPersistence;
    }

    public List<Flashcard> getAllActiveFlashcards() {
        return this.flashcardPersistence.getAllActiveFlashcards();
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

    public void markAttempted(Flashcard currFlashcard) {
        this.flashcardPersistence.markAttempted(currFlashcard);
    }

    public void markAttemptedAndCorrect(Flashcard currFlashcard) {
        this.flashcardPersistence.markAttemptedAndCorrect(currFlashcard);
    }

}