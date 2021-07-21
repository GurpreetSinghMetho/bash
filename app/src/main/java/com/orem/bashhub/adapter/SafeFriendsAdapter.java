package com.orem.bashhub.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.orem.bashhub.data.SearchUserPOJO;
import com.orem.bashhub.databinding.ItemRideFriendsBinding;

import java.util.List;

public class SafeFriendsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context mContext;
    private List<SearchUserPOJO.Data> list;

    public SafeFriendsAdapter(Context mContext, List<SearchUserPOJO.Data> list) {
        this.mContext = mContext;
        this.list = list;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new Holder(ItemRideFriendsBinding.inflate(LayoutInflater.from(mContext), parent, false));
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

        ItemRideFriendsBinding binding;

        Holder(ItemRideFriendsBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind() {
            binding.setData(list.get(getAdapterPosition()));
            binding.executePendingBindings();
            binding.ivDelete.setOnClickListener(v -> {
                list.remove(getAdapterPosition());
                notifyDataSetChanged();
            });
        }
    }
}
