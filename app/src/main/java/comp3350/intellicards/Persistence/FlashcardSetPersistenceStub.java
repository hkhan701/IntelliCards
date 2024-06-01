package comp3350.intellicards.Persistence;

import comp3350.intellicards.Objects.Flashcard;
import comp3350.intellicards.Objects.FlashcardSet;

public class FlashcardSetPersistenceStub {
    private static FlashcardSet flashcardSet;

    public FlashcardSetPersistenceStub() {

    }

    public static void initializeStubData()
    {
        flashcardSet = new FlashcardSet("stub");

        flashcardSet.addFlashCard(new Flashcard("whats 1+1", "its 2"));
        flashcardSet.addFlashCard(new Flashcard("whats 2+2", "its 4"));
        flashcardSet.addFlashCard(new Flashcard("what can a bit be represented by", "0 and 1"));
        flashcardSet.addFlashCard(new Flashcard("whens obamas birthday", "aug 4"));
        Flashcard stub5 = new Flashcard("dogs or cats?", "dogs");
        stub5.markDeleted();
        flashcardSet.addFlashCard(stub5);
        Flashcard stub6 =new Flashcard("finish it, Legend-", "-Dairy");
        stub6.markDeleted();
        flashcardSet.addFlashCard(stub6);
    }

    public static FlashcardSet getFlashcardSet()
    {
        return flashcardSet;
    }

    public static boolean isInstantiated()
    {
        return flashcardSet != null;
    }

}
