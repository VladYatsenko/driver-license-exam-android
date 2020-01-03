package com.android.testdai.ui.dialogs;

import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AlertDialog;

import com.android.testdai.R;

public class DialogImage extends DialogFragment {

    private static final String ARG_IMAGE = "imageSource";

    public static DialogImage newInstance(String source){
        Bundle args = new Bundle();
        args.putSerializable(ARG_IMAGE, source);

        DialogImage fragment = new DialogImage();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        super.onCreateDialog(savedInstanceState);

        String source = (String) getArguments().getSerializable(ARG_IMAGE);

        View v = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_image, null);


        ImageView mImageView = (ImageView) v.findViewById(R.id.imageQuestion);
//        Picasso.get()
//                .load(source)
//                .placeholder(R.drawable.empty)
//                .into(mImageView);
        mImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getDialog().dismiss();
            }
        });


        return new AlertDialog.Builder(getActivity())
                .setView(v)
                .create();
    }

}
