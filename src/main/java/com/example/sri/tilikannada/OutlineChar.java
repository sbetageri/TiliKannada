package com.example.sri.tilikannada;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by sri on 5/5/15.
 */
public class OutlineChar {
    // Gets the outline of the character from the Image;
    // The outline of the character is stored in an ArrayList
    ArrayList<Pixel> points;

    OutlineChar() {
        points = new ArrayList<Pixel>();
    }

    OutlineChar(TextView tT, StringBuilder sb, CharImage img, PixelCharacter boundary) {
//        boundary.showPixels();
        points = new ArrayList<Pixel>();
        Pixel start = boundary.start;
        Pixel end = boundary.end;
        if(end.i > img.width)
            end.i = img.width -1;
        if(end.j > img.height)
            end.j = img.height -1;
        StringBuilder st = new StringBuilder("loop");
        for(int i = start.i; i < end.i; i++) {
            tT.setText("i Loop\n");
            for (int j = start.j; j < end.j; j++) {
                st.replace(0, 1, new Integer(j).toString());
                tT.setText(st.toString());
                if (img.isBoundary(i, j)) {
                    points.add(new Pixel(i, j));
                }
            }
        }
//        System.out.println("BLACK PIXEL COUNT Again: " + black);
//        System.out.println("COUNT : " + count);
    }

    ArrayList<Pixel> getPoints() {
        return points;
    }
}
