package com.android.testdai.application.ui.abstractions;

import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleObserver;
import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.OnLifecycleEvent;

import com.android.testdai.tools.RxTool;

import io.reactivex.disposables.CompositeDisposable;

public abstract class AbstractPresenter<V extends LifecycleOwner> implements LifecycleObserver {

    protected CompositeDisposable disposables;
    protected V view;

    public void attachView(V view) {

        this.view = view;
        this.view.getLifecycle().addObserver(this);
        this.disposables = new CompositeDisposable();

    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    protected void detachView(){

        RxTool.autoDispose(disposables);
        this.view = null;

    }

    public V getView(){return view;}

}
