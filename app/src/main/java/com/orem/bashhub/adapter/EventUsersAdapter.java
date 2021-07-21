package com.orem.bashhub.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.orem.bashhub.R;
import com.orem.bashhub.data.BashDetailsPOJO;
import com.orem.bashhub.data.UsersListPOJO;
import com.orem.bashhub.databinding.ItemEventUsersBinding;
import com.orem.bashhub.fragment.FollowersFragment;
import com.orem.bashhub.interfaces.OnBgApi;
import com.orem.bashhub.utils.Const;
import com.orem.bashhub.utils.Utils;

import java.util.List;

public class EventUsersAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    BashDetailsPOJO bashData;
    private Context mContext;
    private List<UsersListPOJO.Data> list;
    private boolean isPaid;
    private OnBgApi listener;
    private String loggedInUser;

    public EventUsersAdapter(Context mContext, List<UsersListPOJO.Data> list, boolean isPaid, OnBgApi listener, BashDetailsPOJO bashData) {
        this.mContext = mContext;
        this.list = list;
        this.isPaid = isPaid;
        this.listener = listener;
        this.bashData = bashData;
        loggedInUser = Const.getLoggedInUserID(mContext);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new Holder(ItemEventUsersBinding.inflate(LayoutInflater.from(mContext), parent, false));
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

    private void messageClick(String numbers) {
        try {
            Intent sendIntent = new Intent(Intent.ACTION_VIEW);
            sendIntent.setType("vnd.android-dir/mms-sms");
            sendIntent.putExtra("sms_body", "");
            sendIntent.putExtra("address", numbers);
            mContext.startActivity(sendIntent);
        } catch (Exception e) {
            Utils.showMessageDialog(mContext, "", mContext.getString(R.string.failed_to_find_msg_app));
        }
    }

    class Holder extends RecyclerView.ViewHolder {

        ItemEventUsersBinding binding;

        Holder(ItemEventUsersBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind() {
            UsersListPOJO.Data item = list.get(getAdapterPosition());
            binding.setData(item);
            boolean paid = false;
            if (isPaid == true) {
                if (bashData.isIamHost(item.id)) {
                    paid = false;
                } else {
                    paid = true;
                }
            }
            binding.setIsPaid(paid);
            binding.executePendingBindings();
            binding.tvStatus1.setText(item.following_me.equals(Const.ONE) ? mContext.getString(R.string.prompt_follows_you) : "");
            binding.tvStatus.setVisibility(item.id.equals(loggedInUser) ? View.GONE : View.VISIBLE);
            binding.tvStatus1.setVisibility(item.id.equals(loggedInUser) || !item.following_me.equals(Const.ONE) ? View.GONE : View.VISIBLE);
            if (!item.id.equals(loggedInUser)) {
                if (bashData.isIamHost(Const.getLoggedInUserID(mContext))) {
                    binding.ivSend.setVisibility(View.VISIBLE);
                } else {
                    if (item.getFollow().equalsIgnoreCase("1") && item.following_me.equalsIgnoreCase("1")) {
                        binding.ivSend.setVisibility(View.VISIBLE);
                    } else
                        binding.ivSend.setVisibility(View.INVISIBLE);
                }
            }
            binding.ivSend.setOnClickListener(v -> {
                messageClick(item.country_code + item.phone_number);
            });
            binding.outer.setOnClickListener(view -> {
                FollowersFragment fragment = new FollowersFragment();
                fragment.setData(item.id);
                fragment.setListener(listener);
                Utils.goToFragment(mContext, fragment, R.id.fragment_container);
            });
        }
    }
}
