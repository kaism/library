package com.github.kaism.library.ui.books;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.github.kaism.library.R;
import com.github.kaism.library.Utils;
import com.github.kaism.library.db.Book;

public class BookDetailActivity extends AppCompatActivity {
	int bookId;
	BookViewModel bookViewModel;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		bookId = getIntent().getIntExtra("bookId", 0);
		if (bookId == 0) {
			errorNoBook();
		} else {
			Book book = bookViewModel.getBookById(bookId);
			if (book == null) {
				errorNoBook();
			} else {
				// set layout
				setContentView(R.layout.activity_book);

				// set up action bar
				setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
				ActionBar actionBar = getSupportActionBar();
				if (actionBar != null) {
					actionBar.setDisplayHomeAsUpEnabled(true);
				}

				// initialize views
				TextView titleView = findViewById(R.id.list_item_title_detail);
				ImageView imageView = findViewById(R.id.list_item_image_detail);
				TextView descriptionView = findViewById(R.id.list_item_description_detail);

				// populate views
				titleView.setText(book.getTitle());
				descriptionView.setText(book.getDescription());
				Utils.loadImage(imageView, book.getThumbnail());
			}
		}
	}

	@Override
	public boolean onOptionsItemSelected(@NonNull MenuItem item) {
		super.onOptionsItemSelected(item);
		if (item.getItemId() == android.R.id.home) {
			finish();
		}
		return true;
	}

	private void errorNoBook() {
		finish();
	}

}
