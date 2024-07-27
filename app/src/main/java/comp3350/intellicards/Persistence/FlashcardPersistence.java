package comp3350.intellicards.Persistence;

import java.util.List;

import comp3350.intellicards.Objects.Flashcard;

public interface FlashcardPersistence {
    Flashcard getFlashcard(String uuid);

    List<Flashcard> getFlashcardsByKey(String key);

    void insertFlashcard(Flashcard currentFlashcard);

    void updateFlashcard(Flashcard currentFlashcard);

    void markFlashcardAsDeleted(String uuid);

    void restoreFlashcard(String uuid);

    void markAttempted(String uuid);

    void markAttemptedAndCorrect(String uuid);
}