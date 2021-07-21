package com.orem.bashhub.activity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;

import androidx.databinding.DataBindingUtil;

import com.orem.bashhub.R;
import com.orem.bashhub.data.SignUpPOJO;
import com.orem.bashhub.databinding.ActivityConfiramtionBinding;
import com.orem.bashhub.utils.Const;
import com.orem.bashhub.utils.PrefStore;
import com.orem.bashhub.utils.Utils;
import com.orem.bashhub.utils.apiinterface.Events;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.Objects;

public class ConfiramtionActivity extends BaseActivity {

    ActivityConfiramtionBinding binding;
    SignUpPOJO data;
    String token = "";
    boolean isOtp = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_confiramtion);
        init();
    }

    private void init() {
        data = (SignUpPOJO) getIntent().getSerializableExtra(Const.SIGNUP_DATA);
        binding.pBar.setProgress(Objects.requireNonNull(data).getSocialID().isEmpty() ? 60 : 50);
        binding.pBar.setVisibility(mTinyDB.getString(Const.LOGIN_TYPE).equals(Const.ONE) ? View.VISIBLE : View.GONE);
        binding.tvValue.setText(data.isEmail() ? data.getEmail() : data.getNumber());
        binding.contiBT.setOnClickListener(this);
        binding.backIV.setOnClickListener(this);

        binding.etCode.addTextChangedListener(new TextWatcher() {
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
                if (!binding.etCode.getText().toString().equals(data.getOtp())) {
                    Utils.showDialog(mContext, getString(R.string.code_not_verified), getString(R.string.incorrect_code),
                            getString(R.string._yes), (dialog, which) -> apiSendOTP(), null, getString(R.string._no), "", null, false);
                } else if (data.isGoBack()) {
                    EventBus.getDefault().post(new Events.GetOtpCallBack());
                    onBackPressed();
                } else if (!data.isSignUp()) {
                    /*Bundle bundle = new Bundle();
                    bundle.putSerializable(Const.SIGNUP_DATA, data);
                    startNewActivity(GetStartedActivity.class, false, false, bundle);*/
                    apiGetToken();
                } else {
                    Bundle bundle = new Bundle();
                    bundle.putSerializable(Const.SIGNUP_DATA, data);
                    startNewActivity(BirthdayActivity.class, false, false, bundle);
                    //startNewActivity(YourUserNameActivity.class, false, false, bundle);
                }
                break;
            case R.id.backIV:
                finish();
                break;
        }
    }

    private void apiSendOTP() {
        registerEventBus();
        isOtp = true;
        EventBus.getDefault().post(new Events.RequestBasic(mContext, Const.apiSendOtp(mContext,
                data.isEmail() ? "" : data.getNumber(), data.isEmail() ? data.getEmail() : "", data.isSignUp() ? "" : Const.ONE), true, false));
    }

    /*private void apiSignUp() {
        registerEventBus();
        EventBus.getDefault().post(new Events.RequestBasic(mContext, Const.apiSignUp(mContext, data), true, false));
    }

    @Subscribe
    public void apiSignUpRes(Events.GetBasicData res) {
        unRegisterEventBus();
        Utils.showMessageDialog(mContext, "", getString(R.string.reg_success), (dialog, which) -> startNewActivity(LoginActivity.class, true, true, null));
    }*/

    private void apiGetToken() {
        registerEventBus();
        isOtp = false;
        String type = data.getNumber().isEmpty() ? Const.ONE : Const.TWO;
        String value = data.getNumber().isEmpty() ? data.getEmail() : data.getNumber();
        EventBus.getDefault().post(new Events.RequestBasic(mContext, Const.apiGetToken(mContext, value, type), true, false));
    }

    private void apiGetUser(String token) {
        registerEventBus();
        this.token = token;
        EventBus.getDefault().post(new Events.RequestUser(mContext, Const.apiGetUser(mContext, token), true, false));
    }

    @Subscribe
    public void apiSendOtpRes(Events.GetBasicData res) {
        unRegisterEventBus();
        if (isOtp) {
            data.setOtp(res.getData().data.otp);
        } else {
            apiGetUser("Bearer " + res.getData().data.token);
        }
    }

    @Subscribe
    public void apiGetUserRes(Events.GetUserData res) {
        unRegisterEventBus();
        Const.setToken(mContext, token);
        Const.setLoggedInUser(mContext, res.getData().data);
        PrefStore prefStore = new PrefStore(mContext);
        startNewActivity(MainActivity.class, true, true, null);
    }
}
