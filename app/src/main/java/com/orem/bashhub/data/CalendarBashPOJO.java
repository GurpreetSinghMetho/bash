package com.orem.bashhub.data;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CalendarBashPOJO {

    @SerializedName("mesg")
    public String mesg;
    @SerializedName("data")
    public Data data;

    public static class Data {
        @SerializedName("upcomming")
        public List<BashDetailsPOJO> upcomming;
        @SerializedName("today")
        public List<BashDetailsPOJO> today;
    }
}
