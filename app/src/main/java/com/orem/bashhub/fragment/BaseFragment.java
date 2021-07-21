package com.orem.bashhub.fragment;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProviders;

import com.orem.bashhub.R;
import com.orem.bashhub.activity.BaseActivity;
import com.orem.bashhub.utils.LiveDataModel;
import com.orem.bashhub.utils.MyImagePicker;
import com.orem.bashhub.utils.MyPermissionChecker;
import com.orem.bashhub.utils.TinyDB;
import com.wang.avi.AVLoadingIndicatorView;

import org.greenrobot.eventbus.EventBus;

import java.util.Objects;


public class BaseFragment extends Fragment implements View.OnClickListener {

  public     Context mContext;
    public TinyDB mTinyDb;
    public BaseActivity baseActivity;
    public LiveDataModel mLiveModel;
    public Dialog dialog;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getActivity();
        mTinyDb = new TinyDB(mContext);
        baseActivity = (BaseActivity) getActivity();
        mLiveModel = ViewModelProviders.of((FragmentActivity) mContext).get(LiveDataModel.class);
        progressDialog();
    }

    private void progressDialog() {
        dialog = new Dialog(Objects.requireNonNull(getActivity()));
        View view = View.inflate(mContext, R.layout.progress_dialog, null);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(view);
        Objects.requireNonNull(dialog.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setCancelable(false);
        AVLoadingIndicatorView loader = dialog.findViewById(R.id.loader);
        loader.show();
    }

    @Override
    public void onClick(View v) {

    }

    public void showToast(String message) {
        Toast.makeText(mContext, message, Toast.LENGTH_SHORT).show();
    }

    public void startNewActivity(Class<?> activity, boolean isFinishAll, boolean isCurrentFinish) {
        Intent i = new Intent(mContext, activity);
        if (isFinishAll)
            i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(i);
        if (isCurrentFinish) ((Activity) mContext).finish();
    }

    @Override
    public void onDestroy() {
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
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        MyPermissionChecker.onRequestPermissionsResult(mContext, requestCode, permissions, grantResults);
        MyImagePicker.onRequestPermissionsResult(mContext, requestCode, permissions, grantResults);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        MyImagePicker.onActivityResult(mContext, requestCode, resultCode, data);
    }
}
