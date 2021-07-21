package com.orem.bashhub.dialogs;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import com.orem.bashhub.R;
import com.orem.bashhub.adapter.BashHubListAdapter;
import com.orem.bashhub.adapter.VenuListAdapter;
import com.orem.bashhub.data.BashDetailsPOJO;
import com.orem.bashhub.data.VenueListPOJO;
import com.orem.bashhub.databinding.DialogMaplistBashBinding;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;

@SuppressLint("ValidFragment")
public class DialogmapListBashes extends DialogBaseFragment {

    List<VenueListPOJO.Venue> venueList;
    boolean isVenue;
    private DialogMaplistBashBinding binding;
    private LinearLayout bashhubLL;
    private List<BashDetailsPOJO> list;

    public DialogmapListBashes(LinearLayout bashhubLL, List<BashDetailsPOJO> list, List<VenueListPOJO.Venue> venueList, boolean isVenue) {
        this.bashhubLL = bashhubLL;
        this.list = list;
        this.venueList = venueList;
        this.isVenue = isVenue;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void setupDialog(@NonNull Dialog dialog, int style) {
        super.setupDialog(dialog, style);
        binding = DataBindingUtil.inflate(LayoutInflater.from(mContext), R.layout.dialog_maplist_bash, null, false);
        View mDialog = binding.getRoot();
        dialog.setContentView(mDialog);
        ((View) mDialog.getParent()).setBackgroundColor(getResources().getColor(android.R.color.transparent));
        init();
    }

    private void init() {
        if (!isVenue) {
            if (list.size() > 0) {
                binding.listRV.setAdapter(new BashHubListAdapter(mContext, list, this));
            } else {
                binding.listRV.setVisibility(View.GONE);
                binding.tvMsg.setVisibility(View.VISIBLE);
                binding.tvMsg.setText("No Event Found");
            }
        } else {
            binding.mTitle.setText("Business List");
            if (venueList.size() > 0) {
                binding.listRV.setAdapter(new VenuListAdapter(mContext, venueList, this, "0"));
            } else {
                binding.listRV.setVisibility(View.GONE);
                binding.tvMsg.setVisibility(View.VISIBLE);
                binding.tvMsg.setText("No Business Found");
            }
        }

    }

    @Override
    public void onDestroy() {
        bashhubLL.setVisibility(View.VISIBLE);
        super.onDestroy();
    }
}
