package com.orem.bashhub.dialogs;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;

import com.orem.bashhub.R;
import com.orem.bashhub.data.BashDetailsPOJO;
import com.orem.bashhub.databinding.DialogPayConfirmationBinding;
import com.orem.bashhub.fragment.BashDetialFragment;

public class DialogBuyConfirmation extends DialogBaseFragment {

    private DialogPayConfirmationBinding binding;
    private BashDetailsPOJO data;
    private BashDetialFragment fragment;

    public DialogBuyConfirmation(BashDetailsPOJO data, BashDetialFragment fragment) {
        this.data = data;
        this.fragment = fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void setupDialog(@NonNull Dialog dialog, int style) {
        super.setupDialog(dialog, style);
        binding = DataBindingUtil.inflate(LayoutInflater.from(mContext), R.layout.dialog_pay_confirmation, null, false);
        View mDialog = binding.getRoot();
        dialog.setContentView(mDialog);
        ((View) mDialog.getParent()).setBackgroundColor(getResources().getColor(android.R.color.transparent));
        init();
    }


    private void init() {
        binding.setData(data);
        binding.btCancel.setOnClickListener(v -> dismiss());
        binding.btBuy.setOnClickListener(v -> {
            fragment.apiGetToken();
            dismiss();
        });
    }
}
