package com.orem.bashhub.data;

import com.google.gson.annotations.SerializedName;

public class CrashBashPOJO {

    @SerializedName("mesg")
    public String mesg;
    @SerializedName("data")
    public Data data;

    public static class Data {
        @SerializedName("count")
        public String count;
    }
}
