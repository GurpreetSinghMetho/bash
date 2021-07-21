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
import com.orem.bashhub.adapter.ProfileUsersAdapter;
import com.orem.bashhub.databinding.FragmentProfileUsersBinding;
import com.orem.bashhub.interfaces.OnBgApi;
import com.orem.bashhub.utils.Const;
import com.orem.bashhub.utils.apiinterface.Events;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileUsersFragment extends BaseFragment {

    private FragmentProfileUsersBinding binding;
    private String type;
    private OnBgApi pListener, listener = new OnBgApi() {
        @Override
        public void onBgApi() {
            apiGetUsers(false);
            pListener.onBgApi();
        }
    };


    public ProfileUsersFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_profile_users, container, false);
        ini();
        apiGetUsers(true);
        return binding.getRoot();
    }

    private void ini() {
        binding.ivBack.setOnClickListener(view -> Objects.requireNonNull(getActivity()).onBackPressed());
        binding.tvTitle.setText(getString(type.equals(Const.USER_FOLLOWERS) ? R.string.prompt_followers : R.string.prompt_following));
        binding.recyclerView.addItemDecoration(new DividerItemDecoration(mContext, DividerItemDecoration.VERTICAL));
    }

    public void setData(String type, OnBgApi pListener) {
        this.type = type;
        this.pListener = pListener;
    }

    private void apiGetUsers(boolean isDialog) {
        registerEventBus();
        EventBus.getDefault().post(new Events.RequestUsersList(mContext, Const.apiProfileUsersList(mContext, type), isDialog, false));
    }

    @Subscribe
    public void apiGetUsersRes(Events.GetUsersListData res) {
        unRegisterEventBus();
        if (res.getData().data.size() > 0) {
            binding.recyclerView.setAdapter(new ProfileUsersAdapter(mContext, res.getData().data, listener, type));
            binding.recyclerView.setVisibility(View.VISIBLE);
        } else {
            binding.recyclerView.setVisibility(View.GONE);
            binding.tvError.setText(getString(type.equals(Const.USER_FOLLOWERS) ? R.string.no_follower : R.string.no_following));
            binding.tvError.setVisibility(View.VISIBLE);
        }
    }
}
