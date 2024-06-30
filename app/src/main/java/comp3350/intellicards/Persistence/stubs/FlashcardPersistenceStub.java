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

        Flashcard flashcard1 = new Flashcard("0", "What is the capital of France?", "The capital of France is Paris.", "Eiffel Tower");
        Flashcard flashcard2 = new Flashcard("0", "What is the tallest mountain in the world?", "The tallest mountain in the world is Mount Everest.", "Asia");
        Flashcard flashcard3 = new Flashcard("0", "What is the currency of Japan?", "The currency of Japan is Yen.", "");
        Flashcard flashcard4 = new Flashcard("0", "Where is the Great Wall of China located?", "The Great Wall of China is in China.", "");
        Flashcard flashcard5 = new Flashcard("1", "What is the largest ocean on Earth?", "The Pacific Ocean is the largest ocean on Earth.", "");
        Flashcard flashcard6 = new Flashcard("1", "Who was the first President of the United States?", "The first President of the United States was George Washington.", "");
        Flashcard flashcard7 = new Flashcard("2", "What is the chemical formula for water?", "The formula for water is H2O.", "");
        Flashcard flashcard8 = new Flashcard("2", "What is the speed of light?", "The speed of light is approximately 299,792,458 meters per second.", "");
        Flashcard flashcard9 = new Flashcard("2", "How many bones does the human body have?", "The human body has 206 bones.", "");


        //Add flashcards to persistence
        flashcards.put(flashcard1.getUUID(), flashcard1);
        flashcards.put(flashcard2.getUUID(), flashcard2);
        flashcards.put(flashcard3.getUUID(), flashcard3);
        flashcards.put(flashcard4.getUUID(), flashcard4);
        flashcards.put(flashcard5.getUUID(), flashcard5);
        flashcards.put(flashcard6.getUUID(), flashcard6);
        flashcards.put(flashcard7.getUUID(), flashcard7);
        flashcards.put(flashcard8.getUUID(), flashcard8);
        flashcards.put(flashcard9.getUUID(), flashcard9);

        flashcardSetManager.addFlashcardToFlashcardSet("0", flashcard1);
        flashcardSetManager.addFlashcardToFlashcardSet("0", flashcard2);
        flashcardSetManager.addFlashcardToFlashcardSet("0", flashcard3);
        flashcardSetManager.addFlashcardToFlashcardSet("0", flashcard4);
        flashcardSetManager.addFlashcardToFlashcardSet("1", flashcard5);
        flashcardSetManager.addFlashcardToFlashcardSet("1", flashcard6);
        flashcardSetManager.addFlashcardToFlashcardSet("2", flashcard7);
        flashcardSetManager.addFlashcardToFlashcardSet("2", flashcard8);
        flashcardSetManager.addFlashcardToFlashcardSet("2", flashcard9);

    }

    // Can be used to get all active flashcards for searching capability
    @Override
    public List<Flashcard> getAllActiveFlashcards(String setUUID) {
        List<Flashcard> activeFlashcards = new ArrayList<>();
        for (Flashcard flashcard : flashcards.values()) {
            if (!flashcard.isDeleted() && flashcard.getSetUUID().equals(setUUID)) {
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
    public void markAttempted(String uuid) {
        Flashcard flashcard = flashcards.get(uuid);
        if (flashcard != null) {
            flashcard.markAttempted();
        }
    }

    @Override
    public void markAttemptedAndCorrect(String uuid) {
        Flashcard flashcard = flashcards.get(uuid);
        if (flashcard != null) {
            flashcard.markAttemptedAndCorrect();
        }
    }


}