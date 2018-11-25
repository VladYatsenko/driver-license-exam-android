package com.android.testdai.application.ui.activities.main;

import android.content.Intent;

import com.android.testdai.application.data.local.prefs.abstraction.interfaces.IPrefsRepo;
import com.android.testdai.application.domain.category.abstraction.ICategoryInteractor;
import com.android.testdai.application.ui.abstractions.AbstractPresenter;
import com.android.testdai.application.ui.activities.main.abstraction.IMainView;
import com.android.testdai.di.DIProvider;
import com.android.testdai.util.Constants;

import javax.inject.Inject;

public class MainPresenter extends AbstractPresenter<IMainView> {


    @Inject
    protected ICategoryInteractor categoryInteractor;

    public MainPresenter(){
        DIProvider.getDomainComponent().inject(this);
    }

    @Override
     public void attachView(IMainView view) {
        super.attachView(view);
        updateCategoryUI(categoryInteractor.getCategory().toString());
    }

    private void updateCategoryUI(String category){
        view.updateCategory(category);
    }

    void startTest(){
        view.startTestActivity();
    }

    void startDialogCategory(){
        view.startDialogCategory();
    }


    void startSettings() {
        view.startSettingActivity();
    }
}
