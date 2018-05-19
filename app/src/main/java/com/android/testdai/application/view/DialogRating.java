package com.android.testdai.application.view;



import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.SeekBar;
import android.widget.TextView;

import com.android.testdai.R;

public class DialogRating extends DialogFragment {

    private SeekBar mAppearance, mUseful, mInteresting;
    private TextView mAppearanceText, mUsefulText, mInterestingText;

    private static final String ARG_appearance = "appearance";
    private static final String ARG_useful = "useful";
    private static final String ARG_interesting = "interesting";
    public static final String EXTRA_appearance =
            "com.example.android.testdai.appearance";
    public static final String EXTRA_useful =
            "com.example.android.testdai.useful";
    public static final String EXTRA_interesting =
            "com.example.android.testdai.interesting";


    public static DialogRating newInstance(int appearance, int useful, int interesting){
        Bundle args = new Bundle();
        args.putSerializable(ARG_appearance, appearance);
        args.putSerializable(ARG_useful, useful);
        args.putSerializable(ARG_interesting, interesting);

        DialogRating fragment = new DialogRating();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        super.onCreateDialog(savedInstanceState);

        int appearance = (int) getArguments().getSerializable(ARG_appearance);
        int useful = (int) getArguments().getSerializable(ARG_useful);
        int interesting = (int) getArguments().getSerializable(ARG_interesting);

        View v = LayoutInflater.from(getActivity())
                .inflate(R.layout.dialog_rating, null);

        mAppearanceText = (TextView) v.findViewById(R.id.appearanceText);
        mUsefulText = (TextView) v.findViewById(R.id.usefulText);
        mInterestingText = (TextView) v.findViewById(R.id.interestingText);

        mAppearance = (SeekBar) v.findViewById(R.id.appearance);
        mAppearance.setProgress(appearance);
        mAppearanceText.setText(String.valueOf(appearance));
        mAppearance.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

                mAppearanceText.setText(String.valueOf(progress));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        mUseful = (SeekBar) v.findViewById(R.id.useful);
        mUseful.setProgress(useful);
        mUsefulText.setText(String.valueOf(useful));
        mUseful.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

                mUsefulText.setText(String.valueOf(progress));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        mInteresting = (SeekBar) v.findViewById(R.id.interesting);
        mInteresting.setProgress(interesting);
        mInterestingText.setText(String.valueOf(interesting));
        mInteresting.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

                mInterestingText.setText(String.valueOf(progress));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        return new AlertDialog.Builder(getActivity())
                .setView(v)
                .setTitle("Оцінка")
                .setPositiveButton(android.R.string.ok,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int i) {
                                sendResult(4);
                            }
                        })
                .create();
    }

    private void sendResult(int resultCode){
        Intent intent = new Intent();
        //Log.i("VLAD", "Send progress" +mAppearance.getProgress() +" " + mUseful.getProgress() +" " + mInteresting.getProgress());
        intent.putExtra(EXTRA_appearance, mAppearance.getProgress());
        intent.putExtra(EXTRA_useful, mUseful.getProgress());
        intent.putExtra(EXTRA_interesting, mInteresting.getProgress());
        ((MainActivity)getActivity()).onActivityResult(4,resultCode,intent);
    }


}
