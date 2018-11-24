package com.android.testdai.application.ui.activities.settings;

import android.content.Context;

import com.android.testdai.application.ui.abstractions.AbstractPresenter;
import com.android.testdai.application.ui.activities.settings.abstraction.ISettingsView;

public class SettingPresenter extends AbstractPresenter<ISettingsView> {


    public void attachView(ISettingsView view){
        super.attachView(view);
    }

}
