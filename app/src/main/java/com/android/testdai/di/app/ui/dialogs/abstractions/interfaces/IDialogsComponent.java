package com.android.testdai.di.app.ui.dialogs.abstractions.interfaces;

import com.android.testdai.application.ui.dialogs.category.CategoryPresenter;
import com.android.testdai.application.ui.dialogs.category.DialogCategory;
import com.android.testdai.di.app.AppScope;
import com.android.testdai.di.app.ui.dialogs.DialogsModule;
import com.android.testdai.di.singleton.core.abstractions.interfaces.ICoreComponent;

import dagger.Component;

@AppScope
@Component(modules = DialogsModule.class, dependencies = ICoreComponent.class)
public interface IDialogsComponent {

    void inject(DialogCategory dialogCategory);
}
