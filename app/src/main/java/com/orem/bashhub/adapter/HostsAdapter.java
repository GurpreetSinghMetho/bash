package com.orem.bashhub.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.orem.bashhub.R;
import com.orem.bashhub.data.BashDetailsPOJO;
import com.orem.bashhub.databinding.ItemHostsBinding;
import com.orem.bashhub.fragment.FollowersFragment;
import com.orem.bashhub.utils.Utils;

import java.util.List;

public class HostsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context mContext;
    private List<BashDetailsPOJO.Hosts> list;
    String from;

    public HostsAdapter(Context mContext, List<BashDetailsPOJO.Hosts> list,String from) {
        this.mContext = mContext;
        this.list = list;
        this.from = from;

    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new Holder(ItemHostsBinding.inflate(LayoutInflater.from(mContext), parent, false));
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

        ItemHostsBinding binding;

        Holder(ItemHostsBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind() {

            BashDetailsPOJO.Hosts item = list.get(getAdapterPosition());
            if (from.equalsIgnoreCase("web")){
                binding.tvName.setTextColor(mContext.getResources().getColor(R.color.black));
            }
            binding.tvName.setText(item.username);
            binding.view.setVisibility(getAdapterPosition() == list.size() - 1 ? View.GONE : View.VISIBLE);
            Utils.underlineTextView(binding.tvName);
            binding.tvName.setOnClickListener(v -> {
                FollowersFragment fragment = new FollowersFragment();
                fragment.setData(item.id);
                Utils.goToFragment(mContext, fragment, R.id.fragment_container);
            });
        }
    }
}
