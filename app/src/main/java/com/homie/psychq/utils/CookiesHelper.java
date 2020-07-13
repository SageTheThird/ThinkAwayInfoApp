package com.homie.psychq.utils;

import android.app.Activity;
import android.content.Context;

import org.aviran.cookiebar2.CookieBar;
import org.aviran.cookiebar2.OnActionClickListener;

/*Custom class for cookies (showing info as toast)*/

public class CookiesHelper {

    private Activity activity;

    public CookiesHelper(Context context) {

        this.activity = (Activity) context;
    }

    //Activity activity = (Activity) context
    public void showCookie(String title, String message, String action, OnActionClickListener actionClickListener ){
        CookieBar.build(activity)
                .setTitle(title)
                .setMessage(message)
                .setDuration(5000)
                .setAction(action, actionClickListener)
                .show();
    }
}
