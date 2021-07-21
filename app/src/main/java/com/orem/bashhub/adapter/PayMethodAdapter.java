package com.orem.bashhub.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.orem.bashhub.data.PayMethodPOJO;
import com.orem.bashhub.databinding.ItemPayOptionsBinding;
import com.orem.bashhub.dialogs.DialogPayMethod;
import com.orem.bashhub.interfaces.OnPayMethodPick;
import com.orem.bashhub.utils.Const;

import java.util.List;

public class PayMethodAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context mContext;
    private List<PayMethodPOJO> list;
    private OnPayMethodPick listener;
    private DialogPayMethod dialog;

    public PayMethodAdapter(Context mContext, OnPayMethodPick listener, DialogPayMethod dialog) {
        this.mContext = mContext;
        this.list = Const.getPayMethods(mContext);
        this.listener = listener;
        this.dialog = dialog;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new Holder(ItemPayOptionsBinding.inflate(LayoutInflater.from(mContext), parent, false));
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

        ItemPayOptionsBinding binding;

        Holder(ItemPayOptionsBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind() {
            binding.setData(list.get(getAdapterPosition()));
            binding.executePendingBindings();
            binding.llOuter.setOnClickListener(v -> {
                listener.onPayMethodPick(list.get(getAdapterPosition()));
                dialog.dismiss();
            });
        }
    }
}
