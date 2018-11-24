package com.android.testdai.di.singleton.core;

import android.content.Context;
import android.support.annotation.NonNull;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class ContextModule {

    private Context context;

    public ContextModule(Context context){

        this.context = context;

    }

    @NonNull
    @Provides
    @Singleton
    Context provideContext(){
        return context;
    }


}
