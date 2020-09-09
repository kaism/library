package com.github.kaism.library.api;

import com.github.kaism.library.BuildConfig;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiInterface {
	@GET("books/v1/volumes?country=US&key="+BuildConfig.GOOGLE_API_KEY)
	Call<SearchResponse> getResponse(@Query("q") String query,
									 @Query("langRestrict") String languageCode,
									 @Query("startIndex") int startIndex,
									 @Query("maxResults") int maxResults
						 );
}
