package com.orem.bashhub.fragment;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.orem.bashhub.R;
import com.orem.bashhub.databinding.FragmentReportProblemBinding;
import com.orem.bashhub.utils.Const;
import com.orem.bashhub.utils.Utils;

import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 */
public class ReportProblemFragment extends BaseFragment {

    private FragmentReportProblemBinding binding;

    public ReportProblemFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_report_problem, container, false);
        ini();
        return binding.getRoot();
    }

    private void ini() {
        binding.backIV.setOnClickListener(v -> Objects.requireNonNull(getActivity()).onBackPressed());
        binding.llNotWorking.setOnClickListener(v -> {
            SubmitReportFragment fragment = new SubmitReportFragment();
            fragment.setData(Const.ONE);
            Utils.goToFragment(mContext, fragment, R.id.fragment_container);
        });
        binding.llProblem.setOnClickListener(v -> {
            SubmitReportFragment fragment = new SubmitReportFragment();
            fragment.setData(Const.TWO);
            Utils.goToFragment(mContext, fragment, R.id.fragment_container);
        });
    }
}
