package com.android.testdai.application.ui.abstractions;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.android.testdai.R;

import javax.inject.Inject;

public abstract class AbstractActivity <P extends AbstractPresenter> extends AppCompatActivity implements IActivityView {

    @Inject
    protected P presenter;
    protected abstract void injectDependencies();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        injectDependencies();

    }


    @Override
    public FragmentManager getActualFragmentManager() {
        return getSupportFragmentManager();
    }

}
