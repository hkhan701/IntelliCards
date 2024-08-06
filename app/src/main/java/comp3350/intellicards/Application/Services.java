package comp3350.intellicards.Application;

import comp3350.intellicards.Persistence.FlashcardPersistence;
import comp3350.intellicards.Persistence.FlashcardSetPersistence;
import comp3350.intellicards.Persistence.UserPersistence;
import comp3350.intellicards.Persistence.hsqldb.UserPersistenceHSQLDB;
import comp3350.intellicards.Persistence.hsqldb.FlashcardPersistenceHSQLDB;
import comp3350.intellicards.Persistence.hsqldb.FlashcardSetPersistenceHSQLDB;

public class Services {

    private static FlashcardPersistence flashcardPersistence = null;
    private static FlashcardSetPersistence flashcardSetPersistence = null;
    private static UserPersistence userPersistence = null;

    public static synchronized FlashcardPersistence getFlashcardPersistence() {
        if (flashcardPersistence == null) {
            flashcardPersistence = new FlashcardPersistenceHSQLDB(Configuration.getDBPathName());
        }
        return flashcardPersistence;
    }

    public static synchronized FlashcardSetPersistence getFlashcardSetPersistence() {
        if (flashcardSetPersistence == null) {
            flashcardSetPersistence = new FlashcardSetPersistenceHSQLDB(Configuration.getDBPathName());
        }
        return flashcardSetPersistence;
    }

    public static synchronized UserPersistence getUserPersistence() {
        if (userPersistence == null) {
            userPersistence = new UserPersistenceHSQLDB(Configuration.getDBPathName());
        }
        return userPersistence;
    }

}
