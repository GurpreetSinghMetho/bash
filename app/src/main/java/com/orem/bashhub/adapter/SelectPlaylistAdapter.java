package com.orem.bashhub.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.orem.bashhub.R;
import com.orem.bashhub.data.SpotifyListPOJO;
import com.orem.bashhub.databinding.ItemSelectPlaylistBinding;
import com.orem.bashhub.dialogs.DialogPlaylist;
import com.orem.bashhub.utils.Utils;

import java.util.ArrayList;
import java.util.List;

public class SelectPlaylistAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    int count = 0;
    private Context mContext;
    private List<SpotifyListPOJO.Item> list;

    public SelectPlaylistAdapter(Context mContext, List<SpotifyListPOJO.Item> list) {
        this.mContext = mContext;
        this.list = list;
        setHasStableIds(true);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemSelectPlaylistBinding itemBinding =
                ItemSelectPlaylistBinding.inflate(LayoutInflater.from(mContext), parent, false);
        return new Holder(itemBinding);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Holder h = (Holder) holder;
        h.bind();
    }

    public void submitList(List<SpotifyListPOJO.Item> list) {
        this.list = new ArrayList<>(list);
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class Holder extends RecyclerView.ViewHolder {

        ItemSelectPlaylistBinding binding;

        Holder(ItemSelectPlaylistBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        @SuppressLint("SetTextI18n")
        public void bind() {

            if (list.get(getAdapterPosition()).getImages().size() > 0)
                if (list.get(getAdapterPosition()).getImages().get(0).getUrl().equalsIgnoreCase("")) {
                    binding.addNew.setVisibility(View.VISIBLE);
                    binding.oldImage.setVisibility(View.GONE);
                    binding.mName.setText("");
                } else {
                    Utils.loadImage(mContext, list.get(getAdapterPosition()).getImages().get(0).getUrl(), binding.ivImage, R.drawable.places_autocomplete_toolbar_shadow);
                    binding.addNew.setVisibility(View.GONE);
                    binding.oldImage.setVisibility(View.VISIBLE);
                    if (list.get(getAdapterPosition()).getName().equalsIgnoreCase(""))
                        binding.mName.setText("");
                    else
                        binding.mName.setText(list.get(getAdapterPosition()).getName());
                    if (list.get(getAdapterPosition()).isSelected()) {
                        count = count + 1;
                        binding.mName.setChecked(true);
                    }
                }
            else {
                binding.addNew.setVisibility(View.VISIBLE);
                binding.oldImage.setVisibility(View.GONE);
            }
            binding.mName.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (count == 5) {

                        if (list.get(getAdapterPosition()).isSelected()) {
                            count = count - 1;
                            DialogPlaylist.items.get(getAdapterPosition()).setSelected(false);
                        } else {
                            Utils.showToast(mContext, "Cannot select more than 5 playlist's.");
                            binding.mName.setChecked(false);
                        }
                    } else {
                        if (list.get(getAdapterPosition()).isSelected()) {
                            count = count - 1;
                            DialogPlaylist.items.get(getAdapterPosition()).setSelected(false);
                        } else {
                            DialogPlaylist.items.get(getAdapterPosition()).setSelected(true);
                            count = count + 1;
                        }
                    }

                }
            });

        }
    }
}

