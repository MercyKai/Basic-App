package com.myapp.mybasicapplication;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class Activity2 extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity2);

        Button calculate = findViewById(R.id.calculate);
        EditText lengthInput = findViewById(R.id.lengthInput);
        EditText widthInput = findViewById(R.id.widthInput);
        TextView areaOutput = findViewById(R.id.area);
        TextView perimeterOutput = findViewById(R.id.perimeter);

        calculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int length = Integer.valueOf(lengthInput.getText().toString());
                int width = Integer.valueOf(widthInput.getText().toString());

                int area = length * width;
                int perimeter = 2 * (length + width);

                areaOutput.setText("The area = " + String.valueOf(area));
                perimeterOutput.setText("The perimeter = " + String.valueOf(perimeter));
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
