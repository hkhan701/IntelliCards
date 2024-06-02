package comp3350.intellicards.Objects;
import androidx.annotation.NonNull;

import java.util.UUID;

public class Flashcard {

    private final UUID uuid;
    private String answer;
    private String question;
    private boolean deleted;

    // Constructor with answer and question
    public Flashcard(@NonNull String answer, @NonNull String question) {
        this(UUID.randomUUID(), answer, question);
    }

    public Flashcard() {
        this(UUID.randomUUID(),"no answer set", "no question set");
    }

    // Private constructor used internally to ensure UUID is always set
    private Flashcard(UUID uuid, @NonNull String answer, @NonNull String question) {
        this.uuid = uuid;
        this.answer = answer;
        this.question = question;
        this.deleted = false;
    }

    public String getUUIDString() {
        return uuid.toString();
    }

    public UUID getUUID() {
        return uuid;
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
