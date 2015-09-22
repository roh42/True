package roh.funfacts;

import android.graphics.Color;

import java.util.Random;

/**
 * Created by Roh on 05/04/2015.
 */
public class ColorWheel {

    public String [] mColors = {
            "#212121",
            "#424242",
            "#000000"
    };

    public int getColor(){

        String color = "";
        Random randomGenerator = new Random();
        int randomNumber = randomGenerator.nextInt(mColors.length);
        color = mColors[randomNumber];
        int colorAsInt = Color.parseColor(color);


        return colorAsInt;
    }
}