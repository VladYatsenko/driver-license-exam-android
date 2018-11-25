package com.android.testdai.application.data.local.prefs.abstraction.interfaces;

import io.reactivex.Completable;
import io.reactivex.Single;

public interface IPrefsRepo {

    Completable updateSetting(String key, boolean state);

    boolean getSetting(String key);

    void updateCategory(String category);

    String getCategory();

}
