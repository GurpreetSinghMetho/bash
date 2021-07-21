package com.orem.bashhub.fragment;


import android.graphics.PointF;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.dlazaro66.qrcodereaderview.QRCodeReaderView;
import com.orem.bashhub.R;
import com.orem.bashhub.data.BashDetailsPOJO;
import com.orem.bashhub.databinding.FragmentScanTicketBinding;
import com.orem.bashhub.interfaces.OnScanTicket;
import com.orem.bashhub.utils.Const;
import com.orem.bashhub.utils.Utils;
import com.orem.bashhub.utils.apiinterface.Events;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 */
public class ScanTicketFragment extends BaseFragment implements QRCodeReaderView.OnQRCodeReadListener {

    private FragmentScanTicketBinding binding;
    private boolean isScan = false, isFlash = false;
    private OnScanTicket listener;
    private BashDetailsPOJO data;

    public ScanTicketFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_scan_ticket, container, false);
        ini();
        return binding.getRoot();
    }

    private void ini() {
        binding.ivBack.setOnClickListener(v -> Objects.requireNonNull(getActivity()).onBackPressed());
        binding.qrDecoderView.setOnQRCodeReadListener(this);
        binding.qrDecoderView.setAutofocusInterval(2000L);
        binding.qrDecoderView.setBackCamera();
        binding.qrDecoderView.setQRDecodingEnabled(true);
        binding.llFlash.setOnClickListener(v -> {
            isFlash = !isFlash;
            binding.qrDecoderView.setTorchEnabled(isFlash);
            binding.ivFlash.setImageResource(isFlash ? R.drawable.ic_flash_on : R.drawable.ic_flash_off);
        });
        binding.llID.setOnClickListener(v -> {
            binding.ivBack.performClick();
            EnterBashIdFragment fragment = new EnterBashIdFragment();
            fragment.setData(data, listener);
            Utils.goToFragment(mContext, fragment, R.id.fragment_container);
        });
        binding.llWeb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "https://www.bashbusiness.com/?user_id=" + Const.getLoggedInUserID(mContext) + "&is_app=1&redirect_uri=https://www.bashbusiness.com/user/bartender";
                Utils.intentToBrowser(mContext, url);
            }
        });
    }

    public void setData(BashDetailsPOJO data, OnScanTicket listener) {
        this.listener = listener;
        this.data = data;
    }

    @Override
    public void onResume() {
        super.onResume();
        new Handler().postDelayed(() -> {
            isScan = false;
            binding.qrDecoderView.startCamera();
        }, 500);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onPause() {
        super.onPause();
        binding.qrDecoderView.stopCamera();
        if (isFlash) binding.llFlash.performClick();
    }

    @Override
    public void onQRCodeRead(String text, PointF[] points) {
        if (!isScan) {
            isScan = true;
            if (text.contains(Const.QRCODE_PREFIX)) {
                String[] code = text.split("_");
                apiScanTicket(code[1]);
            } else {
                Utils.showMessageDialog(mContext, "", getString(R.string.invalid_qr_code), (dialog, which) -> isScan = false);
            }
        }
    }

    private void apiScanTicket(String code) {
        registerEventBus();
        EventBus.getDefault().post(new Events.RequestBasic(mContext, Const.apiScanTicket(mContext, data.id, code), true, true));
    }

    @Subscribe
    public void apiScanTicketRes(Events.GetBasicData res) {
        unRegisterEventBus();
        if (res.getData().mesg.equals(getString(R.string.bar_code_error)) ||
                res.getData().mesg.equals(getString(R.string.ticket_already_used))) {
            Utils.showMessageDialog(mContext, "", res.getData().mesg, (dialog, which) -> isScan = false);
        } else {
            listener.onScanTicket();
            Utils.showMessageDialog(mContext, "", getString(R.string.ticket_scan_success), (dialog, which) -> binding.ivBack.performClick());
        }
    }

    @Subscribe
    public void errorResult(Events.ErrorResult res) {
        unRegisterEventBus();
        Utils.showMessageDialog(mContext, "", res.getErrorMessage(), (dialog, which) -> isScan = false);
    }
}
