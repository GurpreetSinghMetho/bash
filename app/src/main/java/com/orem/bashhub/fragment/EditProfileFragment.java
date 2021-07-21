package com.orem.bashhub.fragment;


import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;

import com.orem.bashhub.R;
import com.orem.bashhub.activity.MainActivity;
import com.orem.bashhub.data.UserNamesPOJO;
import com.orem.bashhub.data.UserPOJO;
import com.orem.bashhub.databinding.FragmentEditProfileBinding;
import com.orem.bashhub.utils.Const;
import com.orem.bashhub.utils.Utils;
import com.orem.bashhub.utils.apiinterface.Events;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.List;
import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 */
public class EditProfileFragment extends BaseFragment {

    private FragmentEditProfileBinding binding;
    private String imageUrl = "", selectedDate = "";
    public boolean isEdit = false;
    boolean isShow=true;

    public EditProfileFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_edit_profile, container, false);
        ini();
        apiGetUserSuggestions( Const.getLoggedInUser(mContext).fname);
        return binding.getRoot();
    }

    private void ini() {
        binding.tvImagePick.setOnClickListener(this);
        binding.backIV.setOnClickListener(this);
        binding.tvName1.setOnClickListener(this);
        binding.tvName2.setOnClickListener(this);
        binding.tvName3.setOnClickListener(this);
        binding.btDone.setOnClickListener(this);
        binding.etEmail.setOnClickListener(this);
        binding.etNumber.setOnClickListener(this);
        binding.tvDob.setOnClickListener(this);
        binding.tvChangeOutfit.setOnClickListener(this);
        binding.etUsername.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                isShow=false;
                apiGetUserSuggestions( binding.etUsername.getText().toString());

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private void setEditTextWatcher(EditText... editTexts) {
        for (final EditText et : editTexts) {
            et.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {
                    isEdit = true;
                }
            });
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.backIV:
                Objects.requireNonNull(getActivity()).onBackPressed();
                break;
            case R.id.tvImagePick:
                registerEventBus();
                Utils.goToFragment(mContext, new BitEmojiFragment(), R.id.fragment_container);
                break;
            case R.id.tvName1:
                binding.etUsername.setText(binding.tvName1.getText().toString());
                break;
            case R.id.tvName2:
                binding.etUsername.setText(binding.tvName2.getText().toString());
                break;
            case R.id.tvName3:
                binding.etUsername.setText(binding.tvName3.getText().toString());
                break;
            case R.id.btDone:
                if (checkValidation()) {
                    UserPOJO.Data user = Const.getLoggedInUser(mContext);
                    if (!binding.etUsername.getText().toString().equals(user.username)) {
                        apiCheckUsername();
                    } else {
                        apiUpdateProfile();
                    }
                }
                break;
            case R.id.etEmail:
                unRegisterEventBus();
                EditEmailPhoneFragment fragment = new EditEmailPhoneFragment();
                fragment.setValues(true);
                Utils.goToFragment(mContext, fragment, R.id.fragment_container);
                break;
            case R.id.etNumber:
                unRegisterEventBus();
                EditEmailPhoneFragment fragment1 = new EditEmailPhoneFragment();
                fragment1.setValues(false);
                Utils.goToFragment(mContext, fragment1, R.id.fragment_container);
                break;
            case R.id.tvDob:
                Utils.showDOBPickerDialog(mContext, date -> {
                    selectedDate = date;
                    binding.tvDob.setText(Utils.changeDateFormat(selectedDate));
                    isEdit = true;
                });
                break;
            case R.id.tvChangeOutfit:
                unRegisterEventBus();
                Utils.goToFragment(mContext, new ChangeOutfitFragment(), R.id.fragment_container);
                break;
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        //registerEventBus();
    }

    private boolean checkValidation() {
        if (binding.etFName.getText().toString().isEmpty()) {
            Utils.showMessageDialog(mContext, "", getString(R.string.enter_first_name));
            return false;
        } else if (binding.etLName.getText().toString().isEmpty()) {
            Utils.showMessageDialog(mContext, "", getString(R.string.enter_last_name));
            return false;
        } else if (binding.etUsername.getText().toString().isEmpty()) {
            Utils.showMessageDialog(mContext, "", getString(R.string.enter_username));
            return false;
        } /*else if (binding.etAddress.getText().toString().isEmpty()) {
            Utils.showMessageDialog(mContext, "", getString(R.string.enter_address));
            return false;
        }*/ else if (binding.tvDob.getText().toString().isEmpty()) {
            Utils.showMessageDialog(mContext, "", getString(R.string.select_date_of_birth));
            return false;
        } else {
            return true;
        }
    }

    private void setValues(List<UserNamesPOJO.Data> list) {
        if (isShow){
            final Observer<UserPOJO.Data> observer = data -> {
                binding.setData(data);
                selectedDate = data.dob;
            };
            mLiveModel.getUserLiveData().observe((LifecycleOwner) mContext, observer);
            binding.scrollView.setVisibility(View.VISIBLE);
        }

        binding.tvName1.setText(list.get(1).name);
        binding.tvName2.setText(list.get(2).name);
        binding.tvName3.setText(list.get(3).name);

//        new Handler().postDelayed(() -> setEditTextWatcher(binding.etFName, binding.etLName, binding.etUsername, binding.etAddress), 1000);

    }

    public void onBackPress() {
        Utils.showDialog(mContext, getString(R.string.cancel_update_msg), getString(R.string.unsaved_changes),
                getString(R.string._yes), (dialog, which) -> binding.btDone.performClick(), (dialog, which) -> {
                    isEdit = false;
                    binding.backIV.performClick();
                }, getString(R.string._no), "", null, false);
    }

    private void apiGetUserSuggestions(String name) {
        registerEventBus();
        EventBus.getDefault().post(new Events.RequestUserSuggestions(mContext, Const.apiUserSuggestions(mContext,
               name), false, false));
    }

    private void apiCheckUsername() {
        registerEventBus();
        EventBus.getDefault().post(new Events.RequestBasic(mContext, Const.apiCheckUsername(mContext,
                binding.etUsername.getText().toString()), true, false));
    }

    /*private void apiSendOTP() {
        registerEventBus();
        isSendOTP = true;
        EventBus.getDefault().post(new Events.RequestBasic(mContext, Const.apiSendOtp(mContext,
                isNumber ? binding.etNumber.getText().toString() : "", isEmail ? binding.etEmail.getText().toString() : "", ""), true, false));
    }*/

    private void apiUpdateProfile() {
        registerEventBus();
        String selected = binding.spGender.getSelectedItem().toString();
        String gender = selected.equals(getString(R.string.male)) ? Const.MALE : (selected.equals(getString(R.string.female)) ? Const.FEMALE : Const.NOT_SPECIFIED);
        EventBus.getDefault().post(new Events.RequestUser(mContext, Const.apiUpdateProfile(mContext,
                binding.etFName.getText().toString(), binding.etLName.getText().toString(), binding.etUsername.getText().toString(),
                binding.etEmail.getText().toString(), binding.etNumber.getText().toString(), ""/*binding.etAddress.getText().toString()*/,
                gender, selectedDate, imageUrl), true, false));
    }

    @Subscribe
    public void apiGetUserSuggestionsRes(Events.GetUserSuggestionsData res) {
        setValues(res.getData().data);
    }

    @Subscribe
    public void apiCheckUsernameRes(Events.GetBasicData res) {
        if (res.getData().data.response != null) {
            if (res.getData().data.response.equals(Const.ONE)) {
                apiUpdateProfile();
                binding.tvError.setVisibility(View.GONE);
            } else {
                binding.tvError.setVisibility(View.VISIBLE);
            }
        }
    }

    @Subscribe
    public void getEmojiRes(Events.GetEmojiData res) {
        Utils.loadImage(mContext, res.getUrl(), binding.ivImage, R.drawable.ic_avatar_placeholder);
        imageUrl = res.getUrl();
        isEdit = true;
    }

    @Subscribe
    public void getOtpCallBack(Events.GetOtpCallBack res) {
        apiUpdateProfile();
    }

    @Subscribe
    public void apiUpdateProfile(Events.GetUserData res) {
        isEdit = false;
        imageUrl = "";
        Const.setLoggedInUser(mContext, res.getData().data);
        mLiveModel.getUserLiveData().setValue(Const.getLoggedInUser(mContext));
        Utils.showMessageDialog(mContext, "", getString(R.string.profile_update_success), (dialog, which) -> binding.backIV.performClick());
        ((MainActivity) mContext).apiGetBash(false);
    }
}
