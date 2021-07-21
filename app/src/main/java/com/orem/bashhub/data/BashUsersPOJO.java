package com.orem.bashhub.data;

import android.content.Context;

import com.google.gson.annotations.SerializedName;
import com.orem.bashhub.R;
import com.orem.bashhub.utils.Const;

import java.util.List;

public class BashUsersPOJO {

    @SerializedName("data")
    public List<Data> data;
    @SerializedName("mesg")
    public String mesg;

    public static class Data {
        @SerializedName("user_data")
        public User_data user_data;
        @SerializedName("ticket")
        public Ticket ticket;

        public boolean isCheckIn() {
            if (user_data.bash_price > 0) {
                return ticket.id != null && ticket.isCheckIn();
            } else {
                return user_data.check_in.equals(Const.ONE) && user_data.is_host.equals(Const.ZERO);
            }
        }

        public boolean isTicketVisible() {
            return ticket != null && ticket.id != null;
        }
    }

    public static class User_data {
        public boolean isHeadVisible = false;
        @SerializedName("bash_price")
        double bash_price;
        @SerializedName("check_in")
        String check_in;
        @SerializedName("is_host")
        public String is_host;
        @SerializedName("following")
        public int following;
        @SerializedName("followes")
        public int followes;
        @SerializedName("attendant")
        public int attendant;
        @SerializedName("hosted")
        public int hosted;
        @SerializedName("lng")
        public String lng;
        @SerializedName("lat")
        public String lat;
        @SerializedName("today_bash_count")
        public int today_bash_count;
        @SerializedName("standing_img")
        public String standing_img;
        @SerializedName("avtar_img")
        public String avtar_img;
        @SerializedName("costume_id")
        public int costume_id;
        @SerializedName("pref_distance")
        public int pref_distance;
        @SerializedName("pref_bash_type")
        public String pref_bash_type;
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

        public String getFullName() {
            return fname + " " + lname;
        }

        public String getUsernameText() {
            return "@" + username;
        }

        public String getHeadText() {
            return String.valueOf(fname.charAt(0)).toUpperCase();
        }

        public void setHeadVisible(boolean headVisible) {
            isHeadVisible = headVisible;
        }

        public boolean isHost() {
            return is_host.equals(Const.ONE);
        }
    }

    public static class Ticket {
        @SerializedName("barcodedata")
        public List<Barcodedata> barcodedata;
        @SerializedName("amount")
        public String amount;
        @SerializedName("updated_at")
        public String updated_at;
        @SerializedName("created_at")
        public String created_at;
        @SerializedName("qty")
        public String qty;
        @SerializedName("bash_id")
        public int bash_id;
        @SerializedName("user_id")
        public int user_id;
        @SerializedName("id")
        public String id;

        public String usedCount() {
            int used = 0;
            if (id != null) {
                for (Barcodedata item : barcodedata)
                    if (item.status.equals(Const.ONE)) used++;
            }
            return "" + used;
        }

        public String getTicketsText(Context mContext) {
            return mContext.getString(R.string.tickets) + " : " + usedCount() + "/" + (qty != null ? qty : "0");
        }

        public boolean isCheckIn() {
            return Integer.parseInt(usedCount()) > 0;
        }
    }

    public static class Barcodedata {
        @SerializedName("used")
        public String used;
        @SerializedName("status")
        public String status;
        @SerializedName("user_id")
        public int user_id;
        @SerializedName("updated_at")
        public String updated_at;
        @SerializedName("created_at")
        public String created_at;
        @SerializedName("bash_id")
        public int bash_id;
        @SerializedName("ticket_id")
        public int ticket_id;
        @SerializedName("code")
        public String code;
        @SerializedName("id")
        public int id;
    }
}
