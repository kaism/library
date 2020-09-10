package com.github.kaism.library.ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.github.kaism.library.R;
import com.github.kaism.library.db.Book;
import com.squareup.picasso.Picasso;

import java.util.List;


public class BookListAdapter extends RecyclerView.Adapter<BookListAdapter.BookViewHolder> {
	private final LayoutInflater layoutInflater;
	private List<Book> books;
	private Context context;

    BookListAdapter(Context context) {
		layoutInflater = LayoutInflater.from(context);
		this.context = context;
	}

	@NonNull
	@Override
	public BookViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
		return new BookViewHolder(layoutInflater.inflate(R.layout.list_item, parent, false));
	}

	@Override
	public void onBindViewHolder(@NonNull BookViewHolder holder, int position) {
		if (books != null) {
			Book current = books.get(position);
			holder.titleView.setText(current.getTitle());
			holder.descriptionView.setText(current.getDescription());
			Picasso.get().load(current.getImageUrl()).into(holder.imageView);
		}
	}

	void setBooks(List<Book> books) {
    	this.books = books;
    	notifyDataSetChanged();
	}

	public Book getBookAtPosition(int position) {
    	return books.get(position);
	}

	@Override
	public int getItemCount() {
    	if (books != null) {
    		return books.size();
		}
		return 0;
	}

	static class BookViewHolder extends RecyclerView.ViewHolder {
		TextView titleView;
		TextView descriptionView;
		ImageView imageView;

		private BookViewHolder(@NonNull View itemView) {
			super(itemView);
			titleView = itemView.findViewById(R.id.list_item_title);
			descriptionView = itemView.findViewById(R.id.list_item_description);
			imageView = itemView.findViewById(R.id.list_item_image);
		}
	}
}
