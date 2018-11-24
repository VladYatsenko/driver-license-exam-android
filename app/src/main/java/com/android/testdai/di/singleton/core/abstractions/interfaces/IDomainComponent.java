package com.android.testdai.di.singleton.core.abstractions.interfaces;

import com.android.testdai.application.ui.activities.main.MainPresenter;
import com.android.testdai.application.ui.activities.settings.SettingPresenter;
import com.android.testdai.application.ui.activities.settings.SettingsActivity;
import com.android.testdai.application.ui.activities.test.TestPresenter;
import com.android.testdai.application.ui.dialogs.category.CategoryPresenter;
import com.android.testdai.di.app.AppScope;

import dagger.Component;

@AppScope
@Component(dependencies = ICoreComponent.class)
public interface IDomainComponent {

    void inject(MainPresenter presenter);

    void inject(TestPresenter presenter);

    void inject(SettingPresenter presenter);

    void inject(CategoryPresenter presenter);

}
