package com.orem.bashhub.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.orem.bashhub.R;
import com.orem.bashhub.activity.MainActivity;
import com.orem.bashhub.data.VenueListPOJO;
import com.orem.bashhub.databinding.VenueRowBinding;
import com.orem.bashhub.dialogs.DialogmapListBashes;
import com.orem.bashhub.fragment.MyBashVenuwDetail;
import com.orem.bashhub.fragment.VenueDetailsFragment;
import com.orem.bashhub.utils.Const;
import com.orem.bashhub.utils.Utils;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class VenuListAdapter extends RecyclerView.Adapter<VenuListAdapter.ViewHolder> {
    Context mContext;
    List<VenueListPOJO.Venue> list;
    DialogmapListBashes dialogmapListBashes;
String from;
    public VenuListAdapter(Context mContext, List<VenueListPOJO.Venue> list, DialogmapListBashes dialogmapListBashes,String from) {
        this.mContext = mContext;
        this.from = from;
        this.list = list;
        this.dialogmapListBashes = dialogmapListBashes;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(VenueRowBinding.inflate(LayoutInflater.from(mContext), parent, false));

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.binding.setData(list.get(position));
        holder.binding.executePendingBindings();
        Utils.loadImage(mContext, Const.IMAGE_BASE_EVENT + list.get(position).getImage(), holder.binding.mVenuImage, R.drawable.placeholder);
        holder.binding.llOuter.setOnClickListener(v -> {
            if (dialogmapListBashes != null) {
                dialogmapListBashes.dismiss();
                VenueDetailsFragment fragment = new VenueDetailsFragment();
                fragment.setData(list.get(position), true);
                Utils.goToFragment(mContext, fragment, R.id.fragment_container);
                ((MainActivity) mContext).binding.fragmentContainer.setVisibility(View.VISIBLE);
            } else {
                if (from.equalsIgnoreCase("1")){
                    VenueDetailsFragment fragment = new VenueDetailsFragment();
                    fragment.setData(list.get(position), true);
                    Utils.goToFragment(mContext, fragment, R.id.fragment_container);
                    ((MainActivity) mContext).binding.fragmentContainer.setVisibility(View.VISIBLE);
                }else {
                    MyBashVenuwDetail fragment = new MyBashVenuwDetail();
                    fragment.setData(list.get(position), true);
                    Utils.goToFragment(mContext, fragment, R.id.fragment_container);
                    ((MainActivity) mContext).binding.fragmentContainer.setVisibility(View.VISIBLE);
                }

            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        VenueRowBinding binding;

        public ViewHolder(@NonNull VenueRowBinding itemView) {
            super(itemView.getRoot());
            binding = itemView;
        }
    }
}