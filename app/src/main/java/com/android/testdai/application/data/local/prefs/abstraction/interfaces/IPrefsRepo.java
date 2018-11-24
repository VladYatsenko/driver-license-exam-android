package com.android.testdai.application.data.local.prefs.abstraction.interfaces;

import io.reactivex.Completable;
import io.reactivex.Single;

public interface IPrefsRepo {

    Completable updateSetting(String key, boolean state);

    Single<Boolean> getSetting(String key);

    Completable updateCategory(String category);

    Single<String> getCategory();

}
