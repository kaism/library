package com.github.kaism.library.ui.books;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.github.kaism.library.R;
import com.github.kaism.library.db.Book;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class BookListFragment extends Fragment {
	private BookViewModel bookViewModel;
	private Context context;

	@Override
	public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
		super.onCreateView(inflater, parent, savedInstanceState);
		final View view = inflater.inflate(R.layout.fragment_library, parent, false);
		context = view.getContext();

		// set up recycler view
		final BookListAdapter adapter = new BookListAdapter(context);
		RecyclerView recyclerView = view.findViewById(R.id.recycler_view);
		recyclerView.setAdapter(adapter);
		recyclerView.setLayoutManager(new LinearLayoutManager(context));

		// set up view model and observer
		bookViewModel = ViewModelProviders.of(this).get(BookViewModel.class);
		bookViewModel.getBooks().observe(this.getViewLifecycleOwner(), new Observer<List<Book>>() {
			@Override
			public void onChanged(List<Book> books) {
				adapter.setBooks(books);
				int count = adapter.getItemCount();
				if (count > 0) {
					Toast.makeText(context, "No books", Toast.LENGTH_SHORT).show();
				} else {
					Toast.makeText(context, count+" books", Toast.LENGTH_SHORT).show();
				}
			}
		});

		// set delete item action with swipe
		ItemTouchHelper helper = new ItemTouchHelper(
				new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
					@Override
					public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder,
										  @NonNull RecyclerView.ViewHolder target) {
						return false;
					}

					@Override
					public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
						Book book = adapter.getBookAtPosition(viewHolder.getAdapterPosition());
						confirmDelete(book);
					}
				});
		helper.attachToRecyclerView(recyclerView);

		// hide bottom menu on scroll
		recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
			@Override
			public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
				FloatingActionButton fab = view.findViewById(R.id.fab);
				if (dy > 0)
					fab.hide();
				else if (dy < 0)
					fab.show();
			}
		});

		return view;
	}

	private void confirmDelete(final Book book) {
		AlertDialog.Builder builder = new AlertDialog.Builder(context)
				.setMessage(getString(R.string.dialog_question_confirm_delete_book))
				.setPositiveButton(R.string.dialog_button_delete, new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int id) {
						Toast.makeText(context, "Deleting " + book.getTitle(), Toast.LENGTH_LONG).show();
						bookViewModel.delete(book);
					}
				})
				.setNegativeButton(R.string.dialog_button_cancel, new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int id) {
						Toast.makeText(context, "Canceled", Toast.LENGTH_LONG).show();
					}
				});
		builder.show();
	}

}