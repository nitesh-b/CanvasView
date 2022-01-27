package com.example.canvastest.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.canvastest.R;
import com.example.canvastest.customviews.CanvasView;
import com.google.android.material.slider.Slider;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button clearButton;
    private CanvasView canvasView;

    private AppCompatButton red;
    private AppCompatButton blue;
    private AppCompatButton green;
    private AppCompatButton yellow;
    private AppCompatButton white;
    private AppCompatButton pink;

    private Slider strokeSlider;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        canvasView = findViewById(R.id.canvas_view);
        strokeSlider = findViewById(R.id.sldPenWidth);
        setSliderListener();
        red = findViewById(R.id.red_button);
        red.setOnClickListener(this);
        blue = findViewById(R.id.blue_button);
        blue.setOnClickListener(this);
        green = findViewById(R.id.green_button);
        green.setOnClickListener(this);
        yellow = findViewById(R.id.yellow_button);
        yellow.setOnClickListener(this);
        white = findViewById(R.id.white_button);
        white.setOnClickListener(this);
        pink = findViewById(R.id.pink_button);
        pink.setOnClickListener(this);

        clearButton = findViewById(R.id.clear_button);
        clearButton.setOnClickListener(this);
    }

    private void setSliderListener() {
        strokeSlider.addOnChangeListener(new Slider.OnChangeListener() {
            @Override
            public void onValueChange(@NonNull Slider slider, float value, boolean fromUser) {
                if(fromUser){
                    canvasView.setStroke(value);
                    canvasView.freshPaint();
                }
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.red_button:
                canvasView.setColor(Color.parseColor("#ff0000"));
                canvasView.freshPaint();
                break;
            case R.id.blue_button:
                canvasView.setColor(Color.parseColor("#0000ff"));
                canvasView.freshPaint();
                break;
            case R.id.green_button:
                canvasView.setColor(Color.parseColor("#008000"));
                canvasView.freshPaint();
                break;

            case R.id.yellow_button:
                canvasView.setColor(Color.parseColor("#ffff00"));
                canvasView.freshPaint();
                break;

            case R.id.white_button:
                canvasView.setColor(Color.parseColor("#ffffff"));
                canvasView.freshPaint();
                break;

            case R.id.pink_button:
                canvasView.setColor(Color.parseColor("#ffc0cb"));
                canvasView.freshPaint();
                break;

            case R.id.clear_button:
                canvasView.setImageResource(R.drawable.image);
                canvasView.clear();
                break;
        }
    }
}