package com.android.testdai.application.ui.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.android.testdai.R;
import com.android.testdai.application.ui.main.abstraction.IMainView;
import com.android.testdai.application.ui.category.DialogCategory;
import com.android.testdai.util.Constants;
import com.android.testdai.util.PermissionUtil;

public class MainActivity extends AppCompatActivity  implements IMainView {

    private Button mCategory;
    private MainPresenter presenter;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        presenter = new MainPresenter(this);

        Button mStartTest = (Button) findViewById(R.id.start_test);
        mStartTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.startTest();
            }
        });

        mCategory = (Button) findViewById(R.id.category);
        mCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.startDialogCategory();
            }
        });

        ImageButton mSettings = (ImageButton) findViewById(R.id.settings);
        mSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.startSettings();
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        presenter.attachView();
    }


    @Override
    public void updateUI(String category) {
        mCategory.setText(category);
    }

    @Override
    public void startDialogCategory(String category) {

        DialogCategory dialog = DialogCategory
                .newInstance(category);
        dialog.show(getFragmentManager(), Constants.DIALOG_CATEGORY);

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        presenter.onActivityResult(requestCode, resultCode, intent);
    }

}
