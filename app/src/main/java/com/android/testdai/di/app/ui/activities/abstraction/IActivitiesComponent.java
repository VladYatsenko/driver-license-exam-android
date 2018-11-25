package com.android.testdai.di.app.ui.activities.abstraction;

import com.android.testdai.application.ui.activities.main.MainActivity;
import com.android.testdai.application.ui.activities.settings.SettingsActivity;
import com.android.testdai.application.ui.activities.test.TestActivity;
import com.android.testdai.di.app.AppScope;
import com.android.testdai.di.app.ui.activities.ActivitiesModule;
import com.android.testdai.di.singleton.core.abstractions.interfaces.ICoreComponent;

import dagger.Component;

@AppScope
@Component(modules = ActivitiesModule.class, dependencies = ICoreComponent.class)
public interface IActivitiesComponent {

    void inject(MainActivity activity);

    void inject(TestActivity activity);

    void inject(SettingsActivity activity);

}
