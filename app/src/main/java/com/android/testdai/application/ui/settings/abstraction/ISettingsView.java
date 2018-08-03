package com.android.testdai.application.ui.settings.abstraction;

import com.android.testdai.application.ui.settings.model.Setting;

import java.util.List;

public interface ISettingsView {

    void updateUI(List<Setting> settings);

}
