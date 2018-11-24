package com.android.testdai.application.data.local.prefs;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.android.testdai.application.data.local.prefs.abstraction.interfaces.IPrefsRepo;
import com.android.testdai.tools.RxTool;

import io.reactivex.Completable;
import io.reactivex.Single;

import static com.android.testdai.util.Constants.APP_PREFERENCES_DOUBLE_CLICK;

public class PrefsRepo implements IPrefsRepo {

    //  Shared preferences
    private static final String APP_PREFERENCES = "settings";
    private static final String APP_PREFERENCES_CATEGORY = "category";
    private static final String APP_PREFERENCES_TIME_LIMIT = "time_limit";
    private static final String APP_PREFERENCES_ERROR_LIMIT = "error_limit";
    private static final String APP_PREFERENCES_DOUBLE_CLICK = "double_click";

    private SharedPreferences sPrefs;

    public PrefsRepo(Context context){

        sPrefs = PreferenceManager.getDefaultSharedPreferences(context);

    }

    private Completable put(String key, Object value){

        return Completable.fromAction(
                () -> sPrefs.edit()
                .putString(key, String.valueOf(value))
                .apply()
        );

    }

    private Single get(String key){

        return Single.create(

                e ->{
                    String data = sPrefs.getString(key, "");

                    if (!data.isEmpty())
                        e.onSuccess(new RxTool.Optional<>(""));

                    else
                        e.onSuccess(new RxTool.Optional<>(null));
                }

        );

    }

    private Completable remove(String key) {
        return Completable.fromAction(() -> sPrefs.edit().remove(key).apply());
    }

    @Override
    public Completable updateSetting(String key, boolean state) {
        return put(key, state);
    }

    @Override
    public Single<Boolean> getSetting(String key) {
        if (key.equals(APP_PREFERENCES_DOUBLE_CLICK))
            return Single.just(sPrefs.getBoolean(key, false));
        else return Single.just(sPrefs.getBoolean(key, true));
    }

    @Override
    public Completable updateCategory(String category) {
        return put(APP_PREFERENCES_CATEGORY, category);
    }

    @Override
    public Single<String> getCategory() {
        return Single.just(sPrefs.getString(APP_PREFERENCES_CATEGORY, "B"));
    }
}
