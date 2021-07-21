package com.orem.bashhub.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.orem.bashhub.R;
import com.orem.bashhub.activity.MainActivity;
import com.orem.bashhub.data.BashDetailsPOJO;
import com.orem.bashhub.databinding.AdapterBashHubListBinding;
import com.orem.bashhub.dialogs.DialogmapListBashes;
import com.orem.bashhub.fragment.BashDetailsWebFragment;
import com.orem.bashhub.fragment.BashDetialFragment;
import com.orem.bashhub.utils.Utils;

import java.util.List;

public class BashHubListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context mContext;
    private List<BashDetailsPOJO> list;
    private DialogmapListBashes dialog;

    public BashHubListAdapter(Context mContext, List<BashDetailsPOJO> list, DialogmapListBashes dialog) {
        this.mContext = mContext;
        this.list = list;
        this.dialog = dialog;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new Holder(AdapterBashHubListBinding.inflate(LayoutInflater.from(mContext), parent, false));
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

        AdapterBashHubListBinding binding;

        Holder(AdapterBashHubListBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind() {
            binding.setData(list.get(getAdapterPosition()));
            binding.executePendingBindings();
            binding.llOuter.setOnClickListener(v -> {
                dialog.dismiss();
                if (list.get(getAdapterPosition()).getCreatedFrom() == 1) {
                    BashDetialFragment fragment = new BashDetialFragment();
                    fragment.setData(list.get(getAdapterPosition()), true);
                    Utils.goToFragment(mContext, fragment, R.id.fragment_container);
                    ((MainActivity) mContext).binding.fragmentContainer.setVisibility(View.VISIBLE);
                } else {
                    BashDetailsWebFragment fragment = new BashDetailsWebFragment();
                    fragment.setData(list.get(getAdapterPosition()), true);
                    Utils.goToFragment(mContext, fragment, R.id.fragment_container);
                    ((MainActivity) mContext).binding.fragmentContainer.setVisibility(View.VISIBLE);

                }
            });
        }
    }
}