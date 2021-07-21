package com.orem.bashhub.data;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CheckInPOJO {

    @SerializedName("mesg")
    public String mesg;
    @SerializedName("data")
    public Data data;

    public static class Data {
        @SerializedName("past")
        public List<BashDetailsPOJO> past;
        @SerializedName("live")
        public List<BashDetailsPOJO> live;
    }
}
