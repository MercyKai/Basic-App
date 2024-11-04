package com.myapp.mybasicapplication;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.concurrent.ThreadLocalRandom;

public class Activity3 extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity3);

        Button rollButton = findViewById(R.id.rollButton);
        TextView resultsTextView = findViewById(R.id.resultsTextView);
        SeekBar seekBar = findViewById(R.id.seekBar2);

        rollButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int bound = seekBar.getProgress();
                if (bound > 0) {
                    int rand = ThreadLocalRandom.current().nextInt(bound);
                    resultsTextView.setText(String.valueOf(rand));
                } else {
                    Toast.makeText(Activity3.this, "Please set a positive value!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        Button btn = (Button) findViewById(R.id.btn_OK);
        btn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                finish();
            }
        });

    }
}
