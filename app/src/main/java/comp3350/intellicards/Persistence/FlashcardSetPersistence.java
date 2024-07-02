package comp3350.intellicards.Persistence;

import java.util.ArrayList;
import java.util.List;

import comp3350.intellicards.Objects.Flashcard;
import comp3350.intellicards.Objects.FlashcardSet;

public interface FlashcardSetPersistence {
    FlashcardSet getFlashcardSet(String flashcardSetUUID);

    FlashcardSet getActiveFlashcardSet(String uuid);

    FlashcardSet getDeletedFlashcardSet(String uuid);

    List<FlashcardSet> getAllFlashcardSets();

    void insertFlashcardSet(FlashcardSet newFlashcardSet);

    boolean addFlashcardToFlashcardSet(String setUUID, Flashcard flashcard);

    void randomizeFlashcardSet(FlashcardSet flashcardSet);

    List<FlashcardSet> getFlashcardSetsByUsername(String username);

}
