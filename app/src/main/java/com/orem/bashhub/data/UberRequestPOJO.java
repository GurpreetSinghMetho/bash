package com.orem.bashhub.data;

import android.content.Context;

import com.google.gson.annotations.SerializedName;
import com.orem.bashhub.R;
import com.orem.bashhub.utils.Const;

public class UberRequestPOJO {

    @SerializedName("shared")
    public boolean shared;
    @SerializedName("vehicle")
    public Vehicle vehicle;
    @SerializedName("location")
    public Location location;
    @SerializedName("request_id")
    public String request_id;
    @SerializedName("pickup")
    public Pickup pickup;
    @SerializedName("driver")
    public Driver driver;
    @SerializedName("destination")
    public Destination destination;
    @SerializedName("product_id")
    public String product_id;
    @SerializedName("status")
    public String status;

    public static class Vehicle {
        @SerializedName("license_plate")
        public String license_plate;
        @SerializedName("model")
        public String model;
        @SerializedName("picture_url")
        public String picture_url;
        @SerializedName("make")
        public String make;
    }

    public static class Location {
        @SerializedName("longitude")
        public double longitude;
        @SerializedName("bearing")
        public int bearing;
        @SerializedName("latitude")
        public double latitude;
    }

    public static class Pickup {
        @SerializedName("longitude")
        public double longitude;
        @SerializedName("eta")
        public int eta;
        @SerializedName("latitude")
        public double latitude;
    }

    public static class Driver {
        @SerializedName("name")
        public String name;
        @SerializedName("picture_url")
        public String picture_url;
        @SerializedName("rating")
        public String rating;
        @SerializedName("phone_number")
        public String phone_number;
    }

    public static class Destination {
        @SerializedName("longitude")
        public double longitude;
        @SerializedName("eta")
        public int eta;
        @SerializedName("latitude")
        public double latitude;
    }

    public String getNotifyMessage(Context mContext) {
        return String.format(mContext.getString(R.string.uber_notify_msg), Const.getLoggedInUser(mContext).getFullName(), driver.name, driver.phone_number, vehicle.model, vehicle.license_plate);
    }
}
