package com.orem.bashhub.fragment;

import android.Manifest;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.DividerItemDecoration;

import com.orem.bashhub.R;
import com.orem.bashhub.adapter.ListOfPartyMemberAdapter;
import com.orem.bashhub.data.BashDetailsPOJO;
import com.orem.bashhub.data.BashUsersPOJO;
import com.orem.bashhub.databinding.FragmentPartyMemberListBinding;
import com.orem.bashhub.interfaces.OnScanTicket;
import com.orem.bashhub.utils.Const;
import com.orem.bashhub.utils.MyPermissionChecker;
import com.orem.bashhub.utils.Utils;
import com.orem.bashhub.utils.apiinterface.Events;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class ListOfMemberFragment extends BaseFragment {

    private FragmentPartyMemberListBinding binding;
    private List<BashUsersPOJO.Data> users = new ArrayList<>();
    private List<BashUsersPOJO.Data> filteredList = new ArrayList<>();
    private BashDetailsPOJO data;
    private ListOfPartyMemberAdapter adapter;
    private boolean isLive;
    private OnScanTicket listener = () -> apiBashUsers(false);

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_party_member_list, container, false);
        init();
        apiBashUsers(true);
        MyPermissionChecker.getPermission(mContext, new String[]{Manifest.permission.CAMERA});
        return binding.getRoot();
    }

    private void init() {
        binding.setData(data);
        binding.backIV.setOnClickListener(this);
        binding.btScan.setOnClickListener(this);
        binding.ivSearch.setOnClickListener(this);
        binding.ivClose.setOnClickListener(this);

        binding.fastScrollerRecycler.setIndexTextSize((int) mContext.getResources().getDimension(R.dimen._6sdp));
        binding.fastScrollerRecycler.setIndexBarColor("#717171");
        binding.fastScrollerRecycler.setIndexBarCornerRadius(0);
        binding.fastScrollerRecycler.setIndexBarTransparentValue((float) 0.4);
        binding.fastScrollerRecycler.setIndexbarMargin(5);
        binding.fastScrollerRecycler.setIndexbarWidth(mContext.getResources().getDimension(R.dimen._14sdp));
        binding.fastScrollerRecycler.setPreviewPadding(15);
        binding.fastScrollerRecycler.setIndexBarTextColor("#000000");
        binding.fastScrollerRecycler.setPreviewVisibility(true);
        binding.fastScrollerRecycler.setIndexBarVisibility(true);
        binding.fastScrollerRecycler.setIndexbarHighLateTextColor("#862578");
        binding.fastScrollerRecycler.setIndexBarHighLateTextVisibility(true);

        adapter = new ListOfPartyMemberAdapter(mContext, filteredList);
        binding.fastScrollerRecycler.addItemDecoration(new DividerItemDecoration(mContext, DividerItemDecoration.VERTICAL));
        binding.fastScrollerRecycler.setAdapter(adapter);

        binding.etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                filter(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private void filter(String text) {
        filteredList.clear();
        if (text.isEmpty()) {
            filteredList.addAll(users);
        } else {
            for (BashUsersPOJO.Data item : users) {
                if (item.user_data.username.toLowerCase().contains(text) || item.user_data.fname.toLowerCase().contains(text) ||
                        item.user_data.lname.toLowerCase().contains(text))
                    filteredList.add(item);
            }
        }
        setAdapter();
    }

    public void setData(BashDetailsPOJO data, boolean isLive) {
        this.data = data;
        this.isLive = isLive;
    }

    private void setAdapter() {
        String headTitle = "";
        for (int i = 0; i < filteredList.size(); i++) {
            BashUsersPOJO.User_data item = filteredList.get(i).user_data;
            String text = String.valueOf(item.fname.charAt(0)).toUpperCase();
            if (headTitle.equalsIgnoreCase(text)) {
                item.setHeadVisible(false);
            } else {
                item.setHeadVisible(true);
                headTitle = text;
            }
        }
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.backIV:
                Objects.requireNonNull(getActivity()).onBackPressed();
                break;
            case R.id.btScan:
                ScanTicketFragment fragment = new ScanTicketFragment();
                fragment.setData(data, listener);
                Utils.goToFragment(mContext, fragment, R.id.fragment_container);
                break;
            case R.id.ivSearch:
                binding.ivSearch.setVisibility(View.GONE);
                binding.ivClose.setVisibility(View.VISIBLE);
                binding.etSearch.setVisibility(View.VISIBLE);
                break;
            case R.id.ivClose:
                Utils.hideKeyboard(mContext);
                binding.etSearch.setText("");
                binding.ivClose.setVisibility(View.GONE);
                binding.etSearch.setVisibility(View.GONE);
                binding.ivSearch.setVisibility(View.VISIBLE);
                break;
        }
    }

    private void apiBashUsers(boolean isDialog) {
        registerEventBus();
        EventBus.getDefault().post(new Events.RequestBashUsers(mContext, Const.apiBashUsers(mContext, data.id), isDialog, false));
    }

    @Subscribe
    public void apiBashUsersRes(Events.GetBashUsersData res) {
        unRegisterEventBus();
        users.clear();
        users.addAll(res.getData().data);
        Collections.sort(users, (v1, v2) -> String.valueOf(v1.user_data.fname.charAt(0)).toUpperCase().compareTo(String.valueOf(v2.user_data.fname.charAt(0)).toUpperCase()));
        filteredList.clear();
        filteredList.addAll(users);
        setAdapter();
        if (users.size() > 0) {
            binding.ivSearch.setVisibility(View.VISIBLE);
            binding.fastScrollerRecycler.setVisibility(View.VISIBLE);
            if (Double.parseDouble(data.charge) > 0 && isLive)
                binding.btScan.setVisibility(View.VISIBLE);
        }
    }
}
