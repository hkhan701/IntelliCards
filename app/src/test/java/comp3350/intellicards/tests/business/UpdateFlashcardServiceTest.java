package comp3350.intellicards.tests.business;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.internal.matchers.Not;

import comp3350.intellicards.Business.FlashcardManager;
import comp3350.intellicards.Business.FlashcardSetManager;
import comp3350.intellicards.Business.UpdateFlashcardService;
import comp3350.intellicards.Objects.Flashcard;
import comp3350.intellicards.Objects.FlashcardSet;

public class UpdateFlashcardServiceTest {

    private UpdateFlashcardService updateFlashcardService;
    private UpdateFlashcardService updateFlashcardServiceSpy;

    private FlashcardSetManager flashcardSetManagerMock;
    private FlashcardManager flashcardManagerMock;

    private Flashcard flashcardMock;
    private FlashcardSet flashcardSetMock;

    @Before
    public void setUp() {
        flashcardManagerMock = mock(FlashcardManager.class);
        flashcardSetManagerMock = mock(FlashcardSetManager.class);
        flashcardMock = mock(Flashcard.class);
        flashcardSetMock = mock(FlashcardSet.class);

        updateFlashcardService = new UpdateFlashcardService(flashcardManagerMock, flashcardSetManagerMock);
        updateFlashcardServiceSpy = spy(updateFlashcardService);
    }

    /*
     * Test updateFlashcard()
     */
    @Test
    public void testUpdateFlashcard() {
        when(flashcardMock.getSetUUID()).thenReturn("notSetUUID");
        when(flashcardSetMock.getUUID()).thenReturn("setUUID");

        updateFlashcardServiceSpy.updateFlashcard(flashcardMock, flashcardSetMock, "Question", "Answer", null);

        // UpdateFlashcardService will move the flashcard to a new set if the given set and the set specified in the flashcard are different
        verify(updateFlashcardServiceSpy, times(1)).moveFlashcardToNewSet(flashcardMock, flashcardSetMock, "Question", "Answer", null);
    }

    @Test
    public void testUpdateFlashcardNoSetChange() {
        when(flashcardMock.getSetUUID()).thenReturn("setUUID");
        when(flashcardSetMock.getUUID()).thenReturn("setUUID");

        updateFlashcardService.updateFlashcard(flashcardMock, flashcardSetMock, "Question", "Answer", null);

        // UpdateFlashcardService will update details if the given flashcard set and the set specified in the flashcard are the same
        verify(flashcardManagerMock, times(1)).updateFlashcardDetails(flashcardMock, "Question", "Answer", null);

        // UpdateFlashcardService will not move the flashcard to a new set if the given flashcard set and the set specified in the flashcard are the same
        verify(updateFlashcardServiceSpy, times(0)).moveFlashcardToNewSet(flashcardMock, flashcardSetMock, "Question", "Answer", null);
    }

    @Test
    public void testUpdateFlashcardSetNull() {
        when(flashcardMock.getSetUUID()).thenReturn("setUUID");
        when(flashcardSetMock.getUUID()).thenReturn("setUUID");

        updateFlashcardService.updateFlashcard(flashcardMock, null, "Question", "Answer", null);

        // UpdateFlashcardService will update details if flashcard set is null
        verify(flashcardManagerMock, times(1)).updateFlashcardDetails(flashcardMock, "Question", "Answer", null);

        // UpdateFlashcardService will not attempt to move flashcard to different set if given flashcard set is null
        verify(updateFlashcardServiceSpy, times(0)).moveFlashcardToNewSet(flashcardMock, flashcardSetMock, "Question", "Answer", null);    }

    /*
     * Test moveFlashcardToNewSet()
     */
    @Captor ArgumentCaptor<Flashcard> flashcardArgumentCaptor = ArgumentCaptor.forClass(Flashcard.class);
    @Test
    public void testMoveFlashcardToNewSet() {
        FlashcardSet flashcardSetMockMove = mock(FlashcardSet.class);

        when(flashcardSetManagerMock.getFlashcardSet(any())).thenReturn(flashcardSetMockMove);
        when(flashcardMock.getUUID()).thenReturn("flashcardUUID");
        when(flashcardSetMock.getUUID()).thenReturn("flashcardSetUUID");

        updateFlashcardService.moveFlashcardToNewSet(flashcardMock, flashcardSetMock, "Question", "Answer", null);

        // UpdateFlashcardService does not add the old flashcard again
        verify(flashcardManagerMock, times(0)).insertFlashcard(flashcardMock);

        verify(flashcardManagerMock, times(1)).insertFlashcard(flashcardArgumentCaptor.capture());
        assertEquals("UpdateFlashcardService adds new flashcard instance to FlashcardManager",
                "flashcardSetUUID", flashcardArgumentCaptor.getValue().getSetUUID());

        // UpdateFlashcardService deletes old flashcard
        verify(flashcardManagerMock).markFlashcardAsDeleted("flashcardUUID");

        // UpdateFlashcardService adds new flashcard to flashcard set
        verify(flashcardSetManagerMock).addFlashcardToFlashcardSet(eq("flashcardSetUUID"), any());
    }

    @Test
    public void testMoveFlashcardToNewSetNotManaged() {
        updateFlashcardService.moveFlashcardToNewSet(flashcardMock, flashcardSetMock, "Question", "Answer", null);

        // UpdateFlashcardService does not insert a new card when changing sets if the set is not persisted
        verify(flashcardManagerMock, times(0)).insertFlashcard(any());

        // UpdateFlashcardService does not move card to set if it is not persisted
        verify(flashcardSetManagerMock, times(0)).addFlashcardToFlashcardSet(any(), any());
    }
}
