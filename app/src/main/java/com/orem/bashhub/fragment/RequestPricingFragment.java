package com.orem.bashhub.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.orem.bashhub.R;
import com.orem.bashhub.activity.MainActivity;
import com.orem.bashhub.data.BashDetailsPOJO;
import com.orem.bashhub.data.VenueListPOJO;
import com.orem.bashhub.databinding.ActivityRequestPriceBinding;
import com.orem.bashhub.dialogs.DialogRequestSuccess;
import com.orem.bashhub.utils.Const;
import com.orem.bashhub.utils.Utils;
import com.orem.bashhub.utils.apiinterface.Events;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;

public class RequestPricingFragment extends BaseFragment {
    ActivityRequestPriceBinding binding;
    BashDetailsPOJO data;
    VenueListPOJO.Venue venueData;
    String from;
    String type;
    int position;
    String id;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.activity_request_price, container, false);
        init();
        return binding.getRoot();
    }

    private void init() {
        binding.mBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity) mContext).getSupportFragmentManager().popBackStack();
            }
        });
        if (data != null) {
            Utils.loadImage(mContext, Const.IMAGE_BASE_EVENT + data.getVenue().getImage(), binding.mVenuImage, R.drawable.placeholder);
            binding.mLocation.setText(data.getVenue().getAddress());
            binding.mVenueName.setText(data.getVenue().getName());
            if (from.equalsIgnoreCase("bottle")) {
                type = "5";
                binding.mTitle.setText("Bottle");
                binding.mName.setText(data.getBottles().get(position).getName());
                binding.mDescription.setText(data.getBottles().get(position).getDescription());
                binding.mGuestLayout.setVisibility(View.GONE);
                id = data.getBottles().get(position).getId();
            } else if (from.equalsIgnoreCase("table")) {
                id = data.getTables().get(position).getId();
                type = "6";
                binding.mTitle.setText("Table");
                binding.mName.setText(data.getTables().get(position).getName());
                binding.mDescription.setText(data.getTables().get(position).getDescription());
                binding.mTableNo.setText(data.getTables().get(position).getSlug());
                binding.mGuest.setText(data.getTables().get(position).getGuestPerTable());

            } else if (from.equalsIgnoreCase("section")) {
                type = "7";
                id = data.getSections().get(position).getId();
                binding.mTitle.setText("Section");
                binding.mTableNoTitle.setText("Section No.");
                binding.mName.setText(data.getSections().get(position).getName());
                binding.mTableNo.setText(data.getSections().get(position).getSlug());
                binding.mDescription.setText(data.getSections().get(position).description);
                binding.mGuest.setText(data.getSections().get(position).getGuest());

//            binding.mDescriptionTitle.setVisibility(View.GONE);
            }
        } else {
            Utils.loadImage(mContext, Const.IMAGE_BASE_EVENT + venueData.getImage(), binding.mVenuImage, R.drawable.placeholder);
            binding.mLocation.setText(venueData.getAddress());
            binding.mVenueName.setText(venueData.getName());
            if (from.equalsIgnoreCase("bottle")) {
                type = "5";
                binding.mTitle.setText("Bottle");
                binding.mName.setText(venueData.getBottles().get(position).getName());
                binding.mDescription.setText(venueData.getBottles().get(position).getDescription());
                binding.mGuestLayout.setVisibility(View.GONE);
                id = venueData.getBottles().get(position).getId().toString();
            } else if (from.equalsIgnoreCase("table")) {
                id = venueData.getTables().get(position).getId().toString();
                type = "6";
                binding.mTitle.setText("Table");
                binding.mName.setText(venueData.getTables().get(position).getName());
                binding.mDescription.setText(venueData.getTables().get(position).getDescription());
                binding.mTableNo.setText(venueData.getTables().get(position).getSlug().toString());
                binding.mGuest.setText(venueData.getTables().get(position).getGuestPerTable().toString());

            } else if (from.equalsIgnoreCase("section")) {
                type = "7";
                id = venueData.getSections().get(position).getId().toString();
                binding.mTitle.setText("Section");
                binding.mTableNoTitle.setText("Section No.");
                binding.mName.setText(venueData.getSections().get(position).getName());
                binding.mTableNo.setText(venueData.getSections().get(position).getSlug().toString());
                binding.mDescription.setText("");
                if (venueData.getSections().get(position).getGuest() != null)
                    binding.mGuest.setText(venueData.getSections().get(position).getGuest().toString());

//            binding.mDescriptionTitle.setVisibility(View.GONE);
            }
        }


        binding.mAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                apiPriceRequest();
            }
        });
    }

    public void setData(BashDetailsPOJO data, String from, int position) {
        this.data = data;
        this.from = from;
        this.position = position;
    }

    public void setVenueData(VenueListPOJO.Venue data, String from, int position) {
        this.venueData = data;
        this.from = from;
        this.position = position;
    }

    public void apiPriceRequest() {
        registerEventBus();
        if (data != null) {
            EventBus.getDefault().post(new Events.RequestBasic(mContext, Const.priceRequest(mContext, data.id, id,
                    data.getVenueId(), type, binding.mMessage.getText().toString()), true, false));

        } else {
            EventBus.getDefault().post(new Events.RequestBasic(mContext, Const.priceRequest(mContext, "", id,
                    venueData.getId().toString(), type, binding.mMessage.getText().toString()), true, false));
        }
    }

    @Subscribe
    public void apiBuyTicketsRes(Events.GetBasicData res) {
        unRegisterEventBus();

//        Utils.showToast(mContext, getString(R.string.req_submitted));
//        Objects.requireNonNull(getActivity()).onBackPressed();
        DialogRequestSuccess ticketSuccess = new DialogRequestSuccess();
        ticketSuccess.show(getChildFragmentManager(), ticketSuccess.getTag());
    }
}