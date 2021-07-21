package com.orem.bashhub.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.orem.bashhub.R;
import com.orem.bashhub.databinding.ItemGiveComplementBinding;
import com.orem.bashhub.utils.Const;
import com.orem.bashhub.utils.Utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GiveComplementAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context mContext;
    private List<Integer> icons;
    private List<String> names;
    private ArrayList<String> selected = new ArrayList<>();

    public GiveComplementAdapter(Context mContext) {
        this.mContext = mContext;
        icons = Const.getComplementIcons();
        names = Arrays.asList(mContext.getResources().getStringArray(R.array.array_complements));
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new Holder(ItemGiveComplementBinding.inflate(LayoutInflater.from(mContext), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Holder h = (Holder) holder;
        h.bind();
    }

    @Override
    public int getItemCount() {
        return icons.size();
    }

    class Holder extends RecyclerView.ViewHolder {

        ItemGiveComplementBinding binding;

        Holder(ItemGiveComplementBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind() {
            binding.ivImage.setImageResource(icons.get(getAdapterPosition()));
            binding.tvName.setText(names.get(getAdapterPosition()));
            binding.ivTick.setVisibility(selected.contains("" + (getAdapterPosition() + 1)) ? View.VISIBLE : View.GONE);

            binding.ivImage.setOnClickListener(v -> {
                if (selected.contains("" + (getAdapterPosition() + 1)))
                    selected.remove("" + (getAdapterPosition() + 1));
                else selected.add("" + (getAdapterPosition() + 1));
                notifyDataSetChanged();
            });
        }
    }

    public String getSelected() {
        return Utils.getStringFromArray(selected);
    }
}
