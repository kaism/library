package com.github.kaism.library.ui;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.github.kaism.library.db.Book;
import com.github.kaism.library.db.BookRepository;

import java.util.List;

public class BookViewModel extends AndroidViewModel {
	private BookRepository bookRepository;
	private LiveData<List<Book>> books;

	public BookViewModel(Application application) {
		super(application);
		bookRepository = new BookRepository(application);
		books = bookRepository.getAllBooks();
	}

	public LiveData<List<Book>> getBooks() {
		return books;
	}

	public void insert(Book book) {
		bookRepository.insert(book);
	}

	public void update(Book book) {
		bookRepository.update(book);
	}

	public void delete(Book book) {
		bookRepository.delete(book);
	}
}
