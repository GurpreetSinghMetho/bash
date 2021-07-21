package com.orem.bashhub.data;

import android.content.Context;

import androidx.annotation.Nullable;
import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;

import com.google.gson.annotations.SerializedName;
import com.orem.bashhub.R;
import com.orem.bashhub.utils.Const;

import java.util.List;

public class UsersListPOJO {

    @SerializedName("data")
    public List<Data> data;
    @SerializedName("mesg")
    public String mesg;

    public String getBlockedUser() {
        String ids = "";
        for (Data item : data) {
            if (item.block.equals(Const.ONE))
                ids = ids.isEmpty() ? item.id : ids + "," + item.id;
        }
        return ids;
    }

    public static class Data extends BaseObservable {
        @SerializedName("following_me")
        public String following_me;
        @SerializedName("follow_me")
        public String follow_me;
        @SerializedName("bash_invited")
        public String bash_invited;
        @SerializedName("no_of_ticket_purchase")
        public String no_of_ticket_purchase;
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
        @SerializedName("block")
        String block;
        @SerializedName("lng")
        String lng;
        @SerializedName("lat")
        String lat;
        @SerializedName("follow")
        private String follow;

        public void setBash_invited(String bash_invited) {
            this.bash_invited = bash_invited;
        }

        public String getUsernameText() {
            return "@" + username;
        }

        public String getFullName() {
            return fname + " " + lname;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getFollow() {
            return follow;
        }

        public void setFollow(String follow) {
            this.follow = follow;
        }

        public String getFollowText(Context mContext) {
            return mContext.getString(follow.equals(Const.ZERO) ? R.string.follow : R.string.prompt_following);
        }

        public int getFollowColor() {
            return follow.equals(Const.ZERO) ? R.color.lightpurple : R.color.white;
        }

        @Bindable
        public String getBlock() {
            return block;
        }

        public void setBlock(String block) {
            this.block = block;
            notifyChange();
        }

        public boolean isBlock(String isBlock) {
            return isBlock != null && isBlock.equals(Const.ONE);
        }

        @Override
        public boolean equals(@Nullable Object obj) {
            return obj instanceof UsersListPOJO.Data && ((UsersListPOJO.Data) obj).id.equalsIgnoreCase(id);
        }
    }
}
