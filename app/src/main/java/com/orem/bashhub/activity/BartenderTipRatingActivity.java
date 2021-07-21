package com.orem.bashhub.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Toast;

import com.braintreepayments.api.BraintreeFragment;
import com.braintreepayments.api.DataCollector;
import com.braintreepayments.api.GooglePayment;
import com.braintreepayments.api.Venmo;
import com.braintreepayments.api.dropin.DropInActivity;
import com.braintreepayments.api.dropin.DropInRequest;
import com.braintreepayments.api.dropin.DropInResult;
import com.braintreepayments.api.interfaces.BraintreeErrorListener;
import com.braintreepayments.api.interfaces.PaymentMethodNonceCreatedListener;
import com.braintreepayments.api.models.GooglePaymentRequest;
import com.braintreepayments.api.models.PaymentMethodNonce;
import com.google.android.gms.wallet.TransactionInfo;
import com.google.android.gms.wallet.WalletConstants;
import com.orem.bashhub.R;
import com.orem.bashhub.data.PayMethodPOJO;
import com.orem.bashhub.databinding.ActivityBartenderTipRatingBinding;
import com.orem.bashhub.dialogs.DialogPayMethod;
import com.orem.bashhub.interfaces.OnPayMethodPick;
import com.orem.bashhub.utils.Const;
import com.orem.bashhub.utils.Utils;
import com.orem.bashhub.utils.apiinterface.Events;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import androidx.databinding.DataBindingUtil;

public class BartenderTipRatingActivity extends BaseActivity implements PaymentMethodNonceCreatedListener, BraintreeErrorListener {
    ActivityBartenderTipRatingBinding binding;
    String tipAmount = "";
    String rating = "";
    private String payMethod = "";
    private String order_id = "";
    private String bartender_id = "";
    private BraintreeFragment mBraintreeFragment;
    private int PAY_REQ_CODE = 311;
    private OnPayMethodPick listener = new OnPayMethodPick() {
        @Override
        public void onPayMethodPick(PayMethodPOJO item) {
            binding.ivPayMethod.setImageResource(item.getIcon());
            payMethod = item.getType();
        }
    };
    private String amount = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_bartender_tip_rating);
        try {
            mBraintreeFragment = BraintreeFragment.newInstance(this, Const.BRAINTREE_AUTH_KEY);
            GooglePayment.isReadyToPay(mBraintreeFragment, isReadyToPay -> Utils.showLog("Google pay ready to use"));
            DataCollector.collectDeviceData(mBraintreeFragment, deviceData -> Utils.showLog("Device Data : " + deviceData));
        } catch (Exception e) {
            Utils.showLog("Brain ini exp : " + e.getMessage());
        }
        order_id = getIntent().getStringExtra("order_id");
        amount = getIntent().getStringExtra("amount");
        bartender_id = getIntent().getStringExtra("bartender_id");
        init();
    }

    private void init() {
        if (amount != null) {
            if (!amount.equalsIgnoreCase("")) {
                Double oneAmount = Double.parseDouble(amount) * 15 / 100;
                Double twoAmount = Double.parseDouble(amount) * 18 / 100;
                Double threeAmount = Double.parseDouble(amount) * 20 / 100;
                Double fourAmount = Double.parseDouble(amount) * 25 / 100;
                binding.m1stAmount.setText(oneAmount + "");
                binding.m2ndAmount.setText(twoAmount + "");
                binding.m3rdAmount.setText(threeAmount + "");
                binding.m4thAmount.setText(fourAmount + "");
            }
        }

        binding.ivPayMethod.setOnClickListener(v -> {
            DialogPayMethod dialog = new DialogPayMethod(listener);
            dialog.show(getSupportFragmentManager(), dialog.getTag());
        });
        binding.ivPayDrop.setOnClickListener(v -> binding.ivPayMethod.performClick());

        binding.mBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        binding.mAddAmount.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                tipAmount = binding.mAddAmount.getText().toString();
                if (!binding.mAddAmount.getText().toString().equalsIgnoreCase("")) {
                    if (Double.parseDouble(binding.mAddAmount.getText().toString()) > 0) {
                        binding.mPaymentMethod.setVisibility(View.VISIBLE);
                        binding.mChargeText.setVisibility(View.VISIBLE);
                        if (payMethod.equalsIgnoreCase("")) {
                            payMethod = Const.PAY_GOOGLE;
                        }
                    } else {
                        binding.mPaymentMethod.setVisibility(View.GONE);
                        binding.mChargeText.setVisibility(View.GONE);
                    }
                } else {
                    binding.mPaymentMethod.setVisibility(View.GONE);
                    binding.mChargeText.setVisibility(View.GONE);
                }

            }

            @Override
            public void afterTextChanged(Editable s) {
                binding.m1stAmountLL.setBackground(getDrawable(R.drawable.custom_white_button));
                binding.m2ndAmountLL.setBackground(getDrawable(R.drawable.custom_white_button));
                binding.m3rdAmountLL.setBackground(getDrawable(R.drawable.custom_white_button));
                binding.m4thAmountLL.setBackground(getDrawable(R.drawable.custom_white_button));
            }
        });
        binding.m1stAmountLL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tipAmount = binding.m1stAmount.getText().toString();
                binding.m1stAmountLL.setBackground(getDrawable(R.drawable.custom_gray_border));
                binding.m2ndAmountLL.setBackground(getDrawable(R.drawable.custom_white_button));
                binding.m3rdAmountLL.setBackground(getDrawable(R.drawable.custom_white_button));
                binding.m4thAmountLL.setBackground(getDrawable(R.drawable.custom_white_button));
                binding.mPaymentMethod.setVisibility(View.VISIBLE);
                binding.mChargeText.setVisibility(View.VISIBLE);
                if (payMethod.equalsIgnoreCase("")) {
                    payMethod = Const.PAY_GOOGLE;
                }
            }
        });

        binding.m2ndAmountLL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tipAmount = binding.m2ndAmount.getText().toString();
                binding.m2ndAmountLL.setBackground(getDrawable(R.drawable.custom_gray_border));
                binding.m1stAmountLL.setBackground(getDrawable(R.drawable.custom_white_button));
                binding.m3rdAmountLL.setBackground(getDrawable(R.drawable.custom_white_button));
                binding.m4thAmountLL.setBackground(getDrawable(R.drawable.custom_white_button));
                binding.mPaymentMethod.setVisibility(View.VISIBLE);
                binding.mChargeText.setVisibility(View.VISIBLE);
                if (payMethod.equalsIgnoreCase("")) {
                    payMethod = Const.PAY_GOOGLE;
                }
            }
        });
        binding.m3rdAmountLL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tipAmount = binding.m3rdAmount.getText().toString();
                binding.m3rdAmountLL.setBackground(getDrawable(R.drawable.custom_gray_border));
                binding.m1stAmountLL.setBackground(getDrawable(R.drawable.custom_white_button));
                binding.m2ndAmountLL.setBackground(getDrawable(R.drawable.custom_white_button));
                binding.m4thAmountLL.setBackground(getDrawable(R.drawable.custom_white_button));
                binding.mPaymentMethod.setVisibility(View.VISIBLE);
                binding.mChargeText.setVisibility(View.VISIBLE);
                if (payMethod.equalsIgnoreCase("")) {
                    payMethod = Const.PAY_GOOGLE;
                }
            }
        });
        binding.m4thAmountLL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tipAmount = binding.m4thAmount.getText().toString();
                binding.m4thAmountLL.setBackground(getDrawable(R.drawable.custom_gray_border));
                binding.m1stAmountLL.setBackground(getDrawable(R.drawable.custom_white_button));
                binding.m3rdAmountLL.setBackground(getDrawable(R.drawable.custom_white_button));
                binding.m2ndAmountLL.setBackground(getDrawable(R.drawable.custom_white_button));
                binding.mPaymentMethod.setVisibility(View.VISIBLE);
                binding.mChargeText.setVisibility(View.VISIBLE);
                if (payMethod.equalsIgnoreCase("")) {
                    payMethod = Const.PAY_GOOGLE;
                }
            }
        });
        binding.star1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rating = "1";
                binding.star1.setImageDrawable(getDrawable(R.drawable.full_start));
                binding.star2.setImageDrawable(getDrawable(R.drawable.empty_start));
                binding.star3.setImageDrawable(getDrawable(R.drawable.empty_start));
                binding.star4.setImageDrawable(getDrawable(R.drawable.empty_start));
                binding.star5.setImageDrawable(getDrawable(R.drawable.empty_start));
            }
        });
        binding.star2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rating = "2";
                binding.star2.setImageDrawable(getDrawable(R.drawable.full_start));
                binding.star1.setImageDrawable(getDrawable(R.drawable.full_start));
                binding.star3.setImageDrawable(getDrawable(R.drawable.empty_start));
                binding.star4.setImageDrawable(getDrawable(R.drawable.empty_start));
                binding.star5.setImageDrawable(getDrawable(R.drawable.empty_start));
            }
        });
        binding.star3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rating = "3";
                binding.star3.setImageDrawable(getDrawable(R.drawable.full_start));
                binding.star2.setImageDrawable(getDrawable(R.drawable.full_start));
                binding.star1.setImageDrawable(getDrawable(R.drawable.full_start));
                binding.star4.setImageDrawable(getDrawable(R.drawable.empty_start));
                binding.star5.setImageDrawable(getDrawable(R.drawable.empty_start));
            }
        });
        binding.star4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rating = "4";
                binding.star4.setImageDrawable(getDrawable(R.drawable.full_start));
                binding.star2.setImageDrawable(getDrawable(R.drawable.full_start));
                binding.star3.setImageDrawable(getDrawable(R.drawable.full_start));
                binding.star1.setImageDrawable(getDrawable(R.drawable.full_start));
                binding.star5.setImageDrawable(getDrawable(R.drawable.empty_start));
            }
        });
        binding.star5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rating = "5";
                binding.star5.setImageDrawable(getDrawable(R.drawable.full_start));
                binding.star2.setImageDrawable(getDrawable(R.drawable.full_start));
                binding.star3.setImageDrawable(getDrawable(R.drawable.full_start));
                binding.star4.setImageDrawable(getDrawable(R.drawable.full_start));
                binding.star1.setImageDrawable(getDrawable(R.drawable.full_start));
            }
        });
        binding.mDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (rating.equalsIgnoreCase("") && tipAmount.equalsIgnoreCase("") && binding.mMessage.getText().toString().equalsIgnoreCase("")) {
                    Utils.showToast(mContext, "Please rating & Review or Tip");
                } else {
                    if (!tipAmount.equalsIgnoreCase("")) {
                        if (Double.parseDouble(tipAmount) > 0) {
                            Double mm=Double.parseDouble(tipAmount)*5.9/100;
                            mm=Double.parseDouble(tipAmount)+mm+.30;
                            tipAmount=mm+"";
                            if (payMethod.equals(Const.PAY_CARD)) {
                                onCardSubmit(Const.BRAINTREE_AUTH_KEY);
                            } else if (payMethod.equals(Const.PAY_VENMO)) {
                                Venmo.authorizeAccount(mBraintreeFragment, false);
                            } else if (payMethod.equals(Const.PAY_GOOGLE)) {
                                onGooglePaySubmit();
                            } else {
                                apiRating("");
                            }
                        }
                    } else {
                        apiRating("");
                    }
                }
            }
        });
    }

    private void onCardSubmit(String token) {
        DropInRequest dropInRequest = new DropInRequest()
                .clientToken(token)
                .collectDeviceData(true)
                .vaultManager(true)
                .disableGooglePayment()
                .disableVenmo()
                .disablePayPal();
        startActivityForResult(dropInRequest.getIntent(mContext), PAY_REQ_CODE);
    }

    private void onGooglePaySubmit() {
        GooglePaymentRequest googlePaymentRequest = new GooglePaymentRequest()
                .transactionInfo(TransactionInfo.newBuilder()
                        .setTotalPrice("" + tipAmount)
                        .setTotalPriceStatus(WalletConstants.TOTAL_PRICE_STATUS_FINAL)
                        .setCurrencyCode("USD")
                        .build())
                .billingAddressRequired(true)
                .googleMerchantId(Const.GOOGLE_MERCHANT_ID);
        GooglePayment.requestPayment(mBraintreeFragment, googlePaymentRequest);
    }


    @Override
    public void onPaymentMethodNonceCreated(PaymentMethodNonce paymentMethodNonce) {
        String nonce = paymentMethodNonce.getNonce();
        apiRating(nonce);

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PAY_REQ_CODE) {
            if (resultCode == RESULT_OK) {
                DropInResult result = data.getParcelableExtra(DropInResult.EXTRA_DROP_IN_RESULT);
                apiRating(result.getPaymentMethodNonce().getNonce());
            } else {
                if (data != null) {
                    Exception error = (Exception) data.getSerializableExtra(DropInActivity.EXTRA_ERROR);
                    Utils.showToast(mContext, "Braintree error : " + error.getMessage());
                }
            }
        }
    }


    public void apiRating(String token) {
        registerEventBus();
        EventBus.getDefault().post(new Events.RequestBasic(mContext, Const.apiTipRating(mContext, order_id, bartender_id, rating, binding.mMessage.getText().toString(), tipAmount, token, payMethod), true, false));
    }

    @Subscribe
    public void apiRatingRes(Events.GetBasicData res) {
        unRegisterEventBus();
        Toast.makeText(mContext, "Thanks! for your feedback.", Toast.LENGTH_SHORT).show();
        finish();
    }

    @Override
    public void onError(Exception error) {
        Utils.showToast(mContext, "Braintree error : " + error.getMessage());
    }
}