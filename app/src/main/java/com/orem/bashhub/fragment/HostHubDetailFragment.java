package com.orem.bashhub.fragment;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.orem.bashhub.R;
import com.orem.bashhub.adapter.AgeRangeAdapter;
import com.orem.bashhub.adapter.TimeRangeAdapter;
import com.orem.bashhub.data.BashDetailsPOJO;
import com.orem.bashhub.data.HostHubDetailPOJO;
import com.orem.bashhub.databinding.FragmentHostHubDetailBinding;
import com.orem.bashhub.utils.Const;
import com.orem.bashhub.utils.Utils;
import com.orem.bashhub.utils.apiinterface.Events;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 */
@SuppressWarnings("unused")
public class HostHubDetailFragment extends BaseFragment {

    private FragmentHostHubDetailBinding binding;
    private BashDetailsPOJO data;

    public HostHubDetailFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_host_hub_detail, container, false);
        ini();
        apiHostHubDetail();
        return binding.getRoot();
    }

    private void ini() {
        binding.setBash(data);
        binding.backIV.setOnClickListener(v -> Objects.requireNonNull(getActivity()).onBackPressed());
        Utils.underlineTextView(binding.tvAge);
        Utils.underlineTextView(binding.tvTime);
    }

    public void setData(BashDetailsPOJO data) {
        this.data = data;
    }

    private void setHostData(HostHubDetailPOJO.Data data) {
        binding.setData(data);
        binding.rvAge.setAdapter(new AgeRangeAdapter(mContext, data));
        binding.rvTime.setAdapter(new TimeRangeAdapter(mContext, data));
        binding.scrollView.setVisibility(View.VISIBLE);
    }

    private void apiHostHubDetail() {
        registerEventBus();
        EventBus.getDefault().post(new Events.RequestHostHubDetail(mContext, Const.apiHostHubDetail(mContext, data.id), true, false));
    }

    @Subscribe
    public void apiHostHubDetailRes(Events.GetHostHubDetailData res) {
        unRegisterEventBus();
        setHostData(res.getData().data);
    }
}
