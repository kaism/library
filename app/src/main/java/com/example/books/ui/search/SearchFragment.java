package com.example.books.ui.search;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.books.R;
import com.example.books.api.ApiClient;
import com.example.books.api.ApiInterface;
import com.example.books.api.Item;
import com.example.books.api.SearchResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

//TODO: implement search in the app bar per material rec
public class SearchFragment extends Fragment {
	private String languageCode = "en";
	private String query = "Android";
	private int startIndex = 0;
	private int maxResults = 10;

	private Context context;
	private SearchView searchView;
	private RecyclerView recyclerView;
	private ProgressBar progressBar;

	private ApiInterface apiInterface;
	private BookSearchResultsAdapter adapter;

	@Override
	public void onAttach(@NonNull Context context) {
		super.onAttach(context);
		this.context = context;
	}

	public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		setRetainInstance(true);
		View view = inflater.inflate(R.layout.fragment_search, container, false);
		searchView = view.findViewById(R.id.search_view);
		recyclerView = view.findViewById(R.id.recycler_view);
		progressBar = view.findViewById(R.id.progress_bar);

		recyclerView.setLayoutManager(new LinearLayoutManager(context));
		progressBar.setVisibility(View.VISIBLE);

		apiInterface = ApiClient.getClient().create(ApiInterface.class);
		Call<SearchResponse> call = apiInterface.getResponse(languageCode, query, startIndex, maxResults);
		call.enqueue(new Callback<SearchResponse>() {
			@Override
			public void onResponse(@NonNull Call<SearchResponse> call, @NonNull Response<SearchResponse> response) {
				Log.d("KDEBUG", "onResponse: received");
				List<Item> items = null;
				SearchResponse body = response.body();
				if (body != null) {
					items = body.getItems();
					adapter = new BookSearchResultsAdapter(items, context);
					recyclerView.setAdapter(adapter);
					Toast.makeText(context, "Page loaded.", Toast.LENGTH_SHORT).show();
				} else {
					Toast.makeText(context, "Something went wrong", Toast.LENGTH_SHORT).show();
				}
				progressBar.setVisibility(View.GONE);
			}

			@Override
			public void onFailure(@NonNull Call<SearchResponse> call, @NonNull Throwable t) {
				String err = "callback failure: " + t.getMessage();
				Log.d("KDEBUG", "onFailure: "+err);
				Toast.makeText(context, err, Toast.LENGTH_LONG).show();
				progressBar.setVisibility(View.GONE);
			}
		});

		return view;
	}

	@Override
	public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);

		searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
			@Override
			public boolean onQueryTextSubmit(String query) {
				Toast.makeText(context, "Searching for " + query, Toast.LENGTH_LONG).show();
				return false;
			}

			@Override
			public boolean onQueryTextChange(String newText) {
				return false;
			}
		});

	}



}
