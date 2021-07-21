package com.orem.bashhub.data;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SearchUserPOJO {

    @SerializedName("data")
    public List<Data> data;
    @SerializedName("mesg")
    public String mesg;

    public static class Data {
        @SerializedName("username")
        public String username;
        @SerializedName("lname")
        public String lname;
        @SerializedName("fname")
        public String fname;
        @SerializedName("id")
        public String id;

        public String getFullName() {
            return fname + " " + lname;
        }

        @NonNull
        @Override
        public String toString() {
            return getFullName();
        }

        @Override
        public boolean equals(@Nullable Object obj) {
            return obj instanceof SearchUserPOJO.Data && ((SearchUserPOJO.Data) obj).id.equalsIgnoreCase(id);
        }
    }
}
