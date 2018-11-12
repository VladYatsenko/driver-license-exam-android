package com.android.testdai.application.ui.settings;

import android.content.Context;

import com.android.testdai.application.ui.settings.abstraction.ISettingsView;
import com.android.testdai.application.ui.settings.model.Setting;

import java.util.ArrayList;
import java.util.List;

public class SettingPresenter {

    private Context context;
    private ISettingsView iSettingsView;

    public void attachView(Context context){

        this.context = context;
        iSettingsView = (ISettingsView) context;

    }

}
