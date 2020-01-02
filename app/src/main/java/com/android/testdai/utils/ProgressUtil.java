package com.android.testdai.utils;


import android.app.ProgressDialog;
import android.content.Context;

import com.android.testdai.R;

public class ProgressUtil {

    private ProgressDialog progressDialog;

    public ProgressUtil(Context context) {

        progressDialog = new ProgressDialog(context, R.style.AlertDialogStyle);
        progressDialog.setMessage(context.getString(R.string.loading));
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);

    }

    public void updateMessage(String message){
        progressDialog.setMessage(message);
    }

    public void showProgress(){
        progressDialog.show();
    }

    public void hideProgress(){
        progressDialog.dismiss();
    }

}

