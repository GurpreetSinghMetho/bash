package com.orem.bashhub.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;
import android.widget.TextView;

import com.braintreepayments.api.BraintreeFragment;
import com.braintreepayments.api.DataCollector;
import com.braintreepayments.api.GooglePayment;
import com.braintreepayments.api.Venmo;
import com.braintreepayments.api.dropin.DropInActivity;
import com.braintreepayments.api.dropin.DropInRequest;
import com.braintreepayments.api.dropin.DropInResult;
import com.braintreepayments.api.models.GooglePaymentRequest;
import com.bumptech.glide.Glide;
import com.google.android.gms.wallet.TransactionInfo;
import com.google.android.gms.wallet.WalletConstants;
import com.orem.bashhub.R;
import com.orem.bashhub.activity.MainActivity;
import com.orem.bashhub.data.BashDetailsPOJO;
import com.orem.bashhub.data.PayMethodPOJO;
import com.orem.bashhub.data.UserPOJO;
import com.orem.bashhub.data.VenueListPOJO;
import com.orem.bashhub.databinding.FragmentConfirmOrderBinding;
import com.orem.bashhub.databinding.ItemSectionRowBinding;
import com.orem.bashhub.databinding.OrderRowBinding;
import com.orem.bashhub.databinding.TableRowBinding;
import com.orem.bashhub.databinding.TicketRowBinding;
import com.orem.bashhub.dialogs.DialogPayMethod;
import com.orem.bashhub.dialogs.DialogTicketSuccess;
import com.orem.bashhub.interfaces.OnPayMethodPick;
import com.orem.bashhub.utils.Const;
import com.orem.bashhub.utils.Utils;
import com.orem.bashhub.utils.apiinterface.Events;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.json.JSONArray;
import org.json.JSONObject;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import static android.app.Activity.RESULT_OK;

public class ConfirmOrderFragment extends BaseFragment {
    static Double totalPrice = 0.0;
    static Double tax = 0.0;
    static FragmentConfirmOrderBinding binding;
    ArrayList<HashMap<String, String>> drinkList;
    List<VenueListPOJO.ItemSection> itemSectionsList;
    //    ArrayList<HashMap<String, ArrayList<HashMap<String, String>>>> itemSections;
    ArrayList<HashMap<String, String>> ticketList;
    ArrayList<HashMap<String, String>> bottleList;
    ArrayList<HashMap<String, String>> tableList;
    ArrayList<HashMap<String, String>> sectionList;
    ArrayList<HashMap<String, String>> serviceList;
    BashDetailsPOJO data;
    VenueListPOJO.Venue venueData;
    String type;
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

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_confirm_order, container, false);
        init();
        try {
            mBraintreeFragment = BraintreeFragment.newInstance(this, Const.BRAINTREE_AUTH_KEY);
            GooglePayment.isReadyToPay(mBraintreeFragment, isReadyToPay -> Utils.showLog("Google pay ready to use"));
            DataCollector.collectDeviceData(mBraintreeFragment, deviceData -> Utils.showLog("Device Data : " + deviceData));
        } catch (Exception e) {
            Utils.showLog("Brain ini exp : " + e.getMessage());
        }
        return binding.getRoot();
    }

    private void init() {
        binding.ivPayMethod.setOnClickListener(v -> {
            DialogPayMethod dialog = new DialogPayMethod(listener);
            dialog.show(getChildFragmentManager(), dialog.getTag());
        });
        binding.ivPayDrop.setOnClickListener(v -> binding.ivPayMethod.performClick());
        binding.mBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity) mContext).getSupportFragmentManager().popBackStack();
            }
        });
        binding.executePendingBindings();
        binding.llCrashBash.setOnClickListener(v -> {

            if (totalPrice > 0) {
                double wallet = Double.parseDouble(Const.getLoggedInUser(mContext).getWalletValue());
                if (payMethod.equals(Const.PAY_WALLET) && wallet < totalPrice) {
                    Utils.showToast(mContext, getString(R.string.wallet_less_balance));
                } else {
//                    DialogWebBuyConfirmation dialog = new DialogWebBuyConfirmation(data, this, totalPrice + "");
//                    dialog.show(getChildFragmentManager(), dialog.getTag());
                    apiGetToken();
                }
            } else {
                apiBuyTickets("", "");
            }
        });

        if (ticketList != null)
            if (ticketList.size() > 0) {
                binding.mTicketRV.setAdapter(new TicketAdapter(mContext));
            } else {
                binding.mTicketLayout.setVisibility(View.GONE);
            }
        else {
            binding.mTicketLayout.setVisibility(View.GONE);
        }
        if (drinkList != null)
            if (drinkList.size() > 0) {
                binding.mOrderDrinkRV.setAdapter(new DrinkAdapter(mContext, drinkList));
            } else {
                binding.mDrinkLayout.setVisibility(View.GONE);
            }
        else {
            binding.mDrinkLayout.setVisibility(View.GONE);
        }

        if (bottleList != null)
            if (bottleList.size() > 0) {
                binding.mOBottleServiceRV.setAdapter(new BottleAdapter(mContext, bottleList));
            } else {
                binding.mBottleLayout.setVisibility(View.GONE);
            }
        else {
            binding.mBottleLayout.setVisibility(View.GONE);
        }
        if (serviceList != null)
            if (serviceList.size() > 0) {
                binding.mServiceRV.setAdapter(new ProductAdapter(mContext, serviceList));
            } else {
                binding.mServiceLayout.setVisibility(View.GONE);
            }
        else {
            binding.mServiceLayout.setVisibility(View.GONE);
        }

        if (tableList != null)
            if (tableList.size() > 0) {
                binding.mTableServiceRV.setAdapter(new TableAdapter(mContext, tableList));
            } else {
                binding.mTableLayout.setVisibility(View.GONE);
            }
        else {
            binding.mTableLayout.setVisibility(View.GONE);
        }

        if (sectionList != null)
            if (sectionList.size() > 0) {
                binding.mSectionRV.setAdapter(new SectionAdapter(mContext, sectionList));
            } else {
                binding.mSectionLayout.setVisibility(View.GONE);
            }
        else {
            binding.mSectionLayout.setVisibility(View.GONE);
        }
//        itemSections = new ArrayList<>();
        if (itemSectionsList != null) {
            if (itemSectionsList.size() > 0) {
                binding.mItemSectionLayout.setVisibility(View.VISIBLE);
                binding.mItemSectionRV.setAdapter(new ItemSectionAdapter());


            } else {
                binding.mItemSectionLayout.setVisibility(View.GONE);
            }
        } else {
            binding.mItemSectionLayout.setVisibility(View.GONE);
        }

    }

    public void setData(BashDetailsPOJO data, ArrayList<HashMap<String, String>> ticketlist, ArrayList<HashMap<String, String>> drinkList, ArrayList<HashMap<String, String>> bottleList, ArrayList<HashMap<String, String>> tableList, ArrayList<HashMap<String, String>> sectionList, ArrayList<HashMap<String, String>> serviceList, String type) {
        this.data = data;
        this.ticketList = ticketlist;
        this.drinkList = drinkList;
        this.bottleList = bottleList;
        this.tableList = tableList;
        this.sectionList = sectionList;
        this.serviceList = serviceList;
        this.type = type;
    }

    public void setVenueData(VenueListPOJO.Venue data, List<VenueListPOJO.ItemSection> itemSectionsList, ArrayList<HashMap<String, String>> ticketlist, ArrayList<HashMap<String, String>> drinkList, ArrayList<HashMap<String, String>> bottleList, ArrayList<HashMap<String, String>> tableList, ArrayList<HashMap<String, String>> sectionList, ArrayList<HashMap<String, String>> serviceList, String type) {
        this.venueData = data;
        this.itemSectionsList = itemSectionsList;
        this.ticketList = ticketlist;
        this.drinkList = drinkList;
        this.bottleList = bottleList;
        this.tableList = tableList;
        this.sectionList = sectionList;
        this.serviceList = serviceList;
        this.type = type;
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
        JSONArray ticketArray = new JSONArray();
        Double ticketAmount = 0.0;
        if (ticketList != null)
            for (int i = 0; i < ticketList.size(); i++) {
                JSONObject object = new JSONObject();
                try {
                    object.put("id", ticketList.get(i).get("id"));
                    object.put("qty", ticketList.get(i).get("select_qty"));
                    Double pp = Double.parseDouble(ticketList.get(i).get("selected_price").replace("$", ""));
                    ticketAmount = ticketAmount + pp;
                    object.put("price", pp + "");
                    ticketArray.put(object);
                } catch (Exception e) {
                }
            }

        JSONArray drinkArray = new JSONArray();
        if (drinkList != null)
            for (int i = 0; i < drinkList.size(); i++) {
                JSONObject object = new JSONObject();
                try {
                    object.put("id", drinkList.get(i).get("id"));
                    object.put("qty", drinkList.get(i).get("qty"));
                    drinkArray.put(object);
                } catch (Exception e) {
                }
            }
        JSONArray productArray = new JSONArray();
        if (serviceList != null)
            for (int i = 0; i < serviceList.size(); i++) {
                JSONObject object = new JSONObject();
                try {
                    object.put("id", serviceList.get(i).get("id"));
                    object.put("qty", serviceList.get(i).get("qty"));
                    productArray.put(object);
                } catch (Exception e) {
                }
            }
        JSONArray bottleArray = new JSONArray();
        if (bottleList != null)
            for (int i = 0; i < bottleList.size(); i++) {
                JSONObject object = new JSONObject();
                try {
                    object.put("id", bottleList.get(i).get("id"));
                    object.put("qty", bottleList.get(i).get("qty"));

                    bottleArray.put(object);
                } catch (Exception e) {
                }
            }
        JSONArray other_items = new JSONArray();
        if (itemSectionsList != null)
            for (int i = 0; i < itemSectionsList.size(); i++) {
                for (int j = 0; j < itemSectionsList.get(i).getItems().size(); j++) {
                    if (itemSectionsList.get(i).getItems().get(j).isChecked!=null){
                        if (itemSectionsList.get(i).getItems().get(j).isChecked.equalsIgnoreCase("1")){
                            JSONObject object = new JSONObject();
                            try {
                                object.put("id", itemSectionsList.get(i).getItems().get(j).getId().toString());
                                object.put("qty", itemSectionsList.get(i).getItems().get(j).qty);
                                other_items.put(object);
                            } catch (Exception e) {
                            }
                        }
                    }
                }

            }
        String tableIds = "";
        if (tableList != null)
            for (int i = 0; i < tableList.size(); i++) {
                if (tableIds.equalsIgnoreCase("")) {
                    tableIds = tableList.get(i).get("id");
                } else {
                    tableIds = tableIds + "," + tableList.get(i).get("id");
                }
            }
        String sectionIds = "";
        if (sectionList != null)
            for (int i = 0; i < sectionList.size(); i++) {
                if (sectionIds.equalsIgnoreCase("")) {
                    sectionIds = sectionList.get(i).get("id");
                } else {
                    sectionIds = sectionIds + "," + sectionList.get(i).get("id");
                }
            }
        Log.e("payment_token", token);
        if (data != null)
            EventBus.getDefault().post(new Events.RequestBasic(mContext, Const.apiWebBuyTickets(mContext, ticketAmount + "", "bash_id", data.id,
                    ticketArray.toString(), token, payMethod, type, device_data, other_items.toString(), productArray.toString(), drinkArray.toString(), bottleArray.toString(), tableIds, sectionIds), true, false));
        else
            EventBus.getDefault().post(new Events.RequestBasic(mContext, Const.apiWebBuyTickets(mContext, ticketAmount + "", "venue_id", venueData.getId().toString(),
                    ticketArray.toString(), token, payMethod,type, device_data, other_items.toString(), productArray.toString(), drinkArray.toString(), bottleArray.toString(), tableIds, sectionIds), true, false));
    }

    private void apiCrashBash() {
        registerEventBus();
        EventBus.getDefault().post(new Events.RequestCrashBash(mContext, Const.apiCrashBash(mContext, "data.id"), true, false));
    }

    @Subscribe
    public void apiBuyTicketsRes(Events.GetBasicData res) {
        unRegisterEventBus();
        UserPOJO.Data user = Const.getLoggedInUser(mContext);
        user.setToday_bash_count(res.getData().data.count);
        user.setWallet(res.getData().data.wallet);
        Const.setLoggedInUser(mContext, user);
        mLiveModel.getUserLiveData().setValue(user);
//        Utils.showToast(mContext, getString(R.string.tickets_buy_success));
//        Objects.requireNonNull(getActivity()).onBackPressed();
        DialogTicketSuccess ticketSuccess = new DialogTicketSuccess();
        ticketSuccess.show(getChildFragmentManager(), ticketSuccess.getTag());
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
                        .setTotalPrice("" + totalPrice)
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

    @Override
    public void onDestroy() {
        super.onDestroy();
        totalPrice = 0.0;
        tax = 0.0;
    }

    private void setPrice(int position, String price) {
        HashMap<String, String> map = new HashMap<>();
        map.put("is_advance", ticketList.get(position).get("is_advance") + "");
        map.put("is_demography", ticketList.get(position).get("is_demography"));
        map.put("is_price_by_day", ticketList.get(position).get("is_price_by_day") + "");

        map.put("before_male_time", ticketList.get(position).get("before_male_time") + "");
        map.put("before_male_date", ticketList.get(position).get("before_male_date") + "");
        map.put("before_male_price", ticketList.get(position).get("before_male_price") + "");
        map.put("after_male_time", ticketList.get(position).get("after_male_time") + "");
        map.put("after_male_date", ticketList.get(position).get("after_male_date") + "");
        map.put("after_male_price", ticketList.get(position).get("after_male_price") + "");

        map.put("before_female_time", ticketList.get(position).get("before_female_time") + "");
        map.put("before_female_date", ticketList.get(position).get("before_female_date") + "");
        map.put("before_female_price", ticketList.get(position).get("before_female_price") + "");
        map.put("after_female_time", ticketList.get(position).get("after_female_time") + "");
        map.put("after_female_date", ticketList.get(position).get("after_female_date") + "");
        map.put("after_female_price", ticketList.get(position).get("after_female_price") + "");

        map.put("id", ticketList.get(position).get("id") + "");
        map.put("name", ticketList.get(position).get("name"));
        map.put("qty", ticketList.get(position).get("qty") + "");
        map.put("min", ticketList.get(position).get("min") + "");
        map.put("select_qty", ticketList.get(position).get("select_qty"));
        map.put("price", ticketList.get(position).get("price") + "");
        map.put("selected_price", price);
        map.put("des", ticketList.get(position).get("des") + "");

        map.put("male_price", ticketList.get(position).get("") + "");
        map.put("female_price", ticketList.get(position).get("female_price") + "");
        ticketList.set(position, map);

    }

    public static class ProductAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

        private Context mContext;
        private ArrayList<HashMap<String, String>> list;


        public ProductAdapter(Context mContext, ArrayList<HashMap<String, String>> list) {
            this.mContext = mContext;
            this.list = list;
        }

        @NonNull
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new ProductAdapter.Holder(OrderRowBinding.inflate(LayoutInflater.from(mContext), parent, false));
        }

        @Override
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
            ProductAdapter.Holder h = (ProductAdapter.Holder) holder;
            h.bind();
        }

        @Override
        public int getItemCount() {
            return list.size();
        }

        public void optionMenu(int position, AppCompatTextView mDelete, AppCompatTextView mPrice, String pp) {
            final PopupMenu popup = new PopupMenu(mContext, mDelete);
            ArrayList rating = new ArrayList();
            rating.add("1");
            rating.add("2");
            rating.add("3");
            rating.add("4");
            rating.add("5");
            rating.add("6");
            rating.add("7");
            rating.add("8");
            rating.add("9");
            rating.add("10");
            for (int i = 0; i < rating.size(); i++) {
                popup.getMenu().add(Menu.NONE, (i + 1), Menu.NONE, rating.get(i).toString());
            }
            popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem item) {
                    mDelete.setText(item.getTitle());
                    String oldPrice = mPrice.getText().toString();
                    Double price = Double.parseDouble(pp.replace("$", "")) * Double.parseDouble(mDelete.getText().toString());
                    mPrice.setText("$" + price);
                    totalPrice = totalPrice + Double.parseDouble(mPrice.getText().toString().replace("$", ""));
                    totalPrice = totalPrice - Double.parseDouble(oldPrice.replace("$", ""));
                    totalPrice = totalPrice - tax;
                    tax = (totalPrice * 5.9 / 100) + .30;
                    totalPrice = totalPrice + tax;
                    String tt = NumberFormat.getNumberInstance(Locale.getDefault()).format(totalPrice);
                    ConfirmOrderFragment.binding.mTotal.setText("$" + tt);
                    ConfirmOrderFragment.binding.mTax.setText("$" + String.format("%.2f", tax));
                    return false;
                }
            });
            popup.show();
        }

        class Holder extends RecyclerView.ViewHolder {
            OrderRowBinding binding;

            Holder(OrderRowBinding binding) {
                super(binding.getRoot());
                this.binding = binding;
            }

            public void bind() {
                HashMap<String, String> item = list.get(getAdapterPosition());
                binding.mTitle.setText(item.get("name"));
                Double price = Double.parseDouble(item.get("price")) * Double.parseDouble(item.get("qty"));
                binding.mPrice.setText("$" + price);
                totalPrice = totalPrice + Double.parseDouble(binding.mPrice.getText().toString().replace("$", ""));
                totalPrice = totalPrice - tax;
                tax = (totalPrice * 5.9 / 100) + .30;
                totalPrice = totalPrice + tax;
                String tt = NumberFormat.getNumberInstance(Locale.getDefault()).format(totalPrice);
                ConfirmOrderFragment.binding.mTotal.setText("$" + tt);
                ConfirmOrderFragment.binding.mTax.setText("$" + String.format("%.2f", tax));
                binding.mQuantity.setText(item.get("qty"));
                binding.mQuantity.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        optionMenu(getAdapterPosition(), binding.mQuantity, binding.mPrice, item.get("price"));
                    }
                });
                binding.mDelete.setVisibility(View.GONE);
                binding.mDelete.setOnClickListener(v -> {
                    list.remove(getAdapterPosition());
                    notifyItemRemoved(getAdapterPosition());
                });
            }
        }
    }

    public static class DrinkAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

        private Context mContext;
        private ArrayList<HashMap<String, String>> list;


        public DrinkAdapter(Context mContext, ArrayList<HashMap<String, String>> list) {
            this.mContext = mContext;
            this.list = list;
        }

        @NonNull
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new DrinkAdapter.Holder(OrderRowBinding.inflate(LayoutInflater.from(mContext), parent, false));
        }

        @Override
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
            DrinkAdapter.Holder h = (DrinkAdapter.Holder) holder;
            h.bind();
        }

        @Override
        public int getItemCount() {
            return list.size();
        }

        public void optionMenu(int position, AppCompatTextView mDelete, AppCompatTextView mPrice, String pp) {
            final PopupMenu popup = new PopupMenu(mContext, mDelete);
            ArrayList rating = new ArrayList();
            rating.add("1");
            rating.add("2");
            rating.add("3");
            rating.add("4");
            rating.add("5");
            rating.add("6");
            rating.add("7");
            rating.add("8");
            rating.add("9");
            rating.add("10");
            for (int i = 0; i < rating.size(); i++) {
                popup.getMenu().add(Menu.NONE, (i + 1), Menu.NONE, rating.get(i).toString());
            }
            popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem item) {
                    mDelete.setText(item.getTitle());
                    String oldPrice = mPrice.getText().toString();
                    Double price = Double.parseDouble(pp.replace("$", "")) * Double.parseDouble(mDelete.getText().toString());
                    mPrice.setText("$" + price);
                    totalPrice = totalPrice + Double.parseDouble(mPrice.getText().toString().replace("$", ""));
                    totalPrice = totalPrice - Double.parseDouble(oldPrice.replace("$", ""));
                    totalPrice = totalPrice - tax;
                    tax = (totalPrice * 5.9 / 100) + .30;
                    totalPrice = totalPrice + tax;
                    String tt = NumberFormat.getNumberInstance(Locale.getDefault()).format(totalPrice);
                    ConfirmOrderFragment.binding.mTotal.setText("$" + tt);
                    ConfirmOrderFragment.binding.mTax.setText("$" + String.format("%.2f", tax));
                    return false;
                }
            });
            popup.show();
        }

        class Holder extends RecyclerView.ViewHolder {
            OrderRowBinding binding;

            Holder(OrderRowBinding binding) {
                super(binding.getRoot());
                this.binding = binding;
            }

            public void bind() {
                HashMap<String, String> item = list.get(getAdapterPosition());
                binding.mTitle.setText(item.get("name"));
                Double price = Double.parseDouble(item.get("price")) * Double.parseDouble(item.get("qty"));
                binding.mPrice.setText("$" + price);
                totalPrice = totalPrice + Double.parseDouble(binding.mPrice.getText().toString().replace("$", ""));
                totalPrice = totalPrice - tax;
                tax = (totalPrice * 5.9 / 100) + .30;
                totalPrice = totalPrice + tax;
                String tt = NumberFormat.getNumberInstance(Locale.getDefault()).format(totalPrice);
                ConfirmOrderFragment.binding.mTotal.setText("$" + tt);
                ConfirmOrderFragment.binding.mTax.setText("$" + String.format("%.2f", tax));
                binding.mQuantity.setText(item.get("qty"));
                binding.mQuantity.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        optionMenu(getAdapterPosition(), binding.mQuantity, binding.mPrice, item.get("price"));
                    }
                });
                binding.mDelete.setVisibility(View.GONE);
                binding.mDelete.setOnClickListener(v -> {
                    list.remove(getAdapterPosition());
                    notifyItemRemoved(getAdapterPosition());
                });
            }
        }
    }

    public static class BottleAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

        private Context mContext;
        private ArrayList<HashMap<String, String>> list;


        public BottleAdapter(Context mContext, ArrayList<HashMap<String, String>> list) {
            this.mContext = mContext;
            this.list = list;
        }

        @NonNull
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new BottleAdapter.Holder(OrderRowBinding.inflate(LayoutInflater.from(mContext), parent, false));
        }

        @Override
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
            BottleAdapter.Holder h = (BottleAdapter.Holder) holder;
            h.bind();
        }

        @Override
        public int getItemCount() {
            return list.size();
        }

        public void optionMenu(int position, AppCompatTextView mDelete, AppCompatTextView mPrice, String pp) {
            final PopupMenu popup = new PopupMenu(mContext, mDelete);
            ArrayList rating = new ArrayList();
            rating.add("1");
            rating.add("2");
            rating.add("3");
            rating.add("4");
            rating.add("5");
            rating.add("6");
            rating.add("7");
            rating.add("8");
            rating.add("9");
            rating.add("10");
            for (int i = 0; i < rating.size(); i++) {
                popup.getMenu().add(Menu.NONE, (i + 1), Menu.NONE, rating.get(i).toString());
            }
            popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem item) {
                    mDelete.setText(item.getTitle());
                    String oldPrice = mPrice.getText().toString();
                    Double price = Double.parseDouble(pp.replace("$", "")) * Double.parseDouble(mDelete.getText().toString());
                    mPrice.setText("$" + price);
                    totalPrice = totalPrice + Double.parseDouble(mPrice.getText().toString().replace("$", ""));
                    totalPrice = totalPrice - Double.parseDouble(oldPrice.replace("$", ""));
                    totalPrice = totalPrice - tax;
                    tax = (totalPrice * 5.9 / 100) + .30;
                    totalPrice = totalPrice + tax;
                    String tt = NumberFormat.getNumberInstance(Locale.getDefault()).format(totalPrice);
                    ConfirmOrderFragment.binding.mTotal.setText("$" + tt);
                    ConfirmOrderFragment.binding.mTax.setText("$" + String.format("%.2f", tax));

                    return false;
                }
            });
            popup.show();
        }

        class Holder extends RecyclerView.ViewHolder {

            OrderRowBinding binding;

            Holder(OrderRowBinding binding) {
                super(binding.getRoot());
                this.binding = binding;
            }

            public void bind() {
                HashMap<String, String> item = list.get(getAdapterPosition());
                binding.mTitle.setText(item.get("name"));
                Double price = Double.parseDouble(item.get("price")) * Double.parseDouble(item.get("qty"));
                binding.mPrice.setText("$" + price);
                totalPrice = totalPrice + Double.parseDouble(binding.mPrice.getText().toString().replace("$", ""));
                totalPrice = totalPrice - tax;
                tax = (totalPrice * 5.9 / 100) + .30;
                totalPrice = totalPrice + tax;
                String tt = NumberFormat.getNumberInstance(Locale.getDefault()).format(totalPrice);
                ConfirmOrderFragment.binding.mTotal.setText("$" + tt);
                ConfirmOrderFragment.binding.mTax.setText("$" + String.format("%.2f", tax));

                binding.mQuantity.setText(item.get("qty"));
                binding.mDelete.setOnClickListener(v -> {
                    list.remove(getAdapterPosition());
                    notifyItemRemoved(getAdapterPosition());
                });
                binding.mDelete.setVisibility(View.GONE);
                binding.mQuantity.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        optionMenu(getAdapterPosition(), binding.mQuantity, binding.mPrice, item.get("price"));
                    }
                });
            }
        }

    }

    public static class TableAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

        private Context mContext;
        private ArrayList<HashMap<String, String>> list;


        public TableAdapter(Context mContext, ArrayList<HashMap<String, String>> list) {
            this.mContext = mContext;
            this.list = list;
        }

        @NonNull
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new TableAdapter.Holder(TableRowBinding.inflate(LayoutInflater.from(mContext), parent, false));
        }

        @Override
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
            TableAdapter.Holder h = (TableAdapter.Holder) holder;
            h.bind();
        }

        @Override
        public int getItemCount() {
            return list.size();
        }

        class Holder extends RecyclerView.ViewHolder {

            TableRowBinding binding;

            Holder(TableRowBinding binding) {
                super(binding.getRoot());
                this.binding = binding;
            }

            public void bind() {
                HashMap<String, String> item = list.get(getAdapterPosition());
                binding.mName.setText(item.get("name"));
                binding.mGuest.setText(item.get("guest") + " Guest");
                binding.mDescription.setText(item.get("des"));
                binding.mPrice.setText("$" + item.get("price"));
                totalPrice = totalPrice + Double.parseDouble(binding.mPrice.getText().toString().replace("$", ""));
                totalPrice = totalPrice - tax;
                tax = (totalPrice * 5.9 / 100) + .30;
                totalPrice = totalPrice + tax;
                String tt = NumberFormat.getNumberInstance(Locale.getDefault()).format(totalPrice);
                ConfirmOrderFragment.binding.mTotal.setText("$" + tt);
                ConfirmOrderFragment.binding.mTax.setText("$" + String.format("%.2f", tax));

                binding.mTableId.setText("Table : " + item.get("table_id"));
                binding.mDelete.setVisibility(View.GONE);
                binding.mDelete.setOnClickListener(v -> {
                    list.remove(getAdapterPosition());
                    notifyItemRemoved(getAdapterPosition());
                });
            }
        }
    }

    public static class SectionAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

        private Context mContext;
        private ArrayList<HashMap<String, String>> list;


        public SectionAdapter(Context mContext, ArrayList<HashMap<String, String>> list) {
            this.mContext = mContext;
            this.list = list;
        }

        @NonNull
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new SectionAdapter.Holder(TableRowBinding.inflate(LayoutInflater.from(mContext), parent, false));
        }

        @Override
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
            SectionAdapter.Holder h = (SectionAdapter.Holder) holder;
            h.bind();
        }

        @Override
        public int getItemCount() {
            return list.size();
        }

        class Holder extends RecyclerView.ViewHolder {

            TableRowBinding binding;

            Holder(TableRowBinding binding) {
                super(binding.getRoot());
                this.binding = binding;
            }

            public void bind() {
                HashMap<String, String> item = list.get(getAdapterPosition());
                binding.mName.setText(item.get("name"));
                binding.mGuest.setText(item.get("guest") + " Guest");
                binding.mDescription.setVisibility(View.GONE);
                binding.mPrice.setText("$" + item.get("price"));
                totalPrice = totalPrice + Double.parseDouble(binding.mPrice.getText().toString().replace("$", ""));
                totalPrice = totalPrice - tax;
                tax = (totalPrice * 5.9 / 100) + .30;
                totalPrice = totalPrice + tax;
                String tt = NumberFormat.getNumberInstance(Locale.getDefault()).format(totalPrice);
                ConfirmOrderFragment.binding.mTotal.setText("$" + tt);
                ConfirmOrderFragment.binding.mTax.setText("$" + String.format("%.2f", tax));
                binding.mTableId.setVisibility(View.GONE);
                binding.mDelete.setVisibility(View.GONE);
                binding.mDelete.setOnClickListener(v -> {
                    list.remove(getAdapterPosition());
                    notifyItemRemoved(getAdapterPosition());
                });
            }
        }
    }

    private class TicketAdapter extends RecyclerView.Adapter<TicketAdapter.ViewHolder> {
        Context mContext;

        public TicketAdapter(Context mContext) {
            this.mContext = mContext;
        }

        @NonNull
        @Override
        public TicketAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new TicketAdapter.ViewHolder(TicketRowBinding.inflate(LayoutInflater.from(mContext), parent, false));
        }

        @Override
        public void onBindViewHolder(@NonNull TicketAdapter.ViewHolder holder, int position) {
            holder.binding.mDelete.setVisibility(View.GONE);

            holder.binding.mTicketQuantity.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ticketQuantity(holder.binding.mRemainingTickets, position, Integer.parseInt(ticketList.get(position).get("min")), Double.parseDouble(ticketList.get(position).get("price")), Integer.parseInt(ticketList.get(position).get("qty")), holder.binding.mTicketQuantity, holder.binding.tvCharge);
                }
            });
            holder.binding.mTicketName.setText(ticketList.get(position).get("name"));
            holder.binding.mDescription.setText(ticketList.get(position).get("des"));
            holder.binding.mTicketQuantity.setText(ticketList.get(position).get("select_qty"));
            holder.binding.mRemainingTickets.setText("Tickets Remaining: " + ticketList.get(position).get("total_qty"));

            if (ticketList.get(position).get("is_advance") != null)
                if (ticketList.get(position).get("is_advance").equalsIgnoreCase("1")) {
                    String gender = Const.getLoggedInUser(mContext).getGenderText(mContext);
                    if (ticketList.get(position).get("is_demography").equalsIgnoreCase("1") && ticketList.get(position).get("is_price_by_day").equalsIgnoreCase("1")) {
                        if (gender.equalsIgnoreCase("") || gender.equalsIgnoreCase(Const.MALE) || gender.equalsIgnoreCase(Const.NOT_SPECIFIED)) {
                            if (Utils.isValidDOB(ticketList.get(position).get("before_male_date"))) {
                                Double pp = Double.parseDouble(ticketList.get(position).get("select_qty")) * Double.parseDouble(ticketList.get(position).get("before_male_price"));
                                String cost = Const.getCost(pp + "");
                                holder.binding.tvCharge.setText(cost);
                                setPrice(position, holder.binding.tvCharge.getText().toString().replace("$", ""));
                            } else {
                                Double pp = Double.parseDouble(ticketList.get(position).get("select_qty")) * Double.parseDouble(ticketList.get(position).get("after_male_price"));
                                String cost = Const.getCost(pp + "");
                                holder.binding.tvCharge.setText(cost);
                                setPrice(position, holder.binding.tvCharge.getText().toString().replace("$", ""));
                            }
                        } else {
                            if (Utils.isValidDOB(ticketList.get(position).get("before_female_date"))) {
                                Double pp = Double.parseDouble(ticketList.get(position).get("select_qty")) * Double.parseDouble(ticketList.get(position).get("before_female_price"));
                                String cost = Const.getCost(pp + "");
                                holder.binding.tvCharge.setText(cost);
                                setPrice(position, holder.binding.tvCharge.getText().toString().replace("$", ""));
                            } else {
                                Double pp = Double.parseDouble(ticketList.get(position).get("select_qty")) * Double.parseDouble(ticketList.get(position).get("after_female_price"));
                                String cost = Const.getCost(pp + "");
                                holder.binding.tvCharge.setText(cost);
                                setPrice(position, holder.binding.tvCharge.getText().toString().replace("$", ""));
                            }
                        }
                    } else if (ticketList.get(position).get("is_demography").equalsIgnoreCase("1")) {
                        if (gender.equalsIgnoreCase("") || gender.equalsIgnoreCase(Const.MALE) || gender.equalsIgnoreCase(Const.NOT_SPECIFIED)) {
                            Double pp = Double.parseDouble(ticketList.get(position).get("select_qty")) * Double.parseDouble(ticketList.get(position).get("male_price"));
                            String cost = Const.getCost(pp + "");
                            holder.binding.tvCharge.setText(cost);
                            setPrice(position, holder.binding.tvCharge.getText().toString().replace("$", ""));
                        } else {
                            Double pp = Double.parseDouble(ticketList.get(position).get("select_qty")) * Double.parseDouble(ticketList.get(position).get("female_price"));
                            String cost = Const.getCost(pp + "");
                            holder.binding.tvCharge.setText(cost);
                            setPrice(position, holder.binding.tvCharge.getText().toString().replace("$", ""));
                        }
                    } else if (ticketList.get(position).get("is_price_by_day").equalsIgnoreCase("1")) {
                        if (gender.equalsIgnoreCase("") || gender.equalsIgnoreCase(Const.MALE) || gender.equalsIgnoreCase(Const.NOT_SPECIFIED)) {
                            if (Utils.isValidDOB(ticketList.get(position).get("before_male_date"))) {
                                Double pp = Double.parseDouble(ticketList.get(position).get("select_qty")) * Double.parseDouble(ticketList.get(position).get("before_male_price"));

                                String cost = Const.getCost(pp + "");
                                holder.binding.tvCharge.setText(cost);
                                setPrice(position, holder.binding.tvCharge.getText().toString().replace("$", ""));

                            } else {
                                Double pp = Double.parseDouble(ticketList.get(position).get("select_qty")) * Double.parseDouble(ticketList.get(position).get("after_male_price"));
                                String cost = Const.getCost(pp + "");
                                holder.binding.tvCharge.setText(cost);
                                setPrice(position, holder.binding.tvCharge.getText().toString().replace("$", ""));
                            }

                        } else {
                            if (Utils.isValidDOB(ticketList.get(position).get("before_female_date"))) {
                                Double pp = Double.parseDouble(ticketList.get(position).get("select_qty")) * Double.parseDouble(ticketList.get(position).get("before_female_price"));
                                String cost = Const.getCost(pp + "");
                                holder.binding.tvCharge.setText(cost);
                                setPrice(position, holder.binding.tvCharge.getText().toString().replace("$", ""));

                            } else {
                                Double pp = Double.parseDouble(ticketList.get(position).get("select_qty")) * Double.parseDouble(ticketList.get(position).get("after_female_price"));
                                String cost = Const.getCost(pp + "");
                                holder.binding.tvCharge.setText(cost);
                                setPrice(position, holder.binding.tvCharge.getText().toString().replace("$", ""));
                            }
                        }
                    } else {
                        Double pp = Double.parseDouble(ticketList.get(position).get("select_qty")) * Double.parseDouble(ticketList.get(position).get("price"));
                        String cost = Const.getCost(pp + "");
                        holder.binding.tvCharge.setText(cost);
                        setPrice(position, holder.binding.tvCharge.getText().toString().replace("$", ""));
                    }
                } else {
                    Double pp = Double.parseDouble(ticketList.get(position).get("select_qty")) * Double.parseDouble(ticketList.get(position).get("price"));
                    String cost = Const.getCost(pp + "");
                    holder.binding.tvCharge.setText(cost);
                    setPrice(position, holder.binding.tvCharge.getText().toString().replace("$", ""));
                }
            else {
                Double pp = Double.parseDouble(ticketList.get(position).get("select_qty")) * Double.parseDouble(ticketList.get(position).get("price"));
                String cost = Const.getCost(pp + "");
                holder.binding.tvCharge.setText(cost);
                setPrice(position, holder.binding.tvCharge.getText().toString().replace("$", ""));
            }
            if (!holder.binding.tvCharge.getText().toString().equalsIgnoreCase("")) {
                totalPrice = totalPrice + Double.parseDouble(holder.binding.tvCharge.getText().toString().replace("$", ""));
                totalPrice = totalPrice - tax;
                tax = (totalPrice * 5.9 / 100) + .30;
                totalPrice = totalPrice + tax;
                String tt = NumberFormat.getNumberInstance(Locale.getDefault()).format(totalPrice);
                ConfirmOrderFragment.binding.mTotal.setText("$" + tt);
                ConfirmOrderFragment.binding.mTax.setText("$" + String.format("%.2f", tax));
            }
        }

        public void ticketQuantity(TextView mRemainingTickets, int position, int min, Double ticketprice, int ticketQTY, AppCompatTextView mType, TextView tvCharge) {

            final PopupMenu popup = new PopupMenu(mContext, mType);
            ArrayList rating = new ArrayList();
            for (int i = 0; i < ticketQTY; i++) {
                if ((i + 1) >= min)
                    rating.add((i + 1) + "");
            }

            for (int i = 0; i < rating.size(); i++) {
                popup.getMenu().add(Menu.NONE, (i + 1), Menu.NONE, rating.get(i).toString());
            }
            popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem item) {
                    mType.setText(item.getTitle());
//                    int remain= Integer.parseInt( ticketList.get(position).get("qty"))-Integer.parseInt(mType.getText().toString());
//                    mRemainingTickets.setText("Per Order Max Tickets: "+remain);
                    Double pp = ticketprice * Double.parseDouble(mType.getText().toString());
                    String oldPrice = tvCharge.getText().toString();
                    String cost = Const.getCost(pp + "");
                    tvCharge.setText(cost);
                    totalPrice = totalPrice + Double.parseDouble(tvCharge.getText().toString().replace("$", ""));
                    totalPrice = totalPrice - Double.parseDouble(oldPrice.replace("$", ""));
                    totalPrice = totalPrice - tax;
                    tax = (totalPrice * 5.9 / 100) + .30;
                    totalPrice = totalPrice + tax;
                    String tt = NumberFormat.getNumberInstance(Locale.getDefault()).format(totalPrice);
                    ConfirmOrderFragment.binding.mTotal.setText("$" + tt);
                    ConfirmOrderFragment.binding.mTax.setText("$" + String.format("%.2f", tax));
                    HashMap<String, String> map = new HashMap<>();
                    map.put("is_advance", ticketList.get(position).get("is_advance") + "");
                    map.put("is_demography", ticketList.get(position).get("is_demography"));
                    map.put("is_price_by_day", ticketList.get(position).get("is_price_by_day") + "");

                    map.put("before_male_time", ticketList.get(position).get("before_male_time") + "");
                    map.put("before_male_date", ticketList.get(position).get("before_male_date") + "");
                    map.put("before_male_price", ticketList.get(position).get("before_male_price") + "");
                    map.put("after_male_time", ticketList.get(position).get("after_male_time") + "");
                    map.put("after_male_date", ticketList.get(position).get("after_male_date") + "");
                    map.put("after_male_price", ticketList.get(position).get("after_male_price") + "");

                    map.put("before_female_time", ticketList.get(position).get("before_female_time") + "");
                    map.put("before_female_date", ticketList.get(position).get("before_female_date") + "");
                    map.put("before_female_price", ticketList.get(position).get("before_female_price") + "");
                    map.put("after_female_time", ticketList.get(position).get("after_female_time") + "");
                    map.put("after_female_date", ticketList.get(position).get("after_female_date") + "");
                    map.put("after_female_price", ticketList.get(position).get("after_female_price") + "");

                    map.put("id", ticketList.get(position).get("id") + "");
                    map.put("name", ticketList.get(position).get("name"));
                    map.put("qty", ticketList.get(position).get("qty") + "");
                    map.put("min", ticketList.get(position).get("min") + "");
                    map.put("select_qty", mType.getText().toString());
                    map.put("price", ticketList.get(position).get("price") + "");
                    map.put("selected_price", tvCharge.getText().toString().replace("$", ""));
                    map.put("des", ticketList.get(position).get("des") + "");

                    map.put("male_price", ticketList.get(position).get("") + "");
                    map.put("female_price", ticketList.get(position).get("female_price") + "");
                    ticketList.set(position, map);
                    return false;
                }
            });
            popup.show();
        }

        @Override
        public int getItemCount() {
            return ticketList.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            TicketRowBinding binding;

            public ViewHolder(@NonNull TicketRowBinding itemView) {
                super(itemView.getRoot());
                binding = itemView;
            }
        }
    }

    private class ItemSectionAdapter extends RecyclerView.Adapter<ItemSectionAdapter.ViewHolder> {
//        ArrayList<HashMap<String, ArrayList<HashMap<String, String>>>> itemSections;

        public ItemSectionAdapter() {
//            this.itemSections = itemSections;
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new ViewHolder(ItemSectionRowBinding.inflate(LayoutInflater.from(mContext), parent, false));

        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            holder.binding.mMenu.setVisibility(View.GONE);
//            holder.binding.mTitle.setVisibility(View.GONE);
//            holder.binding.iv388.setVisibility(View.GONE);
            holder.binding.mTitle.setText(itemSectionsList.get(position).getName());
            Glide.with(mContext).load(Const.IMAGE_BASE_EVENT + itemSectionsList.get(position).getImage()).into(holder.binding.iv388);
            boolean isHide = true;
            for (int i = 0; i < itemSectionsList.get(position).getItems().size(); i++) {
                if (itemSectionsList.get(position).getItems().get(i).isChecked != null)
                    if (itemSectionsList.get(position).getItems().get(i).isChecked.equalsIgnoreCase("1")) {
                        isHide = false;
                    }
            }
            if (isHide) {
                holder.itemView.setLayoutParams(new ViewGroup.LayoutParams(0, 0));
            }
            if (itemSectionsList.get(position).getItems() != null)
                holder.binding.mItemSectionRV.setAdapter(new ItemSectionMenuAdapter(position));
        }

        @Override
        public int getItemCount() {
            return itemSectionsList.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            ItemSectionRowBinding binding;

            public ViewHolder(@NonNull ItemSectionRowBinding itemView) {
                super(itemView.getRoot());
                binding = itemView;
            }
        }
    }

    private class ItemSectionMenuAdapter extends RecyclerView.Adapter<ItemSectionMenuAdapter.ViewHolder> {
        int itemSections;

        public ItemSectionMenuAdapter(int itemSections) {
            this.itemSections = itemSections;
        }

        @NonNull
        @Override
        public ItemSectionMenuAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new ItemSectionMenuAdapter.ViewHolder(OrderRowBinding.inflate(LayoutInflater.from(mContext), parent, false));
        }

        @Override
        public void onBindViewHolder(@NonNull ItemSectionMenuAdapter.ViewHolder holder, int position) {
            if (itemSectionsList.get(itemSections).getItems().get(position).isChecked != null)
                if (itemSectionsList.get(itemSections).getItems().get(position).isChecked.equalsIgnoreCase("1")) {
                    holder.binding.mDescription.setText(itemSectionsList.get(itemSections).getItems().get(position).getDescription());
                    holder.binding.mTitle.setText(itemSectionsList.get(itemSections).getItems().get(position).getName());
                    if (itemSectionsList.get(itemSections).getItems().get(position).qty.equals("0")) {
                        holder.binding.mPrice.setText("$" + itemSectionsList.get(itemSections).getItems().get(position).getPrice());

                    } else {
                        Double price = Double.parseDouble(itemSectionsList.get(itemSections).getItems().get(position).getPrice()) * Double.parseDouble(itemSectionsList.get(itemSections).getItems().get(position).qty);
                        holder.binding.mPrice.setText("$" + price);
                    }
                    totalPrice = totalPrice + Double.parseDouble(holder.binding.mPrice.getText().toString().replace("$", ""));
                    totalPrice = totalPrice - tax;
                    tax = (totalPrice * 5.9 / 100) + .30;
                    totalPrice = totalPrice + tax;
                    String tt = NumberFormat.getNumberInstance(Locale.getDefault()).format(totalPrice);
                    ConfirmOrderFragment.binding.mTotal.setText("$" + tt);
                    ConfirmOrderFragment.binding.mTax.setText("$" + String.format("%.2f", tax));
                    if (itemSectionsList.get(itemSections).getItems().get(position).getManageStock() == 1) {
                        holder.binding.mQuantityEdit.setVisibility(View.GONE);
                        holder.binding.mQuantity.setVisibility(View.VISIBLE);
                        holder.binding.mQuantity.setText(itemSectionsList.get(itemSections).getItems().get(position).qty);
                    } else {
                        holder.binding.mQuantityEdit.setVisibility(View.VISIBLE);
                        holder.binding.mQuantity.setVisibility(View.GONE);
                        holder.binding.mQuantityEdit.setText(itemSectionsList.get(itemSections).getItems().get(position).qty);
                    }
                    holder.binding.mQuantityEdit.addTextChangedListener(new TextWatcher() {
                        @Override
                        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                        }

                        @Override
                        public void onTextChanged(CharSequence s, int start, int before, int count) {
                            if (!holder.binding.mQuantityEdit.getText().toString().equalsIgnoreCase("")) {
                                if (Integer.parseInt(holder.binding.mQuantityEdit.getText().toString()) > 0) {
                                    String oldPrice = holder.binding.mPrice.getText().toString();
                                    Double price = Double.parseDouble(itemSectionsList.get(itemSections).getItems().get(position).getPrice()) * Double.parseDouble(holder.binding.mQuantityEdit.getText().toString());
                                    holder.binding.mPrice.setText("$" + price);
                                    totalPrice = totalPrice + Double.parseDouble(holder.binding.mPrice.getText().toString().replace("$", ""));
                                    totalPrice = totalPrice - Double.parseDouble(oldPrice.replace("$", ""));
                                    totalPrice = totalPrice - tax;
                                    tax = (totalPrice * 5.9 / 100) + .30;
                                    totalPrice = totalPrice + tax;
                                    String tt = NumberFormat.getNumberInstance(Locale.getDefault()).format(totalPrice);
                                    ConfirmOrderFragment.binding.mTotal.setText("$" + tt);
                                    ConfirmOrderFragment.binding.mTax.setText("$" + String.format("%.2f", tax));
                                    itemSectionsList.get(position).qty = holder.binding.mQuantityEdit.getText().toString();
                                } else {
                                    holder.binding.mQuantityEdit.setText("1");
                                    String oldPrice = holder.binding.mPrice.getText().toString();
                                    Double price = Double.parseDouble(itemSectionsList.get(itemSections).getItems().get(position).getPrice()) * Double.parseDouble(holder.binding.mQuantityEdit.getText().toString());
                                    holder.binding.mPrice.setText("$" + price);
                                    totalPrice = totalPrice + Double.parseDouble(holder.binding.mPrice.getText().toString().replace("$", ""));
                                    totalPrice = totalPrice - Double.parseDouble(oldPrice.replace("$", ""));
                                    totalPrice = totalPrice - tax;
                                    tax = (totalPrice * 5.9 / 100) + .30;
                                    totalPrice = totalPrice + tax;
                                    String tt = NumberFormat.getNumberInstance(Locale.getDefault()).format(totalPrice);
                                    ConfirmOrderFragment.binding.mTotal.setText("$" + tt);
                                    ConfirmOrderFragment.binding.mTax.setText("$" + String.format("%.2f", tax));
                                    itemSectionsList.get(position).qty = "0";
                                }

                            } else {
                                holder.binding.mQuantityEdit.setText("1");
                                String oldPrice = holder.binding.mPrice.getText().toString();
                                Double price = Double.parseDouble(itemSectionsList.get(itemSections).getItems().get(position).getPrice()) * Double.parseDouble(holder.binding.mQuantityEdit.getText().toString());
                                holder.binding.mPrice.setText("$" + price);
                                totalPrice = totalPrice + Double.parseDouble(holder.binding.mPrice.getText().toString().replace("$", ""));
                                totalPrice = totalPrice - Double.parseDouble(oldPrice.replace("$", ""));
                                totalPrice = totalPrice - tax;
                                tax = (totalPrice * 5.9 / 100) + .30;
                                totalPrice = totalPrice + tax;
                                String tt = NumberFormat.getNumberInstance(Locale.getDefault()).format(totalPrice);
                                ConfirmOrderFragment.binding.mTotal.setText("$" + tt);
                                ConfirmOrderFragment.binding.mTax.setText("$" + String.format("%.2f", tax));
                                itemSectionsList.get(position).qty = "0";
                            }
                        }

                        @Override
                        public void afterTextChanged(Editable s) {

                        }
                    });

                    holder.binding.mDelete.setVisibility(View.GONE);
                    holder.binding.mQuantity.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            optionMenu(position, holder.binding.mQuantity, holder.binding.mPrice, itemSectionsList.get(itemSections).getItems().get(position).getPrice().toString(), itemSectionsList.get(itemSections).getItems().get(position).getQuantity() + "");
                        }
                    });
                } else {
                    holder.binding.mRow.setVisibility(View.GONE);
                    holder.itemView.setLayoutParams(new ViewGroup.LayoutParams(0, 0));
                }
            else {
                holder.binding.mRow.setVisibility(View.GONE);
                holder.itemView.setLayoutParams(new ViewGroup.LayoutParams(0, 0));
            }


        }

        private void optionMenu(int position, AppCompatTextView mQuantity, AppCompatTextView mPrice, String pp, String qty) {
            final PopupMenu popup = new PopupMenu(mContext, mQuantity);
            ArrayList rating = new ArrayList();
            for (int i = 0; i < Integer.parseInt(qty); i++) {
                rating.add(i + 1);
            }


            for (int i = 0; i < rating.size(); i++) {
                popup.getMenu().add(Menu.NONE, (i + 1), Menu.NONE, rating.get(i).toString());
            }
            popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem item) {
                    mQuantity.setText(item.getTitle());
                    String oldPrice = mPrice.getText().toString();
                    Double price = Double.parseDouble(pp.replace("$", "")) * Double.parseDouble(mQuantity.getText().toString());
                    mPrice.setText("$" + price);
                    totalPrice = totalPrice + Double.parseDouble(mPrice.getText().toString().replace("$", ""));
                    totalPrice = totalPrice - Double.parseDouble(oldPrice.replace("$", ""));
                    totalPrice = totalPrice - tax;
                    tax = (totalPrice * 5.9 / 100) + .30;
                    totalPrice = totalPrice + tax;
                    String tt = NumberFormat.getNumberInstance(Locale.getDefault()).format(totalPrice);
                    ConfirmOrderFragment.binding.mTotal.setText("$" + tt);
                    ConfirmOrderFragment.binding.mTax.setText("$" + String.format("%.2f", tax));

                    itemSectionsList.get(position).qty = mQuantity.getText().toString();
//                        notifyDataSetChanged();
                    return false;
                }
            });
            popup.show();
        }

        @Override
        public int getItemCount() {
            return itemSectionsList.get(itemSections).getItems().size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            OrderRowBinding binding;

            public ViewHolder(@NonNull OrderRowBinding itemView) {
                super(itemView.getRoot());
                binding = itemView;
            }
        }
    }
}
