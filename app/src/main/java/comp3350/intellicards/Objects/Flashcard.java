package comp3350.intellicards.Objects;

import androidx.annotation.NonNull;

import java.util.UUID;

public class Flashcard {

    private final String uuid;
    private final String setUUID;
    private String answer;
    private String question;
    private String hint;
    private boolean deleted;
    private int attempted;
    private int correct;

    // Private constructor used internally to ensure UUID is always set
    public Flashcard(@NonNull String setUUID, @NonNull String question, @NonNull String answer, String hint) {
        this.uuid = UUID.randomUUID().toString();
        this.setUUID = setUUID;
        this.answer = answer;
        this.question = question;
        this.hint = hint;
        this.deleted = false;
        this.attempted = 0;
        this.correct = 0;
    }

    public Flashcard(@NonNull String uuid, @NonNull String setUUID, @NonNull String question, @NonNull String answer, String hint, boolean deleted, int attempted, int correct) {
        this.uuid = uuid;
        this.setUUID = setUUID;
        this.answer = answer;
        this.question = question;
        this.hint = hint;
        this.deleted = deleted;
        this.attempted = attempted;
        this.correct = correct;
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

    public String getSetUUID() {
        return this.setUUID;
    }

    public int getAttempted() {
        return attempted;
    }

    public int getCorrect() {
        return correct;
    }

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

    public void markAttempted() {
        attempted++;
    }

    public void markAttemptedAndCorrect() {
        correct++;
        attempted++;
    }

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

}
