package com.homie.psychq.subscription;


import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.os.Vibrator;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.android.billingclient.api.AcknowledgePurchaseParams;
import com.android.billingclient.api.AcknowledgePurchaseResponseListener;
import com.android.billingclient.api.BillingClient;
import com.android.billingclient.api.BillingClientStateListener;
import com.android.billingclient.api.BillingFlowParams;
import com.android.billingclient.api.BillingResult;
import com.android.billingclient.api.Purchase;
import com.android.billingclient.api.PurchasesUpdatedListener;
import com.android.billingclient.api.SkuDetails;
import com.android.billingclient.api.SkuDetailsParams;
import com.android.billingclient.api.SkuDetailsResponseListener;
import com.bumptech.glide.Glide;
import com.homie.psychq.BaseApplication;
import com.homie.psychq.R;
import com.homie.psychq.di.psych.DaggerPsychComponent;
import com.homie.psychq.di.psych.PsychComponent;
import com.homie.psychq.google_apis.AccessToken;
import com.homie.psychq.google_apis.DeveloperAPI;
import com.homie.psychq.google_apis.GoogleAccessAPI;
import com.homie.psychq.google_apis.GoogleApiConstants;
import com.homie.psychq.main.api.main.PsychApi;
import com.homie.psychq.main.models.feeds.Subscription;
import com.homie.psychq.main.ui.MainActivity;
import com.homie.psychq.subscription.models.SubscriptionPurchase;
import com.homie.psychq.utils.CookiesHelper;

import com.homie.psychq.utils.SharedPreferences;

import java.io.IOException;
import java.util.ArrayList;
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
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade;

public class SubscriptionActivity extends AppCompatActivity implements PurchasesUpdatedListener, AcknowledgePurchaseResponseListener {


    private static final String TAG = "SubscriptionActivity";
    public static final String SUBSCRIPTION_BUTTON_TEXT = "Start Your 7-Days Free Trial";
    public static final String AFTER_SUBSCRIPTION_BUTTON_TEXT = "Enjoy The Journey";
    public static final String ERROR_BUTTON_TEXT = "Retry";
    public static final String RESUBSCRIPTION_BUTTON_TEXT = "Resubscribe";



    private Button subscribebtn;
    private ImageView mainIv;

    //
    private BillingClient billingClient;
    private BillingResult isFeatureSupportedResult;

    private SkuDetails skuDetailsGlobal;
    private String postedSubscriptionId;

    PsychApi psychApi;
    PsychComponent component;
    GoogleAccessAPI googleAccessAPI;
    DeveloperAPI developerAPI;
    GoogleApiConstants constants;


    String email;
    private SharedPreferences sharedPreferences;
    private CompositeDisposable mDisposibles = new CompositeDisposable();

    private CookiesHelper cookiesHelper;


    private TextView subscribeLaterTv, subscriptionAtJust1_tv;


    /*
    * When The User logs in, request will be sent to getSubscriptionByEmail and if the response is 200
    * then we get the purchaseToken or OrderId and send it to Developer API and validate the purchase and
    * give him entitlement And we show him Thank You Screen and he can continue + isSubscribed is set
    * to True so he can use unlocked content
    * If the response is 404 then we show him the Subscription Screen Then he can do one of the following
    *  - Subscribe Right Away i-e When the purchase is complete, we send the subscription to server, show him
    *    Thank You Page and isSubsribed True
    *  - If he chooses to subscribe later - he will be able to access the 200 posts (20 pages), 2 pages
    *    in each category, 20 articles in each Crash Course, Download + SetAsWall + Share Button Locked, Dictionary
    *    Locked. After Surfing through 200 posts user will be shown Start Your Free 7 Days Trial upon
    *    click he will be directed to SubscriptionActivity and when he purchases subscription, the Feeds are loaded
    *    again with isSubscribed True.
    * */


    /*
    * Library and Backend Setup Done Here
    * Things To Keep in Mind For Future
    *   - After Publishing add in-app product in console with the same id as in Products class
    *   -
    *
    * */


    private Retrofit retrofit;
    private Subscription subscriptionGlobal;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.subscription_activity);
        mainIv = findViewById(R.id.subscription_iv);
        subscribeLaterTv = findViewById(R.id.subscribeLaterTv);
        subscriptionAtJust1_tv = findViewById(R.id.subscription2v);
        constants = new GoogleApiConstants();



        /*
        * When Entered into activity a request to the server will be made, result will determine UI and next actions
        *
        * if the request goes success, act accordingly
        *   - If he is Subscribed - and subscription is Active, Show THank You UI
        *   - If he is subscribed but his subscription is not active - Show ReSubscription UI and init Billing
        *   - if he is not subscribed and there is no record of him in Server, Show Subscription UI and init Billing
        * if error, we show Server Error Vector
        * */

        subscribebtn=findViewById(R.id.subscribeBtn);

        sharedPreferences = new SharedPreferences(this);
        cookiesHelper = new CookiesHelper(this);


        initComponent();


        retrieveUserInfoFromPrefs();

        //This Request has to go Success, if not user cant proceed

        checkUserSubscriptionOnServer();
//        getAccessTokenForAuth();

        subscribebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                vibrate();
                if(subscribebtn.getText().equals(SUBSCRIPTION_BUTTON_TEXT)){
                    //launch Billing Flow
                    launchBillingScreen();

                }

                else if(subscribebtn.getText().equals(AFTER_SUBSCRIPTION_BUTTON_TEXT)) {
                    //Continue to Main Activity
                    gotoMainActivity();
                }

                else if(subscribebtn.getText().equals(ERROR_BUTTON_TEXT)){
                    //Resend Request to API for getSubscriptionByEmail
                    checkUserSubscriptionOnServer();

                }else if(subscribebtn.getText().equals(RESUBSCRIPTION_BUTTON_TEXT)){
                    //Resend Request to API for getSubscriptionByEmail
                    launchBillingScreen();

                }


            }
        });


        subscribeLaterTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //direct user to mainActivity with isOnTrial True
                //Value of isOnTrial will only be Changed when the user clicks Subscribe Later

                sharedPreferences.saveBooleanPref(getString(R.string.isOnTrial),true);
                gotoMainActivity();

            }
        });


    }

    private void vibrate(){
        Vibrator vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        vibrator.vibrate(100);

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

    private void getAccessTokenForAuth() {

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

                                developerAPI.getSubscription(subscriptionGlobal.getPackage_name(),
                                        Products.PRODUCT1,subscriptionGlobal.getPurchase_token())
                                        .toObservable()
                                        .subscribeOn(Schedulers.io())
                                        .observeOn(AndroidSchedulers.mainThread())
                                        .subscribe(new Observer<SubscriptionPurchase>() {
                                            @Override
                                            public void onSubscribe(Disposable d) {

                                            }

                                            @Override
                                            public void onNext(SubscriptionPurchase subscriptionPurchase) {

                                                if(subscriptionPurchase != null){
                                                    Log.d(TAG, "onNext: "+subscriptionPurchase.toString());
                                                    //if the subscription here has cancelReason field, it means that subscription has been canceled
                                                        /*  0 : User canceled the subscription
                                                            1 : Subscription was canceled by the system, for example because of a billing problem
                                                            2 : Subscription was replaced with a new subscription
                                                            3 : Subscription was canceled by the developer*/
                                                }
                                            }

                                            @Override
                                            public void onError(Throwable e) {

                                            }

                                            @Override
                                            public void onComplete() {

                                            }
                                        });
                                if(subscriptionGlobal != null){





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


    private void checkUserSubscriptionOnServer() {


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
                                //SHow Him Thank You Screen and set isSubscribed True

                                subscriptionGlobal = subscriptions.get(0);

                                unlockTheContent();
                                showAfterSubscriptionUI();
                            }else {

                                //if the user has previously canceled subscription, we make him subscribe again and
                                subscriptionGlobal = subscriptions.get(0);
                                cookiesHelper.showCookie("Subscription Previously Canceled","It Looks Like You Have Canceled Your Subscription Previously. Please Subscribe Again To Continue",null,null);
                                initBillingProcess();
                                showResubscriptionUI();



                            }


                        }else {
                            //no object retrieved, user not subscribed before
                            Log.d(TAG, "onNext: User Have Not Subscribed Yet ");
                            initBillingProcess();
                            showSubscriptionUI();

                        }


                    }

                    @Override
                    public void onError(Throwable e) {
                        //If error, user will be shown 500 Internal Server Error Animation or Vector
                        //User cant proceed if encountered error
                        showServerErrorUI();

                    }

                    @Override
                    public void onComplete() {

                    }
                });


    }

    private void showResubscriptionUI() {

        //Glide will load Subscription Banner in IV and Subscribe btn Text to Subscribe
        setTypeFacesSubscriptionScreen();
        loadImage(R.drawable.subscription,mainIv);
        setMainButtonText(RESUBSCRIPTION_BUTTON_TEXT);

        if(subscriptionAtJust1_tv.getVisibility() == View.GONE){
            subscriptionAtJust1_tv.setVisibility(View.VISIBLE);
        }
    }

    private void launchBillingScreen() {

        Log.d(TAG, "launchBillingScreen: "+skuDetailsGlobal);
        if(skuDetailsGlobal != null){


            Log.d(TAG, "launchBillingScreen: Attempting to launch Billing Screen");

            BillingFlowParams flowParams = BillingFlowParams.newBuilder()
                    .setSkuDetails(skuDetailsGlobal)

                    .build();
            BillingResult responseCode = billingClient
                    .launchBillingFlow(SubscriptionActivity.this,flowParams);

            Log.d(TAG, "launchBillingScreen: Response Code : "+responseCode);



        }
    }

    private void initComponent() {

        component= DaggerPsychComponent.builder()
                .build();

        psychApi=component.providePsychApi();
        googleAccessAPI = component.provideGoogleDevelopersAPI();
    }

    private void initBillingProcess() {
        //Billing Client Initiation and Products querying
        billingClient = BillingClient.newBuilder(this)
                .setListener(this)
                .enablePendingPurchases()
                .build();

        //


        isFeatureSupportedResult = billingClient.isFeatureSupported(BillingClient.FeatureType.SUBSCRIPTIONS);

        if(isFeatureSupportedResult.getResponseCode() != BillingResponse.OK){
            //Subscriptions Are Supported
            //start connection
            billingClient.startConnection(new BillingClientStateListener() {
                @Override
                public void onBillingSetupFinished(BillingResult billingResult) {


                    if (billingResult.getResponseCode() == BillingResponse.OK) {
                        // The BillingClient is ready. You can query purchases here.

                        //then we request for query products on Google Play
                        querySkuProducts();

                    }
                }
                @Override
                public void onBillingServiceDisconnected() {
                    // Try to restart the connection on the next request to
                    // Google Play by calling the startConnection() method.
                    //It's strongly recommended that you implement your own connection retry policy
                    showServerErrorUI();
                }
            });


        }

    }

    private void querySkuProducts() {

        List<String> skuList = new ArrayList<>();
        skuList.add(Products.PRODUCT1);//here we add the ID's of the products added in Play


        SkuDetailsParams.Builder params = SkuDetailsParams.newBuilder();

        params.setSkusList(skuList).setType(BillingClient.SkuType.SUBS);

        billingClient.querySkuDetailsAsync(params.build(),
                new SkuDetailsResponseListener() {
                    @Override
                    public void onSkuDetailsResponse(BillingResult billingResult,
                                                     List<SkuDetails> skuDetailsList) {
                        // Process the result.

                        if (billingResult.getResponseCode() == BillingResponse.OK && skuDetailsList != null) {
                            for (SkuDetails skuDetails : skuDetailsList) {
                                String sku = skuDetails.getSku();
                                String price = skuDetails.getPrice();
                                if (Products.PRODUCT1.equals(sku)) {
                                    skuDetailsGlobal = skuDetails;
                                }
//                                } else if ("gas".equals(sku)) {
//                                    gasPrice = price;
//                                }
                            }
                        }
                    }
                });
    }

    private void setTypeFacesSubscriptionScreen() {






        subscribeLaterTv.setPaintFlags(subscribeLaterTv.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);

    }

    private void showSubscriptionUI() {
        //Glide will load Subscription Banner in IV and Subscribe btn Text to Subscribe
        setTypeFacesSubscriptionScreen();
        loadImage(R.drawable.subscription,mainIv);
        setMainButtonText(SUBSCRIPTION_BUTTON_TEXT);
        if(subscriptionAtJust1_tv.getVisibility() == View.GONE){
            subscriptionAtJust1_tv.setVisibility(View.VISIBLE);
        }
    }

    private void showAfterSubscriptionUI() {
        //Glide will show thank you Image and Subscribe Btn Text to Continue
        subscriptionAtJust1_tv.setVisibility(View.INVISIBLE);
        subscribeLaterTv.setVisibility(View.INVISIBLE);
        subscriptionAtJust1_tv.setClickable(false);
        subscribeLaterTv.setClickable(false);
        loadImage(R.drawable.after_subscription,mainIv);
        setMainButtonText(AFTER_SUBSCRIPTION_BUTTON_TEXT);


    }

    private void showServerErrorUI() {
        //Glide will load Server Error Image and Subscribe Btn text to Retry
            loadImage(R.drawable.subscription_erro,mainIv);
        setMainButtonText(ERROR_BUTTON_TEXT);
        subscriptionAtJust1_tv.setVisibility(View.GONE);

    }

    private void retrieveUserInfoFromPrefs() {

        String userInfoString =   sharedPreferences.getString(getString(R.string.user_info_map_pref),"No Info Found");
        Map<String,String> userInfomap= sharedPreferences.getMapFromString(userInfoString);

        if(userInfomap != null){
            String username = userInfomap.get("Username");
            email = userInfomap.get("Email");

        }

    }

    @Override
    public void onPurchasesUpdated(BillingResult billingResult, @Nullable List<Purchase> purchases) {
        // to receive updates on purchases initiated by your app, as well as those initiated by the Google Play Store.
        //Google Play calls the onPurchasesUpdated() method to deliver the result of the purchase operation

        if (billingResult.getResponseCode() == BillingResponse.OK
                && purchases != null) {
            for (Purchase purchase : purchases) {
                //here we verify the purchase from a secure backend server and acknowledge purchase
                if(subscribebtn.getText().equals(SUBSCRIPTION_BUTTON_TEXT)){

                    postSubscriptionToServer(purchase);
                    handlePurchase(purchase);

                }else {
                    //user has purchased subscription again and his old subscription is being replaced in google play
                    //patch his existing subscription object with new
                    // purchaseToken, orderId, purchaseTime, isActive=True, originalJson
                    updatetheExistingSubObject(purchase);
                    handlePurchase(purchase);


                }

            }
        } else if (billingResult.getResponseCode() == BillingResponse.USER_CANCELED) {
            // Handle an error caused by a user cancelling the purchase flow.
            cookiesHelper.showCookie("You have Canceled The Subscription Process", "You can still enjoy a bunch of free content and subscribe later",null,null);
            showSubscriptionUI();
        } else {
            // Handle any other error codes.
            if(billingResult.getResponseCode() == BillingResponse.SERVICE_DISCONNECTED){
                cookiesHelper.showCookie("Service Disconnected", "Please Check Your Internet Connection And Try Again",null,null);
                showSubscriptionUI();
            }

            if(billingResult.getResponseCode() == BillingResponse.ERROR){
                cookiesHelper.showCookie("Error While Attempting Subscription", "Encountered a problem while subscribing. Please check your internet connection and try again",null,null);
                showSubscriptionUI();
            }
        }
    }

    private void updatetheExistingSubObject(Purchase purchase) {

        Subscription subscription = new Subscription();


        subscription.setPurchase_time(String.valueOf(purchase.getPurchaseTime()));
        subscription.setPurchase_status(getPurchaseState(purchase.getPurchaseState()));
        subscription.setDeveloper_payload(purchase.getDeveloperPayload());
        subscription.setIs_acknowledged(purchase.isAcknowledged());
        subscription.setIs_auto_renewing(purchase.isAutoRenewing());
        subscription.setOrder_id(purchase.getOrderId());
        subscription.setOriginal_json(purchase.getOriginalJson());
        subscription.setPurchase_signature(purchase.getSignature());
        subscription.setPurchase_token(purchase.getPurchaseToken());
        subscription.setSku_product(purchase.getSku());
        subscription.setIs_active(true);

        updateSubscription(subscription,subscriptionGlobal.getId());

    }

    private void postSubscriptionToServer(Purchase purchase) {
        //add object of subscription in api (Subscription App)
        Subscription subscription = new Subscription();


        subscription.setPurchase_time(String.valueOf(purchase.getPurchaseTime()));
        subscription.setPurchase_status(getPurchaseState(purchase.getPurchaseState()));
        subscription.setDeveloper_payload(purchase.getDeveloperPayload());
        subscription.setIs_acknowledged(purchase.isAcknowledged());
        subscription.setIs_auto_renewing(purchase.isAutoRenewing());
        subscription.setOrder_id(purchase.getOrderId());
        subscription.setPackage_name(purchase.getPackageName());
        subscription.setOriginal_json(purchase.getOriginalJson());
        subscription.setPurchase_signature(purchase.getSignature());
        subscription.setPurchase_token(purchase.getPurchaseToken());
        subscription.setUser_email(email);
        subscription.setSku_product(purchase.getSku());
        subscription.setIs_active(true);


        postSubscription(subscription);



    }

    private void postSubscription(Subscription subscription) {

        psychApi.postSubscription(subscription)
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
                            Log.d(TAG, "onNext: Subscription Posted To Server : "+subscription.toString());
                            postedSubscriptionId = subscription.getId();
                        }

                    }

                    @Override
                    public void onError(Throwable e) {

                        postSubscription(subscription);


                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    private String getPurchaseState(int purchaseState) {



        if(purchaseState == 0){
            return "UNSPECIFIED_STATE";
        }
        else if(purchaseState == 1){
            return "PURCHASED";
        }else {
            //PENDING
            return "PENDING";
        }

    }

    @Override
    public void onAcknowledgePurchaseResponse(BillingResult billingResult) {

        if(billingResult.getResponseCode() == BillingResponse.OK){
            //Whole Purchase Process is Complete
            if(postedSubscriptionId != null){
                //item has been posted Successfully
                Subscription subscription = new Subscription();
                subscription.setIs_acknowledged(true);
                updateSubscription(subscription,postedSubscriptionId);

            }

        }
    }

    private void gotoMainActivity(){
        Intent intent = new Intent(SubscriptionActivity.this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }

    void handlePurchase(Purchase purchase) {

        if (purchase.getPurchaseState() == Purchase.PurchaseState.PURCHASED) {

            // Acknowledge the purchase if it hasn't already been acknowledged.
            acknowledgeThePurchase(purchase);

            // Grant entitlement to the user.
            unlockTheContent();

            //Now Clicking Subscribe Button will direct them to MainActivity, while purchase is
            //being acknowledged in the background
            showAfterSubscriptionUI();


        }
    }

    private void acknowledgeThePurchase(Purchase purchase) {

        if (!purchase.isAcknowledged()) {
            AcknowledgePurchaseParams acknowledgePurchaseParams =
                    AcknowledgePurchaseParams.newBuilder()
                            .setPurchaseToken(purchase.getPurchaseToken())
                            .setDeveloperPayload(purchase.getDeveloperPayload())
                            .build();
            billingClient.acknowledgePurchase(acknowledgePurchaseParams, this);
        }

    }



    private void loadImage(int image, ImageView mainIv) {

        try {
            Glide.with(BaseApplication.get()).load(image)
                    .transition(withCrossFade())
                    .into(mainIv);

        }catch (Exception e){

            cookiesHelper.showCookie("Please check your internet connection and restart the app"
            ,"",null,null);

        }

    }

    private void setMainButtonText(String text) {

        subscribebtn.setText(text);

    }

    private void unlockTheContent() {
        //change isSubscribed Variable to True
        sharedPreferences.saveBooleanPref(getString(R.string.isSubscribed),true);
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



                        }
                    }

                    @Override
                    public void onError(Throwable e) {

                        updateSubscription(updatedSubscription,subscriptionId);


                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
}
