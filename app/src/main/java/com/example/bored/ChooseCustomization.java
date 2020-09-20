package com.example.bored;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.PopupMenu;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;
import java.text.DecimalFormat;

public class ChooseCustomization extends AppCompatActivity implements PopupMenu.OnMenuItemClickListener {


    private TextView instructions;
    private SeekBar min_range;
    private SeekBar max_range;
    private TextView min_percentage;
    private TextView max_percentage;
    private Button save;
    private Button cancel;
    private Double min_progress = 0.00;
    private Double max_progress = 0.00;
    private String progress_recipient;
    private DecimalFormat df = new DecimalFormat("0");


    String type_selected;
    Integer participants = -1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_customization);

        instructions = findViewById(R.id.instruction_text_view);
        min_range = findViewById(R.id.min_range);
        max_range = findViewById(R.id.max_range);
        min_percentage = findViewById(R.id.min_percentage);
        max_percentage = findViewById(R.id.max_percentage);
        save = findViewById(R.id.save_button);
        cancel = findViewById(R.id.cancel_button);

        Window window = ChooseCustomization.this.getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.setStatusBarColor(ContextCompat.getColor(ChooseCustomization.this, R.color.colorPrimary));
    }

    public void type_menu_button(View view){
        PopupMenu popupMenu = new PopupMenu(this, view);
        popupMenu.setOnMenuItemClickListener(this);
        popupMenu.inflate(R.menu.type_menu);
        popupMenu.show();
    }

    @Override
    public boolean onMenuItemClick(MenuItem menuItem) {
        type_selected = "";
        switch (menuItem.getItemId()){
            case R.id.item1:
                type_selected = "education";
                break;
            case R.id.item2:
                type_selected = "recreational";
                break;
            case R.id.item3:
                type_selected = "social";
                break;
            case R.id.item4:
                type_selected = "diy";
                break;
            case R.id.item5:
                type_selected = "charity";
                break;
            case R.id.item6:
                type_selected = "cooking";
                break;
            case R.id.item7:
                type_selected = "relaxation";
                break;
            case R.id.item8:
                type_selected = "music";
                break;
            case R.id.item9:
                type_selected = "busywork";
                break;
            case R.id.p1:
                participants = 1;
                break;
            case R.id.p2:
                participants = 2;
                break;
            case R.id.p3:
                participants = 3;
                break;
            case R.id.p4:
                participants = 4;
                break;
            case R.id.p5:
                participants = 5;
                break;
            default:
                Toast.makeText(this, "Cancelled", Toast.LENGTH_SHORT).show();
        }

        if (!type_selected.equals("")){
            PassTypeOptionIntent(type_selected);
        }

        if (participants != -1){
            PassParticipantsOptionIntent(participants);
        }
        return false;
    }

    public void PassTypeOptionIntent(String type_option){
        Intent intent = new Intent(this, MainScreen.class);
        intent.putExtra("type", type_option);
        startActivity(intent);
        finish();
    }


    public void participants_menu_button(View view){
        PopupMenu popupMenu = new PopupMenu(this, view);
        popupMenu.setOnMenuItemClickListener(this);
        popupMenu.inflate(R.menu.participants_menu);
        popupMenu.show();
    }

    public void PassParticipantsOptionIntent(Integer participants){
        Intent intent = new Intent(this, MainScreen.class);
        intent.putExtra("participants", participants.toString());
        startActivity(intent);
        finish();
    }

        @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }




    public void CostButton(View view){
        instructions.setText(R.string.cost_slider_desc);
        progress_recipient = "cost";
        instructions.setVisibility(View.VISIBLE);
        save.setVisibility(View.VISIBLE);
        cancel.setVisibility(View.VISIBLE);

        min_range.setVisibility(View.VISIBLE);
        max_range.setVisibility(View.VISIBLE);
        min_percentage.setVisibility(View.VISIBLE);
        max_percentage.setVisibility(View.VISIBLE);

        min_range.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                min_progress = i/1.0;
                min_percentage.setText(String.format("%s%%", df.format(min_progress)));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                progress_recipient = "cost";
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                progress_recipient = "cost";
                min_progress = min_progress /100.0;
            }
        });

        max_range.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                max_progress = i / 1.0;
                max_percentage.setText(String.format("%s%%", df.format(max_progress)));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                progress_recipient = "cost";
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                progress_recipient = "cost";
                max_progress = max_progress / 100.0;
            }
        });
    }


    public void AccessibilityButton(View view){
        instructions.setText(R.string.access_slider_desc);
        progress_recipient = "accessibility";
        instructions.setVisibility(View.VISIBLE);
        save.setVisibility(View.VISIBLE);
        cancel.setVisibility(View.VISIBLE);


        min_range.setVisibility(View.VISIBLE);
        max_range.setVisibility(View.VISIBLE);

        min_percentage.setVisibility(View.VISIBLE);
        max_percentage.setVisibility(View.VISIBLE);

        min_range.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                min_progress = i/1.0;

                min_percentage.setText(String.format("%s%%", df.format(min_progress)));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                progress_recipient = "accessibility";
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                progress_recipient = "accessibility";
                min_progress = min_progress /100.0;
            }
        });

        max_range.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                max_progress = i/1.0;
                max_percentage.setText(String.format("%s%%", df.format(max_progress)));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                progress_recipient = "accessibility";
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                progress_recipient = "accessibility";
                max_progress = max_progress /100.0;
            }
        });

    }


    public void Save(View view){

        if (max_progress < min_progress){
            Toast.makeText(this, "Min range Greater than Max!!", Toast.LENGTH_LONG).show();
            return;
        }

        if (progress_recipient.equals("cost")){
            Intent intent = new Intent(this, MainScreen.class);
            intent.putExtra("min_cost", min_progress);
            intent.putExtra("max_cost", max_progress);
            startActivity(intent);
            finish();
        }

        else if (progress_recipient.equals("accessibility")){
            Intent intent = new Intent(this, MainScreen.class);
            intent.putExtra("min_accessibility", min_progress);
            intent.putExtra("max_accessibility", max_progress);
            startActivity(intent);
            finish();
        }
    }

    public void Cancel(View view){
        Toast.makeText(this, "Cancelled!", Toast.LENGTH_SHORT).show();
        instructions.setVisibility(View.INVISIBLE);
        min_range.setVisibility(View.INVISIBLE);
        max_range.setVisibility(View.INVISIBLE);
        min_percentage.setVisibility(View.INVISIBLE);
        max_percentage.setVisibility(View.INVISIBLE);
        save.setVisibility(View.INVISIBLE);
        cancel.setVisibility(View.INVISIBLE);
        progress_recipient = "";
    }

    public void GoBackHome(View view){
        onBackPressed();
    }

}