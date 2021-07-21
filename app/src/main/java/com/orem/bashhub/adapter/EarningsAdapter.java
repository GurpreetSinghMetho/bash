package com.orem.bashhub.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.orem.bashhub.R;
import com.orem.bashhub.data.HostHubPOJO;
import com.orem.bashhub.databinding.ItemEarningsBinding;
import com.orem.bashhub.utils.Utils;

import java.util.List;

public class EarningsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context mContext;
    private HostHubPOJO.Data data;
    private List<HostHubPOJO.Earning> list;

    public EarningsAdapter(Context mContext, HostHubPOJO.Data data) {
        this.mContext = mContext;
        this.data = data;
        this.list = data.earning;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new Holder(ItemEarningsBinding.inflate(LayoutInflater.from(mContext), parent, false));
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

        ItemEarningsBinding binding;

        Holder(ItemEarningsBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind() {
            HostHubPOJO.Earning item = list.get(getAdapterPosition());
            binding.setData(item);
            binding.pb.setMax((int) data.getHighestAmount());
            binding.pb.setProgress((int)item.earings);
            binding.pb.setProgressDrawable(mContext.getDrawable(getAdapterPosition() == data.getHighEarnPos() ? R.drawable.custom_progress_purple : R.drawable.custom_progress_light_blue));
        }
    }
}
