package comp3350.intellicards.Persistence;

import java.util.List;

import comp3350.intellicards.Objects.Flashcard;
import comp3350.intellicards.Objects.FlashcardSet;

public interface FlashcardSetPersistence {
    FlashcardSet getFlashcardSet(String flashcardSetUUID);

    List<FlashcardSet> getAllFlashcardSets();

    List<FlashcardSet> getFlashcardSetsByKey(String key);

    void insertFlashcardSet(FlashcardSet newFlashcardSet);

    boolean addFlashcardToFlashcardSet(String setUUID, Flashcard flashcard);

}
