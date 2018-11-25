package com.android.testdai.application.ui.dialogs.abstraction;

import android.arch.lifecycle.LifecycleOwner;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;

import javax.inject.Inject;

public abstract class AbstractDialog <P extends AbstractDialogPresenter> extends DialogFragment implements LifecycleOwner {

    @Inject
    protected P presenter;
    protected abstract void injectDependencies();


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        injectDependencies();

    }

}
