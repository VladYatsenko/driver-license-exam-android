package com.android.testdai.application.ui.settings;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.SwitchPreference;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.android.testdai.R;
import com.android.testdai.util.PreferencesUtil;

import static com.android.testdai.util.Constants.APP_PREFERENCES_DOUBLE_CLICK;
import static com.android.testdai.util.Constants.APP_PREFERENCES_ERROR_LIMIT;
import static com.android.testdai.util.Constants.APP_PREFERENCES_TIME_LIMIT;

public class SettingsActivity extends AppCompatActivity {

    private static String TAG = "SettingsActivity";


    public static Intent newIntent (Context context){
        return new Intent(context, SettingsActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_settings);

        getFragmentManager().beginTransaction()
                .replace(android.R.id.content, new SettingsFragment())
                .commit();

    }

}
