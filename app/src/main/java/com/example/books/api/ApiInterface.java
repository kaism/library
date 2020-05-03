package com.example.books.api;

import com.example.books.BuildConfig;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiInterface {
	@GET("books/v1/volumes?key="+BuildConfig.GOOGLE_API_KEY)
	Call<SearchResponse> getResponse(@Query("langRestrict") String languageCode,
									 @Query("q") String query,
									 @Query("startIndex") int startIndex,
									 @Query("maxResults") int maxResults
						 );
}
