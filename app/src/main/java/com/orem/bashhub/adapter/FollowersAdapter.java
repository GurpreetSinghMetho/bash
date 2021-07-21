package com.orem.bashhub.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.orem.bashhub.data.UsersListPOJO;
import com.orem.bashhub.databinding.ItemFollowersBinding;
import com.orem.bashhub.utils.Const;

import java.util.ArrayList;
import java.util.List;

public class FollowersAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context mContext;
    private List<UsersListPOJO.Data> list;
    private List<UsersListPOJO.Data> selected = new ArrayList<>();

    public FollowersAdapter(Context mContext, List<UsersListPOJO.Data> list) {
        this.mContext = mContext;
        this.list = list;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new Holder(ItemFollowersBinding.inflate(LayoutInflater.from(mContext), parent, false));
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

        ItemFollowersBinding binding;

        Holder(ItemFollowersBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind() {
            UsersListPOJO.Data item = list.get(getAdapterPosition());
            binding.setData(item);
            binding.executePendingBindings();
            binding.checkBox.setChecked(selected.contains(item));
            binding.tvNumber.setVisibility(View.GONE);
            binding.tvInvited.setVisibility(item.bash_invited.equals(Const.ONE) ? View.VISIBLE : View.GONE);
            binding.checkBox.setVisibility(item.bash_invited.equals(Const.ZERO) ? View.VISIBLE : View.GONE);
            binding.outer.setOnClickListener(v -> {
                if (!selected.contains(item)) selected.add(item);
                else selected.remove(item);
                notifyDataSetChanged();
            });
        }
    }

    public String getSelected() {
        String users = "";
        for (UsersListPOJO.Data item : selected)
            users = users.isEmpty() ? item.id : users + "," + item.id;
        return users;
    }

    public void sentInvitation() {
        for (UsersListPOJO.Data item : selected) {
            int position = list.indexOf(item);
            if (position != -1)
                list.get(position).setBash_invited(Const.ONE);
        }
        selected.clear();
        notifyDataSetChanged();
    }
}
