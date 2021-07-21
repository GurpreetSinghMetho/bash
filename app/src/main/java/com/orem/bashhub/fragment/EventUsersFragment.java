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

import com.orem.bashhub.R;
import com.orem.bashhub.adapter.EventUsersAdapter;
import com.orem.bashhub.data.BashDetailsPOJO;
import com.orem.bashhub.databinding.FragmentEventUsersBinding;
import com.orem.bashhub.interfaces.OnBgApi;
import com.orem.bashhub.utils.Const;
import com.orem.bashhub.utils.Utils;
import com.orem.bashhub.utils.apiinterface.Events;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 */
public class EventUsersFragment extends BaseFragment {

    private FragmentEventUsersBinding binding;
    private BashDetailsPOJO bashData;
    private OnBgApi listener = () -> apiGetUsers(false);
    private String numbers = "";

    public EventUsersFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_event_users, container, false);
        ini();
        apiGetUsers(true);
        return binding.getRoot();
    }

    private void ini() {
        binding.ivBack.setOnClickListener(view -> Objects.requireNonNull(getActivity()).onBackPressed());
        binding.ivSend.setOnClickListener(view -> messageClick());
        binding.ivSend.setVisibility(View.GONE);
        binding.tvTitle.setText(bashData.name);
        binding.recyclerView.addItemDecoration(new DividerItemDecoration(mContext, DividerItemDecoration.VERTICAL));
    }

    private void messageClick() {
        try {
            Intent sendIntent = new Intent(Intent.ACTION_VIEW);
            sendIntent.setType("vnd.android-dir/mms-sms");
            sendIntent.putExtra("sms_body", "");
            sendIntent.putExtra("address", numbers);
            startActivity(sendIntent);
        } catch (Exception e) {
            Utils.showMessageDialog(mContext, "", getString(R.string.failed_to_find_msg_app));
        }
    }

    public void setData(BashDetailsPOJO bashData) {
        this.bashData = bashData;
    }

    private void apiGetUsers(boolean isDialog) {
        registerEventBus();
        EventBus.getDefault().post(new Events.RequestUsersList(mContext, Const.apiBashUsersList(mContext, bashData.id), isDialog, false));
    }

    @Subscribe
    public void apiGetUsersRes(Events.GetUsersListData res) {
        unRegisterEventBus();
        if (res.getData().data.size() > 0) {
            boolean isPaid = bashData.isCountDropVisible();

            binding.recyclerView.setAdapter(new EventUsersAdapter(mContext, res.getData().data, isPaid, listener, bashData));
            binding.recyclerView.setVisibility(View.VISIBLE);
            if (bashData.isIamHost(Const.getLoggedInUserID(mContext))) {
                binding.ivSend.setVisibility(View.VISIBLE);
                for (int i = 0; i < res.getData().data.size(); i++) {
                    if (!res.getData().data.get(i).id.equals(Const.getLoggedInUserID(mContext)))
                        if (i == (res.getData().data.size() - 1))
                            numbers = numbers + res.getData().data.get(i).country_code + res.getData().data.get(i).phone_number;
                        else
                            numbers = numbers + res.getData().data.get(i).country_code + res.getData().data.get(i).phone_number + ",";
                }
            }
        } else {
            binding.recyclerView.setVisibility(View.GONE);
            binding.tvError.setVisibility(View.VISIBLE);
            binding.ivSend.setVisibility(View.GONE);
        }
    }
}