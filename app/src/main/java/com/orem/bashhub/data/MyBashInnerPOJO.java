package com.orem.bashhub.data;

import com.google.gson.annotations.SerializedName;

public class MyBashInnerPOJO {

    @SerializedName("user_data")
    public User_data user_data;
    @SerializedName("bash_data")
    public BashDetailsPOJO bash_data;
    @SerializedName("updated_at")
    public String updated_at;
    @SerializedName("created_at")
    public String created_at;
    @SerializedName("user_id")
    public int user_id;
    @SerializedName("bash_id")
    public int bash_id;
    @SerializedName("id")
    public int id;

    public static class User_data {
        @SerializedName("lng")
        public String lng;
        @SerializedName("lat")
        public String lat;
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
        public int id;
    }
}
