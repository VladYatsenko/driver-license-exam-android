package com.android.testdai.di.app.domain.abstraction;

import com.android.testdai.application.ui.activities.main.MainPresenter;
import com.android.testdai.application.ui.activities.settings.SettingPresenter;
import com.android.testdai.application.ui.activities.settings.SettingsActivity;
import com.android.testdai.application.ui.activities.test.TestPresenter;
import com.android.testdai.application.ui.dialogs.category.CategoryPresenter;
import com.android.testdai.di.app.AppScope;
import com.android.testdai.di.app.data.LocalModule;
import com.android.testdai.di.app.domain.DomainModule;
import com.android.testdai.di.singleton.core.abstractions.interfaces.ICoreComponent;

import dagger.Component;

@AppScope
@Component(dependencies = ICoreComponent.class, modules = {DomainModule.class, LocalModule.class})
public interface IDomainComponent {

    void inject(MainPresenter presenter);

    void inject(TestPresenter presenter);

    void inject(SettingPresenter presenter);

    void inject(CategoryPresenter presenter);
}
