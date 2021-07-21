package com.orem.bashhub.data;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class FbFriendsLocPOJO {

    @SerializedName("data")
    public List<Data> data;
    @SerializedName("mesg")
    public String mesg;

    public static class Data {
        @SerializedName("last_location")
        public String last_location;
        @SerializedName("lng")
        public String lng;
        @SerializedName("lat")
        public String lat;
        @SerializedName("session_id")
        public String session_id;
    }
}
