package com.orem.bashhub.data;

import android.content.Context;

import com.google.gson.annotations.SerializedName;
import com.orem.bashhub.R;

import java.util.List;

public class UberEstimatePOJO {

    @SerializedName("pickup_estimate")
    public String pickup_estimate;
    @SerializedName("trip")
    public Trip trip;
    @SerializedName("fare")
    public Fare fare;

    public static class Trip {
        @SerializedName("distance_estimate")
        public String distance_estimate;
        @SerializedName("duration_estimate")
        public double duration_estimate;
        @SerializedName("distance_unit")
        public String distance_unit;
    }

    public static class Fare {
        @SerializedName("currency_code")
        public String currency_code;
        @SerializedName("display")
        public String display;
        @SerializedName("expires_at")
        public int expires_at;
        @SerializedName("fare_id")
        public String fare_id;
        @SerializedName("value")
        public String value;
        @SerializedName("breakdown")
        public List<Breakdown> breakdown;
    }

    public static class Breakdown {
        @SerializedName("value")
        public String value;
        @SerializedName("name")
        public String name;
        @SerializedName("type")
        public String type;
    }

    public String getEstimateText(Context mContext) {
        return mContext.getString(R.string.uber_est_time) + " : " + (trip.duration_estimate / 60) + " " + mContext.getString(R.string.mins) + "\n" +
                mContext.getString(R.string.uber_est_distance) + " : " + trip.distance_estimate + " " + trip.distance_unit + "\n" +
                mContext.getString(R.string.uber_estimate_fare) + " : " + fare.display + "\n" +
                mContext.getString(R.string.uber_est_pick_up) + " : " +
                (pickup_estimate != null ? "" + pickup_estimate + " " + mContext.getString(R.string.mins) : mContext.getString(R.string.not_available));
    }
}
