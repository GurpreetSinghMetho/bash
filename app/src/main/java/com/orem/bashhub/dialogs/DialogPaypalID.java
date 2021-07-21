package com.orem.bashhub.dialogs;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;

import com.orem.bashhub.R;
import com.orem.bashhub.databinding.DialogPaypalIdBinding;
import com.orem.bashhub.fragment.PaymentFragment;
import com.orem.bashhub.utils.Const;
import com.orem.bashhub.utils.Utils;

public class DialogPaypalID extends DialogBaseFragment {

    private DialogPaypalIdBinding binding;
    private PaymentFragment fragment;

    public DialogPaypalID(PaymentFragment fragment) {
        this.fragment = fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void setupDialog(@NonNull Dialog dialog, int style) {
        super.setupDialog(dialog, style);
        binding = DataBindingUtil.inflate(LayoutInflater.from(mContext), R.layout.dialog_paypal_id, null, false);
        View mDialog = binding.getRoot();
        dialog.setContentView(mDialog);
        ((View) mDialog.getParent()).setBackgroundColor(getResources().getColor(android.R.color.transparent));
        init();
    }

    private void init() {
        binding.etName.setText(Const.getLoggedInUser(mContext).paypal_name);
        binding.etEmail.setText(Const.getLoggedInUser(mContext).paypal_id);
        binding.btSubmit.setOnClickListener(v -> {
            if (binding.etName.getText().toString().isEmpty()) {
                Utils.showToast(mContext, getString(R.string.enter_paypal_name));
            } else if (binding.etEmail.getText().toString().isEmpty())
                Utils.showToast(mContext, getString(R.string.enter_paypal_email));
            else {
                fragment.apiUpdatePaypal(binding.etEmail.getText().toString(), binding.etName.getText().toString());
                dismiss();
            }
        });
    }
}
