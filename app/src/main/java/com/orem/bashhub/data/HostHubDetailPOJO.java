package com.orem.bashhub.data;

import android.content.Context;

import com.google.gson.annotations.SerializedName;
import com.orem.bashhub.R;
import com.orem.bashhub.utils.Const;
import com.orem.bashhub.utils.Utils;

import java.util.List;

public class HostHubDetailPOJO {

    @SerializedName("data")
    public Data data;
    @SerializedName("mesg")
    public String mesg;

    public static class Data {
        @SerializedName("popular_time")
        public List<Popular_time> popular_time;
        @SerializedName("age_details")
        public List<Age_details> age_details;
        @SerializedName("total_earned_amount")
        public double total_earned_amount;
        @SerializedName("average_time")
        public String average_time;

        public String getTotalEarningText(Context mContext) {
            return String.format(mContext.getString(R.string.total_earnings_bash), Const.DEFAULT_CURRENCY + total_earned_amount);
        }

        private int totalMale() {
            int total = 0;
            for (Age_details item : age_details)
                total = total + item.male;
            return total;
        }

        public String getTotalMaleText(Context mContext) {
            return String.format(mContext.getString(R.string.total_male), "" + totalMale());
        }

        private int totalFemale() {
            int total = 0;
            for (Age_details item : age_details)
                total = total + item.fe_male;
            return total;
        }

        public String getTotalFemaleText(Context mContext) {
            return String.format(mContext.getString(R.string.total_female), "" + totalFemale());
        }

        private int totalAttendees() {
            int total = 0;
            for (Age_details item : age_details)
                total = total + item.totalAttend();
            return total;
        }

        public String getTotalAttendeesText(Context mContext) {
            return String.format(mContext.getString(R.string.total_attendees), "" + totalAttendees());
        }

        public String getAverageTimeText(Context mContext) {
            return String.format(mContext.getString(R.string.avg_time_spend), "" + average_time);
        }

        public int getHighestAgeGraphValue() {
            int amount = 0;
            for (HostHubDetailPOJO.Age_details item : age_details)
                amount = amount > item.getGreater() ? amount : item.getGreater();
            return amount;
        }

        public int getHighestTimeGraphValue() {
            int amount = 0;
            for (HostHubDetailPOJO.Popular_time item : popular_time)
                amount = amount > item.persons ? amount : item.persons;
            return amount;
        }

        public int getHighTimePos() {
            int pos = -1;
            double amount = -1;
            for (int i = 0; i < popular_time.size(); i++) {
                HostHubDetailPOJO.Popular_time item = popular_time.get(i);
                if (amount < item.persons) {
                    amount = item.persons;
                    pos = i;
                }
            }
            return pos;
        }
    }

    public static class Popular_time {
        @SerializedName("persons")
        public int persons;
        @SerializedName("end")
        public String end;
        @SerializedName("start")
        public String start;

        public String getPersons() {
            return "" + persons;
        }

        public String getTimeSlot() {
            return Utils.changeDateFormatHostTime(start, end);
        }
    }

    public static class Age_details {
        @SerializedName("not_specified")
        public int not_specified;
        @SerializedName("fe_male")
        public int fe_male;
        @SerializedName("male")
        public int male;
        @SerializedName("age")
        public String age;

        public String getMale() {
            return "" + male;
        }

        public String getFe_male() {
            return "" + fe_male;
        }

        int totalAttend() {
            return male + fe_male + not_specified;
        }

        int getGreater() {
            return male > fe_male ? male : fe_male;
        }
    }
}
