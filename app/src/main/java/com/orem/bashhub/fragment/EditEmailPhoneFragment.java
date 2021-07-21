package com.orem.bashhub.fragment;


import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import com.orem.bashhub.R;
import com.orem.bashhub.data.UserPOJO;
import com.orem.bashhub.databinding.DialogVerificationCodeBinding;
import com.orem.bashhub.databinding.FragmentEditEmailPhoneBinding;
import com.orem.bashhub.utils.Const;
import com.orem.bashhub.utils.Utils;
import com.orem.bashhub.utils.apiinterface.Events;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

/**
 * A simple {@link Fragment} subclass.
 */
public class EditEmailPhoneFragment extends BaseFragment {

    private FragmentEditEmailPhoneBinding binding;
    private boolean isEmail = false;

    public EditEmailPhoneFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_edit_email_phone, container, false);
        ini();
        return binding.getRoot();
    }

    private void ini() {
        binding.ivBack.setOnClickListener(v -> Objects.requireNonNull(getActivity()).onBackPressed());
        binding.tvTitle.setText(getString(isEmail ? R.string.new_email_address : R.string.new_mobile_number));
        binding.groupEmail.setVisibility(isEmail ? View.VISIBLE : View.GONE);
        binding.groupPhone.setVisibility(isEmail ? View.GONE : View.VISIBLE);
        binding.btSubmit.setOnClickListener(v -> submitClick());
    }

    public void setValues(boolean isEmail) {
        this.isEmail = isEmail;
    }

    private void submitClick() {
        if (isEmail && !Utils.isEmailValid(binding.etEmail.getText().toString()))
            Utils.showMessageDialog(mContext, "", getString(R.string.enter_valid_email));
        else if (!isEmail && binding.etNumber.getText().length() < 10)
            Utils.showMessageDialog(mContext, "", getString(R.string.number_validation));
        else apiSendOTP();
    }

    private void dialogOtp(String otp) {
        final Dialog dialog = new Dialog(mContext, R.style.themeDialog);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        DialogVerificationCodeBinding binding = DataBindingUtil.inflate(LayoutInflater.from(mContext), R.layout.dialog_verification_code, null, false);
        dialog.setContentView(binding.getRoot());
        dialog.setCancelable(true);
        Objects.requireNonNull(dialog.getWindow()).setBackgroundDrawableResource(android.R.color.transparent);

        binding.tvDone.setOnClickListener(v -> {
            if (!binding.etCode.getText().toString().equals(otp)) {
                Utils.showMessageDialog(mContext, "", getString(R.string.enter_valid_code));
            } else {
                dialog.dismiss();
                apiUpdateProfile();
            }

        });

        dialog.show();
    }

    private void apiSendOTP() {
        registerEventBus();
        EventBus.getDefault().post(new Events.RequestBasic(mContext, Const.apiSendOtp(mContext,
                !isEmail ? binding.etNumber.getText().toString() : "", isEmail ? binding.etEmail.getText().toString() : "", ""), true, false));
    }

    private void apiUpdateProfile() {
        registerEventBus();
        EventBus.getDefault().post(new Events.RequestUser(mContext, Const.apiUpdateEmailPhone(mContext,
                isEmail ? binding.etEmail.getText().toString() : "", !isEmail ? binding.etNumber.getText().toString() : ""), true, false));
    }

    @Subscribe
    public void apiSendOtpRes(Events.GetBasicData res) {
        unRegisterEventBus();
        dialogOtp(res.getData().data.otp);
    }

    @Subscribe
    public void apiUpdateProfileRes(Events.GetUserData res) {
        unRegisterEventBus();
        UserPOJO.Data user = mLiveModel.getUserLiveData().getValue();
        if (isEmail) Objects.requireNonNull(user).setEmail(res.getData().data.email);
        else Objects.requireNonNull(user).setPhone_number(res.getData().data.phone_number);
        mLiveModel.getUserLiveData().setValue(user);
        binding.ivBack.performClick();
    }
}
