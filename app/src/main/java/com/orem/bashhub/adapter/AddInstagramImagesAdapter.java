package com.orem.bashhub.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.orem.bashhub.R;
import com.orem.bashhub.data.SpotifyListPOJO;
import com.orem.bashhub.databinding.ItemAddImagesBinding;
import com.orem.bashhub.dialogs.DialogPlaylist;
import com.orem.bashhub.utils.Utils;

import java.util.ArrayList;
import java.util.List;

public class AddInstagramImagesAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public boolean isClickable = true;
    SpotifyListPOJO pojo1;
    private Context mContext;
    private List<SpotifyListPOJO.Item> list;

    public AddInstagramImagesAdapter(Context mContext, List<SpotifyListPOJO.Item> list, SpotifyListPOJO pojo1) {
        this.mContext = mContext;
        this.list = list;
        this.pojo1 = pojo1;
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
        if (list.size() > 5) {
            return 5;
        } else
            return list.size()/* < Const.DEFAULT_MAX_IMAGES ? list.size() + 1 : list.size()*/;
    }

    class Holder extends RecyclerView.ViewHolder {

        ItemAddImagesBinding binding;

        Holder(ItemAddImagesBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        @SuppressLint("SetTextI18n")
        public void bind() {

            if (list.get(getAdapterPosition()).getImages().size() > 0)
                if (list.get(getAdapterPosition()).getImages().get(0).getUrl().equalsIgnoreCase("")) {
                    binding.ivDelete.setVisibility(View.GONE);
                    binding.addNew.setVisibility(View.VISIBLE);
                    binding.oldImage.setVisibility(View.GONE);
                    binding.mName.setText("");
                    binding.mName.setVisibility(View.GONE);
                } else {
                    Utils.loadImage(mContext, list.get(getAdapterPosition()).getImages().get(0).getUrl(), binding.ivImage, R.drawable.places_autocomplete_toolbar_shadow);
                    binding.ivDelete.setVisibility(View.VISIBLE);
                    binding.addNew.setVisibility(View.GONE);
                    binding.oldImage.setVisibility(View.VISIBLE);
                    if (list.get(getAdapterPosition()).getName().equalsIgnoreCase(""))
                        binding.mName.setText("");
                    else
                        binding.mName.setText(list.get(getAdapterPosition()).getName());
                }
            else {
                binding.ivDelete.setVisibility(View.GONE);
                binding.addNew.setVisibility(View.VISIBLE);
                binding.oldImage.setVisibility(View.GONE);
            }
            binding.ivDelete.setOnClickListener(v -> {
                if (isClickable) {
                    SpotifyListPOJO.Item item1 = new SpotifyListPOJO.Item();
                    item1.setHref("");
                    item1.setId("");
                    item1.setName("");
                    item1.setSelected(false);
                    SpotifyListPOJO.Image image = new SpotifyListPOJO.Image();
                    image.setHeight(300);
                    image.setWidth(200);
                    image.setUrl("");
                    ArrayList<SpotifyListPOJO.Image> imageArrayList = new ArrayList<>();
                    imageArrayList.add(image);
                    item1.setImages(imageArrayList);
                    if (pojo1 != null)
                        if (pojo1.getItems() != null)
                            for (int j = 0; j < pojo1.items.size(); j++) {
                                if (pojo1.getItems().get(j).getId().equalsIgnoreCase(list.get(getAdapterPosition()).getId())) {
                                    pojo1.getItems().get(j).setSelected(false);
                                }
                            }
                    list.set(getAdapterPosition(), item1);
                    notifyDataSetChanged();
                }

            });
            binding.addNew.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (isClickable) {
                        if (pojo1 == null) {
                            Utils.showToast(mContext, mContext.getResources().getString(R.string.please_connect_spotify_account));
                        } else if (pojo1.items == null) {
                            Utils.showToast(mContext, mContext.getResources().getString(R.string.please_connect_spotify_account));
                        } else {
                       /* for (int i = 0; i < list.size(); i++) {
                            if (!list.get(i).isSelected()) {
                            }
                        }*/
                            DialogPlaylist dialog = new DialogPlaylist(mContext,pojo1.items);
                            dialog.show(((AppCompatActivity) mContext).getSupportFragmentManager(), dialog.getTag());
                        }
                    }
                }
            });
        }
    }
}