package com.android.testdai.application.ui.activities.main;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;

import com.android.testdai.R;
import com.android.testdai.application.ui.abstractions.AbstractActivity;
import com.android.testdai.application.ui.activities.main.abstraction.IMainView;
import com.android.testdai.application.ui.activities.settings.SettingsActivity;
import com.android.testdai.application.ui.activities.test.TestActivity;
import com.android.testdai.application.ui.dialogs.category.DialogCategory;
import com.android.testdai.di.DIProvider;
import com.android.testdai.util.AnalyticUtil;
import com.android.testdai.util.Constants;
import com.android.testdai.util.PermissionUtil;

public class MainActivity extends AbstractActivity<MainPresenter> implements IMainView {

    private Button categoryButton;

    @Override
    protected void injectDependencies() {
        DIProvider.getActivitiesComponent().inject(this);
    }

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        AnalyticUtil.getInstance(this).logScreenEvent(getClass().getSimpleName());

        Button startTestButton = findViewById(R.id.start_test);
        startTestButton.setOnClickListener(view -> presenter.startTest());

        categoryButton = findViewById(R.id.category);
        categoryButton.setOnClickListener(v -> presenter.startDialogCategory());

        ImageButton settingsButton = findViewById(R.id.settings);
        settingsButton.setOnClickListener(view -> presenter.startSettings());

    }

    @Override
    protected void onResume() {
        super.onResume();
        presenter.attachView(this);
    }


    @Override
    public void updateCategory(String category) {
        categoryButton.setText(category);
    }

    @Override
    public void startDialogCategory() {

        DialogCategory dialog = new DialogCategory();
                //.newInstance(category);
        dialog.show(getFragmentManager(), Constants.DIALOG_CATEGORY);

    }

    @Override
    public void startTestActivity() {
        if(PermissionUtil.isNetworkGranted(this) && PermissionUtil.isExternalStorageGranted(this)){
            Intent intent = new Intent(this, TestActivity.class);
            startActivity(intent);
        }
    }

    @Override
    public void startSettingActivity() {
        Intent intent = new Intent(this, SettingsActivity.class);
        startActivity(intent);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        presenter.onActivityResult(requestCode, resultCode, intent);
    }

}
