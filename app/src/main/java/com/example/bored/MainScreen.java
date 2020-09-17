package com.example.bored;

import androidx.appcompat.app.AppCompatActivity;

import android.app.VoiceInteractor;
import android.content.Intent;
import android.graphics.Path;
import android.os.Bundle;
import android.util.Log;
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

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.GenericArrayType;
import java.text.DecimalFormat;

public class MainScreen extends AppCompatActivity {

    TextView Activity;
    Button GenerateActivity;
    Button Customize;
    ProgressBar progressBar;
    TextView OptionsDisplay;
    Button SameOptionAgain;

    private String url;
    private String randomurl = "https://www.boredapi.com/api/activity";
    private RequestQueue requestQueue;
    private DecimalFormat df = new DecimalFormat("0.00");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_screen);

        Activity = findViewById(R.id.editTextTextMultiLine);
        GenerateActivity = findViewById(R.id.GetRandomActivity);
        Customize = findViewById(R.id.CustomizeResults);
        progressBar = findViewById(R.id.progressBar);
        OptionsDisplay = findViewById(R.id.options_display);
        SameOptionAgain = findViewById(R.id.same_option_again);

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

    void LoadURL(String url){
        progressBar.setVisibility(View.VISIBLE);
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    progressBar.setVisibility(View.INVISIBLE);
                    String activityresult = response.getString("activity");
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

    public void GenerateRandomActivity(View view) {
        requestQueue = Volley.newRequestQueue(this);
        OptionsDisplay.setText("Current Activity : COMPLETELY RANDOM");
        LoadURL(randomurl);
    }

    public void CustomizeResultsButton(View view){
        Intent intent = new Intent(this, ChooseCustomization.class);
        startActivity(intent);
    }

    public void SameOptionAgain(View view){
        requestQueue = Volley.newRequestQueue(this);
        LoadURL(url);
    }
}