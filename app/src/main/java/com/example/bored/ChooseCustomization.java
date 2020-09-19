package com.example.bored;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.PopupMenu;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.toolbox.Volley;

public class ChooseCustomization extends AppCompatActivity implements PopupMenu.OnMenuItemClickListener {


    private TextView instructions;
    private SeekBar seekBar;
    private TextView percentage;
    private Button save;
    private Button cancel;
    private Double progress;
    private String progress_recipient;

    String type_selected;
    Integer participants = -1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_customization);

        instructions = findViewById(R.id.instruction_text_view);
        seekBar = findViewById(R.id.appCompatSeekBar);
        percentage = findViewById(R.id.percentage);
        save = findViewById(R.id.save_button);
        cancel = findViewById(R.id.cancel_button);
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
        instructions.setText("Slide around to set the cost. 0 is free and 100 is spending a bit ;)");
        progress_recipient = "cost";
        instructions.setVisibility(View.VISIBLE);
        save.setVisibility(View.VISIBLE);
        cancel.setVisibility(View.VISIBLE);

        seekBar.setVisibility(View.VISIBLE);
        percentage.setVisibility(View.VISIBLE);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                progress = i/1.0;
                percentage.setText(progress.toString() + "%");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                progress_recipient = "cost";
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                progress_recipient = "cost";
                progress = progress /100.0;
            }
        });
    }


    public void AccessibilityButton(View view){
        instructions.setText("Slide around to choose accessibility of that activity. 0 is easily available and 100 not so much.");
        progress_recipient = "accessibility";
        instructions.setVisibility(View.VISIBLE);
        save.setVisibility(View.VISIBLE);
        cancel.setVisibility(View.VISIBLE);


        seekBar.setVisibility(View.VISIBLE);
        percentage.setVisibility(View.VISIBLE);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                progress = i/1.0;
                percentage.setText(progress.toString() + "%");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                progress_recipient = "accessibility";
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                progress_recipient = "accessibility";
                progress = progress /100.0;
            }
        });

    }


    public void Save(View view){
        if (progress_recipient.equals("cost")){
            Intent intent = new Intent(this, MainScreen.class);
            intent.putExtra("cost", progress);
            startActivity(intent);
            finish();
        }

        else if (progress_recipient.equals("accessibility")){
            Intent intent = new Intent(this, MainScreen.class);
            intent.putExtra("accessibility", progress);
            startActivity(intent);
            finish();
        }
    }

    public void Cancel(View view){
        Toast.makeText(this, "Cancelled!", Toast.LENGTH_SHORT).show();
        instructions.setVisibility(View.INVISIBLE);
        seekBar.setVisibility(View.INVISIBLE);
        percentage.setVisibility(View.INVISIBLE);
        save.setVisibility(View.INVISIBLE);
        cancel.setVisibility(View.INVISIBLE);
        progress = 0.00;
        progress_recipient = "";
    }

    public void GoBackHome(View view){
        onBackPressed();
    }

}