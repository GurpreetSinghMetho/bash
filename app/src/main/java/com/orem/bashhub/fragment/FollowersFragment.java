package com.orem.bashhub.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;

import com.orem.bashhub.R;
import com.orem.bashhub.adapter.RecentBashAdapter;
import com.orem.bashhub.adapter.ShowComplementAdapter;
import com.orem.bashhub.data.BashDetailsPOJO;
import com.orem.bashhub.data.OtherUserPOJO;
import com.orem.bashhub.databinding.FragmentFollowersBinding;
import com.orem.bashhub.interfaces.OnBgApi;
import com.orem.bashhub.utils.Const;
import com.orem.bashhub.utils.Utils;
import com.orem.bashhub.utils.apiinterface.Events;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class FollowersFragment extends BaseFragment {

    FragmentFollowersBinding binding;
    private String otherID;
    private OtherUserPOJO.Data user;
    private boolean isFollow = false;
    private OnBgApi listener;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_followers, container, false);
        init();
        apiOtherUser();
        return binding.getRoot();
    }

    private void init() {
        Utils.underlineTextView(binding.tvMore);
        binding.backIV.setOnClickListener(this);
        binding.tvFollow.setOnClickListener(this);
        binding.tvMore.setOnClickListener(this);
        binding.tvFollow.setVisibility(Const.getLoggedInUserID(mContext).equals(otherID) ? View.GONE : View.VISIBLE);
        binding.ivSend.setOnClickListener(v -> {
            messageClick(user.country_code + user.phone_number);
        });
    }

    private void messageClick(String numbers) {
        try {
            Intent sendIntent = new Intent(Intent.ACTION_VIEW);
            sendIntent.setType("vnd.android-dir/mms-sms");
            sendIntent.putExtra("sms_body", "");
            sendIntent.putExtra("address", numbers);
            mContext.startActivity(sendIntent);
        } catch (Exception e) {
            Utils.showMessageDialog(mContext, "", mContext.getString(R.string.failed_to_find_msg_app));
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.backIV:
                Objects.requireNonNull(getActivity()).onBackPressed();
                break;
            case R.id.tvFollow:
                apiFollowUser();
                break;
            case R.id.tvMore:
                setBashList(true);
                break;
        }
    }

    public void setData(String otherID) {
        this.otherID = otherID;
    }

    public void setListener(OnBgApi listener) {
        this.listener = listener;
    }

    private void setBashList(boolean isAll) {
        if (user.recent_bash.size() > 0) {
            if (user.recent_bash.size() > 5 && !isAll) {
                List<BashDetailsPOJO> list = new ArrayList<>();
                for (int i = 0; i < 5; i++)
                    list.add(user.recent_bash.get(i));
                binding.rvBashes.setAdapter(new RecentBashAdapter(mContext, list));
                binding.tvMore.setVisibility(View.VISIBLE);
            } else {
                binding.rvBashes.setAdapter(new RecentBashAdapter(mContext, user.recent_bash));
                binding.tvMore.setVisibility(View.GONE);
            }
            binding.rvBashes.setVisibility(View.VISIBLE);
            binding.llRecentBash.setVisibility(View.VISIBLE);
        } else {
            binding.rvBashes.setVisibility(View.GONE);
            binding.llRecentBash.setVisibility(View.GONE);
            binding.tvMore.setVisibility(View.GONE);
        }
    }

    private void apiOtherUser() {
        isFollow = false;
        registerEventBus();
        EventBus.getDefault().post(new Events.RequestOtherUser(mContext, Const.apiOtherUser(mContext, otherID), true, false));
    }

    private void apiFollowUser() {
        isFollow = true;
        registerEventBus();
        EventBus.getDefault().post(new Events.RequestOtherUser(mContext, Const.apiFollowUser(mContext, otherID,
                user.getFollow().equals(Const.ONE) ? Const.ZERO : Const.ONE), true, false));
    }

    @Subscribe
    public void apiOtherUserRes(Events.GetOtherUserData res) {
        unRegisterEventBus();
        user = res.getData().data;
        binding.setData(user);
        binding.rvComplements.setAdapter(new ShowComplementAdapter(mContext, user.getComplementCount(mContext)));
        setBashList(false);
        binding.scrollView.setVisibility(View.VISIBLE);
        if (isFollow && listener != null) listener.onBgApi();
        if (res.getData().data.getFollow().equalsIgnoreCase("1") && res.getData().data.getFollowMe().equalsIgnoreCase("1")) {
            binding.ivSend.setVisibility(View.VISIBLE);
        } else
            binding.ivSend.setVisibility(View.GONE);
        //((MainActivity) mContext).getFbImage();
        //if (isFollow) ((MainActivity) mContext).apiFbFriends();
    }
}
