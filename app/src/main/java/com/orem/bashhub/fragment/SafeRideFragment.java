package com.orem.bashhub.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.DividerItemDecoration;

import com.facebook.CallbackManager;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.widget.Autocomplete;
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode;
import com.orem.bashhub.R;
import com.orem.bashhub.adapter.SafeFriendsAdapter;
import com.orem.bashhub.data.SearchUserPOJO;
import com.orem.bashhub.data.UserPOJO;
import com.orem.bashhub.databinding.FragmentSafeRideBinding;
import com.orem.bashhub.utils.Const;
import com.orem.bashhub.utils.Utils;
import com.orem.bashhub.utils.apiinterface.Events;
import com.uber.sdk.android.core.auth.AccessTokenManager;
import com.uber.sdk.android.core.auth.AuthenticationError;
import com.uber.sdk.android.core.auth.LoginCallback;
import com.uber.sdk.android.core.auth.LoginManager;
import com.uber.sdk.core.auth.AccessToken;
import com.uber.sdk.core.auth.Scope;
import com.uber.sdk.core.client.SessionConfiguration;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import static android.app.Activity.RESULT_OK;
import static com.facebook.FacebookSdk.getApplicationContext;

public class SafeRideFragment extends BaseFragment {

    private FragmentSafeRideBinding binding;
    private CallbackManager callbackManager;
    private String address = "", latitude = "", longitude = "";
    private boolean isSelected = false;
    private LoginManager loginManager;
    private SessionConfiguration config = new SessionConfiguration.Builder()
            .setClientId(Const.UBER_CLIENT_ID)
            .setRedirectUri(Const.UBER_REDIRECT_URL)
            .setEnvironment(SessionConfiguration.Environment.SANDBOX)
            .setScopes(Arrays.asList(Scope.PROFILE, Scope.REQUEST_RECEIPT, Scope.REQUEST))
            .build();
    private List<SearchUserPOJO.Data> list = new ArrayList<>();
    private List<SearchUserPOJO.Data> selectedUser = new ArrayList<>();
    private SafeFriendsAdapter friendsAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_safe_ride, container, false);
        Places.initialize(getApplicationContext(), getString(R.string.google_maps_key));
        callbackManager = CallbackManager.Factory.create();
        uberIni();
        init();
        setData();
        return binding.getRoot();
    }

    private void init() {
        binding.backIV.setOnClickListener(this);
        binding.tvLocation.setOnClickListener(this);
        binding.btSave.setOnClickListener(this);
        binding.llConnect.setOnClickListener(this);
        binding.ivInfo.setOnClickListener(this);
        binding.llConnect.setVisibility(loginManager.isAuthenticated() ? View.GONE : View.VISIBLE);
        binding.llConnected.setVisibility(loginManager.isAuthenticated() ? View.VISIBLE : View.GONE);
        binding.rvFriends.addItemDecoration(new DividerItemDecoration(mContext, DividerItemDecoration.VERTICAL));
        friendsAdapter = new SafeFriendsAdapter(mContext, selectedUser);
        binding.rvFriends.setAdapter(friendsAdapter);

        binding.etName.setDropDownVerticalOffset(15);
        binding.etName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                apiSearchUser(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        binding.etName.setOnItemClickListener((parent, view, position, id) -> {
            isSelected = true;
            if (selectedUser.size() >= 5) {
                Utils.showToast(mContext, getString(R.string.max_5_friends));
                binding.etName.setText("");
                return;
            }
            if (selectedUser.indexOf(list.get(position)) == -1) {
                selectedUser.add(list.get(position));
                friendsAdapter.notifyDataSetChanged();
            } else {
                Utils.showToast(mContext, getString(R.string.user_already_added));
            }
            binding.etName.setText("");
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.backIV:
                Objects.requireNonNull(getActivity()).onBackPressed();
                break;
            case R.id.tvLocation:
                locationIntent();
                break;
            case R.id.ivInfo:
                Utils.showMessageDialog(mContext, "", getString(R.string.safe_ride_info));
                break;
            case R.id.btSave:
                if (address.isEmpty())
                    Utils.showToast(mContext, getString(R.string.select_drop_location));
                else apiSaveData();
                break;
            case R.id.llConnect:
                loginManager.login(Objects.requireNonNull(getActivity()));
                break;
        }
    }

    private void setData() {
        UserPOJO.Data user = Const.getLoggedInUser(mContext);
        if (user.safe_ride.id != null) {
            address = user.safe_ride.location;
            latitude = user.safe_ride.lat;
            longitude = user.safe_ride.lng;
            binding.tvLocation.setText(address);
            binding.checkBox.setChecked(user.safe_ride.notify.equals(Const.ONE));
            if (user.safe_ride.users.size() > 0) {
                selectedUser.clear();
                selectedUser.addAll(user.safe_ride.users);
                friendsAdapter.notifyDataSetChanged();
            }
        }
    }

    private void locationIntent() {
        List<Place.Field> fields = Arrays.asList(Place.Field.ID, Place.Field.NAME, Place.Field.ADDRESS, Place.Field.LAT_LNG);
        Intent intent = new Autocomplete.IntentBuilder(AutocompleteActivityMode.FULLSCREEN, fields).build(mContext);
        startActivityForResult(intent, 100);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
        loginManager.onActivityResult(Objects.requireNonNull(getActivity()), requestCode, resultCode, data);
        if (requestCode == 100) {
            if (resultCode == RESULT_OK) {
                Place place = Autocomplete.getPlaceFromIntent(data);
                address = place.getAddress();
                latitude = "" + Objects.requireNonNull(place.getLatLng()).latitude;
                longitude = "" + place.getLatLng().longitude;
                binding.tvLocation.setText(place.getAddress());
            }
        }
    }

    private void uberIni() {
        LoginCallback loginCallback = new LoginCallback() {
            @Override
            public void onLoginCancel() {
                Utils.showLog("Login cancel");
            }

            @Override
            public void onLoginError(@NonNull AuthenticationError error) {
                Utils.showToast(mContext, getString(R.string.uber_login_error) + " : " + error.toStandardString());
            }

            @Override
            public void onLoginSuccess(@NonNull AccessToken accessToken) {
                binding.llConnect.setVisibility(View.GONE);
                binding.llConnected.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAuthorizationCodeReceived(@NonNull String authorizationCode) {
                Utils.showLog("Auth code : " + authorizationCode);
            }
        };
        AccessTokenManager accessTokenStorage = new AccessTokenManager(mContext);
        loginManager = new LoginManager(accessTokenStorage, loginCallback, config, 10);
    }

    private String getUsers() {
        String users = "";
        for (SearchUserPOJO.Data item : selectedUser)
            users = users.isEmpty() ? item.id : users + "," + item.id;
        return users;
    }

    private void apiSearchUser(String text) {
        registerEventBus();
        EventBus.getDefault().post(new Events.RequestSearchUser(mContext, Const.apiSearchUser(mContext, text), false, false));
    }

    private void apiSaveData() {
        registerEventBus();
        EventBus.getDefault().post(new Events.RequestUser(mContext, Const.apiSaveSafeRide(mContext,
                getUsers(), address, latitude, longitude, binding.checkBox.isChecked() ? Const.ONE : Const.ZERO), true, false));
    }

    @Subscribe
    public void apiSearchUserRes(Events.GetSearchUserData res) {
        list.clear();
        list.addAll(res.getData().data);
        ArrayAdapter<SearchUserPOJO.Data> adapter = new ArrayAdapter<>(mContext, R.layout.spinner_text_autocomplete, list);
        adapter.setDropDownViewResource(R.layout.spinner_text_autocomplete);
        binding.etName.setAdapter(adapter);
        if (!isSelected)
            binding.etName.showDropDown();
        else binding.etName.dismissDropDown();
        if (list.size() <= 0) binding.etName.dismissDropDown();
        isSelected = false;
    }

    @Subscribe
    public void spiSaveDataRes(Events.GetUserData res) {
        unRegisterEventBus();
        Const.setLoggedInUser(mContext, res.getData().data);
        Utils.showToast(mContext, getString(R.string.safe_ride_data_save_success));
    }
}
