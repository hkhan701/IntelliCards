package com.example.intellicards;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class FlashCardSet {
    private final UUID uuid;

    private String flashCardSetName;
    private List<FlashCard> flashcards;

    public FlashCardSet() {
        this.uuid = UUID.randomUUID();
    }

    public FlashCardSet(String name) {
        this();
        this.flashCardSetName = name;
        this.flashcards = new ArrayList<>();
    }

    public String getUuid() {
        return uuid.toString();
    }

    public void addFlashCard(FlashCard flashCard) {
        flashcards.add(flashCard);
    }

    public List<FlashCard> getFlashcards() {
        return flashcards;
    }

    public String getFlashCardSetName() {
        return flashCardSetName;
    }

    public void setFlashCardSetName(String name) {
        this.flashCardSetName = name;
    }

    @Override
    public String toString() {
        return "FlashCardSet{" +
                "uuid=" + uuid +
                ", flashCardSetName='" + flashCardSetName + '\'' +
                ", flashcards=" + flashcards +
                '}';
    }
}
