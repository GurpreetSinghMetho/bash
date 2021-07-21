package com.orem.bashhub.fragment;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.orem.bashhub.R;
import com.orem.bashhub.databinding.FragmentPaymentBinding;
import com.orem.bashhub.dialogs.DialogPaypalID;
import com.orem.bashhub.utils.Const;
import com.orem.bashhub.utils.Utils;
import com.orem.bashhub.utils.apiinterface.Events;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 */
public class PaymentFragment extends BaseFragment {

    private FragmentPaymentBinding binding;

    public PaymentFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_payment, container, false);
        ini();
        apiUser();
        return binding.getRoot();
    }

    private void ini() {
        Utils.underlineTextView(binding.tvLink);
        Utils.underlineTextView(binding.tvEdit);
        binding.backIV.setOnClickListener(v -> Objects.requireNonNull(getActivity()).onBackPressed());
        binding.btWithdraw.setOnClickListener(v -> {
            if (Double.parseDouble(Const.getLoggedInUser(mContext).getWalletValue()) <= 0) {
                Utils.showToast(mContext, getString(R.string.not_enough_amount));
            } else if (!Const.getLoggedInUser(mContext).isPaypalLinked()) {
                Utils.showToast(mContext, getString(R.string.paypal_not_linked));
            } else {
                apiWithdraw(Const.getLoggedInUser(mContext).paypal_id);
            }
        });
        binding.tvLink.setOnClickListener(v -> {
            if (Const.getLoggedInUser(mContext).isPaypalLinked()) {
                apiUpdatePaypal("", "");
            } else {
                DialogPaypalID dialog = new DialogPaypalID(this);
                dialog.show(getChildFragmentManager(), dialog.getTag());
            }
        });
        binding.tvEdit.setOnClickListener(v -> {
            DialogPaypalID dialog = new DialogPaypalID(this);
            dialog.show(getChildFragmentManager(), dialog.getTag());
        });
    }

    private void apiUser() {
        registerEventBus();
        EventBus.getDefault().post(new Events.RequestUser(mContext, Const.apiUser(mContext), true, false));
    }

    public void apiWithdraw(String email) {
        registerEventBus();
        EventBus.getDefault().post(new Events.RequestBasic(mContext, Const.apiWithdraw(mContext, email), true, false));
    }

    public void apiUpdatePaypal(String paypalID, String name) {
        registerEventBus();
        EventBus.getDefault().post(new Events.RequestUser(mContext, Const.apiUpdatePaypal(mContext, paypalID, name), true, false));
    }

    @Subscribe
    public void apiUserRes(Events.GetUserData res) {
        unRegisterEventBus();
        Const.setLoggedInUser(mContext, res.getData().data);
        mLiveModel.getUserLiveData().setValue(Const.getLoggedInUser(mContext));
        binding.setData(res.getData().data);
        binding.llOuter.setVisibility(View.VISIBLE);
    }

    @Subscribe
    public void apiWithdrawRes(Events.GetBasicData res) {
        unRegisterEventBus();
        Utils.showMessageDialog(mContext, "", getString(R.string.payout_success), (dialog, which) -> binding.backIV.performClick());
    }
}
