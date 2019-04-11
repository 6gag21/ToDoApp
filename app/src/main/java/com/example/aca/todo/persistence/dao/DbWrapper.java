package com.example.aca.todo.persistence.dao;

import android.arch.persistence.room.Room;
import android.content.Context;

import com.example.aca.todo.persistence.AppDatabase;

public class DbWrapper {
    private static AppDatabase sInstance = null;

    public static void create(Context context) {
        sInstance = Room.databaseBuilder(context,
                AppDatabase.class, "todoitems")
                .build();
    }

    public static AppDatabase getAppDatabase() {
        if (sInstance == null) {
            throw new IllegalStateException("Database not created");
        }

        return sInstance;
    }
}

