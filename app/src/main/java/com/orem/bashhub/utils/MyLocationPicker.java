package com.orem.bashhub.utils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.os.Handler;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;

import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.CommonStatusCodes;
import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.android.gms.location.SettingsClient;
import com.google.android.gms.tasks.Task;
import com.orem.bashhub.R;
import com.orem.bashhub.activity.BaseActivity;
import com.orem.bashhub.utils.apiinterface.Events;

import org.greenrobot.eventbus.EventBus;

import java.util.Objects;

import static android.app.Activity.RESULT_OK;
import static android.content.Context.LOCATION_SERVICE;

public class MyLocationPicker {

    private static int REQUEST_CODE_LOCATION = 8001;
    private static LocationManager locationManager;

    public static void getCurrentLocation(Context mContext) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(mContext, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                ((Activity) mContext).requestPermissions(new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_CODE_LOCATION);
            } else {
                checkLocationSettings(mContext);
            }
        } else {
            checkLocationSettings(mContext);
        }
    }

    public static void onRequestPermissionsResult(Context mContext, int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == REQUEST_CODE_LOCATION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                checkLocationSettings(mContext);
            } else {
                Utils.showToast(mContext, mContext.getString(R.string.location_permission_required));
                Events.showMessage(mContext, mContext.getString(R.string.location_permission_required), true, true);
            }
        }
    }

    @SuppressLint("MissingPermission")
    private static void checkLocationSettings(final Context mContext) {
        locationManager = (LocationManager) mContext.getSystemService(LOCATION_SERVICE);
        boolean isNetworkEnabled = Objects.requireNonNull(locationManager).isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        boolean isGpsEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        if (!isGpsEnabled && !isNetworkEnabled) {
            showSettingPopup(mContext);
        } else {
            getLocation(mContext);
        }
    }

    private static void showSettingPopup(Context mContext) {
        LocationRequest mLocationRequest = new LocationRequest();
        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder()
                .addLocationRequest(mLocationRequest);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        SettingsClient client = LocationServices.getSettingsClient(mContext);
        Task<LocationSettingsResponse> task = client.checkLocationSettings(builder.build());
        task.addOnSuccessListener((Activity) mContext, locationSettingsResponse -> getLocation(mContext));
        task.addOnFailureListener((Activity) mContext, e -> {
            int statusCode = ((ApiException) e).getStatusCode();
            switch (statusCode) {
                case CommonStatusCodes.RESOLUTION_REQUIRED:
                    try {
                        ResolvableApiException resolvable = (ResolvableApiException) e;
                        resolvable.startResolutionForResult((Activity) mContext, REQUEST_CODE_LOCATION);
                    } catch (Exception ex) {
                        Events.showMessage(mContext, "Error : " + ex.getMessage(), true, true);
                    }
                    break;
                case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                    Utils.showToast(mContext, mContext.getString(R.string.location_setting_unavailable));
                    Events.showMessage(mContext, mContext.getString(R.string.location_setting_unavailable), true, true);
                    break;
            }
        });
    }

    public static void onActivityResult(Context mContext, int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CODE_LOCATION) {
            if (resultCode == RESULT_OK) {
                getLocation(mContext);
            } else {
                Utils.showToast(mContext, mContext.getString(R.string.turn_on_gps));
                Events.showMessage(mContext, mContext.getString(R.string.turn_on_gps), true, true);
            }
        }
    }

    @SuppressLint("MissingPermission")
    private static void getLocation(final Context mContext) {
        try {
            ((BaseActivity) mContext).dialog.show();
            new Handler().postDelayed(() -> {
                ((BaseActivity) mContext).dialog.dismiss();
                Location loc = locationManager.getLastKnownLocation(LocationManager.PASSIVE_PROVIDER);
                if (loc != null) {
                    EventBus.getDefault().post(new Events.LocationPickerResult(loc.getLatitude(), loc.getLongitude()));
                } else {
                    Utils.showToast(mContext, mContext.getString(R.string.failed_to_get_location));
                    Events.showMessage(mContext, mContext.getString(R.string.failed_to_get_location), true, true);
                }
            }, 2000);
        } catch (Exception e) {
            Utils.showLog("Exp : " + e.getMessage());
        }

    }
}
