package comp3350.intellicards.Persistence;

import java.util.List;

import comp3350.intellicards.Objects.Flashcard;
import comp3350.intellicards.Objects.FlashcardSet;

public interface FlashcardSetPersistence {
    FlashcardSet getFlashcardSet(FlashcardSet currentFlashcardSet);

    FlashcardSet getFlashcardSet(String flashcardSetId);

    List<FlashcardSet> getAllFlashcardSets();

    FlashcardSet insertFlashcardSet(FlashcardSet newFlashcardSet);

    boolean addFlashCardToFlashcardSet(FlashcardSet flashcardSet, Flashcard flashcard);

    FlashcardSet getActiveFlashcardSet(FlashcardSet currentFlashcardSet);

    FlashcardSet getActiveFlashcardSet(String flashcardSetId);
}
