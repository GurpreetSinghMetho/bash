package com.orem.bashhub.data;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class UserNamesPOJO implements Serializable {

    @SerializedName("data")
    public List<Data> data;
    @SerializedName("mesg")
    public String mesg;

    public static class Data implements Serializable {
        @SerializedName("name")
        public String name;
    }
}
