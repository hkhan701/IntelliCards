package comp3350.intellicards.Persistence.stubs;

import comp3350.intellicards.Objects.Flashcard;
import comp3350.intellicards.Persistence.FlashcardPersistence;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class FlashcardPersistenceStub implements FlashcardPersistence {

    private Map<String, Flashcard> flashcards;

    public FlashcardPersistenceStub() {
        flashcards = new LinkedHashMap<>();



//        Flashcard flashcard1 = new Flashcard("The capital of France is Paris.", "What is the capital of France?", "Eiffel Tower");
//        Flashcard flashcard2 = new Flashcard("The tallest mountain in the world is Mount Everest.", "What is the tallest mountain in the world?", "Asia");
//        Flashcard flashcard3 = new Flashcard("The currency of Japan is Yen.", "What is the currency of Japan?", "");
//        Flashcard flashcard4 = new Flashcard("The Great Wall of China is in China.", "Where is the Great Wall of China located?", "");
//        Flashcard flashcard5 = new Flashcard("The Pacific Ocean is the largest ocean on Earth.", "What is the largest ocean on Earth?", "");
//        Flashcard flashcard6 = new Flashcard("The first President of the United States was George Washington.", "Who was the first President of the United States?", "");
//        Flashcard flashcard7 = new Flashcard("The formula for water is H2O.", "What is the chemical formula for water?", "");
//        Flashcard flashcard8 = new Flashcard("The speed of light is approximately 299,792,458 meters per second.", "What is the speed of light?", "");
//        Flashcard flashcard9 = new Flashcard("The human body has 206 bones.", "How many bones does the human body have?", "");
//
//        // Add flashcards to persistence
//        flashcardPersistence.insertFlashcard(flashcard1);
//        flashcardPersistence.insertFlashcard(flashcard2);
//        flashcardPersistence.insertFlashcard(flashcard3);
//        flashcardPersistence.insertFlashcard(flashcard4);
//        flashcardPersistence.insertFlashcard(flashcard5);
//        flashcardPersistence.insertFlashcard(flashcard6);
//        flashcardPersistence.insertFlashcard(flashcard7);
//        flashcardPersistence.insertFlashcard(flashcard8);
//        flashcardPersistence.insertFlashcard(flashcard9);
    }

    // Can be used to get all active flashcards for searching capability
    @Override
    public List<Flashcard> getAllActiveFlashcards() {
        List<Flashcard> activeFlashcards = new ArrayList<>();
        for (Flashcard flashcard : flashcards.values()) {
            if (!flashcard.isDeleted()) {
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