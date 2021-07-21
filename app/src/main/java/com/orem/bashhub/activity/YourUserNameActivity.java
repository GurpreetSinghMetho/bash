package com.orem.bashhub.activity;

import android.os.Bundle;
import android.view.View;

import com.orem.bashhub.R;
import com.orem.bashhub.data.SignUpPOJO;
import com.orem.bashhub.data.UserNamesPOJO;
import com.orem.bashhub.databinding.ActivityYourUserNameBinding;
import com.orem.bashhub.utils.Const;
import com.orem.bashhub.utils.apiinterface.Events;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import androidx.databinding.DataBindingUtil;

public class YourUserNameActivity extends BaseActivity {

    ActivityYourUserNameBinding binding;
    SignUpPOJO data;
    UserNamesPOJO suggestions;
    String token = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_your_user_name);
        init();
        apiGetUserSuggestions();
    }

    private void init() {
        data = (SignUpPOJO) getIntent().getSerializableExtra(Const.SIGNUP_DATA);
        binding.pBar.setVisibility(mTinyDB.getString(Const.LOGIN_TYPE).equals(Const.ONE) ? View.VISIBLE : View.GONE);
        binding.contiBT.setOnClickListener(this);
        binding.backIV.setOnClickListener(this);
        binding.changeTV.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.contiBT:
                apiSignUp();
                /*Bundle bundle1 = new Bundle();
                bundle1.putSerializable(Const.SIGNUP_DATA, data);
                startNewActivity(SetPasswordActivity.class, false, false, bundle1);*/
                break;
            case R.id.backIV:
                finish();
                break;
            case R.id.changeTV:
                Bundle bundle2 = new Bundle();
                bundle2.putSerializable(Const.SIGNUP_DATA, data);
                bundle2.putSerializable(Const.SUGGESTION_DATA, suggestions);
                startNewActivity(ChangeUserNameActivity.class, false, false, bundle2);
                break;
        }
    }

    private void apiGetUserSuggestions() {
        registerEventBus();
        EventBus.getDefault().post(new Events.RequestUserSuggestions(mContext, Const.apiUserSuggestions(mContext,
                data.getFname()), true, false));
    }

    private void apiSignUp() {
        registerEventBus();
        EventBus.getDefault().post(new Events.RequestBasic(mContext, Const.apiSignUp(mContext, data), true, false));
    }

    private void apiGetUser(String token) {
        registerEventBus();
        this.token = token;
        EventBus.getDefault().post(new Events.RequestUser(mContext, Const.apiGetUser(mContext, token), true, false));
    }

    @Subscribe
    public void apiSignUpRes(Events.GetBasicData res) {
        unRegisterEventBus();
        apiGetUser("Bearer " + res.getData().data.token);
    }

    @Subscribe
    public void apiGetUserSuggestionsRes(Events.GetUserSuggestionsData res) {
        unRegisterEventBus();
        suggestions = res.getData();
        binding.tvName.setText(suggestions.data.get(0).name);
        data.setUsername(suggestions.data.get(0).name);
        binding.tvTitle.setVisibility(View.VISIBLE);
        binding.changeTV.setVisibility(View.VISIBLE);
        binding.contiBT.setVisibility(View.VISIBLE);
    }

    @Subscribe
    public void apiGetUserRes(Events.GetUserData res) {
        unRegisterEventBus();
        Const.setToken(mContext, token);
        Const.setLoggedInUser(mContext, res.getData().data);
        startNewActivity(SyncContactsActivity.class, true, true, null);
    }
}
