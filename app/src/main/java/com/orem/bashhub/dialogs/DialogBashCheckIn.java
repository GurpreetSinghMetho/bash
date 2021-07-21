package com.orem.bashhub.dialogs;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.DividerItemDecoration;

import com.orem.bashhub.R;
import com.orem.bashhub.activity.MainActivity;
import com.orem.bashhub.adapter.BashCheckInAdapter;
import com.orem.bashhub.data.BashDetailsPOJO;
import com.orem.bashhub.data.CheckInPOJO;
import com.orem.bashhub.databinding.DialogBashCheckInBinding;
import com.orem.bashhub.fragment.ListOfMemberFragment;
import com.orem.bashhub.utils.Utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class DialogBashCheckIn extends DialogBaseFragment {

    private DialogBashCheckInBinding binding;
    private CheckInPOJO.Data data;

    public DialogBashCheckIn(CheckInPOJO.Data data) {
        this.data = data;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void setupDialog(@NonNull Dialog dialog, int style) {
        super.setupDialog(dialog, style);
        binding = DataBindingUtil.inflate(LayoutInflater.from(mContext), R.layout.dialog_bash_check_in, null, false);
        View mDialog = binding.getRoot();
        dialog.setContentView(mDialog);
        ((View) mDialog.getParent()).setBackgroundColor(getResources().getColor(android.R.color.transparent));
        init();
        binding.tvLive.performClick();
    }


    private void init() {
        Utils.underlineTextView(binding.tvMore);
        binding.listRV.addItemDecoration(new DividerItemDecoration(mContext, DividerItemDecoration.VERTICAL));

        binding.tvLive.setOnClickListener(v -> {
            binding.tvLive.setBackgroundResource(R.drawable.custom_gray_background);
            binding.tvPast.setBackgroundResource(R.drawable.custom_transparent_circle);
            binding.tvLive.setTextColor(ContextCompat.getColor(mContext, R.color.white));
            binding.tvPast.setTextColor(ContextCompat.getColor(mContext, R.color.black));
            BashCheckInAdapter adapter = new BashCheckInAdapter(mContext, data.live, this, true);
            binding.listRV.setAdapter(adapter);
            binding.tvError.setVisibility(data.live.size() > 0 ? View.GONE : View.VISIBLE);
            binding.tvMore.setVisibility(View.GONE);
        });
        binding.tvPast.setOnClickListener(v -> setPastEvents(false));
        binding.tvMore.setOnClickListener(v -> setPastEvents(true));
    }

    private void setPastEvents(boolean isAll) {
        if (data.past.size() > 5 && !isAll) {
            List<BashDetailsPOJO> list = new ArrayList<>();
            for (int i = 0; i < 5; i++)
                list.add(data.past.get(i));
            BashCheckInAdapter adapter = new BashCheckInAdapter(mContext, list, this, false);
            binding.listRV.setAdapter(adapter);
            binding.tvMore.setVisibility(View.VISIBLE);
        } else {
            BashCheckInAdapter adapter = new BashCheckInAdapter(mContext, data.past, this, false);
            binding.listRV.setAdapter(adapter);
            binding.tvMore.setVisibility(View.GONE);
        }
        binding.tvPast.setBackgroundResource(R.drawable.custom_gray_background);
        binding.tvLive.setBackgroundResource(R.drawable.custom_transparent_circle);
        binding.tvPast.setTextColor(ContextCompat.getColor(mContext, R.color.white));
        binding.tvLive.setTextColor(ContextCompat.getColor(mContext, R.color.black));
        binding.tvError.setVisibility(data.past.size() > 0 ? View.GONE : View.VISIBLE);
    }

    public void openFragment(BashDetailsPOJO item, boolean isLive) {
        dismiss();
        ((MainActivity) Objects.requireNonNull(getActivity())).binding.fragmentContainer.setVisibility(View.VISIBLE);
        ListOfMemberFragment fragment = new ListOfMemberFragment();
        fragment.setData(item, isLive);
        Utils.goToFragment(mContext, fragment, R.id.fragment_container);
    }
}
