package com.orem.bashhub.fragment;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import com.orem.bashhub.R;
import com.orem.bashhub.adapter.CustomSpinnerAdapter;
import com.orem.bashhub.adapter.SearchBashAdapter;
import com.orem.bashhub.adapter.SearchPeopleAdapter;
import com.orem.bashhub.adapter.VenuListAdapter;
import com.orem.bashhub.data.BashListPOJO;
import com.orem.bashhub.data.UsersListPOJO;
import com.orem.bashhub.databinding.FragmentSearchPeopleBashBinding;
import com.orem.bashhub.utils.Const;
import com.orem.bashhub.utils.MyLocationPicker;
import com.orem.bashhub.utils.MyPermissionChecker;
import com.orem.bashhub.utils.Utils;
import com.orem.bashhub.utils.apiinterface.Events;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.DividerItemDecoration;
import retrofit2.Call;

public class SearchBashFragment extends BaseFragment {

    String[] spinnerTitles;
    int[] spinnerImages;
    private FragmentSearchPeopleBashBinding binding;
    private List<UsersListPOJO.Data> users = new ArrayList<>();
    private boolean isUsers = true, isVenue = false, isSearch = false, isReset = false, isNearBy = false;
    private List<UsersListPOJO.Data> list = new ArrayList<>();
    private SearchPeopleAdapter adapter;
    private int position = -1;
    private Call<BashListPOJO> bashCall;
    private Call<UsersListPOJO> userCall;
    private String category = "";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_search_people_bash, container, false);
        init();
        registerEventBus();
        MyPermissionChecker.getPermission(mContext, new String[]{Manifest.permission.READ_CONTACTS});
        return binding.getRoot();
    }

    private void init() {
        spinnerTitles = new String[]{mContext.getString(R.string.bash_category), mContext.getString(R.string.pool_party), mContext.getString(R.string.queer_friendly), mContext.getString(R.string.desi_party), mContext.getString(R.string.gone_country), mContext.getString(R.string.karaoke_time), mContext.getString(R.string.viral_event)};
        spinnerImages = new int[]{R.drawable.bikini
                , R.drawable.bikini
                , R.drawable.gay
                , R.drawable.flag
                , R.drawable.cowboy
                , R.drawable.mic, R.drawable.viral};
        CustomSpinnerAdapter mCustomAdapter = new CustomSpinnerAdapter(mContext, spinnerTitles, spinnerImages);
        binding.spBashCategory.setAdapter(mCustomAdapter);
        binding.spBashCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                                             @Override
                                                             public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                                                 if (position == 0) {
                                                                     category = "";
                                                                     if (binding.searchTV.getText().toString().length() > 0) {
                                                                         isReset = false;
                                                                         apiSearchBash(binding.searchTV.getText().toString().toLowerCase());
                                                                     }
                                                                 } else {
                                                                     category = spinnerTitles[position].toString();
                                                                     isReset = false;
                                                                     apiSearchBash(binding.searchTV.getText().toString().toLowerCase());
                                                                 }
                                                             }

                                                             @Override
                                                             public void onNothingSelected(AdapterView<?> parent) {

                                                             }
                                                         }
        );
        adapter = new SearchPeopleAdapter(mContext, list, this);
        binding.peopleRV.setAdapter(adapter);
        binding.bashsLL.setOnClickListener(this);
        binding.venuesLL.setOnClickListener(this);
        binding.peopleLL.setOnClickListener(this);
        binding.backIV.setOnClickListener(this);
        binding.nearbyLL.setOnClickListener(this);
        binding.peopleRV.addItemDecoration(new DividerItemDecoration(mContext, DividerItemDecoration.VERTICAL));
        binding.searchTV.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (!charSequence.toString().isEmpty()) {
                    if (isUsers) {
                        if (charSequence.length() > 1) {
                            isReset = false;
                            apiSearchUser(charSequence.toString().toLowerCase());
                        }
                    } else if (isVenue) {
                        isReset = false;
                        apiVenue();
                    } else {
                        if (charSequence.length() > 1) {
                            isReset = false;
                            apiSearchBash(charSequence.toString().toLowerCase());
                        } else if (!category.equalsIgnoreCase("")) {
                            isReset = false;
                            apiSearchBash("");
                        }
                    }
                } else if (!category.equalsIgnoreCase("")) {
                    isReset = false;
                    apiSearchBash("");
                } else {
                    resetAdapter();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    private void resetAdapter() {
        if (isUsers) {
            list.clear();
            list.addAll(users);
            adapter.notifyDataSetChanged();
        } else
            binding.bashRV.setAdapter(new SearchBashAdapter(mContext, new ArrayList<>()));
        isReset = true;
    }

    public void gotoOtherUser(String id) {
        unRegisterEventBus();
        Utils.hideKeyboard(mContext);
        FollowersFragment fragment = new FollowersFragment();
        fragment.setData(id);
        Utils.goToFragment(mContext, fragment, R.id.fragment_container);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.peopleLL:
                isUsers = true;
                isVenue = false;
                setLayout(1);
                break;
            case R.id.bashsLL:
                isUsers = false;
                isVenue = false;
                setLayout(0);
                break;
            case R.id.venuesLL:
                isUsers = false;
                isVenue = true;
                setLayout(2);
                break;
            case R.id.backIV:
                Objects.requireNonNull(getActivity()).onBackPressed();
                break;
            case R.id.nearbyLL:
                binding.searchTV.setText("");
                category = "";
                binding.spBashCategory.setSelection(0);
                callBashApi();
                break;
        }
    }

    private void setLayout(int i) {
        Utils.hideKeyboard(mContext);
        binding.venueVW.setBackgroundColor(getResources().getColor(R.color.light_gray));
        binding.peopleVW.setBackgroundColor(getResources().getColor(R.color.light_gray));
        binding.venueTV.setTextColor(getResources().getColor(R.color.purpletrs));
        binding.bashTV.setTextColor(getResources().getColor(R.color.purpletrs));
        binding.peopleTV.setTextColor(getResources().getColor(R.color.purpletrs));
        binding.bashVW.setBackgroundColor(getResources().getColor(R.color.light_gray));
        if (i == 1) {
            binding.searchTV.setHint(getString(R.string.search_people));
            binding.peopleTV.setTextColor(getResources().getColor(R.color.purple));
            binding.peopleVW.setBackgroundColor(getResources().getColor(R.color.black));
            binding.nearbyLL.setVisibility(View.GONE);
            binding.bashRV.setVisibility(View.GONE);
            binding.peopleRV.setVisibility(View.VISIBLE);
            binding.suggestedTV.setVisibility(View.VISIBLE);
            binding.venueRV.setVisibility(View.GONE);
            binding.categoryLayout.setVisibility(View.GONE);
        } else if (i == 2) {
            binding.searchTV.setHint("Search Business");
            binding.venueTV.setTextColor(getResources().getColor(R.color.purple));
            binding.venueVW.setBackgroundColor(getResources().getColor(R.color.black));
            binding.nearbyLL.setVisibility(View.GONE);
            binding.bashRV.setVisibility(View.GONE);
            binding.peopleRV.setVisibility(View.GONE);
            binding.venueRV.setVisibility(View.VISIBLE);
            binding.suggestedTV.setVisibility(View.GONE);
            binding.categoryLayout.setVisibility(View.GONE);
        } else {
            category = "";
            binding.venueRV.setVisibility(View.GONE);
            binding.spBashCategory.setSelection(0);
            binding.searchTV.setHint(getString(R.string.search_bash));
            binding.bashTV.setTextColor(getResources().getColor(R.color.purple));
            binding.bashVW.setBackgroundColor(getResources().getColor(R.color.black));
            binding.peopleRV.setVisibility(View.GONE);
            binding.suggestedTV.setVisibility(View.GONE);
            binding.nearbyLL.setVisibility(View.VISIBLE);
            binding.bashRV.setVisibility(View.VISIBLE);
            binding.categoryLayout.setVisibility(View.VISIBLE);
        }
        binding.searchTV.setText("");
    }

    private void callBashApi() {
        registerEventBus();
        if (mTinyDb.getString(Const.SAVED_LNG).isEmpty())
            MyLocationPicker.getCurrentLocation(mContext);
        else apiGetBash();
    }

    @SuppressLint("StaticFieldLeak")
    private void getContacts() {
        new AsyncTask<String, String, String>() {
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                dialog.show();
            }

            @Override
            protected String doInBackground(String... strings) {
                String contacts = "";
                ContentResolver cr = mContext.getContentResolver();
                Cursor cur = cr.query(ContactsContract.Contacts.CONTENT_URI, null, null, null, null);
                if ((cur != null ? cur.getCount() : 0) > 0) {
                    while (cur.moveToNext()) {
                        String id = cur.getString(cur.getColumnIndex(ContactsContract.Contacts._ID));
                        if (cur.getInt(cur.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER)) > 0) {
                            Cursor pCur = cr.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,
                                    ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?", new String[]{id}, null);
                            assert pCur != null;
                            while (pCur.moveToNext()) {
                                String phoneNo = pCur.getString(pCur.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER))
                                        .replace("(", "").replace(")", "")
                                        .replace("-", "").replace("+1", "")
                                        .replace(" ", "");
                                contacts = contacts.isEmpty() ? phoneNo : contacts + "," + phoneNo;
                            }
                            pCur.close();
                        }
                    }
                }
                if (cur != null) {
                    cur.close();
                }
                return contacts;
            }

            @Override
            protected void onPostExecute(String contacts) {
                super.onPostExecute(contacts);
                dialog.dismiss();
                apiSyncContacts(contacts);
            }
        }.execute();
    }

    private void apiSyncContacts(String contacts) {
        isSearch = false;
        registerEventBus();
        EventBus.getDefault().post(new Events.RequestUsersList(mContext, Const.apiSyncContacts(mContext, contacts), true, false));
    }

    private void apiSearchUser(String text) {
        isSearch = true;
        if (userCall != null && !userCall.isCanceled()) {
            userCall.cancel();
        }
        userCall = Const.apiSearchUserAll(mContext, text);
        registerEventBus();
        EventBus.getDefault().post(new Events.RequestUsersList(mContext, userCall, false, false));
    }

    private void apiGetBash() {
        isReset = false;
        isNearBy = true;
        registerEventBus();
        EventBus.getDefault().post(new Events.RequestBashSearch(mContext, Const.apiBashSearch(mContext), true, false));
    }

    private void apiVenue() {
        isReset = false;
        isNearBy = false;
        registerEventBus();
        EventBus.getDefault().post(new Events.RequestVenueList(mContext, Const.apiGetVenue(mContext, "", binding.searchTV.getText().toString().toLowerCase(), "1"), true, false));
    }

    private void apiSearchBash(String text) {
        isNearBy = false;
        if (bashCall != null && !bashCall.isCanceled()) {
            bashCall.cancel();
        }
        bashCall = Const.apiBashSearchAll(mContext, text, category);
        registerEventBus();
        EventBus.getDefault().post(new Events.RequestBashSearch(mContext, bashCall, false, false));
    }

    public void apiFollowUser(int position) {
        this.position = position;
        UsersListPOJO.Data item = list.get(position);
        registerEventBus();
        EventBus.getDefault().post(new Events.RequestOtherUser(mContext, Const.apiFollowUser(mContext, item.id,
                item.getFollow().equals(Const.ONE) ? Const.ZERO : Const.ONE), true, false));
    }

    @Subscribe
    public void apiUsersListRes(Events.GetUsersListData res) {
        if (!isSearch) {
            users.clear();
            users.addAll(res.getData().data);
        }
        if (!isReset) {
            list.clear();
            list.addAll(res.getData().data);
            adapter.notifyDataSetChanged();
        }
    }

    @Subscribe
    public void apivenueRes(Events.GetVenuListData res) {
//      listVenue=res.getData().getData().getVenues();
        binding.venueRV.setAdapter(new VenuListAdapter(mContext, res.getData().getData().getVenues(), null, "1"));
    }

    @Subscribe
    public void apiGetBashRes(Events.GetBashSearchData res) {
        if (res.getData().data.size() <= 0) {
            Utils.showMessageDialog(mContext, "", res.getData().mesg);
        } else {
            if (!isReset)
                binding.bashRV.setAdapter(new SearchBashAdapter(mContext, res.getData().data));
        }
    }

    @Subscribe
    public void getLocation(Events.LocationPickerResult res) {
        mTinyDb.putString(Const.SAVED_LAT, "" + res.getLatitude());
        mTinyDb.putString(Const.SAVED_LNG, "" + res.getLongitude());
        apiGetBash();
    }

    @Subscribe
    public void apiOtherUserRes(Events.GetOtherUserData res) {
        UsersListPOJO.Data item = list.get(position);
        list.get(position).setFollow(item.getFollow().equals(Const.ONE) ? Const.ZERO : Const.ONE);
        adapter.notifyDataSetChanged();
        //((MainActivity) mContext).apiFbFriends();
    }

    @Subscribe
    public void getMyPermissionCheckerResult(Events.PermissionCheckerResult res) {
        getContacts();
    }
}
