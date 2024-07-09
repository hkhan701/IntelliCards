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

import comp3350.intellicards.Application.Services;
import comp3350.intellicards.Business.FlashcardManager;
import comp3350.intellicards.Objects.Flashcard;
import comp3350.intellicards.Objects.FlashcardSet;
import comp3350.intellicards.Presentation.Utils.FlashcardUtils;
import comp3350.intellicards.R;

public class CardViewAdapter extends RecyclerView.Adapter<CardViewAdapter.FlashcardViewHolder> {

    private final FlashcardSet flashcardSet;
    private final FlashcardManager flashcardManager;

    public CardViewAdapter(FlashcardSet flashcardSet) {
        this.flashcardSet = flashcardSet;
        this.flashcardManager = new FlashcardManager(Services.getFlashcardPersistence());
    }

    @NonNull
    @Override
    public FlashcardViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.flashcard_view, parent, false);
        return new FlashcardViewHolder(view);
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

    public class FlashcardViewHolder extends RecyclerView.ViewHolder {

        private final TextView flashcardTextView;
        private final Button deleteButton;
        private final Button editButton;
        private final Button flipButton;
        private boolean isFrontVisible = true;

        public FlashcardViewHolder(View itemView) {
            super(itemView);
            flashcardTextView = itemView.findViewById(R.id.flashcardTextRecycle);
            deleteButton = itemView.findViewById(R.id.deleteButton);
            editButton = itemView.findViewById(R.id.editButton);
            flipButton = itemView.findViewById(R.id.flipButton);
            setupListeners();
        }

        private void setupListeners() {
            deleteButton.setOnClickListener(v -> deleteFlashcard());
            editButton.setOnClickListener(v -> moveToEditFlashcardActivity());
            flipButton.setOnClickListener(v -> flipFlashcard());
        }

        public void bind(Flashcard flashcard) {
            flashcardTextView.setText(FlashcardUtils.getFlashcardQuestionWithHintText(flashcard));
            deleteButton.setTag(flashcard.getUUID());
            editButton.setTag(flashcard.getUUID());
            flipButton.setTag(flashcard.getUUID());
        }

        private void deleteFlashcard() {
            String flashcardUUID = (String) deleteButton.getTag();
            flashcardManager.markFlashcardAsDeleted(flashcardUUID);

            ViewGroup parentView = (ViewGroup) flashcardTextView.getParent();
            parentView.removeView(flashcardTextView);
            ViewGroup buttonParentView = (ViewGroup) editButton.getParent();
            if (buttonParentView != null) {
                buttonParentView.removeView(editButton);
                buttonParentView.removeView(deleteButton);
                buttonParentView.removeView(flipButton);
            }
        }

        private void moveToEditFlashcardActivity() {
            Intent intent = new Intent(itemView.getContext(), EditFlashcardActivity.class);
            intent.putExtra("flashcardUUID", (String) deleteButton.getTag());
            intent.putExtra("flashcardSetUUID", flashcardSet.getUUID());
            ((Activity) itemView.getContext()).startActivityForResult(intent, 1);
        }

        private void flipFlashcard() {
            String flashcardUUID = (String) deleteButton.getTag();
            Flashcard flashcard = flashcardSet.getFlashcardById(flashcardUUID);
            if (flashcard != null) {
                FlashcardUtils.toggleFlip(flashcard, flashcardTextView, isFrontVisible);
                isFrontVisible = !isFrontVisible;
            }
        }
    }
}
