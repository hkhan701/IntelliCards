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

    public String getUUIDString() {
        return uuid.toString();
    }

    public UUID getUUID() {
        return uuid;
    }

    public String getFlashCardSetName() {
        return flashcardSetName;
    }

    public void setFlashCardSetName(@NonNull String name) {
        this.flashcardSetName = name;
    }

    public void addFlashCard(@NonNull Flashcard flashcard) {
        if (flashcard != null) {
            flashcards.add(flashcard);
        }
    }

    // Return a flashcard set that contains only the active flashcards
    public FlashcardSet getActiveFlashcards() {
        FlashcardSet undeletedCards = new FlashcardSet();
        for (Flashcard card : flashcards) {
            if (!card.isDeleted()) {
                undeletedCards.addFlashCard(card);
            }
        }

        return undeletedCards;
    }

    // Return all the flashcards that have been deleted in this set
    public FlashcardSet getDeletedFlashCards() {
        FlashcardSet deletedCards = new FlashcardSet();
        for (Flashcard card : flashcards) {
            if (card.isDeleted()) {
                deletedCards.addFlashCard(card);
            }
        }
        return deletedCards;
    }

    public Flashcard getFlashCardById(UUID uuid) {
        for (Flashcard card : flashcards) {
            if (card.getUUID().equals(uuid)) {
                return card;
            }
        }
        return null;
    }

    public Flashcard getIndex(int index) {
        return flashcards.get(index);
    }

    public int size() {
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
