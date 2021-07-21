package com.orem.bashhub.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.orem.bashhub.R;
import com.orem.bashhub.data.UberProductsPOJO;
import com.orem.bashhub.databinding.ItemUberProductsBinding;

import java.util.List;

public class UberProductsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context mContext;
    private List<UberProductsPOJO.Products> list;
    private int selected = -1;

    public UberProductsAdapter(Context mContext, List<UberProductsPOJO.Products> list) {
        this.mContext = mContext;
        this.list = list;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new Holder(ItemUberProductsBinding.inflate(LayoutInflater.from(mContext), parent, false));
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

        ItemUberProductsBinding binding;

        Holder(ItemUberProductsBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind() {
            binding.setData(list.get(getAdapterPosition()));
            binding.executePendingBindings();
            binding.ivCar.setBackgroundResource(selected == getAdapterPosition() ? R.drawable.custom_white_circle_black_border :
                    R.drawable.custom_white_circle);
            binding.ivCar.setOnClickListener(v -> {
                selected = getAdapterPosition();
                notifyDataSetChanged();
            });
        }
    }

    public UberProductsPOJO.Products getProduct() {
        return selected == -1 ? null : list.get(selected);
    }
}
