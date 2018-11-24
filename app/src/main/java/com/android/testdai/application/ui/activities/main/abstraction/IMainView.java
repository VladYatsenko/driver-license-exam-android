package com.android.testdai.application.ui.activities.main.abstraction;

import android.arch.lifecycle.LifecycleOwner;

public interface IMainView extends LifecycleOwner {

    void updateCategory(String category);

    void startDialogCategory();

    void startTestActivity();

    void startSettingActivity();

}
