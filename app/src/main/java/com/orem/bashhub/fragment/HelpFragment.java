package com.orem.bashhub.fragment;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.orem.bashhub.R;
import com.orem.bashhub.databinding.FragmentHelpBinding;
import com.orem.bashhub.utils.Utils;

import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 */
public class HelpFragment extends BaseFragment {

    private FragmentHelpBinding binding;

    public HelpFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_help, container, false);
        ini();
        return binding.getRoot();
    }

    private void ini() {
        binding.backIV.setOnClickListener(v -> Objects.requireNonNull(getActivity()).onBackPressed());
        binding.llReport.setOnClickListener(v -> Utils.goToFragment(mContext, new ReportProblemFragment(), R.id.fragment_container));
        binding.llFaq.setOnClickListener(v -> Utils.goToFragment(mContext, new FaqFragment(), R.id.fragment_container));
    }
}
