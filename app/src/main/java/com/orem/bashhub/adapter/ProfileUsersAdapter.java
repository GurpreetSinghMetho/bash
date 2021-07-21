package com.orem.bashhub.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.orem.bashhub.R;
import com.orem.bashhub.data.UsersListPOJO;
import com.orem.bashhub.databinding.ItemProfileUserBinding;
import com.orem.bashhub.fragment.FollowersFragment;
import com.orem.bashhub.interfaces.OnBgApi;
import com.orem.bashhub.utils.Const;
import com.orem.bashhub.utils.Utils;

import java.util.List;

public class ProfileUsersAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context mContext;
    private List<UsersListPOJO.Data> list;
    private OnBgApi listener;
    private String type;

    public ProfileUsersAdapter(Context mContext, List<UsersListPOJO.Data> list, OnBgApi listener, String type) {
        this.mContext = mContext;
        this.list = list;
        this.listener = listener;
        this.type = type;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new Holder(ItemProfileUserBinding.inflate(LayoutInflater.from(mContext), parent, false));
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

        ItemProfileUserBinding binding;

        Holder(ItemProfileUserBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind() {
            UsersListPOJO.Data item = list.get(getAdapterPosition());
            binding.setData(item);
            binding.executePendingBindings();
            if (type.equals(Const.USER_FOLLOWING))
                binding.tvStatus.setText(item.following_me.equals(Const.ONE) ? mContext.getString(R.string.prompt_follows_you) : "");
            else
                binding.tvStatus.setText(mContext.getString(item.follow_me.equals(Const.ONE) ? R.string.prompt_following : R.string.not_following));
            binding.llOuter.setOnClickListener(v -> {
                FollowersFragment fragment = new FollowersFragment();
                fragment.setData(item.id);
                fragment.setListener(listener);
                Utils.goToFragment(mContext, fragment, R.id.fragment_container);
            });
        }
    }
}
