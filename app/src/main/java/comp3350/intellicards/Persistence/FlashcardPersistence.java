package comp3350.intellicards.Persistence;

import java.util.List;

import comp3350.intellicards.Objects.Flashcard;

public interface FlashcardPersistence {
    Flashcard getFlashcard(String uuid);

    Flashcard insertFlashcard(Flashcard currentFlashcard);

    Flashcard updateFlashcard(Flashcard currentFlashcard);

    boolean markFlashcardAsDeleted(String uuid);

    boolean restoreFlashcard(String uuid);

    void markAttempted(String uuid);

    void markAttemptedAndCorrect(String uuid);
}