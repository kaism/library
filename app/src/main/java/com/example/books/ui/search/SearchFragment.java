package com.example.books.ui.search;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.books.BuildConfig;
import com.example.books.R;
import com.example.books.ui.list.ListAdapter;
import com.example.books.ui.list.ListItem;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

//TODO: implement search in the app bar per material rec
public class SearchFragment extends Fragment {
	private Context context;
	private SearchView searchView;
	private RecyclerView recyclerView;
	private RecyclerView.Adapter adapter;
	private List<ListItem> listItems;

	public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_search, container, false);
		searchView = view.findViewById(R.id.search);
		recyclerView = view.findViewById(R.id.recycler_view);
		recyclerView.setLayoutManager(new LinearLayoutManager(context));
		listItems = new ArrayList<>();
		return view;
	}

	@Override
	public void onAttach(@NonNull Context context) {
		super.onAttach(context);
		this.context = context;
	}

	@Override
	public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);

		searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
			@Override
			public boolean onQueryTextSubmit(String query) {
				Toast.makeText(context, "Searching for " + query, Toast.LENGTH_LONG).show();
				listItems.clear();
				loadRecyclerViewData(query);
				return false;
			}

			@Override
			public boolean onQueryTextChange(String newText) {
				return false;
			}
		});

	}

	private void loadRecyclerViewData(String query) {
		final ProgressDialog progressDialog = new ProgressDialog(context);
		progressDialog.setMessage("Loading data...");
		progressDialog.show();

		String url = getUrl(query);

		StringRequest stringRequest = new StringRequest(Request.Method.GET,
				url,
				new Response.Listener<String>() {
					@Override
					public void onResponse(String response) {
						progressDialog.dismiss();
						try {
							JSONObject jsonObject = new JSONObject(response);
							JSONArray array = jsonObject.getJSONArray("items");

							Log.d("MYTAG", "onResponse: num_returned: "+Integer.toString(array.length()));

							for (int i = 0; i<array.length(); i++) {
								JSONObject item = array.getJSONObject(i);
								JSONObject volumeInfo = item.getJSONObject("volumeInfo");
								JSONObject imageLinks = volumeInfo.getJSONObject("imageLinks");

								String title = volumeInfo.getString("title");
								String description = volumeInfo.getString("description");
								String image = imageLinks.getString("smallThumbnail");

								ListItem book = new ListItem(title, description, image);
								listItems.add(book);
							}
							adapter = new ListAdapter(listItems, context);
							recyclerView.setAdapter(adapter);
							adapter.notifyDataSetChanged();
						} catch (JSONException e) {
							e.printStackTrace();
							Log.i("MYTAG", "Error: " + e.getMessage());
						}
					}
				},
				new Response.ErrorListener() {
					@Override
					public void onErrorResponse(VolleyError error) {
						progressDialog.dismiss();
						Toast.makeText(context, error.getMessage(), Toast.LENGTH_LONG).show();
					}
				});
		RequestQueue requestQueue = Volley.newRequestQueue(context);
		requestQueue.add(stringRequest);
	}

	//TODO: urlencode query
	//TODO: add language/country param
	private String getUrl(String query) {
		String url = "https://www.googleapis.com/books/v1/volumes?q=";
		url += query;
		url += "&maxResults=10";
		url += "&key="+BuildConfig.GOOGLE_API_KEY;
		return url;
	}

}
