package roh.funfacts;

import android.graphics.Color;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by Roh on 27/04/2015.
 */
public class FaceWheel {


    public ArrayList<Integer> rdmFaces = new ArrayList();
    int randomNumber;
    Random randomGenerator = new Random();

    public int[] mFaces = {
            R.drawable.rostinhos01,
            R.drawable.rostinhos02,
            R.drawable.rostinhos03,
            R.drawable.rostinhos04,
            R.drawable.rostinhos05,
            R.drawable.rostinhos06
    };

    public int getFace(){

        int face;
        generateRandomNumber();
        face = mFaces[randomNumber];

        return face;
    }

    public int generateRandomNumber(){
        randomNumber = randomGenerator.nextInt(mFaces.length);
        if (rdmFaces.contains(randomNumber)){
            generateRandomNumber();
        } else {
            storeRdmNumber(randomNumber);
        }

        if (rdmFaces.size() == mFaces.length){
            rdmFaces.removeAll(rdmFaces);
        }
        return randomNumber;
    }

    public void storeRdmNumber (int randomNumber) {
        rdmFaces.add(randomNumber);

    }


}
