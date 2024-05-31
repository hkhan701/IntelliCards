package comp3350.intellicards.Presentation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import comp3350.intellicards.Objects.FlashCard;
import comp3350.intellicards.Objects.FlashCardSet;
import comp3350.intellicards.R;

public class CardViewAdapter extends RecyclerView.Adapter<CardViewAdapter.ViewHolder> {

    private FlashCardSet flashCardSet;

    private AdapterView.OnItemClickListener deleteButtonClick;


    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder)
     */
    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView flashCardTextRecycle;
        private final Button deleteButton;

        public ViewHolder(View view, AdapterView.OnItemClickListener deleteButtonClick, FlashCardSet flashCardSet) {
            super(view);
            // Define click listener for the ViewHolder's View
            flashCardTextRecycle = (TextView) view.findViewById(R.id.flashCardTextRecycle);
            deleteButton = (Button) view.findViewById(R.id.deleteButton);

            //Clicking this will mark the cooresponding flashcard as deleted
            // and it will not pop up as a flashcard until restored
            deleteButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                        //set the flashcard as deleted
                        FlashCard card = flashCardSet.getFlashCardById((String)deleteButton.getTag());
                        card.markDeleted();

                        //delete the views associated with that flashcard
                        ViewGroup parentView = ((ViewGroup) flashCardTextRecycle.getParent());
                        parentView.removeView(flashCardTextRecycle);
                        parentView.removeView(deleteButton);

                }
            });

        }

        public TextView getTextView() {
            return flashCardTextRecycle;
        }

        public Button deleteButton() {
            return deleteButton;
        }

        public View getView()
        {
            return super.itemView.getRootView();
        }

    }

    /**
     * Initialize the dataset of the Adapter
     *
     * @param flashCards contain the flashcards the data to populate views to be used
     * by RecyclerView
     */
    public CardViewAdapter(FlashCardSet flashCards) {
        flashCardSet = flashCards;
    }

    // Create new views (invoked by the layout manager)
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        // Create a new view, which defines the UI of the list item
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.flashcard_view, viewGroup, false);

        return new ViewHolder(view,deleteButtonClick,flashCardSet);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {

        FlashCard card = flashCardSet.getIndex(position);
        viewHolder.getTextView().setText(card.toString());
        viewHolder.deleteButton().setTag(card.getUuid());

    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return flashCardSet.size();
    }
}

