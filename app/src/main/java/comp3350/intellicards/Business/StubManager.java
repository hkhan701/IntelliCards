package comp3350.intellicards.Business;

import comp3350.intellicards.Objects.Flashcard;
import comp3350.intellicards.Objects.FlashcardSet;
import comp3350.intellicards.Persistence.FlashcardPersistence;
import comp3350.intellicards.Persistence.FlashcardSetPersistence;
import comp3350.intellicards.Persistence.stubs.FlashcardPersistenceStub;
import comp3350.intellicards.Persistence.stubs.FlashcardSetPersistenceStub;

public class StubManager {
    private static boolean initialized = false;
    private static FlashcardPersistence flashcardPersistence;
    private static FlashcardSetPersistence flashcardSetPersistence;

    public static void initializeStubData() {
        flashcardSetPersistence = new FlashcardSetPersistenceStub();
        flashcardPersistence = new FlashcardPersistenceStub();

        // Create flashcards
        Flashcard flashcard1 = new Flashcard("The capital of France is Paris.", "What is the capital of France?", "Eiffel Tower");
        Flashcard flashcard2 = new Flashcard("The tallest mountain in the world is Mount Everest.", "What is the tallest mountain in the world?", "Asia");
        Flashcard flashcard3 = new Flashcard("The currency of Japan is Yen.", "What is the currency of Japan?", null);
        Flashcard flashcard4 = new Flashcard("The Great Wall of China is in China.", "Where is the Great Wall of China located?", null);
        Flashcard flashcard5 = new Flashcard("The Pacific Ocean is the largest ocean on Earth.", "What is the largest ocean on Earth?", null);
        Flashcard flashcard6 = new Flashcard("The first President of the United States was George Washington.", "Who was the first President of the United States?", null);
        Flashcard flashcard7 = new Flashcard("The formula for water is H2O.", "What is the chemical formula for water?", null);
        Flashcard flashcard8 = new Flashcard("The speed of light is approximately 299,792,458 meters per second.", "What is the speed of light?", null);
        Flashcard flashcard9 = new Flashcard("The human body has 206 bones.", "How many bones does the human body have?", null);

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

        // Create flashcard sets
        FlashcardSet set1 = new FlashcardSet("Geography");
        FlashcardSet set2 = new FlashcardSet("History");
        FlashcardSet set3 = new FlashcardSet("Science");
        FlashcardSet set4 = new FlashcardSet("Math");
        FlashcardSet set5 = new FlashcardSet("English");

        // Add flashcards to flashcard sets
        set1.addFlashcard(flashcard1);
        set1.addFlashcard(flashcard2);
        set1.addFlashcard(flashcard3);
        set2.addFlashcard(flashcard4);
        set2.addFlashcard(flashcard5);
        set2.addFlashcard(flashcard6);
        set3.addFlashcard(flashcard7);
        set3.addFlashcard(flashcard8);
        set3.addFlashcard(flashcard9);

        // Mark flashcards as deleted
        flashcardPersistence.markFlashcardAsDeleted(flashcard3.getUUID());
        flashcardPersistence.markFlashcardAsDeleted(flashcard5.getUUID());
        flashcardPersistence.markFlashcardAsDeleted(flashcard9.getUUID());

        // Add flashcard sets to persistence
        flashcardSetPersistence.insertFlashcardSet(set1);
        flashcardSetPersistence.insertFlashcardSet(set2);
        flashcardSetPersistence.insertFlashcardSet(set3);
        flashcardSetPersistence.insertFlashcardSet(set4);
        flashcardSetPersistence.insertFlashcardSet(set5);

        initialized = true;
    }

    public static boolean isInitialized() {
        return initialized;
    }

    public static FlashcardPersistence getFlashcardPersistence() {
        return flashcardPersistence;
    }

    public static FlashcardSetPersistence getFlashcardSetPersistence() {
        return flashcardSetPersistence;
    }

}
