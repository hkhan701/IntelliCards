package comp3350.intellicards.Application;

import comp3350.intellicards.Persistence.FlashcardPersistence;
import comp3350.intellicards.Persistence.FlashcardSetPersistence;
import comp3350.intellicards.Persistence.UserPersistence;
import comp3350.intellicards.Persistence.stubs.FlashcardPersistenceStub;
import comp3350.intellicards.Persistence.stubs.FlashcardSetPersistenceStub;
import comp3350.intellicards.Persistence.stubs.UserPersistenceStub;

public class Services {

    private static FlashcardPersistence flashcardPersistence;
    private static FlashcardSetPersistence flashcardSetPersistence;
    private static UserPersistence userPersistence;

    public static synchronized FlashcardPersistence getFlashcardPersistence() {
        if (flashcardPersistence == null) {
            flashcardPersistence = new FlashcardPersistenceStub();
        }
        return flashcardPersistence;
    }

    public static synchronized FlashcardSetPersistence getFlashcardSetPersistence() {
        if (flashcardSetPersistence == null) {
            flashcardSetPersistence = new FlashcardSetPersistenceStub();
        }
        return flashcardSetPersistence;
    }

    public static synchronized UserPersistence getUserPersistence() {
        if (userPersistence == null) {
            userPersistence = new UserPersistenceStub();
        }
        return userPersistence;
    }

}
