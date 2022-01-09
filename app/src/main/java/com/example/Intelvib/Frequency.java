package com.example.Intelvib;

import static com.example.Intelvib.DBRead.times;
import static com.example.Intelvib.Reading.axis;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.GridLabelRenderer;
import com.jjoe64.graphview.LegendRenderer;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.Math;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

public class Frequency extends AppCompatActivity {

    public static ArrayList<Entry> freqGraph = new ArrayList<Entry>();
    private  static GraphView graphView;
    private static Button button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_frequency);
        graphView = findViewById(R.id.gView);
        button = findViewById(R.id.button);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                List<Float> time = calc_freq(times);
                List<Float> voltage = new ArrayList<>();
                for(Entry values : DBRead.xValues){
                    voltage.add(values.getY());
                }
                Float[] mag =calc_dft(voltage);
                pointCreation(time,mag);
            }
        });

    }

    private void pointCreation(List<Float> time, Float[] mag) {
        Point point = new Point();
        if(time.size() == mag.length){
            LineGraphSeries<DataPoint> series = new LineGraphSeries<>();
            for(int i = 0; i< mag.length ;i++){
                series.appendData(new DataPoint(time.get(i),mag[i]),true,mag.length);
            }
            showChart(series);
        }
        else
            Toast.makeText(this.getApplicationContext(), "Anomaly Data", Toast.LENGTH_SHORT).show();
    }

    public static List<Float> calc_freq(List<Float> time){
        int numpts = time.size()/2;
        float sample_rate = time.get(1) - time.get(0);
        float nyquist_rate = 2 * sample_rate;
        int max_freq = (int) (1000 / nyquist_rate);
        LineSpace lineSpace = new LineSpace(0,max_freq,numpts+1);
        return lineSpace.generateSteps();
    }

    public static Float[] calc_dft(List<Float> sig){

        int sig_len = sig.size();
        int dft_len = sig_len/2+1;

        Float[] re = new Float[dft_len];
        Float[] im = new Float[dft_len];
        Float[] mag = new Float[dft_len];
        Arrays.fill(re,0.0f);
        Arrays.fill(im,0.0f);
        Arrays.fill(mag,0.0f);
        re[0] = im[0] = mag[0] = 0.0f;
        IntStream.range(1,dft_len).forEach(k ->{
            final int pos = k;
            IntStream.range(1,sig_len).forEach(i ->{
                re[pos] += (float)(sig.get(i) *Math.cos(2 * Math.PI *pos*i / sig_len));
                im[pos] -= (float)(sig.get(i) *Math.sin(2 * Math.PI *pos*i / sig_len));
            });
            mag[k] = (float)(Math.sqrt(Math.pow(re[k],2)+Math.pow(im[k],2)));
        });
        return mag;
    }

    public static void showChart(LineGraphSeries<DataPoint> series) {

        switch(axis){
            case "X-axis":
                series.setColor(Color.RED);
                graphView.setTitle("X-axis Graph");
                graphView.setTitleTextSize(500);
                graphView.setTitleTextSize(Color.BLUE);
                break;
            case "Y-axis":
                series.setColor(Color.BLUE);
                graphView.setTitle("Y-axis Graph");
                graphView.setTitleTextSize(500);
                graphView.setTitleTextSize(Color.BLUE);
                break;
            case "Z-axis":
                series.setColor(Color.GREEN);
                graphView.setTitle("Y-axis Graph");
                graphView.setTitleTextSize(500);
                graphView.setTitleTextSize(Color.BLUE);
                break;
        }
        graphView.addSeries(series);
        graphView.getViewport().setMinX(series.getLowestValueX());
        graphView.getViewport().setMaxX(series.getHighestValueX()+10.0);
        graphView.getViewport().setMinY(series.getLowestValueY());
        graphView.getViewport().setMaxY(series.getHighestValueY());
        graphView.getViewport (). setScalable ( true ); // activate horizontal scrolling
        graphView.getViewport (). setScrollable ( true ); // activate horizontal and vertical zooming and scrolling
        graphView.getViewport (). setScalableY ( true ); // activate vertical scrolling
        graphView.getViewport (). setScrollableY ( true);
        graphView.getViewport().setYAxisBoundsManual(true);
        graphView.getViewport().setXAxisBoundsManual(true);

    }
}