package com.orem.bashhub.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.DividerItemDecoration;

import com.orem.bashhub.R;
import com.orem.bashhub.adapter.CalendarDateAdapter;
import com.orem.bashhub.adapter.TodayUpcomingAdapter;
import com.orem.bashhub.data.BashDetailsPOJO;
import com.orem.bashhub.data.CalendarBashPOJO;
import com.orem.bashhub.data.DatesPOJO;
import com.orem.bashhub.data.UserPOJO;
import com.orem.bashhub.databinding.FragmentCalendarBinding;
import com.orem.bashhub.interfaces.OnDateChange;
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

public class CalendarFragment extends BaseFragment {

    private FragmentCalendarBinding binding;
    private Calendar mCalendar;
    private OnDateChange listener;
    private Date selectedDate;
    private TodayUpcomingAdapter todayAdapter, upcomingAdapter;
    private CalendarBashPOJO.Data data;
    private List<BashDetailsPOJO> todayList = new ArrayList<>(), upcomingList = new ArrayList<>();
    private BashDetailsPOJO selected;

    private OnDateChange innerListener = new OnDateChange() {
        @Override
        public void onDateChange(Date date) {
            selectedDate = date;
            setData();
            apiGetBash();
        }
    };

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_calendar, container, false);
        init();
        return binding.getRoot();
    }

    private void init() {
        Utils.underlineTextView(binding.tvTodayMore);
        Utils.underlineTextView(binding.tvUpcomingMore);
        binding.backIV.setOnClickListener(this);
        binding.ivPreviousMonth.setOnClickListener(this);
        binding.ivNextMonth.setOnClickListener(this);
        binding.tvMap.setOnClickListener(this);
        binding.llToday.setOnClickListener(this);
        binding.tvTodayMore.setOnClickListener(this);
        binding.tvUpcomingMore.setOnClickListener(this);

        todayAdapter = new TodayUpcomingAdapter(mContext, todayList, this);
        binding.rvTodayBashes.addItemDecoration(new DividerItemDecoration(mContext, DividerItemDecoration.VERTICAL));
        binding.rvTodayBashes.setAdapter(todayAdapter);

        upcomingAdapter = new TodayUpcomingAdapter(mContext, upcomingList, this);
        binding.rvUpcomingBashes.addItemDecoration(new DividerItemDecoration(mContext, DividerItemDecoration.VERTICAL));
        binding.rvUpcomingBashes.setAdapter(upcomingAdapter);

        setCalendarData(mCalendar.get(Calendar.MONTH));
        setData();
        apiGetBash();
    }

    public void setData(Calendar mCalendar, OnDateChange listener) {
        this.mCalendar = Calendar.getInstance();
        this.mCalendar.setTime(mCalendar.getTime());
        this.listener = listener;
        selectedDate = mCalendar.getTime();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.backIV:
                Objects.requireNonNull(getActivity()).onBackPressed();
                break;
            case R.id.ivNextMonth:
                setCalendarData(mCalendar.get(Calendar.MONTH) + 1);
                break;
            case R.id.ivPreviousMonth:
                if (Utils.isValidMonth(mCalendar.getTime()))
                    setCalendarData(mCalendar.get(Calendar.MONTH) - 1);
                break;
            case R.id.tvMap:
                listener.onDateChange(selectedDate);
                binding.backIV.performClick();
                break;
            case R.id.llToday:
                mCalendar = Calendar.getInstance();
                selectedDate = mCalendar.getTime();
                setCalendarData(mCalendar.get(Calendar.MONTH));
                setData();
                apiGetBash();
                break;
            case R.id.tvTodayMore:
            case R.id.tvUpcomingMore:
                setBash(true);
                break;
        }
    }

    private void setCalendarData(int monthValue) {
        List<DatesPOJO> mDatesPOJOList = new ArrayList<>();
        mCalendar.set(Calendar.MONTH, monthValue);
        mCalendar.set(Calendar.DAY_OF_MONTH, 1);
        SimpleDateFormat mf = new SimpleDateFormat("MMMM,yyyy", Locale.getDefault());
        binding.tvMonth.setText(String.format("%s", mf.format(mCalendar.getTime())));
        // get and add days to the list
        int lastDay = mCalendar.getActualMaximum(Calendar.DAY_OF_MONTH);
        SimpleDateFormat df = new SimpleDateFormat("d", Locale.getDefault());
        SimpleDateFormat wf = new SimpleDateFormat("E", Locale.getDefault());

        for (int i = 1; i < mCalendar.get(Calendar.DAY_OF_WEEK); i++) {
            mDatesPOJOList.add(new DatesPOJO("", "", null));
        }

        for (int i = 1; i <= lastDay; i++) {
            mCalendar.set(Calendar.DAY_OF_MONTH, i);
            String date = df.format(mCalendar.getTime());
            String day = wf.format(mCalendar.getTime());
            mDatesPOJOList.add(new DatesPOJO(date, day, mCalendar.getTime()));
        }

        CalendarDateAdapter mCalendarAdapter = new CalendarDateAdapter(mContext, mDatesPOJOList, selectedDate, innerListener);
        binding.rvCalendar.setAdapter(mCalendarAdapter);
    }

    private void setData() {
        binding.llToday.setVisibility(Utils.isDateEquals(Calendar.getInstance().getTime(), selectedDate) ? View.GONE : View.VISIBLE);
        binding.tvSelectedDate.setText(Utils.isDateEquals(Calendar.getInstance().getTime(), selectedDate) ? getString(R.string.today) :
                Utils.getFullDate(selectedDate));
    }

    private void setBash(boolean isAll) {
        binding.llTodayOuter.setVisibility(data.today.size() > 0 ? View.VISIBLE : View.GONE);
        binding.cvTodayOuter.setVisibility(data.today.size() > 0 ? View.VISIBLE : View.GONE);
        binding.tvUpcoming.setVisibility(data.upcomming.size() > 0 ? View.VISIBLE : View.GONE);
        binding.cvUpcoming.setVisibility(data.upcomming.size() > 0 ? View.VISIBLE : View.GONE);
        binding.tvTodayMore.setVisibility(data.today.size() < 6 || isAll ? View.GONE : View.VISIBLE);
        binding.tvUpcomingMore.setVisibility(data.upcomming.size() < 6 || isAll ? View.GONE : View.VISIBLE);
        binding.llOuter.setVisibility(data.today.size() > 0 || data.upcomming.size() > 0 ? View.VISIBLE : View.GONE);
        binding.tvNoData.setVisibility(data.today.size() > 0 || data.upcomming.size() > 0 ? View.GONE : View.VISIBLE);

        todayList.clear();
        if (data.today.size() > 5 && !isAll) {
            for (int i = 0; i < 5; i++)
                todayList.add(data.today.get(i));
        } else {
            todayList.addAll(data.today);
        }
        todayAdapter.notifyDataSetChanged();

        upcomingList.clear();
        if (data.upcomming.size() > 5 && !isAll) {
            for (int i = 0; i < 5; i++)
                upcomingList.add(data.upcomming.get(i));
        } else {
            upcomingList.addAll(data.upcomming);
        }
        upcomingAdapter.notifyDataSetChanged();
    }

    private void apiGetBash() {
        binding.tvNoData.setVisibility(View.GONE);
        binding.llOuter.setVisibility(View.GONE);
        if (mTinyDb.getString(Const.SAVED_LAT).isEmpty()) {
            Utils.showToast(mContext, getString(R.string.failed_to_get_location));
        } else {
            registerEventBus();
            EventBus.getDefault().post(new Events.RequestCalenderBash(mContext, Const.apiCalendarBash(mContext,
                    Utils.isDateEquals(Calendar.getInstance().getTime(), selectedDate) ? "" : Utils.getFullDate(selectedDate),
                    Utils.isDateEquals(Calendar.getInstance().getTime(), selectedDate) ? Const.ONE : ""), true, false));
        }
    }

    public void apiCrashBash(BashDetailsPOJO item) {
        if (item.isIamHost(Const.getLoggedInUserID(mContext))) {
//            Utils.showToast(mContext, getString(R.string.crash_bash_host_error));
            if (item.getCreatedFrom() == 1) {
                BashDetialFragment fragment = new BashDetialFragment();
                fragment.setData(item, true);
                Utils.goToFragment(mContext, fragment, R.id.fragment_container);
            } else {
                BashDetailsWebFragment fragment = new BashDetailsWebFragment();
                fragment.setData(item, true);
                Utils.goToFragment(mContext, fragment, R.id.fragment_container);
            }
        } else {
            if (Double.parseDouble(item.charge) > 0) {
                if (item.getCreatedFrom() == 1) {
                    BashDetialFragment fragment = new BashDetialFragment();
                    fragment.setData(item, true);
                    Utils.goToFragment(mContext, fragment, R.id.fragment_container);
                } else {
                    BashDetailsWebFragment fragment = new BashDetailsWebFragment();
                    fragment.setData(item, true);
                    Utils.goToFragment(mContext, fragment, R.id.fragment_container);
                }
            } else {
                selected = item;
                registerEventBus();
                EventBus.getDefault().post(new Events.RequestCrashBash1(mContext, Const.apiCrashBash(mContext, item.id), true, false));
            }
        }
    }

    @Subscribe
    public void apiGetBashRes(Events.GetCalendarBashData res) {
        unRegisterEventBus();
        data = res.getData().data;
        setBash(false);
    }

    @Subscribe
    public void apiCrashBashRes(Events.GetCrashBashData1 res) {
        unRegisterEventBus();
        UserPOJO.Data user = Const.getLoggedInUser(mContext);
        user.setToday_bash_count(res.getData().data.count);
        Const.setLoggedInUser(mContext, user);
        mLiveModel.getUserLiveData().setValue(user);
        Utils.showToast(mContext, res.getData().mesg);

        int today = todayList.indexOf(selected);
        if (today != -1) todayList.get(today).setIs_crash(Const.ONE);
        todayAdapter.notifyDataSetChanged();
        int upcoming = upcomingList.indexOf(selected);
        if (upcoming != -1) upcomingList.get(upcoming).setIs_crash(Const.ONE);
        upcomingAdapter.notifyDataSetChanged();
    }
}
