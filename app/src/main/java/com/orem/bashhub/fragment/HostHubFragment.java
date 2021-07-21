package com.orem.bashhub.fragment;


import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.orem.bashhub.R;
import com.orem.bashhub.adapter.AttendanceAdapter;
import com.orem.bashhub.adapter.EarningsAdapter;
import com.orem.bashhub.adapter.HostHubEventsAdapter;
import com.orem.bashhub.adapter.ShowComplementAdapter;
import com.orem.bashhub.data.BashDetailsPOJO;
import com.orem.bashhub.data.HostHubPOJO;
import com.orem.bashhub.databinding.FragmentHostHubBinding;
import com.orem.bashhub.utils.Const;
import com.orem.bashhub.utils.Utils;
import com.orem.bashhub.utils.apiinterface.Events;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 */
public class HostHubFragment extends BaseFragment {

    String date;
    private FragmentHostHubBinding binding;
    private HostHubPOJO.Data data;

    public HostHubFragment(String date) {
        this.date = date;
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_host_hub, container, false);
        ini();
        if (date.equalsIgnoreCase("")) {
            setDate(Utils.getTodayDate());
        } else
            setDate(date);
        return binding.getRoot();
    }

    private void ini() {
        Utils.underlineTextView(binding.tvMore);
        binding.backIV.setOnClickListener(v -> Objects.requireNonNull(getActivity()).onBackPressed());
        Utils.underlineTextView(binding.tvDate);
        Utils.underlineTextView(binding.tvEarnings);
        Utils.underlineTextView(binding.tvAttendanceInfo);
        Utils.underlineTextView(binding.tvRating);
        binding.tvDate.setOnClickListener(v -> Utils.showDatePickerDialogHost(mContext, this::setDate));
        binding.tvMore.setOnClickListener(v -> setData(true));
    }

    @SuppressLint("SetTextI18n")
    private void setDate(String date) {
        SimpleDateFormat inputPattern = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
        SimpleDateFormat outputPattern = new SimpleDateFormat("dd MMM", Locale.US);
        try {
            Date inputDate = inputPattern.parse(date);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(inputDate);
            calendar.add(Calendar.DAY_OF_YEAR, -6);
            binding.tvDate.setText(outputPattern.format(calendar.getTime()) + " - " + outputPattern.format(inputDate));
            binding.tvSelectedDate.setText(outputPattern.format(calendar.getTime()) + " - " + outputPattern.format(inputDate));
            apiHostHub(date);
        } catch (Exception e) {
            Utils.showLog("Error : " + e.getMessage());
            binding.tvDate.setText("");
            binding.tvSelectedDate.setText("");
        }
    }

    private void setData(boolean isAll) {
        binding.setData(data);
        if (data.bash_data.size() > 5 && !isAll) {
            List<BashDetailsPOJO> list = new ArrayList<>();
            for (int i = 0; i < 5; i++)
                list.add(data.bash_data.get(i));
            binding.rvEvents.setAdapter(new HostHubEventsAdapter(mContext, list));
            binding.tvMore.setVisibility(View.VISIBLE);
        } else {
            binding.rvEvents.setAdapter(new HostHubEventsAdapter(mContext, data.bash_data));
            binding.tvMore.setVisibility(View.GONE);
        }
        binding.rvEarnings.setAdapter(new EarningsAdapter(mContext, data));
        binding.rvAttendance.setAdapter(new AttendanceAdapter(mContext, data));
        binding.rvComplements.setAdapter(new ShowComplementAdapter(mContext, data.rate_data.getComplementCount(mContext)));
        binding.tvError.setVisibility(View.GONE);
        binding.llOuter.setVisibility(View.VISIBLE);
    }

    private void apiHostHub(String date) {
        registerEventBus();
        EventBus.getDefault().post(new Events.RequestHostHub(mContext, Const.apiHostHub(mContext, date), true, false));
    }

    @Subscribe
    public void apiHostHubRes(Events.GetHostHubData res) {
        unRegisterEventBus();
        if (res.getData().data.bash_data != null) {
            data = res.getData().data;
            setData(false);
        } else {
            binding.llOuter.setVisibility(View.GONE);
            binding.tvError.setVisibility(View.VISIBLE);
        }
    }
}
