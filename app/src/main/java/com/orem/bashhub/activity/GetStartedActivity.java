package com.orem.bashhub.activity;

import android.os.Bundle;
import android.view.View;

import com.orem.bashhub.R;
import com.orem.bashhub.data.SignUpPOJO;
import com.orem.bashhub.databinding.ActivityGetStartedBinding;
import com.orem.bashhub.utils.Const;
import com.orem.bashhub.utils.apiinterface.Events;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import androidx.databinding.DataBindingUtil;

public class GetStartedActivity extends BaseActivity {

    ActivityGetStartedBinding binding;
    SignUpPOJO data;
    String token = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_get_started);
        init();
    }

    private void init() {
        data = (SignUpPOJO) getIntent().getSerializableExtra(Const.SIGNUP_DATA);
        binding.contiBT.setOnClickListener(this);
        binding.backIV.setOnClickListener(this);
        binding.allowTV.setText(getString(R.string.allow_prompted));
        binding.pBar.setVisibility(mTinyDB.getString(Const.LOGIN_TYPE).equals(Const.ONE) ? View.VISIBLE : View.GONE);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.contiBT:
                if (data.isSignUp()) {
                    Bundle bundle = new Bundle();
                    bundle.putSerializable(Const.SIGNUP_DATA, data);
                    startNewActivity(PhoneActivity.class, false, false, bundle);
                } else {
                    apiGetToken();
                }
                break;
            case R.id.backIV:
                finish();
                break;
        }
    }

    private void apiGetToken() {
        registerEventBus();
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
    public void apiGetTokenRes(Events.GetBasicData res) {
        unRegisterEventBus();
        apiGetUser("Bearer " + res.getData().data.token);
    }

    @Subscribe
    public void apiGetUserRes(Events.GetUserData res) {
        unRegisterEventBus();
        Const.setToken(mContext, token);
        Const.setLoggedInUser(mContext, res.getData().data);
        startNewActivity(MainActivity.class, true, true, null);
    }
}
