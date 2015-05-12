package com.example.sri.tilikannada;
import android.content.Context;
import android.graphics.Bitmap;
import android.widget.TextView;

import java.io.*;
import java.util.ArrayList;

/**
 * Created by sri on 5/5/15.
 */
public class Control {
    // Class that is the central hub of all data flows
    // Needs a wrapper in the middle before it can be used with droid

    CharImage img;
    ArrayList<PixelCharacter> pChar;
    String recogStr;
    Context mContext;

    /*
    public static void main(String[] args) throws IOException {
        File dir = new File("/home/sri/p/newFont/kedage font");
        File oDir = new File("/home/sri/p/newFont/kCSV");
        String[] files = dir.list();
        for (String s : files) {
            StringBuilder sb = new StringBuilder(dir.getAbsolutePath());
            StringBuilder outSb = new StringBuilder(oDir.getAbsolutePath());
            sb.append("/");
            sb.append(s);
            outSb.append("/");
            outSb.append(s);
            outSb.append("/");
            // sb has the specific directory within the directory
            File inDir = new File(sb.toString());
            // inDir has the inner directory
            File outDir = new File(outSb.toString());
            if(!outDir.exists()) {
                outDir.mkdir();
            }
            File[] inFile = inDir.listFiles();
            for (File iF : inFile) {
                // iF points to each bmp file
//        for(int j = 0; j < 6; j++) {
//            StringBuilder src = new StringBuilder("/home/sri/p/font/lohit/");
                StringBuilder sTemp = new StringBuilder(iF.getName());
                sTemp.delete(sTemp.length() - 4, sTemp.length() - 1);
                StringBuilder dsc = new StringBuilder(outSb);
                dsc.append(sTemp);
                dsc.append(".csv");

//            src.append(folder);
//            src.append("/");
//            src.append(folder);
//            if(j != 0)
//                src.append(j);
//            src.append(".bmp");
//            dsc.append(folder);
//            dsc.append("/");
//            dsc.append(folder);
//            dsc.append(j);
//            dsc.append(".csv");
//            System.out.println(dsc.toString());
                CharImage obj = new CharImage(iF.getAbsolutePath());
                Control trial = new Control(obj);
                trial.extract();
                for (int i = 0; i < trial.pChar.size(); i++) {
                    OutlineChar oChar = new OutlineChar(obj, trial.pChar.get(i));
//                    System.out.println("NUMBER OF PIXELS : " + oChar.points.size());
//                    oChar has the outline of the character in the form of an ArrayList<Pixel>
                    FeatureExtraction feat = new FeatureExtraction(oChar.getPoints(), trial.pChar.get(i));
//                    feat.display();
                    String features = feat.getArray();
                    System.out.println(dsc.toString());
                    File outputCSV = new File(dsc.toString());
                    if(!outputCSV.exists())
                        outputCSV.createNewFile();
                    FileWriter fw = new FileWriter(new File(dsc.toString()));
                    BufferedWriter bw = new BufferedWriter(fw);
                    bw.write(features);
                    bw.close();
                }
            }
        }
    }
    */

    String getRecogStr() {
        return recogStr;
    }

    /*
        TODO
        Need to identify which flag belongs to which letter
     */

    void recognise(TextView tT, StringBuilder sb) throws IOException {
        extract();
        StringBuilder output = new StringBuilder();
        for(int i = 0; i < pChar.size(); i++) {
            tT.setText("before outline");
            OutlineChar oChar = new OutlineChar(tT, sb, img, pChar.get(i));
            tT.setText("after outline");
            FeatureExtraction feat = new FeatureExtraction(oChar.getPoints(), pChar.get(i));
            Classifier kChar = new Classifier(mContext, feat.getFeat());
            output.append(kChar.getOutChar());
        }
        if(pChar.size() == 0)
            output.append("GET Better image");
        recogStr = output.toString();
    }

    Control() {
        CharImage obj = new CharImage();
    }

    Control(Context mActivity, CharImage image, StringBuilder a) throws IOException {
        // Given image, convert to black and white
        mContext = mActivity;
        img = image;
        pChar = new ArrayList<PixelCharacter>();
        for(int i = 0; i < img.width; i++) {
            for (int j = 0; j < img.height; j++) {
                if (image.isBelowThreshold(i, j)) {
                    image.setColor(i, j, CharImage.black);
//                    a.append("forming outline");
                }
                else
                    image.setColor(i, j, CharImage.white);
            }
        }
    }

    void extract() {
        // Forms the boundary of the characters
        // Stores the boundaries in the
        for(int i = 0; i < img.width; i++) {
            Pixel start = new Pixel();
            Pixel end = new Pixel();
            if(!findX(i, start, end)) {
                // returns false when no black pixels have been found
                continue;
            }
            findY(0, start, end);
            if(i < end.i) {
                i = end.i + 1;
            }
            pChar.add(new PixelCharacter(start, end));
        }
    }

    void findY(int stY, Pixel start, Pixel end) {
        // scans each row
        boolean flag = false;
        for(int j = stY; j < img.height; j++) {
            int white = 0;
            for(int i = 0; i < img.width; i++) {
                if(img.isBlack(i, j)) {
                    if (!flag) {
                        flag = true;
                        start.j = j;
                    }
                } else {
                    white++;
                }
            }
            if(white == img.width && flag) {
                end.j = j;
                break;
            }
            if(j == img.height - 1)
                end.j = j;
        }
    }

    boolean findX(int stX, Pixel start, Pixel end) {
        //  Checks each column
        boolean flag = false;
        for(int i = stX; i < img.width; i++) {
            int white = 0;
            for(int j = 0; j < img.height; j++) {
                if(img.isBlack(i, j)) {
                    if(!flag) {
                        // flag is true only when a black pixel has already been encountered
                        flag = true;
                        start.i = i;
                    }
                } else {
                    white++;
                }
            }
            if(white == img.height && flag) {
                // if flag is true; it implies that a number of black pixels have already been found
                end.i = i;
                break;
            }
            if(i == img.width - 1)
                end.i = img.width - 1;
        }
        return flag;
    }
}
