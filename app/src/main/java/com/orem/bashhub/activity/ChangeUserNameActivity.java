package com.orem.bashhub.activity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;

import androidx.databinding.DataBindingUtil;

import com.orem.bashhub.R;
import com.orem.bashhub.data.SignUpPOJO;
import com.orem.bashhub.data.UserNamesPOJO;
import com.orem.bashhub.databinding.ActivityChangeUserNameBinding;
import com.orem.bashhub.utils.Const;
import com.orem.bashhub.utils.Utils;
import com.orem.bashhub.utils.apiinterface.Events;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

public class ChangeUserNameActivity extends BaseActivity {

    ActivityChangeUserNameBinding binding;
    SignUpPOJO data;
    UserNamesPOJO suggestions;
    String token = "";
    boolean isSingUp = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_change_user_name);
        init();
        setValues();
    }

    private void init() {
        data = (SignUpPOJO) getIntent().getSerializableExtra(Const.SIGNUP_DATA);
        binding.pBar.setVisibility(mTinyDB.getString(Const.LOGIN_TYPE).equals(Const.ONE) ? View.VISIBLE : View.GONE);
        suggestions = (UserNamesPOJO) getIntent().getSerializableExtra(Const.SUGGESTION_DATA);
        binding.contiBT.setOnClickListener(this);
        binding.backIV.setOnClickListener(this);
        binding.ivRefresh.setOnClickListener(this);
        binding.tvName1.setOnClickListener(this);
        binding.tvName2.setOnClickListener(this);
        binding.tvName3.setOnClickListener(this);

        binding.etName.addTextChangedListener(new TextWatcher() {
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
                if (binding.etName.getText().toString().isEmpty())
                    Utils.showMessageDialog(mContext, "", getString(R.string.enter_username));
                else apiCheckUsername();
                break;
            case R.id.backIV:
                finish();
                break;
            case R.id.ivRefresh:
                apiGetUserSuggestions();
                break;
            case R.id.tvName1:
                binding.etName.setText(binding.tvName1.getText().toString());
                break;
            case R.id.tvName2:
                binding.etName.setText(binding.tvName2.getText().toString());
                break;
            case R.id.tvName3:
                binding.etName.setText(binding.tvName3.getText().toString());
                break;
        }
    }

    private void setValues() {
        binding.etName.setText(data.getUsername());
        binding.tvName1.setText(suggestions.data.get(1).name);
        binding.tvName2.setText(suggestions.data.get(2).name);
        binding.tvName3.setText(suggestions.data.get(3).name);
    }

    private void apiGetUserSuggestions() {
        registerEventBus();
        EventBus.getDefault().post(new Events.RequestUserSuggestions(mContext, Const.apiUserSuggestions(mContext,
                data.getFname()), true, false));
    }

    private void apiCheckUsername() {
        registerEventBus();
        isSingUp = false;
        EventBus.getDefault().post(new Events.RequestBasic(mContext, Const.apiCheckUsername(mContext,
                binding.etName.getText().toString()), false, false));
    }

    private void apiSignUp() {
        registerEventBus();
        isSingUp = true;
        EventBus.getDefault().post(new Events.RequestBasic(mContext, Const.apiSignUp(mContext, data), true, false));
    }

    private void apiGetUser(String token) {
        registerEventBus();
        this.token = token;
        EventBus.getDefault().post(new Events.RequestUser(mContext, Const.apiGetUser(mContext, token), true, false));
    }

    @Subscribe
    public void apiGetUserSuggestionsRes(Events.GetUserSuggestionsData res) {
        unRegisterEventBus();
        suggestions = res.getData();
        data.setUsername(suggestions.data.get(0).name);
        setValues();
    }

    @Subscribe
    public void apiCheckUsernameRes(Events.GetBasicData res) {
        unRegisterEventBus();
        if (isSingUp) {
            apiGetUser("Bearer " + res.getData().data.token);
        } else {
            if (res.getData().data.response.equals(Const.ONE)) {
                data.setUsername(binding.etName.getText().toString());
                apiSignUp();
                /*Bundle bundle1 = new Bundle();
                bundle1.putSerializable(Const.SIGNUP_DATA, data);
                startNewActivity(SetPasswordActivity.class, false, false, bundle1);*/
                binding.tvError.setVisibility(View.INVISIBLE);
            } else {
                binding.tvError.setVisibility(View.VISIBLE);
            }
        }
    }

    @Subscribe
    public void apiGetUserRes(Events.GetUserData res) {
        unRegisterEventBus();
        Const.setToken(mContext, token);
        Const.setLoggedInUser(mContext, res.getData().data);
        startNewActivity(SyncContactsActivity.class, true, true, null);
    }
}
