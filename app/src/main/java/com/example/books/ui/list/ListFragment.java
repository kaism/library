package com.example.books.ui.list;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.books.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class ListFragment extends Fragment {

	private Context context;
	private RecyclerView recyclerView;
	private RecyclerView.Adapter adapter;
	private List<ListItem> listItems;

	@Override
	public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_list, parent, false);
		recyclerView = view.findViewById(R.id.recycler_view);
		recyclerView.setLayoutManager(new LinearLayoutManager(context));
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

			for (int i = 0; i<items.length(); i++) {
				JSONObject item = items.getJSONObject(i);
				JSONObject volumeInfo = item.getJSONObject("volumeInfo");
				JSONObject imageLinks = volumeInfo.getJSONObject("imageLinks");

				String title = volumeInfo.getString("title");
				String description = volumeInfo.getString("description");
				String imageUrl = imageLinks.getString("smallThumbnail");

				ListItem book = new ListItem(title, description, imageUrl);
				listItems.add(book);
			}
			adapter = new ListAdapter(listItems, context);
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