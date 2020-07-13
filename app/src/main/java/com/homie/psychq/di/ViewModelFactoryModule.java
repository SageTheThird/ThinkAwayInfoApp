package com.homie.psychq.di;

import androidx.lifecycle.ViewModelProvider;

import dagger.Binds;
import dagger.Module;


@Module
public abstract class ViewModelFactoryModule {

    @Binds
    public abstract ViewModelProvider.Factory bindVIewModelFactory(ViewModelProviderFactory factory);




    //Binds do in one line while below is redundant
//    @Provides
//    static ViewModelProvider.Factory bindViewModelFActory(ViewModelProviderFactory factory){
//        return factory;
//    }
}
