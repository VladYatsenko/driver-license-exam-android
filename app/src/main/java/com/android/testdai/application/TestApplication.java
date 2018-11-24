package com.android.testdai.application;

import android.app.Application;

import com.android.testdai.R;
import com.android.testdai.di.DIProvider;
import com.crashlytics.android.Crashlytics;
import com.google.android.gms.ads.MobileAds;

import io.fabric.sdk.android.Fabric;

public class TestApplication extends Application {


    @Override
    public void onCreate() {
        super.onCreate();
        DIProvider.init(this);

        Fabric.with(this, new Crashlytics());
        MobileAds.initialize(this, getString(R.string.app_id));



    }
}
