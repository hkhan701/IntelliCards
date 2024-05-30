package comp3350.intellicards;
import androidx.annotation.NonNull;

import java.util.UUID;

public class FlashCard {

    private final UUID uuid;


    private String answer;
    private String question;
    private boolean deleted;

    public FlashCard() {
        this.uuid = UUID.randomUUID();
        this.deleted = false;
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

    public boolean isDeleted()
    {
        return deleted;
    }


    public void markDeleted() {
        deleted = true;
    }

    public void markRecovered()
    {
        deleted = false;
    }


    @NonNull
    @Override
    public String toString() {
        return "uuid='" + uuid + "'\n" +
                ", answer='" + answer + "'\n"  +
                ", question='" + question + "'\n";
    }
}
