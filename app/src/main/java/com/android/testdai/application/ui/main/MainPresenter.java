package com.android.testdai.application.ui.main;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;

import com.android.testdai.application.ui.category.DialogCategory;
import com.android.testdai.application.ui.main.abstraction.IMainView;
import com.android.testdai.application.ui.settings.SettingsActivity;
import com.android.testdai.application.ui.test.TestActivity;
import com.android.testdai.util.AnalyticUtil;
import com.android.testdai.util.Constants;
import com.android.testdai.util.PermissionUtil;
import com.android.testdai.util.PreferencesUtil;

import static com.android.testdai.util.Constants.APP_PREFERENCES_CATEGORY;

public class MainPresenter  {

    private Context context;
    private IMainView iMainView;
    private String category;

    MainPresenter(Context context){

        this.context = context;
        iMainView = (IMainView) context;
        category = PreferencesUtil.getInstance(context).getCategory();

    }

    public void attachView() {

        iMainView.updateUI(category);
        AnalyticUtil.getInstance(context).logScreenEvent(context);

    }

    public void startTest(){

        //if(PermissionUtil.isNetworkGranted(activity)){}
            Intent intent = TestActivity.newIntent(context);
            context.startActivity(intent);

    }

    public void startDialogCategory(){
        iMainView.startDialogCategory(category);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent intent){

        if (requestCode == Constants.REQUEST_CATEGORY) {
            iMainView.updateUI((String) intent.getSerializableExtra(DialogCategory.EXTRA_CATEGORY));
        }

    }

    public void startSettings() {
        Intent intent = SettingsActivity.newIntent(context);
        context.startActivity(intent);
    }
}
