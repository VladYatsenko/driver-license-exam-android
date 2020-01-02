package com.android.testdai.ui.fragments

import android.content.Intent
import android.os.Bundle
import android.preference.SwitchPreference
import android.view.View
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.SwitchPreferenceCompat
import com.android.testdai.R
import com.android.testdai.di.components.DaggerScreenComponent
import com.android.testdai.managers.SharedPreferencesManager
import com.android.testdai.ui.activities.BaseActivity
import com.android.testdai.utils.Constants.*
import com.android.testdai.utils.PreferencesUtil
import javax.inject.Inject

class SettingsFragment: PreferenceFragmentCompat() {

    @Inject
    lateinit var sharedPreferencesManager: SharedPreferencesManager

    private val TIME_LIMIT = "time_limit"
    private val ERROR_LIMIT = "error_limit"
    private val DOUBLE_CLICK = "double_click"
    private val SHARE_ID = "share_it"


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        inject()
    }

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.preferences, rootKey);
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val timeLimit = findPreference<SwitchPreferenceCompat>(TIME_LIMIT)
        timeLimit?.isChecked = sharedPreferencesManager.isTimeLimit
        timeLimit?.setOnPreferenceClickListener { preference ->
            sharedPreferencesManager.isTimeLimit = timeLimit.isChecked
            true
        }

        val errorLimit = findPreference<SwitchPreferenceCompat>(ERROR_LIMIT)
        errorLimit?.isChecked = PreferencesUtil.getInstance(activity).getPreference(APP_PREFERENCES_ERROR_LIMIT)
        errorLimit?.setOnPreferenceClickListener { preference ->
            sharedPreferencesManager.isErrorLimit = errorLimit.isChecked
            true
        }

        val doubleClick = findPreference<SwitchPreferenceCompat>(DOUBLE_CLICK)
        doubleClick?.isChecked = PreferencesUtil.getInstance(activity).getPreference(APP_PREFERENCES_DOUBLE_CLICK)
        doubleClick?.setOnPreferenceClickListener { preference ->
            sharedPreferencesManager.isDoubleClick = doubleClick.isChecked
            true
        }

        val shareIt = findPreference<Preference>(SHARE_ID)
        shareIt?.setOnPreferenceClickListener { preference ->

            var i = Intent(Intent.ACTION_SEND)
            i.type = "text/plain"
            i.putExtra(Intent.EXTRA_TEXT, getString(R.string.share_text) + getString(R.string.app_source))
            i = Intent.createChooser(i, getString(R.string.send_share))
            startActivity(i)

            true
        }
    }

    private fun inject() {
        DaggerScreenComponent.builder()
                .applicationComponent((activity as BaseActivity).applicationComponent)
                .build()
                .inject(this)
    }

}