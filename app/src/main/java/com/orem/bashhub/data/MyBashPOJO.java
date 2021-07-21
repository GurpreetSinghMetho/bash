package com.orem.bashhub.data;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MyBashPOJO {

    @SerializedName("data")
    public Data data;
    @SerializedName("mesg")
    public String mesg;

    public static class Data {
        @SerializedName("upcomming")
        public List<MyBashInnerPOJO> upcomming;
        @SerializedName("today")
        public List<MyBashInnerPOJO> today;
    }
}
