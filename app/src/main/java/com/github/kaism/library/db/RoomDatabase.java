package com.github.kaism.library.db;

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

	public abstract BookDao getBookDao();

	private static androidx.room.RoomDatabase.Callback roomDatabaseCallback = new androidx.room.RoomDatabase.Callback() {
		@Override
		public void onOpen(@NonNull SupportSQLiteDatabase db) {
			super.onOpen(db);
			new PopulateDbAsync(INSTANCE).execute();
		}
	};

	private static class PopulateDbAsync extends AsyncTask<Void, Void, Void> {
		private final BookDao bookDao;

		String[] titles = {"Don Quixote", "Harry Potter", "A Tale of Two Cities", "The Lord of the Rings", "The Little Prince"};
		String[] authors = {"Miguel de Cervantes", "J.K.Rowling", "Charles Dickens", "J. R. R. Tolkien", "Antoine de Saint-Exupéry"};
		String[] texts = {
				"The classic, satirical romance of an eccentric knight-errant, Don Quixote de La Mancha. It follows the adventures of Don Quixote and his rustic companion, Sancho Panza, in central Spain.",
				"Rescued from the outrageous neglect of his aunt and uncle, a young boy with a great destiny proves his worth while attending Hogwarts School for Witchcraft and Wizardry.",
				"During the French Revolution, a young Englishman gives up his life to save the husband of the woman he loves",
				"Presents the epic depicting the Great War of the Ring, a struggle between good and evil in Middle-earth, following the odyssey of Frodo the hobbit and his companions on a quest to destroy the Ring of Power",
				"An aviator whose plane is forced down in the Sahara Desert encounters a little prince from a small planet who relates his adventures in seeking the secret of what is important in life. Howard's new translation of this beloved classic beautifully reflects Saint-Exupery's unique, gifted style."
		};
		String[] imageUrls = {
				"https://books.google.com/books/content?id=HSycAQAACAAJ&printsec=frontcover&img=1&zoom=5&source=gbs_api",
				"https://books.google.com/books/content?id=HBVunAEACAAJ&printsec=frontcover&img=1&zoom=5&source=gbs_api",
				"https://books.google.com/books/content?id=i6r6Oyf7RpAC&printsec=frontcover&img=1&zoom=5&edge=curl&source=gbs_api",
				"https://books.google.com/books/content?id=XApOPwAACAAJ&printsec=frontcover&img=1&zoom=5&source=gbs_api",
				"https://books.google.com/books/content?id=nbkAiXfRlqkC&printsec=frontcover&img=1&zoom=5&source=gbs_api"
		};

		PopulateDbAsync(RoomDatabase db) {
			bookDao = db.getBookDao();
		}

		@Override
		protected Void doInBackground(final Void... voids) {
			if (bookDao.selectOne().size() < 1) {
				for (int i = 0; i <= titles.length - 1; i++) {
					Book book = new Book(titles[i], authors[i], texts[i], imageUrls[i]);
					bookDao.insert(book);
				}
			}
			return null;
		}
	}
}
