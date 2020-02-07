package com.example.books.ui.scan;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.MenuItem;

import androidx.core.app.NavUtils;

import com.example.books.R;

public class ScanActivity extends AppCompatActivity {
	final Handler handler = new Handler();
	private String isbn = "9780132350884";	// hardcode isbn for now

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_scan);
		ActionBar actionBar = getSupportActionBar();
		if (actionBar != null) {
			actionBar.setDisplayHomeAsUpEnabled(true);
		}

		// show this activity for 3 seconds, then send hardcoded result back
		handler.postDelayed(new Runnable() {
			@Override
			public void run() {
				// Do something after 5s = 5000ms
				Intent output = new Intent();
				output.putExtra("isbn", isbn);
				setResult(RESULT_OK, output);
				finish();
			}
		}, 3000);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		if (id == android.R.id.home) {
			// This ID represents the Home or Up button.
			NavUtils.navigateUpFromSameTask(this);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
