package com.homie.psychq.di;

import com.homie.psychq.auth.ui.AuthActivity;
import com.homie.psychq.di.auth.AuthModule;
import com.homie.psychq.main.ui.MainActivity;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class ActivityBuildersModule {
    //where all dependencies lives
    //for instance if we had 10 activities we will add 10 @ContrubutesAndroidInjector for each activity

    //We are declaring AuthViewModelsModule to be in scope of AuthActivity

    @ContributesAndroidInjector(modules = AuthModule.class)
    abstract AuthActivity contributeAuthActivity();


    @ContributesAndroidInjector
    abstract MainActivity contributeMainActivity();


}
