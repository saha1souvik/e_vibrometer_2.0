package com.example.Intelvib;

import android.util.Log;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class LineSpace {
    private float start,stop,step;
    List<Double> freq;
    public LineSpace(int start, int stop, int step) {
        this.start = start;
        this.stop = stop;
        this.step = step;
        freq = new ArrayList<>();
    }

    public List<Float> generateSteps(){
        double stepSize = (stop-start)/step;
        List<Float> steps = new ArrayList<>();
        while (step>0){
            steps.add(start);
            start += stepSize;
            step--;
        }
        return steps;
    }

    public static List<Float> divide(Float[] mag,int numpts){
        List<Float> res = new ArrayList<>();
        Arrays.stream(mag).forEach(i -> {
         res.add(i%(float)numpts);
        });
        return res;
    }

    public static List<Float> dividePower(List<Float> mag){
        List<Float> res = mag.stream()
                .map(i -> i*i).collect(Collectors.toList());
        Float[] arr = res.stream().toArray(Float[] ::new);
        return divide(arr,100);
    }

    public static List<Float> multiply(List<Float> mag){
        return mag.stream().map(i -> (float)(Math.log10((double)i))).map(i-> i*10).collect(Collectors.toList());
    }
}
