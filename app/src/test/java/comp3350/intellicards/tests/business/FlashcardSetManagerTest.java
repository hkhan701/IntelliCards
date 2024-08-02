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
        flashcardSetManager.getFlashcardSet("uuid");

        // FlashcardSetManager can call getFlashcardSet() for the persistence from its own method of the same name
        verify(flashcardSetData).getFlashcardSet("uuid");
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
     * Test getFlashcardSetsByKey(String)
     */
    @Test
    public void testGetFlashcardSetsByKeyVerify() {
        flashcardSetManager.getFlashcardSetsByKey("Key");

        // FlashcardSetManager can call getFlashcardSetsByKey() for the persistence from its own method of the same name
        verify(flashcardSetData).getFlashcardSetsByKey("Key");
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
        when(flashcardSetData.getFlashcardSet("TestSet")).thenReturn(flashcardSet);

        FlashcardSet retrievedFlashcardSet = flashcardSetManager.addFlashcardToFlashcardSet("TestSet", flashcard);

        // FlashcardSetManager adds flashcard to flashcard set instance if it is managed
        verify(flashcardSet).addFlashcard(flashcard);
        assertEquals("FlashcardSetManager returns flashcardSet with added flashcard",
                flashcardSet, retrievedFlashcardSet);
    }

    @Test
    public void testAddFlashcardToNonManagedFlashcardSet() {
        FlashcardSet retrievedFlashcardSet = flashcardSetManager.addFlashcardToFlashcardSet("TestSet", flashcard);

        // FlashcardSetManager does not add flashcard to flashcard set instance if the set is not managed
        verify(flashcardSet, times(0)).addFlashcard(flashcard);
        assertNull("FlashcardSetManager does not return a flashcard set since there was nothing modified",
                retrievedFlashcardSet);
    }

    /*
     * Test getAllFlashcardSets()
     */
    @Test
    public void testGetAllFlashcardSets() {
        flashcardSetManager.getAllFlashcardSets();

        // FlashcardSetManager can call getAllFlashcardSets() for the persistence from its own method of the same name
        verify(flashcardSetData).getAllFlashcardSets();
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

    /*
     * Test getFlashcardSetsByKey(String, String)
     */
    @Test
    public void testGetFlashcardSetsByKey() {
        List<FlashcardSet> allFlashcards = mock(ArrayList.class);
        Iterator<FlashcardSet> flashcardSetIterator = mock(Iterator.class);

        when(allFlashcards.iterator()).thenReturn(flashcardSetIterator);
        when(flashcardSetIterator.hasNext()).thenReturn(true, false);
        when(flashcardSetIterator.next()).thenReturn(flashcardSet);

        when(flashcardSet.getUsername()).thenReturn("TestUser");

        when(flashcardSetData.getFlashcardSetsByKey("TestKey")).thenReturn(allFlashcards);

        List<FlashcardSet> retrievedList = flashcardSetManager.getFlashcardSetsByKey("TestUser", "TestKey");

        assertNotEquals("FlashcardManager can retrieve FlashcardSet with matching key and user can be retrieved",
                -1, retrievedList.indexOf(flashcardSet));
    }

    @Test
    public void testGetFlashcardSetsByKeyNoMatch() {
        List<FlashcardSet> allFlashcards = mock(ArrayList.class);
        Iterator<FlashcardSet> flashcardSetIterator = mock(Iterator.class);

        when(allFlashcards.iterator()).thenReturn(flashcardSetIterator);
        when(flashcardSetIterator.hasNext()).thenReturn(false);

        when(flashcardSetData.getFlashcardSetsByKey(any())).thenReturn(allFlashcards);

        List<FlashcardSet> retrievedList = flashcardSetManager.getFlashcardSetsByKey("TestUser", "TestKey");

        assertEquals("FlashcardManager cannot retrieve FlashcardSet with non matching key",
                0, retrievedList.size());
    }

    @Test
    public void testGetFlashcardSetsByKeyMatchDifferentUser() {
        FlashcardSet userMatch = mock(FlashcardSet.class);
        List<FlashcardSet> allFlashcards = mock(ArrayList.class);
        Iterator<FlashcardSet> flashcardSetIterator = mock(Iterator.class);

        when(allFlashcards.iterator()).thenReturn(flashcardSetIterator);
        when(flashcardSetIterator.hasNext()).thenReturn(true, true, true, true, false);
        when(flashcardSetIterator.next()).thenReturn(userMatch, flashcardSet, flashcardSet, userMatch);

        when(userMatch.getUsername()).thenReturn("TestUser");
        when(flashcardSet.getUsername()).thenReturn("NotTestUser");

        when(flashcardSetData.getFlashcardSetsByKey("TestKey")).thenReturn(allFlashcards);

        List<FlashcardSet> retrievedList = flashcardSetManager.getFlashcardSetsByKey("TestUser", "TestKey");

        assertEquals("FlashcardManager cannot retrieve FlashcardSet with matching key, but non matching user",
                -1, retrievedList.indexOf(flashcardSet));
    }

    /*
     * Test getSearchedFlashcards
     */
    @Test
    public void testGetSearchedFlashcards() {
        List<Flashcard> flashcardList = mock(ArrayList.class);
        Iterator<Flashcard> flashcardIterator = mock(Iterator.class);

        when(flashcardList.iterator()).thenReturn(flashcardIterator);
        when(flashcardIterator.hasNext()).thenReturn(true, false);
        when(flashcardIterator.next()).thenReturn(flashcard);

        when(flashcardSetData.getFlashcardSet("setUUID")).thenReturn(flashcardSet);
        when(flashcardSet.getUsername()).thenReturn("TestUser");
        when(flashcardSet.getFlashcardSetName()).thenReturn("TestName");
        when(flashcard.getSetUUID()).thenReturn("setUUID");

        FlashcardSet retrievedSet = flashcardSetManager.getSearchedFlashcards("setUUID", flashcardList);

        assertEquals("FlashcardSetManager can check which flashcards from a list are a part of the set",
                        1, retrievedSet.size());

        assertEquals("FlashcardSetManager can check which flashcards from a list are a part of the set",
                flashcard, retrievedSet.getIndex(0));
    }

    @Test
    public void testGetSearchedFlashcardsMixedSets() {
        List<Flashcard> flashcardList = mock(ArrayList.class);
        Iterator<Flashcard> flashcardIterator = mock(Iterator.class);
        Flashcard setMatch = mock(Flashcard.class);

        when(flashcardList.iterator()).thenReturn(flashcardIterator);
        when(flashcardIterator.hasNext()).thenReturn(true, true, true, true, false);
        when(flashcardIterator.next()).thenReturn(flashcard, flashcard, setMatch, flashcard);

        when(flashcardSetData.getFlashcardSet("setUUID")).thenReturn(flashcardSet);
        when(flashcardSet.getUsername()).thenReturn("TestUser");
        when(flashcardSet.getFlashcardSetName()).thenReturn("TestName");

        when(flashcard.getSetUUID()).thenReturn("notSetUUID");
        when(setMatch.getSetUUID()).thenReturn("setUUID");

        FlashcardSet retrievedSet = flashcardSetManager.getSearchedFlashcards("setUUID", flashcardList);

        assertEquals("FlashcardSetManager can pick from a list of flashcards which ones are a part of the given set",
                1, retrievedSet.size());

        assertEquals("FlashcardSetManager can pick from a list of flashcards which ones are a part of the given set",
                setMatch, retrievedSet.getIndex(0));
    }

    @Test
    public void testGetSearchedFlashcardsSetNotManaged() {
        List<Flashcard> flashcardList = mock(ArrayList.class);

        FlashcardSet retrievedSet = flashcardSetManager.getSearchedFlashcards("setUUID", flashcardList);

        assertNull("can check which flashcards from a list are a part of the set if the set is not managed",
                retrievedSet);
    }

}
