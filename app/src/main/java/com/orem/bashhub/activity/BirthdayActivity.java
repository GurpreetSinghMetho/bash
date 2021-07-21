package com.orem.bashhub.activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;

import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;

import com.orem.bashhub.R;
import com.orem.bashhub.data.SignUpPOJO;
import com.orem.bashhub.databinding.ActivityBirthdayBinding;
import com.orem.bashhub.utils.Const;
import com.orem.bashhub.utils.Utils;

import java.util.Calendar;
import java.util.Objects;

public class BirthdayActivity extends BaseActivity {

    ActivityBirthdayBinding binding;
    String GENDER = Const.MALE, selectedDate = "";
    SignUpPOJO data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_birthday);
        init();
    }

    @SuppressLint("SetTextI18n")
    private void init() {
        data = (SignUpPOJO) getIntent().getSerializableExtra(Const.SIGNUP_DATA);
        binding.pBar.setProgress(Objects.requireNonNull(data).getSocialID().isEmpty() ? 80 : 75);
        binding.contiBT.setOnClickListener(this);
        binding.backIV.setOnClickListener(this);

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        binding.dateField.init(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH), (datePicker, year, month, dayOfMonth) -> {
            selectedDate = year + "-" + (month + 1) + "-" + dayOfMonth;
            binding.etBirthday.setText(Utils.changeDateFormat(selectedDate));
            binding.contiBT.setBackgroundResource(R.drawable.custom_light_purple_button);
        });
        //binding.dateField.setMaxDate(calendar.getTime().getTime());
        binding.tvMale.setOnClickListener(v -> {
            GENDER = Const.MALE;
            binding.tvMale.setTextColor(ContextCompat.getColor(mContext, R.color.colorPurple));
            binding.tvFeMale.setTextColor(ContextCompat.getColor(mContext, R.color.gray));
            binding.tvOther.setTextColor(ContextCompat.getColor(mContext, R.color.gray));
        });
        binding.tvFeMale.setOnClickListener(v -> {
            GENDER = Const.FEMALE;
            binding.tvFeMale.setTextColor(ContextCompat.getColor(mContext, R.color.colorPurple));
            binding.tvMale.setTextColor(ContextCompat.getColor(mContext, R.color.gray));
            binding.tvOther.setTextColor(ContextCompat.getColor(mContext, R.color.gray));
        });
        binding.tvOther.setOnClickListener(v -> {
            GENDER = Const.NOT_SPECIFIED;
            binding.tvOther.setTextColor(ContextCompat.getColor(mContext, R.color.colorPurple));
            binding.tvMale.setTextColor(ContextCompat.getColor(mContext, R.color.gray));
            binding.tvFeMale.setTextColor(ContextCompat.getColor(mContext, R.color.gray));
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.contiBT:
                if (binding.etBirthday.getText().toString().isEmpty()) {
                    Utils.showMessageDialog(mContext, "", getString(R.string.select_date_of_birth));
                } else if (!Utils.isValidDOB(selectedDate)) {
                    Utils.showMessageDialog(mContext, "", getString(R.string.invalid_dob));
                } else {
                    data.setDob(selectedDate);
                    data.setGender(GENDER);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable(Const.SIGNUP_DATA, data);
                    startNewActivity(YourUserNameActivity.class, false, false, bundle);
                }
                break;
            case R.id.backIV:
                finish();
                break;
        }
    }
}
