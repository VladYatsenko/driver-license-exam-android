package com.android.testdai.di.app.data.abstractions.interfaces;

import com.android.testdai.application.domain.question.QuestionInteractor;
import com.android.testdai.di.app.AppScope;
import com.android.testdai.di.app.data.LocalModule;
import com.android.testdai.di.singleton.core.abstractions.interfaces.ICoreComponent;

import dagger.Component;

@AppScope
@Component(dependencies = ICoreComponent.class, modules = LocalModule.class)
public interface IDataComponent {


    void inject(QuestionInteractor questionInteractor);
}
