package com.android.testdai.application.ui.main;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.support.annotation.NonNull;
import android.util.Log;

import com.android.testdai.application.ui.category.DialogCategory;
import com.android.testdai.application.ui.main.abstraction.IMainView;
import com.android.testdai.application.ui.settings.SettingsActivity;
import com.android.testdai.application.ui.test.TestActivity;
import com.android.testdai.util.AnalyticUtil;
import com.android.testdai.util.Constants;
import com.android.testdai.util.PermissionUtil;
import com.android.testdai.util.PreferencesUtil;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import static com.android.testdai.util.Constants.APP_PREFERENCES_CATEGORY;

public class MainPresenter  {

    private final String TAG = "MainPresenter";

    private Context context;
    private Activity activity;
    private IMainView iMainView;
    private String category;
    private FirebaseAuth auth;
    private FirebaseAuth.AuthStateListener authStateListener;

    MainPresenter(Context context, Activity activity){

        this.context = context;
        this.activity = activity;
        iMainView = (IMainView) context;
        category = PreferencesUtil.getInstance(context).getCategory();



    }

    public void attachView() {

        iMainView.updateUI(category);
/*
        auth = FirebaseAuth.getInstance();

        FirebaseUser user = auth.getInstance().getCurrentUser();
        if(user != null){
            Log.i(TAG, "auth2");
        }
        else{
            auth.signInWithEmailAndPassword("yatsenko.dev@gmail.com", "qwertypoiu")
            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        Log.d(TAG, "signInWithEmail:success");
                        FirebaseUser user = auth.getCurrentUser();
                    } else {
                        // If sign in fails, display a message to the user.
                        Log.w(TAG, "signInWithEmail:failure", task.getException());
                    }
                }
            });
            Log.i(TAG, "auth");
        }

        authStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();

            }
        };*/

    }

    public void startTest(){

        if(PermissionUtil.isNetworkGranted(activity) && PermissionUtil.isExternalStorageGranted(activity)){
            Intent intent = TestActivity.newIntent(context);
            context.startActivity(intent);
        }

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
