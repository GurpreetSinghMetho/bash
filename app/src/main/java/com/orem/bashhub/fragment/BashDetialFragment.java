package com.orem.bashhub.fragment;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.braintreepayments.api.BraintreeFragment;
import com.braintreepayments.api.DataCollector;
import com.braintreepayments.api.GooglePayment;
import com.braintreepayments.api.Venmo;
import com.braintreepayments.api.dropin.DropInActivity;
import com.braintreepayments.api.dropin.DropInRequest;
import com.braintreepayments.api.dropin.DropInResult;
import com.braintreepayments.api.models.GooglePaymentRequest;
import com.google.android.gms.wallet.TransactionInfo;
import com.google.android.gms.wallet.WalletConstants;
import com.google.gson.Gson;
import com.orem.bashhub.R;
import com.orem.bashhub.activity.MainActivity;
import com.orem.bashhub.adapter.HostsAdapter;
import com.orem.bashhub.adapter.SpotifyDetailAdapter;
import com.orem.bashhub.data.BashDetailsPOJO;
import com.orem.bashhub.data.PayMethodPOJO;
import com.orem.bashhub.data.SpotifyDetailPojo;
import com.orem.bashhub.data.UserPOJO;
import com.orem.bashhub.databinding.FragmentBashDetailBinding;
import com.orem.bashhub.dialogs.DialogBuyConfirmation;
import com.orem.bashhub.dialogs.DialogPayMethod;
import com.orem.bashhub.interfaces.OnPayMethodPick;
import com.orem.bashhub.utils.Const;
import com.orem.bashhub.utils.OnSwipeListener;
import com.orem.bashhub.utils.Utils;
import com.orem.bashhub.utils.apiinterface.Events;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;

import static android.app.Activity.RESULT_OK;

public class BashDetialFragment extends BaseFragment {

    private FragmentBashDetailBinding binding;
    private BashDetailsPOJO data;
    private GestureDetector gestureDetector;
    private boolean isDetailShow = false;
    private String payMethod = Const.PAY_GOOGLE;
    private BraintreeFragment mBraintreeFragment;
    private int PAY_REQ_CODE = 311;
    private OnPayMethodPick listener = new OnPayMethodPick() {
        @Override
        public void onPayMethodPick(PayMethodPOJO item) {
            binding.ivPayMethod.setImageResource(item.getIcon());
            payMethod = item.getType();
        }
    };
    private String spotifyLink = "";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_bash_detail, container, false);
        if (data != null) init();
        else Objects.requireNonNull(getActivity()).onBackPressed();

        try {
            mBraintreeFragment = BraintreeFragment.newInstance(this, Const.BRAINTREE_AUTH_KEY);
            GooglePayment.isReadyToPay(mBraintreeFragment, isReadyToPay -> Utils.showLog("Google pay ready to use"));
            DataCollector.collectDeviceData(mBraintreeFragment, deviceData -> Utils.showLog("Device Data : " + deviceData));
        } catch (Exception e) {
            Utils.showLog("Brain ini exp : " + e.getMessage());
        }
        return binding.getRoot();
    }

    @SuppressLint("ClickableViewAccessibility")
    private void init() {
        data.setTickets(Const.ONE);
        binding.tvCharge.setText(Const.getCost(data.charge));
        binding.setData(data);
        binding.executePendingBindings();
        if (data.isCountDropVisible()) {
            binding.textBash.setText(mContext.getString(R.string.buy_ticket));
        }
        if (data.getHideNationalFact() == 1) {
            binding.mNationalFact.setVisibility(View.GONE);
            binding.view.setVisibility(View.GONE);
        }
        binding.mDesc.setClickable(true);
        binding.mDesc.setMovementMethod(LinkMovementMethod.getInstance());
        if (data.spotifyImages != null)
            if (data.spotifyImages.equalsIgnoreCase("")) {
                binding.mImagesLayout.setVisibility(View.GONE);
            } else {
                if (!data.spotifyImages.equalsIgnoreCase("")) {
                    spotifyLink = data.spotifyLink;
                    if (data.spotifyUserName != null)
                        if (!data.spotifyUserName.equalsIgnoreCase(""))
                            binding.mSpotifyUserName.setText("@" + data.spotifyUserName);
                    String spotifyImages = data.spotifyImages;
                    SpotifyDetailPojo pojo = new Gson().fromJson(spotifyImages, SpotifyDetailPojo.class);
                    binding.spotifyRecyclerView.setAdapter(new SpotifyDetailAdapter(mContext, pojo.getImages(), "detail"));
                } else {
                    binding.mSpotyfyLayout.setVisibility(View.GONE);
                }
            }
        else {
            binding.mSpotyfyLayout.setVisibility(View.GONE);
        }
        binding.rvHosts.setAdapter(new HostsAdapter(mContext, data.hosts, "app"));

        binding.halfLL.setVisibility(isDetailShow ? View.GONE : View.VISIBLE);
        binding.fullLL.setVisibility(isDetailShow ? View.VISIBLE : View.GONE);

        Animation slideUp = AnimationUtils.loadAnimation(baseActivity, R.anim.slide_up);
        binding.topRL.setVisibility(View.VISIBLE);
        binding.topRL.startAnimation(slideUp);
        gestureDetector = new GestureDetector(baseActivity, new OnSwipeListener() {

            @Override
            public boolean onSwipe(Direction direction) {
                if (direction == Direction.up) {
                    if (binding.fullLL.getVisibility() != View.VISIBLE) {
                        Animation slideUp = AnimationUtils.loadAnimation(baseActivity, R.anim.slide_up);
                        binding.halfLL.setVisibility(View.GONE);
                        binding.fullLL.setVisibility(View.VISIBLE);
                        binding.fullLL.startAnimation(slideUp);
                    }
                } else if (direction == Direction.down) {

                    Animation slideUp = AnimationUtils.loadAnimation(baseActivity, R.anim.slide_bottom);
                    binding.topRL.setVisibility(View.GONE);
                    binding.topRL.startAnimation(slideUp);
                    slideUp.setAnimationListener(new Animation.AnimationListener() {
                        @Override
                        public void onAnimationStart(Animation animation) {

                        }

                        @Override
                        public void onAnimationEnd(Animation animation) {
                            Objects.requireNonNull(getActivity()).onBackPressed();
                        }

                        @Override
                        public void onAnimationRepeat(Animation animation) {

                        }
                    });
                }
                return true;
            }
        });

        binding.halfLL.setOnTouchListener((view1, motionEvent) -> {
            gestureDetector.onTouchEvent(motionEvent);
            return true;
        });
        binding.fullViewLL.setOnTouchListener((view12, motionEvent) -> {
            gestureDetector.onTouchEvent(motionEvent);
            return true;
        });
        binding.mSpotyfy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utils.intentToBrowser(mContext, spotifyLink);
            }
        });
        binding.mClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Animation slideUp = AnimationUtils.loadAnimation(baseActivity, R.anim.slide_bottom);
                binding.topRL.setVisibility(View.GONE);
                binding.topRL.startAnimation(slideUp);
                slideUp.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        Objects.requireNonNull(getActivity()).onBackPressed();
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }
                });
            }
        });

        binding.llCrashBash.setOnClickListener(v -> {
            if (data.isIamHost(Const.getLoggedInUserID(mContext))) {
                Utils.goToFragment(mContext, new MyBashFragment(false), R.id.fragment_container);
                ((MainActivity) mContext).binding.fragmentContainer.setVisibility(View.VISIBLE);
            } else {
                if (Double.parseDouble(data.charge) > 0) {
                    double wallet = Double.parseDouble(Const.getLoggedInUser(mContext).getWalletValue());
                    if (payMethod.equals(Const.PAY_WALLET) && wallet < data.getTotalCost()) {
                        Utils.showToast(mContext, getString(R.string.wallet_less_balance));
                    } else {
                        DialogBuyConfirmation dialog = new DialogBuyConfirmation(data, this);
                        dialog.show(getChildFragmentManager(), dialog.getTag());
                    }
                } else apiCrashBash();
            }
        });
        ArrayAdapter<String> adapter = new ArrayAdapter<>(mContext, R.layout.spinner_textview_selected, getResources().getStringArray(R.array.array_tickets));
        adapter.setDropDownViewResource(R.layout.spinner_textview);
        binding.spTickets.setAdapter(adapter);
        binding.spTickets.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                data.setTickets(binding.spTickets.getSelectedItem().toString());
                binding.tvCharge.setText(data.getChargeText());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        binding.ivUber.setOnClickListener(v -> Const.onUberClick(mContext));
        binding.ivShare.setOnClickListener(v -> Utils.shareContent(mContext, data.getShareData(mContext)));
        binding.ivNavigation.setOnClickListener(v -> Utils.intentToMap(mContext, data.lat, data.lng));
        binding.ivPayMethod.setOnClickListener(v -> {
            DialogPayMethod dialog = new DialogPayMethod(listener);
            dialog.show(getChildFragmentManager(), dialog.getTag());
        });
        binding.ivPayDrop.setOnClickListener(v -> binding.ivPayMethod.performClick());
    }

    public void setData(BashDetailsPOJO data, boolean isDetailShow) {
        this.data = data;
        this.isDetailShow = isDetailShow;
    }

    public void apiGetToken() {
        if (payMethod.equals(Const.PAY_CARD)) {
            /*registerEventBus();
            EventBus.getDefault().post(new Events.RequestBraintreeToken(mContext, Const.apiBraintreeToken(mContext), true, false));*/
            onCardSubmit(Const.BRAINTREE_AUTH_KEY);
        } else if (payMethod.equals(Const.PAY_VENMO)) {
            Venmo.authorizeAccount(mBraintreeFragment, false);
        } else if (payMethod.equals(Const.PAY_GOOGLE)) {
            onGooglePaySubmit();
        } else {
            apiBuyTickets("", "");
        }
    }

    public void apiBuyTickets(String token, String device_data) {
        registerEventBus();
        JSONObject object = new JSONObject();
        JSONArray ticketArray = new JSONArray();
        Double ticketAmount = 0.0;
        try {
            object.put("id", data.getEventTickets().get(0).getId() + "");
            object.put("qty", data.getTickets());
            object.put("price", data.getTotalCost());
            ticketArray.put(object);
        } catch (Exception e) {
        }
        EventBus.getDefault().post(new Events.RequestBasic(mContext, Const.apiBuyTickets(mContext, ticketArray.toString(), data.id,
                "" , "", token, payMethod, device_data), true, false));
    }

    private void apiCrashBash() {
        registerEventBus();
        EventBus.getDefault().post(new Events.RequestCrashBash(mContext, Const.apiCrashBash(mContext, data.id), true, false));
    }

    @Subscribe
    public void apiBuyTicketsRes(Events.GetBasicData res) {
        unRegisterEventBus();
        UserPOJO.Data user = Const.getLoggedInUser(mContext);
        user.setToday_bash_count(res.getData().data.count);
        user.setWallet(res.getData().data.wallet);
        Const.setLoggedInUser(mContext, user);
        mLiveModel.getUserLiveData().setValue(user);
        Utils.showToast(mContext, getString(R.string.tickets_buy_success));
        Objects.requireNonNull(getActivity()).onBackPressed();
        if (Const.getLoggedInUser(mContext).email.equals("")) {
            Utils.showHelpDialog(mContext, "", mContext.getString(R.string.send_receipt1), (dialog1, which) -> dialog1.dismiss(), (dialog1, which) -> checkEmail(), null, "");
        } else
            Utils.showHelpDialog(mContext, "", String.format(mContext.getString(R.string.send_receipt), Const.getLoggedInUser(mContext).email), (dialog1, which) -> dialog1.dismiss(), (dialog1, which) -> checkEmail(), (dialog1, which) -> enterEmialDialog(mContext, R.layout.enter_email_dialogbox), "Edit Email");
    }

    private void checkEmail() {
        if (Const.getLoggedInUser(mContext).email.equals("")) {
            enterEmialDialog(mContext, R.layout.enter_email_dialogbox);
        } else {
            sendReceipt(Const.getLoggedInUser(mContext).email);
//            enterEmialDialog(mContext, R.layout.enter_email_dialogbox);
        }
    }

    public void enterEmialDialog(Context context, int layout) {
        ColorDrawable dialogColor = new ColorDrawable(Color.TRANSPARENT);
        dialogColor.setAlpha(0); //(0-255) 0 means fully transparent, and 255 means fully opaque
        Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(layout);
        dialog.setCancelable(true);
        EditText etEmail = dialog.findViewById(R.id.etEmail);
        TextView obtk = dialog.findViewById(R.id.send);
        obtk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!Utils.isEmailValid(etEmail.getText().toString()))
                    Utils.showMessageDialog(mContext, "", getString(R.string.enter_valid_email));
                else {
                    sendReceipt(etEmail.getText().toString());
                    dialog.dismiss();
                }
            }
        });
        dialog.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawable(dialogColor);
        dialog.show();
    }


    private void sendReceipt(String email) {
        registerEventBus();
        EventBus.getDefault().post(new Events.RequestBasic(mContext, Const.email_ticket(mContext, data.id, email), true, false));

    }

    @Subscribe
    public void apiSendOtpRes(Events.GetBasicData res) {
        unRegisterEventBus();
        Toast.makeText(mContext, mContext.getString(R.string.receipt_sent_email_address), Toast.LENGTH_SHORT).show();
    }

    @Subscribe
    public void apiCrashBashRes(Events.GetCrashBashData res) {
        unRegisterEventBus();
        UserPOJO.Data user = Const.getLoggedInUser(mContext);
        user.setToday_bash_count(res.getData().data.count);
        Const.setLoggedInUser(mContext, user);
        mLiveModel.getUserLiveData().setValue(user);
        Utils.showToast(mContext, res.getData().mesg);
    }

    @Subscribe
    public void apiGetTokenRes(Events.GetBraintreeTokenData res) {
        unRegisterEventBus();
        onCardSubmit(res.getData().data.token);
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
                        .setTotalPrice("" + data.getTotalCost())
                        .setTotalPriceStatus(WalletConstants.TOTAL_PRICE_STATUS_FINAL)
                        .setCurrencyCode("USD")
                        .build())
                .billingAddressRequired(true)
                .googleMerchantId(Const.GOOGLE_MERCHANT_ID);
        GooglePayment.requestPayment(mBraintreeFragment, googlePaymentRequest);
    }

    public void onNonceCreated(String nonce) {
        apiBuyTickets(nonce, "");
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PAY_REQ_CODE) {
            if (resultCode == RESULT_OK) {
                DropInResult result = data.getParcelableExtra(DropInResult.EXTRA_DROP_IN_RESULT);
                apiBuyTickets(result.getPaymentMethodNonce().getNonce(), result.getDeviceData());
            } else {
                if (data != null) {
                    Exception error = (Exception) data.getSerializableExtra(DropInActivity.EXTRA_ERROR);
                    Utils.showToast(mContext, "Braintree error : " + error.getMessage());
                }
            }
        }
    }
}
