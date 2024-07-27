package comp3350.intellicards.Persistence.stubs;

import comp3350.intellicards.Application.Services;
import comp3350.intellicards.Objects.Flashcard;
import comp3350.intellicards.Persistence.FlashcardPersistence;
import comp3350.intellicards.Business.FlashcardSetManager;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class FlashcardPersistenceStub implements FlashcardPersistence {

    private Map<String, Flashcard> flashcards;

    public FlashcardPersistenceStub() {
        flashcards = new LinkedHashMap<>();
        FlashcardSetManager flashcardSetManager = new FlashcardSetManager(Services.getFlashcardSetPersistence());

        Flashcard flashcard1 = new Flashcard("set1", "What is 2+2?", "4", "Basic arithmetic");
        Flashcard flashcard2 = new Flashcard("set1", "What is the square root of 16?", "4", "Basic arithmetic");
        Flashcard flashcard3 = new Flashcard("set2", "What is the chemical symbol for water?", "H2O", "Chemical formula");
        Flashcard flashcard4 = new Flashcard("set2", "What planet is known as the Red Planet?", "Mars", "Solar System");
        Flashcard flashcard5 = new Flashcard("set3", "Who was the first president of the United States?", "George Washington", "US History");
        Flashcard flashcard6 = new Flashcard("set3", "What year did the Titanic sink?", "1912", "Maritime history");
        Flashcard flashcard7 = new Flashcard("set4", "What is the capital of France?", "Paris", "European capitals");
        Flashcard flashcard8 = new Flashcard("set4", "Which continent is the Sahara Desert located on?", "Africa", "World geography");

        // Add flashcards to persistence
        flashcards.put(flashcard1.getUUID(), flashcard1);
        flashcards.put(flashcard2.getUUID(), flashcard2);
        flashcards.put(flashcard3.getUUID(), flashcard3);
        flashcards.put(flashcard4.getUUID(), flashcard4);
        flashcards.put(flashcard5.getUUID(), flashcard5);
        flashcards.put(flashcard6.getUUID(), flashcard6);
        flashcards.put(flashcard7.getUUID(), flashcard7);
        flashcards.put(flashcard8.getUUID(), flashcard8);

        // Add flashcards to their respective sets
        flashcardSetManager.addFlashcardToFlashcardSet("set1", flashcard1);
        flashcardSetManager.addFlashcardToFlashcardSet("set1", flashcard2);
        flashcardSetManager.addFlashcardToFlashcardSet("set2", flashcard3);
        flashcardSetManager.addFlashcardToFlashcardSet("set2", flashcard4);
        flashcardSetManager.addFlashcardToFlashcardSet("set3", flashcard5);
        flashcardSetManager.addFlashcardToFlashcardSet("set3", flashcard6);
        flashcardSetManager.addFlashcardToFlashcardSet("set4", flashcard7);
        flashcardSetManager.addFlashcardToFlashcardSet("set4", flashcard8);

    }

    @Override
    public Flashcard getFlashcard(String uuid) {
        return flashcards.get(uuid);
    }

    @Override
    public List<Flashcard> getFlashcardsByKey(String key) {
        List<Flashcard> flashcardsWithKey = new ArrayList<>();
        String lowerKey = key.toLowerCase();

        for (Flashcard flashcard : flashcards.values()) {
            if (!flashcard.isDeleted()
                    && (flashcard.getAnswer().toLowerCase().contains(lowerKey)
                    || flashcard.getQuestion().toLowerCase().contains(lowerKey)
                    || flashcard.getHint().toLowerCase().contains(lowerKey))) {
                flashcardsWithKey.add(flashcard);
            }
        }

        return flashcardsWithKey;
    }

    @Override
    public void insertFlashcard(Flashcard currentFlashcard) {
        flashcards.put(currentFlashcard.getUUID(), currentFlashcard);
    }

    @Override
    public void updateFlashcard(Flashcard newFlashcard) {
        flashcards.put(newFlashcard.getUUID(), newFlashcard);
    }

    @Override
    public void markFlashcardAsDeleted(String uuid) {
        Flashcard flashcard = flashcards.get(uuid);
        if (flashcard != null) {
            flashcard.markDeleted();
        }
    }

    @Override
    public void restoreFlashcard(String uuid) {
        Flashcard flashcard = flashcards.get(uuid);
        if (flashcard != null) {
            flashcard.markRecovered();
        }
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