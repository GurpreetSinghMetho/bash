package com.orem.bashhub.data;

import androidx.annotation.Nullable;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class FbFriendsPOJO {

    @SerializedName("data")
    public List<Data> data;
    @SerializedName("mesg")
    public String mesg;

    public static class Data {
        @SerializedName("last_location_text")
        String last_location_text;
        @SerializedName("standing_img")
        public String standing_img;
        @SerializedName("lng")
        String lng;
        @SerializedName("lat")
        String lat;
        @SerializedName("today_bash_count")
        public int today_bash_count;
        @SerializedName("image")
        public String image;
        @SerializedName("address")
        public String address;
        @SerializedName("gender")
        public String gender;
        @SerializedName("social_id")
        public String social_id;
        @SerializedName("device_type")
        public String device_type;
        @SerializedName("device_token")
        public String device_token;
        @SerializedName("otp")
        public String otp;
        @SerializedName("country_code")
        public String country_code;
        @SerializedName("username")
        public String username;
        @SerializedName("dob")
        public String dob;
        @SerializedName("lname")
        public String lname;
        @SerializedName("fname")
        public String fname;
        @SerializedName("userType")
        public int userType;
        @SerializedName("linked_snap")
        public String linked_snap;
        @SerializedName("linked_fb")
        public int linked_fb;
        @SerializedName("session_id")
        public String session_id;
        @SerializedName("updated_at")
        public String updated_at;
        @SerializedName("created_at")
        public String created_at;
        @SerializedName("phone_number")
        public String phone_number;
        @SerializedName("email_verified_at")
        public String email_verified_at;
        @SerializedName("email")
        public String email;
        @SerializedName("name")
        public String name;
        @SerializedName("id")
        public String id;

        public String getLast_update_text() {
            return last_location_text;
        }

        public void setLast_update_text(String last_update_text) {
            this.last_location_text = last_update_text;
        }

        public double getLng() {
            return lng != null && !lng.isEmpty() ? Double.parseDouble(lng) : 0;
        }

        public double getLat() {
            return lat != null && !lat.isEmpty() ? Double.parseDouble(lat) : 0;
        }

        public void setLng(String lng) {
            this.lng = lng;
        }

        public void setLat(String lat) {
            this.lat = lat;
        }

        public String getFullName() {
            return fname + " " + lname;
        }

        public void setSession_id(String session_id) {
            this.session_id = session_id;
        }

        @Override
        public boolean equals(@Nullable Object obj) {
            return obj instanceof FbFriendsPOJO.Data && ((FbFriendsPOJO.Data) obj).session_id.equalsIgnoreCase(session_id);
        }
    }
}
