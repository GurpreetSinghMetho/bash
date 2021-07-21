package com.orem.bashhub.data;

import android.content.Context;

import com.google.gson.annotations.SerializedName;
import com.orem.bashhub.R;
import com.orem.bashhub.utils.Const;

import java.util.ArrayList;
import java.util.List;

public class OtherUserPOJO {

    @SerializedName("mesg")
    public String mesg;
    @SerializedName("data")
    public Data data;

    public static class Data {
        @SerializedName("recent_bash")
        public List<BashDetailsPOJO> recent_bash;
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
        @SerializedName("pref_bash_type")
        public String pref_bash_type;
        @SerializedName("pref_distance")
        public String pref_distance;
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
        @SerializedName("follow")
        private String follow;
        @SerializedName("following_me")
        private String followMe;
        @SerializedName("hosted")
        private String hosted;
        @SerializedName("attendant")
        private String attendant;
        @SerializedName("followes")
        private String followes;
        @SerializedName("following")
        private String following;
        @SerializedName("today_bash_count")
        private String today_bash_count;

        public String getFollowMe() {
            return followMe;
        }

        public void setFollowMe(String followMe) {
            this.followMe = followMe;
        }

        public String getUsernameText() {
            return "@" + username;
        }

        public String getFullName() {
            return fname + " " + lname;
        }

        public String getGenderText(Context mContext) {
            String text;
            if (gender.equals(Const.MALE)) text = mContext.getString(R.string.male);
            else if (gender.equals(Const.FEMALE)) text = mContext.getString(R.string.female);
            else text = mContext.getString(R.string.not_specified);
            return text;
        }

        public String getHosted() {
            return hosted.isEmpty() ? Const.ZERO : hosted;
        }

        public String getAttendant() {
            return attendant.isEmpty() ? Const.ZERO : attendant;
        }

        public String getFollowes() {
            return followes.isEmpty() ? Const.ZERO : followes;
        }

        public String getFollowing() {
            return following.isEmpty() ? Const.ZERO : following;
        }

        public String getFollow() {
            return follow;
        }

        public void setFollow(String follow) {
            this.follow = follow;
        }

        public String getFollowText(Context mContext) {
            return mContext.getString(getFollow().equals(Const.ONE) ? R.string.un_follow : R.string.follow);
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
    }
}