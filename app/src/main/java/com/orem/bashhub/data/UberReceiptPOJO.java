package com.orem.bashhub.data;

import android.content.Context;

import com.google.gson.annotations.SerializedName;
import com.orem.bashhub.R;

import java.util.List;

public class UberReceiptPOJO {

    @SerializedName("currency_code")
    public String currency_code;
    @SerializedName("subtotal")
    public String subtotal;
    @SerializedName("total_fare")
    public String total_fare;
    @SerializedName("duration")
    public String duration;
    @SerializedName("request_id")
    public String request_id;
    @SerializedName("total_charged")
    public String total_charged;
    @SerializedName("distance_label")
    public String distance_label;
    @SerializedName("charge_adjustments")
    public List<Charge_adjustments> charge_adjustments;
    @SerializedName("distance")
    public String distance;

    public static class Charge_adjustments {
        @SerializedName("name")
        public String name;
        @SerializedName("type")
        public String type;
        @SerializedName("amount")
        public String amount;
    }

    public String getReceiptText(Context mContext) {
        return mContext.getString(R.string.total_amount) + " : " + total_fare + "\n" +
                mContext.getString(R.string.total_distance) + " : " + distance + " " + distance_label + "\n" +
                mContext.getString(R.string.total_duration) + " : " + duration;
    }
}
