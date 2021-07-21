package com.orem.bashhub.activity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProviders;

import com.orem.bashhub.R;
import com.orem.bashhub.fragment.SafeRideFragment;
import com.orem.bashhub.utils.LiveDataModel;
import com.orem.bashhub.utils.MyImagePicker;
import com.orem.bashhub.utils.MyLocationPicker;
import com.orem.bashhub.utils.MyPermissionChecker;
import com.orem.bashhub.utils.PrefStore;
import com.orem.bashhub.utils.TinyDB;
import com.wang.avi.AVLoadingIndicatorView;

import org.greenrobot.eventbus.EventBus;

import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class BaseActivity extends AppCompatActivity implements View.OnClickListener {

    public static Context mContext;
    public TinyDB mTinyDB;
    public PrefStore store;
    public Dialog dialog;
    public LiveDataModel mLiveModel;

    public static boolean isValidPassword(final String password) {
        Pattern pattern;
        Matcher matcher;
        final String PASSWORD_PATTERN = "^(?=.*?[a-z])(?=.*?[0-9]).{8,}$";
        pattern = Pattern.compile(PASSWORD_PATTERN);
        matcher = pattern.matcher(password);
        return matcher.matches();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        mTinyDB = new TinyDB(mContext);
        store = new PrefStore(this);
        mLiveModel = ViewModelProviders.of((FragmentActivity) mContext).get(LiveDataModel.class);
        progressDialog();
    }

    @Override
    protected void onStart() {
        super.onStart();
        // setOnline(1);
    }

    private void progressDialog() {
        dialog = new Dialog(this);
        View view = View.inflate(this, R.layout.progress_dialog, null);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(view);
        Objects.requireNonNull(dialog.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setCancelable(false);
        AVLoadingIndicatorView loader = dialog.findViewById(R.id.loader);
        loader.show();
    }

    public void startProgressDialog() {
        if (dialog != null && !dialog.isShowing()) {
            try {
                dialog.show();
            } catch (Exception e) {
            }
        }
    }

    public void stopProgressDialog() {
        if (dialog != null && dialog.isShowing()) {
            try {
                dialog.dismiss();
            } catch (Exception e) {
            }
        }
    }

    @Override
    public void onClick(View v) {

    }

    public void showToast(String message) {
        Handler mHandler = new Handler(Looper.getMainLooper());
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                final Toast toast = Toast.makeText(mContext, message, Toast.LENGTH_LONG);
                toast.show();
                stopProgressDialog();

            }
        }, 10);
    }

    /*@Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }*/

    public void log(String message) {
        Log.e(getString(R.string.app_name), message);
    }

    @Override
    protected void onStop() {
        super.onStop();
    }


   /* public void gotoForgotActivity() {
        Intent intent = new Intent(this, ForgotPasswordActivity.class);
        startActivity(intent);
    }*/

    public void gotoLoginActivity() {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

    public void gotoMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
        finishAffinity();
    }

    public boolean emailValidator(String email) {
        Pattern pattern;
        Matcher matcher;
        final String EMAIL_PATTERN = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
        pattern = Pattern.compile(EMAIL_PATTERN);
        matcher = pattern.matcher(email);
        return matcher.matches();
    }

    public void startNewActivity(Class<?> activity, boolean isFinishAll, boolean isCurrentFinish, Bundle bundle) {
        Intent i = new Intent(mContext, activity);
        if (bundle != null) i.putExtras(bundle);
        if (isFinishAll)
            i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(i);
        if (isCurrentFinish) finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unRegisterEventBus();
    }

    public void registerEventBus() {
        if (!EventBus.getDefault().isRegistered(this))
            EventBus.getDefault().register(this);
    }

    public void unRegisterEventBus() {
        if (EventBus.getDefault().isRegistered(this))
            EventBus.getDefault().unregister(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        MyLocationPicker.onActivityResult(mContext, requestCode, resultCode, data);
        MyImagePicker.onActivityResult(mContext, requestCode, resultCode, data);
        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.fragment_container);
        if (fragment instanceof SafeRideFragment)
            fragment.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        MyLocationPicker.onRequestPermissionsResult(mContext, requestCode, permissions, grantResults);
        MyPermissionChecker.onRequestPermissionsResult(mContext, requestCode, permissions, grantResults);
        MyImagePicker.onRequestPermissionsResult(mContext, requestCode, permissions, grantResults);
    }
}