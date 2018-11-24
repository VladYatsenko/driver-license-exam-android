package com.android.testdai.di;

import android.content.Context;

import com.android.testdai.di.app.data.LocalModule;
import com.android.testdai.di.app.data.abstractions.interfaces.DaggerIDataComponent;
import com.android.testdai.di.app.data.abstractions.interfaces.IDataComponent;
import com.android.testdai.di.app.ui.ActivitiesModule;
import com.android.testdai.di.singleton.core.ContextModule;
import com.android.testdai.di.singleton.core.abstractions.interfaces.DaggerIActivitiesComponent;
import com.android.testdai.di.singleton.core.abstractions.interfaces.DaggerICoreComponent;
import com.android.testdai.di.singleton.core.abstractions.interfaces.DaggerIDomainComponent;
import com.android.testdai.di.singleton.core.abstractions.interfaces.IActivitiesComponent;
import com.android.testdai.di.singleton.core.abstractions.interfaces.ICoreComponent;
import com.android.testdai.di.singleton.core.abstractions.interfaces.IDomainComponent;


public class DIProvider {

    private static IActivitiesComponent activitiesComponent;

    private static IDomainComponent domainComponent;

    private static IDataComponent dataComponent;

    public static void init(Context context){

        ICoreComponent coreComponent = DaggerICoreComponent
                .builder()
                .contextModule(new ContextModule(context))
                .build();

        dataComponent = DaggerIDataComponent
                .builder()
                .iCoreComponent(coreComponent)
                .localModule(new LocalModule())
                .build();

        domainComponent = DaggerIDomainComponent
                .builder()
                .iCoreComponent(coreComponent)
                //.domainModule(new DomainModule())
                .build();

        activitiesComponent = DaggerIActivitiesComponent
                .builder()
                .iCoreComponent(coreComponent)
                .activitiesModule(new ActivitiesModule())
                .build();

    }

    public static IActivitiesComponent getActivitiesComponent(){
        return activitiesComponent;
    }

    public static IDomainComponent getDomainComponent(){
        return domainComponent;
    }

    public static IDataComponent getDataComponent(){
        return dataComponent;
    }

}
