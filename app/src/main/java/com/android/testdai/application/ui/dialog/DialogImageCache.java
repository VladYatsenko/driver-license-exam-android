package com.android.testdai.application.ui.dialog;

import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.testdai.R;
import com.android.testdai.application.db.DaiRepository;
import com.android.testdai.application.model.Question;
import com.android.testdai.util.AnalyticUtil;
import com.android.testdai.util.PreferencesUtil;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Action;
import io.reactivex.schedulers.Schedulers;

public class DialogImageCache extends DialogFragment {

    private static final String ARG_ACTION = "action";
    private ImageView imageView;
    private TextView textProgress;
    boolean action;
    private List<Question> sourceList;

    public static DialogImageCache newInstance(boolean action){
        Bundle args = new Bundle();
        args.putSerializable(ARG_ACTION, action);

        DialogImageCache fragment = new DialogImageCache();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        super.onCreateDialog(savedInstanceState);

        action = (boolean) getArguments().getSerializable(ARG_ACTION);

        View v = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_cache, null);
        imageView = (ImageView) v.findViewById(R.id.imageView);
        textProgress = (TextView) v.findViewById(R.id.text_progress);

        sourceList = DaiRepository.get(getActivity()).getQuestionsWithImage();

        loadImage(0);

        /*Completable.fromAction(new Action() {
            @Override
            public void run() throws Exception {
            }
        }).subscribeOn(Schedulers.io()).andThen().subscribe();

*/
        return new AlertDialog.Builder(getActivity())
                .setView(v)
                .create();

    }

    private Completable loadImages(int imageIndex) {

        textProgress.setText(imageIndex+"/"+sourceList.size());


        return Completable.fromAction(() -> {
            if(action) {
                Picasso.get().load(sourceList.get(imageIndex).getImageSource()).into(imageView, new Callback() {
                    @Override
                    public void onSuccess() {
                        if (imageIndex < sourceList.size())
                            loadImage(imageIndex + 1);
                        else getDialog().dismiss();
                    }

                    @Override
                    public void onError(Exception e) {
                        getDialog().dismiss();
                    }
                });
            }
            else {
                Picasso.get().invalidate(sourceList.get(imageIndex).getImageSource());
                loadImage(imageIndex + 1);
            }
        }).subscribeOn(AndroidSchedulers.mainThread());

    }

    private void loadImage(int imageIndex) {

        textProgress.setText(imageIndex + "/" + sourceList.size());
        if (imageIndex != sourceList.size()) {

            if (action) {
                Picasso.get().load(sourceList.get(imageIndex).getImageSource()).into(imageView, new Callback() {
                    @Override
                    public void onSuccess() {
                        if (imageIndex < sourceList.size() || imageIndex != sourceList.size())
                            loadImage(imageIndex + 1);
                        else getDialog().dismiss();
                    }

                    @Override
                    public void onError(Exception e) {
                        getDialog().dismiss();
                    }
                });
            } else {
                Picasso.get().invalidate(sourceList.get(imageIndex).getImageSource());
                if (imageIndex < sourceList.size())
                    loadImage(imageIndex + 1);
                else getDialog().dismiss();
            }

        }
        else getDialog().dismiss();
    }


}
