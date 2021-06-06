package com.example.countries.data.database;

import android.content.Context;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {CountryMapper.class}, version = 1)
public abstract class CountryDb extends RoomDatabase {

    public abstract CountryDao getDao();

    private volatile static CountryDb instance;

    public static CountryDb getInstance(Context context) {

        if(instance == null) {
            synchronized (CountryDb.class) {
                if(instance == null) {

                    instance = Room.databaseBuilder(context,CountryDb.class,"country_db")
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }
        }

        return instance;
    }
}
