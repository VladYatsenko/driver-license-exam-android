package com.android.testdai.di.app.domain;

import android.support.annotation.NonNull;

import com.android.testdai.application.domain.category.CategoryInteractor;
import com.android.testdai.application.domain.category.abstraction.ICategoryInteractor;
import com.android.testdai.di.app.AppScope;

import dagger.Module;
import dagger.Provides;

@Module
public class DomainModule {

    @NonNull
    @Provides
    @AppScope
    ICategoryInteractor provideICategoryInteractor() {
        return new CategoryInteractor();
    }

}
