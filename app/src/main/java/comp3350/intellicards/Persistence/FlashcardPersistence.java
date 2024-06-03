package comp3350.intellicards.Persistence;

import java.util.List;

import comp3350.intellicards.Objects.Flashcard;

public interface FlashcardPersistence {

    List<Flashcard> getAllActiveFlashcards();

    List<Flashcard> getAllDeletedFlashcards();

    Flashcard getFlashcard(Flashcard currentFlashcard);

    Flashcard insertFlashcard(Flashcard currentFlashcard);

    Flashcard updateFlashcard(Flashcard currentFlashcard);

    boolean markFlashcardAsDeleted(Flashcard currentFlashcard);

    boolean restoreFlashcard(Flashcard currentFlashcard);
}