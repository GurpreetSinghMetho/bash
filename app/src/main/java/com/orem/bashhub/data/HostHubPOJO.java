package com.orem.bashhub.data;

import android.content.Context;

import com.google.gson.annotations.SerializedName;
import com.orem.bashhub.R;
import com.orem.bashhub.utils.Const;
import com.orem.bashhub.utils.Utils;

import java.util.ArrayList;
import java.util.List;

public class HostHubPOJO {

    @SerializedName("data")
    public Data data;
    @SerializedName("mesg")
    public String mesg;

    public static class Data {
        @SerializedName("rate_data")
        public Rate_data rate_data;
        @SerializedName("attendance")
        public List<Attendance> attendance;
        @SerializedName("earning")
        public List<Earning> earning;
        @SerializedName("bash_data")
        public List<BashDetailsPOJO> bash_data;

        public String getTotalEarningText(Context mContext) {
            return String.format(mContext.getString(R.string.total_earnings), Const.DEFAULT_CURRENCY + getTotal());
        }

        public String getHighestEarningText(Context mContext) {
            return getHighestAmount() > 0 ? String.format(mContext.getString(R.string.highest_earning), getHighestAmountDate(), Const.DEFAULT_CURRENCY + getHighestAmount()) :
                    String.format(mContext.getString(R.string.highest_earning1), Const.DEFAULT_CURRENCY + 0.0);
        }

        public String getHighestAttendanceText(Context mContext) {
            return getHighestAttendance() > 0 ? String.format(mContext.getString(R.string.highest_attendance), getHighestAttendanceDate(), "" + getHighestAttendance()) :
                    String.format(mContext.getString(R.string.highest_attendance1), "0");
        }

        public double getTotal() {
            double total = 0;
            for (Earning item : earning)
                total = total + item.earings;
            return total;
        }

        public int getHighEarnPos() {
            int pos = -1;
            double amount = -1;
            for (int i = 0; i < earning.size(); i++) {
                Earning item = earning.get(i);
                if (amount < item.earings) {
                    amount = item.earings;
                    pos = i;
                }
            }
            return pos;
        }

        public double getHighestAmount() {
            double amount = 0;
            for (Earning item : earning)
                amount = amount > item.earings ? amount : item.earings;
            return amount;
        }

        private String getHighestAmountDate() {
            String date = "";
            double amount = -1;
            for (Earning item : earning)
                if (amount < item.earings) {
                    amount = item.earings;
                    date = Utils.changeDateFormatHost1(item.date);
                }
            return date;
        }

        private int getHighestAttendance() {
            double amount = 0;
            for (Attendance item : attendance)
                amount = amount > item.getTotalAttendance() ? amount : item.getTotalAttendance();
            return (int) amount;
        }

        private String getHighestAttendanceDate() {
            String date = "";
            double amount = -1;
            for (Attendance item : attendance)
                if (amount < item.getTotalAttendance()) {
                    amount = item.getTotalAttendance();
                    date = Utils.changeDateFormatHost1(item.date);
                }
            return date;
        }

        public int getHighestGraphAttendance() {
            int amount = 0;
            for (Attendance item : attendance)
                amount = amount > item.getGreater() ? amount : item.getGreater();
            return amount;
        }
    }

    public static class Rate_data {
        @SerializedName("complement5")
        public String complement5;
        @SerializedName("complement4")
        public String complement4;
        @SerializedName("complement3")
        public String complement3;
        @SerializedName("complement2")
        public String complement2;
        @SerializedName("complement1")
        public String complement1;
        @SerializedName("complement")
        public String complement;
        @SerializedName("ratings")
        public String ratings;

        public List<ComplementPOJO> getComplementCount(Context mContext) {
            List<ComplementPOJO> list = new ArrayList<>();
            if (!complement1.equals(Const.ZERO))
                list.add(new ComplementPOJO(mContext.getString(R.string.awesome_music), complement1, R.drawable.ic_c_music));
            if (!complement2.equals(Const.ZERO))
                list.add(new ComplementPOJO(mContext.getString(R.string.groovy_dancing), complement2, R.drawable.ic_c_dance));
            if (!complement3.equals(Const.ZERO))
                list.add(new ComplementPOJO(mContext.getString(R.string.perfect_BASH), complement3, R.drawable.ic_c_bashper));
            if (!complement4.equals(Const.ZERO))
                list.add(new ComplementPOJO(mContext.getString(R.string.delicious_drink), complement4, R.drawable.ic_c_drink));
            if (!complement5.equals(Const.ZERO))
                list.add(new ComplementPOJO(mContext.getString(R.string.great_atmosphere), complement5, R.drawable.ic_c_happy));
            return list;
        }

        public boolean isComplementVisible() {
            return !complement1.equals(Const.ZERO) || !complement2.equals(Const.ZERO) || !complement3.equals(Const.ZERO) ||
                    !complement4.equals(Const.ZERO) || !complement5.equals(Const.ZERO);
        }
    }

    public static class Attendance {
        @SerializedName("not_specified")
        public int not_specified;
        @SerializedName("fe_male")
        public int fe_male;
        @SerializedName("male")
        public int male;
        @SerializedName("day")
        public String day;
        @SerializedName("date")
        public String date;

        private int getTotalAttendance() {
            return not_specified + fe_male + male;
        }

        public String getMale() {
            return "" + male;
        }

        public String getFe_male() {
            return "" + fe_male;
        }

        int getGreater() {
            return male > fe_male ? male : fe_male;
        }
    }

    public static class Earning {
        @SerializedName("earings")
        public double earings;
        @SerializedName("day")
        public String day;
        @SerializedName("date")
        public String date;

        public String getEarningText() {
            return Const.DEFAULT_CURRENCY + earings;
        }
    }
}
