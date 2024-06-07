package comp3350.intellicards.Business;

import java.util.List;

import comp3350.intellicards.Objects.Flashcard;
import comp3350.intellicards.Persistence.FlashcardPersistence;
import comp3350.intellicards.Persistence.stubs.FlashcardPersistenceStub;

public class FlashcardManager {
    private FlashcardPersistence flashcardPersistence;

    public FlashcardManager() {
        flashcardPersistence = new FlashcardPersistenceStub();

        Flashcard flashcard1 = new Flashcard("The capital of France is Paris.", "What is the capital of France?", "Eiffel Tower");
        Flashcard flashcard2 = new Flashcard("The tallest mountain in the world is Mount Everest.", "What is the tallest mountain in the world?", "Asia");
        Flashcard flashcard3 = new Flashcard("The currency of Japan is Yen.", "What is the currency of Japan?", "");
        Flashcard flashcard4 = new Flashcard("The Great Wall of China is in China.", "Where is the Great Wall of China located?", "");
        Flashcard flashcard5 = new Flashcard("The Pacific Ocean is the largest ocean on Earth.", "What is the largest ocean on Earth?", "");
        Flashcard flashcard6 = new Flashcard("The first President of the United States was George Washington.", "Who was the first President of the United States?", "");
        Flashcard flashcard7 = new Flashcard("The formula for water is H2O.", "What is the chemical formula for water?", "");
        Flashcard flashcard8 = new Flashcard("The speed of light is approximately 299,792,458 meters per second.", "What is the speed of light?", "");
        Flashcard flashcard9 = new Flashcard("The human body has 206 bones.", "How many bones does the human body have?", "");

        // Add flashcards to persistence
        flashcardPersistence.insertFlashcard(flashcard1);
        flashcardPersistence.insertFlashcard(flashcard2);
        flashcardPersistence.insertFlashcard(flashcard3);
        flashcardPersistence.insertFlashcard(flashcard4);
        flashcardPersistence.insertFlashcard(flashcard5);
        flashcardPersistence.insertFlashcard(flashcard6);
        flashcardPersistence.insertFlashcard(flashcard7);
        flashcardPersistence.insertFlashcard(flashcard8);
        flashcardPersistence.insertFlashcard(flashcard9);
    }

    public FlashcardManager(final FlashcardPersistence flashcardPersistence) {
        this.flashcardPersistence = flashcardPersistence;
    }

    public List<Flashcard> getAllActiveFlashcards() {
        return this.flashcardPersistence.getAllActiveFlashcards();
    }

    public List<Flashcard> getAllDeletedFlashcards() {
        return this.flashcardPersistence.getAllDeletedFlashcards();
    }

    public Flashcard getFlashcard(String id) {
        return this.flashcardPersistence.getFlashcard(id);
    }

    public Flashcard insertFlashcard(Flashcard currFlashcard) {
        return this.flashcardPersistence.insertFlashcard(currFlashcard);
    }

    public Flashcard updateFlashcard(Flashcard currFlashcard) {
        return this.flashcardPersistence.updateFlashcard(currFlashcard);
    }

    public boolean markFlashcardAsDeleted(String id) {
        return this.flashcardPersistence.markFlashcardAsDeleted(id);
    }

    public boolean restoreFlashcard(String id) {
        return this.flashcardPersistence.restoreFlashcard(id);
    }
}