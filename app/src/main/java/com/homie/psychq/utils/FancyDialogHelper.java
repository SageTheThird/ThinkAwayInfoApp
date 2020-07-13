package com.homie.psychq.utils;

import android.app.Activity;
import android.content.Context;
import android.widget.Toast;

import com.homie.psychq.R;
import com.shashank.sony.fancygifdialoglib.FancyGifDialog;
import com.shashank.sony.fancygifdialoglib.FancyGifDialogListener;

import java.net.ContentHandler;




/*A dialog which lets you show GIf as background*/

public class FancyDialogHelper {

    private Context context;

    public FancyDialogHelper(Context context) {
        this.context= context;
    }

    public void showFancyDialog(String title, String message, String positiveBtnText, String negativeBtnText
                                ,FancyGifDialogListener positiveBtnListener, FancyGifDialogListener negativeBtnListener){


        new FancyGifDialog.Builder((Activity) context)
                .setTitle(title)
                .setMessage(message)
                .setNegativeBtnText(negativeBtnText)
//                .setPositiveBtnBackground("#000")
                .setPositiveBtnText(positiveBtnText)
                .setNegativeBtnBackground("#ebb134")
                .setPositiveBtnBackground("#242525")
                .setGifResource(R.drawable.fireplace)   //Pass your Gif here
                .isCancellable(true)
                .OnPositiveClicked(positiveBtnListener)
                .OnNegativeClicked(negativeBtnListener)
                .build();

    }
}
