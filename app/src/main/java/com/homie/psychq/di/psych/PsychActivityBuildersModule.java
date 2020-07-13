package com.homie.psychq.di.psych;

import com.homie.psychq.main.ui.factspool.FactsPoolActivity;
import com.homie.psychq.main.ui.categories.CategoryOnClick;
import dagger.Module;
import dagger.android.ContributesAndroidInjector;

/*Allows Activities to use injections*/
@Module
public abstract class PsychActivityBuildersModule {

    //annotate all provides with @PsychScope
    @ContributesAndroidInjector(modules = FactsPoolModule.class)
    abstract FactsPoolActivity contributeFactsPoolActivity();

    @ContributesAndroidInjector(
            modules = {
                    PsychModule.class}
    )
    abstract CategoryOnClick contributeOnCLickCategory();

}
