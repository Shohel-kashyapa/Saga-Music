package com.example.sagamusic;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.content.Intent;
import android.view.View;
import android.view.accessibility.AccessibilityEventSource;
import android.widget.Button;

public class ordertrack extends AppCompatActivity {
            Button btnback;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ordertrack);

        View Button = findViewById(R.id.btnback);

        Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });



    }
}