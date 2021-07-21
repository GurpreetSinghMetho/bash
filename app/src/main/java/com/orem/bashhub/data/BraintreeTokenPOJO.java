package com.orem.bashhub.data;

import com.google.gson.annotations.SerializedName;

public class BraintreeTokenPOJO {

    @SerializedName("mesg")
    public String mesg;
    @SerializedName("data")
    public Data data;

    public static class Data {
        @SerializedName("token")
        public String token;
    }
}
