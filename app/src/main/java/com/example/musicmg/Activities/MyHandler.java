package com.example.musicmg.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;

import com.example.musicmg.R;
import com.example.musicmg.databinding.ActivityHandlerBinding;
import com.example.musicmg.databinding.ActivityListenSongsBinding;
import com.example.musicmg.databinding.ActivityMainBinding;
import com.example.musicmg.databinding.ActivityTestBinding;

public class MyHandler extends AppCompatActivity {
    ActivityHandlerBinding bnd;
    private Handler handler;
    private boolean isRed = true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bnd = ActivityHandlerBinding.inflate(getLayoutInflater());
        setContentView(bnd.getRoot());

        handler = new Handler();
        // Create a Runnable to change text color every 10 seconds
        Runnable colorChangeRunnable = new Runnable() {
            @Override
            public void run() {
                if (isRed) {
                    bnd.changeColor.setTextColor(Color.GREEN);
                } else {
                    bnd.changeColor.setTextColor(Color.RED);
                }
                isRed = !isRed; // Change the flag
                handler.postDelayed(this, 2000); // Run the Runnable again after 10 seconds
            }
        };

        // Start the color change process
        handler.post(colorChangeRunnable);
    }
}