package com.orem.bashhub.fragment;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;

import com.orem.bashhub.R;
import com.orem.bashhub.adapter.FbEventsAdapter;
import com.orem.bashhub.databinding.FragmentFbEventsBinding;

import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 */
public class FbEventsFragment extends BaseFragment {

    FragmentFbEventsBinding binding;

    public FbEventsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_fb_events, container, false);
        ini();
        return binding.getRoot();
    }

    private void ini() {
        binding.ivBack.setOnClickListener(v -> Objects.requireNonNull(getActivity()).onBackPressed());

        binding.rvEvents.addItemDecoration(new DividerItemDecoration(mContext, DividerItemDecoration.VERTICAL));
        binding.rvEvents.setAdapter(new FbEventsAdapter(mContext));
    }
}
