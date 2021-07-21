package com.orem.bashhub.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.orem.bashhub.databinding.ItemCostumesBinding;
import com.orem.bashhub.interfaces.OnCostumePick;
import com.orem.bashhub.utils.Const;
import com.orem.bashhub.utils.Utils;

import java.util.List;

public class CostumesAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context mContext;
    private int selected;
    private List<Integer> list;
    private OnCostumePick listener;

    public CostumesAdapter(Context mContext, OnCostumePick listener) {
        this.mContext = mContext;
        this.listener = listener;
        list = Const.getCostumes();
        selected = Const.getLoggedInUser(mContext).costume_id - 1;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new Holder(ItemCostumesBinding.inflate(LayoutInflater.from(mContext), parent, false));
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

        ItemCostumesBinding binding;

        Holder(ItemCostumesBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind() {
            Utils.loadImage(mContext, list.get(getAdapterPosition()), binding.ivCostume, 0);
            binding.ivStand.setVisibility(selected == getAdapterPosition() ? View.VISIBLE : View.GONE);
            binding.rlOuter.setOnClickListener(v -> {
                selected = getAdapterPosition();
                listener.OnCostumePick(getAdapterPosition(), list.get(getAdapterPosition()));
                notifyDataSetChanged();
            });
        }
    }
}
