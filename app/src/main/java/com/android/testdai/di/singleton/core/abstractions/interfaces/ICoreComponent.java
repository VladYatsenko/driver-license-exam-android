package com.android.testdai.di.singleton.core.abstractions.interfaces;

import android.content.Context;

import com.android.testdai.di.singleton.core.ContextModule;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = ContextModule.class)
public interface ICoreComponent {

    Context shareContext();

}
