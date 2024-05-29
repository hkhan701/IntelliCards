package com.example.intellicards;
import java.util.UUID;

public class FlashCard {

    private final UUID uuid;


    private String answer;
    private String question;

    public FlashCard() {
        this.uuid = UUID.randomUUID();
    }

    public FlashCard(UUID uuid) {
        this();
        this.answer = "";
        this.question = "";
    }

    public String getUuid() {
        return uuid.toString();
    }

    public String getAnswer() {
        return answer;
    }

    public String getQuestion() {
        return question;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    @Override
    public String toString() {
        return "FlashCard{" +
                "uuid='" + uuid + '\'' +
                ", answer='" + answer + '\'' +
                ", question='" + question + '\'' +
                '}';
    }
}
