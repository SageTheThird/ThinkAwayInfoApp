package com.homie.psychq.google_apis;

import com.homie.psychq.subscription.models.SubscriptionPurchase;

import io.reactivex.Completable;
import io.reactivex.Flowable;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;


/*end-points for managing subscriptions*/
public interface DeveloperAPI {


    /*
    * grant_type=authorization_code
        code = code from above
        client_id = obvious from name
        client_secret= Provided when OAuth is created
        redirect_uri=should be same as registered
    *
    * */

    //getRefreshToken() is called first to get the refresh_token which can be used to refresh access token for
    //every request
    //getAccessToken() will take refresh_token from getRefreshToken() and refresh access_token


    //subId is the subscriptionId of the product that we enter in console at the time of creation i-e monthly001
    @POST("androidpublisher/v3/applications/{packageName}/purchases/subscriptions/{subId}/tokens/{token}:cancel")
    Call<ResponseBody> cancelSubscription(@Path("packageName") String packageName,
                                   @Path("subId") String subscriptionId,
                                   @Path("token") String purchaseToken  );


    @GET("androidpublisher/v3/applications/{packageName}/purchases/subscriptions/{subId}/tokens/{token}")
    Flowable<SubscriptionPurchase> getSubscription(@Path("packageName") String packageName,
                                                   @Path("subId") String subscriptionId,
                                                   @Path("token") String purchaseToken  );



    @GET("androidpublisher/v3/applications/{packageName}/purchases/voidedpurchases")
    Call<ResponseBody> getAllProductsForPackageName(@Path("packageName") String packageName);



}