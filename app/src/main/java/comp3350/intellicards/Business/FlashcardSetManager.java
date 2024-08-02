package comp3350.intellicards.Business;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

import comp3350.intellicards.Application.Services;
import comp3350.intellicards.Objects.Flashcard;
import comp3350.intellicards.Objects.FlashcardSet;
import comp3350.intellicards.Persistence.FlashcardSetPersistence;

public class FlashcardSetManager {
    private FlashcardSetPersistence flashcardSetPersistence;

    public FlashcardSetManager() {
        flashcardSetPersistence = Services.getFlashcardSetPersistence();
    }

    public FlashcardSetManager(FlashcardSetPersistence persistence) {
        flashcardSetPersistence = persistence;
    }

    public FlashcardSet getFlashcardSet(String uuid) {
        return this.flashcardSetPersistence.getFlashcardSet(uuid);
    }

    public FlashcardSet getActiveFlashcardSet(String uuid) {
        FlashcardSet set = getFlashcardSet(uuid);
        if (set != null) {
            return set.getActiveFlashcards();
        }

        return null;
    }

    private FlashcardSet getDeletedFlashcardSet(String uuid) {
        FlashcardSet set = getFlashcardSet(uuid);
        if (set != null) {
            return set.getDeletedFlashcards();
        }

        return null;
    }

    public List<Flashcard> getAllDeletedFlashcards(String username) {
        List<Flashcard> deletedFlashcards = new ArrayList<>();

        // Retrieve deleted flashcards for the user
        List<FlashcardSet> userFlashcardSets = getFlashcardSetsByUsername(username);

        for (FlashcardSet flashcardSet : userFlashcardSets) {
            FlashcardSet deletedSet = getDeletedFlashcardSet(flashcardSet.getUUID());
            if (deletedSet != null)
            {
                for(int i = 0; i < deletedSet.size(); i++)
                {
                    Flashcard flashcard = deletedSet.getIndex(i);
                    deletedFlashcards.add(flashcard);
                }
            }
        }
        return deletedFlashcards;
    }

    public List<FlashcardSet> getAllFlashcardSets() {
        return this.flashcardSetPersistence.getAllFlashcardSets();
    }

    public void insertFlashcardSet(FlashcardSet newFlashcardSet) {
        this.flashcardSetPersistence.insertFlashcardSet(newFlashcardSet);
    }

    public List<FlashcardSet> getFlashcardSetsByKey(String key) {
        return this.flashcardSetPersistence.getFlashcardSetsByKey(key);
    }

    public FlashcardSet addFlashcardToFlashcardSet(@NonNull String setUUID, @NonNull Flashcard flashcard) {
        FlashcardSet flashcardSet = getFlashcardSet(setUUID);

        if (flashcardSet != null) {
            flashcardSet.addFlashcard(flashcard);
            return flashcardSet;
        }

        return null;
    }

    public void shuffleFlashcardSet(FlashcardSet flashcardSet) {
        FlashcardSet set = getActiveFlashcardSet(flashcardSet.getUUID());
        if (set != null)
        {
            flashcardSet.randomizeSet();
        }
    }

    public List<FlashcardSet> getFlashcardSetsByUsername(String username) {
        List<FlashcardSet> flashcardSets = getAllFlashcardSets();
        List<FlashcardSet> userSets = new ArrayList<>();

        for (FlashcardSet flashcardSet : flashcardSets) {
            if (flashcardSet.getUsername().equals(username)) {
                userSets.add(flashcardSet);
            }
        }
        return userSets;
    }

    public List<FlashcardSet> getFlashcardSetsByKey(String username, String key) {
        List<FlashcardSet> allFlashcardSets = getFlashcardSetsByKey(key);
        List<FlashcardSet> searchedFlashcardSets = new ArrayList<>();

        for(FlashcardSet flashcardSet : allFlashcardSets) {
            if(username.equals(flashcardSet.getUsername())) {
                searchedFlashcardSets.add(flashcardSet);
            }
        }

        return searchedFlashcardSets;
    }

    public FlashcardSet getSearchedFlashcards(String setUUID, List<Flashcard> searchedFlashcards) {
        FlashcardSet originalFlashcardSet = getFlashcardSet(setUUID);
        FlashcardSet searchedFlashcardSet = null;

        if (originalFlashcardSet != null) {
            searchedFlashcardSet = new FlashcardSet(setUUID, originalFlashcardSet.getUsername(), originalFlashcardSet.getFlashcardSetName());

            for (Flashcard flashcard : searchedFlashcards) {
                if (flashcard.getSetUUID().equals(setUUID)) {
                    searchedFlashcardSet.addFlashcard(flashcard);
                }
            }
        }
        return searchedFlashcardSet;
    }
}

