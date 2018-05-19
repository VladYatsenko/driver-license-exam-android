package com.android.testdai;

import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

public class DialogImage extends DialogFragment {

    private ImageView mImageView;

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

        View v = LayoutInflater.from(getActivity())
                .inflate(R.layout.dialog_image, null);

        mImageView = (ImageView) v.findViewById(R.id.imageQuestion);
        Picasso.get()
                .load(source)
                .placeholder(R.drawable.empty)
                .into(mImageView);


        return new AlertDialog.Builder(getActivity())
                .setView(v)
                .create();
    }

    private void sendResult(int resultCode){
        Intent intent = new Intent();

        ((MainActivity)getActivity()).onActivityResult(4,resultCode,intent);
    }


}
