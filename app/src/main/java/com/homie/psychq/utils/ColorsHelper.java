package com.homie.psychq.utils;

import androidx.core.content.ContextCompat;

import com.homie.psychq.BaseApplication;
import com.homie.psychq.R;



import java.util.Random;



/*Responsible for assigning a color to each category*/

public class ColorsHelper {


    //f78749
    //4abcd9
    //496075
    //c48d81
    //8c76a5
    //ec7e74
    //e1b975
    //9b8271
    //b7556b

    public int getCategoryColorMock(String category){




        if (category.equals("Quotes")) {
            return 120;
        } else if ("PsychologicalTricks".equals(category)) {
            return 121;
        } else if ("PsychologicalDisorders".equals(category)) {
            return 122;
        } else if ("Psychology".equals(category)) {
            return 123;
        } else if ("Phobias".equals(category)) {
            return 124;
        } else if ("Philosophy".equals(category)) {
            return 125;
        } else if ("Pessimism".equals(category)) {
            return 126;
        } else if ("PersonalityTypes".equals(category)) {
            return 127;
        } else if ("Movies".equals(category)) {
            return 128;
        } else if ("MoviesFacts".equals(category)) {
            return 129;
        } else if ("Literature".equals(category)) {
            return 130;
        } else if ("LifePointers".equals(category)) {
            return 131;
        } else if ("InterestingWords".equals(category)) {
            return 132;
        } else if ("Idioms".equals(category)) {
            return 133;
        } else if ("HumanBehavior".equals(category)) {
            return 134;
        } else if ("Grammar".equals(category)) {
            return 135;
        } else if ("Facts".equals(category)) {
            return 136;
        } else if ("Emotions".equals(category)) {
            return 137;
        } else if ("CriticalThinking".equals(category)) {
            return 138;
        } else if ("ColorPsychology".equals(category)) {
            return 139;
        } else if ("BooksReferences".equals(category)) {
            return 140;
        }
        return 4000;
    }

    public static int getCategoryColor(String category){


        if (BaseApplication.get().getString(R.string.Quotes).equals(category)) {
            return ContextCompat.getColor(BaseApplication.get(), R.color.Quotes);
        } else if (BaseApplication.get().getString(R.string.PsychologicalTricks).equals(category)) {
            return ContextCompat.getColor(BaseApplication.get(), R.color.PsychologicalTricks);
        } else if (BaseApplication.get().getString(R.string.PsychologicalDisorders).equals(category)) {
            return ContextCompat.getColor(BaseApplication.get(), R.color.PsychologicalDisorders);
        } else if (BaseApplication.get().getString(R.string.Psychology).equals(category)) {
            return ContextCompat.getColor(BaseApplication.get(), R.color.Psychology);
        } else if (BaseApplication.get().getString(R.string.Phobias).equals(category)) {
            return ContextCompat.getColor(BaseApplication.get(), R.color.Phobias);
        } else if (BaseApplication.get().getString(R.string.Philosophy).equals(category)) {
            return ContextCompat.getColor(BaseApplication.get(), R.color.Philosophy);
        } else if (BaseApplication.get().getString(R.string.Pessimism).equals(category)) {
            return ContextCompat.getColor(BaseApplication.get(), R.color.Pessimism);
        } else if (BaseApplication.get().getString(R.string.PersonalityTypes).equals(category)) {
            return ContextCompat.getColor(BaseApplication.get(), R.color.PersonalityTypes);
        } else if (BaseApplication.get().getString(R.string.Movies).equals(category)) {
            return ContextCompat.getColor(BaseApplication.get(), R.color.Movies);
        } else if (BaseApplication.get().getString(R.string.MoviesFacts).equals(category)) {
            return ContextCompat.getColor(BaseApplication.get(), R.color.MoviesFacts);
        } else if (BaseApplication.get().getString(R.string.Literature).equals(category)) {
            return ContextCompat.getColor(BaseApplication.get(), R.color.Literature);
        } else if (BaseApplication.get().getString(R.string.LifePointers).equals(category)) {
            return ContextCompat.getColor(BaseApplication.get(), R.color.LifePointers);
        } else if (BaseApplication.get().getString(R.string.InterestingWords).equals(category)) {
            return ContextCompat.getColor(BaseApplication.get(), R.color.InterestingWords);
        } else if (BaseApplication.get().getString(R.string.Idioms).equals(category)) {
            return ContextCompat.getColor(BaseApplication.get(), R.color.Idioms);
        } else if (BaseApplication.get().getString(R.string.HumanBehavior).equals(category)) {
            return ContextCompat.getColor(BaseApplication.get(), R.color.HumanBehavior);
        } else if (BaseApplication.get().getString(R.string.Grammar).equals(category)) {
            return ContextCompat.getColor(BaseApplication.get(), R.color.Grammar);
        } else if (BaseApplication.get().getString(R.string.Facts).equals(category)) {
            return ContextCompat.getColor(BaseApplication.get(), R.color.Facts);
        } else if (BaseApplication.get().getString(R.string.Emotions).equals(category)) {
            return ContextCompat.getColor(BaseApplication.get(), R.color.Emotions);
        } else if (BaseApplication.get().getString(R.string.CriticalThinking).equals(category)) {
            return ContextCompat.getColor(BaseApplication.get(), R.color.CriticalThinking);
        } else if (BaseApplication.get().getString(R.string.ColorPsychology).equals(category)) {
            return ContextCompat.getColor(BaseApplication.get(), R.color.ColorPsychology);
        } else if (BaseApplication.get().getString(R.string.BooksReferences).equals(category)) {
            return ContextCompat.getColor(BaseApplication.get(), R.color.BooksReferences);
        }
        return ContextCompat.getColor(BaseApplication.get(), R.color.Default);
    }
    public static int getRandomColor(){
        Random rand = new Random();
        int newrand = rand.nextInt(9);

        if (newrand == 0) {
            return ContextCompat.getColor(BaseApplication.get(), R.color.c1);
        } else if (newrand == 1) {
            return ContextCompat.getColor(BaseApplication.get(), R.color.c2);
        } else if (newrand == 2) {
            return ContextCompat.getColor(BaseApplication.get(), R.color.c3);
        } else if (newrand == 3) {
            return ContextCompat.getColor(BaseApplication.get(), R.color.c4);
        } else if (newrand == 4) {
            return ContextCompat.getColor(BaseApplication.get(), R.color.c5);
        } else if (newrand == 5) {
            return ContextCompat.getColor(BaseApplication.get(), R.color.c6);
        } else if (newrand == 6) {
            return ContextCompat.getColor(BaseApplication.get(), R.color.c7);
        } else if (newrand == 7) {
            return ContextCompat.getColor(BaseApplication.get(), R.color.c8);
        } else if (newrand == 8) {
            return ContextCompat.getColor(BaseApplication.get(), R.color.c9);
        }else if (newrand == 9) {
            return ContextCompat.getColor(BaseApplication.get(), R.color.c10);
        } else if (newrand == 10) {
            return ContextCompat.getColor(BaseApplication.get(), R.color.c11);
        } else if (newrand == 11) {
            return ContextCompat.getColor(BaseApplication.get(), R.color.c12);
        } else if (newrand == 12) {
            return ContextCompat.getColor(BaseApplication.get(), R.color.c13);
        } else if (newrand == 13) {
            return ContextCompat.getColor(BaseApplication.get(), R.color.c14);
        } else if (newrand == 14) {
            return ContextCompat.getColor(BaseApplication.get(), R.color.c15);
        } else if (newrand == 15) {
            return ContextCompat.getColor(BaseApplication.get(), R.color.c16);
        } else if (newrand == 16) {
            return ContextCompat.getColor(BaseApplication.get(), R.color.c17);
        }else if (newrand == 17) {
            return ContextCompat.getColor(BaseApplication.get(), R.color.c18);
        } else if (newrand == 18) {
            return ContextCompat.getColor(BaseApplication.get(), R.color.c19);
        } else if (newrand == 19) {
            return ContextCompat.getColor(BaseApplication.get(), R.color.c20);
        } else if (newrand == 20) {
            return ContextCompat.getColor(BaseApplication.get(), R.color.c21);
        }
        return ContextCompat.getColor(BaseApplication.get(), R.color.c22);
    }
}
