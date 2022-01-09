package com.example.Intelvib;

import static com.example.Intelvib.DBRead.parseItems;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;

import android.widget.Spinner;
import android.widget.Toast;


import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;

import java.util.ArrayList;


public class Reading extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    public static LineChart lineChart;
    private Button sGraph,absoluteMagnitude,startReading,powerSpectrum;
    public static String axis = "X-axis";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reading);


        startReading = findViewById(R.id.startReading);
        sGraph = findViewById(R.id.sGraph);
        Spinner mySpinner = findViewById(R.id.spinner);
        lineChart = findViewById(R.id.chart);
        absoluteMagnitude = findViewById(R.id.absoluteMagnitude);
        powerSpectrum = findViewById(R.id.powerSpectrum);

        ArrayAdapter<String> myAdapter = new ArrayAdapter<>(Reading.this,
                android.R.layout.simple_list_item_1,getResources().getStringArray(R.array.axes));
        myAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mySpinner.setAdapter(myAdapter);
        mySpinner.setOnItemSelectedListener(this);

        powerSpectrum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Reading.this,PowerSpectrum.class);
                startActivity(intent);
            }
        });

        startReading.setOnClickListener(new View.OnClickListener()
        {

            @Override
            public void onClick(View view) {
                getItems();
            }
        });

        sGraph.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                parseItems(DBRead.res);
            }
        });
        absoluteMagnitude.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Reading.this,Frequency.class);
                startActivity(intent);
            }
        });

    }
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        axis = parent.getItemAtPosition(position).toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    private void getItems() {

        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "https://script.google.com/macros/s/AKfycbwK8E_1hqI86xsBaU6sDws9cST1jXvioqAR2" +
                "x8b-hFgKUvjxvUy8f5mddXWojHtiQ0y/exec?action="+String.valueOf(Maintenance.getSelectedValue());
        DBRead dbRead = new DBRead(url,queue,getApplicationContext());
        dbRead.run();
    }

    public static void showChart_x(ArrayList<Entry> xValues) {

        ArrayList<ILineDataSet> lineDataSets = new ArrayList<>();

        lineDataSets.clear();

        LineDataSet lineDataSet1 = null;
        switch(axis){
            case "X-axis":
                lineDataSet1 = new LineDataSet(xValues,"X-axis");
                lineDataSet1.setColor(Color.RED);
                Toast.makeText(lineChart.getContext(), "Showing X-axis chart",Toast.LENGTH_SHORT).show();
            break;
            case "Y-axis":
                lineDataSet1 = new LineDataSet(xValues,"Y-axis");
                lineDataSet1.setColor(Color.BLUE);
                Toast.makeText(lineChart.getContext(), "Showing Y-axis chart",Toast.LENGTH_SHORT).show();
            break;
            case "Z-axis":
                lineDataSet1 = new LineDataSet(xValues,"Z-axis");
                lineDataSet1.setColor(Color.GREEN);
                Toast.makeText(lineChart.getContext(), "Showing Z-axis chart",Toast.LENGTH_SHORT).show();
            break;
        }
        lineDataSet1.setDrawCircles(false);
        lineDataSets.add(lineDataSet1);
        lineChart.setData(new LineData(lineDataSets));
    }


}