package com.orem.bashhub.fragment;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.orem.bashhub.R;
import com.orem.bashhub.data.BashDetailsPOJO;
import com.orem.bashhub.databinding.FragmentEnterBashIdBinding;
import com.orem.bashhub.interfaces.OnScanTicket;
import com.orem.bashhub.utils.Const;
import com.orem.bashhub.utils.Utils;
import com.orem.bashhub.utils.apiinterface.Events;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 */
public class EnterBashIdFragment extends BaseFragment {

    private FragmentEnterBashIdBinding binding;
    private OnScanTicket listener;
    private BashDetailsPOJO data;

    public EnterBashIdFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_enter_bash_id, container, false);
        ini();
        return binding.getRoot();
    }

    private void ini() {
        binding.ivBack.setOnClickListener(v -> Objects.requireNonNull(getActivity()).onBackPressed());
        binding.ivScan.setOnClickListener(v -> {
            binding.ivBack.performClick();
            Utils.goToFragment(mContext, new ScanTicketFragment(), R.id.fragment_container);
        });
        binding.tvScan.setOnClickListener(v -> binding.ivScan.performClick());
        binding.btSubmit.setOnClickListener(v -> {
            if (binding.etCode.getText().toString().isEmpty())
                Utils.showToast(mContext, getString(R.string.enter_ticket_code));
            else apiScanTicket(binding.etCode.getText().toString());
        });
    }

    public void setData(BashDetailsPOJO data, OnScanTicket listener) {
        this.listener = listener;
        this.data = data;
    }

    private void apiScanTicket(String code) {
        registerEventBus();
        EventBus.getDefault().post(new Events.RequestBasic(mContext, Const.apiScanTicket(mContext, data.id, code), true, false));
    }

    @Subscribe
    public void apiScanTicketRes(Events.GetBasicData res) {
        unRegisterEventBus();
        if (res.getData().mesg.equals(getString(R.string.bar_code_error)) ||
                res.getData().mesg.equals(getString(R.string.ticket_already_used))) {
            Utils.showMessageDialog(mContext, "", res.getData().mesg, null);
        } else {
            listener.onScanTicket();
            Utils.showMessageDialog(mContext, "", getString(R.string.ticket_scan_success), (dialog, which) -> binding.ivBack.performClick());
        }
    }
}
