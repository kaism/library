package com.example.books;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.books.ui.scan.ScanActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

public class MainActivity extends AppCompatActivity {

	static final int SCAN_RESULT_REQUEST = 1001;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_items_list);
		BottomNavigationView navView = findViewById(R.id.nav_view);
		// Passing each menu ID as a set of Ids because each
		// menu should be considered as top level destinations.
		AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
				R.id.navigation_list, R.id.navigation_scan, R.id.navigation_search)
				.build();
		NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
		NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
		NavigationUI.setupWithNavController(navView, navController);

		FloatingActionButton fab = findViewById(R.id.fab);
		fab.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				Intent i = new Intent(getBaseContext(), ScanActivity.class);
				startActivityForResult(i, SCAN_RESULT_REQUEST);
			}
		});
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == SCAN_RESULT_REQUEST) {
			if (resultCode == RESULT_OK) {
				String isbn = data.getStringExtra("isbn");
				displayToast("ISBN: " + isbn);
			}
		}
	}


	/**
	 * Displays a Toast with the message.
	 *
	 * @param message message to display
	 */
	public void displayToast(String message) {
		Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
	}
}
