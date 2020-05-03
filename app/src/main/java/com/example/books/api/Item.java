package com.example.books.api;

import com.google.gson.annotations.SerializedName;

public class Item {
	@SerializedName("volumeInfo") private VolumeInfo volumeInfo;

	public String getTitle() { return volumeInfo.getTitle(); }
	public String getDescription() { return volumeInfo.getDescription(); }
	public String getImageUrl() { return volumeInfo.getImageUrl(); }
}

class VolumeInfo {
	@SerializedName("title") private String title;
	@SerializedName("description") private String description;
	@SerializedName("imageLinks") private ImageLinks imageLinks;

	String getTitle() { return title; }
	String getDescription() { return description; }
	String getImageUrl() { return imageLinks.getImageUrl(); }
}

class ImageLinks {
	@SerializedName("smallThumbnail") private String smallThumbnail;
	String getImageUrl() { return smallThumbnail.replace("http://","https://"); }
}
