package com.orem.bashhub.dialogs;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.DividerItemDecoration;

import com.orem.bashhub.R;
import com.orem.bashhub.adapter.PayMethodAdapter;
import com.orem.bashhub.databinding.DialogPayMethodBinding;
import com.orem.bashhub.interfaces.OnPayMethodPick;

public class DialogPayMethod extends DialogBaseFragment {

    private DialogPayMethodBinding binding;
    private OnPayMethodPick listener;

    public DialogPayMethod(OnPayMethodPick listener) {
        this.listener = listener;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void setupDialog(@NonNull Dialog dialog, int style) {
        super.setupDialog(dialog, style);
        binding = DataBindingUtil.inflate(LayoutInflater.from(mContext), R.layout.dialog_pay_method, null, false);
        View mDialog = binding.getRoot();
        dialog.setContentView(mDialog);
        ((View) mDialog.getParent()).setBackgroundColor(getResources().getColor(android.R.color.transparent));
        init();
    }

    private void init() {
        binding.rvPayMethods.addItemDecoration(new DividerItemDecoration(mContext, DividerItemDecoration.VERTICAL));
        binding.rvPayMethods.setAdapter(new PayMethodAdapter(mContext, listener, this));
    }
}
