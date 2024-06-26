package comp3350.intellicards.Presentation;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import comp3350.intellicards.Business.FlashcardManager;
import comp3350.intellicards.Objects.Flashcard;
import comp3350.intellicards.Objects.FlashcardSet;
import comp3350.intellicards.Presentation.Utils.FlashcardUtils;
import comp3350.intellicards.R;

public class CardViewAdapter extends RecyclerView.Adapter<CardViewAdapter.FlashcardViewHolder> {

    private FlashcardSet flashcardSet;

    public CardViewAdapter(FlashcardSet flashcardSet) {
        this.flashcardSet = flashcardSet;
    }

    @NonNull
    @Override
    public FlashcardViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.flashcard_view, parent, false);
        return new FlashcardViewHolder(view, flashcardSet);
    }

    @Override
    public void onBindViewHolder(FlashcardViewHolder holder, int position) {
        Flashcard flashcard = flashcardSet.getIndex(position);
        holder.bind(flashcard);
    }

    @Override
    public int getItemCount() {
        return flashcardSet.size();
    }

    public static class FlashcardViewHolder extends RecyclerView.ViewHolder {

        private final TextView flashcardTextView;
        private final Button deleteButton;
        private final Button editButton;
        private final Button flipButton;
        private boolean isFrontVisible = true; // Default to showing the front of the card
        private FlashcardSet flashcardSet;
        private FlashcardManager flashcardManager;

        public FlashcardViewHolder(View itemView, FlashcardSet flashcardSet) {
            super(itemView);
            this.flashcardSet = flashcardSet;
            this.flashcardManager = new FlashcardManager();

            flashcardTextView = itemView.findViewById(R.id.flashcardTextRecycle);
            deleteButton = itemView.findViewById(R.id.deleteButton);
            editButton = itemView.findViewById(R.id.editButton);
            flipButton = itemView.findViewById(R.id.flipButton);

            setupListeners();
        }

        private void setupListeners() {
            deleteButton.setOnClickListener(v -> deleteFlashcard());
            editButton.setOnClickListener(v -> editFlashcard());
            flipButton.setOnClickListener(v -> flipFlashcard());
        }

        public void bind(Flashcard flashcard) {
            flashcardTextView.setText(FlashcardUtils.getFlashcardQuestionWithHintText(flashcard));
            deleteButton.setTag(flashcard.getUUID());
            editButton.setTag(flashcard.getUUID());
            flipButton.setTag(flashcard.getUUID());
        }

        private void deleteFlashcard() {
            Flashcard flashcardToDelete = flashcardSet.getFlashcardById((String) deleteButton.getTag());
            flashcardManager.markFlashcardAsDeleted(flashcardToDelete.getUUID());

            ViewGroup parentView = (ViewGroup) flashcardTextView.getParent();
            parentView.removeView(flashcardTextView);
            ViewGroup buttonParentView = (ViewGroup) editButton.getParent();
            if (buttonParentView != null) {
                buttonParentView.removeView(editButton);
                buttonParentView.removeView(deleteButton);
                buttonParentView.removeView(flipButton);
            }
        }

        private void editFlashcard() {
            Intent intent = new Intent(itemView.getContext(), EditFlashcardActivity.class);
            intent.putExtra("flashcardUUID", (String) deleteButton.getTag());
            intent.putExtra("flashcardSetUUID", flashcardSet.getUUID());
            ((Activity) itemView.getContext()).startActivityForResult(intent, 1);
        }

        private void flipFlashcard() {
            Flashcard flashcard = flashcardSet.getFlashcardById((String) deleteButton.getTag());
            if (flashcard != null) {
                FlashcardUtils.toggleFlip(flashcard, flashcardTextView, isFrontVisible);
                isFrontVisible = !isFrontVisible;
            }
        }
    }
}
