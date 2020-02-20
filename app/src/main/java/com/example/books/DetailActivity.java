package com.example.books;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class DetailActivity extends AppCompatActivity {

	// Member variables for the views
	TextView titleView;
	ImageView imageView;
	TextView descriptionView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_detail);

		// initialize the views
		titleView = findViewById(R.id.list_item_title_detail);
		imageView = findViewById(R.id.list_item_image_detail);
		descriptionView = findViewById(R.id.list_item_description_detail);

		// populate the views with data from Intent Extras
		titleView.setText(getIntent().getStringExtra("title"));
		Picasso.get().load(getIntent().getStringExtra("image")).into(imageView);
		descriptionView.setText(getIntent().getStringExtra("description"));
	}
}
