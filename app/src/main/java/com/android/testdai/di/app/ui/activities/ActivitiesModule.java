package com.android.testdai.di.app.ui.activities;

import android.support.annotation.NonNull;

import com.android.testdai.application.ui.activities.main.MainPresenter;
import com.android.testdai.application.ui.activities.settings.SettingPresenter;
import com.android.testdai.application.ui.activities.test.TestPresenter;
import com.android.testdai.di.app.AppScope;

import dagger.Module;
import dagger.Provides;

@Module
public class ActivitiesModule {

    @NonNull
    @Provides
    @AppScope
    MainPresenter provideMainPresenter() {
        return new MainPresenter();
    }

    @NonNull
    @Provides
    @AppScope
    SettingPresenter provideSettingPresenter() {
        return new SettingPresenter();
    }

    @NonNull
    @Provides
    @AppScope
    TestPresenter provideTestPresenter() {
        return new TestPresenter();
    }

}
