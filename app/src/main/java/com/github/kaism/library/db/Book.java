package com.github.kaism.library.db;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "books")
public class Book {

	@PrimaryKey(autoGenerate = true) protected int mId;
	@NonNull @ColumnInfo(name = "title") private String title;
	@ColumnInfo(name = "author") private String author;
	@ColumnInfo(name = "description") private String description;
	@ColumnInfo(name = "imageUrl") private String imageUrl;

	public Book(@NonNull String title, String author, String description, String imageUrl) {
		this.title = title;
		this.author = author;
		this.description = description;
		this.imageUrl = imageUrl;
	}

	@NonNull public String getTitle() {
		return this.title;
	}
	public String getAuthor() {
		return this.author;
	}
	public String getDescription() {
		return this.description;
	}
	public String getImageUrl() {
		return this.imageUrl;
	}

	public void setAuthor(String author) {
		this.author = author;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}
}
