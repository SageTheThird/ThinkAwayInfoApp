package com.homie.psychq;


import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import com.homie.psychq.main.api.main.Constants;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;


import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;


/*
* CLass responsible for all caching of https calls from retrofit
* */
public class RetrofitCache {

    private static final String TAG = "ServiceGenerator";


    public RetrofitCache() {

    }


    public  OkHttpClient okHttpClientPixa(){

        return new OkHttpClient.Builder()
                .cache(cache())
                .addInterceptor(httpLoggingInterceptor()) // used if network off OR on
                .addNetworkInterceptor(networkInterceptor()) // only used when network is on
                .addInterceptor(offlineInterceptor())
//                .addInterceptor(new HeaderInterceptor(Config.pixa_access_key))
                .build();

    }


    public  OkHttpClient okHttpClientPych(){
        Constants constants=new Constants();
        return new OkHttpClient.Builder()
                .cache(cache())
                .addInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(Chain chain) throws IOException {
                        Request request = chain.request();
                        Request.Builder newRequest = request.newBuilder()
                                .addHeader("Connection", "close")
                                .header("Authorization", constants.AUTHORIZATION);
                        return chain.proceed(newRequest.build());
                    }
                })
                .addInterceptor(httpLoggingInterceptor()) // used if network off OR on
                .addNetworkInterceptor(networkInterceptor()) // only used when network is on
                .addInterceptor(offlineInterceptor())
                .build();

    }

    public  OkHttpClient okHttpClientDeveloperApi(){
        Constants constants=new Constants();
        return new OkHttpClient.Builder()
                .cache(cache())
                .addInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(Chain chain) throws IOException {
                        Request request = chain.request();
                        Request.Builder newRequest = request.newBuilder()
                                .addHeader("Connection", "close")
                                .header("Authorization", constants.AUTHORIZATION);
                        return chain.proceed(newRequest.build());
                    }
                })
                .addInterceptor(httpLoggingInterceptor()) // used if network off OR on
                .addNetworkInterceptor(networkInterceptor()) // only used when network is on
                .addInterceptor(offlineInterceptor())
                .build();

    }

    public  OkHttpClient okHttpClientGoogleAccessToken(){
        return new OkHttpClient.Builder()
                .cache(cache())
                .addInterceptor(httpLoggingInterceptor()) // used if network off OR on
                .addNetworkInterceptor(networkInterceptor()) // only used when network is on
                .addInterceptor(offlineInterceptor())
                .build();

    }

    public  OkHttpClient okHttpClientWordsAPi(){



        return new OkHttpClient.Builder()
                .cache(cache())
                .addInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(Chain chain) throws IOException {
                        Request request = chain.request();
                        Request.Builder newRequest = request.newBuilder()
                                .addHeader("x-rapidapi-host","wordsapiv1.p.rapidapi.com")
                                .addHeader("x-rapidapi-key",BaseApplication.get().getString(R.string.WordsApiAccessKey));
                        return chain.proceed(newRequest.build());
                    }
                })
                .addInterceptor(httpLoggingInterceptor()) // used if network off OR on
                .addNetworkInterceptor(networkInterceptor()) // only used when network is on
                .addInterceptor(offlineInterceptor())
                .build();

    }


    public  OkHttpClient okHttpClientFactsPool(){
        return new OkHttpClient.Builder()
                .cache(cache())
                .addInterceptor(httpLoggingInterceptor()) // used if network off OR on
                .addNetworkInterceptor(networkInterceptor()) // only used when network is on
                .addInterceptor(offlineInterceptor())
                .build();

    }

    public  Cache cache(){
        return new Cache(new File(BaseApplication.get().getCacheDir(),"someIdentifier"), CacheInfo.cacheSize);
    }

    /**
     * This interceptor will be called both if the network is available and if the network is not available
     * @return
     */
    public  Interceptor offlineInterceptor() {
        return new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Log.d(TAG, "offline interceptor: called.");
                Request request = chain.request();

                // prevent caching when network is on. For that we use the "networkInterceptor"
                if (!isNetworkConnected()) {
                    CacheControl cacheControl = new CacheControl.Builder()
                            .maxStale(7, TimeUnit.DAYS)
                            .build();

                    request = request.newBuilder()
                            .removeHeader(CacheInfo.HEADER_PRAGMA)
                            .removeHeader(CacheInfo.HEADER_CACHE_CONTROL)
                            .cacheControl(cacheControl)
                            .build();
                }

                return chain.proceed(request);
            }
        };
    }

    /**
     * This interceptor will be called ONLY if the network is available
     * @return
     */
    public  Interceptor networkInterceptor() {
        return new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {


                Log.d(TAG, "network interceptor: called.");

                Response response = chain.proceed(chain.request());

                CacheControl cacheControl = new CacheControl.Builder()
                        .maxAge(10, TimeUnit.HOURS)//request will be stored in cache for this amount of time, until it gets refreshed (even with internet access)
                        .build();

                return response.newBuilder()
                        .removeHeader(CacheInfo.HEADER_PRAGMA)
                        .removeHeader(CacheInfo.HEADER_CACHE_CONTROL)
                        .header(CacheInfo.HEADER_CACHE_CONTROL, cacheControl.toString())
                        .build();
            }
        };
    }

    public  HttpLoggingInterceptor httpLoggingInterceptor () {
        HttpLoggingInterceptor httpLoggingInterceptor =
                new HttpLoggingInterceptor( new HttpLoggingInterceptor.Logger()
                {
                    @Override
                    public void log (String message)
                    {
                        Log.d(TAG, "log: http log: " + message);
                    }
                } );
        httpLoggingInterceptor.setLevel( HttpLoggingInterceptor.Level.BODY);
        return httpLoggingInterceptor;
    }



    public  boolean isNetworkConnected(){
        ConnectivityManager cm =
                (ConnectivityManager) BaseApplication.get().getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();
    }

}








