package com.homie.psychq.di;

import android.app.Application;

import com.homie.psychq.BaseApplication;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;
import dagger.android.AndroidInjector;
import dagger.android.support.AndroidSupportInjectionModule;

//here we tell components which modules (we created) to use
//@Singleton : the component will exist as long as application
@Singleton //the AppComponent owns singleton scope and to keep it in memory as long as app is running
@Component(
        modules = {
                AndroidSupportInjectionModule.class,//contains classes we need for dagger (Universal)
                ActivityBuildersModule.class, //module we created
                AppModule.class,
                ViewModelFactoryModule.class
        })
public interface AppComponent extends AndroidInjector<BaseApplication> {

    //for instantiating AppComponent
    @Component.Builder
    interface Builder{
        //@BindsInstance is used to bind an object or instance of object to the component at the time of construction, in this
        //case we bind Application object
        @BindsInstance
        Builder application(Application application);

        //overriding the builder
        //and it will return AppComponent
        AppComponent build();
    }

}
