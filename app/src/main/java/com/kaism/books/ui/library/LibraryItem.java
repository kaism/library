package com.kaism.books.ui.library;

public class LibraryItem {
	private String title;
	private String description;
	private String imageUrl;

	public LibraryItem(String title, String description, String imageUrl) {
		this.title = title;
		this.description = description;
		this.imageUrl = imageUrl;
	}

	public String getTitle() { return title; }

	public String getDescription() { return description; }

	public String getImageUrl() { return imageUrl; }
}
