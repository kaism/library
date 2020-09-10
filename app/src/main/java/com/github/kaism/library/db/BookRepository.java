package com.github.kaism.library.db;

import android.app.Application;
import android.os.AsyncTask;
import androidx.lifecycle.LiveData;

import java.util.List;


public class BookRepository {
	private BookDao bookDao;
	private LiveData<List<Book>> allBooks;

	public BookRepository(Application application) {
		RoomDatabase db = RoomDatabase.getDatabase(application);
		bookDao = db.getBookDao();
		allBooks = bookDao.getAllBooks();
	}

	public LiveData<List<Book>> getAllBooks() {
		return allBooks;
	}

	// insert
	public void insert(Book book) {
		new insertAsyncTask(bookDao).execute(book);
	}

	private static class insertAsyncTask extends AsyncTask<Book, Void, Void> {
		private BookDao asyncTaskDao;

		insertAsyncTask(BookDao dao) {
			asyncTaskDao = dao;
		}

		@Override
		protected Void doInBackground(final Book... params) {
			asyncTaskDao.insert(params[0]);
			return null;
		}
	}

	// update
	public void update(Book book) {
		new updateAsyncTask(bookDao).execute(book);
	}

	private static class updateAsyncTask extends AsyncTask<Book, Void, Void> {
		private BookDao asyncTaskDao;

		updateAsyncTask(BookDao dao) {
			asyncTaskDao = dao;
		}

		@Override
		protected Void doInBackground(final Book... params) {
			asyncTaskDao.update(params[0]);
			return null;
		}
	}

	// delete
	public void delete(Book book) {
		new deleteBookAsyncTask(bookDao).execute(book);
	}

	private static class deleteBookAsyncTask extends AsyncTask<Book, Void, Void> {
		private BookDao asyncTaskDao;

		deleteBookAsyncTask(BookDao dao) {
			asyncTaskDao = dao;
		}

		@Override
		protected Void doInBackground(final Book... params) {
			asyncTaskDao.delete(params[0]);
			return null;
		}
	}

}
