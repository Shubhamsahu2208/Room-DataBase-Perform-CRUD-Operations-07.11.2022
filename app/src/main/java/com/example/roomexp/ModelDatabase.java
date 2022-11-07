package com.example.roomexp;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;


@Database(entities = {Model.class}, version = 1)
public abstract class ModelDatabase extends RoomDatabase {
    public abstract ModelDao userDao();

    private static ModelDatabase instance;

    public static synchronized ModelDatabase getDb(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context, ModelDatabase.class, "contacts")
                    .fallbackToDestructiveMigration()
                    .allowMainThreadQueries().build();
        }
        return instance;


    }

}



