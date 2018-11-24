package com.android.testdai.application.ui.activities.settings;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.android.testdai.R;
import com.android.testdai.application.ui.abstractions.AbstractActivity;
import com.android.testdai.di.DIProvider;

public class SettingsActivity extends AbstractActivity<SettingPresenter> {

    @Override
    protected void injectDependencies() {
        DIProvider.getActivitiesComponent().inject(this);
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
