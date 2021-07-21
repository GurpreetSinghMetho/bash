package com.orem.bashhub.activity;

import androidx.databinding.DataBindingUtil;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;

import com.orem.bashhub.R;
import com.orem.bashhub.data.SignUpPOJO;
import com.orem.bashhub.databinding.ActivitySetPasswordBinding;
import com.orem.bashhub.utils.Const;
import com.orem.bashhub.utils.Utils;
import com.orem.bashhub.utils.apiinterface.Events;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

public class SetPasswordActivity extends BaseActivity {

    ActivitySetPasswordBinding binding;
    SignUpPOJO data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_set_password);
        init();
    }

    private void init() {
        data = (SignUpPOJO) getIntent().getSerializableExtra(Const.SIGNUP_DATA);
        binding.tvTitle.setText(getString(data.getFname() != null ? R.string.set_a_password : R.string.reset_password));
        binding.tvTitle1.setText(getString(data.getFname() != null ? R.string.password : R.string.reset_password));
        binding.contiBT.setOnClickListener(this);
        binding.backIV.setOnClickListener(this);

        binding.etPassword.addTextChangedListener(new TextWatcher() {
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
                if (binding.etPassword.getText().length() < 8) {
                    Utils.showMessageDialog(mContext, "", getString(R.string.password_msg));
                } else if (data.getFname() != null) {
                    data.setPassword(binding.etPassword.getText().toString());
                    Bundle bundle = new Bundle();
                    bundle.putSerializable(Const.SIGNUP_DATA, data);
                    startNewActivity(PhoneActivity.class, false, false, bundle);
                } else {
                    apiResetPassword();
                }
                break;
            case R.id.backIV:
                finish();
                break;

        }
    }

    public void apiResetPassword() {
        registerEventBus();
        EventBus.getDefault().post(new Events.RequestBasic(mContext, Const.apiResetPassword(mContext,
                data.getNumber(), data.getEmail(), binding.etPassword.getText().toString()), true, false));
    }

    @Subscribe
    public void apiResetPasswordRes(Events.GetBasicData res) {
        unRegisterEventBus();
        Utils.showMessageDialog(mContext, "", getString(R.string.reset_success), (dialog, which) -> startNewActivity(LoginActivity.class, true, true, null));
    }
}
