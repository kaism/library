package com.kaism.books;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class DetailActivity extends AppCompatActivity {

	// Member variables for the views
	TextView titleView;
	ImageView imageView;
	TextView descriptionView;
	ActionBar actionBar;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_detail);

		// my_child_toolbar is defined in the layout file
		Toolbar myChildToolbar = findViewById(R.id.my_child_toolbar);
		setSupportActionBar(myChildToolbar);

		// set ActionBar and enable up button
		actionBar = getSupportActionBar();
		if (actionBar != null) {
			actionBar.setDisplayHomeAsUpEnabled(true);
		}

		// initialize the views
		titleView = findViewById(R.id.list_item_title_detail);
		imageView = findViewById(R.id.list_item_image_detail);
		descriptionView = findViewById(R.id.list_item_description_detail);

		// populate the views with data from Intent Extras
		titleView.setText(getIntent().getStringExtra("title"));
		Picasso.get().load(getIntent().getStringExtra("image")).into(imageView);
		descriptionView.setText(getIntent().getStringExtra("description"));
	}

	@Override
	public boolean onOptionsItemSelected(@NonNull MenuItem item) {
		super.onOptionsItemSelected(item);
		if (item.getItemId() == android.R.id.home) {
			finish();
		}

		return true;
	}
}
