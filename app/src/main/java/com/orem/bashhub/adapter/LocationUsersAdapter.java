package com.orem.bashhub.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.orem.bashhub.data.UsersListPOJO;
import com.orem.bashhub.databinding.ItemLocationUsersBinding;
import com.orem.bashhub.utils.Const;

import java.util.ArrayList;
import java.util.List;

public class LocationUsersAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context mContext;
    private List<UsersListPOJO.Data> list;

    public LocationUsersAdapter(Context mContext, List<UsersListPOJO.Data> list) {
        this.mContext = mContext;
        this.list = list;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new Holder(ItemLocationUsersBinding.inflate(LayoutInflater.from(mContext), parent, false));
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

        ItemLocationUsersBinding binding;

        Holder(ItemLocationUsersBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind() {
            UsersListPOJO.Data item = list.get(getAdapterPosition());
            binding.setData(item);
            binding.executePendingBindings();
            binding.outer.setOnClickListener(v -> item.setBlock(item.getBlock().equals(Const.ONE) ? Const.ZERO : Const.ONE));
        }
    }

    public void submitList(List<UsersListPOJO.Data> list) {
        this.list = new ArrayList<>(list);
        notifyDataSetChanged();
    }
}
