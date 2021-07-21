package com.orem.bashhub.utils;

import android.content.Context;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.viewpager.widget.ViewPager;

import com.orem.bashhub.R;

public class MyPagerIndicator implements ViewPager.OnPageChangeListener {

    private final Context mContext;
    private ViewPager viewPager;
    private int dotsCount;
    private ImageView[] dots;
    private LinearLayout llPagerDots;

    public MyPagerIndicator(Context mContext, ViewPager viewPager, int dotsCount, LinearLayout llPagerDots) {
        llPagerDots.removeAllViews();
        this.mContext = mContext;
        this.viewPager = viewPager;
        this.dotsCount = dotsCount;
        this.llPagerDots = llPagerDots;
    }

    public void setPagerIndicator() {
        viewPager.setCurrentItem(0);
        viewPager.addOnPageChangeListener(this);
        dots = new ImageView[dotsCount];
        for (int i = 0; i < dotsCount; i++) {
            dots[i] = new ImageView(mContext);
            dots[i].setImageDrawable(mContext.getResources().getDrawable(R.drawable.custom_dot_unselected));
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );
            params.setMargins(4, 0, 4, 0);
            llPagerDots.addView(dots[i], params);
        }
        dots[0].setImageDrawable(mContext.getResources().getDrawable(R.drawable.custom_dot_selected));
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        for (int i = 0; i < dotsCount; i++)
            dots[i].setImageDrawable(mContext.getResources().getDrawable(R.drawable.custom_dot_unselected));

        dots[position].setImageDrawable(mContext.getResources().getDrawable(R.drawable.custom_dot_selected));
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
