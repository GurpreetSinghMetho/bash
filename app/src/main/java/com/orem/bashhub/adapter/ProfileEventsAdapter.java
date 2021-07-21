package com.orem.bashhub.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.orem.bashhub.data.BashDetailsPOJO;
import com.orem.bashhub.databinding.ItemProfileEventsBinding;

import java.util.List;

public class ProfileEventsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context mContext;
    private List<BashDetailsPOJO> list;

    public ProfileEventsAdapter(Context mContext, List<BashDetailsPOJO> list) {
        this.mContext = mContext;
        this.list = list;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new Holder(ItemProfileEventsBinding.inflate(LayoutInflater.from(mContext), parent, false));
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

        ItemProfileEventsBinding binding;

        Holder(ItemProfileEventsBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind() {
            BashDetailsPOJO item = list.get(getAdapterPosition());
            binding.setData(item);
            binding.executePendingBindings();
        }
    }
}
