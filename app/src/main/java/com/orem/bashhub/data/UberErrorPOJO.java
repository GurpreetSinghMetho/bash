package com.orem.bashhub.data;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class UberErrorPOJO {

    @SerializedName("errors")
    public List<Errors> errors;
    @SerializedName("meta")
    public Meta meta;

    public static class Errors {
        @SerializedName("title")
        public String title;
        @SerializedName("code")
        public String code;
        @SerializedName("status")
        public int status;
    }

    public static class Meta {
    }
}
