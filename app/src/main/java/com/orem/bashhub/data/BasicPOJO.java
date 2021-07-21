package com.orem.bashhub.data;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Dawinder on 13/02/2018.
 */

public class BasicPOJO {

    @SerializedName("mesg")
    public String mesg;
    @SerializedName("data")
    public Data data;

    public static class Data {
        @SerializedName("id")
        public String id;
        @SerializedName("response")
        public String response;
        @SerializedName("otp")
        public String otp;
        @SerializedName("is_registered")
        public String is_registered;
        @SerializedName("token")
        public String token;
        @SerializedName("count")
        public String count;
        @SerializedName("wallet")
        public String wallet;
    }
}
