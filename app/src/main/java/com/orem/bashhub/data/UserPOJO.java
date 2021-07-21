package com.orem.bashhub.data;

import android.content.Context;

import com.google.gson.annotations.SerializedName;
import com.orem.bashhub.R;
import com.orem.bashhub.utils.Const;
import com.orem.bashhub.utils.Utils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Dawinder on 12/02/2018.
 */

public class UserPOJO {

    @SerializedName("mesg")
    public String mesg;
    @SerializedName("data")
    public Data data;

    public static class Data {
        @SerializedName("safe_ride")
        public Safe_ride safe_ride;
        @SerializedName("pendingRating")
        public List<BashDetailsPOJO> pendingRating;
        @SerializedName("free_notify")
        public List<BashDetailsPOJO> free_notify;
        @SerializedName("location")
        public String location;
        @SerializedName("today_bash")
        public String today_bash;
        @SerializedName("paypal_id")
        public String paypal_id;
        @SerializedName("paypal_name")
        public String paypal_name;
        @SerializedName("temp_wallet")
        public String temp_wallet;
        @SerializedName("complement5")
        public String complement5;
        @SerializedName("complement4")
        public String complement4;
        @SerializedName("complement3")
        public String complement3;
        @SerializedName("complement2")
        public String complement2;
        @SerializedName("complement1")
        public String complement1;
        @SerializedName("ratings")
        public String ratings;
        @SerializedName("avtar_img")
        public String avtar_img;
        @SerializedName("standing_img")
        public String standing_img;
        @SerializedName("costume_id")
        public int costume_id;
        @SerializedName("image")
        public String image;
        @SerializedName("address")
        public String address;
        @SerializedName("gender")
        public String gender;
        @SerializedName("social_id")
        public String social_id;
        @SerializedName("email_verify_token")
        public String email_verify_token;
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
        public String linked_fb;
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
        UsersListPOJO userList = null;
        @SerializedName("wallet")
        private String wallet;
        @SerializedName("hosted")
        private String hosted;
        @SerializedName("attendant")
        private String attendant;
        @SerializedName("followes")
        private String followes;
        @SerializedName("following")
        private String following;
        @SerializedName("pref_bash_type")
        private String pref_bash_type;
        @SerializedName("pref_distance")
        private String pref_distance;
        @SerializedName("today_bash_count")
        private String today_bash_count;
        @SerializedName("venue_count")
        private String today_venue_count;


        public String getFormattedDOB() {
            return Utils.changeDateFormat(dob);
        }

        public String getWalletValue() {
            return wallet != null && !wallet.isEmpty() ? wallet : "0";
        }

        public String getUsernameText() {
            return "@" + username;
        }

        public String getFullName() {
            return fname + " " + lname;
        }

        public String getGenderText(Context mContext) {
            String text = "";
            if (gender != null) {
                if (gender.equals(Const.MALE)) text = mContext.getString(R.string.male);
                else if (gender.equals(Const.FEMALE)) text = mContext.getString(R.string.female);
                else text = mContext.getString(R.string.not_specified);
            }
            return text;
        }

        public void setPhone_number(String phone_number) {
            this.phone_number = phone_number;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getToday_bash_count() {
            return today_bash_count != null && !today_bash_count.isEmpty() ? today_bash_count : Const.ZERO;
        }

        public void setToday_bash_count(String today_bash_count) {
            this.today_bash_count = today_bash_count;
        }

        public String getToday_venue_count() {
            return today_venue_count != null && !today_venue_count.isEmpty() ? today_venue_count : Const.ZERO;
        }

        public void setToday_venue_count(String today_venue_count) {
            this.today_venue_count = today_venue_count;
        }

        public String getHosted() {
            return hosted == null || hosted.isEmpty() ? Const.ZERO : hosted;
        }

        public String getAttendant() {
            return attendant == null || attendant.isEmpty() ? Const.ZERO : attendant;
        }

        public String getFollowes() {
            return followes == null || followes.isEmpty() ? Const.ZERO : followes;
        }

        public String getFollowing() {
            return following == null || following.isEmpty() ? Const.ZERO : following;
        }

        public String getPref_bash_type() {
            return pref_bash_type == null || pref_bash_type.isEmpty() ? "" : pref_bash_type;
        }

        public void setPref_bash_type(String pref_bash_type) {
            this.pref_bash_type = pref_bash_type;
        }

        public String getPref_distance() {
            return pref_distance == null || pref_distance.isEmpty() ? Const.ZERO : pref_distance;
        }

        public void setPref_distance(String pref_distance) {
            this.pref_distance = pref_distance;
        }

        public List<ComplementPOJO> getComplementCount(Context mContext) {
            List<ComplementPOJO> list = new ArrayList<>();
            if (!complement1.equals(Const.ZERO))
                list.add(new ComplementPOJO(mContext.getString(R.string.awesome_music), complement1, R.drawable.ic_c_music));
            if (!complement2.equals(Const.ZERO))
                list.add(new ComplementPOJO(mContext.getString(R.string.groovy_dancing), complement2, R.drawable.ic_c_dance));
            if (!complement3.equals(Const.ZERO))
                list.add(new ComplementPOJO(mContext.getString(R.string.perfect_BASH), complement3, R.drawable.ic_c_bashper));
            if (!complement4.equals(Const.ZERO))
                list.add(new ComplementPOJO(mContext.getString(R.string.delicious_drink), complement4, R.drawable.ic_c_drink));
            if (!complement5.equals(Const.ZERO))
                list.add(new ComplementPOJO(mContext.getString(R.string.great_atmosphere), complement5, R.drawable.ic_c_happy));
            return list;
        }

        public boolean isComplementVisible() {
            return !complement1.equals(Const.ZERO) || !complement2.equals(Const.ZERO) || !complement3.equals(Const.ZERO) ||
                    !complement4.equals(Const.ZERO) || !complement5.equals(Const.ZERO);
        }

        public String getWallet() {
            return Const.DEFAULT_CURRENCY + getWalletValue();
        }

        public void setWallet(String wallet) {
            this.wallet = wallet;
        }

        public String getTemp_wallet() {
            return Const.DEFAULT_CURRENCY + temp_wallet;
        }

        public String getTemp_walletText(Context mContext) {
            return mContext.getString(R.string.bash_pending) + " : " + Const.DEFAULT_CURRENCY + temp_wallet;
        }

        public String getPaypalDetails(Context mContext) {
            return String.format(mContext.getString(R.string.paypal_details), paypal_name, paypal_id);
        }

        public boolean isPaypalLinked() {
            return !paypal_id.isEmpty();
        }

        public String paypalLinkedText(Context mContext) {
            return isPaypalLinked() ? mContext.getString(R.string.unlink_paypal) : mContext.getString(R.string.link_paypal);
        }

        public boolean isCheckInVisible() {
            return hosted != null && Integer.parseInt(hosted) > 0;
        }

        public boolean isActiveEvent() {
            return today_bash != null && Integer.parseInt(today_bash) > 0;
        }

        public boolean isLocationOn() {
            return location != null && location.equals(Const.ONE);
        }

        public void setUserList(UsersListPOJO userList) {
            this.userList = userList;
        }

        public String getLocationText(Context mContext) {
            return mContext.getString(isLocationOn() ? R.string.location_on : R.string.location_off);
        }

        public boolean isUserVisible() {
            return !isLocationOn() && userList != null && userList.data.size() > 0;
        }
    }

    public static class Safe_ride {
        @SerializedName("notify")
        public String notify;
        @SerializedName("lng")
        public String lng;
        @SerializedName("lat")
        public String lat;
        @SerializedName("location")
        public String location;
        @SerializedName("users")
        public List<SearchUserPOJO.Data> users;
        @SerializedName("id")
        public String id;
    }
}
