package com.android.testdai.application.ui.main;

import android.app.Activity;
import android.content.Intent;

import com.android.testdai.application.ui.category.DialogCategory;
import com.android.testdai.application.ui.main.abstraction.IMainView;
import com.android.testdai.application.ui.settings.SettingsActivity;
import com.android.testdai.application.ui.test.TestActivity;
import com.android.testdai.util.Constants;
import com.android.testdai.util.PermissionUtil;
import com.android.testdai.util.PreferencesUtil;

class MainPresenter  {

    private Activity activity;
    private IMainView iMainView;
    private String category;

    MainPresenter(Activity activity){

        this.activity = activity;
        iMainView = (IMainView) activity;
        category = PreferencesUtil.getInstance(activity).getCategory();

    }

    void attachView() {

        iMainView.updateUI(category);
    }

    void startTest(){

        if(PermissionUtil.isNetworkGranted(activity) && PermissionUtil.isExternalStorageGranted(activity)){
            Intent intent = TestActivity.newIntent(activity);
            activity.startActivity(intent);
        }

    }

    void startDialogCategory(){
        iMainView.startDialogCategory(category);
    }

    void onActivityResult(int requestCode, int resultCode, Intent intent){

        if (requestCode == Constants.REQUEST_CATEGORY) {
            iMainView.updateUI((String) intent.getSerializableExtra(DialogCategory.EXTRA_CATEGORY));
        }

    }

    void startSettings() {
        Intent intent = SettingsActivity.newIntent(activity);
        activity.startActivity(intent);
    }
}
