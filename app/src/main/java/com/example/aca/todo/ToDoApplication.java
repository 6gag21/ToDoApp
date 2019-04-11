package com.example.aca.todo;

import android.app.Application;

import com.example.aca.todo.persistence.dao.DbWrapper;

public class ToDoApplication extends Application {
    @Override
    public void onCreate(){
        super.onCreate();
        DbWrapper.create(this);
    }
}
