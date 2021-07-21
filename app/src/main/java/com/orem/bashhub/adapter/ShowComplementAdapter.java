package com.orem.bashhub.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.orem.bashhub.data.ComplementPOJO;
import com.orem.bashhub.databinding.ItemShowComplementBinding;

import java.util.List;

public class ShowComplementAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context mContext;
    private List<ComplementPOJO> list;

    public ShowComplementAdapter(Context mContext, List<ComplementPOJO> list) {
        this.mContext = mContext;
        this.list = list;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new Holder(ItemShowComplementBinding.inflate(LayoutInflater.from(mContext), parent, false));
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

        ItemShowComplementBinding binding;

        Holder(ItemShowComplementBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind() {
            ComplementPOJO item = list.get(getAdapterPosition());
            binding.ivImage.setImageResource(item.getIcon());
            binding.tvName.setText(item.getName());
            binding.tvCount.setText(item.getCount());
        }
    }
}
