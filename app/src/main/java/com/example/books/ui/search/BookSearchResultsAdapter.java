package com.example.books.ui.search;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.books.R;
import com.example.books.api.Item;
import com.squareup.picasso.Picasso;

import java.util.List;

public class BookSearchResultsAdapter extends RecyclerView.Adapter<BookSearchResultsAdapter.MyViewHolder> {
	private List<Item> items;
	private Context context;

	public BookSearchResultsAdapter(List<Item> items, Context context) {
		this.items = items;
		this.context = context;
	}

	@NonNull
	@Override
	public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
		View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
		return new MyViewHolder(view);
	}

	@Override
	public void onBindViewHolder(@NonNull final MyViewHolder holder, int position) {
		Item item = items.get(position);
		holder.titleView.setText(item.getTitle());
		holder.descriptionView.setText(item.getDescription());
		String imgUrl = item.getImageUrl();
		Log.d("KDEBUG", "onBindViewHolder: "+imgUrl);

		holder.imageView.setClipToOutline(true);
		Picasso.get()
				.load(imgUrl)
				.into(holder.imageView);
	}

	@Override
	public int getItemCount() { return items.size(); }

	static class MyViewHolder extends RecyclerView.ViewHolder {
		TextView titleView;
		TextView descriptionView;
		ImageView imageView;

		MyViewHolder(@NonNull View itemView) {
			super(itemView);
			titleView = itemView.findViewById(R.id.list_item_title);
			descriptionView = itemView.findViewById(R.id.list_item_description);
			imageView = itemView.findViewById(R.id.list_item_image);
		}
	}

	public void addItems(List<Item> items) {
		this.items.addAll(items);
		notifyDataSetChanged();
	}
}
