package com.github.kaism.library.db;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface BookDao {
	@Query("SELECT * from books ORDER BY title ASC")
	LiveData<List<Book>> getAllBooks();

	@Query("SELECT * from books LIMIT 1")
	List<Book> selectOne();

	@Query("SELECT * from books where bookId=:bookId")
	List<Book> selectById(int bookId);

	@Insert(onConflict = OnConflictStrategy.IGNORE)
	void insert(Book book);

	@Update
	void update(Book... book);

	@Delete
	void delete(Book book);

}
