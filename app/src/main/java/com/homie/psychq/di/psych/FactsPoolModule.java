package com.homie.psychq.di.psych;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.homie.psychq.RetrofitCache;
import com.homie.psychq.main.api.factspool.ApiUrls;
import com.homie.psychq.main.api.factspool.FactsPoolApi;
import com.homie.psychq.main.ui.factspool.FactsPoolActivity;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/*Inits and provides FactsPool API*/
@Module
public class FactsPoolModule {


    private FactsPoolActivity factsPoolActivity;

    public FactsPoolModule(FactsPoolActivity factsPoolActivity) {
        this.factsPoolActivity = factsPoolActivity;
    }

    @PsychScope
    @Named("factspool")  //when u have two provides with same return type
    @Provides
    public static Retrofit getFactsPoolClient(ApiUrls apiUrls) {

        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        RetrofitCache retrofitCache=new RetrofitCache();

        return new Retrofit.Builder().
                baseUrl(apiUrls.BASE_URL)
                .client(retrofitCache.okHttpClientFactsPool())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create()) //with this we can return flowable object from retrofit call
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

    }

    @PsychScope
    @Provides
    static FactsPoolApi provideFactsApi(@Named("factspool") Retrofit retrofit){
        return retrofit.create(FactsPoolApi.class);
    }

}
