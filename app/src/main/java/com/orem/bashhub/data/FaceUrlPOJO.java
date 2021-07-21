package com.orem.bashhub.data;

import com.google.gson.annotations.SerializedName;

public class FaceUrlPOJO {

    @SerializedName("data")
    public Data data;
    @SerializedName("mesg")
    public String mesg;

    public static class Data {
        @SerializedName("update_at")
        public String update_at;
        @SerializedName("created_at")
        public String created_at;
        @SerializedName("gender")
        public int gender;
        @SerializedName("beared")
        public int beared;
        @SerializedName("eyebrow_color")
        public String eyebrow_color;
        @SerializedName("eye_color")
        public String eye_color;
        @SerializedName("face_color")
        public String face_color;
        @SerializedName("image")
        public String image;
        @SerializedName("id")
        public int id;
    }
}
