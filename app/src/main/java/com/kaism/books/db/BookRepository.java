package com.kaism.books.db;

import android.app.Application;
import android.os.AsyncTask;
import androidx.lifecycle.LiveData;

import java.util.List;


public class BookRepository {
	private BookDao mBookDao;
	private LiveData<List<Book>> mAllBooks;

	public BookRepository(Application application) {
		RoomDatabase db = RoomDatabase.getDatabase(application);
		mBookDao = db.bookDao();
		mAllBooks = mBookDao.getAllBooks();
	}

	public LiveData<List<Book>> getAllBooks() {
		return mAllBooks;
	}

	public void insert(Book book) {
		new insertAsyncTask(mBookDao).execute(book);
	}

	private static class insertAsyncTask extends AsyncTask<Book, Void, Void> {
		private BookDao mAsyncTaskDao;

		insertAsyncTask(BookDao dao) {
			mAsyncTaskDao = dao;
		}

		@Override
		protected Void doInBackground(final Book... params) {
			mAsyncTaskDao.insert(params[0]);
			return null;
		}
	}

	public void update(Book book) {
		new updateAsyncTask(mBookDao).execute(book);
	}

	private static class updateAsyncTask extends AsyncTask<Book, Void, Void> {
		private BookDao mAsyncTaskDao;

		updateAsyncTask(BookDao dao) {
			mAsyncTaskDao = dao;
		}

		@Override
		protected Void doInBackground(final Book... params) {
			mAsyncTaskDao.update(params[0]);
			return null;
		}
	}

	public void deleteBook(Book book) {
		new deleteBookAsyncTask(mBookDao).execute(book);
	}

	private static class deleteBookAsyncTask extends AsyncTask<Book, Void, Void> {
		private BookDao mAsyncTaskDao;

		deleteBookAsyncTask(BookDao dao) {
			mAsyncTaskDao = dao;
		}

		@Override
		protected Void doInBackground(final Book... params) {
			mAsyncTaskDao.deleteBook(params[0]);
			return null;
		}
	}

}
