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
import androidx.recyclerview.widget.DividerItemDecoration;

import com.orem.bashhub.R;
import com.orem.bashhub.adapter.AddHostAdapter;
import com.orem.bashhub.data.UsersListPOJO;
import com.orem.bashhub.databinding.FragmentAddHostBinding;
import com.orem.bashhub.interfaces.OnAddHost;
import com.orem.bashhub.utils.Const;
import com.orem.bashhub.utils.apiinterface.Events;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;

/**
 * A simple {@link Fragment} subclass.
 */
public class AddHostFragment extends BaseFragment {

    private FragmentAddHostBinding binding;
    private AddHostAdapter adapter;
    private List<UsersListPOJO.Data> list = new ArrayList<>();
    private List<UsersListPOJO.Data> selectedUser;
    private OnAddHost listener;
    private Call<UsersListPOJO> userCall;

    public AddHostFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_add_host, container, false);
        ini();
        apiUsersList(true);
        return binding.getRoot();
    }

    private void ini() {
        binding.ivBack.setOnClickListener(v -> Objects.requireNonNull(getActivity()).onBackPressed());
        adapter = new AddHostAdapter(mContext, list, selectedUser);
        binding.rvFollowers.setAdapter(adapter);
        binding.rvFollowers.addItemDecoration(new DividerItemDecoration(mContext, DividerItemDecoration.VERTICAL));

        binding.etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (!charSequence.toString().isEmpty()) {
                    if (charSequence.length() > 1)
                        apiSearchUser(charSequence.toString().toLowerCase());
                } else apiUsersList(false);
            }

            @Override
            public void afterTextChanged(Editable editable) {
                //filter(editable.toString().toLowerCase());
            }
        });

        binding.btSubmit.setOnClickListener(v -> {
            listener.onAddHost(adapter.getSelectedUser());
            binding.ivBack.performClick();
        });
    }

    public void setData(List<UsersListPOJO.Data> selectedUser, OnAddHost listener) {
        this.selectedUser = new ArrayList<>(selectedUser);
        this.listener = listener;
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

    private void apiUsersList(boolean isDialog) {
        registerEventBus();
        EventBus.getDefault().post(new Events.RequestUsersList(mContext, Const.apiFollowFollowing(mContext), isDialog, false));
    }

    private void apiSearchUser(String text) {
        if (userCall != null && !userCall.isCanceled()) {
            userCall.cancel();
        }
        userCall = Const.apiSearchUserAll(mContext, text);
        registerEventBus();
        EventBus.getDefault().post(new Events.RequestUsersList(mContext, userCall, false, false));
    }

    @Subscribe
    public void apiUsersListRes(Events.GetUsersListData res) {
        list.clear();
        list.addAll(res.getData().data);
        adapter.notifyDataSetChanged();
        binding.etSearch.setVisibility(View.VISIBLE);
        binding.btSubmit.setVisibility(View.VISIBLE);
    }
}
