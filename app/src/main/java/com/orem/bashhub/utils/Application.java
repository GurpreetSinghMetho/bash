package com.orem.bashhub.utils;

import android.content.Context;

import androidx.multidex.MultiDex;

public final class Application extends android.app.Application {

    public static String Font_Text = "fonts/avenir_normal.TTF";
    private static Application mInstance;

    @Override
    protected void attachBaseContext(Context context) {
        super.attachBaseContext(context);
        MultiDex.install(this);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
        /*CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath(Font_Text)
                .setFontAttrId(R.attr.fontPath)
                .build());*/
    }

    public static synchronized Application getInstance() {
        return mInstance;
    }
}