package com.android.testdai.application.ui.settings;

import android.content.Intent;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.SwitchPreference;
import android.support.annotation.Nullable;

import com.android.testdai.R;
import com.android.testdai.application.ui.dialog.DialogImage;
import com.android.testdai.application.ui.dialog.DialogImageCache;
import com.android.testdai.util.PreferencesUtil;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.PicassoProvider;

import static com.android.testdai.util.Constants.APP_PREFERENCES_DOUBLE_CLICK;
import static com.android.testdai.util.Constants.APP_PREFERENCES_ERROR_LIMIT;
import static com.android.testdai.util.Constants.APP_PREFERENCES_TIME_LIMIT;

public class SettingsFragment extends PreferenceFragment {

    private final String DIALOG_IMAGE_CACHE = "DIALOG_IMAGE_CACHE";

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        addPreferencesFromResource(R.xml.preferences);

        SwitchPreference timeLimit = (SwitchPreference) findPreference("time_limit");
        timeLimit.setChecked(PreferencesUtil.getInstance(getActivity()).getPreference(APP_PREFERENCES_TIME_LIMIT));
        timeLimit.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                PreferencesUtil.getInstance(getActivity()).setPreference(APP_PREFERENCES_TIME_LIMIT, timeLimit.isChecked());
                return true;
            }
        });

        SwitchPreference errorLimit = (SwitchPreference) findPreference("error_limit");
        errorLimit.setChecked(PreferencesUtil.getInstance(getActivity()).getPreference(APP_PREFERENCES_ERROR_LIMIT));
        errorLimit.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                PreferencesUtil.getInstance(getActivity()).setPreference(APP_PREFERENCES_ERROR_LIMIT, errorLimit.isChecked());
                return true;
            }
        });

        SwitchPreference doubleClick = (SwitchPreference) findPreference("double_click");
        doubleClick.setChecked(PreferencesUtil.getInstance(getActivity()).getPreference(APP_PREFERENCES_DOUBLE_CLICK));
        doubleClick.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                PreferencesUtil.getInstance(getActivity()).setPreference(APP_PREFERENCES_DOUBLE_CLICK, doubleClick.isChecked());
                return true;
            }
        });

        Preference shareIt = (Preference) findPreference("share_it");
        shareIt.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {

                Intent i = new Intent (Intent.ACTION_SEND);
                i.setType("text/plain");
                i.putExtra(Intent.EXTRA_TEXT, getString(R.string.share_text)+getString(R.string.app_source));
                i = Intent.createChooser(i, getString(R.string.send_share));
                startActivity(i);

                return true;
            }
        });

  /*      Preference cacheImage = (Preference) findPreference("image_cache");
        cacheImage.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {

                DialogImageCache dialog = DialogImageCache.newInstance(true);
                dialog.show(getFragmentManager(), DIALOG_IMAGE_CACHE);

                return true;
            }
        });

        Preference clearCache = (Preference) findPreference("clear_cache");
        clearCache.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {

                DialogImageCache dialog = DialogImageCache.newInstance(false);
                dialog.show(getFragmentManager(), DIALOG_IMAGE_CACHE);

                return true;
            }
        });
*/
    }

}