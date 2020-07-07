package com.kaism.books.db;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.sqlite.db.SupportSQLiteDatabase;

@Database(entities = {Book.class}, version = 1, exportSchema = false)
public abstract class RoomDatabase extends androidx.room.RoomDatabase {
	private static RoomDatabase INSTANCE;

	public static RoomDatabase getDatabase(final Context context) {
		if (INSTANCE == null) {
			synchronized (RoomDatabase.class) {
				if (INSTANCE == null) {
					INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
							RoomDatabase.class, "kaism.books.database")
							.addCallback(roomDatabaseCallback)
							.build();
				}
			}
		}
		return INSTANCE;
	}

	public abstract BookDao bookDao();

	private static androidx.room.RoomDatabase.Callback roomDatabaseCallback = new androidx.room.RoomDatabase.Callback() {
		@Override
		public void onOpen(@NonNull SupportSQLiteDatabase db) {
			super.onOpen(db);
			new PopulateDbAsync(INSTANCE).execute();
		}
	};

	private static class PopulateDbAsync extends AsyncTask<Void, Void, Void> {
		private final BookDao mDao;

		String[] titles = {"Don Quixote", "Harry Potter", "A Tale of Two Cities", "The Lord of the Rings", "Le Petit Prince"};
		String[] authors = {"Miguel de Cervantes", "J.K.Rowling", "Charles Dickens", "J. R. R. Tolkien", "Antoine de Saint-Exupéry"};
		String[] texts = {"", "", "", "", ""};
		String[] imageUrls = {"", "", "", "", ""};

		PopulateDbAsync(RoomDatabase db) {
			mDao = db.bookDao();
		}

		@Override
		protected Void doInBackground(final Void... voids) {
			if (mDao.getAnyBook().length < 1) {
				for (int i = 0; i <= titles.length - 1; i++) {
					Book book = new Book(titles[i], authors[i], texts[i], imageUrls[i]);
					mDao.insert(book);
				}
			}
			return null;
		}
	}

}
