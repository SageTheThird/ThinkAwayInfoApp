package com.homie.psychq.utils;

import com.homie.psychq.R;

import org.junit.Test;

import static org.junit.Assert.*;

public class ColorsHelperTest {

    @Test
    public void getCategoryColor() throws Exception {
        /*Input 1 is a default case of the method:when no valid input*/

        String input1 = "Enter";
        String input2 = "Grammar";
        int expected1 = 4000;
        int expected2 = 135;
        int output1 ;
        int output2 ;

        ColorsHelper colorsHelper = new ColorsHelper();

        output1 = colorsHelper.getCategoryColorMock(input1);
        output2 = colorsHelper.getCategoryColorMock(input2);

        assertEquals(expected1,output1);
        assertEquals(expected2,output2);
    }

    @Test
    public void getRandomColor() throws Exception {
    }
}