package com.orem.bashhub.fragment;


import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.MarkerOptions;
import com.orem.bashhub.R;
import com.orem.bashhub.adapter.UberProductsAdapter;
import com.orem.bashhub.data.UberEstimatePOJO;
import com.orem.bashhub.data.UberRequestPOJO;
import com.orem.bashhub.data.UserPOJO;
import com.orem.bashhub.databinding.FragmentUberRequestBinding;
import com.orem.bashhub.utils.Const;
import com.orem.bashhub.utils.Utils;
import com.orem.bashhub.utils.apiinterface.Events;
import com.uber.sdk.android.core.auth.AccessTokenManager;
import com.uber.sdk.android.core.auth.LoginManager;
import com.uber.sdk.core.auth.Scope;
import com.uber.sdk.core.client.SessionConfiguration;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 */
public class UberRequestFragment extends BaseFragment implements OnMapReadyCallback {

    private FragmentUberRequestBinding binding;
    private GoogleMap mGoogleMap;
    private LoginManager loginManager;
    private SessionConfiguration config = new SessionConfiguration.Builder()
            .setClientId(Const.UBER_CLIENT_ID)
            .setRedirectUri(Const.UBER_REDIRECT_URL)
            .setEnvironment(SessionConfiguration.Environment.SANDBOX)
            .setScopes(Arrays.asList(Scope.PROFILE, Scope.REQUEST_RECEIPT, Scope.REQUEST))
            .build();
    private LatLng source, destination;
    private String token;
    private UberProductsAdapter productsAdapter;
    private UberEstimatePOJO estimateData;
    private UberRequestPOJO requestData;
    private boolean isRefresh = false, isNotify = false;
    private Handler reqHandler = new Handler();
    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            if (reqHandler != null) reqHandler.removeCallbacks(runnable);
            if (isRefresh) {
                apiCurrentRequest();
                startHandler();
            }
        }
    };

    public UberRequestFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_uber_request, container, false);
        ini();
        return binding.getRoot();
    }

    private void ini() {
        UserPOJO.Data user = Const.getLoggedInUser(mContext);
        SupportMapFragment map = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        Objects.requireNonNull(map).getMapAsync(this);
        binding.ivBack.setOnClickListener(v -> Objects.requireNonNull(getActivity()).onBackPressed());
        binding.btRequest.setOnClickListener(v -> {
            if (productsAdapter.getProduct() != null)
                apiMakeRequest(productsAdapter.getProduct().product_id);
            else Utils.showToast(mContext, getString(R.string.please_select_vehicle));
        });
        if (!mTinyDb.getString(Const.SAVED_LAT).isEmpty()) {
            source = new LatLng(Double.parseDouble(mTinyDb.getString(Const.SAVED_LAT)), Double.parseDouble(mTinyDb.getString(Const.SAVED_LNG)));
            destination = new LatLng(Double.parseDouble(user.safe_ride.lat), Double.parseDouble(user.safe_ride.lng));
            new Handler().post(() -> binding.tvFrom.setText(getAddressFromLatLng(source.latitude, source.longitude)));
            binding.tvTo.setText(user.safe_ride.location);
            getToken();
        } else {
            Utils.showToast(mContext, getString(R.string.no_source_address));
        }
    }

    private String getAddressFromLatLng(double lat, double lng) {
        String add;
        try {
            Geocoder geocoder = new Geocoder(mContext, Locale.getDefault());
            List<Address> addresses = geocoder.getFromLocation(lat, lng, 1);
            add = addresses.get(0).getAddressLine(0);
        } catch (Exception e) {
            add = getString(R.string.no_address_found);
        }
        return add;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        cancelHandler();
    }

    private void startHandler() {
        reqHandler.postDelayed(runnable, 10000);
    }

    private void cancelHandler() {
        if (reqHandler != null) reqHandler.removeCallbacks(runnable);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        try {
            mGoogleMap = googleMap;
            mGoogleMap.getUiSettings().setCompassEnabled(false);
            googleMap.setMapStyle(MapStyleOptions.loadRawResourceStyle(getContext(), R.raw.mapstyle));
            LatLngBounds.Builder builder = new LatLngBounds.Builder();
            builder.include(source);
            builder.include(destination);
            mGoogleMap.addMarker(new MarkerOptions().icon(Const.getUberPin(mContext, true))
                    .title(getString(R.string.source)).position(source));
            mGoogleMap.addMarker(new MarkerOptions().icon(Const.getUberPin(mContext, false))
                    .title(getString(R.string.destination)).position(destination));
            new Handler().postDelayed(() -> mGoogleMap.animateCamera(CameraUpdateFactory.newLatLngBounds(builder.build(), 300, 350, 1)), 3000);
        } catch (Exception e) {
            Utils.showLog("Can't find style. Error: " + e);
        }
    }

    private void getToken() {
        AccessTokenManager accessTokenStorage = new AccessTokenManager(mContext);
        loginManager = new LoginManager(accessTokenStorage, null, config, 10);
        if (loginManager.isAuthenticated()) {
            token = Objects.requireNonNull(loginManager.getAccessTokenStorage().getAccessToken()).getToken();
            apiCheckRequest();
        } else {
            Utils.showToast(mContext, getString(R.string.token_not_found));
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        loginManager.onActivityResult(Objects.requireNonNull(getActivity()), requestCode, resultCode, data);
    }

    private void apiGetProducts() {
        registerEventBus();
        EventBus.getDefault().post(new Events.RequestUberProducts(mContext, Const.apiUberProducts(mContext, token,
                "" + source.latitude, "" + source.longitude), true, false));
    }

    private void apiGetEstimates() {
        registerEventBus();
        EventBus.getDefault().post(new Events.RequestUberEstimates(mContext, Const.apiUberEstimates(mContext, token,
                "" + source.latitude, "" + source.longitude,
                "" + destination.latitude, "" + destination.longitude), true, false));
    }

    private void apiMakeRequest(String product_id) {
        registerEventBus();
        EventBus.getDefault().post(new Events.RequestUberRequest(mContext, Const.apiUberMakeRequest(mContext, token,
                "" + source.latitude, "" + source.longitude, "" + destination.latitude,
                "" + destination.longitude, product_id, estimateData.fare.fare_id), true, false));
    }

    private void apiCheckRequest() {
        registerEventBus();
        EventBus.getDefault().post(new Events.RequestUberRequest(mContext, Const.apiUberCheckRequest(mContext, token), true, false));
    }

    private void apiCurrentRequest() {
        registerEventBus();
        EventBus.getDefault().post(new Events.RequestUberRequest(mContext, Const.apiUberCurrentRequest(mContext, token, requestData.request_id), false, false));
    }

    private void apiGetReceipt() {
        registerEventBus();
        EventBus.getDefault().post(new Events.RequestUberReceipt(mContext, Const.apiUberReceipt(mContext, token, requestData.request_id), true, false));
    }

    private void apiNotifyUser() {
        registerEventBus();
        isNotify = true;
        EventBus.getDefault().post(new Events.RequestBasic(mContext, Const.apiNotifyUser(mContext,
                requestData.getNotifyMessage(mContext), requestData.request_id), false, false));
    }

    @Subscribe
    public void apiGetProductsRes(Events.GetUberProductsData res) {
        unRegisterEventBus();
        productsAdapter = new UberProductsAdapter(mContext, res.getData().products);
        binding.rvProducts.setAdapter(productsAdapter);
        binding.tvSelectVehicle.setVisibility(View.VISIBLE);
        binding.rvProducts.setVisibility(View.VISIBLE);
        binding.llDetails.setVisibility(View.VISIBLE);
        binding.btRequest.setVisibility(View.VISIBLE);
    }

    @Subscribe
    public void apiGetEstimatesRes(Events.GetUberEstimatesData res) {
        unRegisterEventBus();
        estimateData = res.getData();
        binding.tvEstimate.setText(estimateData.getEstimateText(mContext));
        binding.view1.setVisibility(View.VISIBLE);
        apiGetProducts();
    }

    @Subscribe
    public void apiRequestRes(Events.GetUberRequestData res) {
        unRegisterEventBus();
        requestData = res.getData();
        if (requestData.status.equals(Const.UBER_PROCESSING)) {
            binding.tvTitle.setText(getString(R.string.req_processing));
            binding.btRequest.setVisibility(View.GONE);
            binding.llDetails.setVisibility(View.GONE);
            binding.tvLookDriver.setVisibility(View.VISIBLE);
            if (!isRefresh) startHandler();
            isRefresh = true;
        } else if (requestData.status.equals(Const.UBER_ACCEPTED)) {
            binding.tvTitle.setText(getString(R.string.req_accepted));
            binding.setData(requestData);
            binding.tvLookDriver.setVisibility(View.GONE);
            binding.llDriver.setVisibility(View.VISIBLE);
            if (!isNotify && Const.getLoggedInUser(mContext).safe_ride.notify.equals(Const.ONE))
                apiNotifyUser();
            if (!isRefresh) startHandler();
            isRefresh = true;
        } else if (requestData.status.equals(Const.UBER_ARRIVED)) {
            binding.tvTitle.setText(getString(R.string.req_arrived));
            if (!isRefresh) startHandler();
            isRefresh = true;
        } else if (requestData.status.equals(Const.UBER_ONGOING)) {
            binding.tvTitle.setText(getString(R.string.req_ongoing));
            if (!isRefresh) startHandler();
            isRefresh = true;
        } else if (requestData.status.equals(Const.UBER_COMPLETE)) {
            binding.tvTitle.setText(getString(R.string.req_complete));
            isRefresh = false;
            cancelHandler();
            apiGetReceipt();
        } else if (requestData.status.equals(Const.UBER_NO_DRIVER)) {
            binding.tvTitle.setText(getString(R.string.req_no_driver));
            isRefresh = false;
            cancelHandler();
            Utils.showMessageDialog(mContext, "", getString(R.string.ride_no_driver),
                    (dialog, which) -> binding.ivBack.performClick());
        } else if (requestData.status.equals(Const.UBER_DRIVER_CANCELLED)) {
            binding.tvTitle.setText(getString(R.string.req_driver_cancel));
            isRefresh = false;
            cancelHandler();
            Utils.showMessageDialog(mContext, "", getString(R.string.ride_driver_cancelled),
                    (dialog, which) -> binding.ivBack.performClick());
        } else if (requestData.status.equals(Const.UBER_RIDER_CANCELLED)) {
            binding.tvTitle.setText(getString(R.string.req_rider_cancel));
            isRefresh = false;
            cancelHandler();
            Utils.showMessageDialog(mContext, "", getString(R.string.ride_rider_cancelled),
                    (dialog, which) -> binding.ivBack.performClick());
        } else {
            isRefresh = false;
            cancelHandler();
            Utils.showMessageDialog(mContext, "", getString(R.string.something_went_wrong),
                    (dialog, which) -> binding.ivBack.performClick());
        }
    }

    @Subscribe
    public void apiGetReceiptRes(Events.GetUberReceiptData res) {
        unRegisterEventBus();
        binding.setReceipt(res.getData());
        binding.view2.setVisibility(View.VISIBLE);
        binding.tvReceipt.setVisibility(View.VISIBLE);
    }

    @Subscribe
    public void apiCheckRequestRes(Events.GetUberError res) {
        unRegisterEventBus();
        apiGetEstimates();
    }
}
