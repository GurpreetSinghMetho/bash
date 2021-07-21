package com.orem.bashhub.activity;

import android.os.Bundle;

import com.orem.bashhub.R;
import com.orem.bashhub.data.SignUpPOJO;
import com.orem.bashhub.databinding.ActivityTroubleLoginBinding;
import com.orem.bashhub.utils.Const;
import com.orem.bashhub.utils.Utils;

import androidx.databinding.DataBindingUtil;

public class TroubleLoginActivity extends BaseActivity {

    ActivityTroubleLoginBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_trouble_login);
        ini();
    }

    private void ini() {
        Utils.underlineTextView(binding.tvEmailMsg);
        Utils.underlineTextView(binding.tvPhoneMsg);

        SignUpPOJO data = new SignUpPOJO();
        data.setSignUp(false);
        Bundle bundle = new Bundle();
        bundle.putSerializable(Const.SIGNUP_DATA, data);
        binding.btEmail.setOnClickListener(v -> startNewActivity(EMailActivity.class, false, false, bundle));
        binding.btNumber.setOnClickListener(v -> startNewActivity(PhoneActivity.class, false, false, bundle));
        binding.backIV.setOnClickListener(v -> onBackPressed());
    }
}
