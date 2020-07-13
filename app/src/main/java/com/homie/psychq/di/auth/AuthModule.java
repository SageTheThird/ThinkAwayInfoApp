package com.homie.psychq.di.auth;

import android.app.Application;
import android.content.Context;

import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.homie.psychq.R;

import dagger.Module;
import dagger.Provides;

import static com.blankj.utilcode.util.StringUtils.getString;


/*
* Builds and provides GoogleSignInOptions
* */
@Module
public class AuthModule {


    @Provides
    static GoogleSignInOptions requestIdToken(Application application){
        return new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
    }
}
