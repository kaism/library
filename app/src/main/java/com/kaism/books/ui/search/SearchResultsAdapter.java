package com.kaism.books.ui.search;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.kaism.books.R;
import com.kaism.books.api.Item;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.List;

public class SearchResultsAdapter extends RecyclerView.Adapter<SearchResultsAdapter.MyViewHolder> {
	private List<Item> mItems;

	public SearchResultsAdapter(List<Item> items) {
		mItems = items;
	}

	@NonNull
	@Override
	public SearchResultsAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
		try {
			Context context = parent.getContext();
			LayoutInflater inflater = LayoutInflater.from(context);
			View view = inflater.inflate(R.layout.search_item_layout, parent, false);
			return new MyViewHolder(view);
		} catch (Exception e) {
			Log.d("KDBUG", "onCreateViewHolder: "+e.getMessage());

			Context context = parent.getContext();
			LayoutInflater inflater = LayoutInflater.from(context);
			View view = inflater.inflate(R.layout.list_item, parent, false);
			return new MyViewHolder(view);
		}
	}

	@Override
	public void onBindViewHolder(@NonNull final MyViewHolder holder, int position) {
		Item item = mItems.get(position);
		holder.titleView.setText(item.getTitle());
		holder.descriptionView.setText(item.getDescription());

		// image
		holder.imageView.setClipToOutline(true);
		try {
			Picasso.get()
					.load(item.getImageUrl())
					.into(holder.imageView, new Callback() {
						@Override
						public void onSuccess() {  }
						@Override
						public void onError(Exception e) {
							holder.imageView.setImageResource(R.drawable.ic_no_image);
						}
					});
		} catch (Exception e) {
			Log.d("KDBUG", "onBindViewHolder: e caught");
		}
	}

	@Override
	public int getItemCount() { return mItems.size(); }

	static class MyViewHolder extends RecyclerView.ViewHolder {
		TextView titleView;
		TextView descriptionView;
		ImageView imageView;

		MyViewHolder(@NonNull View itemView) {
			super(itemView);
			titleView = itemView.findViewById(R.id.productName);
			descriptionView = itemView.findViewById(R.id.productText);
			imageView = itemView.findViewById(R.id.productImage);
		}
	}

}
