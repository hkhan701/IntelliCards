package comp3350.intellicards.Business;

import comp3350.intellicards.Application.Services;
import comp3350.intellicards.Objects.Flashcard;
import comp3350.intellicards.Objects.FlashcardSet;

public class UpdateFlashcardService {

    private FlashcardManager flashcardManager;
    private FlashcardSetManager flashcardSetManager;

    public UpdateFlashcardService() {
        flashcardManager = new FlashcardManager(Services.getFlashcardPersistence());
        flashcardSetManager = new FlashcardSetManager(Services.getFlashcardSetPersistence());
    }

    public UpdateFlashcardService(FlashcardManager flashcardManager, FlashcardSetManager flashcardSetManager) {
        this.flashcardManager = flashcardManager;
        this.flashcardSetManager = flashcardSetManager;
    }

    public void updateFlashcard(Flashcard flashcard, FlashcardSet newSet, String newQuestion, String newAnswer, String newHint) {
        if (newSet != null && !flashcard.getSetUUID().equals(newSet.getUUID())) {
            moveFlashcardToNewSet(flashcard, newSet, newQuestion, newAnswer, newHint);
        } else {
            flashcardManager.updateFlashcardDetails(flashcard, newQuestion, newAnswer, newHint);
        }
    }

    public void moveFlashcardToNewSet(Flashcard flashcard, FlashcardSet newSet, String newQuestion, String newAnswer, String newHint) {
        if (flashcardSetManager.getFlashcardSet(newSet.getUUID()) != null) {
            Flashcard newFlashcard = new Flashcard(newSet.getUUID(), newQuestion, newAnswer, newHint);

            flashcardManager.insertFlashcard(newFlashcard); // Insert the new flashcard into the database for the new set
            flashcardSetManager.addFlashcardToFlashcardSet(newSet.getUUID(), newFlashcard);
            flashcardManager.markFlashcardAsDeleted(flashcard.getUUID()); // Soft delete the flashcard
        }
    }
}
