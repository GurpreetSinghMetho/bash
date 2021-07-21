package com.orem.bashhub.fragment;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.orem.bashhub.R;
import com.orem.bashhub.activity.MainActivity;
import com.orem.bashhub.databinding.FragmentSubmitReportBinding;
import com.orem.bashhub.utils.Const;
import com.orem.bashhub.utils.MyImagePicker;
import com.orem.bashhub.utils.Utils;
import com.orem.bashhub.utils.apiinterface.Events;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 */
public class SubmitReportFragment extends BaseFragment {

    private FragmentSubmitReportBinding binding;
    private String type, path = "";

    public SubmitReportFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_submit_report, container, false);
        ini();
        return binding.getRoot();
    }

    private void ini() {
        binding.backIV.setOnClickListener(v -> Objects.requireNonNull(getActivity()).onBackPressed());
        binding.ivAttachment.setOnClickListener(v -> {
            registerEventBus();
            MyImagePicker.selectImage(mContext, true, "");
        });
        binding.btSend.setOnClickListener(v -> {
            if (binding.etIssue.getText().toString().isEmpty())
                Utils.showToast(mContext, getString(R.string.enter_issue_exp));
            else apiSubmitReport();
        });
    }

    public void setData(String type) {
        this.type = type;
    }

    private void apiSubmitReport() {
        registerEventBus();
        EventBus.getDefault().post(new Events.RequestBasic(mContext, Const.apiSubmitReport(mContext,
                getString(type.equals(Const.ONE) ? R.string.something_not_working : R.string.problem_with_event),
                binding.etIssue.getText().toString(), path), true, false));
    }

    @Subscribe
    public void getImagePath(Events.ImagePickerResult res) {
        unRegisterEventBus();
        path = res.getPath();
        binding.tvPickImage.setVisibility(View.GONE);
        Utils.loadImage(mContext, path, binding.ivAttachment, 0);
    }

    @Subscribe
    public void apiSubmitReportRes(Events.GetBasicData res) {
        unRegisterEventBus();
        Utils.showMessageDialog(mContext, "", getString(R.string.report_success_msg), (dialog1, which) -> ((MainActivity) mContext).clearAllFragments());
    }
}
