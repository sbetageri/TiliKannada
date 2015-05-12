package com.example.sri.tilikannada;

/**
 * Created by sri on 31/3/15.
 */
public class PixelCharacter {
    // Each character, in an image, is cropped and the
    // top left and bottom right pixel co-ordinate is stored in this class

    Pixel start;
    Pixel end;

    PixelCharacter() {
        start = new Pixel();
        end  = new Pixel();
    }

    PixelCharacter(Pixel a, Pixel b) {
        start = new Pixel(a);
        end = new Pixel(b);
    }

    void showPixels() {
        // Debugging method
        start.display();
        end.display();
    }

    int getWidth() {
        return end.i - start.i;
    }

    int getHeight() {
        return end.j - start.j;
    }

}