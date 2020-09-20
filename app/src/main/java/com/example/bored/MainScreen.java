package com.example.bored;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.room.Room;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
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

public class MainScreen extends AppCompatActivity {

    TextView Activity;
    Button GenerateActivity;
    ProgressBar progressBar;
    TextView OptionsDisplay;
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

        Window window = MainScreen.this.getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.setStatusBarColor(ContextCompat.getColor(MainScreen.this, R.color.colorPrimary));

        Activity = findViewById(R.id.editTextTextMultiLine);
        GenerateActivity = findViewById(R.id.GetRandomActivity);
        progressBar = findViewById(R.id.progressBar);
        OptionsDisplay = findViewById(R.id.options_display);
        bottomAppBar = findViewById(R.id.bottom_bar);
        favouriteButton = findViewById(R.id.fav_button);
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
                        Toast.makeText(MainScreen.this, "Didn't select anything?", Toast.LENGTH_SHORT).show();
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
            OptionsDisplay.setText(String.format("Current Activity Type : %s%s", type.substring(0, 1).toUpperCase(), type.substring(1)));
            LoadURL(url);
        }

        String participants = getIntent().getStringExtra("participants");

        if (participants != null){
            url += "?participants=" + participants;
            requestQueue = Volley.newRequestQueue(this);
            if (participants.equals("5")){
                participants = "more than 4";
            }
            OptionsDisplay.setText(String.format("Current Activity is for %s participant(s).", participants));
            LoadURL(url);
        }

        double min_cost = getIntent().getDoubleExtra("min_cost", -1.0);
        double max_cost = getIntent().getDoubleExtra("max_cost", -1.0);


        if (min_cost != -1.0 && max_cost != -1.0){
            url += "?minprice=" + df.format(min_cost) +"&maxprice=" + df.format(max_cost);
            requestQueue = Volley.newRequestQueue(this);
            OptionsDisplay.setText(String.format("Current activity has min price set to %smax price set to %s", df.format(min_cost * 100), df.format(max_cost * 100)));
            LoadURL(url);
        }


        double min_acessibility = getIntent().getDoubleExtra("min_accessibility", -1.0);
        double max_acessibility = getIntent().getDoubleExtra("max_accessibility", -1.0);


        if (min_acessibility != -1.0 && max_acessibility != -1.0){
            url += "?minaccessibility=" + df.format(min_acessibility) + "&maxaccessibility=" + df.format(max_acessibility);
            requestQueue = Volley.newRequestQueue(this);
            OptionsDisplay.setText(String.format("Current activity has min accessibility set to %smax accessibility set to %s", df.format(min_acessibility * 100), df.format(max_acessibility * 100)));
            LoadURL(url);
        }
    }

    public void GenerateRandomActivity(View view) {
        favouriteButton.setImageResource(R.drawable.ic_star_empty);
        requestQueue = Volley.newRequestQueue(this);
        OptionsDisplay.setText(R.string.Default_Options_Display);
        LoadURL(randomurl);
    }


    public void StartCustomizeScreen(){
        Intent intent = new Intent(this, ChooseCustomization.class);
        startActivity(intent);
    }

    public void StartFavouritesScreen() {
        Intent intent = new Intent(this, Favourites.class);
        startActivity(intent);
    }


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