package com.github.kaism.library.db;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;

@Database(entities = {Book.class}, version = 1, exportSchema = false)
public abstract class RoomDatabase extends androidx.room.RoomDatabase {
	private static RoomDatabase INSTANCE;

	public static RoomDatabase getDatabase(final Context context) {
		if (INSTANCE == null) {
			synchronized (RoomDatabase.class) {
				if (INSTANCE == null) {
					INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
							RoomDatabase.class, "kaism.books.database")
							.build();
				}
			}
		}
		return INSTANCE;
	}

	public abstract BookDao getBookDao();

}
