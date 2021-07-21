package com.orem.bashhub.dialogs;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;

import com.orem.bashhub.R;
import com.orem.bashhub.data.BashDetailsPOJO;
import com.orem.bashhub.databinding.DialogCheckinConfirmationBinding;
import com.orem.bashhub.utils.Const;
import com.orem.bashhub.utils.Utils;
import com.orem.bashhub.utils.apiinterface.Events;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

public class DialogCheckInConfirmation extends DialogBaseFragment {

    private DialogCheckinConfirmationBinding binding;
    private BashDetailsPOJO data;
    private String skip;
    private boolean isCheckIn = false;

    public DialogCheckInConfirmation(BashDetailsPOJO data) {
        this.data = data;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void setupDialog(@NonNull Dialog dialog, int style) {
        super.setupDialog(dialog, style);
        binding = DataBindingUtil.inflate(LayoutInflater.from(mContext), R.layout.dialog_checkin_confirmation, null, false);
        View mDialog = binding.getRoot();
        dialog.setContentView(mDialog);
        ((View) mDialog.getParent()).setBackgroundColor(getResources().getColor(android.R.color.transparent));
        init();
    }

    private void init() {
        binding.setData(data);
        binding.btCancel.setOnClickListener(v -> dismiss());
        binding.btCheckIn.setOnClickListener(v -> {
            skip = Const.ZERO;
            apiCheckIn();
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (!isCheckIn) {
            skip = Const.ONE;
            apiCheckIn();
        }
    }

    private void apiCheckIn() {
        registerEventBus();
        EventBus.getDefault().post(new Events.RequestBasic(mContext, Const.apiFreeCheckIn(mContext, data.id, skip), true, false));
    }

    @Subscribe
    public void apiCheckIn(Events.GetBasicData res) {
        unRegisterEventBus();
        if (skip.equals(Const.ZERO))
            Utils.showToast(mContext, getString(R.string.check_in_success));
        isCheckIn = true;
        dismiss();
    }
}