package com.android.testdai.util;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;

import java.util.ArrayList;

import static com.android.testdai.util.Constants.NETWORK_PERMISSION_REQUEST;

public class PermissionUtil {

    private static String[] networkPermissions = new String[]{
            Manifest.permission.INTERNET,
            Manifest.permission.ACCESS_NETWORK_STATE
    };

    public static boolean isNetworkGranted(Activity activity) {
        return checkForGrant(activity, networkPermissions, NETWORK_PERMISSION_REQUEST);
    }

    private static boolean checkForGrant(Activity activity, String[] grantStr, int resultCode) {
        if (Build.VERSION.SDK_INT >= 23) {
            ArrayList<String> tmpPermissions = new ArrayList<>();
            for (String permission : grantStr) {
                if (activity.checkSelfPermission(permission) != PackageManager.PERMISSION_GRANTED)
                    tmpPermissions.add(permission);
            }
            if (tmpPermissions.size() > 0) {
                ActivityCompat.requestPermissions(activity, tmpPermissions.toArray(new String[0]), resultCode);
                return false;
            }
            return true;
        } else return true;
    }

}
