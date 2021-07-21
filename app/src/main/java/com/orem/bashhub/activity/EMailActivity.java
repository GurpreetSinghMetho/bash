package com.orem.bashhub.activity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;

import com.orem.bashhub.R;
import com.orem.bashhub.data.SignUpPOJO;
import com.orem.bashhub.databinding.ActivityEmailBinding;
import com.orem.bashhub.utils.Const;
import com.orem.bashhub.utils.Utils;
import com.orem.bashhub.utils.apiinterface.Events;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import androidx.databinding.DataBindingUtil;

public class EMailActivity extends BaseActivity {

    ActivityEmailBinding binding;
    SignUpPOJO data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_email);
        init();
    }

    private void init() {
        data = (SignUpPOJO) getIntent().getSerializableExtra(Const.SIGNUP_DATA);
        binding.tvTag.setText(getString(data.isSignUp() ? R.string.what_s_your_email_address : R.string.bash_reg_email));
        binding.contiBT.setOnClickListener(this);
        binding.backIV.setOnClickListener(this);
        binding.emailTV.setOnClickListener(this);

        binding.etEmail.addTextChangedListener(new TextWatcher() {
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
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.contiBT:
                if (!Utils.isEmailValid(binding.etEmail.getText().toString()))
                    Utils.showMessageDialog(mContext, "", getString(R.string.enter_valid_email));
                else apiSendOTP();
                break;
            case R.id.backIV:
            case R.id.emailTV:
                finish();
                break;
        }
    }

    private void apiSendOTP() {
        registerEventBus();
        EventBus.getDefault().post(new Events.RequestBasic(mContext, Const.apiSendOtp(mContext,
                "", binding.etEmail.getText().toString(), data.isSignUp() ? "" : Const.ONE), true, false));
    }

    @Subscribe
    public void apiSendOtpRes(Events.GetBasicData res) {
        unRegisterEventBus();
        data.setEmail(binding.etEmail.getText().toString());
        data.setOtp(res.getData().data.otp);
        data.setIsEmail(true);
        Bundle bundle = new Bundle();
        bundle.putSerializable(Const.SIGNUP_DATA, data);
        startNewActivity(ConfiramtionActivity.class, false, false, bundle);
    }
}
