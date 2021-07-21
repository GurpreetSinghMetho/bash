package com.orem.bashhub.activity;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.orem.bashhub.R;
import com.orem.bashhub.databinding.ActivityFullImageBinding;
import com.orem.bashhub.utils.Utils;

public class FullImageActivity extends AppCompatActivity implements View.OnClickListener {
    ActivityFullImageBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_full_image);
        initViews();
    }

    private void initViews() {
        binding.backIV.setOnClickListener(this);
        String image = getIntent().getStringExtra("image");
        Utils.loadImage(this, image, binding.mImage, R.drawable.places_autocomplete_toolbar_shadow);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.backIV:
                finish();
                break;
        }
    }
}