package com.kaism.books.ui.library;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.kaism.books.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class LibraryFragment extends Fragment {

	private Context context;
	private RecyclerView recyclerView;
	private RecyclerView.Adapter adapter;
	private List<LibraryItem> listItems;
	private FloatingActionButton fab;

	@Override
	public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_library, parent, false);
	 	fab = getActivity().findViewById(R.id.fab);
		recyclerView = view.findViewById(R.id.recycler_view);
		recyclerView.setLayoutManager(new LinearLayoutManager(context));
		recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
			@Override
			public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
				if (dy > 0)
					fab.hide();
				else if (dy < 0)
					fab.show();
			}
		});
		listItems = new ArrayList<>();
		loadRecyclerViewData();
		return view;
	}

	@Override
	public void onAttach(@NonNull Context context) {
		super.onAttach(context);
		this.context = context;
	}

	private void loadRecyclerViewData() {
		try {
			JSONObject o = new JSONObject(loadJSONFromAsset());
			JSONArray items = o.getJSONArray("items");

			for (int i = 0; i < items.length(); i++) {
				JSONObject item = items.getJSONObject(i);
				JSONObject volumeInfo = item.getJSONObject("volumeInfo");
				JSONObject imageLinks = volumeInfo.getJSONObject("imageLinks");

				String title = volumeInfo.getString("title");
				String description = volumeInfo.getString("description");
				String imageUrl = imageLinks.getString("smallThumbnail");

				LibraryItem book = new LibraryItem(title, description, imageUrl);
				listItems.add(book);
			}
			adapter = new LibraryAdapter(listItems, context);
			recyclerView.setAdapter(adapter);
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	private String loadJSONFromAsset() {
		String json;
		try {
			InputStream is = getActivity().getAssets().open("volumes.json");
			int size = is.available();
			byte[] buffer = new byte[size];
			is.read(buffer);
			is.close();
			json = new String(buffer, "UTF-8");
		} catch (IOException ex) {
			ex.printStackTrace();
			return null;
		}
		return json;
	}

}