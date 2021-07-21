package com.orem.bashhub.fragment;


import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.orem.bashhub.R;
import com.orem.bashhub.databinding.FragmentFaqBinding;
import com.orem.bashhub.utils.Const;

import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 */
public class FaqFragment extends BaseFragment {

    private FragmentFaqBinding binding;

    public FaqFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_faq, container, false);
        ini();
        return binding.getRoot();
    }

    @SuppressLint("SetJavaScriptEnabled")
    private void ini() {
        binding.backIV.setOnClickListener(v -> Objects.requireNonNull(getActivity()).onBackPressed());

        binding.webView.setBackgroundColor(Color.TRANSPARENT);
        binding.webView.getSettings().setJavaScriptEnabled(true);
        binding.webView.setWebViewClient(new CustomWebViewClient(binding.pb));
        binding.webView.loadUrl(Const.FAQ_LINK);
    }

    public class CustomWebViewClient extends WebViewClient {

        private ProgressBar mDialog;

        CustomWebViewClient(ProgressBar mDialog) {
            this.mDialog = mDialog;
            mDialog.setVisibility(View.VISIBLE);
        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            mDialog.setVisibility(View.GONE);
        }
    }
}
