package com.example.Intelvib;

import static com.example.Intelvib.DBRead.times;
import static com.example.Intelvib.Reading.axis;

import android.graphics.Color;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;


import com.github.mikephil.charting.data.Entry;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.GridLabelRenderer;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

public class PowerSpectrum extends AppCompatActivity {

    private static GraphView graphView;
    private static Button button;
    private static TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_power_spectrum);
        graphView = findViewById(R.id.gView);
        button = findViewById(R.id.button);



        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                calculate();
            }
        });
    }

    public void calculate(){
        List<Float> freq = Frequency.calc_freq(times);
        List<Float> voltage = new ArrayList<>();
        for(Entry values : DBRead.xValues){
            voltage.add(values.getY());
        }
        Float[] mag =Frequency.calc_dft(voltage);
        List<Float> res = LineSpace.divide(mag,freq.size());
        res = LineSpace.dividePower(res);
        res = LineSpace.multiply(res);
        pointCreation(freq,res);
    }

    private void pointCreation(List<Float> time, List<Float> mag) {
        Point point = new Point();
        if(time.size() == mag.size()){
            LineGraphSeries<DataPoint> series = new LineGraphSeries<>();
            IntStream.range(0,mag.size()).forEach( i->
                    series.appendData(new DataPoint(time.get(i),mag.get(i)),true,mag.size()));
            showChart(series);
        }
        else
            Toast.makeText(this.getApplicationContext(), "Anomaly Data", Toast.LENGTH_SHORT).show();
    }

    public static void showChart(LineGraphSeries<DataPoint> series) {

        switch(axis){
            case "X-axis":
                series.setColor(Color.RED);
               // textView.setText("X-axis Graph");
                graphView.setTitleTextSize(500);
                graphView.setTitleTextSize(Color.BLUE);
                break;
            case "Y-axis":
                series.setColor(Color.BLUE);
                //textView.setText("Y-axis Graph");
                graphView.setTitleTextSize(500);
                graphView.setTitleTextSize(Color.BLUE);
                break;
            case "Z-axis":
                series.setColor(Color.GREEN);
                //textView.setText("Z-axis Graph");
                graphView.setTitleTextSize(500);
                graphView.setTitleTextSize(Color.BLUE);
                break;
        }
        graphView.addSeries(series);
        graphView.getViewport().setMinX(series.getLowestValueX());
        graphView.getViewport().setMaxX(series.getHighestValueX()+10.0);
        graphView.getViewport().setMinY(-50.0);
        graphView.getViewport().setMaxY(series.getHighestValueY()+50);
        graphView.getViewport (). setScalable ( true ); // activate horizontal scrolling
        graphView.getViewport (). setScrollable ( true ); // activate horizontal and vertical zooming and scrolling
        graphView.getViewport().setYAxisBoundsManual(true);
        graphView.getViewport().setXAxisBoundsManual(true);
    }
}