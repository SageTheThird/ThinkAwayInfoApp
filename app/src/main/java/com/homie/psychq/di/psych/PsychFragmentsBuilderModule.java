package com.homie.psychq.di.psych;

import com.homie.psychq.main.ui.factspool.DateFragment;
import com.homie.psychq.main.ui.factspool.NumberFragment;
import com.homie.psychq.main.ui.factspool.YearFragment;
import com.homie.psychq.main.products.ProductsFragment;
import com.homie.psychq.main.ui.categories.CategoriesFragmentPsych;
import com.homie.psychq.main.ui.feeds.FeedsFragment;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

/*Allows fragments to use injections*/

@Module
public abstract class PsychFragmentsBuilderModule {


    @ContributesAndroidInjector(modules = {
            PsychModule.class
    })
    abstract FeedsFragment contributeFeaturedFragment();


    @ContributesAndroidInjector(
            modules = {}
    )
    abstract CategoriesFragmentPsych contributeCategoriesFragment();

    @ContributesAndroidInjector(
            modules = {}
    )
    abstract ProductsFragment contributeProductsFragment();

    @ContributesAndroidInjector(
            modules = {}
    )
    abstract NumberFragment contributeNumbersFragment();

    @ContributesAndroidInjector(
            modules = {}
    )
    abstract DateFragment contributeDateFragment();

    @ContributesAndroidInjector(
            modules = {}
    )
    abstract YearFragment contributeYearFragment();



}
