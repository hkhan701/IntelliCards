package comp3350.intellicards.Objects;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import comp3350.intellicards.Objects.FlashCard;

public class FlashCardSet {
    private final UUID uuid;

    private String flashCardSetName;
    private List<FlashCard> flashcards;

    public FlashCardSet() {
        this.uuid = UUID.randomUUID();
        this.flashcards = new ArrayList<>();
    }

    public FlashCardSet(String name) {
        this();
        this.flashCardSetName = name;

    }

    public String getUuid() {
        return uuid.toString();
    }

    public void addFlashCard(FlashCard flashCard) {
        flashcards.add(flashCard);
    }

    //return the undeleted flashcards
    public FlashCardSet getFlashcards() {
        FlashCardSet undeletedCards = new FlashCardSet();
        for(FlashCard card:flashcards)
        {
            if(!card.isDeleted())
            {
                undeletedCards.addFlashCard(card);
            }
        }
        return undeletedCards;
    }

    //return deleted flashcards
    public FlashCardSet getDeletedFlashCards()
    {
        FlashCardSet deletedCards = new FlashCardSet();
        for(FlashCard card:flashcards)
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
    public FlashCard getFlashCardById(String uuid)
    {
        for(int i = 0; i < size() ; i++)
        {
            FlashCard card = getIndex(i);
            if (card.getUuid().equals(uuid))
            {
                return card;
            }
        }
        return null;
    }

    public FlashCard getIndex(int index)
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
