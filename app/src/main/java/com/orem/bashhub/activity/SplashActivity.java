package com.orem.bashhub.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import com.orem.bashhub.R;
import com.orem.bashhub.databinding.ActivitySplashBinding;
import com.orem.bashhub.utils.Const;
import com.orem.bashhub.utils.Utils;

import androidx.databinding.DataBindingUtil;

public class SplashActivity extends BaseActivity {

    ActivitySplashBinding binding;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_splash);

        try {
            PackageInfo info = getPackageManager().getPackageInfo(getPackageName(), 0);
            binding.tvVersion.setText(getString(R.string.menu_app_version) + " " + info.versionName);
        } catch (Exception e) {
            e.printStackTrace();
        }

        mTinyDB.putString(Const.INVITATION_USER, "");
        mTinyDB.putString(Const.INVITATION_BASH_ID, "");
        mTinyDB.putString(Const.SHARE_USER, "");
        mTinyDB.putString(Const.SHARE_BASH_ID, "");
        mTinyDB.putString(Const.SHARE_VENUE_ID, "");
        Uri data = getIntent().getData();
        if (data != null && data.getHost() != null && data.getHost().equals(Const.APP_HOST)) {
            String url = data.toString();
            String id = url.substring(url.indexOf("redirect/") + 9);
            String[] ids = Utils.convertBase64Decode(id).split("_");
            mTinyDB.putString(Const.INVITATION_USER, Utils.convertBase64Decode(ids[0]));
            mTinyDB.putString(Const.INVITATION_BASH_ID, Utils.convertBase64Decode(ids[1]));
        }
        if (data != null && data.getHost() != null && data.getHost().equals(Const.APP_HOST_EVENT)) {
            String url = data.toString();
            Log.e("redirect_url",url);
            if (url.contains("venue")) {
                String id = url.substring(url.indexOf("venue/") + 6);
                String[] ids = Utils.convertBase64Decode(id).split("_");
                mTinyDB.putString(Const.SHARE_USER, Utils.convertBase64Decode(ids[0]));
                mTinyDB.putString(Const.SHARE_VENUE_ID, Utils.convertBase64Decode(ids[1]));
            } else {
                String id = url.substring(url.indexOf("redirect/") + 9);
                String[] ids = Utils.convertBase64Decode(id).split("_");
                mTinyDB.putString(Const.SHARE_USER, Utils.convertBase64Decode(ids[0]));
                mTinyDB.putString(Const.SHARE_BASH_ID, Utils.convertBase64Decode(ids[1]));
            }

        }
        if (getIntent().hasExtra("type"))
            if (getIntent().getStringExtra("type").equalsIgnoreCase(Const.NOTI_BARTENDER_RATING)) {
                if (Const.isLoggedIn(mContext)) {
                    startNewActivity(Const.isLoggedIn(mContext) ? MainActivity.class : LoginActivity.class, false, true, null);
                    Intent intent = new Intent(mContext, BartenderTipRatingActivity.class);
                    String amount = getIntent().getStringExtra("amount") != null ? getIntent().getStringExtra("amount") : "";
                    String bartender_id = getIntent().getStringExtra("bartender_id") != null ? getIntent().getStringExtra("bartender_id") : "";
                    String order_id = getIntent().getStringExtra("order_id") != null ? getIntent().getStringExtra("order_id") : "";
                    intent.putExtra("order_id", order_id);
                    intent.putExtra("amount", amount);
                    intent.putExtra("bartender_id", bartender_id);
                    startActivity(intent);
                } else {
                    new Handler().postDelayed(() -> {
                        startNewActivity(Const.isLoggedIn(mContext) ? MainActivity.class : LoginActivity.class, false, true, null);
                    }, 1000);
                }
            } else {
                new Handler().postDelayed(() -> {
                    startNewActivity(Const.isLoggedIn(mContext) ? MainActivity.class : LoginActivity.class, false, true, null);
                }, 1000);
            }
        else
            new Handler().postDelayed(() -> {
                startNewActivity(Const.isLoggedIn(mContext) ? MainActivity.class : LoginActivity.class, false, true, null);
            }, 1000);

        Utils.printHashKey(mContext);
    }
}