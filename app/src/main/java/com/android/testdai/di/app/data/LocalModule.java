package com.android.testdai.di.app.data;

import android.content.Context;
import android.support.annotation.NonNull;

import com.android.testdai.application.data.local.prefs.PrefsRepo;
import com.android.testdai.application.data.local.prefs.abstraction.interfaces.IPrefsRepo;
import com.android.testdai.di.app.AppScope;

import dagger.Module;
import dagger.Provides;

@Module
public class LocalModule {

    @NonNull
    @Provides
    @AppScope
    IPrefsRepo providePrefsRepo(Context context){
        return new PrefsRepo(context);
    }

}
