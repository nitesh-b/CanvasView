package com.example.canvastest.activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.content.ContextCompat;

import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.canvastest.R;
import com.example.canvastest.customviews.CanvasView;
import com.google.android.material.slider.Slider;
import com.yalantis.ucrop.UCrop;
import com.yalantis.ucrop.UCropActivity;
import com.yalantis.ucrop.view.GestureCropImageView;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.Calendar;
import java.util.UUID;

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
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.image);
        File  file = bitmapToFile(this, bitmap, "image.jpeg");
        Uri imageUri = Uri.fromFile(file);
        cropImage(imageUri);
    }

    public  File bitmapToFile(Context context, Bitmap bitmap, String fileNameToSave) { // File name like "image.png"
        //create a file to write bitmap data
        File file = null;
        try {
            file = new File(getExternalFilesDir(Environment.DIRECTORY_PICTURES) , "saved_images");
            file.createNewFile();

//Convert bitmap to byte array
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 0 , bos); // YOU can also save it in JPEG
            byte[] bitmapdata = bos.toByteArray();

//write the bytes in file
            FileOutputStream fos = new FileOutputStream(file);
            fos.write(bitmapdata);
            fos.flush();
            fos.close();
            return file;
        }catch (Exception e){
            e.printStackTrace();
            return file; // it will return null
        }
    }
    private void setSliderListener() {
        strokeSlider.addOnChangeListener(new Slider.OnChangeListener() {
            @SuppressLint("RestrictedApi")
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
                //canvasView.setCanBeManipulated(false);
                break;
        }
    }




    private int CROP_IMG = 123;
    private void cropImage(Uri sourceUri) {
        UCrop.Options options = new UCrop.Options();
        options.setToolbarTitle("Crop image");
        options.setFreeStyleCropEnabled(true);
        options.setAllowedGestures(UCropActivity.ALL, UCropActivity.SCALE, UCropActivity.ROTATE);
        options.setHideBottomControls(false);
        options.setShowCropFrame(false);
        Uri destinationUri = Uri.fromFile(new File(getCacheDir(), UUID.randomUUID().toString()));
        UCrop uCrop = UCrop.of(sourceUri, destinationUri);

        uCrop.withAspectRatio(8, 5);
        uCrop.withOptions(options);
        uCrop.start(this, CROP_IMG);

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == CROP_IMG && resultCode == RESULT_OK) {
            if (data != null) {
                try{
                    final Uri resultUri = UCrop.getOutput(data);
                    Toast.makeText(this, "Success", Toast.LENGTH_SHORT).show();
                }catch(Exception ex){
                    Toast.makeText(this, "Error occured", Toast.LENGTH_SHORT).show();
                }

            } else {
                Toast.makeText(this, "Cannot get image. Please try again later", Toast.LENGTH_SHORT).show();
            }

        }
    }
}