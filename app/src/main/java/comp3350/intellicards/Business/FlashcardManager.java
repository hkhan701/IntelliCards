package comp3350.intellicards.Business;

import comp3350.intellicards.Application.Services;
import comp3350.intellicards.Objects.Flashcard;
import comp3350.intellicards.Objects.FlashcardSet;
import comp3350.intellicards.Persistence.FlashcardPersistence;
import comp3350.intellicards.Persistence.FlashcardSetPersistence;

public class FlashcardManager {
    private FlashcardPersistence flashcardPersistence;
    private FlashcardSetManager flashcardSetManager;

    public FlashcardManager() {
        flashcardPersistence = Services.getFlashcardPersistence();
        flashcardSetManager = new FlashcardSetManager(Services.getFlashcardSetPersistence());
    }

    public FlashcardManager(FlashcardPersistence persistence) {
        flashcardPersistence = persistence;
        flashcardSetManager = new FlashcardSetManager(Services.getFlashcardSetPersistence());
    }

    public FlashcardManager(FlashcardPersistence flashcardPersistence, FlashcardSetPersistence flashcardSetPersistence) {
        this.flashcardPersistence = flashcardPersistence;
        this.flashcardSetManager = new FlashcardSetManager(flashcardSetPersistence);
    }

    public Flashcard getFlashcard(String uuid) {
        return this.flashcardPersistence.getFlashcard(uuid);
    }

    public void insertFlashcard(Flashcard currFlashcard) {
        this.flashcardPersistence.insertFlashcard(currFlashcard);
    }

    public void moveFlashcardToNewSet(Flashcard flashcard, FlashcardSet newSet, String newQuestion, String newAnswer, String newHint) {
        markFlashcardAsDeleted(flashcard.getUUID()); // Soft delete the flashcard

        Flashcard newFlashcard = new Flashcard(newSet.getUUID(), newQuestion, newAnswer, newHint);
        insertFlashcard(newFlashcard); // Insert the new flashcard into the database for the new set
        flashcardSetManager.addFlashcardToFlashcardSet(newSet.getUUID(), newFlashcard);
    }

    public void updateFlashcard(Flashcard flashcard) {
        flashcardPersistence.updateFlashcard(flashcard);
    }

    public void updateFlashcard(Flashcard flashcard, FlashcardSet newSet, String newQuestion, String newAnswer, String newHint) {
        if (newSet != null && !flashcard.getSetUUID().equals(newSet.getUUID())) {
            moveFlashcardToNewSet(flashcard, newSet, newQuestion, newAnswer, newHint);
        } else {
            updateFlashcardDetails(flashcard, newQuestion, newAnswer, newHint);
        }
    }

    private void updateFlashcardDetails(Flashcard flashcard, String newQuestion, String newAnswer, String newHint) {
        flashcard.setQuestion(newQuestion);
        flashcard.setAnswer(newAnswer);
        flashcard.setHint(newHint);
        updateFlashcard(flashcard);
    }

    public void markFlashcardAsDeleted(String uuid) {
        this.flashcardPersistence.markFlashcardAsDeleted(uuid);
    }

    public void restoreFlashcard(String uuid) {
        this.flashcardPersistence.restoreFlashcard(uuid);
    }

    public void markAttempted(String uuid) {
        this.flashcardPersistence.markAttempted(uuid);
    }

    public void markAttemptedAndCorrect(String uuid) {
        this.flashcardPersistence.markAttemptedAndCorrect(uuid);
    }

}