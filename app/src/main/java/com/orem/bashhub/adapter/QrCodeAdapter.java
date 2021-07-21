package com.orem.bashhub.adapter;

import android.content.Context;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.orem.bashhub.data.BashDetailsPOJO;
import com.orem.bashhub.databinding.ItemQrcodeBinding;

import java.util.List;

public class QrCodeAdapter extends PagerAdapter {

    private Context mContext;
    private List<BashDetailsPOJO.QrTicket> list;

    public QrCodeAdapter(Context mContext, List<BashDetailsPOJO.QrTicket> list) {
        this.mContext = mContext;
        this.list = list;
    }

    public int getCount() {
        return list.size();
    }

    @NonNull
    public Object instantiateItem(@NonNull ViewGroup collection, final int position) {
        ItemQrcodeBinding itemBinding =
                ItemQrcodeBinding.inflate(LayoutInflater.from(mContext), collection, false);
        itemBinding.setData(list.get(position));
        itemBinding.executePendingBindings();
        collection.addView(itemBinding.getRoot(), 0);
        return itemBinding.getRoot();
    }

    @Override
    public void destroyItem(@NonNull ViewGroup arg0, int arg1, @NonNull Object arg2) {
        arg0.removeView((View) arg2);
    }

    @Override
    public boolean isViewFromObject(@NonNull View arg0, @NonNull Object arg1) {
        return arg0 == arg1;
    }

    @Override
    public Parcelable saveState() {
        return null;
    }
}
