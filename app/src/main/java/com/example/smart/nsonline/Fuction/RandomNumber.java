package com.example.smart.nsonline.Fuction;

import java.util.Random;

public class RandomNumber {

    public RandomNumber(){
        super();
    }

    public int getNumber(){
        int random;

        Random ran = new Random();
        random = ran.nextInt(100) + 1;

        return random;
    }
}
