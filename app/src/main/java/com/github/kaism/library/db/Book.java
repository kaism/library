package com.github.kaism.library.db;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "books")
public class Book {

	@PrimaryKey(autoGenerate = true) private int bookId;
	@NonNull @ColumnInfo(name = "title") private String title;
	@NonNull @ColumnInfo(name = "author") private String author;
	@NonNull @ColumnInfo(name = "description") private String description;
	@NonNull @ColumnInfo(name = "imageUrl") private String imageUrl;

	public Book(@NonNull String title, @NonNull String author, @NonNull String description, @NonNull String imageUrl) {
		this.title = title;
		this.author = author;
		this.description = description;
		this.imageUrl = imageUrl;
	}

	@NonNull public String getTitle() {
		return this.title;
	}
	@NonNull public String getAuthor() {
		return this.author;
	}
	@NonNull public String getDescription() {
		return this.description;
	}
	@NonNull public String getThumbnail() {
		return this.imageUrl;
	}

	public void setAuthor(@NonNull String author) {
		this.author = author;
	}
	public void setDescription(@NonNull String description) {
		this.description = description;
	}
	public void setImageUrl(@NonNull String imageUrl) {
		this.imageUrl = imageUrl;
	}
}
