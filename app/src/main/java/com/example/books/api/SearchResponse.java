package com.example.books.api;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SearchResponse {
	@SerializedName("totalItems") private int totalItems;
	@SerializedName("items") private List<Item> items;

	public int getTotalItems() { return totalItems; }
	public List<Item> getItems() { return items; }
}
