package com.example.sagamusic;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class MainActivity extends AppCompatActivity {



       // public class SplashScreenActivity extends AppCompatActivity {

    private static final int SPLASH_SCREEN = 900;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        // Set the layout for the Splash Screen
        setContentView(R.layout.activity_main);

        // Delay the screen display and then launch the main activity
        new Handler().postDelayed(new Runnable(){
            @Override
            public void run() {
                Intent intent = new Intent(MainActivity.this, login.class);
                startActivity(intent);
                finish();
            }
        }, SPLASH_SCREEN);
    }
}