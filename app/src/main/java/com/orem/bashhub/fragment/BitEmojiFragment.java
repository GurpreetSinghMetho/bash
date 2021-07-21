package com.orem.bashhub.fragment;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.orem.bashhub.R;
import com.orem.bashhub.databinding.FragmentBitEmojiBinding;
import com.orem.bashhub.utils.apiinterface.Events;
import com.snapchat.kit.sdk.bitmoji.ui.BitmojiFragment;

import org.greenrobot.eventbus.Subscribe;

import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 */
public class BitEmojiFragment extends BaseFragment {

    FragmentBitEmojiBinding binding;

    public BitEmojiFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_bit_emoji, container, false);
        ini();
        return binding.getRoot();
    }

    private void ini() {
        binding.backIV.setOnClickListener(this);
        getChildFragmentManager().beginTransaction().replace(R.id.emojiContainer, new BitmojiFragment()).commit();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.backIV:
                Objects.requireNonNull(getActivity()).onBackPressed();
                break;
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        registerEventBus();
    }

    @Subscribe
    public void getEmojiRes(Events.GetEmojiData res) {
        binding.backIV.performClick();
    }
}
