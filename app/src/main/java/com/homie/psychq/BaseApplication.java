package com.homie.psychq;



import android.app.Activity;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.util.Log;

import androidx.annotation.NonNull;

import com.downloader.PRDownloader;
import com.downloader.PRDownloaderConfig;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.google.firebase.messaging.FirebaseMessaging;
import com.homie.psychq.di.AppComponent;
import com.homie.psychq.di.DaggerAppComponent;
import com.homie.psychq.utils.SharedPreferences;

import dagger.android.AndroidInjector;
import dagger.android.support.DaggerApplication;

//Base Application acts like a client to AppComponent
public class BaseApplication extends DaggerApplication {

    private static final String TAG = "BaseApplication";

    public static final String CHANNEL_ID="Channel_1";
    public static final String CHANNEL_NAME="Channel__1";

    AppComponent component;
//    static PixaComponent pixaComponent;
    static BaseApplication baseApplication;

    @Override
    protected AndroidInjector<? extends DaggerApplication> applicationInjector() {
        // Note :  Rebuild the project once the components are injected/Annotations used
        // for dagger to generate the code
        //AppComponent will persist for entire life time of application by this code

        component= DaggerAppComponent.builder().application(this).build();



//        pixaComponent = DaggerPixaComponent.builder().appComponent(component).build();

        baseApplication = (BaseApplication) getApplicationContext();


        createNotificationChannel();
        subscribeToFCM();
        generateToken();
        initPRDownloader();

        return component;
    }

    private void initPRDownloader() {
        // Enabling database for resume support even after the application is killed:
        PRDownloaderConfig config = PRDownloaderConfig.newBuilder()
                .setDatabaseEnabled(true)
                .build();
        PRDownloader.initialize(baseApplication,config);
    }

    public AppComponent component(){
        return component;
    }

    public static BaseApplication get(Activity activity){
        return (BaseApplication) activity.getApplication();
    }

//    public static PixaComponent getPixaComponent(){
//        return pixaComponent;
//    }

    public static BaseApplication get(){
        return baseApplication;
    }

    private void subscribeToFCM() {

        //if account type is free we save a variable in shared prefs with two values freeVersion and paidVersion
        SharedPreferences sharedPreferences=new SharedPreferences(baseApplication);
        String accountType = sharedPreferences.getString("AccountType","freeVersion");
        if(accountType.equals("freeVersion")){

            FirebaseMessaging.getInstance().subscribeToTopic(getString(R.string.FreeVersion_NotificationSub))
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                        }
                    });
        }

        if(accountType.equals(getString(R.string.paidVersion_accountType))){
            FirebaseMessaging.getInstance().subscribeToTopic("paidVersion")
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                        }
                    });
        }


    }

    private void createNotificationChannel(){

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            NotificationChannel channel=new NotificationChannel(
                    CHANNEL_ID,
                    CHANNEL_NAME
                    , NotificationManager.IMPORTANCE_DEFAULT
            );

            NotificationManager manager=getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);


        }

    }

    private void generateToken(){
        FirebaseInstanceId.getInstance().getInstanceId()
                .addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
                    @Override
                    public void onComplete(@NonNull Task<InstanceIdResult> task) {

                        if (!task.isSuccessful()) {
                            Log.w(TAG, "getInstanceId failed", task.getException());
                            return;
                        }

                        // Get new Instance ID token
                        String token = task.getResult().getToken();

                        // Log and toast
                        String msg = token;
                        Log.d(TAG, msg);
                    }
                });
    }

    public static NetworkInfo internetConnected(){
        //Internet Connectivity
        ConnectivityManager connectivityManager = (ConnectivityManager)
                baseApplication.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return networkInfo;
    }




}
