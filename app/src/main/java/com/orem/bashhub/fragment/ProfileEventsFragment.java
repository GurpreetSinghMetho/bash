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
import com.orem.bashhub.activity.MainActivity;
import com.orem.bashhub.adapter.ProfileEventsAdapter;
import com.orem.bashhub.databinding.FragmentProfileEventsBinding;
import com.orem.bashhub.utils.Const;
import com.orem.bashhub.utils.apiinterface.Events;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileEventsFragment extends BaseFragment {

    private FragmentProfileEventsBinding binding;
    private String type;

    public ProfileEventsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_profile_events, container, false);
        ini();
        apiGetEvents();
        return binding.getRoot();
    }

    private void ini() {
        binding.ivBack.setOnClickListener(view -> Objects.requireNonNull(getActivity()).onBackPressed());
        binding.tvTitle.setText(getString(type.equals(Const.EVENT_HOSTED) ? R.string.prompt_hosted : R.string.prompt_attended));
        binding.recyclerView.addItemDecoration(new DividerItemDecoration(mContext, DividerItemDecoration.VERTICAL));
    }

    public void setData(String type) {
        this.type = type;
    }

    private void apiGetEvents() {
        registerEventBus();
        EventBus.getDefault().post(new Events.RequestBashList(mContext, Const.apiProfileEventsList(mContext, type), true, false));
    }

    @Subscribe
    public void aiGetEventsRes(Events.GetBashListData res) {
        unRegisterEventBus();
        if (res.getData().data.size() > 0) {
            binding.recyclerView.setAdapter(new ProfileEventsAdapter(mContext, res.getData().data));
            binding.recyclerView.setVisibility(View.VISIBLE);
        } else {
            binding.recyclerView.setVisibility(View.GONE);
            binding.tvError.setText(getString(type.equals(Const.EVENT_HOSTED) ? R.string.no_hosted_events : R.string.no_attended_event));
            binding.tvError.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        ((MainActivity) mContext).isCalled=true;
        ((MainActivity) mContext).apiGetBash(false);
    }
}
