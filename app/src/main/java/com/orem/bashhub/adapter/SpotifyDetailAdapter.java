package com.orem.bashhub.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.orem.bashhub.R;
import com.orem.bashhub.data.SpotifyDetailPojo;
import com.orem.bashhub.databinding.ItemAddImagesBinding;
import com.orem.bashhub.utils.Utils;

import java.util.List;

public class SpotifyDetailAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    String from;
    private Context mContext;
    private List<SpotifyDetailPojo.Image> list;

    public SpotifyDetailAdapter(Context mContext, List<SpotifyDetailPojo.Image> list, String from) {
        this.mContext = mContext;
        this.list = list;
        this.from = from;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemAddImagesBinding itemBinding =
                ItemAddImagesBinding.inflate(LayoutInflater.from(mContext), parent, false);
        return new Holder(itemBinding);
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

        ItemAddImagesBinding binding;

        Holder(ItemAddImagesBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        @SuppressLint("SetTextI18n")
        public void bind() {
            Utils.loadImage(mContext, list.get(getAdapterPosition()).getImage(), binding.ivImage, R.drawable.places_autocomplete_toolbar_shadow);
            binding.ivDelete.setVisibility(View.GONE);
            binding.addNew.setVisibility(View.GONE);
            binding.oldImage.setVisibility(View.VISIBLE);
            if (list.get(getAdapterPosition()).getName().equalsIgnoreCase(""))
                binding.mName.setText("");
            else
                binding.mName.setText(list.get(getAdapterPosition()).getName());
            if (from.equalsIgnoreCase("detail"))
                binding.mName.setTextColor(mContext.getResources().getColor(R.color.white));
            binding.oldImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Utils.intentToBrowser(mContext, list.get(getAdapterPosition()).getUrl());
                }
            });
        }
    }
}
