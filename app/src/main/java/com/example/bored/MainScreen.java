package com.example.bored;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.room.Database;
import androidx.room.Room;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.bottomappbar.BottomAppBar;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.text.MessageFormat;

public class MainScreen extends AppCompatActivity {

    TextView Activity;
    Button GenerateActivity;
    Button Customize;
    ProgressBar progressBar;
    TextView OptionsDisplay;
    Button SameOptionAgain;
    BottomAppBar bottomAppBar;
    FloatingActionButton favouriteButton;

    private String url;
    private String randomurl = "https://www.boredapi.com/api/activity";
    private RequestQueue requestQueue;
    private DecimalFormat df = new DecimalFormat("0.00");
    public static FavouritesDatabase database;
    private int key;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_screen);

        Activity = findViewById(R.id.editTextTextMultiLine);
        GenerateActivity = findViewById(R.id.GetRandomActivity);
//        Customize = findViewById(R.id.CustomizeResults);
        progressBar = findViewById(R.id.progressBar);
        OptionsDisplay = findViewById(R.id.options_display);
//        SameOptionAgain = findViewById(R.id.same_option_again);
        bottomAppBar = findViewById(R.id.bottom_bar);
        setSupportActionBar(bottomAppBar);
        database = Room.databaseBuilder(getApplicationContext(), FavouritesDatabase.class, "Favourites_DB").allowMainThreadQueries().build();

        ReceiveCustomization();

        bottomAppBar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()){
                    case R.id.customize:
                        StartCustomizeScreen();
                        break;
                    case R.id.favourites:
                        StartFavouritesScreen();
                        break;
                    default:
                        Toast.makeText(MainScreen.this, "Didnt select anything?", Toast.LENGTH_SHORT).show();
                }
                return true;
            }
        });

    }

    void LoadURL(String url){
        progressBar.setVisibility(View.VISIBLE);
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    progressBar.setVisibility(View.INVISIBLE);
                    String activityresult = response.getString("activity");
                    key = response.getInt("key");
                    Activity.setText(activityresult);
                }
                catch (JSONException e){
                    progressBar.setVisibility(View.INVISIBLE);
                    Log.e("JSON", "Error Fetching URL");
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressBar.setVisibility(View.INVISIBLE);
                Log.e("JSON", "Volley Error", error);
            }
        });

        requestQueue.add(request);
    }

    void ReceiveCustomization() {
        url = "https://www.boredapi.com/api/activity";
        String type = getIntent().getStringExtra("type");

        if (type != null){
            url += "?type=" + type;
            requestQueue = Volley.newRequestQueue(this);
            OptionsDisplay.setText("Current Activity Type : " + type.substring(0,1).toUpperCase() + type.substring(1));
            LoadURL(url);
        }

        String participants = getIntent().getStringExtra("participants");

        if (participants != null){
            url += "?participants=" + participants;
            requestQueue = Volley.newRequestQueue(this);
            if (participants == "5"){
                participants = "more than 4";
            }
            OptionsDisplay.setText("Current Activity is for " + participants + " participant(s).");
            LoadURL(url);
        }

        Double cost = getIntent().getDoubleExtra("cost", -1.0);

        if (cost != -1.0){
            url += "?minprice=0&maxprice=" + df.format(cost);
            requestQueue = Volley.newRequestQueue(this);
            OptionsDisplay.setText("Current activity has max price set to " + df.format(cost*100));
            LoadURL(url);
        }


        Double acessibility = getIntent().getDoubleExtra("accessibility", -1.0);

        if (acessibility != -1.0){
            url += "?minaccessibility=0&maxaccessibility=" + df.format(acessibility);
            requestQueue = Volley.newRequestQueue(this);
            OptionsDisplay.setText("Current activity has max accessibility set to " + df.format(acessibility*100));
            LoadURL(url);
        }
    }

    public void GenerateRandomActivity(View view) {
        requestQueue = Volley.newRequestQueue(this);
        OptionsDisplay.setText("Current Activity : COMPLETELY RANDOM");
        LoadURL(randomurl);
    }

//    public void CustomizeResultsButton(View view){
//        Intent intent = new Intent(this, ChooseCustomization.class);
//        startActivity(intent);
//        finish();
//    }

    public void StartCustomizeScreen(){
        Intent intent = new Intent(this, ChooseCustomization.class);
        startActivity(intent);
        finish();
    }

    public void StartFavouritesScreen() {
        Intent intent = new Intent(this, Favourites.class);
        startActivity(intent);
    }

//    public void SameOptionAgain(View view){
//        requestQueue = Volley.newRequestQueue(this);
//        LoadURL(url);
//    }

    public void LoadAnotherActivityFAB(View view){
        requestQueue = Volley.newRequestQueue(this);
        if (Activity.getText().toString().equals("Your Activity Will be Generated Here!"))
            LoadURL(randomurl);
        else
            LoadURL(url);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.bottombarmenu, menu);
        return true;
    }

    public void AddedToFavourites(View view){

        if (database.favouritesDao().FindKey(key) > 0){
            Toast.makeText(this, "Already in Favourites!", Toast.LENGTH_SHORT).show();
            return;
        }
        database.favouritesDao().AddFavourite(key, Activity.getText().toString());
        Toast.makeText(this, "Added to favourites", Toast.LENGTH_SHORT).show();
    }
}