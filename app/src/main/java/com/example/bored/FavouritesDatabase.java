package com.example.bored;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {ActivityObject.class}, version = 1)
public abstract class FavouritesDatabase extends RoomDatabase {
    public abstract FavouritesDao favouritesDao();
}
