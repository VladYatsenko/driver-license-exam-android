package com.android.testdai.application.ui.activities.settings.abstraction;

import android.arch.lifecycle.LifecycleOwner;

import com.android.testdai.application.ui.activities.settings.model.Setting;

import java.util.List;

public interface ISettingsView extends LifecycleOwner {

    void updateUI(List<Setting> settings);

}
