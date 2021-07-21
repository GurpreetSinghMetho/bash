package com.orem.bashhub.dialogs;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import com.orem.bashhub.R;
import com.orem.bashhub.activity.MainActivity;
import com.orem.bashhub.databinding.TicketSuccessDialogboxBinding;

import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.FragmentManager;

public class DialogTicketSuccess extends DialogBaseFragment {
    private TicketSuccessDialogboxBinding binding;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void setupDialog(@NonNull Dialog dialog, int style) {
        super.setupDialog(dialog, style);
        binding = DataBindingUtil.inflate(LayoutInflater.from(mContext), R.layout.ticket_success_dialogbox, null, false);
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
                ((MainActivity) mContext).binding.fragmentContainer.setVisibility(View.GONE);
                FragmentManager fm = getChildFragmentManager();
                for (int i = 0; i < fm.getBackStackEntryCount(); ++i) {
                    fm.popBackStack();
                }
            }
        });
    }
}
