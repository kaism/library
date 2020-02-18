package com.example.books.ui.list;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.books.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ViewHolder> {
	private List<ListItem> listItems;
	private Context context;

	public ListAdapter(List<ListItem> listItems, Context context) {
		this.listItems = listItems;
		this.context = context;
	}

	@NonNull
	@Override
	public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
		View v = LayoutInflater.from(parent.getContext())
				.inflate(R.layout.list_item, parent, false);
		return new ViewHolder(v);
	}

	@Override
	public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
		final ListItem listItem = listItems.get(position);
		holder.titleView.setText(listItem.getTitle());
		holder.descriptionView.setText(listItem.getDescription());
		Picasso.get()
			.load(listItem.getImageUrl())
			.into(holder.imageView);
	}

	@Override
	public int getItemCount() {
		return listItems.size();
	}

	class ViewHolder extends RecyclerView.ViewHolder {
		LinearLayout layout;
		TextView titleView;
		TextView descriptionView;
		public ImageView imageView;

		ViewHolder(@NonNull View itemView) {
			super(itemView);
			layout = itemView.findViewById(R.id.list_item_layout);
			titleView = itemView.findViewById(R.id.list_item_title);
			descriptionView = itemView.findViewById(R.id.list_item_description);
			imageView = itemView.findViewById(R.id.list_item_image);
		}
	}
}
