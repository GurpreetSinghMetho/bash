package com.orem.bashhub.dialogs;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import com.orem.bashhub.R;
import com.orem.bashhub.databinding.RequestSuccessDialogboxBinding;
import com.orem.bashhub.utils.Const;
import com.orem.bashhub.utils.Utils;

import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;

public class DialogRequestSuccess extends DialogBaseFragment {

    private RequestSuccessDialogboxBinding binding;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void setupDialog(@NonNull Dialog dialog, int style) {
        super.setupDialog(dialog, style);
        binding = DataBindingUtil.inflate(LayoutInflater.from(mContext), R.layout.request_success_dialogbox, null, false);
        View mDialog = binding.getRoot();
        dialog.setContentView(mDialog);
        ((View) mDialog.getParent()).setBackgroundColor(getResources().getColor(android.R.color.transparent));
        init();
    }

    private void init() {
        binding.btOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
                Objects.requireNonNull(getActivity()).onBackPressed();
            }
        });
        binding.btMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "https://www.bashbusiness.com/?user_id=" + Const.getLoggedInUserID(mContext) + "&is_app=1&redirect_uri=https://www.bashbusiness.com/user/chat";
                Utils.intentToBrowser(mContext, url);
                dismiss();
                Objects.requireNonNull(getActivity()).onBackPressed();
            }
        });
    }
}