package com.example.sri.tilikannada;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;

import java.io.File;
import java.io.IOException;

/**
 * Created by sri on 5/5/15.
 */
public class CharImage {
    //    BufferedImage img;
    Bitmap bImage;
    int height;
    int width;

    static int white;
    static int black;
    static int threshold;

    static {
        white = Color.WHITE;
//        white = Color.white.getRGB();
//        black = Color.black.getRGB();
        black = Color.BLACK;
        threshold = Color.rgb(127, 127, 127);
    }

    void writeToDisk(String path, PixelCharacter point) throws IOException {
        Bitmap bTemp = Bitmap.createBitmap(bImage, point.start.i, point.start.j, point.getWidth(), point.getHeight());
    }

    CharImage() {
//        img = null;
        bImage = null;
        height = 0;
        width = 0;
    }

    CharImage(int width, int height) {
        bImage = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
//        img = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        this.height = height;
        this.width = width;
    }

    CharImage(String source) throws IOException {
    }

    CharImage(Bitmap obj) {
        bImage = obj.copy(Bitmap.Config.ARGB_8888, true);
        height = obj.getHeight();
        width = obj.getWidth();
    }



    boolean isBelowThreshold(int i, int j) {
        if(i < 0)
            i = 0;
        if(j < 0)
            j = 0;
        if(i >= width)
            i = width - 1;
        if(j >= height)
            j = height - 1;
        if(getColor(i, j) < CharImage.threshold)
            return true;
        return false;
    }

    int getColor(int x, int y) {
        if(x < 0)
            x = 0;
        if(y < 0)
            y = 0;
        if(x >= width)
            x = width - 1;
        if(y >= height)
            y = height - 1;
        return bImage.getPixel(x, y);
//        return img.getRGB(x, y);
    }

    void setColor(int x, int y, int rgb) {
        if(x < 0)
            x = 0;
        if(y < 0)
            y = 0;
        if(x >= width)
            x = width - 1;
        if(y >= height)
            y = height - 1;
        bImage.setPixel(x, y, rgb);
//        img.setRGB(x, y, rgb);
    }

    /*
    void writeDisk(String path) throws IOException {
        ImageIO.write(img, "bmp", new File(path));
    }
    */

    boolean isWithin(int i, int j) {
        // returns true if i and j are within the boundary of the ******  IMAGE  *********
        if(i >= 0 && j <= height)
            return true;
        return false;
    }

    boolean isBoundary(int i, int j) {
        if(i < 0)
            return false;
        if(j < 0)
            return false;
        if(i > width)
            return false;
        if(j > height)
            return false;
        if(i == 0)
            return false;
        if(j == 0)
            return false;
        if(i == width - 1)
            return false;
        if(j == height - 1)
            return false;
        int[] box = new int[9];
//        img.getRGB( i -1, j - 1, 3, 3, box, 0, 3);
        bImage.getPixels(box, 0, width + 100, i - 1, j - 1, 3, 3);
        if(box[4] == Color.BLACK) {
//            System.out.println("BOX VALUES");
//            System.out.println(box[4] == Color.black.getRGB());
//            System.out.println(new Color(box[1]).toString());
//            System.out.println(new Color(box[3]).toString());
//            System.out.println(new Color(box[5]).toString());
//            System.out.println(new Color(box[7]).toString());
            if (box[1] == Color.WHITE || box[3] == Color.WHITE ||
                    box[5] == Color.WHITE || box[7] == Color.WHITE) {
                return true;
            }
        }
        return false;
        /*
        if(!isBlack(i, j))
            return false;
        if(isWithin(i, j - 1))
            if(isWhite(i, j - 1))
                return true;
        if(isWithin(i - 1, j))
            if(isWhite(i - 1, j))
                return true;
        if(isWithin(i, j + 1))
            if(isWhite(i, j + 1))
                return true;
        if(isWithin(i + 1, j))
            if(isWhite(i + 1, j))
                return true;
                */
    }

    boolean isBlack(int i, int j) {
        if(i < 0)
            i = 0;
        if(j < 0)
            j = 0;
        if(i >= width)
            i = width - 1;
        if(j >= height)
            j = height - 1;
        if(bImage.getPixel(i, j) == Color.BLACK)
            return true;
        return false;
    }

    boolean isWhite(int pixColor){
        if(pixColor == Color.WHITE)
            return true;
        return false;
    }


    boolean isWhite(int i, int j) {
        if(bImage.getPixel(i, j) == CharImage.white)
            return true;
        return false;
    }
}
