package com.example.aca.todo.persistence.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.example.aca.todo.item.ToDoItem;

import java.util.List;

@Dao
public interface ToDoItemDao {

    @Query("SELECT * FROM todo_items")
    List<ToDoItem> findAll();

    @Query("SELECT * FROM todo_items" +
            " WHERE title LIKE :title")
    List<ToDoItem> findByTitle(String title);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insert(ToDoItem todoItemEntity);

    @Update
    void update(ToDoItem... entities);

    @Delete
    void delete(ToDoItem... entities);

}
