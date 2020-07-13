package com.homie.psychq.subscription;

import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.homie.psychq.R;
import com.homie.psychq.auth.ui.AuthActivity;
import com.homie.psychq.di.psych.DaggerPsychComponent;
import com.homie.psychq.di.psych.PsychComponent;
import com.homie.psychq.google_apis.AccessToken;
import com.homie.psychq.google_apis.DeveloperAPI;
import com.homie.psychq.google_apis.GoogleAccessAPI;
import com.homie.psychq.google_apis.GoogleApiConstants;
import com.homie.psychq.main.api.main.PsychApi;
import com.homie.psychq.main.models.feeds.Subscription;
import com.homie.psychq.utils.FancyDialogHelper;
import com.homie.psychq.utils.SharedPreferences;
import com.shashank.sony.fancygifdialoglib.FancyGifDialogListener;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;


/*Handles if the user want to unsubscribe*/

public class UnSubActivity extends AppCompatActivity {

    private static final String TAG = "UnSubActivity";
    public static final String  packageName = "com.homie.psychq";
    private static final String  sku = "premium_monthly";

    private Button unsubBtn;
    PsychApi psychApi;
    PsychComponent component;
    GoogleAccessAPI googleAccessAPI;
    String email;
    private SharedPreferences sharedPreferences;
    private Retrofit retrofit;
    private Subscription subscriptionGlobal;
    CompositeDisposable mDisposibles = new CompositeDisposable();
    GoogleApiConstants constants;
    private DeveloperAPI developerAPI;
    private FancyDialogHelper fancyDialogHelper;
    private ProgressDialog dialog;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.unsub_activity);
        unsubBtn = findViewById(R.id.unsub_btn);
        sharedPreferences = new SharedPreferences(this);
        constants=new GoogleApiConstants();

        initComponent();
        retrieveUserInfoFromPrefs();
        checkUserSubscriptionOnServer();

        googleAccessAPI = component.provideGoogleDevelopersAPI();
        fancyDialogHelper = new FancyDialogHelper(this);



        unsubBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //First a dialog for confirmation then
                ///onConfirmation below code

                fancyDialogHelper.showFancyDialog("Confirmation Dialog","Please Confirm The Termination of Your Subscription"
                ,"Confirm","Cancel",ConfirmClickListener,CancelClickListener);


            }
        });


    }

    FancyGifDialogListener ConfirmClickListener = new FancyGifDialogListener() {
        @Override
        public void OnClick() {
         //SUbscription confirmed
         initSubscriptionTermination();
        }
    };

    private void initSubscriptionTermination() {

        dialog = new ProgressDialog(UnSubActivity.this);
        dialog.setMessage("Please wait while we cancel your subscription...");
        dialog.show();


        googleAccessAPI.getAccessToken(constants.GRANT_TYPE_ACCESS_TOKEN,constants.CLIENT_ID,constants.CLIENT_SECRET,
                constants.REFRESH_TOKEN)
                .enqueue(new Callback<AccessToken>() {
                    @Override
                    public void onResponse(Call<AccessToken> call, Response<AccessToken> response) {
                        if(response.body() != null){
                            Log.d(TAG, "onResponse: "+response.body().toString());

                            if(response.body().getAccess_token() != null){


                                //preparing retrofit and attaching accessToken with header for authorization
                                initRetrofitForApiWithAccessToken(response.body().getAccess_token());

                                //here we can call any method of developer's api with accessToken
                                //cancel subscription method should be called here
                                //set isActive in api to false
                                //show a dialog : Please note that by hitting unsub, your subscription to our services
                                //will be terminated and you will no longer be able to access all the content and features
                                //of the app. It is highly recommended that you UnSubscribe from our services a few days
                                //before the subscription renewal, so that you can use the full extent of your subscription.

                                if(subscriptionGlobal != null){


                                    developerAPI.cancelSubscription(subscriptionGlobal.getPackage_name(),
                                            Products.PRODUCT1,subscriptionGlobal.getPurchase_token())
                                            .enqueue(new Callback<ResponseBody>() {
                                                @Override
                                                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                                                    if(response.body() == null){

                                                        Log.d(TAG, "onComplete: Subscription Canceled Successfully");
                                                        //hit api for isActive=false patch
                                                        updateSubscriptionInServer();
                                                        logOutTheUser();
                                                        lockTheContent();

                                                    }

                                                }

                                                @Override
                                                public void onFailure(Call<ResponseBody> call, Throwable t) {
                                                    Log.d(TAG, "onError: Error While Canceling Subscription : "+t.getMessage());

                                                }
                                            });


                                }





                            }

                        }
                    }

                    @Override
                    public void onFailure(Call<AccessToken> call, Throwable t) {

                        Log.d(TAG, "onFailure: Error While Getting Access Token : "+t.getMessage());

                    }
                });
    }

    private void lockTheContent() {

        //change isSubscribed Variable to True
        sharedPreferences.saveBooleanPref(getString(R.string.isSubscribed),false);

    }

    private void updateSubscriptionInServer() {
        //setting the active will prompt him to resubscribe on startup
        Subscription subscription = new Subscription();
        subscription.setIs_active(false);

        updateSubscription(subscription, subscriptionGlobal.getId());
    }

    FancyGifDialogListener CancelClickListener = new FancyGifDialogListener() {
        @Override
        public void OnClick() {

            //subscription termination canceled

        }
    };

    private void gotoManageSubscriptions(){

        try {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/account/subscriptions?sku=$sku&package=$packageName")));
        } catch (ActivityNotFoundException e) {
            Toast.makeText(this, "Error While Loading Page", Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }


    }

    private void logOutTheUser(){


            sharedPreferences.saveBooleanPref(getString(R.string.loginStatus),false);
            Intent mainIntent = new Intent(UnSubActivity.this, AuthActivity.class);
            mainIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(mainIntent);
            finish();

    }

    private void initComponent() {

        component= DaggerPsychComponent.builder()
//                .pixaComponent(BaseApplication.getPixaComponent())
                .build();

        psychApi=component.providePsychApi();
    }

    private void retrieveUserInfoFromPrefs() {

        String userInfoString =   sharedPreferences.getString(getString(R.string.user_info_map_pref),"No Info Found");
        Map<String,String> userInfomap= sharedPreferences.getMapFromString(userInfoString);

        if(userInfomap != null){
            String username = userInfomap.get("Username");
            email = userInfomap.get("Email");

        }

    }

    private void checkUserSubscriptionOnServer() {

//        email = "ksajid";

        psychApi.getSubscriptionByUserEmail(email)
                .toObservable()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<Subscription>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        mDisposibles.add(d);
                    }

                    @Override
                    public void onNext(List<Subscription> subscriptions) {

                        if(subscriptions != null && subscriptions.size() > 0){
                            Log.d(TAG, "onNext: The User have Subscribed Before");
                            Log.d(TAG, "onNext: "+subscriptions.get(0).toString());
                            boolean isActive = subscriptions.get(0).isIs_active();
                            if(isActive){
                                //subscription is active
                                subscriptionGlobal = subscriptions.get(0);


                            }else {

                                //Subscription already Canceled
                                Toast.makeText(UnSubActivity.this, "Subscription Already Canceled", Toast.LENGTH_LONG).show();

                            }


                        }else {

                            //Subscription Not Found with specified account



                        }


                    }

                    @Override
                    public void onError(Throwable e) {
                        //If error, user will be shown 500 Internal Server Error Animation or Vector
                        //User cant proceed if encountered error
                        Toast.makeText(UnSubActivity.this, "Error While Checking", Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onComplete() {

                    }
                });


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mDisposibles.clear();
    }

    private void initRetrofitForApiWithAccessToken(String access_token) {

        retrofit = new Retrofit.Builder().
                baseUrl(constants.CONSTANT_URL_DEVELOPER_API)
                .client(new OkHttpClient.Builder()
                        .addInterceptor(new Interceptor() {
                            @Override
                            public okhttp3.Response intercept(Chain chain) throws IOException {
                                Request request = chain.request();
                                Request.Builder newRequest = request.newBuilder()
                                        .addHeader("Authorization","Bearer "+access_token);
                                return chain.proceed(newRequest.build());
                            }
                        })
                        .build())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create()) //with this we can return flowable object from retrofit call
                .addConverterFactory(GsonConverterFactory.create())
                .build();


        developerAPI = retrofit.create(DeveloperAPI.class);
    }

    private void updateSubscription(Subscription updatedSubscription, String subscriptionId){

        psychApi.updateSubscription(updatedSubscription,subscriptionId)
                .toObservable()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Subscription>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        mDisposibles.add(d);
                    }

                    @Override
                    public void onNext(Subscription subscription) {

                        if(subscription != null){
                            Log.d(TAG, "onNext: Subscription Fields Updated Successfully : "+subscription.toString());
                            if(dialog != null){
                                dialog.dismiss();
                            }
                        }
                    }

                    @Override
                    public void onError(Throwable e) {

                        Log.d(TAG, "onError: Error Updating Subscription "+e.getMessage());
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
}
