package com.orem.bashhub.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.orem.bashhub.data.HostHubPOJO;
import com.orem.bashhub.databinding.ItemAttendanceInfoBinding;

import java.util.List;

public class AttendanceAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context mContext;
    private HostHubPOJO.Data data;
    private List<HostHubPOJO.Attendance> list;

    public AttendanceAdapter(Context mContext, HostHubPOJO.Data data) {
        this.mContext = mContext;
        this.data = data;
        this.list = data.attendance;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new Holder(ItemAttendanceInfoBinding.inflate(LayoutInflater.from(mContext), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Holder h = (Holder) holder;
        h.bind();
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class Holder extends RecyclerView.ViewHolder {

        ItemAttendanceInfoBinding binding;

        Holder(ItemAttendanceInfoBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind() {
            HostHubPOJO.Attendance item = list.get(getAdapterPosition());
            binding.setData(item);
            binding.pbMale.setMax(data.getHighestGraphAttendance());
            binding.pbFemale.setMax(data.getHighestGraphAttendance());
            binding.pbMale.setProgress(item.male);
            binding.pbFemale.setProgress(item.fe_male);
            binding.view.setVisibility(getAdapterPosition() == list.size() - 1 ? View.GONE : View.VISIBLE);
        }
    }
}
