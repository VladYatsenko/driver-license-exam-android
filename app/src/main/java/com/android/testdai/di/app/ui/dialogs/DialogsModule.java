package com.android.testdai.di.app.ui.dialogs;

import android.support.annotation.NonNull;

import com.android.testdai.application.ui.dialogs.category.CategoryPresenter;
import com.android.testdai.di.app.AppScope;

import dagger.Module;
import dagger.Provides;

@Module
public class DialogsModule {

    @NonNull
    @Provides
    @AppScope
    CategoryPresenter provideCategoryPresenter() {
        return new CategoryPresenter();
    }


}
