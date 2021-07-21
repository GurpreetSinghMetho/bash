package com.orem.bashhub.adapter;

import android.graphics.Bitmap;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.databinding.BindingAdapter;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.orem.bashhub.R;
import com.orem.bashhub.utils.Const;
import com.orem.bashhub.utils.Utils;

public class BindingAdapters {

    @BindingAdapter("android:loadProfileImage")
    public static void loadProfileImage(ImageView view, String url) {
        String finalUrl = "";
        if (url != null) {
            finalUrl = url.startsWith("http") ? url : Const.IMAGE_BASE + url;
        }
        Utils.loadImage(view.getContext(), finalUrl, view, R.drawable.ic_fake_body);
    }

    /*@BindingAdapter("android:loadImage")
    public static void loadImage(ImageView view, String url) {
        Utils.loadImage(view.getContext(), Const.IMAGE_BASE + url, view, R.drawable.ic_placeholder);
    }*/

    @BindingAdapter("android:loadDrawableImage")
    public static void loadDrawableImage(ImageView view, Object url) {
        Utils.loadImage(view.getContext(), url, view, 0);
    }

    @BindingAdapter("android:loadUberImage")
    public static void loadUberImage(ImageView view, String url) {
        Utils.loadImage(view.getContext(), url, view, 0);
    }

    @BindingAdapter("android:loadQrCode")
    public static void loadQrCode(ImageView view, String code) {
        String url = "https://chart.googleapis.com/chart?chs=300x300&cht=qr&chl=BASH_" + code + "&choe=UTF-8";
        //Utils.loadImage(view.getContext(), url, view, 0);
        Glide.with(view.getContext()).asBitmap().load(url)
                .addListener(new RequestListener<Bitmap>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Bitmap> target, boolean isFirstResource) {
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Bitmap bitmap, Object model, Target<Bitmap> target, DataSource dataSource, boolean isFirstResource) {
                        view.setImageBitmap(getCroppedImage(bitmap));
                        return false;
                    }
                }).submit();
    }

    private static Bitmap getCroppedImage(Bitmap bitmap) {
        int cropValue = 40;
        int cropWidth = bitmap.getWidth() - cropValue - cropValue;
        int cropHeight = bitmap.getHeight() - cropValue - cropValue;
        return Bitmap.createBitmap(bitmap, cropValue, cropValue, cropWidth, cropHeight);
    }

    @BindingAdapter("android:setImageResource")
    public static void setImageResource(ImageView view, int resource) {
        view.setImageResource(resource);
    }

    @BindingAdapter("android:setRating")
    public static void setRating(RatingBar view, String rating) {
        if (rating != null && !rating.isEmpty()) view.setRating(Float.parseFloat(rating));
    }

    @BindingAdapter("android:setGender")
    public static void setGender(Spinner view, String gender) {
        if (gender != null)
            view.setSelection(gender.equals(Const.MALE) ? 0 : (gender.equals(Const.FEMALE) ? 1 : 2));
    }

    @BindingAdapter("android:setFollowText")
    public static void setFollowText(TextView view, String value) {
        if (value != null) {
            view.setText(view.getContext().getString(value.equals(Const.ONE) ? R.string.un_follow : R.string.follow));
            view.setTextColor(ContextCompat.getColor(view.getContext(), value.equals(Const.ONE) ? R.color.white : R.color.lightpurple));
            view.setBackgroundResource(value.equals(Const.ONE) ? R.drawable.custom_light_purple_button : R.drawable.custom_black_border);
        }
    }

    @BindingAdapter("android:setLocationSwitch")
    public static void setLocationSwitch(ImageView view, boolean status) {
        view.setImageResource(status ? R.drawable.ic_switch_selected : R.drawable.ic_switch_unselected);
    }
}