package com.example.Intelvib;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Maintenance extends AppCompatActivity {

    private Button bearings,motor,tempControl,value,
    forty,fifty;
    private static int selectedValue;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maintainance);

        bearings = findViewById(R.id.bearings);
        motor = findViewById(R.id.motor);
        tempControl = findViewById(R.id.tempControl);
        forty = findViewById(R.id.FortyHz);
        fifty = findViewById(R.id.FiftyHz);
        value = findViewById(R.id.freq);
        motor.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view) {
                selectedValue = 10;
                Intent intent = new Intent(Maintenance.this,Reading.class);
                startActivity(intent);
            }
        });
        bearings.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view) {
                selectedValue = 20;
                Intent intent = new Intent(Maintenance.this,Reading.class);
                startActivity(intent);
            }
        });
        tempControl.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view) {
                selectedValue = 30;
                Intent intent = new Intent(Maintenance.this,Reading.class);
                startActivity(intent);
            }
        });
        forty.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view) {
                selectedValue = 40;
                Intent intent = new Intent(Maintenance.this,Reading.class);
                startActivity(intent);
            }
        });
        fifty.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view) {
                selectedValue = 50;
                Intent intent = new Intent(Maintenance.this,Reading.class);
                startActivity(intent);
            }
        });
    }

    public static int getSelectedValue() {
        return selectedValue;
    }
}