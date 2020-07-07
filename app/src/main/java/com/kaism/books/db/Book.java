package com.kaism.books.db;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "books")
public class Book {

	@PrimaryKey(autoGenerate = true) protected int mId;
	@NonNull @ColumnInfo(name = "title") private String mTitle;
	@NonNull @ColumnInfo(name = "author") private String mAuthor;
	@NonNull @ColumnInfo(name = "text") private String mText;
	@NonNull @ColumnInfo(name = "imageUrl") private String mImageUrl;

	// create new
	public Book(@NonNull String title, @NonNull String author, @NonNull String text, @NonNull String imageUrl) {
		this.mTitle = title;
		this.mAuthor = author;
		this.mText = text;
		this.mImageUrl = imageUrl;
	}

	// update
	@Ignore
	public Book(int id, @NonNull String title, @NonNull String author, @NonNull String text, @NonNull String imageUrl) {
		this.mId = id;
		this.mTitle = title;
		this.mAuthor = author;
		this.mText = text;
		this.mImageUrl = imageUrl;
	}

	public String getTitle() {
		return this.mTitle;
	}
	public String getAuthor() {
		return this.mAuthor;
	}
	public String getText() {
		return this.mText;
	}
	public String getImageUrl() {
		return this.mImageUrl;
	}
}
