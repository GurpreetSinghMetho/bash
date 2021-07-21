package com.orem.bashhub.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.orem.bashhub.R;

public class CustomSpinnerAdapter extends ArrayAdapter<String> {
    String[] spinnerTitles;
    int[] spinnerImages;
    Context mContext;

    public CustomSpinnerAdapter(Context mContext, String[] titles, int[] images) {
        super(mContext, R.layout.custome_spinner_row);
        this.spinnerTitles = titles;
        this.spinnerImages = images;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ViewHolder mViewHolder = new ViewHolder();
        if (convertView == null) {
            LayoutInflater mInflater = (LayoutInflater) mContext.
                    getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = mInflater.inflate(R.layout.custome_spinner_row, parent, false);
            mViewHolder.mEmojis = (ImageView) convertView.findViewById(R.id.ivEmojis);
            mViewHolder.mName = (TextView) convertView.findViewById(R.id.tvName);
            convertView.setTag(mViewHolder);
        } else {
            mViewHolder = (ViewHolder) convertView.getTag();
        }
        if (position == 0) {
            mViewHolder.mEmojis.setVisibility(View.GONE);
            mViewHolder.mName.setText(spinnerTitles[position]);
            mViewHolder.mName.setPadding(0,5,5,5);
        } else {
            mViewHolder.mEmojis.setImageResource(spinnerImages[position]);
            mViewHolder.mName.setText(spinnerTitles[position]);
            mViewHolder.mName.setPadding(10,5,5,5);
        }
        return convertView;
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return getView(position, convertView, parent);
    }

    @Override
    public int getCount() {
        return spinnerTitles.length;
    }

    private static class ViewHolder {
        ImageView mEmojis;
        TextView mName;
    }
}