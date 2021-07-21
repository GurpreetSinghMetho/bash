package com.orem.bashhub.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.orem.bashhub.data.HostHubDetailPOJO;
import com.orem.bashhub.databinding.ItemAgeRangeBinding;

import java.util.List;

public class AgeRangeAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context mContext;
    private HostHubDetailPOJO.Data data;
    private List<HostHubDetailPOJO.Age_details> list;

    public AgeRangeAdapter(Context mContext, HostHubDetailPOJO.Data data) {
        this.mContext = mContext;
        this.data = data;
        this.list = data.age_details;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new Holder(ItemAgeRangeBinding.inflate(LayoutInflater.from(mContext), parent, false));
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

        ItemAgeRangeBinding binding;

        Holder(ItemAgeRangeBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind() {
            HostHubDetailPOJO.Age_details item = list.get(getAdapterPosition());
            binding.setData(item);
            binding.pbMale.setMax(data.getHighestAgeGraphValue());
            binding.pbFemale.setMax(data.getHighestAgeGraphValue());
            binding.pbMale.setProgress(item.male);
            binding.pbFemale.setProgress(item.fe_male);
            binding.view.setVisibility(getAdapterPosition() == list.size() - 1 ? View.GONE : View.VISIBLE);
        }
    }
}
