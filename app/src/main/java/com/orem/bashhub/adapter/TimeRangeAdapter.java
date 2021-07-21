package com.orem.bashhub.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.orem.bashhub.R;
import com.orem.bashhub.data.HostHubDetailPOJO;
import com.orem.bashhub.databinding.ItemTimeRangeBinding;

import java.util.List;

public class TimeRangeAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context mContext;
    private HostHubDetailPOJO.Data data;
    private List<HostHubDetailPOJO.Popular_time> list;

    public TimeRangeAdapter(Context mContext, HostHubDetailPOJO.Data data) {
        this.mContext = mContext;
        this.data = data;
        this.list = data.popular_time;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new Holder(ItemTimeRangeBinding.inflate(LayoutInflater.from(mContext), parent, false));
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

        ItemTimeRangeBinding binding;

        Holder(ItemTimeRangeBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind() {
            HostHubDetailPOJO.Popular_time item = list.get(getAdapterPosition());
            binding.setData(item);
            binding.pb.setMax(data.getHighestTimeGraphValue());
            binding.pb.setProgress(item.persons);
            binding.pb.setProgressDrawable(mContext.getDrawable(getAdapterPosition() == data.getHighTimePos() ? R.drawable.custom_progress_purple : R.drawable.custom_progress_light_blue));
        }
    }
}
