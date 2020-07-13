package com.homie.psychq.utils;

import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;


/*
* Takes in timestamp, can: return how many days,hours ago
* */

public class TimeStampHelper {

    private static final String TAG = "TimeStampHelper";



    public static Date getPastTime(String timestamp) {

        /*Returns Past Time by Parsing TimeStamp*/

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");

        Date past = null;

        String dateString = timestamp;
        if (dateString != null) {

            try {

                past = format.parse(timestamp);

            } catch (ParseException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }


        }

        return past;
    }

    public static String getTimeDifference(String timestamp){

        //pass Api timeStamp as a parameter and it returns it in timeAgo format

        long seconds = 0;
        long minutes = 0;
        long hours = 0;
        long days =0;

        Date past = TimeStampHelper.getPastTime(timestamp);

            Date now = new Date();
            if(past != null){


                seconds= TimeUnit.MILLISECONDS.toSeconds(now.getTime() - past.getTime());
                minutes=TimeUnit.MILLISECONDS.toMinutes(now.getTime() - past.getTime());
                hours=TimeUnit.MILLISECONDS.toHours(now.getTime() - past.getTime());
                days=TimeUnit.MILLISECONDS.toDays(now.getTime() - past.getTime());

            }

        if(seconds<60)
        {
            return seconds+" seconds ago";
        }
        else if(minutes<60)
        {
            return minutes+" minutes ago";
        }
        else if(hours<24)
        {
            return hours+" hours ago";
        }
        else
        {
            return days+" days ago";
        }
    }

    public static int getDaysAgoInt(String timestamp){

        //returns first character as a int
        Date past = TimeStampHelper.getPastTime(timestamp);
        Date now = new Date();
        long days=TimeUnit.MILLISECONDS.toDays(now.getTime() - past.getTime());

        return (int) days;
    }
}
