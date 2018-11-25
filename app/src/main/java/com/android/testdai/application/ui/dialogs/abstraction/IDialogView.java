package com.android.testdai.application.ui.dialogs.abstraction;

import android.arch.lifecycle.LifecycleOwner;

import io.reactivex.Completable;

public interface IDialogView extends LifecycleOwner {

    Completable closeDialog();

}