package com.homie.psychq.main.settings;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.SwitchPreferenceCompat;
import com.homie.psychq.R;
import com.homie.psychq.auth.ui.AuthActivity;
import com.homie.psychq.subscription.SubscriptionActivity;
import com.homie.psychq.subscription.UnSubActivity;
import com.homie.psychq.utils.CookiesHelper;
import com.homie.psychq.utils.SharedPreferences;

public class SettingsFragment extends PreferenceFragmentCompat {

    private static final String TAG = "SettingsFragment";

    SharedPreferences sharedPreferences;
    CookiesHelper cookiesHelper;

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.settings_prefs, rootKey);

        SwitchPreferenceCompat messageNotification = findPreference("message_notification");
        SwitchPreferenceCompat contentNotification = findPreference("content_notification");
        SwitchPreferenceCompat productsNotification = findPreference("products_notification");
        Preference logOut = findPreference("sign_out");
        Preference unsub = findPreference("unsub");
        Preference subscribe = findPreference("subscribe");
        Preference reportFeedback = findPreference("feedback");
        Preference changeInfo = findPreference("change_info");

        sharedPreferences=new SharedPreferences(getActivity());
        cookiesHelper = new CookiesHelper(getActivity());

              //here we can make preferences visible in accordance with a feature or membership

        Log.d(TAG, "onCreatePreferences: "+messageNotification);






        if(logOut != null){

            logOut.setOnPreferenceClickListener(logOutClickListener);
        }

        if(unsub != null){

            unsub.setOnPreferenceClickListener(unSubClickListener);
        }

        if(subscribe != null){

            subscribe.setOnPreferenceClickListener(SubClickListener);
        }


        if(reportFeedback != null){

            reportFeedback.setOnPreferenceClickListener(reportFeedbackClickListener);
        }

        if(changeInfo != null){

            changeInfo.setOnPreferenceClickListener(changeInfoClickListener);
        }



        if(messageNotification != null){

            messageNotification.setOnPreferenceChangeListener(messageNotificationChangeListener);
        }
        if(contentNotification != null){

            contentNotification.setOnPreferenceChangeListener(contentUpdatesNotificationChangeListener);
        }

        if(productsNotification != null){

            productsNotification.setOnPreferenceChangeListener(productNotificationChangeListener);
        }





    }

    Preference.OnPreferenceClickListener logOutClickListener = new Preference.OnPreferenceClickListener() {
        @Override
        public boolean onPreferenceClick(Preference preference) {


            sharedPreferences.saveBooleanPref(getString(R.string.loginStatus),false);

            if(getActivity()!=null){
                Intent mainIntent = new Intent(getActivity(), AuthActivity.class);
                mainIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(mainIntent);
                getActivity().finish();
            }





            return false;


        }
    };

    Preference.OnPreferenceClickListener changeInfoClickListener = new Preference.OnPreferenceClickListener() {
        @Override
        public boolean onPreferenceClick(Preference preference) {

            if(getActivity() != null){
                getActivity().startActivity(new Intent(getActivity(),ChangeInfoActivity.class));
            }

            return true;


        }
    };

    Preference.OnPreferenceClickListener reportFeedbackClickListener = new Preference.OnPreferenceClickListener() {
        @Override
        public boolean onPreferenceClick(Preference preference) {

            final Intent intent = new Intent(Intent.ACTION_VIEW)
                    .setType("plain/text")
                    .setClassName("com.google.android.gm", "com.google.android.gm.ComposeActivityGmail");

            intent.setData(Uri.parse("skgismos999@gmail.com"))
                    .putExtra(Intent.EXTRA_SUBJECT, "Feedback On PsychQ - The Mind Food");
            //.putExtra(Intent.EXTRA_TEXT, "hello. this is a message sent from my demo app :-)") // for body
            startActivity(intent);


            return true;


        }
    };

    Preference.OnPreferenceClickListener unSubClickListener = new Preference.OnPreferenceClickListener() {
        @Override
        public boolean onPreferenceClick(Preference preference) {


            if(sharedPreferences.getBooleanPref(getString(R.string.isSubscribed),false)){

                if(getActivity() != null){

                    getActivity().startActivity(new Intent(getActivity(), UnSubActivity.class));

                }

            }else {
                //user is not subscribed
                cookiesHelper.showCookie("Subscription not found","You are not subscribed to our content. Please subscribe first.",null,null);
            }




            return true;


        }
    };

    Preference.OnPreferenceClickListener SubClickListener = new Preference.OnPreferenceClickListener() {
        @Override
        public boolean onPreferenceClick(Preference preference) {


            if(getActivity() != null){

                if(sharedPreferences.getBooleanPref(getString(R.string.isSubscribed),false)){
                    //User is already subscribed
                    cookiesHelper.showCookie("Already Subscribed","You are already Subscribed to our content",null,null);

                }else {
                    //user is not subscribed
                    getActivity().startActivity(new Intent(getActivity(), SubscriptionActivity.class));

                }


            }



            return true;


        }
    };

    Preference.OnPreferenceChangeListener messageNotificationChangeListener = new Preference.OnPreferenceChangeListener() {
        @Override
        public boolean onPreferenceChange(Preference preference, Object newValue) {


            if (newValue.equals("true")) {
                Toast.makeText(getActivity(), "New Letters Notifications Enabled", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(getActivity(), "New Letters Notifications Disabled", Toast.LENGTH_LONG).show();
            }


            return true;
        }
    };

    Preference.OnPreferenceChangeListener productNotificationChangeListener = new Preference.OnPreferenceChangeListener() {
        @Override
        public boolean onPreferenceChange(Preference preference, Object newValue) {


            Log.d(TAG, "onPreferenceChange: "+newValue);

            if (newValue.equals("true")) {
                Toast.makeText(getActivity(), "Products Notifications Enabled", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(getActivity(), "Products Notifications Disabled", Toast.LENGTH_LONG).show();
            }


            return true;
        }
    };

    Preference.OnPreferenceChangeListener contentUpdatesNotificationChangeListener = new Preference.OnPreferenceChangeListener() {
        @Override
        public boolean onPreferenceChange(Preference preference, Object newValue) {


            if (newValue.equals("true")) {
                Toast.makeText(getActivity(), "Content Update Notifications Enabled", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getActivity(), "Content Update Notifications Disabled", Toast.LENGTH_SHORT).show();
            }


            return true;
        }
    };

}
