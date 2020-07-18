package com.saisreenivas.inputcontrols.myseekbar;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Layout;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import java.util.ArrayList;

import Data.CustomAdapter;
import Data.DBHandler;
import Model.ColorModel;

import static com.saisreenivas.inputcontrols.myseekbar.R.id.saveBtn;

public class MainActivity extends AppCompatActivity implements SeekBar.OnSeekBarChangeListener{

    private SeekBar colorRed, colorGreen, colorBlue;
    private Button backgroundBtn, defaultBtn, saveBtn;
    ImageView colorImage;
    ArrayList<ColorModel> colorsData = new ArrayList<>();
    TextView textRed, textGreen, textBlue, convertedValue, title, redText, greenText, blueText, hexaText;
    String hex;
    GridView gridView;
    DBHandler dbHandler;
    Activity activity;
    int red = 0, green = 0, blue = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        takeVar();
        onClicks();

        showColors();


    }

    public void takeVar(){
        colorRed = (SeekBar) findViewById(R.id.seekBarRed);
        colorGreen = (SeekBar) findViewById(R.id.seekBarGreen);
        colorBlue = (SeekBar) findViewById(R.id.seekBarBlue);

        convertedValue = (TextView) findViewById(R.id.hexaValue);
        textRed = (TextView) findViewById(R.id.textViewRed);
        textGreen = (TextView) findViewById(R.id.textViewGreen);
        textBlue = (TextView) findViewById(R.id.textViewBlue);

        colorImage = (ImageView) findViewById(R.id.outputColor);

        backgroundBtn = (Button) findViewById(R.id.setBackground);
        defaultBtn = (Button) findViewById(R.id.defaultBtn);
        saveBtn = (Button) findViewById(R.id.saveBtn);

        title = (TextView) findViewById(R.id.title);
        redText = (TextView) findViewById(R.id.redText);
        blueText = (TextView) findViewById(R.id.blueText);
        greenText = (TextView) findViewById(R.id.greenText);
        hexaText = (TextView) findViewById(R.id.hexaText);

        activity = this;

        textRed.setText(colorRed.getProgress() + "");
        textGreen.setText(colorGreen.getProgress() + "");
        textBlue.setText(colorBlue.getProgress() + "");
        colorRed.setOnSeekBarChangeListener(this);
        colorGreen.setOnSeekBarChangeListener(this);
        colorBlue.setOnSeekBarChangeListener(this);

        dbHandler = new DBHandler(this);


    }

    public void onClicks(){
        defaultBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                defaultColors();
                activity.findViewById(android.R.id.content).setBackgroundColor(Color.parseColor("#FFFFFF"));
                colorRed.setProgress(0);
                colorGreen.setProgress(0);
                colorBlue.setProgress(0);
            }
        });

        backgroundBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.v("onClick", red + " " + blue + " " + green +" " + setColorImage());
                activity.findViewById(android.R.id.content).setBackgroundColor(Color.parseColor(setColorImage()));
                colorCheck();
            }
        });

        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addColors();
            }
        });
    }

    public void colorCheck(){
        ColorDrawable colorDrawable = (ColorDrawable) activity.findViewById(android.R.id.content).getBackground();
        String mColor = Integer.toHexString(colorDrawable.getColor());

        if(mColor.equals("ff000000")){
            reverseDefaultColors();
            }
        else if(mColor.equals("ffffffff")){
            defaultColors();
        }
    }

    public void defaultColors(){
        textRed.setTextColor(Color.parseColor("#000000"));
        textGreen.setTextColor(Color.parseColor("#000000"));
        textBlue.setTextColor(Color.parseColor("#000000"));
        convertedValue.setTextColor(Color.parseColor("#000000"));
        title.setTextColor(Color.parseColor("#000000"));
        redText.setTextColor(Color.parseColor("#000000"));
        greenText.setTextColor(Color.parseColor("#000000"));
        blueText.setTextColor(Color.parseColor("#000000"));
        hexaText.setTextColor(Color.parseColor("#000000"));
    }

    public void reverseDefaultColors(){
        textRed.setTextColor(Color.parseColor("#FFFFFF"));
        textGreen.setTextColor(Color.parseColor("#FFFFFF"));
        textBlue.setTextColor(Color.parseColor("#FFFFFF"));
        convertedValue.setTextColor(Color.parseColor("#FFFFFF"));
        title.setTextColor(Color.parseColor("#FFFFFF"));
        redText.setTextColor(Color.parseColor("#FFFFFF"));
        greenText.setTextColor(Color.parseColor("#FFFFFF"));
        blueText.setTextColor(Color.parseColor("#FFFFFF"));
        hexaText.setTextColor(Color.parseColor("#FFFFFF"));
    }

    public void showColors(){
        colorsData.clear();
        colorsData = dbHandler.showAllColors();
        gridView = (GridView) findViewById(R.id.gridview);
        CustomAdapter adapter1 = new CustomAdapter(MainActivity.this ,R.layout.row , colorsData);
        gridView.setAdapter(adapter1);
        adapter1.notifyDataSetChanged();
    }


    public void addColors(){
        ColorDrawable drawable = (ColorDrawable) colorImage.getBackground();
        String color = Integer.toHexString(drawable.getColor());
        dbHandler.addColor(color);
        showColors();
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        switch (seekBar.getId()){
            case R.id.seekBarRed:
                textRed.setText(Integer.toString(progress));
                setColorImage();
                colorImage.setBackgroundColor(Color.parseColor(hex));
                break;
            case R.id.seekBarGreen:
                textGreen.setText(Integer.toString(progress));
                hex = setColorImage();
                colorImage.setBackgroundColor(Color.parseColor(hex));
                break;
            case R.id.seekBarBlue:
                textBlue.setText(Integer.toString(progress));
                hex = setColorImage();
                colorImage.setBackgroundColor(Color.parseColor(hex));
                break;
        }
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }

    public String setColorImage(){
        red = Integer.parseInt(textRed.getText().toString());
        green = Integer.parseInt(textGreen.getText().toString());
        blue = Integer.parseInt(textBlue.getText().toString());

        hex = String.format("#%02X%02X%02X", red , green , blue);
        convertedValue.setText(hex);
        return hex;
    }
}
