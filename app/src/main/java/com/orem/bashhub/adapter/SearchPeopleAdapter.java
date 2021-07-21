package com.orem.bashhub.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.orem.bashhub.data.UsersListPOJO;
import com.orem.bashhub.databinding.AdapterSearchPeopleBinding;
import com.orem.bashhub.fragment.SearchBashFragment;

import java.util.List;

public class SearchPeopleAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context mContext;
    private List<UsersListPOJO.Data> list;
    private SearchBashFragment fragment;

    public SearchPeopleAdapter(Context mContext, List<UsersListPOJO.Data> list, SearchBashFragment fragment) {
        this.mContext = mContext;
        this.list = list;
        this.fragment = fragment;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new Holder(AdapterSearchPeopleBinding.inflate(LayoutInflater.from(mContext), parent, false));
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

        AdapterSearchPeopleBinding binding;

        Holder(AdapterSearchPeopleBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind() {
            UsersListPOJO.Data item = list.get(getAdapterPosition());
            binding.setData(item);
            binding.executePendingBindings();
            binding.tvFollow.setOnClickListener(v -> fragment.apiFollowUser(getAdapterPosition()));
            binding.topLL.setOnClickListener(v -> fragment.gotoOtherUser(item.id));
        }
    }
}
