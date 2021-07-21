package com.orem.bashhub.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.orem.bashhub.R;
import com.orem.bashhub.activity.MainActivity;
import com.orem.bashhub.adapter.MyBashAdapter;
import com.orem.bashhub.adapter.VenuListAdapter;
import com.orem.bashhub.data.UserPOJO;
import com.orem.bashhub.data.VenueListPOJO;
import com.orem.bashhub.databinding.FragmentMyBashBinding;
import com.orem.bashhub.interfaces.OnBgApi;
import com.orem.bashhub.utils.Const;
import com.orem.bashhub.utils.apiinterface.Events;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;

public class MyBashFragment extends BaseFragment {

    boolean isVenue;
    private FragmentMyBashBinding binding;
    private OnBgApi listener = () -> apiMyBash(false);
    private List<VenueListPOJO.Venue> venueList = new ArrayList<>();

    public MyBashFragment(boolean isVenue) {
        this.isVenue = isVenue;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_my_bash, container, false);
        init();
        if (!isVenue) {
            binding.mPurchaseImage.setImageDrawable(mContext.getDrawable(R.drawable.event_purchase));
            binding.tvBashCount.setText(Const.getLoggedInUser(mContext).getToday_bash_count());
            apiMyBash(true);
        } else {
            binding.tvBashCount.setText(Const.getLoggedInUser(mContext).getToday_venue_count());
            apiGetVenue(true);
            binding.mPurchaseImage.setImageDrawable(mContext.getDrawable(R.drawable.business_purchase));
        }
        return binding.getRoot();
    }

    private void init() {
        binding.backIV.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.backIV:
                Objects.requireNonNull(getActivity()).onBackPressed();
                break;
        }
    }

    private void apiMyBash(boolean isDialog) {
        registerEventBus();
        EventBus.getDefault().post(new Events.RequestMyBash(mContext, Const.apiMyBash(mContext), isDialog, false));
    }

    public void apiGetVenue(boolean isDialog) {
        registerEventBus();
        EventBus.getDefault().post(new Events.RequestVenueList(mContext, Const.apiGetVenue(mContext, "", "", "2"), isDialog, false));
    }

    @Subscribe
    public void apiGetVenueRes(Events.GetVenuListData res) {
        venueList.clear();
        venueList.addAll(res.getData().getData().getVenues());
        unRegisterEventBus();
        if (res.getData().getData().getVenues().size() > 0) {
            binding.tvBashCount.setText("" + res.getData().getData().getVenues().size());
            binding.rvToday.setAdapter(new VenuListAdapter(mContext, venueList, null, "0"));
            binding.tvToday.setVisibility(View.VISIBLE);
            binding.rvToday.setVisibility(View.VISIBLE);
        } else {
            binding.tvBashCount.setText(Const.ZERO);
            binding.tvToday.setVisibility(View.GONE);
            binding.rvToday.setVisibility(View.GONE);
        }

        binding.tvUpcoming.setVisibility(View.GONE);
        binding.rvUpcoming.setVisibility(View.GONE);
        binding.scrollView.setVisibility(View.VISIBLE);

        UserPOJO.Data user = Const.getLoggedInUser(mContext);
        user.setToday_venue_count("" + res.getData().getData().getVenues().size());
        Const.setLoggedInUser(mContext, user);
        mLiveModel.getUserLiveData().setValue(user);
        /*if (isFirstTime && bashList.size() <= 0)
            Utils.showToast(mContext, getString(R.string.no_event_found));*/
    }


    @SuppressLint("SetTextI18n")
    @Subscribe
    public void apiMyBashRes(Events.GetMyBashData res) {
        unRegisterEventBus();
        if (res.getData().data.today.size() > 0) {
            binding.tvBashCount.setText("" + res.getData().data.today.size());
            binding.rvToday.setAdapter(new MyBashAdapter(mContext, res.getData().data.today, listener));
            binding.tvToday.setVisibility(View.VISIBLE);
            binding.rvToday.setVisibility(View.VISIBLE);
        } else {
            binding.tvBashCount.setText(Const.ZERO);
            binding.tvToday.setVisibility(View.GONE);
            binding.rvToday.setVisibility(View.GONE);
        }
        if (res.getData().data.upcomming.size() > 0) {
            binding.rvUpcoming.setAdapter(new MyBashAdapter(mContext, res.getData().data.upcomming, listener));
            binding.tvUpcoming.setVisibility(View.VISIBLE);
            binding.rvUpcoming.setVisibility(View.VISIBLE);
        } else {
            binding.tvUpcoming.setVisibility(View.GONE);
            binding.rvUpcoming.setVisibility(View.GONE);
        }
        binding.scrollView.setVisibility(View.VISIBLE);

        UserPOJO.Data user = Const.getLoggedInUser(mContext);
        user.setToday_bash_count("" + res.getData().data.today.size());
        Const.setLoggedInUser(mContext, user);
        mLiveModel.getUserLiveData().setValue(user);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (isVenue){
            MainActivity.apiGetVenue(true);
        }
    }
}
