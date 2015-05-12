package com.example.sri.tilikannada;
import java.util.HashMap;

/**
 * Created by sri on 30/4/15.
 */
public class GridFeatures {
    HashMap<Double, Integer> val;
    Double total;
    /*
        Double here maps to angle
        Integer maps to number of occurences
     */

    GridFeatures(HashMap<Double, Integer> train) {
        val = train;
    }

    GridFeatures() {
        val = new HashMap<Double, Integer>();
        total = 0.0;
    }

    void insert(Double a, Integer b) {
        val.put(a, b);
        total += b.doubleValue();
    }

    Double getProb(Double angle) {
        Double prob = 0.0;
        if(val.containsKey(angle)) {
//            System.out.println(val.get(angle).doubleValue());
//            System.out.println(val.size());
            prob = val.get(angle).doubleValue() / total;
        }
        return prob;
    }

    boolean isPresent(Double angle) {
        if(val.containsKey(angle))
            return true;
        return false;
    }

    void display() {
        for(Double angle : val.keySet()) {
            System.out.println("Angle : " + angle);
            System.out.println("Occurance : " + val.get(angle));
            System.out.println("Total : " + total);
        }
    }
}
