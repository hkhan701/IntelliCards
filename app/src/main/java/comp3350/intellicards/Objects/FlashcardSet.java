package comp3350.intellicards.Objects;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class FlashcardSet {
    private final UUID uuid;

    private String flashCardSetName;
    private List<Flashcard> flashcards;

    public FlashcardSet() {
        this.uuid = UUID.randomUUID();
        this.flashcards = new ArrayList<>();
    }

    public FlashcardSet(String name) {
        this();
        this.flashCardSetName = name;

    }

    public String getUuid() {
        return uuid.toString();
    }

    public void addFlashCard(Flashcard flashCard) {
        flashcards.add(flashCard);
    }

    //return the undeleted flashcards
    public FlashcardSet getFlashcards() {
        FlashcardSet undeletedCards = new FlashcardSet();
        for(Flashcard card:flashcards)
        {
            if(!card.isDeleted())
            {
                undeletedCards.addFlashCard(card);
            }
        }
        return undeletedCards;
    }

    //return deleted flashcards
    public FlashcardSet getDeletedFlashCards()
    {
        FlashcardSet deletedCards = new FlashcardSet();
        for(Flashcard card:flashcards)
        {
            if(card.isDeleted())
            {
                deletedCards.addFlashCard(card);
            }
        }
        return deletedCards;
    }


    public String getFlashCardSetName() {
        return flashCardSetName;
    }

    public void setFlashCardSetName(String name) {
        this.flashCardSetName = name;
    }

//was considering implementing a hashtable for this search, but too much overhead
    public Flashcard getFlashCardById(String uuid)
    {
        for(int i = 0; i < size() ; i++)
        {
            Flashcard card = getIndex(i);
            if (card.getUuid().equals(uuid))
            {
                return card;
            }
        }
        return null;
    }

    public Flashcard getIndex(int index)
    {
        return flashcards.get(index);
    }

    public int size()
    {
        return flashcards.size();
    }



    @NonNull
    @Override
    public String toString() {
        return "FlashCardSet{" +
                "uuid=" + uuid +
                ", flashCardSetName='" + flashCardSetName + '\'' +
                ", flashcards=" + flashcards +
                '}';
    }
}
