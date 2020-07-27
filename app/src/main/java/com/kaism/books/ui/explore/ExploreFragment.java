package com.kaism.books.ui.explore;

import android.content.Context;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.kaism.books.BuildConfig;
import com.kaism.books.R;
import com.kaism.books.api.ApiClient;
import com.kaism.books.api.ApiInterface;
import com.kaism.books.api.Item;
import com.kaism.books.api.SearchResponse;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

//TODO: implement search in the app bar per material rec
public class ExploreFragment extends Fragment {
	private int startIndex = 0;		// paging index
	private int maxResults = 20;	// how many results per page

	private Context context;
	private SearchView searchView;
	private FloatingActionButton fab;
	private ApiInterface apiInterface;
	private RecyclerView recyclerView;
	private EndlessRecyclerViewScrollListener scrollListener;
	private SearchResultsAdapter adapter;

	private List<Item> items;
	private String query;
	private String languageCode;

	@Override
	public void onAttach(@NonNull Context context) {
		super.onAttach(context);
		this.context = context;
	}

	@Nullable @Override
	public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		setRetainInstance(true);
		View view = inflater.inflate(R.layout.fragment_explore, container, false);

		// configure search view
		searchView = view.findViewById(R.id.search_input);
		searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

			@Override
			public boolean onQueryTextSubmit(String q) {
				query = q;
				searchView.clearFocus();
				performSearch();
				return true;
			}

			@Override
			public boolean onQueryTextChange(String newText) {
				return false;
			}
		});

		// configure api
		apiInterface = ApiClient.getClient().create(ApiInterface.class);

		// configure recycler view
		recyclerView = view.findViewById(R.id.recyclerView);
		LinearLayoutManager layoutManager = new LinearLayoutManager(context);
		recyclerView.setLayoutManager(layoutManager);
		recyclerView.setHasFixedSize(true);

		fab = getActivity().findViewById(R.id.fab);
		recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
			@Override
			public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
				if (dy > 0)
					fab.hide();
				else if (dy < 0)
					fab.show();
			}
		});

		// add scroll listener
		scrollListener = new EndlessRecyclerViewScrollListener(layoutManager) {
			@Override
			public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
				Toast.makeText(context, "Scrolled...page "+page, Toast.LENGTH_LONG).show();
				loadNextDataFromApi(query, languageCode, page);
			}
		};
		recyclerView.addOnScrollListener(scrollListener);

		return view;
	}

	@Override
	public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
	}

	// query is member variable
	// startIndex is member variable
	// countryCode and languageCode should also be member variable --> should this be a searchObject?
	private void performSearch() {
		// clear old results
		scrollListener.resetState();

		// get user phone settings
		String countryCode = getUserCountry(context);
//		languageCode = Locale.getDefault().getLanguage().toUpperCase(Locale.ENGLISH);
		languageCode = "en";

		// url encode query to handle spaces and other chars
		try {
			query = URLEncoder.encode(query, "utf-8");
			loadNextDataFromApi(query, languageCode, startIndex);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}

	private void loadNextDataFromApi(String query, String languageCode, int page) {
		startIndex = page * maxResults;

		Log.d("KDBUG", "loadNextDataFromApi: requesting page "+page+" of search \'"+query+"\' with lang "+languageCode);
		Log.d("KDBUG", "url should be: https://www.googleapis.com/books/v1/volumes?key="+BuildConfig.GOOGLE_API_KEY+"&langRestrict="+languageCode+"&q="+query+"&startIndex="+startIndex+"&maxResults="+maxResults);

		Call<SearchResponse> call = apiInterface.getResponse(query, languageCode, startIndex, maxResults);
		call.enqueue(new Callback<SearchResponse>() {

			@Override
			public void onResponse(@NonNull Call<SearchResponse> call, @NonNull Response<SearchResponse> response) {
				List<Item> nItems = Objects.requireNonNull(response.body()).getItems();
				if (nItems != null) {
					if (adapter == null) {
						items = nItems;
						adapter = new SearchResultsAdapter(nItems);
						recyclerView.setAdapter(adapter);
					} else {
						items.addAll(nItems);
						adapter.notifyDataSetChanged();
					}
				}
				Toast.makeText(context, "Loaded", Toast.LENGTH_SHORT).show();
			}

			@Override
			public void onFailure(@NonNull Call<SearchResponse> call, @NonNull Throwable t) {
				Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show();
			}
		});
	}

	/**
	 * Get ISO 3166-1 alpha-2 country code for this device
	 * Tries to use TelephonyManager to get country from SIM or network.
	 * If unavailable, returns locale from language setting.
	 *
	 * @param context Context reference to get the TelephonyManager instance from
	 * @return String country code
	 */
	private String getUserCountry(Context context) {
		TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
		if (tm != null) {
			String simCountry = tm.getSimCountryIso();
			if (simCountry != null && simCountry.length() == 2) { // SIM country code is available
				return simCountry.toUpperCase(Locale.ENGLISH);
			} else if (tm.getPhoneType() != TelephonyManager.PHONE_TYPE_CDMA) { // device is not 3G (would be unreliable)
				String networkCountry = tm.getNetworkCountryIso();
				if (networkCountry != null && networkCountry.length() == 2) { // network country code is available
					return networkCountry.toUpperCase(Locale.ENGLISH);
				}
			}
		}
		return Locale.getDefault().getCountry(); // default to locale from language setting
	}

}
