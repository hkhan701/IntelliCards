package comp3350.intellicards.Persistence;

import comp3350.intellicards.Objects.Flashcard;
import comp3350.intellicards.Objects.FlashcardSet;
import comp3350.intellicards.Persistence.stubs.FlashcardPersistenceStub;
import comp3350.intellicards.Persistence.stubs.FlashcardSetPersistenceStub;

public class InitializePersistence {
    private static boolean initialized = false;
    private static FlashcardPersistence flashcardPersistence;
    private static FlashcardSetPersistence flashcardSetPersistence;

    public InitializePersistence() {
    }

    public static void initializeStubData() {
        flashcardSetPersistence = new FlashcardSetPersistenceStub();
        flashcardPersistence = new FlashcardPersistenceStub();

        // Create flashcards
        Flashcard flashcard1 = new Flashcard("Answer 1 (Set 1)", "Question 1", "Hint 1");
        Flashcard flashcard2 = new Flashcard("Answer 2 (Set 1)", "Question 2", "Hint 2");
        Flashcard flashcard3 = new Flashcard("Answer 3 (Set 2)", "Question 3", "Hint 3");
        Flashcard flashcard4 = new Flashcard("Answer 4 (Set 2)", "Question 4", "");
        Flashcard flashcard5 = new Flashcard("Answer 5 (Set 2)", "Question 5", null);
        Flashcard flashcard6 = new Flashcard("Answer 6 (Set 2)", "Question 6", "Hint 6");
        Flashcard flashcard7 = new Flashcard("Answer 7 (Set 1)", "Question 7", "");
        Flashcard flashcard8 = new Flashcard("Answer 8 (Set 1)", "Question 8", null);
        Flashcard flashcard9 = new Flashcard("Answer 9 (Set 2)", "Question 9", "Hint 9");


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
        FlashcardSet set1 = new FlashcardSet("Set 1");
        FlashcardSet set2 = new FlashcardSet("Set 2");
        FlashcardSet set3 = new FlashcardSet("Set 3");
        FlashcardSet set4 = new FlashcardSet("Set 4");
        FlashcardSet set5 = new FlashcardSet("Set 5");

        // Add flashcards to flashcard sets
        set1.addFlashCard(flashcard1);
        set1.addFlashCard(flashcard2);
        set1.addFlashCard(flashcard7);
        set1.addFlashCard(flashcard8);

        set2.addFlashCard(flashcard3);
        set2.addFlashCard(flashcard4);
        set2.addFlashCard(flashcard5);
        set2.addFlashCard(flashcard6);
        set2.addFlashCard(flashcard9);

        // Mark flashcards as deleted
        flashcardPersistence.markFlashcardAsDeleted(flashcard7);
        flashcardPersistence.markFlashcardAsDeleted(flashcard8);
        flashcardPersistence.markFlashcardAsDeleted(flashcard9);

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
