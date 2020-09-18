package com.example.bored;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActionBar;
import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class Favourites extends AppCompatActivity {

    private ScrollView scrollView;
    private TextView textView;
    List<ActivityObject> favourites = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favourites);

        scrollView = findViewById(R.id.scroll_view);
        textView = findViewById(R.id.text_view_scroll);

        CreateScrollList();

    }

    private void CreateScrollList(){
        favourites = MainScreen.database.favouritesDao().GetAllFavourites();
        for (ActivityObject favourite: favourites){

        }
        textView.setText(favourites.get(0).activity);
    }
}