package com.homie.psychq.google_apis;

import retrofit2.Call;
import retrofit2.http.POST;
import retrofit2.http.Query;


/*end-points For getting authentication token of google api's which can be used to manage subscriptions*/
public interface GoogleAccessAPI {


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

    @POST("o/oauth2/token")
    Call<RefreshToken> getRefreshToken(@Query("grant_type") String grant_type, @Query("code") String code
            , @Query("client_id") String client_id , @Query("client_secret") String client_secret
            , @Query("redirect_uri") String redirect_uri);

    @POST("o/oauth2/token")
    Call<AccessToken> getAccessToken(@Query("grant_type") String grant_type,
                                      @Query("client_id") String client_id ,
                                      @Query("client_secret") String client_secret
            ,@Query("refresh_token") String refresh_token);

}