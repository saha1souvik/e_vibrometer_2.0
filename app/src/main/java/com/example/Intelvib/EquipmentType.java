package com.example.Intelvib;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class EquipmentType extends AppCompatActivity {

    private Button demo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_equipment_type);

        demo = findViewById(R.id.demo);

        demo.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(EquipmentType.this, Maintenance.class);
                startActivity(intent);
            }
        });
    }
}