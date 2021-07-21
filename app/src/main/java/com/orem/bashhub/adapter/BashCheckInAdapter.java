package com.orem.bashhub.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.orem.bashhub.data.BashDetailsPOJO;
import com.orem.bashhub.databinding.AdapterCheckInPartyBinding;
import com.orem.bashhub.dialogs.DialogBashCheckIn;

import java.util.List;

public class BashCheckInAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context mContext;
    private List<BashDetailsPOJO> list;
    private DialogBashCheckIn dialog;
    private boolean isLive;

    public BashCheckInAdapter(Context mContext, List<BashDetailsPOJO> list, DialogBashCheckIn dialog, boolean isLive) {
        this.mContext = mContext;
        this.list = list;
        this.dialog = dialog;
        this.isLive = isLive;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new Holder(AdapterCheckInPartyBinding.inflate(LayoutInflater.from(mContext), parent, false));
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

        AdapterCheckInPartyBinding binding;

        Holder(AdapterCheckInPartyBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind() {
            binding.setData(list.get(getAdapterPosition()));
            binding.executePendingBindings();
            binding.llOuter.setOnClickListener(v -> dialog.openFragment(list.get(getAdapterPosition()), isLive));
        }
    }
}
