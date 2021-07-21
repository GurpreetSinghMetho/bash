package com.orem.bashhub.data;

import com.google.gson.annotations.SerializedName;

public class SingleBashPOJO {

    @SerializedName("mesg")
    public String mesg;
    @SerializedName("data")
    public BashDetailsPOJO data;
}
