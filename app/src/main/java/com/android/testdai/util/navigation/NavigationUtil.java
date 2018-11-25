package com.android.testdai.util.navigation;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;

import io.reactivex.Completable;
import io.reactivex.Single;

public class NavigationUtil {

    private static final String INVALID_TARGET = "Invalid target type";

    private Context context;

    public NavigationUtil(Context context) {
        this.context = context;
    }

    private Single<Intent> getIntent(Class targetClass) {

        return Single.create(e -> {

                    if (!Activity.class.isAssignableFrom(targetClass))
                        e.onError(new RuntimeException(INVALID_TARGET));

                    e.onSuccess(new Intent(context, targetClass));

                }

        );

    }

    public Completable toActivity(Class targetClass, boolean clearStack) {

        return getIntent(targetClass)
                .flatMapCompletable(

                        intent -> Completable.fromAction(

                                () -> {

                                    if (clearStack)
                                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                    else
                                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                                    context.startActivity(intent);

                                }

                        )

                );

    }

    public Completable toActivity(Class targetClass) {
        return toActivity(targetClass, true);
    }


    public Completable showDialog(IFragmentContainer container,
                                  DialogFragment dialogFragment) {

        return Completable.fromAction(

                () -> {

                    FragmentManager manager = container.getActualFragmentManager();

                    Fragment prev = manager.findFragmentByTag("dialog");
                    if (prev == null)
                        dialogFragment.show(manager, "dialog");


                }

        );

    }


}
