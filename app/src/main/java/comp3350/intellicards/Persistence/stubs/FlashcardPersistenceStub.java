package comp3350.intellicards.Persistence.stubs;

import comp3350.intellicards.Objects.Flashcard;
import comp3350.intellicards.Persistence.FlashcardPersistence;
import comp3350.intellicards.Business.FlashcardSetManager;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class FlashcardPersistenceStub implements FlashcardPersistence {

    private Map<String, Flashcard> flashcards;

    public FlashcardPersistenceStub() {
        flashcards = new LinkedHashMap<>();
        FlashcardSetManager flashcardSetManager = new FlashcardSetManager();

        Flashcard flashcard1 = new Flashcard("0","The capital of France is Paris.", "What is the capital of France?", "Eiffel Tower");
        Flashcard flashcard2 = new Flashcard("0", "The tallest mountain in the world is Mount Everest.", "What is the tallest mountain in the world?", "Asia");
        Flashcard flashcard3 = new Flashcard("0", "The currency of Japan is Yen.", "What is the currency of Japan?", "");
        Flashcard flashcard4 = new Flashcard("0", "The Great Wall of China is in China.", "Where is the Great Wall of China located?", "");
        Flashcard flashcard5 = new Flashcard("1", "The Pacific Ocean is the largest ocean on Earth.", "What is the largest ocean on Earth?", "");
        Flashcard flashcard6 = new Flashcard("1", "The first President of the United States was George Washington.", "Who was the first President of the United States?", "");
        Flashcard flashcard7 = new Flashcard("2", "The formula for water is H2O.", "What is the chemical formula for water?", "");
        Flashcard flashcard8 = new Flashcard("2", "The speed of light is approximately 299,792,458 meters per second.", "What is the speed of light?", "");
        Flashcard flashcard9 = new Flashcard("2", "The human body has 206 bones.", "How many bones does the human body have?", "");

        // Add flashcards to persistence
//        flashcards.put(flashcard1.getSetID(), flashcard1);
//        flashcards.put(flashcard2.getSetID(), flashcard2);
//        flashcards.put(flashcard3.getSetID(), flashcard3);
//        flashcards.put(flashcard4.getSetID(), flashcard4);
//        flashcards.put(flashcard5.getSetID(), flashcard5);
//        flashcards.put(flashcard6.getSetID(), flashcard6);
//        flashcards.put(flashcard7.getSetID(), flashcard7);
//        flashcards.put(flashcard8.getSetID(), flashcard8);
//        flashcards.put(flashcard9.getSetID(), flashcard9);

        flashcardSetManager.addFlashcardToFlashcardSet("0", flashcard1);
        flashcardSetManager.addFlashcardToFlashcardSet("0", flashcard2);
        flashcardSetManager.addFlashcardToFlashcardSet("0", flashcard3);

        flashcardSetManager.addFlashcardToFlashcardSet("1", flashcard4);
        flashcardSetManager.addFlashcardToFlashcardSet("1", flashcard5);
        flashcardSetManager.addFlashcardToFlashcardSet("1", flashcard6);
        flashcardSetManager.addFlashcardToFlashcardSet("1", flashcard7);
        flashcardSetManager.addFlashcardToFlashcardSet("1", flashcard8);
        flashcardSetManager.addFlashcardToFlashcardSet("1", flashcard9);

    }

    // Can be used to get all active flashcards for searching capability
    @Override
    public List<Flashcard> getAllActiveFlashcards(String setUUID) {
        List<Flashcard> activeFlashcards = new ArrayList<>();
        for (Flashcard flashcard : flashcards.values()) {
            if (!flashcard.isDeleted() && flashcard.getSetID().equals(setUUID)) {
                activeFlashcards.add(flashcard);
            }
        }

        return Collections.unmodifiableList(activeFlashcards);
    }

    // Can be used to get all deleted flashcards for the recover view
    @Override
    public List<Flashcard> getAllDeletedFlashcards() {
        List<Flashcard> deletedFlashcards = new ArrayList<>();
        for (Flashcard flashcard : flashcards.values()) {
            if (flashcard.isDeleted()) {
                deletedFlashcards.add(flashcard);
            }
        }
        return Collections.unmodifiableList(deletedFlashcards);
    }

    @Override
    public Flashcard getFlashcard(String uuid) {
        return flashcards.get(uuid);
    }


    @Override
    public Flashcard insertFlashcard(Flashcard currentFlashcard) {
        flashcards.put(currentFlashcard.getUUID(), currentFlashcard);
        return currentFlashcard;
    }

    @Override
    public Flashcard updateFlashcard(Flashcard newFlashcard) {
        flashcards.put(newFlashcard.getUUID(), newFlashcard);
        return newFlashcard;
    }

    @Override
    public boolean markFlashcardAsDeleted(String uuid) {
        Flashcard flashcard = flashcards.get(uuid);
        if (flashcard != null) {
            flashcard.markDeleted();
            return true;
        }
        return false;
    }

    @Override
    public boolean restoreFlashcard(String uuid) {
        Flashcard flashcard = flashcards.get(uuid);
        if (flashcard != null) {
            flashcard.markRecovered();
            return true;
        }
        return false;
    }

    @Override
    public void markAttempted(Flashcard flashcard) {
        flashcard.markAttempted();
    }

    @Override
    public void markAttemptedAndCorrect(Flashcard flashcard) {
        flashcard.markAttemptedAndCorrect();
    }


}