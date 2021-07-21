package com.orem.bashhub.activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.ContactsContract;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.DividerItemDecoration;

import com.orem.bashhub.R;
import com.orem.bashhub.adapter.ContactsAdapter;
import com.orem.bashhub.data.UsersListPOJO;
import com.orem.bashhub.databinding.ActivitySyncContactsBinding;
import com.orem.bashhub.fragment.FollowersFragment;
import com.orem.bashhub.utils.Const;
import com.orem.bashhub.utils.MyPermissionChecker;
import com.orem.bashhub.utils.Utils;
import com.orem.bashhub.utils.apiinterface.Events;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

public class SyncContactsActivity extends BaseActivity {

    private ActivitySyncContactsBinding binding;
    private List<UsersListPOJO.Data> list = new ArrayList<>();
    private ContactsAdapter adapter;
    private int position = -1;

    @Override
    public void onBackPressed() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_sync_contacts);
        ini();
        registerEventBus();
        MyPermissionChecker.getPermission(mContext, new String[]{Manifest.permission.READ_CONTACTS});
    }

    private void ini() {
        binding.tvSkip.setOnClickListener(v -> startNewActivity(SnapChatActivity.class, true, true, null));

        adapter = new ContactsAdapter(mContext, list);
        binding.rvContacts.addItemDecoration(new DividerItemDecoration(mContext, DividerItemDecoration.VERTICAL));
        binding.rvContacts.setAdapter(adapter);
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
        registerEventBus();
        EventBus.getDefault().post(new Events.RequestUsersList(mContext, Const.apiSyncContacts(mContext, contacts), true, false));
    }

    public void apiFollowUser(int position) {
        this.position = position;
        UsersListPOJO.Data item = list.get(position);
        registerEventBus();
        EventBus.getDefault().post(new Events.RequestOtherUser(mContext, Const.apiFollowUser(mContext, item.id,
                item.getFollow().equals(Const.ONE) ? Const.ZERO : Const.ONE), true, false));
    }

    @Subscribe
    public void getMyPermissionCheckerResult(Events.PermissionCheckerResult res) {
        unRegisterEventBus();
        getContacts();
    }

    @Subscribe
    public void apiSyncContactsRes(Events.GetUsersListData res) {
        unRegisterEventBus();
        list.clear();
        list.addAll(res.getData().data);
        adapter.notifyDataSetChanged();
    }

    @Subscribe
    public void apiOtherUserRes(Events.GetOtherUserData res) {
        unRegisterEventBus();
        UsersListPOJO.Data item = list.get(position);
        list.get(position).setFollow(item.getFollow().equals(Const.ONE) ? Const.ZERO : Const.ONE);
        adapter.notifyDataSetChanged();
    }
}
