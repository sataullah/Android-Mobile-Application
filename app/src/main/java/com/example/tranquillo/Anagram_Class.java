package com.example.tranquillo;

import java.util.Random;

public class Anagram_Class {

    public static final Random RANDOM = new Random();
    public static final String[] Words = {"Netherlands","Reykjavik","Lithuania","Seychelles","Madagascar","Romania","Philippines"};

    public static String randomPlace(){
        return Words[RANDOM.nextInt(Words.length)];
    }

    public static String shufflePlace (String word){
        if(word!=null && !"".equals(word)){
            char a[] = word.toCharArray();

            for (int i = 0; i<a.length; i++){
                int j = RANDOM.nextInt(a.length);
                char tmp = a[i];
                a[i] = a[j];
                a[j] = tmp;
            }
            return new String(a);
        }
        return word;
    }
}
