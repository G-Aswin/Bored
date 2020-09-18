package com.example.bored;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "Favourites")
public class ActivityObject {

    @PrimaryKey
    public int id;

    @ColumnInfo(name = "activity")
    public String activity;
}
