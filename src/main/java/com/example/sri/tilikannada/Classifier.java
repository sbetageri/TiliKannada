package com.example.sri.tilikannada;

import android.content.Context;
import android.content.res.AssetManager;
import android.content.res.Resources;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

/**
 * Created by sri on 30/4/15.
 */
public class Classifier {
    /*
        Input : testFeat <GridNum(Integer), Angle(Double)>
        Output : index of the recognised character

        How ::
            testFeat is passed as input
            The classifier cycles over all the trained files and finds the probability

     */
    HashMap<Integer, Grid> testFeat;
    int flag;
    String outChar;

    Classifier() {
        flag = 0;
    }

    Classifier(Context mContext, HashMap<Integer, Grid> testData) throws IOException {
        this();
        // Gets the testData from Control
        testFeat = testData;
        AssetManager am = mContext.getAssets();
//        String trainedFile = new String("/home/sri/p/foobar");
//        File dir = new File(trainedFile);
        String[] tFiles = am.list("");
        Double max = 0.0;
        int count = 0;
        for(String s: tFiles) {
            StringBuilder sb = new StringBuilder(s);
            count++;
            Double temp = 0.0;
            GetTrained trainedData = new GetTrained(mContext, sb.toString());
            temp = trainedData.getProbability(testData);
            if(temp > max) {
                max = temp;
                flag = count;
            }
//           9880737893
        }
        StringBuilder recogChar = new StringBuilder(tFiles[flag - 1]);
        recogChar.delete(recogChar.length() - 4, recogChar.length() - 1);
        outChar = recogChar.toString();
    }

    String getOutChar() {
        return outChar;
    }

    int getFlag() {
        return flag;
    }
}
