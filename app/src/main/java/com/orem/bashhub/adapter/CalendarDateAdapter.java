package com.orem.bashhub.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.orem.bashhub.R;
import com.orem.bashhub.data.DatesPOJO;
import com.orem.bashhub.interfaces.OnDateChange;
import com.orem.bashhub.utils.Utils;

import java.util.Date;
import java.util.List;


public class CalendarDateAdapter extends RecyclerView.Adapter<CalendarDateAdapter.DateHolderView> {

    private Context mContext;
    private List<DatesPOJO> mDatesPOJOList;
    private Date selectedDate;
    private OnDateChange listener;

    public CalendarDateAdapter(Context mContext, List<DatesPOJO> mDatesPOJOList, Date selectedDate, OnDateChange listener) {
        this.mContext = mContext;
        this.mDatesPOJOList = mDatesPOJOList;
        this.selectedDate = selectedDate;
        this.listener = listener;
    }

    @NonNull
    @Override
    public DateHolderView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.view_calendar_date, parent, false);
        return new DateHolderView(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DateHolderView holder, int position) {
        DatesPOJO mDate = mDatesPOJOList.get(position);
        holder.tvDate.setText(mDate.getDate());
        if (mDate.getDateObject() != null && Utils.isValidDay(mDate.getDateObject()) && !mDate.getDate().isEmpty()) {
            holder.tvDate.setBackgroundResource(Utils.isDateEquals(mDate.getDateObject(), selectedDate) ? R.drawable.custom_white_circle : R.drawable.custom_transparent_circle);
            holder.tvDate.setTextColor(ContextCompat.getColor(mContext, Utils.isDateEquals(mDate.getDateObject(), selectedDate) ? R.color.lightpurple : R.color.white));
        } else {
            holder.tvDate.setTextColor(ContextCompat.getColor(mContext, R.color.gray));
            holder.tvDate.setBackgroundResource(R.drawable.custom_transparent_circle);
        }

        holder.tvDate.setOnClickListener(v -> {
            if (mDate.getDateObject() != null && Utils.isValidDay(mDate.getDateObject()) && !Utils.isDateEquals(selectedDate, mDate.getDateObject())) {
                listener.onDateChange(mDate.getDateObject());
                selectedDate = mDate.getDateObject();
                notifyDataSetChanged();
            }
        });
    }

    @Override
    public int getItemCount() {
        return mDatesPOJOList.size();
    }

    class DateHolderView extends RecyclerView.ViewHolder {

        TextView tvDate;

        DateHolderView(View view) {
            super(view);
            mContext = itemView.getContext();
            tvDate = itemView.findViewById(R.id.tvDate);
        }
    }
}
