package com.android.testdai.util;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;

import com.google.firebase.analytics.FirebaseAnalytics;

public class AnalyticUtil {

    private static AnalyticUtil analyticUtil;

    private static FirebaseAnalytics mFirebaseAnalytics;

    public static AnalyticUtil getInstance(Context context){
        if(analyticUtil==null){
            analyticUtil=new AnalyticUtil(context);
        }
        return analyticUtil;
    }

    private AnalyticUtil(Context context) {
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(context);
    }

    public void logButtonEvent(String name) {
        Bundle params = new Bundle();
        params.putString("name", name);
        mFirebaseAnalytics.logEvent(Constants.BUTTON_CLICK, params);
    }

    public void logScreenEvent(String className) {
        Bundle params = new Bundle();
        params.putString("name", className);
        mFirebaseAnalytics.logEvent(Constants.OPEN_SCREEN, params);
    }


}
