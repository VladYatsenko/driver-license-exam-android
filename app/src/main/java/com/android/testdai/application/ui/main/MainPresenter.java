package com.android.testdai.application.ui.main;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;

import com.android.testdai.application.ui.category.DialogCategory;
import com.android.testdai.application.ui.main.abstraction.IMainView;
import com.android.testdai.application.ui.test.TestActivity;
import com.android.testdai.util.Constants;

import static com.android.testdai.util.Constants.APP_PREFERENCES_CATEGORY;

public class MainPresenter  {

    private Context context;
    private IMainView iMainView;
    private SharedPreferences settings;
    private String category;

    MainPresenter(Context context){

        this.context = context;
        iMainView = (IMainView) context;
        settings = context.getSharedPreferences(Constants.APP_PREFERENCES, Context.MODE_PRIVATE);
        category = settings.getString(APP_PREFERENCES_CATEGORY, "B");

    }

    public void attachView() {
//        if (settings.contains(APP_PREFERENCES_CATEGORY)) {
            iMainView.updateUI(category);
//        }
    }

    public void startTest(){

        Intent intent = TestActivity.newIntent(context, category);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            context.startActivity(intent);
        }

    }

    public void startDialogCategory(){
        iMainView.startDialogCategory(category);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent intent){
        if (requestCode == Constants.REQUEST_CATEGORY) {
            category = (String) intent
                    .getSerializableExtra(DialogCategory.EXTRA_CATEGORY);
            SharedPreferences.Editor editor = settings.edit();
            editor.putString(APP_PREFERENCES_CATEGORY, category);
            editor.apply();
            iMainView.updateUI(category);
        }
    }

}