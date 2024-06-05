package comp3350.intellicards.Presentation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import comp3350.intellicards.Objects.Flashcard;
import comp3350.intellicards.Objects.FlashcardSet;
import comp3350.intellicards.R;

public class CardViewAdapter extends RecyclerView.Adapter<CardViewAdapter.ViewHolder> {

    private FlashcardSet flashcardSet;

    /**
     * Provide a reference to the type of views that you are using
     */
    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView flashcardTextRecycle;
        private final Button deleteButton;

        public ViewHolder(View view, FlashcardSet flashcardSet) {
            super(view);

            // Define click listener for the ViewHolder's View
            flashcardTextRecycle = (TextView) view.findViewById(R.id.flashcardTextRecycle);
            deleteButton = (Button) view.findViewById(R.id.deleteButton);

            // Clicking this will mark the corresponding card as deleted
            // and it will not pop up as a flashcard in the recycle view until restored
            deleteButton.setOnClickListener(v -> {
                //set the flashcard as deleted
                Flashcard card = flashcardSet.getFlashCardById((String) deleteButton.getTag());
                card.markDeleted();

                //delete the views associated with that flashcard
                ViewGroup parentView = ((ViewGroup) flashcardTextRecycle.getParent());
                parentView.removeView(flashcardTextRecycle);
                parentView.removeView(deleteButton);

            });

        }//end of ViewHolder class

        public TextView getTextView() {
            return flashcardTextRecycle;
        }

        public Button deleteButton() {
            return deleteButton;
        }

        public View getView() {
            return super.itemView.getRootView();
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
        viewHolder.getTextView().setText(card.toString());
        viewHolder.deleteButton().setTag(card.getUUID());

    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return flashcardSet.size();
    }
}

