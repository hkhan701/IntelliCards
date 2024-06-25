package comp3350.intellicards.Presentation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import comp3350.intellicards.Application.Services;
import comp3350.intellicards.Objects.Flashcard;
import comp3350.intellicards.Presentation.Utils.FlashcardUtils;
import comp3350.intellicards.R;

public class CardRecoverAdapter extends RecyclerView.Adapter<CardRecoverAdapter.FlashcardViewHolder> {

    private static List<Flashcard> flashcardList;

    public CardRecoverAdapter(List<Flashcard> flashcardList) {
        this.flashcardList = flashcardList;
    }

    @NonNull
    @Override
    public FlashcardViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recover_view, parent, false);
        return new FlashcardViewHolder(view);
    }

    @Override
    public void onBindViewHolder(FlashcardViewHolder holder, int position) {
        Flashcard flashcard = flashcardList.get(position);
        holder.bind(flashcard);
    }

    @Override
    public int getItemCount() {
        return flashcardList.size();
    }

    public static class FlashcardViewHolder extends RecyclerView.ViewHolder {

        private final TextView flashcardTextView;
        private final Button recoverButton;
        private final Button flipButton;
        private boolean isFrontVisible = true; // Default to showing the front of the card

        public FlashcardViewHolder(View itemView) {
            super(itemView);
            flashcardTextView = itemView.findViewById(R.id.flashcardTextRecycle);
            recoverButton = itemView.findViewById(R.id.recoveryButton);
            flipButton = itemView.findViewById(R.id.flipButton);

            setupListeners();
        }

        private void setupListeners() {
            recoverButton.setOnClickListener(v -> recoverFlashcard());
            flipButton.setOnClickListener(v -> flipFlashcard());
        }

        public void bind(Flashcard flashcard) {
            flashcardTextView.setText(FlashcardUtils.getFlashcardQuestionWithHintText(flashcard));
            recoverButton.setTag(flashcard.getUUID());
            flipButton.setTag(flashcard.getUUID());
        }

        private void recoverFlashcard() {
            int position = getBindingAdapterPosition();
            Flashcard flashcardToRecover = flashcardList.get(position);
            Services.getFlashcardPersistence().restoreFlashcard(flashcardToRecover.getUUID());

            ViewGroup parentView = (ViewGroup) flashcardTextView.getParent();
            parentView.removeView(flashcardTextView);
            ViewGroup buttonParentView = (ViewGroup) recoverButton.getParent();
            if (buttonParentView != null) {
                buttonParentView.removeView(recoverButton);
                buttonParentView.removeView(flipButton);
            }
        }

        private void flipFlashcard() {
            int position = getBindingAdapterPosition();
            Flashcard flashcard = flashcardList.get(position);
            FlashcardUtils.toggleFlip(flashcard, flashcardTextView, isFrontVisible);
            isFrontVisible = !isFrontVisible;
        }
    }
}
