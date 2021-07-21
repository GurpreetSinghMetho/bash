package com.orem.bashhub.activity;

import android.os.Bundle;
import android.text.Editable;
import android.text.SpannableStringBuilder;
import android.text.TextWatcher;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;

import com.orem.bashhub.R;
import com.orem.bashhub.data.SignUpPOJO;
import com.orem.bashhub.databinding.ActivityUsernameBinding;
import com.orem.bashhub.utils.Const;
import com.orem.bashhub.utils.Utils;

public class UsernameActivity extends BaseActivity {

    ActivityUsernameBinding binding;
    SignUpPOJO data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_username);
        init();
        setTextWatcher();
    }

    private void init() {
        data = (SignUpPOJO) getIntent().getSerializableExtra(Const.SIGNUP_DATA);
        binding.btContinue.setOnClickListener(this);
        binding.backIV.setOnClickListener(this);
        binding.conditionTV.setMovementMethod(LinkMovementMethod.getInstance());
        binding.conditionTV.setText(addClickablePart(getString(R.string.terms)));
    }

    private SpannableStringBuilder addClickablePart(String str) {
        SpannableStringBuilder ssb = new SpannableStringBuilder(str);
        int idx1 = str.indexOf("Privacy");
        int idx2 = str.indexOf("Privacy") + 14;
        int idx3 = str.indexOf("Terms");
        int idx4 = str.indexOf("Terms") + 17;
        ssb.setSpan(new ClickableSpan() {
            @Override
            public void onClick(@NonNull View widget) {
                Utils.showToast(mContext, "Privacy Click");
            }
        }, idx1, idx2, 0);
        ssb.setSpan(new ClickableSpan() {
            @Override
            public void onClick(@NonNull View widget) {
                Utils.showToast(mContext, "Terms Click");
            }
        }, idx3, idx4, 0);
        return ssb;
    }

    private void setTextWatcher() {
        binding.etFName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String str = s.toString();
                if (str.length() > 0 && str.startsWith(" ")) {
                    binding.etFName.setText(str.trim());
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                setButtonColor();
            }
        });
        binding.etLName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String str = s.toString();
                if (str.length() > 0 && str.startsWith(" ")) {
                    binding.etLName.setText(str.trim());
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                setButtonColor();
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btContinue:
                if (checkValidation()) {
                    data.setFname(binding.etFName.getText().toString());
                    data.setLname(binding.etLName.getText().toString());
                    Bundle bundle = new Bundle();
                    bundle.putSerializable(Const.SIGNUP_DATA, data);
                    startNewActivity(PhoneActivity.class, false, false, bundle);
                }
                break;
            case R.id.backIV:
                finish();
                break;
            default:
                break;
        }
    }

    private void setButtonColor() {
        binding.btContinue.setBackgroundResource(binding.etFName.getText().toString().isEmpty() || binding.etLName.getText().toString().isEmpty() ? R.drawable.custom_grays_button : R.drawable.custom_light_purple_button);
    }

    private boolean checkValidation() {
        if (binding.etFName.getText().toString().isEmpty()) {
            Utils.showMessageDialog(mContext, "", getString(R.string.enter_first_name));
            return false;
        } else if (binding.etLName.getText().toString().isEmpty()) {
            Utils.showMessageDialog(mContext, "", getString(R.string.enter_last_name));
            return false;
        } else {
            return true;
        }
    }
}
