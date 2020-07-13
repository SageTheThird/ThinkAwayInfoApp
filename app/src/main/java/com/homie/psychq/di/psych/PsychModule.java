package com.homie.psychq.di.psych;

import com.homie.psychq.RetrofitCache;
import com.homie.psychq.google_apis.GoogleAccessAPI;
import com.homie.psychq.google_apis.GoogleApiConstants;
import com.homie.psychq.main.api.main.Constants;
import com.homie.psychq.main.api.main.PsychApi;
import com.homie.psychq.main.api.factspool.ApiUrls;
import com.homie.psychq.main.ui.feeds.FeedsFragment;
import com.homie.psychq.main.api.wordsapi.ConstantsWordsAPI;
import com.homie.psychq.main.api.wordsapi.WordsAPi;


import javax.inject.Named;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/*Responsible for Main API, Words Api, Google Developers API Provision*/

@Module
public class PsychModule {
    //context
    private final FeedsFragment context;

    public PsychModule(FeedsFragment context) {
        this.context = context;
    }


    @Provides
    static Constants provideConstantForPsych(){
        Constants constants=new Constants();
        return constants;
    }

    @Provides
    static ApiUrls provideConstantForFactsPoolApi(){
        ApiUrls apiUrls=new ApiUrls();
        return apiUrls;
    }

    @Provides
    static ConstantsWordsAPI provideConstantForWordsApi(){
        ConstantsWordsAPI apiUrls=new ConstantsWordsAPI();
        return apiUrls;
    }


    @Provides
    static GoogleApiConstants provideConstantForGoogleDevelopersAPI(){
        GoogleApiConstants constants=new GoogleApiConstants();
        return constants;
    }


    @PsychScope
    @Provides
    static PsychApi providePsychApi(@Named("psych") Retrofit retrofit){
        return retrofit.create(PsychApi.class);
    }

    @PsychScope
    @Named("psych")
    @Provides
    static Retrofit getPsychAPIClient(Constants constants) {


        RetrofitCache retrofitCache=new RetrofitCache();

        return new Retrofit.Builder().
                baseUrl(constants.BASE_URL)
                .client(retrofitCache.okHttpClientPych())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create()) //with this we can return flowable object from retrofit call
                .addConverterFactory(GsonConverterFactory.create())
                .build();

    }

    @PsychScope
    @Provides
    static WordsAPi provideWordsApi(@Named("wordsapi") Retrofit retrofit){
        return retrofit.create(WordsAPi.class);
    }

    @PsychScope
    @Named("wordsapi")
    @Provides
    static Retrofit getWordsAPiClient(ConstantsWordsAPI constants) {


        RetrofitCache retrofitCache=new RetrofitCache();

        return new Retrofit.Builder().
                baseUrl(constants.BASE_URL_WORDS_API)
                .client(retrofitCache.okHttpClientWordsAPi())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create()) //with this we can return flowable object from retrofit call
                .addConverterFactory(GsonConverterFactory.create())
                .build();

    }

    @PsychScope
    @Provides
    static GoogleAccessAPI provideDevelopersApi(@Named("developersapi") Retrofit retrofit){
        return retrofit.create(GoogleAccessAPI.class);
    }

    @PsychScope
    @Named("developersapi")
    @Provides
    static Retrofit getGoogleDevelopersApiClient(GoogleApiConstants constants) {


        RetrofitCache retrofitCache=new RetrofitCache();

        return new Retrofit.Builder().
                baseUrl(constants.CONSTANT_URL_ACCESS_TOKEN)
                .client(retrofitCache.okHttpClientGoogleAccessToken())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create()) //with this we can return flowable object from retrofit call
                .addConverterFactory(GsonConverterFactory.create())
                .build();

    }





}
