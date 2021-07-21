package com.orem.bashhub.data;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class UberProductsPOJO {

    @SerializedName("products")
    public List<Products> products;

    public static class Products {
        @SerializedName("description")
        public String description;
        @SerializedName("product_group")
        public String product_group;
        @SerializedName("display_name")
        public String display_name;
        @SerializedName("short_description")
        public String short_description;
        @SerializedName("shared")
        public boolean shared;
        @SerializedName("cash_enabled")
        public boolean cash_enabled;
        @SerializedName("image")
        public String image;
        @SerializedName("price_details")
        public Price_details price_details;
        @SerializedName("product_id")
        public String product_id;
        @SerializedName("capacity")
        public String capacity;
        @SerializedName("upfront_fare_enabled")
        public boolean upfront_fare_enabled;
    }

    public static class Price_details {
        @SerializedName("currency_code")
        public String currency_code;
        @SerializedName("cancellation_fee")
        public String cancellation_fee;
        @SerializedName("base")
        public String base;
        @SerializedName("cost_per_distance")
        public String cost_per_distance;
        @SerializedName("minimum")
        public String minimum;
        @SerializedName("distance_unit")
        public String distance_unit;
        @SerializedName("cost_per_minute")
        public String cost_per_minute;
        @SerializedName("service_fees")
        public List<String> service_fees;
    }
}
