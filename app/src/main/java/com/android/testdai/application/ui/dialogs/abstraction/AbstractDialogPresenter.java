package com.android.testdai.application.ui.dialogs.abstraction;

import android.arch.lifecycle.LifecycleOwner;

import com.android.testdai.application.ui.abstractions.AbstractPresenter;

public abstract class AbstractDialogPresenter<V extends LifecycleOwner> extends AbstractPresenter<V> {

    public abstract void onDismissCommand();

}
