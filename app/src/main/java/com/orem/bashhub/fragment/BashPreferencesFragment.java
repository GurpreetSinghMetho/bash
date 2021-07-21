package com.orem.bashhub.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;

import com.orem.bashhub.R;
import com.orem.bashhub.activity.MainActivity;
import com.orem.bashhub.data.UserPOJO;
import com.orem.bashhub.databinding.FragmentSearchBashBinding;
import com.orem.bashhub.utils.Const;
import com.orem.bashhub.utils.Utils;
import com.orem.bashhub.utils.apiinterface.Events;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.Objects;

public class BashPreferencesFragment extends BaseFragment {

    private FragmentSearchBashBinding binding;
    private UserPOJO.Data user;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_search_bash, container, false);
        init();
        setValues();
        return binding.getRoot();
    }

    private void init() {
        user = Const.getLoggedInUser(mContext);
        binding.backIV.setOnClickListener(this);
        binding.ivRestaurant.setOnClickListener(this);
        binding.ivClub.setOnClickListener(this);
        binding.ivBar.setOnClickListener(this);
        binding.btSearch.setOnClickListener(this);
        binding.seekBar.setProgress(Integer.parseInt(user.getPref_distance()));
        binding.seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                user.setPref_distance("" + progress);
                setValues();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.backIV:
                Objects.requireNonNull(getActivity()).onBackPressed();
                break;
            case R.id.ivRestaurant:
                onBashTypeClick(Const.EVENT_RESTAURANT);
                break;
            case R.id.ivClub:
                onBashTypeClick(Const.EVENT_CLUB);
                break;
            case R.id.ivBar:
                onBashTypeClick(Const.EVENT_BAR);
                break;
            case R.id.btSearch:
                if (user.getPref_bash_type().isEmpty())
                    Utils.showToast(mContext, getString(R.string.select_bash_type));
                else apiUpdatePreferences();
                break;
        }
    }

    @SuppressLint("SetTextI18n")
    private void setValues() {
        binding.tvSeekValue.setText(user.getPref_distance() + " " + getString(R.string.miles));
        binding.ivRestaurant.setImageResource(Utils.getArrayFromString(user.getPref_bash_type()).contains(Const.EVENT_RESTAURANT) ?
                R.drawable.ic_restaurant_dark : R.drawable.ic_restaurant_unselected);
        binding.ivClub.setImageResource(Utils.getArrayFromString(user.getPref_bash_type()).contains(Const.EVENT_CLUB) ?
                R.drawable.ic_club_dark : R.drawable.ic_club_unselected);
        binding.ivBar.setImageResource(Utils.getArrayFromString(user.getPref_bash_type()).contains(Const.EVENT_BAR) ?
                R.drawable.ic_bar_dark : R.drawable.ic_bar_unselected);
    }

    private void onBashTypeClick(String type) {
        ArrayList<String> array = Utils.getArrayFromString(user.getPref_bash_type());
        if (array.contains(type)) array.remove(type);
        else array.add(type);
        user.setPref_bash_type(Utils.getStringFromArray(array));
        setValues();
    }

    private void apiUpdatePreferences() {
        registerEventBus();
        EventBus.getDefault().post(new Events.RequestUser(mContext, Const.apiUpdatePreferences(mContext,
                user.getPref_bash_type(), user.getPref_distance()), true, false));
    }

    @Subscribe
    public void apiUpdatePreferencesRes(Events.GetUserData res) {
        unRegisterEventBus();
        Const.setLoggedInUser(mContext, res.getData().data);
        mLiveModel.getUserLiveData().setValue(Const.getLoggedInUser(mContext));
        ((MainActivity) mContext).searchText = binding.etKeywork.getText().toString();
        ((MainActivity) mContext).isFirstTime = true;
        ((MainActivity) mContext).callBashApi();
        binding.backIV.performClick();
    }
}
