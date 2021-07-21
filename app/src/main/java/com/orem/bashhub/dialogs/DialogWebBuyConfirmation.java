package com.orem.bashhub.dialogs;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import com.orem.bashhub.R;
import com.orem.bashhub.data.BashDetailsPOJO;
import com.orem.bashhub.databinding.DialogPayConfirmationWebBinding;
import com.orem.bashhub.fragment.ConfirmOrderFragment;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;

public class DialogWebBuyConfirmation extends DialogBaseFragment {

    private DialogPayConfirmationWebBinding binding;
    private BashDetailsPOJO data;
    private ConfirmOrderFragment fragment;
    String total;
    public DialogWebBuyConfirmation(BashDetailsPOJO data, ConfirmOrderFragment fragment,String total) {
        this.total = total;
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
        binding = DataBindingUtil.inflate(LayoutInflater.from(mContext), R.layout.dialog_pay_confirmation_web, null, false);
        View mDialog = binding.getRoot();
        dialog.setContentView(mDialog);
        ((View) mDialog.getParent()).setBackgroundColor(getResources().getColor(android.R.color.transparent));
        init();
    }

    private void init() {
        binding.setData(data);
        binding.mPrice.setText("Total Price $"+total);
        binding.btCancel.setOnClickListener(v -> dismiss());
        binding.btBuy.setOnClickListener(v -> {
            fragment.apiGetToken();
            dismiss();
        });
    }
}