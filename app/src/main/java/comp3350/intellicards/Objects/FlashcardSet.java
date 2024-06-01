package comp3350.intellicards.Objects;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class FlashcardSet {
    private final UUID uuid;

    private String flashcardSetName;
    private List<Flashcard> flashcards;

    public FlashcardSet() {
        this.uuid = UUID.randomUUID();
        this.flashcards = new ArrayList<>();
    }

    public FlashcardSet(String name) {
        this();
        this.flashcardSetName = name;

    }

    public String getUuid() {
        return uuid.toString();
    }

    public void addFlashCard(Flashcard flashcard) {
        flashcards.add(flashcard);
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
        return flashcardSetName;
    }

    public void setFlashCardSetName(String name) {
        this.flashcardSetName = name;
    }

    public Flashcard getFlashCardById(String uuid)
    {
        for (Flashcard card : flashcards) {
            if (card.getUuid().equals(uuid)) {
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
                ", flashcardSetName='" + flashcardSetName + '\'' +
                ", flashcards=" + flashcards +
                '}';
    }
}
