package com.orem.bashhub.data;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class BashListPOJO {

    @SerializedName("data")
    public List<BashDetailsPOJO> data;
    @SerializedName("mesg")
    public String mesg;
}
