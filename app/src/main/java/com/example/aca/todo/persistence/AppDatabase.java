package com.example.aca.todo.persistence;


import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;

import com.example.aca.todo.item.ToDoItem;
import com.example.aca.todo.persistence.dao.ToDoItemDao;
import com.example.aca.todo.util.DateUtil;

@Database(entities = ToDoItem.class, version = 1)
//@TypeConverters(DateUtil.class)
public abstract class AppDatabase extends RoomDatabase {

    public abstract ToDoItemDao toDoItemDao();

}
