package com.example.bored;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Query;

import java.util.List;

@Dao
public interface FavouritesDao {

    @Query("INSERT INTO Favourites (id, activity) VALUES (:id, :activity)")
    void AddFavourite(int id, String activity);

    @Query("SELECT * FROM favourites")
    List<ActivityObject> GetAllFavourites();

    @Query("SELECT COUNT(*) FROM favourites WHERE id = :key")
    int FindKey(int key);

    @Delete
    int DeleteFavourite(ActivityObject activityObject);
}
