package comp3350.intellicards.Objects;

import androidx.annotation.NonNull;

import java.util.UUID;

public class Flashcard {

    private final String uuid;
    private final String setID;
    private String answer;
    private String question;
    private String hint;
    private boolean deleted;
    private int attempted;
    private int correct;

    // Private constructor used internally to ensure UUID is always set
    public Flashcard(@NonNull String setID, @NonNull String answer, @NonNull String question, String hint) {
        this.uuid = UUID.randomUUID().toString();
        this.setID = setID;
        this.answer = answer;
        this.question = question;
        this.hint = hint;
        this.deleted = false;
        this.attempted = 0;
        this.correct = 0;
    }

    public Flashcard(@NonNull String uuid, @NonNull String setID, @NonNull String answer, @NonNull String question, String hint) {
        this.uuid = uuid;
        this.setID = setID;
        this.answer = answer;
        this.question = question;
        this.hint = hint;
        this.deleted = false;
        this.attempted = 0;
        this.correct = 0;
    }

    public String getUUID() {
        return this.uuid;
    }

    public String getAnswer() {
        return this.answer;
    }

    public String getQuestion() {
        return this.question;
    }

    public String getHint() {
        return this.hint;
    }

    public String getSetID() {
        return this.setID;
    }

    public int getAttempted() { return attempted; }

    public int getCorrect() { return correct; }

    public void setAnswer(@NonNull String answer) {
        this.answer = answer;
    }

    public void setQuestion(@NonNull String question) {
        this.question = question;
    }

    public void setHint(String hint) {
        this.hint = hint;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void markDeleted() {
        deleted = true;
    }

    public void markRecovered() {
        deleted = false;
    }

    public void markAttempted() { attempted++;}

    public void markAttemptedAndCorrect() { correct++; attempted++;}

    @NonNull
    @Override
    public String toString() {
        String info = String.format("uuid=%s,\nquestion='%s',\nanswer='%s'"
                , uuid
                , question
                , answer);
        if (hint != null && !hint.trim().isEmpty()) {
            info += String.format(",\nhint = '%s'", hint);
        }
        return info;
    }

    public String getDataFormatted() {
        String data = String.format("Q: %s\n", question);
        if ((hint != null) && !hint.trim().isEmpty())
            data += String.format("Hint: %s\n", hint);
        data += String.format("\nA: %s", answer);
        return data;
    }
}
