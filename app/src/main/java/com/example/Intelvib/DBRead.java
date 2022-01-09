package com.example.Intelvib;

import static com.example.Intelvib.Reading.lineChart;

import android.app.ProgressDialog;
import android.content.Context;
import android.widget.Toast;


import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.github.mikephil.charting.data.Entry;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class DBRead{
    ProgressDialog loading;
    private String url;
    private RequestQueue queue;
    public static List<Float> times = new ArrayList<>();
    public static ArrayList<Entry> xValues = new ArrayList<Entry>();
    private static Context context;
    public static String res;

    public DBRead(String url, RequestQueue queue,Context context) {
        this.url = url;
        this.queue = queue;
        this.context = context;
    }

    public DBRead() {

    }


    public void run(){
        StringRequest stringRequest = new StringRequest(Request.Method.GET,
                url,new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                res = response;
                Toast.makeText(context.getApplicationContext(), "DB Read Complete",Toast.LENGTH_SHORT).show();

            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });


        int socketTimeOut = 50000;
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeOut, 0, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);

        stringRequest.setRetryPolicy(policy);

        queue.add(stringRequest);
    }

    public static void parseItems(String jsonResponse) {
        float time,xAxis;int flag = 0;
        times = new ArrayList<>();
        xValues = new ArrayList<>();
        try {
            JSONObject jobj = new JSONObject(jsonResponse);
            JSONArray jarray = jobj.getJSONArray("items");

            switch(Reading.axis) {
                case "X-axis":
                    for (int i = 0; i < jarray.length(); i++) {
                        JSONObject jo = jarray.getJSONObject(i);
                        time = Float.parseFloat(jo.getString("time"));
                        times.add(time);
                        xAxis = Float.parseFloat(jo.getString("xAxis"));
                        Point point_x = new Point(time, xAxis);
                        xValues.add(new Entry(point_x.getxValue(), point_x.getyValue()));
                    }
                    break;
                case "Y-axis":
                    for (int i = 0; i < jarray.length(); i++) {
                        JSONObject jo = jarray.getJSONObject(i);
                        time = Float.parseFloat(jo.getString("time"));
                        times.add(time);
                        xAxis = Float.parseFloat(jo.getString("yAxis"));
                        Point point_x = new Point(time, xAxis);
                        xValues.add(new Entry(point_x.getxValue(), point_x.getyValue()));
                    }
                    break;
                case "Z-axis":
                    for (int i = 0; i < jarray.length(); i++) {
                        JSONObject jo = jarray.getJSONObject(i);
                        time = Float.parseFloat(jo.getString("time"));
                        times.add(time);
                        xAxis = Float.parseFloat(jo.getString("zAxis"));
                        Point point_x = new Point(time, xAxis);
                        xValues.add(new Entry(point_x.getxValue(), point_x.getyValue()));
                    }
            }
            Toast.makeText(context,"Data Read",Toast.LENGTH_SHORT).show();
            Reading.showChart_x(xValues);
        } catch (JSONException e) {
            e.printStackTrace(); }
    }
}
