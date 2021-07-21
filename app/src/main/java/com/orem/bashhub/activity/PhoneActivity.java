package com.orem.bashhub.activity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;

import androidx.databinding.DataBindingUtil;

import com.orem.bashhub.R;
import com.orem.bashhub.data.BasicPOJO;
import com.orem.bashhub.data.SignUpPOJO;
import com.orem.bashhub.databinding.ActivityPhoneBinding;
import com.orem.bashhub.utils.Const;
import com.orem.bashhub.utils.Utils;
import com.orem.bashhub.utils.apiinterface.Events;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.Objects;

public class PhoneActivity extends BaseActivity {

    ActivityPhoneBinding binding;
    SignUpPOJO data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_phone);
        init();
    }

    private void init() {
        data = (SignUpPOJO) getIntent().getSerializableExtra(Const.SIGNUP_DATA);
        binding.pBar.setProgress(Objects.requireNonNull(data).getSocialID().isEmpty() ? 40 : 25);
        binding.pBar.setVisibility(mTinyDB.getString(Const.LOGIN_TYPE).equals(Const.ONE) ? View.VISIBLE : View.GONE);
        binding.tvTag.setText(getString(data.isSignUp() ? R.string.what_s_your_mobile_number : R.string.bash_reg_mobile_number));
        binding.contiBT.setOnClickListener(this);
        binding.backIV.setOnClickListener(this);
        binding.emailTV.setOnClickListener(this);

        binding.etNumber.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                binding.contiBT.setBackgroundResource(s.toString().isEmpty() ? R.drawable.custom_grays_button : R.drawable.custom_light_purple_button);
            }
        });
        binding.etNumber.setText(data.getNumber());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.contiBT:
                if (binding.etNumber.getText().length() < 10)
                    Utils.showMessageDialog(mContext, "", getString(R.string.number_validation));
                else apiSendOTP();
                break;
            case R.id.backIV:
                finish();
                break;
            case R.id.emailTV:
                //Not used
                Bundle bundle = new Bundle();
                bundle.putSerializable(Const.SIGNUP_DATA, data);
                startNewActivity(EMailActivity.class, false, false, bundle);
                break;
        }
    }

    private void apiSendOTP() {
        registerEventBus();
        EventBus.getDefault().post(new Events.RequestBasic(mContext, Const.apiSendOtp(mContext,
                binding.etNumber.getText().toString(), "", data.isSignUp() ? "" : Const.ONE), true, false));
    }

    @Subscribe
    public void apiSendOtpRes(Events.GetBasicData res) {
        unRegisterEventBus();
        if (data.isSignUp()) {
            data.setNumber(binding.etNumber.getText().toString());
            data.setOtp(res.getData().data.otp);
            data.setIsEmail(false);
            Bundle bundle = new Bundle();
            bundle.putSerializable(Const.SIGNUP_DATA, data);
            startNewActivity(ConfiramtionActivity.class, false, false, bundle);
            //Utils.showToast(mContext, "OTP is : " + res.getData().data.otp);
        } else {
            BasicPOJO.Data item = res.getData().data;
            if (item.is_registered != null && item.is_registered.equals(Const.ZERO)) {
                Utils.showDialog(mContext, getString(R.string.want_to_signup), "", getString(R.string._yes), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        mTinyDB.putString(Const.LOGIN_TYPE, Const.ONE);
                        SignUpPOJO data = new SignUpPOJO();
                        data.setGoBack(false);
                        data.setSignUp(true);
                        data.setNumber(binding.etNumber.getText().toString());
                        Bundle bundle = new Bundle();
                        bundle.putSerializable(Const.SIGNUP_DATA, data);
                        startNewActivity(UsernameActivity.class, false, true, bundle);
                    }
                }, null, getString(R.string._no), "", null, false);
            } else {
                if (item.otp != null) {
                    data.setNumber(binding.etNumber.getText().toString());
                    data.setOtp(res.getData().data.otp);
                    data.setIsEmail(false);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable(Const.SIGNUP_DATA, data);
                    startNewActivity(ConfiramtionActivity.class, false, false, bundle);
                    //Utils.showToast(mContext, "OTP is : " + res.getData().data.otp);
                } else {
                    Utils.showMessageDialog(mContext, "", res.getData().mesg);
                }
            }
        }
    }
}
