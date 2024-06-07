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


import comp3350.intellicards.Objects.Flashcard;
import comp3350.intellicards.Objects.FlashcardSet;
import comp3350.intellicards.R;

public class CardViewAdapter extends RecyclerView.Adapter<CardViewAdapter.ViewHolder> {

    private FlashcardSet flashcardSet = null;

    /**
     * Provide a reference to the type of views that you are using
     */
    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView flashcardTextRecycle;
        private final Button deleteButton;
        private final Button editButton;

        public ViewHolder(View view, FlashcardSet flashcardSet) {
            super(view);

            // Define click listener for the ViewHolder's View
            flashcardTextRecycle = view.findViewById(R.id.flashcardTextRecycle);
            deleteButton = view.findViewById(R.id.deleteButton);
            editButton = view.findViewById(R.id.editButton);

            // Clicking this will mark the corresponding card as deleted
            // and it will not pop up as a flashcard in the recycle view until restored
            deleteButton.setOnClickListener(v -> {
                // set the flashcard as deleted
                Flashcard flashcardToDelete = flashcardSet.getFlashCardById((String) deleteButton.getTag());
                flashcardToDelete.markDeleted();

                //delete the views associated with that flashcard
                ViewGroup parentView = ((ViewGroup) flashcardTextRecycle.getParent());
                parentView.removeView(flashcardTextRecycle);

                // Remove the edit and delete buttons from the parent layout
                ViewGroup parentViewNew = (ViewGroup) editButton.getParent();
                if (parentViewNew != null) {
                    parentViewNew.removeView(editButton);
                    parentViewNew.removeView(deleteButton);
                }

            });

            editButton.setOnClickListener(v -> {

                Intent intent = new Intent(v.getContext(), EditFlashcardActivity.class);
                intent.putExtra("flashcardSetID", flashcardSet.getUUID());
                intent.putExtra("flashcardID", (String) deleteButton.getTag());
                ((Activity) v.getContext()).startActivityForResult(intent, 1);

                flashcardTextRecycle.setText(flashcardSet.getFlashCardById((String) deleteButton.getTag()).getDataFormatted());
            });

        }// end of ViewHolder class

        public TextView getTextView() {
            return flashcardTextRecycle;
        }

        public Button deleteButton() {
            return deleteButton;
        }

    }

    /**
     * Initialize the dataset of the Adapter
     *
     * @param flashcards contain the flashcards the data to populate views to be used
     *                   by RecyclerView
     */
    public CardViewAdapter(FlashcardSet flashcards) {
        flashcardSet = flashcards;
    }

    // Create new views (invoked by the layout manager)
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        // Create a new view, which defines the UI of the list item
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.flashcard_view, viewGroup, false);

        return new ViewHolder(view, flashcardSet);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {
        Flashcard card = flashcardSet.getIndex(position);

        viewHolder.getTextView().setText(card.getDataFormatted());
        viewHolder.deleteButton().setTag(card.getUUID());
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return flashcardSet.size();
    }
}

