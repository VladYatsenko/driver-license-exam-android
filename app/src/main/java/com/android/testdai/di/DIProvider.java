package com.android.testdai.di;

import android.content.Context;

import com.android.testdai.di.app.data.LocalModule;
import com.android.testdai.di.app.data.abstractions.interfaces.DaggerIDataComponent;
import com.android.testdai.di.app.data.abstractions.interfaces.IDataComponent;
import com.android.testdai.di.app.domain.DomainModule;
import com.android.testdai.di.app.domain.abstraction.DaggerIDomainComponent;
import com.android.testdai.di.app.domain.abstraction.IDomainComponent;
import com.android.testdai.di.app.ui.activities.ActivitiesModule;
import com.android.testdai.di.app.ui.activities.abstraction.DaggerIActivitiesComponent;
import com.android.testdai.di.app.ui.activities.abstraction.IActivitiesComponent;
import com.android.testdai.di.app.ui.dialogs.DialogsModule;
import com.android.testdai.di.app.ui.dialogs.abstractions.interfaces.DaggerIDialogsComponent;
import com.android.testdai.di.app.ui.dialogs.abstractions.interfaces.IDialogsComponent;
import com.android.testdai.di.singleton.core.ContextModule;
import com.android.testdai.di.singleton.core.abstractions.interfaces.DaggerICoreComponent;
import com.android.testdai.di.singleton.core.abstractions.interfaces.ICoreComponent;


public class DIProvider {

    private static IActivitiesComponent activitiesComponent;

    private static IDomainComponent domainComponent;

    private static IDataComponent dataComponent;

    private static IDialogsComponent dialogsComponent;

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
                .domainModule(new DomainModule())
                .build();

        activitiesComponent = DaggerIActivitiesComponent
                .builder()
                .iCoreComponent(coreComponent)
                .activitiesModule(new ActivitiesModule())
                .build();

        dialogsComponent = DaggerIDialogsComponent
                .builder()
                .iCoreComponent(coreComponent)
                .dialogsModule(new DialogsModule())
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

    public static IDialogsComponent getDialogsComponent() {
        return dialogsComponent;
    }

}
