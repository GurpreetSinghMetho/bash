package com.orem.bashhub.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.orem.bashhub.data.UsersListPOJO;
import com.orem.bashhub.databinding.ItemFollowersBinding;

import java.util.ArrayList;
import java.util.List;

public class AddHostAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context mContext;
    private List<UsersListPOJO.Data> list;
    private List<UsersListPOJO.Data> selectedUser;

    public AddHostAdapter(Context mContext, List<UsersListPOJO.Data> list, List<UsersListPOJO.Data> selectedUser) {
        this.mContext = mContext;
        this.list = list;
        this.selectedUser = selectedUser;
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
            binding.checkBox.setChecked(isUserExists(item));
            binding.outer.setOnClickListener(v -> {
                if (!isUserExists(item)) selectedUser.add(item);
                else removeItem(item);
                notifyDataSetChanged();
            });
        }
    }

    private void removeItem(UsersListPOJO.Data item) {
        int position = -1;
        for (int i = 0; i < selectedUser.size(); i++) {
            if (selectedUser.get(i).id.equals(item.id)) {
                position = i;
                break;
            }
        }
        if (position != -1) selectedUser.remove(position);
        notifyDataSetChanged();
    }

    private boolean isUserExists(UsersListPOJO.Data item) {
        boolean isExist = false;
        for (int i = 0; i < selectedUser.size(); i++) {
            if (selectedUser.get(i).id.equals(item.id)) {
                isExist = true;
                break;
            }
        }
        return isExist;
    }

    public List<UsersListPOJO.Data> getSelectedUser() {
        return selectedUser;
    }

    public void submitList(List<UsersListPOJO.Data> list) {
        this.list = new ArrayList<>(list);
        notifyDataSetChanged();
    }
}
