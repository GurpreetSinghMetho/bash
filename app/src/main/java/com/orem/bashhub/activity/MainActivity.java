package com.orem.bashhub.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.CompoundButton;

import com.braintreepayments.api.interfaces.BraintreeErrorListener;
import com.braintreepayments.api.interfaces.PaymentMethodNonceCreatedListener;
import com.braintreepayments.api.models.PaymentMethodNonce;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.maps.android.SphericalUtil;
import com.google.maps.android.clustering.ClusterManager;
import com.orem.bashhub.R;
import com.orem.bashhub.data.BashDetailsPOJO;
import com.orem.bashhub.data.FbFriendsLocPOJO;
import com.orem.bashhub.data.FbFriendsPOJO;
import com.orem.bashhub.data.UserPOJO;
import com.orem.bashhub.data.VenueListPOJO;
import com.orem.bashhub.databinding.ActivityMainBinding;
import com.orem.bashhub.databinding.MapBubbleBinding;
import com.orem.bashhub.dialogs.DialogBashCheckIn;
import com.orem.bashhub.dialogs.DialogCheckInConfirmation;
import com.orem.bashhub.dialogs.DialogmapListBashes;
import com.orem.bashhub.fragment.AddHostFragment;
import com.orem.bashhub.fragment.BashDetailsWebFragment;
import com.orem.bashhub.fragment.BashDetialFragment;
import com.orem.bashhub.fragment.BashPreferencesFragment;
import com.orem.bashhub.fragment.BitEmojiFragment;
import com.orem.bashhub.fragment.CalendarFragment;
import com.orem.bashhub.fragment.ChangeOutfitFragment;
import com.orem.bashhub.fragment.ConfirmOrderFragment;
import com.orem.bashhub.fragment.DrinkBottleListFragment;
import com.orem.bashhub.fragment.EditEmailPhoneFragment;
import com.orem.bashhub.fragment.EditProfileFragment;
import com.orem.bashhub.fragment.EnterBashIdFragment;
import com.orem.bashhub.fragment.EventUsersFragment;
import com.orem.bashhub.fragment.FaqFragment;
import com.orem.bashhub.fragment.FbEventsFragment;
import com.orem.bashhub.fragment.FollowersFragment;
import com.orem.bashhub.fragment.FragmentImageTableSection;
import com.orem.bashhub.fragment.HelpFragment;
import com.orem.bashhub.fragment.HostHubDetailFragment;
import com.orem.bashhub.fragment.HostHubFragment;
import com.orem.bashhub.fragment.InviteFriendsFragment;
import com.orem.bashhub.fragment.ListOfMemberFragment;
import com.orem.bashhub.fragment.MyBashFragment;
import com.orem.bashhub.fragment.MyBashVenuwDetail;
import com.orem.bashhub.fragment.MyBashWebDetailFragment;
import com.orem.bashhub.fragment.OrderDetailFragment;
import com.orem.bashhub.fragment.PaymentFragment;
import com.orem.bashhub.fragment.ProfileEventsFragment;
import com.orem.bashhub.fragment.ProfileFragment;
import com.orem.bashhub.fragment.ProfileUsersFragment;
import com.orem.bashhub.fragment.RatingFragment;
import com.orem.bashhub.fragment.ReportProblemFragment;
import com.orem.bashhub.fragment.RequestPricingFragment;
import com.orem.bashhub.fragment.SafeRideFragment;
import com.orem.bashhub.fragment.ScanTicketFragment;
import com.orem.bashhub.fragment.SearchBashFragment;
import com.orem.bashhub.fragment.SettingsFragment;
import com.orem.bashhub.fragment.SubmitReportFragment;
import com.orem.bashhub.fragment.TableSectionListFragment;
import com.orem.bashhub.fragment.TicketsFragment;
import com.orem.bashhub.fragment.UberRequestFragment;
import com.orem.bashhub.fragment.VenueDetailsFragment;
import com.orem.bashhub.interfaces.OnDateChange;
import com.orem.bashhub.utils.Const;
import com.orem.bashhub.utils.MyLocationPicker;
import com.orem.bashhub.utils.PrefStore;
import com.orem.bashhub.utils.TinyDB;
import com.orem.bashhub.utils.Utils;
import com.orem.bashhub.utils.apiinterface.Events;
import com.orem.bashhub.utils.clustering.CustomClusterRenderer;
import com.orem.bashhub.utils.clustering.MyClusterItem;
import com.snapchat.kit.sdk.bitmoji.OnBitmojiSelectedListener;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Objects;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import okhttp3.Call;
import okhttp3.OkHttpClient;

@SuppressWarnings("unused")
public class MainActivity extends BaseActivity implements OnMapReadyCallback, OnBitmojiSelectedListener,
        PaymentMethodNonceCreatedListener, BraintreeErrorListener {
    public static final String CLIENT_ID = "19db39b9dcf8400ba1ca2c875c9802e1";
    public static final int AUTH_TOKEN_REQUEST_CODE = 0x10;
    public static ActivityMainBinding binding;
    public static Activity ctx;
    private final OkHttpClient mOkHttpClient = new OkHttpClient();
    public boolean isCalled = true;
    public GoogleMap mGoogleMap;
    public static  boolean isFirstTime = true, isIncSize = false, isClickMarker = false, isUploadFB = true;
    public static Calendar homeCalendar;
    public static String searchText = "";
    SupportMapFragment mapFrag;
    FusedLocationProviderClient mFusedLocationClient;
    LocationCallback mLocationCallback = new LocationCallback() {
        @Override
        public void onLocationResult(LocationResult locationResult) {
            if (locationResult != null) {
                apiUpdateLocation("" + locationResult.getLastLocation().getLatitude(),
                        "" + locationResult.getLastLocation().getLongitude());
            }
        }
    };
    private String mAccessToken;
    private String mAccessCode;
    private Call mCall;
    private List<BashDetailsPOJO> bashList = new ArrayList<>();
    private List<VenueListPOJO.Venue> venueList = new ArrayList<>();
    private List<FbFriendsPOJO.Data> friendsList = new ArrayList<>();
    //private List<Marker> mBashMarkers = new ArrayList<>();
    private List<Marker> mFriendsMarkers = new ArrayList<>();
    private List<Bitmap> mFriendsBitmap = new ArrayList<>();
    private List<MyClusterItem> clusterItems = new ArrayList<>();
    private Marker myMarker = null, selectedMarker = null, bigMarker = null;
    private Bitmap myBitmap;
    private float oldZoom = 10;
    private ClusterManager<MyClusterItem> mClusterManager;
    private Handler pongHandler = new Handler();
    private Runnable pongRunnable = this::apiFbFriendsLoc;
    private boolean isVenue = true;
    private OnDateChange listener = date -> {
        homeCalendar.setTime(date);
        setDate();
        isFirstTime = true;
        callBashApi();
        binding.tvToday.setVisibility(Utils.isDateEquals(Calendar.getInstance().getTime(), date) ? View.GONE : View.VISIBLE);
    };
    private UserPOJO.Data userdata;

    @Override
    public void onBackPressed() {
        Utils.hideKeyboard(mContext);
        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.fragment_container);
        if (binding.fragmentContainer.getVisibility() == View.GONE) {
            finish();
            finishAffinity();
        } else if (fragment instanceof EditProfileFragment) {
            if (((EditProfileFragment) fragment).isEdit)
                ((EditProfileFragment) fragment).onBackPress();
            else getSupportFragmentManager().popBackStack();
        } else if (fragment instanceof RatingFragment) {
            ((RatingFragment) fragment).onBackPressed();
            getSupportFragmentManager().popBackStack();
        } else if (fragment instanceof FollowersFragment || fragment instanceof BitEmojiFragment ||
                fragment instanceof InviteFriendsFragment || fragment instanceof EditEmailPhoneFragment ||
                fragment instanceof AddHostFragment || fragment instanceof FbEventsFragment ||
                fragment instanceof ChangeOutfitFragment || fragment instanceof ScanTicketFragment ||
                fragment instanceof EnterBashIdFragment || fragment instanceof TicketsFragment || fragment instanceof OrderDetailFragment ||
                /*fragment instanceof CreateBashFragment ||*/ fragment instanceof UberRequestFragment ||
                fragment instanceof HelpFragment || fragment instanceof ReportProblemFragment ||
                fragment instanceof SubmitReportFragment || fragment instanceof FaqFragment ||
                fragment instanceof HostHubDetailFragment || fragment instanceof EventUsersFragment || fragment instanceof EventUsersFragment ||
                fragment instanceof ProfileUsersFragment || fragment instanceof MyBashVenuwDetail || fragment instanceof MyBashWebDetailFragment || fragment instanceof ConfirmOrderFragment || fragment instanceof DrinkBottleListFragment || fragment instanceof FragmentImageTableSection || fragment instanceof TableSectionListFragment || fragment instanceof RequestPricingFragment || fragment instanceof ProfileEventsFragment) {
            getSupportFragmentManager().popBackStack();
        } else if (fragment instanceof ProfileFragment || fragment instanceof SafeRideFragment
                || fragment instanceof MyBashFragment || fragment instanceof BashPreferencesFragment
                || fragment instanceof CalendarFragment || fragment instanceof ListOfMemberFragment
                || fragment instanceof VenueDetailsFragment || fragment instanceof BashDetailsWebFragment || fragment instanceof BashDetialFragment || fragment instanceof PaymentFragment
                || fragment instanceof HostHubFragment || fragment instanceof SettingsFragment) {
            binding.fragmentContainer.setVisibility(View.GONE);
            binding.bashhubLL.setVisibility(View.VISIBLE);
            FragmentManager fm = getSupportFragmentManager();
            for (int i = 0; i < fm.getBackStackEntryCount(); ++i) {
                fm.popBackStack();
            }
        } else if (fragment instanceof SearchBashFragment) {
            binding.fragmentContainer.setVisibility(View.GONE);
            binding.bashhubLL.setVisibility(View.VISIBLE);
            FragmentManager fm = getSupportFragmentManager();
            for (int i = 0; i < fm.getBackStackEntryCount(); ++i) {
                fm.popBackStack();
            }
            apiFbFriends();
        }
    }

   /* @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (AUTH_TOKEN_REQUEST_CODE == requestCode) {
            super.onActivityResult(requestCode, resultCode, data);
            final AuthenticationResponse response = AuthenticationClient.getResponse(resultCode, data);
            mAccessToken = response.getAccessToken();
            onGetUserProfileClicked();
        }
    }*/

   /* public void onGetUserProfileClicked() {
        if (mAccessToken == null) {
            Toast.makeText(mContext, "Unable to get user playlist.", Toast.LENGTH_SHORT).show();
            return;
        }

        final Request request = new Request.Builder()
                .url("https://api.spotify.com/v1/users/vswdoruf6bqpujtcc9irmu0dt/playlists")
                .addHeader("Authorization", "Bearer " + mAccessToken)
                .build();

        cancelCall();
        mCall = mOkHttpClient.newCall(request);

        mCall.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Toast.makeText(mContext, "Unable to get user playlist.", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                try {
                    final JSONObject jsonObject = new JSONObject(response.body().string());
                    String ss = jsonObject.toString(3);
                    SpotifyListPOJO pojo = new Gson().fromJson(ss, SpotifyListPOJO.class);
                    if (pojo.getItems().size() > 0) {
                        Log.e("playlist", pojo.getItems().get(0).getName().toString());
                        CreateBashFragment fragment=new CreateBashFragment();
                        fragment.addImage(pojo.getItems());
                    } else {
                        Toast.makeText(mContext, "You don't have any playlist on your spotify account.", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    Toast.makeText(mContext, "Unable to get user playlist.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void onRequestTokenClicked() {
        final AuthenticationRequest request = getAuthenticationRequest(AuthenticationResponse.Type.TOKEN);
        AuthenticationClient.openLoginActivity(this, AUTH_TOKEN_REQUEST_CODE, request);
    }

    private AuthenticationRequest getAuthenticationRequest(AuthenticationResponse.Type type) {
        return new AuthenticationRequest.Builder(CLIENT_ID, type, REDIRECT_URL)
                .setShowDialog(false)
                .setScopes(new String[]{"user-read-email"})
                *//* .setCampaign("your-campaign-token")*//*
                .build();
    }

    private void cancelCall() {
        if (mCall != null) {
            mCall.cancel();
        }
    }*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        store = new PrefStore(mContext);
        ctx = this;
        init();
        setUser();
        apiSendInvitations();
        callBashApi();
        getFbImage();
        registerEventBus();
        MyLocationPicker.getCurrentLocation(mContext);
        if (getIntent().getExtras() != null) {
            String type = getIntent().getStringExtra(Const.NOTI_TYPE) != null ? getIntent().getStringExtra(Const.NOTI_TYPE) : "";
            String bash_id = getIntent().getStringExtra(Const.NOTI_ID) != null ? getIntent().getStringExtra(Const.NOTI_ID) : "";

            if ((Objects.requireNonNull(type).equals(Const.NOTI_INVITATION) || type.equals(Const.NOTI_UPDATE_EVENT)) && !Objects.requireNonNull(bash_id).isEmpty())
                apiSingleBash(bash_id);
            if (type.equals(Const.NOTI_WALLET))
                binding.drawer.llPayments.performClick();
            if (type.equals(Const.NOTI_FOLLOW) && !Objects.requireNonNull(bash_id).isEmpty()) {
                FollowersFragment fragment = new FollowersFragment();
                fragment.setData(bash_id);
                Utils.goToFragment(mContext, fragment, R.id.fragment_container);
                binding.fragmentContainer.setVisibility(View.VISIBLE);
            }
            if (type.equals(Const.NOTI_MYBASH_REMINDER)) {
                Utils.goToFragment(mContext, new MyBashFragment(isVenue), R.id.fragment_container);
                binding.fragmentContainer.setVisibility(View.VISIBLE);
            }
            if (type.equals(Const.NOTI_BARTENDER_RATING)) {
                Intent intent = new Intent(mContext, BartenderTipRatingActivity.class);
                String amount = getIntent().getStringExtra("amount") != null ? getIntent().getStringExtra("amount") : "";
                String bartender_id = getIntent().getStringExtra("bartender_id") != null ? getIntent().getStringExtra("bartender_id") : "";
                String order_id = getIntent().getStringExtra("order_id") != null ? getIntent().getStringExtra("order_id") : "";

                intent.putExtra("order_id", order_id);
                intent.putExtra("amount", amount);
                intent.putExtra("bartender_id", bartender_id);
                startActivity(intent);
            }
        }
        if (!mTinyDB.getString(Const.SHARE_BASH_ID).isEmpty())
            apiSingleBash(mTinyDB.getString(Const.SHARE_BASH_ID));
        if (!mTinyDB.getString(Const.SHARE_VENUE_ID).isEmpty())
            apiSingleVenue(mTinyDB.getString(Const.SHARE_VENUE_ID));

        binding.mToggle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    binding.mTitle.setText("Event List");
                    isVenue = false;
                    apiGetBash(true);
                    binding.mPurchaseImage.setImageDrawable(getDrawable(R.drawable.event_purchase));
                    binding.tvBashCount.setText(userdata.getToday_bash_count());
                } else {
                    binding.mTitle.setText("Business List");
                    isVenue = true;
                    binding.mPurchaseImage.setImageDrawable(getDrawable(R.drawable.business_purchase));
                    apiGetVenue(true);
                    binding.tvBashCount.setText(userdata.getToday_venue_count());
                }
            }
        });
    }

    @SuppressLint({"ClickableViewAccessibility", "SetTextI18n"})
    private void init() {
        homeCalendar = Calendar.getInstance();
        setDate();
        mapFrag = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        Objects.requireNonNull(mapFrag).getMapAsync(this);
        try {
            PackageInfo info = getPackageManager().getPackageInfo(getPackageName(), 0);
            binding.drawer.tvVersion.setText(getString(R.string.menu_app_version) + " " + info.versionName);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        binding.drawerIV.setOnClickListener(this);
        binding.addIV.setOnClickListener(this);
        binding.mybashIV.setOnClickListener(this);
        binding.bashhubLL.setOnClickListener(this);
        binding.clubIV.setOnClickListener(this);
        binding.calenderIV.setOnClickListener(this);
        binding.ivMyLocation.setOnClickListener(this);
        binding.tvToday.setOnClickListener(this);
        binding.ivUberReq.setOnClickListener(this);
        binding.ivAddVenu.setOnClickListener(this);
        binding.drawer.ivImage.setOnClickListener(this);
        binding.drawer.searchLL.setOnClickListener(this);
        binding.drawer.safeRideLL.setOnClickListener(this);
        binding.drawer.profileLL.setOnClickListener(this);
        binding.drawer.bashPrefLL.setOnClickListener(this);
        binding.drawer.checkinLL.setOnClickListener(this);
        binding.drawer.backIV.setOnClickListener(this);
        binding.drawer.logoutLL.setOnClickListener(this);
        binding.drawer.bitmojiLL.setOnClickListener(this);
        binding.drawer.llInvite.setOnClickListener(this);
        binding.drawer.llPayments.setOnClickListener(this);
        binding.drawer.llGetHelp.setOnClickListener(this);
        binding.drawer.llCheckIn.setOnClickListener(this);
        binding.drawer.llHost.setOnClickListener(this);
        binding.drawer.llSettings.setOnClickListener(this);

        binding.bashhubLL.setOnTouchListener((view, motionEvent) -> {
            if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                binding.bashhubLL.setVisibility(View.GONE);
                DialogmapListBashes bottomSheetFragment = new DialogmapListBashes(binding.bashhubLL, bashList, venueList, isVenue);
                bottomSheetFragment.show(getSupportFragmentManager(), bottomSheetFragment.getTag());
            }
            return true;
        });
    }

    public void clearAllFragments() {
        binding.fragmentContainer.setVisibility(View.GONE);
        FragmentManager fm = getSupportFragmentManager();
        for (int i = 0; i < fm.getBackStackEntryCount(); ++i) {
            fm.popBackStack();
        }
    }

    public void setUser() {
        final Observer<UserPOJO.Data> observer = data -> {
            userdata=data;
            binding.setData(data);
            if (isVenue)
            binding.tvBashCount.setText(userdata.getToday_venue_count());
            else
            binding.tvBashCount.setText(userdata.getToday_bash_count());

            loadMyMarker();
            binding.drawer.ivCheckInTick.setImageResource(data.isActiveEvent() ? R.drawable.bash_check_in_100 : R.drawable.bash_check_in_100_gray);
            binding.drawer.ivCheckInBash.setImageResource(data.isActiveEvent() ? R.drawable.bash_icon_192 : R.drawable.bash_icon_192_gray);
            binding.drawer.tvCheckInText.setTextColor(ContextCompat.getColor(mContext, data.isActiveEvent() ? R.color.black : R.color.gray));
        };
        mLiveModel.getUserLiveData().observe((LifecycleOwner) mContext, observer);
        mLiveModel.getUserLiveData().setValue(Const.getLoggedInUser(mContext));
    }

    public void setDate() {
        binding.tvDayName.setText(Utils.getDayName(homeCalendar.getTime()));
        binding.tvDayNo.setText(Utils.getDayNo(homeCalendar.getTime()));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.drawerIV:
                binding.drawerLayout.openDrawer(GravityCompat.START);
                break;
            case R.id.ivImage:
            case R.id.profileLL:
                finishAllFragment();
                isCalled = false;
                Utils.goToFragment(mContext, new ProfileFragment(), R.id.fragment_container);
                binding.drawerLayout.closeDrawers();
                binding.fragmentContainer.setVisibility(View.VISIBLE);
                break;
            case R.id.ivAddVenu:
                String url = "https://www.bashbusiness.com/?user_id=" + Const.getLoggedInUserID(mContext) + "&is_app=1&redirect_uri=https://www.bashbusiness.com/user/chat";
                Utils.intentToBrowser(mContext, url);
                break;
            case R.id.llPayments:
                finishAllFragment();
                Utils.goToFragment(mContext, new PaymentFragment(), R.id.fragment_container);
                binding.drawerLayout.closeDrawers();
                binding.fragmentContainer.setVisibility(View.VISIBLE);
                break;
            case R.id.searchLL:
                finishAllFragment();
                Utils.goToFragment(mContext, new SearchBashFragment(), R.id.fragment_container);
                binding.drawerLayout.closeDrawers();
                binding.fragmentContainer.setVisibility(View.VISIBLE);
                break;
            case R.id.safeRideLL:
                finishAllFragment();
                Utils.goToFragment(mContext, new SafeRideFragment(), R.id.fragment_container);
                binding.drawerLayout.closeDrawers();
                binding.fragmentContainer.setVisibility(View.VISIBLE);
                break;
            case R.id.addIV:
//                onRequestTokenClicked();
                Intent intent = new Intent(this, CreateBashActivity.class);
                startActivity(intent);
//                Utils.goToFragment(mContext, new CreateBashFragment(), R.id.fragment_container);
                binding.fragmentContainer.setVisibility(View.VISIBLE);
                break;
            case R.id.mybashIV:

                Utils.goToFragment(mContext, new MyBashFragment(isVenue), R.id.fragment_container);
                binding.fragmentContainer.setVisibility(View.VISIBLE);
                break;
            case R.id.bashPrefLL:
                finishAllFragment();
                Utils.goToFragment(mContext, new BashPreferencesFragment(), R.id.fragment_container);
                binding.drawerLayout.closeDrawers();
                binding.fragmentContainer.setVisibility(View.VISIBLE);
                break;
            case R.id.clubIV:
                Utils.goToFragment(mContext, new BashDetialFragment(), R.id.fragment_container);
                binding.drawerLayout.closeDrawers();
                binding.fragmentContainer.setVisibility(View.VISIBLE);
                break;
            case R.id.bashhubLL:
                binding.bashhubLL.setVisibility(View.GONE);
                DialogmapListBashes bottomSheetFragment = new DialogmapListBashes(binding.bashhubLL, bashList, venueList, isVenue);
                bottomSheetFragment.show(getSupportFragmentManager(), bottomSheetFragment.getTag());
                break;
            case R.id.checkinLL:
            case R.id.llCheckIn:
                binding.drawerLayout.closeDrawers();
                apiCheckInList();
                break;
            case R.id.calenderIV:
                CalendarFragment fragment = new CalendarFragment();
                fragment.setData(homeCalendar, listener);
                Utils.goToFragment(mContext, fragment, R.id.fragment_container);
                binding.fragmentContainer.setVisibility(View.VISIBLE);
                break;
            case R.id.backIV:
                binding.drawerLayout.closeDrawers();
                break;
            case R.id.bitmojiLL:
                try {
                    startActivity(getPackageManager().getLaunchIntentForPackage("com.bitstrips.imoji"));
                } catch (Exception e) {
                    Utils.intentToBrowser(mContext, Const.BITMOJI_APPLINK);
                }
                break;
            case R.id.ivMyLocation:
                loadMyMarker();
                moveCameraToMyLocation();
                break;
            case R.id.tvToday:
                listener.onDateChange(Calendar.getInstance().getTime());
                break;
            case R.id.llInvite:
                Utils.shareContent(mContext, getString(R.string.app_invite_text));
                break;
            case R.id.logoutLL:
                Utils.showDialog(mContext, getString(R.string.want_to_logout), "", getString(R.string._yes),
                        (dialog, which) -> apiLogout(), null, getString(R.string._no), "", null, false);
                break;
            case R.id.ivUberReq:
                Const.onUberClick(mContext);
                binding.fragmentContainer.setVisibility(View.VISIBLE);
                break;
            case R.id.llGetHelp:
                finishAllFragment();
                Utils.goToFragment(mContext, new HelpFragment(), R.id.fragment_container);
                binding.drawerLayout.closeDrawers();
                binding.fragmentContainer.setVisibility(View.VISIBLE);
                break;
            case R.id.llHost:
                finishAllFragment();
                Utils.goToFragment(mContext, new HostHubFragment(""), R.id.fragment_container);
                binding.drawerLayout.closeDrawers();
                binding.fragmentContainer.setVisibility(View.VISIBLE);
                break;
            case R.id.llSettings:
                finishAllFragment();
                UserPOJO.Data user = Const.getLoggedInUser(mContext);
                user.setUserList(null);
                mLiveModel.getUserLiveData().setValue(user);
                Utils.goToFragment(mContext, new SettingsFragment(), R.id.fragment_container);
                binding.drawerLayout.closeDrawers();
                binding.fragmentContainer.setVisibility(View.VISIBLE);
                break;
        }
    }

    private void finishAllFragment() {
        FragmentManager fm = getSupportFragmentManager();
        for (int i = 0; i < fm.getBackStackEntryCount(); ++i) {
            fm.popBackStack();
        }
    }

    @SuppressLint("StaticFieldLeak")
    @Override
    public void onMapReady(GoogleMap googleMap) {
        try {
            mGoogleMap = googleMap;
            mGoogleMap.getUiSettings().setCompassEnabled(false);
            mGoogleMap.getUiSettings().setMapToolbarEnabled(false);
            googleMap.setMapStyle(MapStyleOptions.loadRawResourceStyle(this, R.raw.mapstyle));
        } catch (Exception e) {
            log("Can't find style. Error: " + e);
        }
        mGoogleMap.setOnMarkerClickListener(marker -> {
            if (mFriendsMarkers.contains(marker) || marker.equals(myMarker)) {
                return zoomOnMarkerClick(marker);
            } else {
                mClusterManager.onMarkerClick(marker);
                return true;
            }
        });
        mGoogleMap.setOnInfoWindowClickListener(marker -> {
            if (marker.equals(myMarker)) {
                FollowersFragment fragment = new FollowersFragment();
                fragment.setData(Const.getLoggedInUserID(mContext));
                Utils.goToFragment(mContext, fragment, R.id.fragment_container);
                binding.fragmentContainer.setVisibility(View.VISIBLE);
            }
            if (!marker.equals(myMarker) && mFriendsMarkers.contains(marker)) {
                FollowersFragment fragment = new FollowersFragment();
                fragment.setData(friendsList.get(mFriendsMarkers.indexOf(marker)).id);
                Utils.goToFragment(mContext, fragment, R.id.fragment_container);
                binding.fragmentContainer.setVisibility(View.VISIBLE);
            }
        });
        mGoogleMap.setInfoWindowAdapter(new GoogleMap.InfoWindowAdapter() {
            @Override
            public View getInfoWindow(Marker marker) {
                if (marker.equals(myMarker) || mFriendsMarkers.contains(marker)) {
                    MapBubbleBinding binding = DataBindingUtil.inflate(LayoutInflater.from(mContext), R.layout.map_bubble, null, false);
                    binding.tvName.setText(marker.getTitle());
                    binding.tvTime.setText(marker.getSnippet() != null ? marker.getSnippet() : "");
                    binding.tvTime.setVisibility(marker.getSnippet() != null && !marker.getSnippet().isEmpty() ? View.VISIBLE : View.GONE);
                    binding.view.setVisibility(marker.getSnippet() != null && !marker.getSnippet().isEmpty() ? View.VISIBLE : View.GONE);
                    return binding.getRoot();
                }
                return null;
            }

            @Override
            public View getInfoContents(Marker marker) {
                return null;
            }
        });
        mGoogleMap.setOnCameraIdleListener(() -> {
            try {
                myMarker.setIcon(Const.getSmallBitmapPin(mContext, myBitmap));
                for (int i = 0; i < mFriendsMarkers.size(); i++) {
                    mFriendsMarkers.get(i).setIcon(Const.getSmallBitmapPin(mContext, mFriendsBitmap.get(i)));
                }
                if (isIncSize && bigMarker != null) {
                    if (bigMarker.equals(myMarker))
                        myMarker.setIcon(Const.getBigBitmapPin(mContext, myBitmap));
                    else
                        mFriendsMarkers.get(mFriendsMarkers.indexOf(bigMarker)).setIcon(Const.getBigBitmapPin(mContext, mFriendsBitmap.get(mFriendsMarkers.indexOf(bigMarker))));
                }
                isIncSize = false;
                bigMarker = null;
            } catch (Exception e) {
                Utils.showLog("Exp on map idle : " + e.getMessage());
            }
            if (!isClickMarker) {
                selectedMarker = null;
            }
            isClickMarker = false;
            mClusterManager.onCameraIdle();
        });

        mClusterManager = new ClusterManager<>(this, mGoogleMap);
        final CustomClusterRenderer renderer = new CustomClusterRenderer(this, mGoogleMap, mClusterManager);
        mClusterManager.setRenderer(renderer);
        mClusterManager.setOnClusterClickListener(cluster -> {
            mGoogleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(
                    cluster.getPosition(), (float) Math.floor(mGoogleMap
                            .getCameraPosition().zoom + 4)));
            return true;
        });
        mClusterManager.setOnClusterItemClickListener(clusterItem -> {
            if (isVenue) {
                VenueDetailsFragment fragment = new VenueDetailsFragment();
                fragment.setData(clusterItem.getVenudetail(), true);
                Utils.goToFragment(mContext, fragment, R.id.fragment_container);
                binding.fragmentContainer.setVisibility(View.VISIBLE);
            } else if (clusterItem.getBashdetail().getCreatedFrom() == 1) {
                BashDetialFragment fragment = new BashDetialFragment();
                fragment.setData(clusterItem.getBashdetail(), true);
                Utils.goToFragment(mContext, fragment, R.id.fragment_container);
                binding.fragmentContainer.setVisibility(View.VISIBLE);
            } else {
                BashDetailsWebFragment fragment = new BashDetailsWebFragment();
                fragment.setData(clusterItem.getBashdetail(), true);
                Utils.goToFragment(mContext, fragment, R.id.fragment_container);
                binding.fragmentContainer.setVisibility(View.VISIBLE);
            }
            return false;
        });
        loadMyMarker();
    }

    //Old method without clustering
    /*private boolean zoomOnMarkerClick(Marker marker) {
        isClickMarker = true;
        isIncSize = false;
        bigMarker = null;
        int count = 0;
        if (selectedMarker == null) {
            LatLngBounds.Builder builder = new LatLngBounds.Builder();
            for (Marker m : mFriendsMarkers) {
                if (m != null) {
                    double distance = SphericalUtil.computeDistanceBetween(marker.getPosition(), m.getPosition());
                    if (distance < Const.ZOOM_CIRCLE) {
                        builder.include(m.getPosition());
                        count++;
                    }
                }
            }
            for (Marker m : mBashMarkers) {
                if (m != null) {
                    double distance = SphericalUtil.computeDistanceBetween(marker.getPosition(), m.getPosition());
                    if (distance < Const.ZOOM_CIRCLE) {
                        builder.include(m.getPosition());
                        count++;
                    }
                }
            }
            if (myMarker != null) {
                double distance = SphericalUtil.computeDistanceBetween(marker.getPosition(), myMarker.getPosition());
                if (distance < Const.ZOOM_CIRCLE) {
                    builder.include(myMarker.getPosition());
                    count++;
                }
            }
            if (count > 1) {
                new Handler().postDelayed(() -> mGoogleMap.animateCamera(CameraUpdateFactory.newLatLngBounds(builder.build(), 120)), 50);
            } else {
                new Handler().postDelayed(() -> {
                    if (!mBashMarkers.contains(marker)) {
                        isIncSize = true;
                        bigMarker = marker;
                    }
                    mGoogleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(marker.getPosition(), 15));
                }, 5);
            }
            selectedMarker = marker;
        } else {
            if (mBashMarkers.contains(marker)) {
                BashDetialFragment fragment = new BashDetialFragment();
                fragment.setData(bashList.get(mBashMarkers.indexOf(marker)), true);
                Utils.goToFragment(mContext, fragment, R.id.fragment_container);
                binding.fragmentContainer.setVisibility(View.VISIBLE);
            }
            if (!mBashMarkers.contains(marker)) {
                isIncSize = true;
                bigMarker = marker;
            }
            selectedMarker = null;
        }
        return mBashMarkers.contains(marker);
    }*/

    private boolean zoomOnMarkerClick(Marker marker) {
        isClickMarker = true;
        isIncSize = false;
        bigMarker = null;
        int count = 0;
        if (selectedMarker == null) {
            LatLngBounds.Builder builder = new LatLngBounds.Builder();
            for (Marker m : mFriendsMarkers) {
                if (m != null) {
                    double distance = SphericalUtil.computeDistanceBetween(marker.getPosition(), m.getPosition());
                    if (distance < Const.ZOOM_CIRCLE) {
                        builder.include(m.getPosition());
                        count++;
                    }
                }
            }
            if (myMarker != null) {
                double distance = SphericalUtil.computeDistanceBetween(marker.getPosition(), myMarker.getPosition());
                if (distance < Const.ZOOM_CIRCLE) {
                    builder.include(myMarker.getPosition());
                    count++;
                }
            }
            if (count > 1) {
                new Handler().postDelayed(() -> mGoogleMap.animateCamera(CameraUpdateFactory.newLatLngBounds(builder.build(), 120)), 50);
                selectedMarker = marker;
            } else {
                int finalCount = count;
                new Handler().postDelayed(() -> {
                    if (finalCount == 1) {
                        isIncSize = true;
                        bigMarker = marker;
                        mGoogleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(marker.getPosition(), 17));
                    }
                }, 5);
                if (finalCount == 1) selectedMarker = marker;
            }
        } else {
            isIncSize = true;
            bigMarker = marker;
            selectedMarker = null;
        }
        return false;
    }

    @Override
    protected void onResume() {
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_SECURE);
        super.onResume();
        stopLocationService();
        startLocationService();
        if (!isUploadFB) apiUpdateToken("");
        if (!isFirstTime) loadMyMarker();
        if (!isFirstTime) callBashApi();
        if (pongHandler != null) pongHandler.removeCallbacks(pongRunnable);
        apiFbFriends();
        Log.e("method_called", "onResume");
    }

    @Override
    protected void onPause() {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);
        super.onPause();
        if (pongHandler != null) pongHandler.removeCallbacks(pongRunnable);
        Log.e("method_called", "onPause");

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        stopLocationService();
        unRegisterEventBus();
        Log.e("method_called", "onDestroy");

    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.e("method_called", "onStop");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.e("method_called", "onStart");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.e("method_called", "onRestart");
    }

    public void callBashApi() {
        registerEventBus();
        if (!isVenue) {
            if (!mTinyDB.getString(Const.SAVED_LNG).isEmpty())
                apiGetBash(isFirstTime);
        } else {
            apiGetVenue(false);
        }
    }

    public void startLocationService() {
        getLocation();
        /*if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
            startForegroundService(new Intent(mContext, LocationService.class));
        else startService(new Intent(mContext, LocationService.class));*/
    }

    public void stopLocationService() {
        //stopService(new Intent(mContext, LocationService.class));
        if (mFusedLocationClient != null && mLocationCallback != null)
            mFusedLocationClient.removeLocationUpdates(mLocationCallback);
    }

    //Commented code was used before clustering
    private void setMarkers(boolean isLoaded) {
        /*for (Marker marker : mBashMarkers)
            marker.remove();
        mBashMarkers.clear();*/

        mClusterManager.clearItems();
        mClusterManager.cluster();
        clusterItems.clear();
        for (BashDetailsPOJO item : bashList) {
            LatLng loc = Utils.getLatLng(item.lat, item.lng);
            /*Marker m = mGoogleMap.addMarker(new MarkerOptions().icon(Const.getBashPin(mContext, item.bash_type)).position(loc));
            mBashMarkers.add(m);*/
            clusterItems.add(new MyClusterItem(loc.latitude, loc.longitude, Const.getBashPin(mContext, item.bash_type, item.category), item));
        }
        mClusterManager.addItems(clusterItems);
        mClusterManager.cluster();

        if (!isLoaded)
            moveCameraToMyLocation();
    }

    private void setvenueMarkers(boolean isLoaded) {
        /*for (Marker marker : mBashMarkers)
            marker.remove();
        mBashMarkers.clear();*/

        mClusterManager.clearItems();
        mClusterManager.cluster();
        clusterItems.clear();
        for (VenueListPOJO.Venue item : venueList) {
            if (item.getLat() != null) {
                LatLng loc = Utils.getLatLng(item.getLat(), item.getLng());
            /*Marker m = mGoogleMap.addMarker(new MarkerOptions().icon(Const.getBashPin(mContext, item.bash_type)).position(loc));
            mBashMarkers.add(m);*/
                if (item.getBusinessType() != null)
                    loadVenueMarkerFromUrl(Const.IMAGE_BASE_EVENT + item.getBusinessType().getImage(), loc, item);
            }
        }


        if (!isLoaded)
            moveCameraToMyLocation();
    }

    private void loadMyMarker() {
        if (!mTinyDB.getString(Const.SAVED_LAT).isEmpty()) {
            loadMarkerFromUrl(Const.IMAGE_BASE + Const.getLoggedInUser(mContext).standing_img, Double.parseDouble(mTinyDB.getString(Const.SAVED_LAT)),
                    Double.parseDouble(mTinyDB.getString(Const.SAVED_LNG)), true, getString(R.string.your_current_location), "", -1);
        }
    }

    private void moveCameraToMyLocation() {
        if (!mTinyDB.getString(Const.SAVED_LAT).isEmpty()) {
            LatLng loc = new LatLng(Double.parseDouble(mTinyDB.getString(Const.SAVED_LAT)), Double.parseDouble(mTinyDB.getString(Const.SAVED_LNG)));
            mGoogleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(loc, 10));
        }
    }

    private void loadFriendsOnMap() {
        for (int i = 0; i < friendsList.size(); i++) {
            FbFriendsPOJO.Data item = friendsList.get(i);
            loadMarkerFromUrl(Const.IMAGE_BASE + item.standing_img, item.getLat(), item.getLng(), false, item.getFullName(), item.getLast_update_text(), i);
        }
    }

    @SuppressLint({"StaticFieldLeak", "CheckResult"})
    private void loadMarkerFromUrl(String url, double latitude, double longitude, boolean isMyLoc, String title, String time, int position) {
        Glide.with(mContext).asBitmap().load(url).addListener(new RequestListener<Bitmap>() {
            @Override
            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Bitmap> target, boolean isFirstResource) {
                Utils.showLog("Failed");
                return false;
            }

            @Override
            public boolean onResourceReady(Bitmap bitmap, Object model, Target<Bitmap> target, DataSource dataSource, boolean isFirstResource) {
                try {
                    if (bitmap != null && mGoogleMap != null) {
                        if (isMyLoc) {
                            if (myMarker != null) {
                                myMarker.setPosition(new LatLng(latitude, longitude));
                                myMarker.setIcon(myMarker.equals(bigMarker) ? Const.getBigBitmapPin(mContext, bitmap) : Const.getSmallBitmapPin(mContext, bitmap));
                            } else {
                                myMarker = mGoogleMap.addMarker(new MarkerOptions()
                                        .icon(Const.getSmallBitmapPin(mContext, bitmap))
                                        .title(title).position(new LatLng(latitude, longitude)));
                            }
                            myBitmap = bitmap;
                        } else {
                            Marker marker;
                            if (mFriendsMarkers.get(position) != null) {
                                marker = mFriendsMarkers.get(position);
                                marker.setPosition(new LatLng(latitude, longitude));
                                marker.setSnippet(time);
                                marker.setIcon(marker.equals(bigMarker) ? Const.getBigBitmapPin(mContext, bitmap) : Const.getSmallBitmapPin(mContext, bitmap));
                                if (marker.isInfoWindowShown()) {
                                    marker.hideInfoWindow();
                                    marker.showInfoWindow();
                                }
                            } else {
                                marker = mGoogleMap.addMarker(new MarkerOptions().icon(Const.getSmallBitmapPin(mContext, bitmap))
                                        .title(title).snippet(time).position(new LatLng(latitude, longitude)));
                            }
                            mFriendsMarkers.set(position, marker);
                            mFriendsBitmap.set(position, bitmap);
                        }
                    }
                } catch (Exception e) {
                    Utils.showLog("Exp in loading bimoji : " + e.getMessage());
                }
                return false;
            }
        }).submit();
    }

    @SuppressLint({"StaticFieldLeak", "CheckResult"})
    private void loadVenueMarkerFromUrl(String url, LatLng loc, VenueListPOJO.Venue item) {
        Glide.with(mContext).asBitmap().load(url).addListener(new RequestListener<Bitmap>() {
            @Override
            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Bitmap> target, boolean isFirstResource) {
                Utils.showLog("Failed");
                return false;
            }

            @Override
            public boolean onResourceReady(Bitmap bitmap, Object model, Target<Bitmap> target, DataSource dataSource, boolean isFirstResource) {
                try {
                    if (bitmap != null) {
                        clusterItems.add(new MyClusterItem(loc.latitude, loc.longitude, BitmapDescriptorFactory.fromBitmap(Bitmap.createScaledBitmap(bitmap,
                                (int) (mContext.getResources().getDimension(R.dimen._25sdp)), (int) (mContext.getResources().getDimension(R.dimen._25sdp)), false)), item));
                        mClusterManager.addItems(clusterItems);
                        mClusterManager.cluster();
                    }
                } catch (Exception e) {
                    Utils.showLog("Exp in loading bimoji : " + e.getMessage());
                }
                return false;
            }
        }).submit();
    }

    private void apiLogout() {
        EventBus.getDefault().post(new Events.RequestLogout(mContext, Const.apiLogout(mContext), true, false));
    }

    public void getFbImage() {
        GraphRequest request = GraphRequest.newMeRequest(AccessToken.getCurrentAccessToken(), (object, response) -> {
            if (object != null) {
                String socialImage = "";
                try {
                    socialImage = object.getJSONObject("picture").getJSONObject("data").getString("url");
                } catch (Exception e) {
                    e.printStackTrace();
                }
                apiUpdateToken(Const.isUploadFB(mContext) ? socialImage : "");
            } else {
                apiUpdateToken("");
            }
        });
        Bundle parameters = new Bundle();
        parameters.putString("fields", "id,name,email,gender,birthday,first_name,last_name,picture.type(normal)");
        request.setParameters(parameters);
        request.executeAsync();
    }

    public void apiUpdateToken(String fbImage) {
        FirebaseInstanceId.getInstance().getInstanceId().addOnSuccessListener(this, instanceIdResult -> {
            Utils.showLog("Token : " + instanceIdResult.getToken());
            EventBus.getDefault().post(new Events.RequestUpdateToken(mContext, Const.apiUpdateToken(mContext,
                    instanceIdResult.getToken(), fbImage), false, false));
        });
    }

    public void apiGetBash(boolean isDialog) {
        isFirstTime = false;
        EventBus.getDefault().post(new Events.RequestBashList(mContext, Const.apiGetBash(mContext,
                Utils.isDateEquals(Calendar.getInstance().getTime(), homeCalendar.getTime()) ? "" : Utils.getFullDate(homeCalendar.getTime()),
                searchText), isDialog, false));
        searchText = "";
    }

    public static void apiGetVenue(boolean isDialog) {
        isFirstTime = false;
        EventBus.getDefault().post(new Events.RequestVenueList(mContext, Const.apiGetVenue(mContext,
                Utils.isDateEquals(Calendar.getInstance().getTime(), homeCalendar.getTime()) ? "" : Utils.getFullDate(homeCalendar.getTime()),
                searchText, "1"), isDialog, false));
        searchText = "";
    } public void apiGetMyVenue(boolean isDialog) {
        isFirstTime = false;
        EventBus.getDefault().post(new Events.RequestVenueList(mContext, Const.apiGetVenue(mContext,
               "","", "2"), false, false));
        searchText = "";
    }

    public void apiFbFriends() {
        if (pongHandler != null) pongHandler.removeCallbacks(pongRunnable);
        EventBus.getDefault().post(new Events.RequestFbFriends(mContext, Const.apiFbFriendsContacts(mContext), false, false));
    }


    private void apiSendInvitations() {
        if (!mTinyDB.getString(Const.INVITATION_BASH_ID).isEmpty())
            apiSingleBash(mTinyDB.getString(Const.INVITATION_BASH_ID));

        String userID = mTinyDB.getString(Const.INVITATION_USER);
        if (!userID.isEmpty() && !userID.equals(Const.getLoggedInUserID(mContext)))
            EventBus.getDefault().post(new Events.RequestBasic(mContext, Const.apiSendInvitations(mContext, Const.getLoggedInUserID(mContext),
                    mTinyDB.getString(Const.INVITATION_BASH_ID)), false, false));
    }

    public void apiFbFriendsLoc() {
        pongHandler.postDelayed(pongRunnable, Const.PONG_API_INTERVAL);
        String session_ids = "";
        for (FbFriendsPOJO.Data item : friendsList)
            session_ids = session_ids.isEmpty() ? item.session_id : session_ids + "," + item.session_id;
        EventBus.getDefault().post(new Events.RequestFbFriendsLoc(mContext, Const.apiFbFriendsLoc(mContext, session_ids), false, false));
    }

    public void apiCheckInList() {
        EventBus.getDefault().post(new Events.RequestCheckIn(mContext, Const.apiCheckInList(mContext), true, false));
    }

    public void apiSingleBash(String bash_id) {
        EventBus.getDefault().post(new Events.RequestSingleBash(mContext, Const.apiSingleBash(mContext, bash_id), false, false));
    }

    public void apiSingleVenue(String venue_id) {
        EventBus.getDefault().post(new Events.RequestSingleVenue(mContext, Const.apiSingleVenu(mContext, venue_id), false, false));
    }

    @Subscribe
    public void apiLogoutRes(Events.GetLogoutData res) {
        String lat = mTinyDB.getString(Const.SAVED_LAT);
        String lng = mTinyDB.getString(Const.SAVED_LNG);
        mTinyDB.clear();
        mTinyDB.putString(Const.SAVED_LAT, lat);
        mTinyDB.putString(Const.SAVED_LNG, lng);
        startNewActivity(SplashActivity.class, true, true, null);
    }

    @Subscribe
    public void apiUpdateTokenRes(Events.GetUpdateTokenData res) {
        isUploadFB = false;
        Const.setLoggedInUser(mContext, res.getData().data);
        mLiveModel.getUserLiveData().setValue(Const.getLoggedInUser(mContext));
        Utils.showLog("Token update");

        List<BashDetailsPOJO> rateList = res.getData().data.pendingRating;
        if (rateList.size() > 0) {
            RatingFragment fragment = new RatingFragment();
            fragment.setData(rateList.get(0));
            Utils.goToFragment(mContext, fragment, R.id.fragment_container);
            binding.fragmentContainer.setVisibility(View.VISIBLE);
        }
        List<BashDetailsPOJO> list = res.getData().data.free_notify;
        if (list.size() > 0) {
            DialogCheckInConfirmation bottomSheetFragment = new DialogCheckInConfirmation(list.get(0));
            bottomSheetFragment.show(getSupportFragmentManager(), bottomSheetFragment.getTag());
        }
    }

    @Subscribe
    public void apiGetBashRes(Events.GetBashListData res) {
        boolean isLoaded = bashList.size() > 0;
        bashList.clear();
        bashList.addAll(res.getData().data);
//        binding.tvBashCount.setText(res.getData().data.get(0).getba+"");

        setMarkers(isLoaded);
        /*if (isFirstTime && bashList.size() <= 0)
            Utils.showToast(mContext, getString(R.string.no_event_found));*/
    }

    @Subscribe
    public void apiGetVenueRes(Events.GetVenuListData res) {
        boolean isLoaded = venueList.size() > 0;
        venueList.clear();
        venueList.addAll(res.getData().getData().getVenues());
        setvenueMarkers(isLoaded);
//        binding.tvBashCount.setText(venueList.size()+"");
        /*if (isFirstTime && bashList.size() <= 0)
            Utils.showToast(mContext, getString(R.string.no_event_found));*/
    }

    @SuppressWarnings("unused")
    @Subscribe
    public void apiFbFriendsRes(Events.GetFbFriendsData res) {
        friendsList.clear();
        for (Marker marker : mFriendsMarkers)
            if (marker != null) marker.remove();
        mFriendsMarkers.clear();
        mFriendsBitmap.clear();
        friendsList.addAll(res.getData().data);
        if (friendsList.size() > 0) {
            for (FbFriendsPOJO.Data item : friendsList) {
                mFriendsMarkers.add(null);
                mFriendsBitmap.add(null);
            }
            loadFriendsOnMap();
            pongHandler.postDelayed(pongRunnable, Const.PONG_API_INTERVAL);
        }
    }

    @Subscribe
    public void apiFbFriendsLocRes(Events.GetFbFriendsLocData res) {
        for (FbFriendsLocPOJO.Data item : res.getData().data) {
            FbFriendsPOJO.Data f = new FbFriendsPOJO.Data();
            f.setSession_id(item.session_id);
            int index = friendsList.indexOf(f);
            if (index != -1) {
                friendsList.get(index).setLat(item.lat);
                friendsList.get(index).setLng(item.lng);
                friendsList.get(index).setLast_update_text(item.last_location);
            }
        }
        loadMyMarker();
        loadFriendsOnMap();
    }

    @Subscribe
    public void apiCheckInListRes(Events.GetCheckInData res) {
        DialogBashCheckIn bashCheckInDialog = new DialogBashCheckIn(res.getData().data);
        bashCheckInDialog.show(getSupportFragmentManager(), bashCheckInDialog.getTag());
    }

    @Subscribe
    public void apiSingleBashRes(Events.GetSingleBashData res) {
        if (res.getData().data.id != null) {
            if (res.getData().data.getCreatedFrom() == 1) {
                BashDetialFragment fragment = new BashDetialFragment();
                fragment.setData(res.getData().data, true);
                Utils.goToFragment(mContext, fragment, R.id.fragment_container);
                binding.fragmentContainer.setVisibility(View.VISIBLE);
            } else {
                BashDetailsWebFragment fragment = new BashDetailsWebFragment();
                fragment.setData(res.getData().data, true);
                Utils.goToFragment(mContext, fragment, R.id.fragment_container);
                binding.fragmentContainer.setVisibility(View.VISIBLE);
            }
        } else {
            Utils.showToast(mContext, res.getData().mesg);
        }
    }

    @Subscribe
    public void apiSingleVenueRes(Events.GetSingleVenueData res) {
        if (res.getData().getData().getVenue().getId() != null) {
            VenueDetailsFragment fragment = new VenueDetailsFragment();
            fragment.setData(res.getData().getData().getVenue(), true);
            Utils.goToFragment(mContext, fragment, R.id.fragment_container);
            binding.fragmentContainer.setVisibility(View.VISIBLE);
        } else {
            Utils.showToast(mContext, res.getData().getMesg());
        }
    }

    @Subscribe
    public void getLocation(Events.LocationPickerResult res) {
        mTinyDB.putString(Const.SAVED_LAT, "" + res.getLatitude());
        mTinyDB.putString(Const.SAVED_LNG, "" + res.getLongitude());
        if (!isVenue)
            apiGetBash(isFirstTime);
        else apiGetVenue(false);
        loadMyMarker();
    }

    @Override
    public void onBitmojiSelected(String s, Drawable drawable) {
        EventBus.getDefault().post(new Events.GetEmojiData(s));
    }

    @Override
    public void onError(Exception error) {
        Utils.showToast(mContext, "Braintree error : " + error.getMessage());
    }

    @Override
    public void onPaymentMethodNonceCreated(PaymentMethodNonce paymentMethodNonce) {
        String nonce = paymentMethodNonce.getNonce();
        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.fragment_container);
        if (fragment instanceof BashDetialFragment)
            ((BashDetialFragment) fragment).onNonceCreated(nonce);
        if (fragment instanceof ConfirmOrderFragment)
            ((ConfirmOrderFragment) fragment).onNonceCreated(nonce);
    }

    //Location updating
    @SuppressLint("MissingPermission")
    private void getLocation() {
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        LocationRequest mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(Const.LOC_UPDATE_INTERVAL);
        mLocationRequest.setFastestInterval(Const.LOC_UPDATE_INTERVAL);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        mFusedLocationClient.requestLocationUpdates(mLocationRequest, mLocationCallback, null);
    }

    public void apiUpdateLocation(String lat, String lng) {
        new TinyDB(this).putString(Const.SAVED_LAT, lat);
        new TinyDB(this).putString(Const.SAVED_LNG, lng);
        if (Const.isLoggedIn(this))
            EventBus.getDefault().post(new Events.RequestUpdateLocation(this, Const.apiUpdateLocation(this, lat, lng), false, false));
    }
}
