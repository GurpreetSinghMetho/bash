package com.orem.bashhub.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.RecyclerView;

import com.orem.bashhub.R;
import com.orem.bashhub.adapter.FollowersAdapter;
import com.orem.bashhub.data.UsersListPOJO;
import com.orem.bashhub.databinding.FragmentInviteFriendsBinding;
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
public class InviteFriendsFragment extends BaseFragment {

    private FragmentInviteFriendsBinding binding;
    private List<UsersListPOJO.Data> list = new ArrayList<>();
    private FollowersAdapter adapter;
    private String shareContent, bash_id;

    public InviteFriendsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_invite_friends, container, false);
        ini();
        apiGetFollowers();
        return binding.getRoot();
    }

    private void ini() {
        binding.ivBack.setOnClickListener(this);
        binding.ivMessage.setOnClickListener(this);
        binding.ivMessenger.setOnClickListener(this);
        binding.btSend.setOnClickListener(this);
        binding.llMessenger.setOnClickListener(this);
        binding.llMessage.setOnClickListener(this);

        adapter = new FollowersAdapter(mContext, list);
        binding.rvFollowers.addItemDecoration(new DividerItemDecoration(mContext, RecyclerView.VERTICAL));
        binding.rvFollowers.setAdapter(adapter);
    }

    public void setData(String shareContent, String bash_id) {
        this.shareContent = shareContent;
        this.bash_id = bash_id;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ivBack:
                Objects.requireNonNull(getActivity()).onBackPressed();
                break;
            case R.id.ivMessage:
                messageClick();
                break;
            case R.id.ivMessenger:
                messengerClick();
                break;
            case R.id.btSend:
                if (adapter.getSelected().isEmpty())
                    Utils.showToast(mContext, getString(R.string.select_user_invitation));
                else apiSendInvitations();
                break;
            case R.id.llMessenger:
                binding.ivMessenger.performClick();
                break;
            case R.id.llMessage:
                binding.ivMessage.performClick();
                break;
        }
    }

    private void messageClick() {
        try {
            Intent sendIntent = new Intent(Intent.ACTION_VIEW);
            sendIntent.setType("vnd.android-dir/mms-sms");
            sendIntent.putExtra("sms_body", shareContent);
            startActivity(sendIntent);
        } catch (Exception e) {
            Utils.showMessageDialog(mContext, "", getString(R.string.failed_to_find_msg_app));
        }
    }

    private void messengerClick() {
        try {
            Intent share = new Intent(Intent.ACTION_SEND);
            share.putExtra(Intent.EXTRA_TEXT, shareContent);
            share.setType("text/plain");
            share.setPackage("com.facebook.orca");
            startActivity(share);
        } catch (Exception e) {
            Utils.showMessageDialog(mContext, "", getString(R.string.install_messenger));
        }
    }

    private void apiGetFollowers() {
        registerEventBus();
        EventBus.getDefault().post(new Events.RequestUsersList(mContext, Const.apiGetFollowers(mContext, bash_id), true, false));
    }

    private void apiSendInvitations() {
        registerEventBus();
        EventBus.getDefault().post(new Events.RequestBasic(mContext, Const.apiSendInvitations(mContext, adapter.getSelected(),
                bash_id), true, false));
    }

    @Subscribe
    public void apiGetFollowersRes(Events.GetUsersListData res) {
        unRegisterEventBus();
        if (res.getData().data.size() > 0) {
            list.addAll(res.getData().data);
            adapter.notifyDataSetChanged();
            binding.group.setVisibility(View.VISIBLE);
        } else {
            binding.llSocial.setVisibility(View.VISIBLE);
        }
    }

    @Subscribe
    public void apiSendInvitationsApi(Events.GetBasicData res) {
        unRegisterEventBus();
        Utils.showToast(mContext, getString(R.string.invitation_sent_success));
        adapter.sentInvitation();
    }
}