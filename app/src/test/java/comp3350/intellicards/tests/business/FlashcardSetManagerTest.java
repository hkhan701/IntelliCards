package comp3350.intellicards.tests.business;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Test;
import org.junit.Before;

import comp3350.intellicards.Business.FlashcardSetManager;
import comp3350.intellicards.Objects.Flashcard;
import comp3350.intellicards.Objects.FlashcardSet;
import comp3350.intellicards.Persistence.FlashcardSetPersistence;

public class FlashcardSetManagerTest {

    private FlashcardSetPersistence flashcardSetData;
    private FlashcardSet flashcardSet;
    private Flashcard flashcard;
    private FlashcardSetManager flashcardSetManager;

    @Before
    public void setUp() {
        flashcardSet = mock(FlashcardSet.class);
        flashcard = mock(Flashcard.class);
        flashcardSetData = mock(FlashcardSetPersistence.class);
        flashcardSetManager = new FlashcardSetManager(flashcardSetData);
    }

    /*
     * Test getFlashcardSet()
     */
    @Test
    public void testGetFlashcardSet() {
        when(flashcardSetData.getFlashcardSet("uuid")).thenReturn(flashcardSet);

        assertEquals("FlashcardSetManager can retrieve managed flashcardSet given its UUID",
                flashcardSet, flashcardSetManager.getFlashcardSet("uuid"));
    }

    @Test
    public void testGetFlashcardSetNotManaged() {
        when(flashcardSetData.getFlashcardSet(any())).thenReturn(null);

        assertNull("FlashcardSetManager cannot retrieve a non managed set",
                flashcardSetManager.getFlashcardSet("TestUUID"));
    }

    @Test
    public void testGetFlashcardSetNull() {
        assertNull("FlashcardSetManager cannot retrieve a set if given a null",
                flashcardSetManager.getFlashcardSet(null));
    }

    /*
     * Test getActiveFlashcardSet()
     */
    @Test
    public void testGetActiveFlashcardSet() {
        FlashcardSet activeSet = mock(FlashcardSet.class);

        when(activeSet.getIndex(0)).thenReturn(flashcard);
        when(flashcardSet.getActiveFlashcards()).thenReturn(activeSet);
        when(flashcardSetData.getFlashcardSet("active")).thenReturn(flashcardSet);

        FlashcardSet flashcardSetRetrieved = flashcardSetManager.getActiveFlashcardSet("active");

        assertEquals("FlashcardSetManager can retrieve active cards from managed set",
                flashcard, flashcardSetRetrieved.getIndex(0));
    }

    @Test
    public void testGetActiveFlashcardSetNotManaged() {
        assertNull("FlashcardSetManager cannot retrieve active cards from non-managed set",
                flashcardSetManager.getActiveFlashcardSet("TestSetNotExist"));
    }

    /*
     * Test insertFlashcard()
     */
    @Test
    public void testInsertFlashcard() {
        flashcardSetManager.insertFlashcardSet(flashcardSet);

        // FlashcardSetManager can call insertFlashcardSet() for the persistence from its own method of the same name
        verify(flashcardSetData).insertFlashcardSet(flashcardSet);
    }


    /*
     * Test getAllDeletedFlashcards()
     */
    @Test
    public void testGetAllDeletedFlashcards() {
        FlashcardSet deletedSet = mock(FlashcardSet.class);
        List<FlashcardSet> allFlashcards = mock(ArrayList.class);
        Iterator<FlashcardSet> flashcardSetIterator = mock(Iterator.class);

        when(flashcardSetData.getAllFlashcardSets()).thenReturn(allFlashcards);
        when(flashcardSetData.getFlashcardSet("TestSet")).thenReturn(flashcardSet);

        when(allFlashcards.iterator()).thenReturn(flashcardSetIterator);
        when(flashcardSetIterator.hasNext()).thenReturn(true, false);
        when(flashcardSetIterator.next()).thenReturn(flashcardSet);

        when(flashcardSet.getUsername()).thenReturn("TestUser");
        when(flashcardSet.getUUID()).thenReturn("TestSet");
        when(flashcardSet.getDeletedFlashcards()).thenReturn(deletedSet);
        when(deletedSet.size()).thenReturn(1);
        when(deletedSet.getIndex(anyInt())).thenReturn(flashcard);

        List<Flashcard> retrievedSet = flashcardSetManager.getAllDeletedFlashcards("TestUser");

        assertEquals("FlashcardSetManager can retrieve all deleted cards for single user",
                flashcard, retrievedSet.get(0));
    }

    @Test
    public void testGetAllDeletedFlashcardsNoDeleted() {
        FlashcardSet deletedSet = mock(FlashcardSet.class);
        List<FlashcardSet> allFlashcards = mock(ArrayList.class);
        Iterator<FlashcardSet> flashcardSetIterator = mock(Iterator.class);

        when(flashcardSetData.getAllFlashcardSets()).thenReturn(allFlashcards);
        when(flashcardSetData.getFlashcardSet("TestSet")).thenReturn(flashcardSet);

        when(allFlashcards.iterator()).thenReturn(flashcardSetIterator);
        when(flashcardSetIterator.hasNext()).thenReturn(true, false);
        when(flashcardSetIterator.next()).thenReturn(flashcardSet);

        when(flashcardSet.getUsername()).thenReturn("TestUser");
        when(flashcardSet.getUUID()).thenReturn("TestSet");
        when(flashcardSet.getDeletedFlashcards()).thenReturn(deletedSet);
        when(deletedSet.size()).thenReturn(0);
        when(deletedSet.getIndex(anyInt())).thenReturn(flashcard);

        List<Flashcard> retrievedSet = flashcardSetManager.getAllDeletedFlashcards("TestUser");

        assertEquals("FlashcardSetManager cannot retrieve all deleted cards for a single user because there are none",
                0, retrievedSet.size());
    }

    @Test
    public void testAllDeletedFlashcardsOtherUser() {
        FlashcardSet userSet = mock(FlashcardSet.class);
        Flashcard userFlashcard = mock(Flashcard.class);
        FlashcardSet userSetDeleted = mock(FlashcardSet.class);
        FlashcardSet flashcardSetDeleted = mock(FlashcardSet.class);
        List<FlashcardSet> allFlashcards = mock(ArrayList.class);
        Iterator<FlashcardSet> flashcardSetIterator = mock(Iterator.class);

        when(flashcardSetData.getAllFlashcardSets()).thenReturn(allFlashcards);
        when(flashcardSetData.getFlashcardSet("TestSet")).thenReturn(userSet);
        when(flashcardSetData.getFlashcardSet("NotTestSet")).thenReturn(flashcardSet);

        when(allFlashcards.iterator()).thenReturn(flashcardSetIterator);
        when(flashcardSetIterator.hasNext()).thenReturn(true, true, false);
        when(flashcardSetIterator.next()).thenReturn(flashcardSet, userSet);

        when(userSet.getUsername()).thenReturn("TestUser");
        when(userSet.getUUID()).thenReturn("TestSet");
        when(userSet.getDeletedFlashcards()).thenReturn(userSetDeleted);
        when(userSetDeleted.size()).thenReturn(1);
        when(userSetDeleted.getIndex(anyInt())).thenReturn(userFlashcard);

        when(flashcardSet.getUsername()).thenReturn("NotTestUser");
        when(flashcardSet.getUUID()).thenReturn("NotTestSet");
        when(flashcardSet.getDeletedFlashcards()).thenReturn(flashcardSetDeleted);
        when(flashcardSetDeleted.size()).thenReturn(1);
        when(flashcardSetDeleted.getIndex(anyInt())).thenReturn(flashcard);

        List<Flashcard> retrievedFlashcards = flashcardSetManager.getAllDeletedFlashcards("TestUser");

        assertEquals("FlashcardSetManager cannot retrieve the deleted cards of a different user",
                1, retrievedFlashcards.size());

        assertEquals("FlashcardSetManager cannot retrieve the deleted cards of a different user",
                userFlashcard, retrievedFlashcards.get(0));
    }

    @Test
    public void testGetDeletedFlashcardSetNotManaged() {
        assertEquals("If the flashcardSetManager receives a user that is not managed, there should be nothing returned",
                0, flashcardSetManager.getAllDeletedFlashcards("TestUser").size());
    }

    /*
     * Test addFlashcardToFlashcardSet()
     */
    @Test
    public void testAddFlashcardToFlashcardSet() {
        when(flashcardSetData.getFlashcardSet("flashcard")).thenReturn(flashcardSet);

        assertTrue("FlashcardSetManager verifies that the flashcard was added to the set with a return value",
                flashcardSetManager.addFlashcardToFlashcardSet("TestSet", flashcard));
    }

    @Test
    public void testAddFlashcardToNonManagedFlashcardSet() {
        assertFalse("FlashcardSetManager cannot add a flashcard to a set if the set is not managed",
                flashcardSetManager.addFlashcardToFlashcardSet(flashcardSet.getUUID(), flashcard));
    }

    /*
     * Test getAllFlashcardSets()
     */
    @Test
    public void testGetAllFlashcardSets() {
        List<FlashcardSet> allFlashcards = mock(ArrayList.class);

        when(flashcardSetData.getAllFlashcardSets()).thenReturn(allFlashcards);

        assertEquals("FlashcardSetManager can retrieve all sets managed",
                allFlashcards, flashcardSetManager.getAllFlashcardSets());
    }

    /*
     * Test shuffleFlashcardSet()
     */
    @Test
    public void testShuffleFlashcards() {
        when(flashcardSetData.getFlashcardSet("TestSet")).thenReturn(flashcardSet);

        when(flashcardSet.getActiveFlashcards()).thenReturn(flashcardSet);
        when(flashcardSet.getUUID()).thenReturn("TestSet");

        flashcardSetManager.shuffleFlashcardSet(flashcardSet);

        // FlashcardSetManager can call the method to randomize a set
        verify(flashcardSet).randomizeSet();
    }

    @Test
    public void testShuffleNonManagedFlashcardSet() {
        when(flashcardSetData.getFlashcardSet("TestSet")).thenReturn(null);

        when(flashcardSet.getActiveFlashcards()).thenReturn(flashcardSet);
        when(flashcardSet.getUUID()).thenReturn("TestSet");

        flashcardSetManager.shuffleFlashcardSet(flashcardSet);

        // FlashcardSetManager cannot call the method to randomize a set if it is not managed
        verify(flashcardSet, times(0)).randomizeSet();
    }

    /*
     * Test getFlashcardSetsByUsername()
     */
    @Test
    public void testGetFlashcardSetsByUsername() {
        List<FlashcardSet> allFlashcards = mock(ArrayList.class);
        Iterator<FlashcardSet> flashcardSetIterator = mock(Iterator.class);

        when(allFlashcards.iterator()).thenReturn(flashcardSetIterator);
        when(flashcardSetIterator.hasNext()).thenReturn(true, false);
        when(flashcardSetIterator.next()).thenReturn(flashcardSet);

        when(flashcardSet.getUsername()).thenReturn("TestUser");
        when(flashcardSetData.getAllFlashcardSets()).thenReturn(allFlashcards);

        List<FlashcardSet> retrievedFlashcardSets = flashcardSetManager.getFlashcardSetsByUsername("TestUser");

        assertNotEquals("FlashcardSetManager can retrieve sets based on username",
                -1, retrievedFlashcardSets.indexOf(flashcardSet));
    }

    @Test
    public void testGetFlashcardSetByUserMixed() {
        FlashcardSet userSet = mock(FlashcardSet.class);
        List<FlashcardSet> allFlashcards = mock(ArrayList.class);
        Iterator<FlashcardSet> flashcardSetIterator = mock(Iterator.class);

        when(allFlashcards.iterator()).thenReturn(flashcardSetIterator);
        when(flashcardSetIterator.hasNext()).thenReturn(true, true, true, false);
        when(flashcardSetIterator.next()).thenReturn(flashcardSet, userSet, flashcardSet);

        when(flashcardSetData.getAllFlashcardSets()).thenReturn(allFlashcards);
        when(flashcardSet.getUsername()).thenReturn("NotTestUser");
        when(userSet.getUsername()).thenReturn("TestUser");

        List<FlashcardSet> retrievedFlashcardSets = flashcardSetManager.getFlashcardSetsByUsername("TestUser");

        assertEquals("FlashcardSetManager will not retrieve other people's sets when calling getFlashcardSetsByUsername()",
                -1, retrievedFlashcardSets.indexOf(flashcardSet));
    }

    @Test
    public void testGetFlashcardSetByUserNonManaged() {
        assertEquals("FlashcardSetManager will not retrieve non-managed sets from a user",
                new ArrayList<>(), flashcardSetManager.getFlashcardSetsByUsername("TestUser"));
    }

}
