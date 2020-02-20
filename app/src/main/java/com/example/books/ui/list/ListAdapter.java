package com.example.books.ui.list;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.books.DetailActivity;
import com.example.books.R;
import com.squareup.picasso.Picasso;

import java.util.List;


/***
 * Adapter class for RecyclerView, contains list item data
 */
public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ViewHolder> {

    // Member variables
	private List<ListItem> listItems;
	private Context context;

    /**
     * Constructor that passes in list item data and the context
     * @param listItems List containing the data
     * @param context Context of the application
     */
    public ListAdapter(List<ListItem> listItems, Context context) {
		this.listItems = listItems;
		this.context = context;
	}


    /**
     * Required method for creating ViewHolder object
     * @param parent The ViewGroup into which the new View will be added after it is bound to an adapter position
     * @param viewType The view type of the new View
     * @return The newly created ViewHolder
     */
    @NonNull
	@Override
	public ListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
		return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.list_item, parent, false));
	}

    /**
     * Required method that binds the data to the ViewHolder
     * @param holder The ViewHolder into which the data should be put
     * @param position The adapter position
     */
    @Override
	public void onBindViewHolder(@NonNull ListAdapter.ViewHolder holder, int position) {
    	// get current item
		final ListItem currentItem = listItems.get(position);

		// populate the views with data & image
        holder.bindTo(currentItem);
	}

    /**
     * Required method for determining the size of the data set
     * @return Size of the data set.
     */
	@Override
	public int getItemCount() {
		return listItems.size();
	}


    /**
     * ViewHolder class that represents each row of data in the RecyclerView
     */
	class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

		// Member variables for the views
		TextView titleView;
		TextView descriptionView;
		ImageView imageView;

        /**
         * Constructor for the ViewHolder, used in onCreateViewHolder()
         * @param itemView The root View of the list_item.xml layout file
         */
		ViewHolder(@NonNull View itemView) {
			super(itemView);

            // initialize the views
			titleView = itemView.findViewById(R.id.list_item_title);
			descriptionView = itemView.findViewById(R.id.list_item_description);
			imageView = itemView.findViewById(R.id.list_item_image);

			// set OnClickListener to the entire view
			itemView.setOnClickListener(this);
		}

		/**
		 * Required method for handling click events.
         * @param v The View that was clicked.
		 */
		@Override
		public void onClick(View v) {
			ListItem currentItem = listItems.get(getAdapterPosition());
			Intent detailIntent = new Intent(context, DetailActivity.class);
			detailIntent.putExtra("title", currentItem.getTitle());
			detailIntent.putExtra("image", currentItem.getImageUrl());
			detailIntent.putExtra("description", currentItem.getDescription());
			context.startActivity(detailIntent);
		}

		/**
		 * Method that populates the views with the data
		 * @param currentItem current ListItem of the data set.
		 */
		void bindTo(ListItem currentItem) {
			// populate the views with data
			titleView.setText(currentItem.getTitle());
			descriptionView.setText(currentItem.getDescription());
			Picasso.get().load(currentItem.getImageUrl()).into(imageView);
		}
	}
}
