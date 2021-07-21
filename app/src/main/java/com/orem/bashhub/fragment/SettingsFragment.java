package com.orem.bashhub.fragment;


import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;

import com.orem.bashhub.R;
import com.orem.bashhub.adapter.LocationUsersAdapter;
import com.orem.bashhub.data.UserPOJO;
import com.orem.bashhub.data.UsersListPOJO;
import com.orem.bashhub.databinding.FragmentSettingsBinding;
import com.orem.bashhub.utils.Const;
import com.orem.bashhub.utils.Utils;
import com.orem.bashhub.utils.apiinterface.Events;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 */
@SuppressWarnings("unused")
public class SettingsFragment extends BaseFragment {

    private FragmentSettingsBinding binding;
    private List<UsersListPOJO.Data> list = new ArrayList<>();
    private LocationUsersAdapter adapter;
    private UsersListPOJO userList = null;
    private boolean isBlock = false;

    public SettingsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_settings, container, false);
        ini();
        apiGetUsers();
        return binding.getRoot();
    }

    private void ini() {
        final Observer<UserPOJO.Data> observer = data -> binding.setData(data);
        mLiveModel.getUserLiveData().observe((LifecycleOwner) mContext, observer);
        binding.backIV.setOnClickListener(v -> Objects.requireNonNull(getActivity()).onBackPressed());
        adapter = new LocationUsersAdapter(mContext, list);
        binding.rvUsers.setAdapter(adapter);
        binding.ivSwitch.setOnClickListener(view -> apiUpdateSetting(Const.getLoggedInUser(mContext).location.equals(Const.ZERO) ? Const.ONE : Const.ZERO, ""));
        binding.btUpdate.setOnClickListener(view -> apiUpdateSetting(Const.TWO, userList.getBlockedUser()));

        binding.etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                filter(editable.toString().toLowerCase());
            }
        });
    }

    private void filter(String text) {
        List<UsersListPOJO.Data> filteredList = new ArrayList<>();
        if (text.isEmpty()) {
            filteredList.addAll(list);
        } else {
            for (UsersListPOJO.Data item : list) {
                if (item.username.toLowerCase().contains(text) || item.fname.toLowerCase().contains(text) ||
                        item.lname.toLowerCase().contains(text) || item.phone_number.toLowerCase().contains(text))
                    filteredList.add(item);
            }
        }
        adapter.submitList(filteredList);
    }

    private void apiGetUsers() {
        registerEventBus();
        EventBus.getDefault().post(new Events.RequestUsersList(mContext, Const.apiLocationUser(mContext), true, false));
    }

    private void apiUpdateSetting(String location, String blockUser) {
        isBlock = location.equals(Const.TWO);
        registerEventBus();
        EventBus.getDefault().post(new Events.RequestUser(mContext, Const.apiUpdateSetting(mContext,
                location, blockUser), true, false));
    }

    @Subscribe
    public void apiGetUsersRes(Events.GetUsersListData res) {
        unRegisterEventBus();
        userList = res.getData();
        list.clear();
        list.addAll(res.getData().data);
        adapter.notifyDataSetChanged();
        UserPOJO.Data user = Const.getLoggedInUser(mContext);
        user.setUserList(userList);
        mLiveModel.getUserLiveData().setValue(user);
    }

    @Subscribe
    public void apiUpdateSettingRes(Events.GetUserData res) {
        unRegisterEventBus();
        UserPOJO.Data user = res.getData().data;
        user.setUserList(userList);
        Const.setLoggedInUser(mContext, user);
        mLiveModel.getUserLiveData().setValue(user);
        if (isBlock)
            Utils.showToast(mContext, getString(R.string.users_updated_success));
    }
}
