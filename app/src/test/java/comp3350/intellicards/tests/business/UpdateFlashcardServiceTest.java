package comp3350.intellicards.tests.business;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;

import org.junit.Before;
import org.junit.Test;

import comp3350.intellicards.Business.FlashcardManager;
import comp3350.intellicards.Business.FlashcardSetManager;
import comp3350.intellicards.Business.UpdateFlashcardService;

public class UpdateFlashcardServiceTest {

    private UpdateFlashcardService updateFlashcardService;

    private FlashcardSetManager flashcardSetManagerMock;
    private FlashcardManager flashcardManagerMock;

    @Before
    public void setUp() {
        flashcardManagerMock = mock(FlashcardManager.class);
        flashcardSetManagerMock = mock(FlashcardSetManager.class);
        updateFlashcardService = new UpdateFlashcardService(flashcardManagerMock, flashcardSetManagerMock);
    }

    @Test
    public void testUpdateFlashcardNewSet() {
        FlashcardSet testCardSet = new FlashcardSet("testUser", "Test Card Set");
        FlashcardSet testCardSetMove = new FlashcardSet("testUser", "Test Card Set Update");
        Flashcard flashcard = new Flashcard(testCardSet.getUUID(), "Test Question", "Test Answer", null);

        flashcardSetData.insertFlashcardSet(testCardSet);
        flashcardSetData.insertFlashcardSet(testCardSetMove);
        flashcardManager.insertFlashcard(flashcard);
        flashcardSetData.addFlashcardToFlashcardSet(testCardSet.getUUID(), flashcard);

        flashcardManager.updateFlashcard(flashcard, testCardSetMove, "Test Question", "Test Answer", null);
        testCardSetMove = flashcardSetData.getFlashcardSet(testCardSetMove.getUUID());
        flashcard = flashcardManager.getFlashcard(flashcard.getUUID());

        assertNotNull("FlashcardManager can move a flashcard between sets",
                testCardSetMove.getActiveFlashcards().getIndex(0));

        assertTrue("FlashcardManager deletes old card when it is moved to new set",
                flashcard.isDeleted());
    }

    @Test
    public void testUpdateFlashcardNewSetNotManaged() {
        FlashcardSet testCardSet = new FlashcardSet("testUser", "Test Card Set");
        FlashcardSet testCardSetMove = new FlashcardSet("testUser", "Test Card Set Update");
        Flashcard flashcard = new Flashcard(testCardSet.getUUID(), "Test Question", "Test Answer", null);

        flashcardSetData.insertFlashcardSet(testCardSet);
        flashcardManager.insertFlashcard(flashcard);
        flashcardSetData.addFlashcardToFlashcardSet(testCardSet.getUUID(), flashcard);

        flashcardManager.updateFlashcard(flashcard, testCardSetMove, "Test Question", "Test Answer", null);

        flashcard = flashcardManager.getFlashcard(flashcard.getUUID());

        assertEquals("FlashcardManager cannot move a flashcard to a new set that is unmanaged",
                0, testCardSetMove.getActiveFlashcards().size());

        assertFalse("FlashcardManager does not delete old card when it is not moved to new set",
                flashcard.isDeleted());
    }
}
