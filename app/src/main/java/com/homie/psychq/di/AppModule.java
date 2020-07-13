package com.homie.psychq.di;


import android.app.Application;

import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;

import com.homie.psychq.R;
import com.homie.psychq.utils.SharedPreferences;
import com.nostra13.universalimageloader.cache.memory.impl.WeakMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import javax.inject.Singleton;
import dagger.Module;
import dagger.Provides;


/*
* Provides Universal Classes/Components to be injected
* */
@Module
public class AppModule {

    public static final int DEFAULT_IMAGE= R.drawable.ic_default_universal;


    @Provides
    static SharedPreferences provideSharedPrefs(Application context){
        return new SharedPreferences(context);
    }

    @Singleton
    @Provides
    static DisplayImageOptions provideDisplayImageOptions(){

        return new DisplayImageOptions.Builder()
                .showImageOnLoading(DEFAULT_IMAGE)
                .showImageForEmptyUri(DEFAULT_IMAGE)
                .considerExifParams(true)
                .showImageOnFail(DEFAULT_IMAGE)
                .cacheOnDisk(true).cacheInMemory(true)
                .cacheOnDisk(true).resetViewBeforeLoading(true)
                .imageScaleType(ImageScaleType.EXACTLY)
                .displayer(new FadeInBitmapDisplayer(300)).build();
    }

    @Provides
    static ImageLoaderConfiguration provideImageLoaderConfig(DisplayImageOptions displayImageOptions,Application context){
        /*
        * use of imageLoader : inject ImageLoaderConfiguration and init imageLoader with ImageLoaderConfiguration
        * */
        return new ImageLoaderConfiguration.Builder(context)
                .defaultDisplayImageOptions(displayImageOptions)
                .memoryCache(new WeakMemoryCache())
                .diskCacheSize(100 *1024 *1024).build();


    }



    @Singleton
    @Provides          //We are able to use Application because we declared it in AppComponent on BindInstance
    static boolean getApp(Application application){
        return application==null; //will return true if application is null
    }


    @Provides
    static RequestOptions provideRequestOptions(){
        return RequestOptions.
                placeholderOf(R.drawable.ic_testthree)
                .error(R.drawable.ic_refresh)
                .override(18,18)
                .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC);

    }

}
