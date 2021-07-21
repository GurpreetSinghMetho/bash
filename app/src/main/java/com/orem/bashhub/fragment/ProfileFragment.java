package com.orem.bashhub.fragment;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;

import com.orem.bashhub.R;
import com.orem.bashhub.adapter.ShowComplementAdapter;
import com.orem.bashhub.data.UserPOJO;
import com.orem.bashhub.databinding.FragmentProfileBinding;
import com.orem.bashhub.interfaces.OnBgApi;
import com.orem.bashhub.utils.Const;
import com.orem.bashhub.utils.MyImagePicker;
import com.orem.bashhub.utils.Utils;
import com.orem.bashhub.utils.apiinterface.Events;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.Objects;

public class ProfileFragment extends BaseFragment {

    private FragmentProfileBinding binding;
    private Bitmap selectedBitmap = null;
    private boolean isUploadImage = false;
    private OnBgApi listener = this::apiGetUser;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_profile, container, false);
        init();
        setUser();
        apiGetUser();
        return binding.getRoot();
    }

    private void init() {
        Utils.underlineTextView(binding.tvChangeImage);
        Utils.underlineTextView(binding.tvCostume);
        binding.backIV.setOnClickListener(this);
        binding.btEditProfile.setOnClickListener(this);
        binding.tvCostume.setOnClickListener(this);
        binding.tvChangeImage.setOnClickListener(this);
        binding.llHosted.setOnClickListener(this);
        binding.llAttended.setOnClickListener(this);
        binding.llFollowers.setOnClickListener(this);
        binding.llFollowing.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.backIV:
                Objects.requireNonNull(getActivity()).onBackPressed();
                break;
            case R.id.llHosted:
                ProfileEventsFragment fragment1 = new ProfileEventsFragment();
                fragment1.setData(Const.EVENT_HOSTED);
                Utils.goToFragment(mContext, fragment1, R.id.fragment_container);
                break;
            case R.id.llAttended:
                ProfileEventsFragment fragment2 = new ProfileEventsFragment();
                fragment2.setData(Const.EVENT_ATTENDED);
                Utils.goToFragment(mContext, fragment2, R.id.fragment_container);
                break;
            case R.id.llFollowers:
                ProfileUsersFragment fragment3 = new ProfileUsersFragment();
                fragment3.setData(Const.USER_FOLLOWERS, listener);
                Utils.goToFragment(mContext, fragment3, R.id.fragment_container);
                break;
            case R.id.llFollowing:
                ProfileUsersFragment fragment4 = new ProfileUsersFragment();
                fragment4.setData(Const.USER_FOLLOWING, listener);
                Utils.goToFragment(mContext, fragment4, R.id.fragment_container);
                break;
            case R.id.btEditProfile:
                unRegisterEventBus();
                Utils.goToFragment(mContext, new EditProfileFragment(), R.id.fragment_container);
                break;
            case R.id.tvCostume:
                unRegisterEventBus();
                Utils.goToFragment(mContext, new ChangeOutfitFragment(), R.id.fragment_container);
                break;
            case R.id.tvChangeImage:
                registerEventBus();
                MyImagePicker.selectImage(mContext, false, "");
                break;
        }
    }

    private void setUser() {
        final Observer<UserPOJO.Data> observer = data -> {
            binding.setData(data);
            binding.rvComplements.setAdapter(new ShowComplementAdapter(mContext, data.getComplementCount(mContext)));
        };
        mLiveModel.getUserLiveData().observe((LifecycleOwner) mContext, observer);
    }

    private void apiGetUser() {
        registerEventBus();
        isUploadImage = false;
        EventBus.getDefault().post(new Events.RequestUser(mContext, Const.apiGetUser(mContext, Const.getToken(mContext)), false, false));
    }

    private void apiUpdateImage() {
        registerEventBus();
        isUploadImage = true;
        EventBus.getDefault().post(new Events.RequestUser(mContext, Const.apiUpdateProfileImage(mContext,
                Utils.getCompressFile(mContext, selectedBitmap)), true, false));
    }

    @Subscribe
    public void apiUpdateProfile(Events.GetUserData res) {
        unRegisterEventBus();
        Const.setLoggedInUser(mContext, res.getData().data);
        mLiveModel.getUserLiveData().setValue(Const.getLoggedInUser(mContext));
        if (isUploadImage)
            Utils.showMessageDialog(mContext, "", getString(R.string.profile_image_updated), null);
    }

    @Subscribe
    public void getImagePath(Events.ImagePickerResult res) {
        unRegisterEventBus();
        try {
            Log.e("imagessss", res.getPath().toString());
            selectedBitmap = Utils.getImageInPortrait(res.getPath());
            apiUpdateImage();
        } catch (Exception e) {
            Utils.showToast(mContext, getString(R.string.failed_to_fetch));
        }
    }
}