package com.orem.bashhub.utils;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;

import com.orem.bashhub.R;
import com.orem.bashhub.utils.apiinterface.Events;

import org.greenrobot.eventbus.EventBus;

public class MyPermissionChecker {

    private static int REQUEST_CODE_PERMISSION = 9001;

    public static void getPermission(Context mContext, String[] permissions) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (!isPermissionsGranted(mContext, permissions)) {
                ((Activity) mContext).requestPermissions(permissions, REQUEST_CODE_PERMISSION);
            } else {
                sendPermissionResult();
            }
        } else {
            sendPermissionResult();
        }
    }

    private static boolean isPermissionsGranted(Context mContext, String[] permissions) {
        boolean isGranted = true;
        for (String permission : permissions) {
            if (ContextCompat.checkSelfPermission(mContext, permission) != PackageManager.PERMISSION_GRANTED) {
                isGranted = false;
                break;
            }
        }
        return isGranted;
    }

    public static void onRequestPermissionsResult(Context mContext, int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == REQUEST_CODE_PERMISSION) {
            if (grantResults.length > 0) {
                boolean isGranted = true;
                for (int result : grantResults) {
                    if (result != PackageManager.PERMISSION_GRANTED) {
                        isGranted = false;
                        break;
                    }
                }
                if (isGranted)
                    sendPermissionResult();
                else
                    Utils.showToast(mContext, mContext.getString(R.string.permission_not_granted_message));
            } else {
                Utils.showToast(mContext, mContext.getString(R.string.permission_not_granted_message));
            }
        }
    }

    private static void sendPermissionResult() {
        EventBus.getDefault().post(new Events.PermissionCheckerResult(true));
    }
}
