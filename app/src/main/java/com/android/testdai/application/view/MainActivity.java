package com.android.testdai.application.view;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.android.testdai.R;

public class MainActivity extends AppCompatActivity {

    private Button mStartTest, mCategory;
    private SharedPreferences mSettings;

    private static final int SEND_CATEGORY = 0;
    private static final int REQUEST_CATEGORY = 1;

    private static final int SEND_RATING = 3;
    private static final int REQUEST_SETTING = 4;

    private static final String DIALOG_CATEGORY = "DialogCategory";
    private static final String DIALOG_RATING = "DialogRating";
    private static final String DIALOG_SETTING = "DialogSetting";
    private static final String DIALOG_ABOUT = "DialogAbout";
    public static final String APP_PREFERENCES = "settings";
    public static final String APP_PREFERENCES_CATEGORY = "category";

    public static final String APP_PREFERENCES_TIME = "time";
    public static final String APP_PREFERENCES_ERROR = "error";

    boolean mTime, mError;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        mSettings = getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);

        mTime = mSettings.getBoolean(APP_PREFERENCES_TIME, true);
        mError = mSettings.getBoolean(APP_PREFERENCES_ERROR, true);


        mStartTest = (Button) findViewById(R.id.start_test);
        mStartTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Intent intent = new Intent(MainActivity.this, TestActivity.class);
                Intent intent = TestActivity.newIntent(MainActivity.this, mCategory.getText().toString());
                //intent.putExtra(APP_PREFERENCES_CATEGORY, (Serializable) mCategory);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    startActivity(intent);
                }
            }
        });

        mCategory = (Button) findViewById(R.id.category);
        if (mSettings.contains(APP_PREFERENCES_CATEGORY)) {
            updateUI();
        }
        mCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogCategory dialog = DialogCategory
                        .newInstance(mCategory.getText().toString());
                //dialog.setTargetFragment(dialog, 1);
                dialog.show(getFragmentManager(), DIALOG_CATEGORY);
            }
        });

        //ImageView imageView = (ImageView) findViewById(R.id.menu);
        //imageView.setOnClickListener(viewClickListener);

    }
    /*
     View.OnClickListener viewClickListener = new View.OnClickListener() {
         @Override
         public void onClick(View v) {
             showPopupMenu(v);
         }
     };

     private void showPopupMenu(View v) {
         PopupMenu popupMenu = new PopupMenu(this, v);
         popupMenu.inflate(R.menu.main_menu); // Для Android 4.0

         popupMenu
                 .setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {

                     @Override
                     public boolean onMenuItemClick(MenuItem item) {
                         switch (item.getItemId()) {
                             case R.id.rating:
                                 RatingFragment dialog = RatingFragment
                                     .newInstance(mAppearance, mUseful, mInteresting);
                                 //dialog.setTargetFragment(dialog, 3);
                                 dialog.show(getFragmentManager(), DIALOG_RATING);
                                 return true;
                             case R.id.settings:
                                 SettingFragment dialogSetting = SettingFragment
                                         .newInstance(mTime, mError);
                                 //dialog.setTargetFragment(dialog, 3);
                                 dialogSetting.show(getFragmentManager(), DIALOG_SETTING);
                                 Intent intent = SettingsActivity.newIntent(MainActivity.this);
                                 if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                                     startActivity(intent);
                                 }
                                 return true;
                             case R.id.about:
                                 //AboutFragment dialogAbout = AboutFragment.newInstance();
                                 //dialog.setTargetFragment(dialog, 3);
                                 //dialogAbout.show(getFragmentManager(), DIALOG_ABOUT);
                                 return true;
                             default:
                                 return false;
                         }
                     }
                 });

         popupMenu.setOnDismissListener(new PopupMenu.OnDismissListener() {

             @Override
             public void onDismiss(PopupMenu menu) {
             }
         });
         popupMenu.show();
     }
 */
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        if (requestCode == REQUEST_CATEGORY) {
            String cat = (String) intent
                    .getSerializableExtra(DialogCategory.EXTRA_CATEGORY);
            SharedPreferences.Editor editor = mSettings.edit();
            editor.putString(APP_PREFERENCES_CATEGORY, cat);
            editor.apply();
            updateUI();
        }
        /*if (requestCode == REQUEST_SETTING) {

            mTime = (boolean) intent
                    .getSerializableExtra(SettingFragment.EXTRA_TIME);
            mError = (boolean) intent
                    .getSerializableExtra(SettingFragment.EXTRA_ERROR);
            SharedPreferences.Editor editor = mSettings.edit();

            editor.putBoolean(APP_PREFERENCES_TIME, mTime);
            editor.putBoolean(APP_PREFERENCES_ERROR, mError);
            editor.apply();
            updateUI();
        }*/
    }

    private void updateUI(){
        mCategory.setText(mSettings.getString(APP_PREFERENCES_CATEGORY, "B"));
    }
}
