package com.example.project2cs478pradhanbsuresh;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

public class ImageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();
        // Make a new ImageView
        ImageView imageView = new ImageView(getApplicationContext());
        // Get the ID of the image to display from the intent and set it as the image for this ImageView
        imageView.setImageResource(intent.getIntExtra(MainActivity.EXTRA_RES_ID, 0));
        // Define the content of this programmatically
        setContentView(imageView);
    }
}