package com.homie.psychq.di.psych;

import com.homie.psychq.google_apis.GoogleAccessAPI;
import com.homie.psychq.main.api.main.PsychApi;
import com.homie.psychq.main.api.factspool.FactsPoolApi;
import com.homie.psychq.main.api.wordsapi.WordsAPi;

import dagger.Component;

@PsychScope
@Component(modules = {
        PsychActivityBuildersModule.class,
        PsychFragmentsBuilderModule.class,
        PsychModule.class,
        FactsPoolModule.class
}       )  //linking two components ,dependencies = PixaComponent.class
public interface PsychComponent {
    //will give us unsplash api, adapters,
    //we would have to inject this component in fragment or activity it will be using first
    //PixabayApi provideUnsplashApi();

    FactsPoolApi provideFactsApi();
    PsychApi providePsychApi();
    WordsAPi provideWordsAPI();
    GoogleAccessAPI provideGoogleDevelopersAPI();



}
