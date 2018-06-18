package com.android.testdai.application.ui.dialog;


import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;


import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.testdai.R;
import com.android.testdai.application.ui.test.TestActivity;

public class DialogResult extends DialogFragment {

    private static final String ARG_RESULT = "result";
    public static final String EXTRA_RESULT =
            "com.example.android.testdai.result";
    private TextView mResultText, mResult;
    private ImageView mResultImage;

    public static DialogResult newInstance(int result){
        Bundle args = new Bundle();
        args.putSerializable(ARG_RESULT, result);

        DialogResult fragment = new DialogResult();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        super.onCreateDialog(savedInstanceState);
        int result = (int) getArguments().getSerializable(ARG_RESULT);

        View v = LayoutInflater.from(getActivity())
                .inflate(R.layout.dialog_result, null);

        mResult = (TextView) v.findViewById(R.id.result);
        mResultImage = (ImageView) v.findViewById(R.id.result_image);
        if (result<18){
            mResult.setText("Іспит не складений");
            mResultImage.setImageResource(R.drawable.cat);
        }else{
            mResult.setText("Іспит складений");
            mResultImage.setImageResource(R.drawable.prize);
        }
        mResultText = (TextView) v.findViewById(R.id.result_text);
        mResultText.setText("Правильних відповідей: "+result + "/20");

        return new AlertDialog.Builder(getActivity())
                .setView(v)
                .setNeutralButton(R.string.restart,
                        new DialogInterface.OnClickListener(){
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                sendResult(1);
                            }
                        }
                )
                .setPositiveButton(android.R.string.ok,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int i) {
                                dialog.cancel();

                            }
                        })
                .create();
    }

    private void sendResult(int resultCode){

        Intent intent = new Intent();
        ((TestActivity)getActivity()).onActivityResult(resultCode, resultCode, intent);

    }

}
