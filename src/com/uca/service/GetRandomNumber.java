package com.uca.service;

import java.util.Random;

public class GetRandomNumber
{
    public static String stringRandomNumber(int nbMax)
    {
        Random random = new Random();
        int randomNumber = random.nextInt(nbMax);
        String randomNumberToString = Integer.toString(randomNumber);

        return randomNumberToString;
    }
}
