package comp3350.intellicards.Presentation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import comp3350.intellicards.Objects.Flashcard;
import comp3350.intellicards.Persistence.InitializePersistence;
import comp3350.intellicards.R;

public class CardRecoverAdapter extends RecyclerView.Adapter<CardRecoverAdapter.ViewHolder> {

    private static List<Flashcard> flashcards;

    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder)
     */
    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView flashcardTextRecycle;
        private final Button recoverButton;

        public ViewHolder(View view) {
            super(view);
            // Define click listener for the ViewHolder's View
            flashcardTextRecycle = view.findViewById(R.id.flashcardTextRecycle);
            recoverButton = view.findViewById(R.id.recoveryButton);

            recoverButton.setOnClickListener(v -> {

                //set the flashcard as deleted
                Flashcard flashcardToRecover = flashcards.get(getBindingAdapterPosition());
                InitializePersistence.getFlashcardPersistence().restoreFlashcard(flashcardToRecover.getUUID());

                //delete the views associated with that flashcard
                ViewGroup parentView = ((ViewGroup) flashcardTextRecycle.getParent());
                parentView.removeView(flashcardTextRecycle);
                parentView.removeView(recoverButton);
            });

        }// end of ViewHolder class

        public TextView getTextView() {
            return flashcardTextRecycle;
        }

        public Button getRecoverButton() {
            return recoverButton;
        }
    }

    /**
     * Initialize the dataset of the Adapter
     *
     * @param flashcards contain the flashcards the data to populate views to be used
     *                   by RecyclerView
     */
    public CardRecoverAdapter(List<Flashcard> flashcards) {
        CardRecoverAdapter.flashcards = flashcards;
    }

    // Create new views (invoked by the layout manager)
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        // Create a new view, which defines the UI of the list item
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.recover_view, viewGroup, false);

        return new ViewHolder(view);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {
        Flashcard card = flashcards.get(position);
        viewHolder.getTextView().setText(card.toString());
        viewHolder.getRecoverButton().setTag(card.getUUID());
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return flashcards.size();
    }
}
