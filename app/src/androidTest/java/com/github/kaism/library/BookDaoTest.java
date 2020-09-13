package com.github.kaism.library;

import android.content.Context;

import androidx.room.Room;
import androidx.test.core.app.ApplicationProvider;

import com.github.kaism.library.db.Book;
import com.github.kaism.library.db.BookDao;
import com.github.kaism.library.db.RoomDatabase;

import org.hamcrest.CoreMatchers;
import org.hamcrest.MatcherAssert;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

public class BookDaoTest {
	private BookDao bookDao;
	private RoomDatabase db;

	String title = "Don Quixote";

	@Before
	public void createDb() {
		Context context = ApplicationProvider.getApplicationContext();
		db = Room.inMemoryDatabaseBuilder(context, RoomDatabase.class).build();
		bookDao = db.getBookDao();
	}

	@After
	public void closeDb() {
		db.close();
	}

	@Test
	public void insertBook() {
		// insert book
		Book book = new Book(title, "", "", "");
		bookDao.insert(book);
		// verify in db
		book = bookDao.selectOne().get(0);
		assertThat(book.getTitle(), equalTo(title));
	}

	@Test
	public void deleteBook() {
		// insert
		Book book = new Book(title, "", "", "");
		bookDao.insert(book);

		// delete
		book = bookDao.selectOne().get(0);
		bookDao.delete(book);

		// verify not in db
		List<Book> empty = bookDao.selectOne();
		MatcherAssert.assertThat(empty.size(), CoreMatchers.equalTo(0));
	}

}

