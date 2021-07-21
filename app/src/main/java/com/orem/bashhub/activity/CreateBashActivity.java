package com.orem.bashhub.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

import androidx.annotation.MainThread;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.share.Sharer;
import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.widget.ShareDialog;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.widget.Autocomplete;
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.orem.bashhub.R;
import com.orem.bashhub.adapter.AddSpotufyImagesAdapter;
import com.orem.bashhub.adapter.CustomSpinnerAdapter;
import com.orem.bashhub.data.BashDetailsPOJO;
import com.orem.bashhub.data.SpotifyDetailPojo;
import com.orem.bashhub.data.SpotifyListPOJO;
import com.orem.bashhub.data.UserPOJO;
import com.orem.bashhub.data.UsersListPOJO;
import com.orem.bashhub.databinding.FragmentCreateBashBinding;
import com.orem.bashhub.dialogs.DialogPlaylist;
import com.orem.bashhub.fragment.AddHostFragment;
import com.orem.bashhub.fragment.FbEventsFragment;
import com.orem.bashhub.fragment.InviteFriendsFragment;
import com.orem.bashhub.interfaces.OnAddHost;
import com.orem.bashhub.interfaces.OnBgApi;
import com.orem.bashhub.utils.Const;
import com.orem.bashhub.utils.Utils;
import com.orem.bashhub.utils.apiinterface.Events;
import com.spotify.sdk.android.authentication.AuthenticationClient;
import com.spotify.sdk.android.authentication.AuthenticationRequest;
import com.spotify.sdk.android.authentication.AuthenticationResponse;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class CreateBashActivity extends BaseActivity {
    public static final String CLIENT_ID = "19db39b9dcf8400ba1ca2c875c9802e1";
    //    public static final String REDIRECT_URL = "http://3.15.223.201/bash_demo/callbacks/";
    public static final String REDIRECT_URL = "https://apis.bashparty.app/callbacks/";
    //    public static final String REDIRECT_URL = "http://bashhub_app://returnafterlogin";
    public static final int AUTH_TOKEN_REQUEST_CODE = 0x10;
    public static Context mContext;
    static RecyclerView spotifyRecyclerView;
    private static BashDetailsPOJO bashData = null;
    private static OnBgApi bgListener = null;
    private static SpotifyListPOJO pojo = null;
    private static SpotifyListPOJO pojo1 = null;
    private static List<SpotifyListPOJO.Item> playlist;
    private static AddSpotufyImagesAdapter adapter;
    private final OkHttpClient mOkHttpClient = new OkHttpClient();
    String[] spinnerTitles;
    int[] spinnerImages;
    private FragmentCreateBashBinding binding;
    private String EVENT_MODE = Const.ZERO, EVENT_REPEAT = Const.ZERO, EVENT_TYPE = Const.EVENT_RESTAURANT;
    private CallbackManager callbackManager;
    private Place selectedPlace = null;
    private String startDate = "", endDate = "", repeatEndDate = "", category = "";
    private List<UsersListPOJO.Data> selectedUser = new ArrayList<>();
    private String hostID = "", bash_id = "";
    private boolean isDelete = false;
    private List<String> list = new ArrayList<>();
    private OnAddHost listener = this::setHost;
    private String mAccessToken;
    private String mAccessCode;
    private Call mCall;
    private String spotifyLisk = "";
    private String instaLink = "";
    private Handler mHandler;
    private String spotifyId = "";
    private String spotifyData = "";
    private String instaData = "";
    private String spotifyUserName = "";

    public static void setData(BashDetailsPOJO bashData1, OnBgApi bgListener1) {
        bashData = bashData1;
        bgListener = bgListener1;
   /*  new Handler().postDelayed(new Runnable() {
         @Override
         public void run() {
             if (bashData != null)
                 setBashData();
         }
     },4000);*/
    }

    public static void playlist(List<SpotifyListPOJO.Item> item) {
        playlist = item;
        if (playlist.size() < 5) {
            int size = 5 - playlist.size();
            for (int i = 0; i < size; i++) {
                SpotifyListPOJO.Item item1 = new SpotifyListPOJO.Item();
                item1.setHref("");
                item1.setId("");
                item1.setName("");
                SpotifyListPOJO.Image image = new SpotifyListPOJO.Image();
                image.setHeight(300);
                image.setWidth(200);
                image.setUrl("");
                ArrayList<SpotifyListPOJO.Image> imageArrayList = new ArrayList<>();
                imageArrayList.add(image);
                item1.setImages(imageArrayList);
                playlist.add(item1);
            }
        }
        adapter = new AddSpotufyImagesAdapter(mContext, playlist, pojo1);
        spotifyRecyclerView.setAdapter(adapter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.fragment_create_bash);
        Places.initialize(getApplicationContext(), getString(R.string.google_maps_key));
        callbackManager = CallbackManager.Factory.create();
        mContext = this;
        init();
        if (bashData != null)
            setBashData();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        bashData = null;
        pojo1 = null;
        pojo = null;
    }

    private void init() {
        spotifyRecyclerView = findViewById(R.id.spotify_recyclerView);
        pojo = new SpotifyListPOJO();
        pojo1 = new SpotifyListPOJO();
        SpotifyListPOJO.Item item1 = new SpotifyListPOJO.Item();
        item1.setHref("");
        item1.setId("");
        item1.setName("");
        SpotifyListPOJO.Image image = new SpotifyListPOJO.Image();
        image.setHeight(300);
        image.setWidth(200);
        image.setUrl("");
        ArrayList<SpotifyListPOJO.Image> imageArrayList = new ArrayList<>();
        imageArrayList.add(image);
        item1.setImages(imageArrayList);
        ArrayList<SpotifyListPOJO.Item> itemArrayList = new ArrayList<>();
        itemArrayList.add(item1);
        itemArrayList.add(item1);
        itemArrayList.add(item1);
        itemArrayList.add(item1);
        itemArrayList.add(item1);
        pojo.setItems(itemArrayList);
        adapter = new AddSpotufyImagesAdapter(mContext, pojo.getItems(), pojo1);
        binding.spotifyRecyclerView.setAdapter(adapter);
        ArrayList list = new ArrayList();
        list.add("");
        list.add("");
        list.add("");
        list.add("");
        list.add("");

       /* list.add("https://cdn.shopify.com/s/files/1/1381/1501/products/9334059_rfr_summer_banners_ci_1024x1024.jpg?v=1468450863");
        list.add("https://i.pinimg.com/originals/c6/da/03/c6da0308991deed3af22d92065242a08.jpg");
        list.add("https://image.freepik.com/free-photo/two-umbrella-tropical-beach-summer-holiday-banner_34755-248.jpg");
        list.add("https://previews.123rf.com/images/maridav/maridav1604/maridav160400665/55657495-panorama-summer-vacation-couple-walking-on-beach-young-adults-having-fun-together-enjoying-their-hol.jpg");
        list.add("https://keylimeexcursions.com/wp-content/uploads/2019/07/best-beach-umbrellas.jpg");
    */
        spinnerTitles = new String[]{mContext.getString(R.string.bash_category), mContext.getString(R.string.pool_party), mContext.getString(R.string.queer_friendly), mContext.getString(R.string.desi_party), mContext.getString(R.string.gone_country), mContext.getString(R.string.karaoke_time), mContext.getString(R.string.viral_event)};
        spinnerImages = new int[]{R.drawable.bikini
                , R.drawable.bikini
                , R.drawable.gay
                , R.drawable.flag
                , R.drawable.cowboy
                , R.drawable.mic,
                R.drawable.viral};
        CustomSpinnerAdapter mCustomAdapter = new CustomSpinnerAdapter(mContext, spinnerTitles, spinnerImages);
        binding.spBashCategory.setAdapter(mCustomAdapter);
        binding.spBashCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {
                    category = "";
                } else
                    category = spinnerTitles[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        Utils.underlineTextView(binding.tvDelete);
        binding.backIV.setOnClickListener(this);
        binding.ivSwitch.setOnClickListener(this);
        binding.ivRepeatSwitch.setOnClickListener(this);
        binding.btWebEvent.setOnClickListener(this);
        binding.ivRestaurant.setOnClickListener(this);
        binding.ivClub.setOnClickListener(this);
        binding.ivBar.setOnClickListener(this);
        binding.tvStartDate.setOnClickListener(this);
        binding.tvEndDate.setOnClickListener(this);
        binding.tvStartTime.setOnClickListener(this);
        binding.tvEndTime.setOnClickListener(this);
        binding.tvLocation.setOnClickListener(this);
        binding.llShare.setOnClickListener(this);
        binding.tvRepeatEndDate.setOnClickListener(this);
        binding.llInvite.setOnClickListener(this);
        binding.btCreate.setOnClickListener(this);
        binding.ivAdd.setOnClickListener(this);
        binding.tvFbEvents.setOnClickListener(this);
        binding.tvDelete.setOnClickListener(this);
        binding.mSpotyfy.setOnClickListener(this);
        binding.mHelp.setOnClickListener(this);
        binding.mNotificationShow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (binding.mNotificationShow.isChecked()) {
                }
            }
        });

        UsersListPOJO.Data item = new UsersListPOJO.Data();
        item.setId(Const.getLoggedInUser(mContext).id);
        item.setUsername(Const.getLoggedInUser(mContext).username);
        selectedUser.add(item);
        setHost(new ArrayList<>(selectedUser));

        binding.etEventInfo.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                binding.tvCount.setText(s.toString().isEmpty() ? getString(R.string._140_character) : (140 - s.length()) + " " + getString(R.string.character));
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.backIV:
                onBackPressed();
                break;
            case R.id.mHelp:
                Utils.showMessageDialog(mContext, "", mContext.getResources().getString(R.string.hide_nutritional_messge));
                break;
            case R.id.ivSwitch:
                EVENT_MODE = EVENT_MODE.equals(Const.ZERO) ? Const.ONE : Const.ZERO;
                binding.ivSwitch.setImageResource(EVENT_MODE.equals(Const.ZERO) ? R.drawable.ic_switch_unselected : R.drawable.ic_switch_selected);
                break;
            case R.id.btWebEvent:
                String url = "https://www.bashbusiness.com/?user_id=" + Const.getLoggedInUserID(mContext) + "&is_app=1&redirect_uri=https://www.bashbusiness.com/event/add";
                Utils.intentToBrowser(mContext, url);
                break;
            case R.id.ivRepeatSwitch:
                EVENT_REPEAT = EVENT_REPEAT.equals(Const.ZERO) ? Const.ONE : Const.ZERO;
                binding.ivRepeatSwitch.setImageResource(EVENT_REPEAT.equals(Const.ZERO) ? R.drawable.ic_switch_unselected : R.drawable.ic_switch_selected);
                binding.repeatDateLayout.setVisibility(EVENT_REPEAT.equals(Const.ZERO) ? View.GONE : View.VISIBLE);
                if (EVENT_REPEAT.equalsIgnoreCase(Const.ONE)) {
                    if (store.getString("isNotificationShow") == null) {
                        Utils.showCustomMessageDialog(mContext, "", mContext.getResources().getString(R.string.repeat_event_alert));
                    } else if (store.getString("isNotificationShow").equalsIgnoreCase("0")) {
                        Utils.showCustomMessageDialog(mContext, "", mContext.getResources().getString(R.string.repeat_event_alert));
                    }
                }
                break;
            case R.id.ivRestaurant:
                selectEventType(true, false, false);
                break;
            case R.id.ivClub:
                selectEventType(false, true, false);
                break;
            case R.id.ivBar:
                selectEventType(false, false, true);
                break;
            case R.id.tvStartDate:
                Utils.showDatePickerDialog(mContext, startDate, date -> {
                    startDate = date;
                    binding.tvStartDate.setText(Utils.changeDateFormat(startDate));
                    binding.tvStartTime.setText("");
                    if (EVENT_REPEAT.equalsIgnoreCase(Const.ONE)) {
                        binding.tvEndDate.setText(Utils.changeDateFormat(startDate));
                        endDate = date;
                    } else {
                        binding.tvEndDate.setText(Utils.changeDateFormat(Utils.addOneDay(startDate)));
                        endDate = Utils.addOneDay(startDate);
                    }
                });
                break;
            case R.id.tvEndDate:
                if (startDate.isEmpty()) {
                    Utils.showToast(mContext, getString(R.string.select_event_start_date));
                } else {
                 /*   if (EVENT_REPEAT.equalsIgnoreCase(Const.ONE)) {
                        Utils.showToast(mContext, mContext.getString(R.string.repeat_event_canbot_greater));
                    } else {*/
                    Utils.showDatePickerDialog(mContext, endDate, date -> {
                        if (Utils.isValidEndDate(startDate, date)) {
                            endDate = date;
                            binding.tvEndDate.setText(Utils.changeDateFormat(endDate));
                            binding.tvEndTime.setText("");
                        } else {
                            Utils.showToast(mContext, getString(R.string.end_date_msg));
                        }
                    });
//                    }
                }
                break;
            case R.id.tvRepeatEndDate:
                if (startDate.isEmpty()) {
                    Utils.showToast(mContext, getString(R.string.select_event_start_date));
                } else {
                    Utils.showRepeatDatePickerDialog(mContext, startDate, date -> {
//                        if (Utils.isValidEndDate(startDate, date)) {
                        repeatEndDate = date;
                        binding.tvRepeatEndDate.setText(Utils.changeDateFormat(repeatEndDate));
//                            binding.tvEndDate.setText(Utils.changeDateFormat(startDate));
                      /*  } else {
                            Utils.showToast(mContext, getString(R.string.end_date_msg));
                        }*/
                    });
                }
                break;
            case R.id.tvStartTime:
                if (startDate.isEmpty())
                    Utils.showToast(mContext, getString(R.string.select_event_start_date));
                else
                    Utils.showTimePickerDialog(mContext, startDate, binding.tvStartTime.getText().toString(), time -> {
                        binding.tvStartTime.setText(time);
                        binding.tvEndTime.setText("");
                    });
                break;
            case R.id.tvEndTime:
                if (endDate.isEmpty())
                    Utils.showToast(mContext, getString(R.string.select_event_end_date));
                else if (binding.tvStartTime.getText().toString().isEmpty())
                    Utils.showToast(mContext, getString(R.string.select_start_time));
                else
                    Utils.showTimePickerDialog(mContext, endDate, binding.tvEndTime.getText().toString(), time -> {
                        if (!Utils.isCorrectTime(startDate + " " + binding.tvStartTime.getText().toString(), endDate + " " + time))
                            Utils.showToast(mContext, getString(R.string.invalid_end_time));
                        else binding.tvEndTime.setText(time);
                    });
                break;
            case R.id.tvLocation:
                locationIntent();
                break;
            case R.id.llShare:
                fbShare();
                break;
            case R.id.llInvite:
                InviteFriendsFragment fragment1 = new InviteFriendsFragment();
                fragment1.setData(getInvitationContent(), bash_id);
                Utils.goToFragment(mContext, fragment1, R.id.fragment_container);
                break;
            case R.id.btCreate:
                if (checkValidation()) apiCreateEvent();
                break;
            case R.id.mSpotyfy:
                onRequestTokenClicked();
                break;

            case R.id.ivAdd:
                AddHostFragment fragment2 = new AddHostFragment();
                fragment2.setData(selectedUser, listener);
                Utils.goToFragment(mContext, fragment2, R.id.fragment_container);
                break;
            case R.id.tvFbEvents:
                Utils.goToFragment(mContext, new FbEventsFragment(), R.id.fragment_container);
                break;
            case R.id.tvDelete:
                Const.showCustomMessageDialog(mContext, getString(R.string.delete_event), getString(bashData.isCountDropVisible() ? R.string.want_to_delete_event : R.string.want_to_delete_event_free),
                        getString(R.string._yes), getString(R.string._no), v1 -> apiDeleteEvent(), null);
                break;
        }
    }

    private void setBashData() {
        binding.mainRepeatLayout.setVisibility(View.GONE);
        bash_id = bashData.id;
        EVENT_MODE = bashData.is_private;
        EVENT_TYPE = bashData.bash_type;
        startDate = bashData.start_date;
        endDate = bashData.end_date;
        binding.tvTitle.setText(getString(R.string.edit_your));
        selectEventType(bashData.bash_type.equals(Const.EVENT_RESTAURANT), bashData.bash_type.equals(Const.EVENT_CLUB), bashData.bash_type.equals(Const.EVENT_BAR));
        binding.ivSwitch.setImageResource(EVENT_MODE.equals(Const.ZERO) ? R.drawable.ic_switch_unselected : R.drawable.ic_switch_selected);
        category = bashData.category;
        if (bashData.getHideNationalFact() == 1) {
            binding.mShow.setChecked(true);
        }
        if (category.equalsIgnoreCase("") || category.equalsIgnoreCase("Bash Category")) {
            binding.spBashCategory.setSelection(0);
        } else {
            int pos = category.equals(Const.POOL_PARTY) ? 1 : category.equals(Const.QUEER_FRIENDLY) ? 2 : category.equals(Const.DESI_PARTY) ? 3 : category.equals(Const.GONE_COUNTRY) ? 4 : category.equals(Const.KARAOKE_TIME) ? 5 : 6;
            binding.spBashCategory.setSelection(pos);
        }

        if (!bashData.spotifyImages.equalsIgnoreCase("")) {
            spotifyLisk = bashData.spotifyLink;
            spotifyData = bashData.spotifyImages;
            spotifyUserName = bashData.spotifyUserName;
            SpotifyDetailPojo pojo2 = new Gson().fromJson(spotifyData, SpotifyDetailPojo.class);
            ArrayList<SpotifyListPOJO.Item> itemArrayList = new ArrayList<>();

            for (int i = 0; i < pojo2.getImages().size(); i++) {
                SpotifyListPOJO.Item item1 = new SpotifyListPOJO.Item();
                item1.setHref(pojo2.getImages().get(i).getUrl());
                item1.setId(pojo2.getImages().get(i).getId());
                item1.setName(pojo2.getImages().get(i).getName());
                item1.setSelected(true);
                SpotifyListPOJO.ExternalUrls externalUrls = new SpotifyListPOJO.ExternalUrls();
                externalUrls.setSpotify(pojo2.getImages().get(i).getUrl());
                item1.setExternalUrls(externalUrls);
                SpotifyListPOJO.Image image = new SpotifyListPOJO.Image();
                image.setHeight(300);
                image.setWidth(200);
                image.setUrl(pojo2.getImages().get(i).getImage());
                ArrayList<SpotifyListPOJO.Image> imageArrayList = new ArrayList<>();
                imageArrayList.add(image);
                item1.setImages(imageArrayList);
                itemArrayList.add(item1);
            }
//            pojo1.setItems(itemArrayList);
            pojo.setItems(itemArrayList);
            if (pojo.getItems().size() < 5) {
                int size = 5 - pojo.getItems().size();
                for (int i = 0; i < size; i++) {
                    SpotifyListPOJO.Item item1 = new SpotifyListPOJO.Item();
                    item1.setHref("");
                    item1.setId("");
                    item1.setName("");
                    SpotifyListPOJO.Image image = new SpotifyListPOJO.Image();
                    image.setHeight(300);
                    image.setWidth(200);
                    image.setUrl("");
                    ArrayList<SpotifyListPOJO.Image> imageArrayList = new ArrayList<>();
                    imageArrayList.add(image);
                    item1.setImages(imageArrayList);
                    itemArrayList.add(item1);
                }
            }
            pojo.setItems(itemArrayList);

            adapter = new AddSpotufyImagesAdapter(mContext, pojo.getItems(), pojo1);
            binding.spotifyRecyclerView.setAdapter(adapter);

        }
        if (!bashData.instaImages.equalsIgnoreCase("")) {
            instaLink = bashData.instaLink;
        }

        binding.etEventName.setText(bashData.name);
        binding.etEventInfo.setText(bashData.description);
        binding.tvStartDate.setText(bashData.start_date);
        binding.tvEndDate.setText(bashData.end_date);
        binding.tvStartTime.setText(Utils.changeTimeFormat2(bashData.start_time));
        binding.tvEndTime.setText(Utils.changeTimeFormat2(bashData.end_time));
        binding.tvLocation.setText(bashData.location);
        binding.etCharge.setText(bashData.charge);
        binding.etCharge.setEnabled(false);
        binding.etStartAge.setText(bashData.age);
        binding.etEndAge.setText(bashData.age_max);
        binding.btCreate.setText(getString(R.string.save));
        selectedUser.clear();
        for (BashDetailsPOJO.Hosts user : bashData.hosts) {
            UsersListPOJO.Data item = new UsersListPOJO.Data();
            item.setId(user.id);
            item.setUsername(user.username);
            selectedUser.add(item);
        }
        setHost(new ArrayList<>(selectedUser));
        binding.llOR.setVisibility(View.GONE);
        binding.tvFbEvents.setVisibility(View.GONE);
        binding.llShareOuter.setVisibility(View.VISIBLE);
        binding.tvDelete.setVisibility(bashData.delete.equals(Const.ONE) ? View.VISIBLE : View.GONE);
    }

    private void setHost(List<UsersListPOJO.Data> list) {
        hostID = "";
        String host = "";
        selectedUser.clear();
        selectedUser.addAll(list);
        for (UsersListPOJO.Data item : selectedUser) {
            host = host.isEmpty() ? item.username : host + "," + item.username;
            hostID = hostID.isEmpty() ? item.id : hostID + "," + item.id;
        }
        binding.etEventHost.setText(host);
    }

    private void selectEventType(boolean isRes, boolean isClub, boolean isBar) {
        binding.ivRestaurant.setImageResource(isRes ? R.drawable.ic_restaurant_dark : R.drawable.ic_restaurant_unselected);
        binding.ivClub.setImageResource(isClub ? R.drawable.ic_club_dark : R.drawable.ic_club_unselected);
        binding.ivBar.setImageResource(isBar ? R.drawable.ic_bar_dark : R.drawable.ic_bar_unselected);
        EVENT_TYPE = isRes ? Const.EVENT_RESTAURANT : (isClub ? Const.EVENT_CLUB : Const.EVENT_BAR);
    }

    private void fbShare() {
        ShareDialog shareDialog = new ShareDialog(this);
        shareDialog.registerCallback(callbackManager, new FacebookCallback<Sharer.Result>() {
            @Override
            public void onSuccess(Sharer.Result result) {
                Utils.showToast(mContext, getString(R.string.bash_share_success));
            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onError(FacebookException error) {

            }
        });
        String data = getString(R.string.fb_share_text, binding.etEventName.getText().toString(),
                binding.tvStartDate.getText().toString() + " " + binding.tvStartTime.getText().toString() + " - " +
                        binding.tvEndDate.getText().toString() + " " + binding.tvEndTime.getText().toString(),
                binding.tvLocation.getText().toString());
        ShareLinkContent content = new ShareLinkContent.Builder()
                .setContentUrl(Uri.parse(Const.APP_WEB_LINK))
                .setQuote(data)
                .build();
        ShareDialog.show(this, content);
    }

    private void locationIntent() {
        List<Place.Field> fields = Arrays.asList(Place.Field.ID, Place.Field.NAME, Place.Field.ADDRESS, Place.Field.LAT_LNG);
        Intent intent = new Autocomplete.IntentBuilder(AutocompleteActivityMode.FULLSCREEN, fields).build(mContext);
        startActivityForResult(intent, 100);
    }

    @SuppressLint("MissingSuperCall")
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100) {
            if (resultCode == RESULT_OK) {
                Place place = Autocomplete.getPlaceFromIntent(data);
                selectedPlace = place;
                binding.tvLocation.setText(place.getName() != null && !place.getAddress().contains(place.getName()) ?
                        place.getName() + ", " + place.getAddress() : place.getAddress());
            }
        } else if (AUTH_TOKEN_REQUEST_CODE == requestCode) {
            final AuthenticationResponse response = AuthenticationClient.getResponse(resultCode, data);
            mAccessToken = response.getAccessToken();
            startProgressDialog();
            onGetProfileClicked();
        }
    }

    @MainThread
    private void onGetProfileClicked() {
        if (mAccessToken == null) {
            Toast.makeText(mContext, "Unable to get user playlist.", Toast.LENGTH_SHORT).show();
            stopProgressDialog();
            return;
        }
        final Request request = new Request.Builder()
                .url("https://api.spotify.com/v1/me")
                .addHeader("Authorization", "Bearer " + mAccessToken)
                .build();

        cancelCall();
        mCall = mOkHttpClient.newCall(request);
        mCall = mOkHttpClient.newCall(request);
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mCall.enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        showToast("Unable to get user playlist.");
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        binding.mSpotyfyText.setText("Connected to Spotify");

                        try {
                            final JSONObject jsonObject = new JSONObject(response.body().string());
                            String ss = jsonObject.toString(3);
                            pojo = new Gson().fromJson(ss, SpotifyListPOJO.class);
                            spotifyLisk = pojo.getExternalUrls().getSpotify();
                            spotifyId = pojo.getId();
                            spotifyUserName = pojo.getDisplayName();
                            onGetUserProfileClicked(pojo.getId());
                        } catch (JSONException e) {
                            showToast(e.toString());
                        }
                    }
                });
            }
        });
    }

    @MainThread
    public void onGetUserProfileClicked(String id) {
        if (mAccessToken == null) {
            Toast.makeText(mContext, "Unable to get user playlist.", Toast.LENGTH_SHORT).show();
            stopProgressDialog();
            return;
        }

        final Request request = new Request.Builder()
                .url("https://api.spotify.com/v1/me/playlists?limit=50")
                .addHeader("Authorization", "Bearer " + mAccessToken)
                .build();
        cancelCall();
        mCall = mOkHttpClient.newCall(request);
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mCall.enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        showToast("Unable to get user playlist.");
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        try {
                            final JSONObject jsonObject = new JSONObject(response.body().string());
                            String ss = jsonObject.toString(3);
                            pojo1 = new Gson().fromJson(ss, SpotifyListPOJO.class);
                            if (pojo1.getItems().size() > 0) {
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {

                                        SpotifyListPOJO.Item item1 = new SpotifyListPOJO.Item();
                                        item1.setHref("");
                                        item1.setId("");
                                        item1.setName("");
                                        SpotifyListPOJO.Image image = new SpotifyListPOJO.Image();
                                        image.setHeight(300);
                                        image.setWidth(200);
                                        image.setUrl("");
                                        ArrayList<SpotifyListPOJO.Image> imageArrayList = new ArrayList<>();
                                        imageArrayList.add(image);
                                        item1.setImages(imageArrayList);
                                        ArrayList<SpotifyListPOJO.Item> itemArrayList = new ArrayList<>();
                                        itemArrayList.add(item1);
                                        itemArrayList.add(item1);
                                        itemArrayList.add(item1);
                                        itemArrayList.add(item1);
                                        itemArrayList.add(item1);
                                        pojo.setItems(itemArrayList);
                                        adapter = new AddSpotufyImagesAdapter(mContext, pojo.getItems(), pojo1);
                                        binding.spotifyRecyclerView.setAdapter(adapter);
//                                        binding.recyclerView.setAdapter(adapter);
                                    }
                                });
                                DialogPlaylist dialog = new DialogPlaylist(mContext, pojo1.items);
                                dialog.show(getSupportFragmentManager(), dialog.getTag());

                                stopProgressDialog();

                            } else {
                                showToast("You don't have any playlist on your Spotify account.");
                            }
                        } catch (JSONException e) {
                            showToast("Unable to get user playlist.");
                        }
                    }
                });
            }
        });
    }

    private boolean checkValidation() {
        if (binding.etEventName.getText().toString().isEmpty()) {
            Utils.showToast(mContext, getString(R.string.enter_event_name));
            return false;
        } else if (binding.etEventHost.getText().toString().isEmpty()) {
            Utils.showToast(mContext, getString(R.string.enter_host_name));
            return false;
        } else if (binding.tvStartDate.getText().toString().isEmpty()) {
            Utils.showToast(mContext, getString(R.string.select_event_start_date));
            return false;
        } else if (binding.tvEndDate.getText().toString().isEmpty()) {
            Utils.showToast(mContext, getString(R.string.select_event_end_date));
            return false;
        } else if (binding.tvStartTime.getText().toString().isEmpty()) {
            Utils.showToast(mContext, getString(R.string.select_start_time));
            return false;
        } else if (binding.tvEndTime.getText().toString().isEmpty()) {
            Utils.showToast(mContext, getString(R.string.select_end_time));
            return false;
        } else if (binding.tvLocation.getText().toString().isEmpty()) {
            Utils.showToast(mContext, getString(R.string.pick_event_location));
            return false;
        } /*else if (binding.etCharge.getText().toString().isEmpty()) {
            Utils.showToast(mContext, getString(R.string.enter_cover_charge));
            return false;
        }*/ else if (binding.etStartAge.getText().toString().isEmpty() || Integer.parseInt(binding.etStartAge.getText().toString()) < 15) {
            Utils.showToast(mContext, getString(R.string.min_age_limit_msg));
            return false;
        } else if (!binding.etEndAge.getText().toString().isEmpty() && Integer.parseInt(binding.etEndAge.getText().toString()) <
                Integer.parseInt(binding.etStartAge.getText().toString())) {
            Utils.showToast(mContext, getString(R.string.max_age_limit_msg));
            return false;
        } else {
            return true;
        }
    }

    private void disableAllViews() {
        if (spotifyData.equalsIgnoreCase("") && instaData.equalsIgnoreCase("")) {
            binding.mImagesLayout.setVisibility(View.GONE);
        } else if (spotifyLisk.equalsIgnoreCase("")) {
            binding.mSpotyfyLayout.setVisibility(View.GONE);
        } else if (instaLink.equalsIgnoreCase("")) {
//            binding.mInstagramLayout.setVisibility(View.GONE);
        }
        adapter.isClickable = false;
//        binding.mInstagram.setEnabled(false);
        binding.mShow.setEnabled(false);
        binding.mHelp.setEnabled(false);
        binding.mSpotyfy.setEnabled(false);
        binding.spBashCategory.setEnabled(false);
        binding.ivRepeatSwitch.setEnabled(false);
        binding.tvRepeatEndDate.setEnabled(false);
        binding.ivRestaurant.setEnabled(false);
        binding.ivClub.setEnabled(false);
        binding.ivBar.setEnabled(false);
        binding.ivSwitch.setEnabled(false);
        binding.etEventName.setEnabled(false);
        binding.etEventInfo.setEnabled(false);
        binding.etEventHost.setEnabled(false);
        binding.tvStartDate.setEnabled(false);
        binding.tvEndDate.setEnabled(false);
        binding.tvStartTime.setEnabled(false);
        binding.tvEndTime.setEnabled(false);
        binding.tvLocation.setEnabled(false);
        binding.etCharge.setEnabled(false);
        binding.etStartAge.setEnabled(false);
        binding.etEndAge.setEnabled(false);
        binding.ivAdd.setEnabled(false);
    }

    private String getInvitationContent() {
        return getString(R.string.invite_share_text,
                Const.getLoggedInUser(mContext).getFullName(),
                binding.etEventName.getText().toString(),
                binding.tvStartDate.getText().toString() + " " + binding.tvStartTime.getText().toString() + " - " +
                        binding.tvEndDate.getText().toString() + " " + binding.tvEndTime.getText().toString(),
                binding.tvLocation.getText().toString(),
                Const.getInviteLink(mContext, bash_id));
    }

    private void apiCreateEvent() {
        isDelete = false;
        registerEventBus();
        JsonObject object = new JsonObject();
        JsonArray array = new JsonArray();
        if (pojo1.getItems() == null) {
            pojo1 = pojo;
        }
        for (int i = 0; i < pojo1.getItems().size(); i++) {
            if (pojo1.getItems().get(i).isSelected()) {
                JsonObject object1 = new JsonObject();
                object1.addProperty("id", pojo1.getItems().get(i).getId());
                object1.addProperty("name", pojo1.getItems().get(i).getName());
                object1.addProperty("image", pojo1.getItems().get(i).getImages().get(0).getUrl());
                object1.addProperty("url", pojo1.getItems().get(i).getExternalUrls().getSpotify());
                array.add(object1);
            }
        }

        object.add("images", array);
        spotifyData = object.toString();
        if (array.size() == 0) {
            spotifyLisk = "";
            spotifyData = "";
            spotifyUserName = "";
        }
        if (instaData.equalsIgnoreCase("")) {
            instaLink = "";
        }
        String mShow = "0";
        if (binding.mShow.isChecked()) {
            mShow = "1";
        }
        EventBus.getDefault().post(new Events.RequestBasic(mContext, Const.apiCreateBash(mContext, spotifyUserName, spotifyLisk, spotifyData, category, repeatEndDate, EVENT_TYPE,
                binding.etEventName.getText().toString(), hostID,
                startDate, endDate, Utils.changeTimeFormat1(binding.tvStartTime.getText().toString()),
                Utils.changeTimeFormat1(binding.tvEndTime.getText().toString()),
                binding.tvLocation.getText().toString(),
                selectedPlace != null ? "" + Objects.requireNonNull(selectedPlace.getLatLng()).latitude : bashData.lat,
                selectedPlace != null ? "" + selectedPlace.getLatLng().longitude : bashData.lng,
                binding.etCharge.getText().toString(), binding.etStartAge.getText().toString(),
                binding.etEndAge.getText().toString(), EVENT_MODE, binding.etEventInfo.getText().toString(), bash_id, mShow), true, false));
    }

    private void apiDeleteEvent() {
        isDelete = true;
        registerEventBus();
        EventBus.getDefault().post(new Events.RequestBasic(mContext, Const.apiDeleteEvent(mContext, bash_id), true, false));
    }

    @Subscribe
    public void apiCreateEventRes(Events.GetBasicData res) {
        unRegisterEventBus();
        if (isDelete) {
            Utils.showMessageDialog(mContext, "", getString(R.string.event_delete_success), (dialog, which) -> binding.backIV.performClick());
        } else {
            bash_id = res.getData().data.id;
            UserPOJO.Data user = Const.getLoggedInUser(mContext);
            user.setToday_bash_count(res.getData().data.count);
            Const.setLoggedInUser(mContext, user);
            mLiveModel.getUserLiveData().setValue(user);
            Utils.showToast(mContext, bashData != null ? getString(R.string.event_update_success) : getString(R.string.event_create_success));
            disableAllViews();
            if (EVENT_TYPE.equals(Const.EVENT_RESTAURANT)) {
                binding.ivClub.setVisibility(View.GONE);
                binding.ivBar.setVisibility(View.GONE);
            }
            if (EVENT_TYPE.equals(Const.EVENT_BAR)) {
                binding.ivRestaurant.setVisibility(View.GONE);
                binding.ivClub.setVisibility(View.GONE);
            }
            if (EVENT_TYPE.equals(Const.EVENT_CLUB)) {
                binding.ivRestaurant.setVisibility(View.GONE);
                binding.ivBar.setVisibility(View.GONE);
            }
            binding.ivAdd.setVisibility(View.GONE);
            binding.ivSwitch.setVisibility(View.GONE);
            binding.tvChoose.setVisibility(View.GONE);
            binding.btCreate.setVisibility(View.GONE);
            binding.llOR.setVisibility(View.GONE);
            binding.tvFbEvents.setVisibility(View.GONE);
            binding.llShareOuter.setVisibility(View.VISIBLE);
        }
        if (bgListener != null) bgListener.onBgApi();
        ((MainActivity) mContext).apiGetBash(false);
        ((MainActivity) mContext).getFbImage();
    }


    public void onRequestTokenClicked() {
        final AuthenticationRequest request = getAuthenticationRequest(AuthenticationResponse.Type.TOKEN);
        AuthenticationClient.openLoginActivity(this, AUTH_TOKEN_REQUEST_CODE, request);
    }

    private AuthenticationRequest getAuthenticationRequest(AuthenticationResponse.Type type) {
        return new AuthenticationRequest.Builder(CLIENT_ID, type, REDIRECT_URL)
                .setShowDialog(true)
                .setScopes(new String[]{"playlist-read-collaborative", "user-read-currently-playing", "playlist-read-private"})
                /* .setCampaign("your-campaign-token")*/
                .build();
    }

    private void cancelCall() {
        if (mCall != null) {
            mCall.cancel();
        }
    }

}
