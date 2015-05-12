package com.example.sri.tilikannada;

import android.content.Context;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;

/**
 * Created by sri on 30/4/15.
 */
public class GetTrained {
    HashMap<Integer, GridFeatures> data;
    // Integer holds the grid number
    // GridFeatures holds the features of t
    /*
        Use the airtel dongle itself
     */

    GetTrained() {
    }

    GetTrained(Context mCont, String trainedFile) throws IOException {
        getTrainedData(mCont,trainedFile);
    }

    Double getProbability(HashMap<Integer, Grid> feat) {
        Double prob = 0.0;
        for(Integer gridNum : feat.keySet()) {
            if(data.containsKey(gridNum)) {
                GridFeatures grid = data.get(gridNum);
                if(grid.isPresent(feat.get(gridNum).angle))
                    prob += 1.0;
            }
        }
        prob = prob / new Double(feat.size());
        return prob;
    }

    /*
    Double getProbability(Integer grid, Double angle) {
        if(data.containsKey(grid)) {
            GridFeatures obj = data.get(grid);
            return obj.getProb(angle);
        } else {
            return 0.0;
        }
    }
    */

    void getTrainedData(Context mCont, String trainedFile) throws IOException {
        data = new HashMap<Integer, GridFeatures>();
        BufferedReader br = new BufferedReader(new InputStreamReader(mCont.getAssets().open(trainedFile)));
        String ip = br.readLine();
        String[] val;
        do {
            val = ip.split(",");
            Integer gridNumber = new Integer(val[0]);
            GridFeatures obj = new GridFeatures();
//            System.out.println("GRID : " + gridNumber);
            for(int i = 1; i < val.length; i += 2) {
                Double angle = new Double(val[i]);
//                System.out.println("Angle : " + angle);
                Integer app = new Integer(val[i + 1]);
//                System.out.println("Occur : " + app);
                obj.insert(angle, app);
            }
            data.put(gridNumber, obj);
            ip = br.readLine();
        }while(ip != null);
    }

    boolean isPresent(Integer grid, Double angle) {
        if(data.containsKey(grid)){
            return data.get(grid).isPresent(angle);
        }
        return false;
    }

    void display() {
        GridFeatures obj;
        for(Integer key : data.keySet()) {
            System.out.println("GRIDNUMBER : " + key);
            obj = data.get(key);
            obj.display();
        }
    }
}
