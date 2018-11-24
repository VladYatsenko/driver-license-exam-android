package com.android.testdai.application.ui.activities.main;

import android.content.Intent;

import com.android.testdai.application.data.local.prefs.abstraction.interfaces.IPrefsRepo;
import com.android.testdai.application.ui.abstractions.AbstractPresenter;
import com.android.testdai.application.ui.activities.main.abstraction.IMainView;
import com.android.testdai.application.ui.dialogs.category.DialogCategory;
import com.android.testdai.di.DIProvider;
import com.android.testdai.util.Constants;

import javax.inject.Inject;

public class MainPresenter extends AbstractPresenter<IMainView> {

    @Inject
    protected IPrefsRepo prefsRepo;

    private String category;

    public MainPresenter(){
        DIProvider.getDomainComponent().inject(this);
        category = prefsRepo.getCategory().toString();
    }

    @Override
     public void attachView(IMainView view) {
        super.attachView(view);
        updateCategoryUI(category);
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

    void onActivityResult(int requestCode, int resultCode, Intent intent){

        if (requestCode == Constants.REQUEST_CATEGORY) {
            updateCategoryUI((String) intent.getSerializableExtra(DialogCategory.EXTRA_CATEGORY));
        }

    }

    void startSettings() {
        view.startSettingActivity();
    }
}
