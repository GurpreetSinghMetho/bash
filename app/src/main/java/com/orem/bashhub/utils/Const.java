package com.orem.bashhub.utils;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.gson.Gson;
import com.orem.bashhub.R;
import com.orem.bashhub.data.BashListPOJO;
import com.orem.bashhub.data.BashUsersPOJO;
import com.orem.bashhub.data.BasicPOJO;
import com.orem.bashhub.data.BraintreeTokenPOJO;
import com.orem.bashhub.data.CalendarBashPOJO;
import com.orem.bashhub.data.CheckInPOJO;
import com.orem.bashhub.data.CrashBashPOJO;
import com.orem.bashhub.data.FacePOJO;
import com.orem.bashhub.data.FaceUrlPOJO;
import com.orem.bashhub.data.FbFriendsLocPOJO;
import com.orem.bashhub.data.FbFriendsPOJO;
import com.orem.bashhub.data.HostHubDetailPOJO;
import com.orem.bashhub.data.HostHubPOJO;
import com.orem.bashhub.data.MyBashPOJO;
import com.orem.bashhub.data.NotificationPOJO;
import com.orem.bashhub.data.OtherUserPOJO;
import com.orem.bashhub.data.PayMethodPOJO;
import com.orem.bashhub.data.SearchUserPOJO;
import com.orem.bashhub.data.SignUpPOJO;
import com.orem.bashhub.data.SingleBashPOJO;
import com.orem.bashhub.data.SingleVenueDetails;
import com.orem.bashhub.data.UberEstimatePOJO;
import com.orem.bashhub.data.UberProductsPOJO;
import com.orem.bashhub.data.UberReceiptPOJO;
import com.orem.bashhub.data.UberRequestPOJO;
import com.orem.bashhub.data.UserNamesPOJO;
import com.orem.bashhub.data.UserPOJO;
import com.orem.bashhub.data.UsersListPOJO;
import com.orem.bashhub.data.VenueListPOJO;
import com.orem.bashhub.databinding.DialogCustomConfirmationBinding;
import com.orem.bashhub.fragment.SafeRideFragment;
import com.orem.bashhub.fragment.UberRequestFragment;
import com.orem.bashhub.utils.apiinterface.RetrofitClient;
import com.uber.sdk.android.core.auth.AccessTokenManager;
import com.uber.sdk.android.core.auth.LoginManager;
import com.uber.sdk.core.auth.Scope;
import com.uber.sdk.core.client.SessionConfiguration;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import javax.inject.Singleton;

import androidx.appcompat.app.AlertDialog;
import androidx.databinding.DataBindingUtil;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;

@SuppressWarnings("ALL")
@Singleton
public class Const {
//    https://apis.bashparty.app/apis/api/api_social_login
// social_id=2258375764452872&social_type=1&timezone=Asia%2FKolkata

    public static final String NOTIFICATION_COUNT = "notification_count_app";
    public static final String NOTI_BARTENDER_RATING = "101";
    public static int DEFAULT_MAX_IMAGES = 5;

    //<-------------------------------------------------------------------------------->\\
    //Basic constants
    //<-------------------------------------------------------------------------------->\\
    public static String HEADER_KEY = "Authorization";
    public static String UBER_BASE_URl = "https://sandbox-api.uber.com/v1.2/";
    public static String SPOTIFY_BASE_URl = "https://api.spotify.com/v1/";
    public static String UBER_CLIENT_ID = "cG5D5woUGc_8mb2d3HyoIzJAW2DRYDLF";
    public static String UBER_REDIRECT_URL = "com.orem.bashhub.uberauth://redirect";
    //    public static String BRAINTREE_AUTH_KEY = "sandbox_jy49xfym_t8wv7sd2vz2mk9bj";
    public static String BRAINTREE_AUTH_KEY = "production_pgqxg8gw_mvtf4n87ff53nwcp";
    public static String GOOGLE_MERCHANT_ID = "18100801083170515741";
    public static String SUCCESS = "1";
    public static int SPLASH_TIME = 2000;
    public static String ERROR_NO_DATA = "1";
    public static String ERROR_NO_INTERNET = "2";
    public static String ERROR_SOME_WRONG = "3";
    public static String LANGUAGE_ENGLISH = "en";
    public static String DEFAULT_CURRENCY = "$";
    public static String APP_WEB_LINK = "https://www.bashparty.app/apis/";
    public static int PASSWORD_LIMIT = 8;
    public static int NUMBER_LIMIT = 7;
    public static String ZERO = "0";
    public static String ONE = "1";
    public static String TWO = "2";
    public static String SOCIAL_FACEBOOOK = "1";
    public static String SOCIAL_SNAPCHAT = "2";
    public static String DEFAULT_CODE = "+1";
    public static String MALE = "1";
    public static String FEMALE = "2";
    public static String NOT_SPECIFIED = "3";
    public static String EVENT_RESTAURANT = "1";
    public static String EVENT_CLUB = "2";
    public static String EVENT_BAR = "3";
    public static String POOL_PARTY = "Pool Party";
    public static String QUEER_FRIENDLY = "Queer Friendly";
    public static String DESI_PARTY = "Desi Party";
    public static String GONE_COUNTRY = "Gone Country";
    public static String KARAOKE_TIME = "Karaoke Time";
    public static String VIRAL = "Viral Events";
    public static String PAY_CARD = "1";
    public static String PAY_VENMO = "2";
    public static String PAY_GOOGLE = "3";
    public static String PAY_WALLET = "5";
    public static String EVENT_HOSTED = "hosted";
    public static String EVENT_ATTENDED = "attendant";
    public static String USER_FOLLOWERS = "followes";
    public static String USER_FOLLOWING = "following";
    public static String COLOR_EYE = "1";
    public static String COLOR_EYE_BROW = "2";
    public static String COLOR_FACE = "3";
    public static String NO_GLASSES = "NoGlasses";
    public static String GENDER_MALE = "male";
    public static double BEARD_VALUE = 0.2;
    public static String HAIR_BROWN = "brown";
    public static String HAIR_BLOND = "blond";
    public static String HAIR_BLACK = "black";
    public static String HAIR_OTHER = "other";
    public static String HAIR_GRAY = "gray";
    public static String HAIR_RED = "red";
    public static String NOTI_INVITATION = "invitation";
    public static String NOTI_WALLET = "walleta_added";
    public static String NOTI_FOLLOW = "follow";
    public static String NOTI_UPDATE_EVENT = "update_event";
    public static String NOTI_MYBASH_REMINDER = "upcoming_event";
    public static String UBER_PROCESSING = "processing";
    public static String UBER_ACCEPTED = "accepted";
    public static String UBER_ARRIVED = "arriving";
    public static String UBER_ONGOING = "in_progress";
    public static String UBER_COMPLETE = "completed";
    public static String UBER_NO_DRIVER = "no_drivers_available";
    public static String UBER_DRIVER_CANCELLED = "driver_canceled";
    public static String UBER_RIDER_CANCELLED = "rider_canceled";
    public static long LOC_UPDATE_INTERVAL = 25000;
    public static long PONG_API_INTERVAL = 25000;
    public static long ZOOM_CIRCLE = 100;
    public static String APP_HOST = "bash.com";
    public static String APP_HOST_EVENT = "event.bash.com";
    public static String QRCODE_PREFIX = "BASH";
    public static String BITMOJI_APPLINK = "https://play.google.com/store/apps/details?id=com.bitstrips.imoji";
    public static String SELECTED_LANGUAGE = "selected_language_fhduifh7rt667t";
    public static String SIGNUP_DATA = "signup_data_gdyvdgyyyyy56cvfrx6";
    public static String SUGGESTION_DATA = "suggestion_data_fkgvhbuctyr67";
    public static String SAVED_LAT = "saved_lat_djgbvhdgbydfgcsr";
    public static String SAVED_LNG = "saved_lng_dfhgxvcfbgucg7";
    public static String LOGIN_TYPE = "login_type_dhfbdurs467evrtbs";
    public static String INVITATION_USER = "invitation_user_xdfgxvdgyvc";
    public static String INVITATION_BASH_ID = "invitation_bash_id_vjgcbhgucbgt";
    public static String NOTI_TYPE = "noti_type_fhvfbgxcvgvxcg";
    public static String NOTI_ID = "noti_id_gjvucghbufgyr";
    public static String SHARE_USER = "share_user_hghghjgfhjxdfgxvdgyvc";
    public static String SHARE_BASH_ID = "share_bash_id_vjgcbfghjghjhgucbgt";
    public static String SHARE_VENUE_ID = "share_venue_id_vjgcbfghjghjhgucbgt";
    //    Notification Types
    public static String FOLLOW = "follow";
    public static String INVITE_EVENT = "invitation";
    public static String EDIT_EVENT = "update_event";
    public static String NEAR_EVENT = "near_event";
    public static String UPCOMING_EVENT = "upcoming_event";
    public static String ADMIN = "admin_notify";
    public static String ADMIN_BASH = "admin_notify_bash";
    public static String WALLET = "walleta_added";
    public static String RATE_EVENT = "cron_notify_rating";
    public static String DELETE_EVENT = "delete_event";
    public static String BUY_TICKET = "buy_ticket";
    public static String CHECK_IN = "check_in";
    public static String RATED = "rated";

    private static String MAIN_BASE = "https://www.api.bashbusiness.com/v2/";
//        private static String MAIN_BASE = "https://apis.bashparty.app/apis/";
//    private static String MAIN_BASE = "https://apis.bashparty.app/apis/dev/";

    public static String BASE_URL = MAIN_BASE + "api/";
    public static String FACE_URL = MAIN_BASE + "public/";
    public static String IMAGE_BASE = MAIN_BASE + "";
    public static String FAQ_LINK = MAIN_BASE + "help";
    public static String IMAGE_BASE_EVENT = MAIN_BASE + "public/storage/";
    private static String DEVICE_TYPE = "android";
    private static String LOGGED_IN_USER = "logged_in_user_idufhe46ufr46";
    private static String USER_TOKEN = "user_token_ydvfxydg7cyce";


    //<-------------------------------------------------------------------------------->\\
    //Logged in user data
    //<-------------------------------------------------------------------------------->\\

    public static void setLoggedInUser(Context mContext, UserPOJO.Data data) {
        new TinyDB(mContext).putString(LOGGED_IN_USER, new Gson().toJson(data));
    }

    public static UserPOJO.Data getLoggedInUser(Context mContext) {
        return new Gson().fromJson(new TinyDB(mContext).getString(LOGGED_IN_USER), UserPOJO.Data.class);
    }

    public static boolean isLoggedIn(Context mContext) {
        return (getLoggedInUser(mContext) != null);
    }

    public static String getLoggedInUserID(Context mContext) {
        if (getLoggedInUser(mContext) != null)
            return new Gson().fromJson(new TinyDB(mContext).getString(LOGGED_IN_USER), UserPOJO.Data.class).id;
        else return "";
    }

    public static void setToken(Context mContext, String token) {
        new TinyDB(mContext).putString(USER_TOKEN, token);
    }

    public static String getToken(Context mContext) {
        return new TinyDB(mContext).getString(USER_TOKEN);
    }

    public static boolean isUploadFB(Context mContext) {
        UserPOJO.Data user = new Gson().fromJson(new TinyDB(mContext).getString(LOGGED_IN_USER), UserPOJO.Data.class);
        return user.image == null || user.image.isEmpty() || user.image.startsWith("http");
    }

    //<-------------------------------------------------------------------------------->\\
    //Get basic multipart api content
    //<-------------------------------------------------------------------------------->\\

    private static RequestBody getFileRequest(String filePath) {
        return RequestBody.create(MediaType.parse("multipart/form-data"), new File(filePath));
    }

    private static RequestBody getPlaneRequest(String value) {
        return RequestBody.create(MediaType.parse("text/plain"), value);
    }

    private static String getFileKey(String key, String filePath) {
        return key + "\";filename=\"" + filePath;
    }

    //<-------------------------------------------------------------------------------->\\
    //Apis methods
    //<-------------------------------------------------------------------------------->\\

    public static Call<UserNamesPOJO> apiUserSuggestions(Context mConetxt, String text) {
        return RetrofitClient.apIs(mConetxt).requestUserSuggestions("suggest_username?name=" + text);
    }

    public static Call<BasicPOJO> apiCheckUsername(Context mConetxt, String username) {
        return RetrofitClient.apIs(mConetxt).requestBasicGET("check_username?username=" + username);
    }

    public static Call<BasicPOJO> apiSendOtp(Context mConetxt, String phone, String email, String type) {
        HashMap<String, String> map = new HashMap<>();
        map.put("phone", phone);
        map.put("email", email);
        map.put("type", type);
        map.put("country_code", DEFAULT_CODE);
        map.put("timezone", Calendar.getInstance().getTimeZone().getID());
        return RetrofitClient.apIs(mConetxt).requestBasicPost("send_otp", map);
    }

    public static Call<BasicPOJO> apiSignUp(Context mConetxt, SignUpPOJO data) {
        HashMap<String, String> map = new HashMap<>();
        map.put("name", data.getFname() + " " + data.getLname());
        map.put("email", data.getEmail());
        map.put("password", data.getPassword());
        map.put("fname", data.getFname());
        map.put("lname", data.getLname());
        map.put("dob", data.getDob());
        map.put("username", data.getUsername());
        map.put("country_code", DEFAULT_CODE);
        map.put("phone_number", data.getNumber());
        map.put("gender", data.getGender());
        map.put("social_id", data.getSocialID());
        map.put("image", data.getImage());
        map.put("timezone", Calendar.getInstance().getTimeZone().getID());
        return RetrofitClient.apIs(mConetxt).requestBasicPost("register", map);
    }

    public static Call<BasicPOJO> priceRequest(Context mConetxt, String bash_id, String id, String venue_id, String type, String message) {
        HashMap<String, String> map = new HashMap<>();
        map.put("user_id", getLoggedInUserID(mConetxt));
        map.put("id", id);
        map.put("bash_id", bash_id);
        map.put("venue_id", venue_id);
        map.put("message", message);
        map.put("type", type);
        map.put("timezone", Calendar.getInstance().getTimeZone().getID());
        return RetrofitClient.apIs(mConetxt).requestBasicPost("venue/price_request", map);
    }

    public static Call<BasicPOJO> apiWebBuyTickets(Context mConetxt, String amount, String param, String bash_id, String tickets,
                                                   String token, String type, String purchase_type, String device_data,
                                                   String other_items, String productArray, String drink_id, String bottle_id, String table_id, String section_id) {
        HashMap<String, String> map = new HashMap<>();
        map.put("user_id", getLoggedInUserID(mConetxt));
        map.put(param, bash_id);
        map.put("qty", "");
        map.put("amount", amount);
        map.put("token", token);
        map.put("type", type);
        map.put("purchase_type", purchase_type);
        map.put("other_items", other_items);
        map.put("drink_id", drink_id);
        map.put("product_id", productArray);
        map.put("bottle_id", bottle_id);
        map.put("table_id", table_id);
        map.put("section_id", section_id);
        map.put("tickets", tickets);
        map.put("device_data", device_data);
        map.put("timezone", Calendar.getInstance().getTimeZone().getID());
        return RetrofitClient.apIs(mConetxt).requestBasicPost("buy_ticket", map);
    }

    public static Call<BasicPOJO> apiTipRating(Context mConetxt, String order_id, String bartender_id, String rating, String text,
                                               String amount, String token, String type) {
        HashMap<String, String> map = new HashMap<>();
        map.put("user_id", getLoggedInUserID(mConetxt));
        map.put("order_id", order_id);
        map.put("bartender_id", bartender_id);
        map.put("rating", rating);
        map.put("text", text);
        map.put("amount", amount);
        map.put("token", token);
        map.put("type", type);
        map.put("timezone", Calendar.getInstance().getTimeZone().getID());
        return RetrofitClient.apIs(mConetxt).requestBasicPost("order/rating", map);
    }


    public static Call<BasicPOJO> apiResetPassword(Context mConetxt, String phone, String email, String password) {
        HashMap<String, String> map = new HashMap<>();
        map.put("phone", phone);
        map.put("email", email);
        map.put("password", password);
        map.put("timezone", Calendar.getInstance().getTimeZone().getID());
        return RetrofitClient.apIs(mConetxt).requestBasicPost("reset_password", map);
    }

    public static Call<BasicPOJO> apiLogin(Context mConetxt, String email, String password) {
        HashMap<String, String> map = new HashMap<>();
        map.put("email", email);
        map.put("password", password);
        map.put("timezone", Calendar.getInstance().getTimeZone().getID());
        return RetrofitClient.apIs(mConetxt).requestBasicPost("login", map);
    }

    public static Call<BasicPOJO> apiReadNotification(Context mConetxt, String notify_id) {
        return RetrofitClient.apIs(mConetxt).requestBasicGET("read_notification?timezone=" + Calendar.getInstance().getTimeZone().getID() + "&user_id=" + getLoggedInUserID(mConetxt) + "&notify_id=" + notify_id);
    }

    public static Call<BasicPOJO> apiSocialLogin(Context mConetxt, String social_id, String name, String email,
                                                 String fname, String lname, String dob, String phone_number,
                                                 String social_type) {
        HashMap<String, String> map = new HashMap<>();
        //@bash : "115963479799315"
        map.put("social_id", social_id);
        /*map.put("name", name != null ? name : "");
        map.put("email", email);
        map.put("fname", fname);
        map.put("lname", lname);
        map.put("dob", dob);
        map.put("country_code", DEFAULT_CODE);
        map.put("phone_number", phone_number);*/
        map.put("social_type", social_type);
        map.put("timezone", Calendar.getInstance().getTimeZone().getID());
        return RetrofitClient.apIs(mConetxt).requestBasicPost("api_social_login", map);
    }

    public static Call<UserPOJO> apiGetUser(Context mConetxt, String token) {
        return RetrofitClient.apIs(mConetxt).requestGetUser(token, "user?timezone=" + Calendar.getInstance().getTimeZone().getID());
    }

    public static Call<BasicPOJO> apiLogout(Context mConetxt) {
        return RetrofitClient.apIs(mConetxt).requestBasicPost("logout");
    }

    public static Call<UserPOJO> apiUpdateToken(Context mConetxt, String device_token, String image) {
        HashMap<String, String> map = new HashMap<>();
        map.put("user_id", getLoggedInUserID(mConetxt));
        map.put("image", image);
        map.put("device_token", device_token);
        map.put("device_type", DEVICE_TYPE);
        map.put("timezone", Calendar.getInstance().getTimeZone().getID());
        return RetrofitClient.apIs(mConetxt).requestUserPost("update_token", map);
    }

    public static Call<NotificationPOJO> apiNotification(Context mConetxt) {
        return RetrofitClient.apIs(mConetxt).notification("notification_list?timezone=" + Calendar.getInstance().getTimeZone().getID() + "&user_id=" + getLoggedInUserID(mConetxt));
    }

    public static Call<UserPOJO> apiUpdateProfileImage(Context mConetxt, String image) {
        HashMap<String, RequestBody> map = new HashMap<>();
        map.put("user_id", getPlaneRequest(getLoggedInUserID(mConetxt)));
        map.put(getFileKey("image", image), getFileRequest(image));
        map.put("timezone", getPlaneRequest(Calendar.getInstance().getTimeZone().getID()));
        return RetrofitClient.apIs(mConetxt).requestUserMultipart("update_token", map);
    }

    public static Call<UserPOJO> apiUpdateProfile(Context mConetxt, String fname, String lname, String username,
                                                  String email, String phone_number, String address, String gender,
                                                  String dob, String image) {
        HashMap<String, String> map = new HashMap<>();
        map.put("fname", fname);
        map.put("lname", lname);
        map.put("username", username);
        map.put("email", email);
        map.put("country_code", DEFAULT_CODE);
        map.put("phone_number", phone_number);
        map.put("address", address);
        map.put("gender", gender);
        map.put("dob", dob);
        map.put("image", image);
        map.put("timezone", Calendar.getInstance().getTimeZone().getID());
        return RetrofitClient.apIs(mConetxt).requestUserPost("update_profile", map);
    }

    public static Call<UserPOJO> apiUpdateEmailPhone(Context mConetxt, String email, String phone_number) {
        HashMap<String, String> map = new HashMap<>();
        if (!email.isEmpty()) map.put("email", email);
        if (!phone_number.isEmpty()) map.put("country_code", DEFAULT_CODE);
        if (!phone_number.isEmpty()) map.put("phone_number", phone_number);
        map.put("timezone", Calendar.getInstance().getTimeZone().getID());
        return RetrofitClient.apIs(mConetxt).requestUserPost("update_profile", map);
    }

    public static Call<UserPOJO> apiUpdateOutfit(Context mConetxt, String linked_snap, String avtar_img, String costume_id, String file) {
        HashMap<String, RequestBody> map = new HashMap<>();
        if (!avtar_img.isEmpty()) {
            map.put("avtar_img", getPlaneRequest(avtar_img));
            //map.put("image", getPlaneRequest(avtar_img));
        }
        if (!linked_snap.isEmpty()) map.put("linked_snap", getPlaneRequest(linked_snap));
        map.put("costume_id", getPlaneRequest(costume_id));
        map.put(getFileKey("standing_img", file), getFileRequest(file));
        map.put("timezone", getPlaneRequest(Calendar.getInstance().getTimeZone().getID()));
        return RetrofitClient.apIs(mConetxt).requestUserMultipart("update_profile", map);
    }

    public static Call<BasicPOJO> apiGetToken(Context mConetxt, String value, String type) {
        HashMap<String, String> map = new HashMap<>();
        map.put("value", value);
        map.put("type", type);
        map.put("timezone", Calendar.getInstance().getTimeZone().getID());
        return RetrofitClient.apIs(mConetxt).requestBasicPost("get_token", map);
    }

    public static Call<BasicPOJO> apiUpdateLocation(Context mConetxt, String latitude, String longitude) {
        HashMap<String, String> map = new HashMap<>();
        map.put("lat", latitude);
        map.put("long", longitude);
        map.put("session_id", getLoggedInUser(mConetxt).session_id);
        map.put("timezone", Calendar.getInstance().getTimeZone().getID());
        return RetrofitClient.apIs(mConetxt).requestBasicPost("ping", map);
    }

    public static Call<BasicPOJO> apiCreateBash(Context mConetxt, String spotify_user_name, String spotify_link, String spotify_images, String category, String repeat_end_date, String bash_type, String name, String name_of_host,
                                                String start_date, String end_date, String start_time, String end_time, String location,
                                                String lat, String lng, String charge, String age, String age_max, String is_private,
                                                String description, String bash_id, String hide_national_fact) {
        HashMap<String, String> map = new HashMap<>();
        map.put("user_id", getLoggedInUserID(mConetxt));
        map.put("spotify_user_name", spotify_user_name);
        map.put("spotify_images", spotify_images);
        map.put("spotify_link", spotify_link);
        map.put("category", category);
        map.put("repeat_end_date", repeat_end_date);
        map.put("bash_type", bash_type);
        map.put("name", name);
        map.put("name_of_host", name_of_host);
        map.put("start_date", start_date);
        map.put("end_date", end_date);
        map.put("start_time", start_time);
        map.put("end_time", end_time);
        map.put("location", location);
        map.put("lat", lat);
        map.put("lng", lng);
        map.put("charge", charge.isEmpty() ? ZERO : charge);
        map.put("age", age);
        map.put("age_max", age_max);
        map.put("is_private", is_private);
        map.put("description", description);
        map.put("bash_id", bash_id);
        map.put("timezone", Calendar.getInstance().getTimeZone().getID());
        map.put("hide_national_fact", hide_national_fact);
        //map.put("timezone1", Calendar.getInstance().getTimeZone().getDisplayName(false, TimeZone.SHORT));
        return RetrofitClient.apIs(mConetxt).requestBasicPost(bash_id.isEmpty() ? "create_bash" : "update_bash", map);
    }

    public static Call<BashListPOJO> apiGetBash(Context mConetxt, String date, String txt) {
        HashMap<String, String> map = new HashMap<>();
        map.put("user_id", getLoggedInUserID(mConetxt));
        map.put("lat", new TinyDB(mConetxt).getString(SAVED_LAT));
        map.put("lng", new TinyDB(mConetxt).getString(SAVED_LNG));
        map.put("date", date);
        map.put("txt", txt);
        map.put("timezone", Calendar.getInstance().getTimeZone().getID());
        return RetrofitClient.apIs(mConetxt).requestBash("bash_list", map);
    }

    public static Call<VenueListPOJO> apiGetVenue(Context mConetxt, String date, String txt, String type) {
        HashMap<String, String> map = new HashMap<>();
        map.put("user_id", getLoggedInUserID(mConetxt));
        map.put("lat", new TinyDB(mConetxt).getString(SAVED_LAT));
        map.put("lng", new TinyDB(mConetxt).getString(SAVED_LNG));
        map.put("date", date);
        map.put("text", txt);
        map.put("timezone", Calendar.getInstance().getTimeZone().getID());
        return RetrofitClient.apIs(mConetxt).requestVenue("venues?type=" + type + "&text=" + txt);
    }

    public static Call<CalendarBashPOJO> apiCalendarBash(Context mConetxt, String date, String today) {
        HashMap<String, String> map = new HashMap<>();
        map.put("user_id", getLoggedInUserID(mConetxt));
        map.put("lat", new TinyDB(mConetxt).getString(SAVED_LAT));
        map.put("lng", new TinyDB(mConetxt).getString(SAVED_LNG));
        map.put("date", date);
        map.put("today", today);
        map.put("timezone", Calendar.getInstance().getTimeZone().getID());
        return RetrofitClient.apIs(mConetxt).requestCalendarBash("calender_list", map);
    }

    public static Call<CrashBashPOJO> apiCrashBash(Context mConetxt, String bash_id) {
        HashMap<String, String> map = new HashMap<>();
        map.put("user_id", getLoggedInUserID(mConetxt));
        map.put("bash_id", bash_id);
        map.put("timezone", Calendar.getInstance().getTimeZone().getID());
        return RetrofitClient.apIs(mConetxt).requestCrashBash("crash_the_bash", map);
    }

    public static Call<BasicPOJO> email_ticket(Context mConetxt, String bash_id, String email) {
        HashMap<String, String> map = new HashMap<>();
        map.put("user_id", getLoggedInUserID(mConetxt));
        map.put("bash_id", bash_id);
        map.put("email", email);
        map.put("timezone", Calendar.getInstance().getTimeZone().getID());
        return RetrofitClient.apIs(mConetxt).requestBasicPost("email_ticket", map);
    }

    public static Call<MyBashPOJO> apiMyBash(Context mConetxt) {
        HashMap<String, String> map = new HashMap<>();
        map.put("user_id", getLoggedInUserID(mConetxt));
        map.put("timezone", Calendar.getInstance().getTimeZone().getID());
        return RetrofitClient.apIs(mConetxt).requestMyBash("get_crash_list", map);
    }

    public static Call<FbFriendsPOJO> apiFbFriends(Context mConetxt) {
        return RetrofitClient.apIs(mConetxt).requestFbFriends("users_list_home?user_id=" + getLoggedInUserID(mConetxt) +
                "&timezone=" + Calendar.getInstance().getTimeZone().getID());
    }

    public static Call<FbFriendsPOJO> apiFbFriendsContacts(Context mConetxt) {
        return RetrofitClient.apIs(mConetxt).requestFbFriends("users_contact_list_home?user_id=" + getLoggedInUserID(mConetxt) +
                "&timezone=" + Calendar.getInstance().getTimeZone().getID());
    }

    public static Call<FbFriendsLocPOJO> apiFbFriendsLoc(Context mConetxt, String session_ids) {
        return RetrofitClient.apIs(mConetxt).requestFbFriendsLoc("get_location_by_session_id?session_ids=" + session_ids +
                "&timezone=" + Calendar.getInstance().getTimeZone().getID());
    }

    public static Call<UsersListPOJO> apiUsersList(Context mConetxt) {
        return RetrofitClient.apIs(mConetxt).requestUserList("users_list?user_id=" + getLoggedInUserID(mConetxt) +
                "&timezone=" + Calendar.getInstance().getTimeZone().getID());
    }

    public static Call<UsersListPOJO> apiFollowFollowing(Context mConetxt) {
        return RetrofitClient.apIs(mConetxt).requestUserList("user_follow_both?user_id=" + getLoggedInUserID(mConetxt) +
                "&timezone=" + Calendar.getInstance().getTimeZone().getID());
    }

    public static Call<UsersListPOJO> apiSearchUserAll(Context mConetxt, String search) {
        return RetrofitClient.apIs(mConetxt).requestUserList("search_users?user_id=" + getLoggedInUserID(mConetxt) +
                "&search=" + search + "&timezone=" + Calendar.getInstance().getTimeZone().getID());
    }

    public static Call<OtherUserPOJO> apiOtherUser(Context mConetxt, String other_id) {
        return RetrofitClient.apIs(mConetxt).requestOtherUser("get_user_data?user_id=" + getLoggedInUserID(mConetxt) +
                "&other_id=" + other_id + "&timezone=" + Calendar.getInstance().getTimeZone().getID());
    }

    public static Call<OtherUserPOJO> apiFollowUser(Context mConetxt, String other_id, String status) {
        HashMap<String, String> map = new HashMap<>();
        map.put("user_id", getLoggedInUserID(mConetxt));
        map.put("other_id", other_id);
        map.put("status", status);
        map.put("timezone", Calendar.getInstance().getTimeZone().getID());
        return RetrofitClient.apIs(mConetxt).requestOtherUser("follow", map);
    }

    public static Call<UsersListPOJO> apiGetFollowers(Context mConetxt, String bash_id) {
        return RetrofitClient.apIs(mConetxt).requestUserList("followers_list?user_id=" + getLoggedInUserID(mConetxt) +
                "&bash_id=" + bash_id + "&timezone=" + Calendar.getInstance().getTimeZone().getID());
    }

    public static Call<BasicPOJO> apiSendInvitations(Context mConetxt, String user_id, String bash_id) {
        HashMap<String, String> map = new HashMap<>();
        map.put("user_id", user_id);
        map.put("bash_id", bash_id);
        map.put("timezone", Calendar.getInstance().getTimeZone().getID());
        return RetrofitClient.apIs(mConetxt).requestBasicPost("add_to_bash", map);
    }

    public static Call<BasicPOJO> apiRejectInvitation(Context mConetxt, String user_id, String notify_id) {
        HashMap<String, String> map = new HashMap<>();
        map.put("user_id", user_id);
        map.put("notify_id", notify_id);
        map.put("timezone", Calendar.getInstance().getTimeZone().getID());
        return RetrofitClient.apIs(mConetxt).requestBasicGET("reject_invitation?timezone=" + Calendar.getInstance().getTimeZone().getID() + "&user_id=" + user_id + "&notify_id=" + notify_id);
    }

    public static Call<UserPOJO> apiUpdatePreferences(Context mConetxt, String bash_type, String distance) {
        HashMap<String, String> map = new HashMap<>();
        map.put("user_id", getLoggedInUserID(mConetxt));
        map.put("bash_type", bash_type);
        map.put("distance", distance);
        map.put("timezone", Calendar.getInstance().getTimeZone().getID());
        return RetrofitClient.apIs(mConetxt).requestUserPost("update_prefrence", map);
    }

    public static Call<BashListPOJO> apiBashSearch(Context mConetxt) {
        return RetrofitClient.apIs(mConetxt).requestBash("near_by?user_id=" + getLoggedInUserID(mConetxt) +
                "&lat=" + new TinyDB(mConetxt).getString(SAVED_LAT) + "&lng=" + new TinyDB(mConetxt).getString(SAVED_LNG) +
                "&timezone=" + Calendar.getInstance().getTimeZone().getID());
    }

    public static Call<BashListPOJO> apiBashSearchAll(Context mConetxt, String search, String category) {
        return RetrofitClient.apIs(mConetxt).requestBash("search_bash?search=" + search + "&category=" + category + "&user_id=" + getLoggedInUserID(mConetxt) +
                "&lat=" + new TinyDB(mConetxt).getString(SAVED_LAT) + "&lng=" + new TinyDB(mConetxt).getString(SAVED_LNG) +
                "&timezone=" + Calendar.getInstance().getTimeZone().getID());
    }

    public static Call<CheckInPOJO> apiCheckInList(Context mConetxt) {
        return RetrofitClient.apIs(mConetxt).requestCheckInList("get_check_in_list?user_id=" + getLoggedInUserID(mConetxt) +
                "&timezone=" + Calendar.getInstance().getTimeZone().getID());
    }

    public static Call<BasicPOJO> apiBuyTickets(Context mConetxt, String tickets, String bash_id, String qty, String amount, String token,
                                                String type, String device_data) {
        HashMap<String, String> map = new HashMap<>();
        map.put("user_id", getLoggedInUserID(mConetxt));
        map.put("bash_id", bash_id);
        map.put("qty", qty);
        map.put("amount", amount);
        map.put("token", token);
        map.put("purchase_type", "1");

        map.put("tickets", tickets);
        map.put("type", type);
        map.put("device_data", device_data);
        map.put("timezone", Calendar.getInstance().getTimeZone().getID());
        return RetrofitClient.apIs(mConetxt).requestBasicPost("buy_ticket", map);
    }

    public static Call<BashUsersPOJO> apiBashUsers(Context mConetxt, String bash_id) {
        return RetrofitClient.apIs(mConetxt).requestBashUsers("bash_users_list?bash_id=" + bash_id +
                "&timezone=" + Calendar.getInstance().getTimeZone().getID());
    }

    public static Call<BasicPOJO> apiScanTicket(Context mConetxt, String bash_id, String code) {
        HashMap<String, String> map = new HashMap<>();
        map.put("user_id", getLoggedInUserID(mConetxt));
        map.put("code", code);
        map.put("bash_id", bash_id);
        map.put("timezone", Calendar.getInstance().getTimeZone().getID());
        return RetrofitClient.apIs(mConetxt).requestBasicPost("barcode_check_in", map);
    }

    public static Call<BasicPOJO> apiDeleteEvent(Context mConetxt, String bash_id) {
        return RetrofitClient.apIs(mConetxt).requestBasicGET("delete_bash?bash_id=" + bash_id +
                "&user_id=" + getLoggedInUserID(mConetxt) + "&timezone=" + Calendar.getInstance().getTimeZone().getID());
    }

    public static Call<BasicPOJO> apiFreeCheckIn(Context mConetxt, String bash_id, String skip) {
        HashMap<String, String> map = new HashMap<>();
        map.put("user_id", getLoggedInUserID(mConetxt));
        map.put("bash_id", bash_id);
        map.put("skip", skip);
        map.put("timezone", Calendar.getInstance().getTimeZone().getID());
        return RetrofitClient.apIs(mConetxt).requestBasicPost("free_check_in", map);
    }

    public static Call<BasicPOJO> apiRating(Context mConetxt, String bash_id, String skip, String rating, String complement) {
        HashMap<String, String> map = new HashMap<>();
        map.put("user_id", getLoggedInUserID(mConetxt));
        map.put("bash_id", bash_id);
        map.put("skip", skip);
        map.put("rating", rating);
        map.put("complement", complement);
        map.put("timezone", Calendar.getInstance().getTimeZone().getID());
        return RetrofitClient.apIs(mConetxt).requestBasicPost("give_rate", map);
    }

    public static Call<SearchUserPOJO> apiSearchUser(Context mConetxt, String text) {
        return RetrofitClient.apIs(mConetxt).requestSearchUser("search?user_id=" + getLoggedInUserID(mConetxt) +
                "&text=" + text + "&timezone=" + Calendar.getInstance().getTimeZone().getID());
    }

    public static Call<UserPOJO> apiSaveSafeRide(Context mConetxt, String users, String location, String lat, String lng, String notify) {
        HashMap<String, String> map = new HashMap<>();
        map.put("user_id", getLoggedInUserID(mConetxt));
        map.put("users", users);
        map.put("location", location);
        map.put("lat", lat);
        map.put("lng", lng);
        map.put("notify", notify);
        map.put("timezone", Calendar.getInstance().getTimeZone().getID());
        return RetrofitClient.apIs(mConetxt).requestUserPost("add_ride", map);
    }

    public static Call<BasicPOJO> apiNotifyUser(Context mConetxt, String message, String request_id) {
        HashMap<String, String> map = new HashMap<>();
        map.put("user_id", getLoggedInUserID(mConetxt));
        map.put("message", message);
        map.put("request_id", request_id);
        map.put("timezone", Calendar.getInstance().getTimeZone().getID());
        return RetrofitClient.apIs(mConetxt).requestBasicPost("notify_ride", map);
    }

    public static Call<BraintreeTokenPOJO> apiBraintreeToken(Context mConetxt) {
        return RetrofitClient.apIs(mConetxt).requestBraintreeToken("generateToken");
    }

    public static Call<UserPOJO> apiUser(Context mConetxt) {
        return RetrofitClient.apIs(mConetxt).requestUserGet("user");
    }

    public static Call<BasicPOJO> apiWithdraw(Context mConetxt, String email) {
        HashMap<String, String> map = new HashMap<>();
        map.put("user_id", getLoggedInUserID(mConetxt));
        map.put("email", email);
        map.put("timezone", Calendar.getInstance().getTimeZone().getID());
        return RetrofitClient.apIs(mConetxt).requestBasicPost("withdraw_money", map);
    }

    public static Call<SingleBashPOJO> apiSingleBash(Context mConetxt, String bash_id) {
        return RetrofitClient.apIs(mConetxt).requestSingleBash("get_bash_details?bash_id=" + bash_id
                + "&timezone=" + Calendar.getInstance().getTimeZone().getID());
    }

    public static Call<SingleVenueDetails> apiSingleVenu(Context mConetxt, String bash_id) {
        return RetrofitClient.apIs(mConetxt).requestSingleVenue("get_venue_details?venue_id=" + bash_id
                + "&timezone=" + Calendar.getInstance().getTimeZone().getID());
    }

    public static Call<UserPOJO> apiUpdatePaypal(Context mConetxt, String paypal_id, String paypal_name) {
        HashMap<String, String> map = new HashMap<>();
        map.put("paypal_id", paypal_id);
        map.put("paypal_name", paypal_name);
        map.put("timezone", Calendar.getInstance().getTimeZone().getID());
        return RetrofitClient.apIs(mConetxt).requestUserPost("update_paypal", map);
    }

    public static Call<UsersListPOJO> apiSyncContacts(Context mConetxt, String contacts) {
        HashMap<String, String> map = new HashMap<>();
        map.put("user_id", getLoggedInUserID(mConetxt));
        map.put("phone", contacts);
        map.put("timezone", Calendar.getInstance().getTimeZone().getID());
        return RetrofitClient.apIs(mConetxt).requestUserListPOST("users_contact_list", map);
    }

    public static Call<BasicPOJO> apiSubmitReport(Context mConetxt, String subject, String issue, String attachment) {
        HashMap<String, RequestBody> map = new HashMap<>();
        map.put("user_id", getPlaneRequest(getLoggedInUserID(mConetxt)));
        map.put("subject", getPlaneRequest(subject));
        map.put("issue", getPlaneRequest(issue));
        map.put("timezone", getPlaneRequest(Calendar.getInstance().getTimeZone().getID()));
        if (!attachment.isEmpty())
            map.put(getFileKey("attachment", attachment), getFileRequest(attachment));
        return RetrofitClient.apIs(mConetxt).requestBasicMultipart("submit_report", map);
    }

    public static Call<HostHubPOJO> apiHostHub(Context mConetxt, String date) {
        return RetrofitClient.apIs(mConetxt).requestHostHub("weekly_payment_details?user_id=" + getLoggedInUserID(mConetxt) + "&date=" + date
                + "&timezone=" + Calendar.getInstance().getTimeZone().getID());
    }

    public static Call<HostHubDetailPOJO> apiHostHubDetail(Context mConetxt, String bash_id) {
        return RetrofitClient.apIs(mConetxt).requestHostHubDetail("weekly_bash_details?bash_id=" + bash_id
                + "&timezone=" + Calendar.getInstance().getTimeZone().getID());
    }

    public static Call<FacePOJO> apiFaceAttr(Context mConetxt, String file) {
        HashMap<String, RequestBody> map = new HashMap<>();
        map.put(getFileKey("image", file), getFileRequest(file));
        map.put("timezone", getPlaneRequest(Calendar.getInstance().getTimeZone().getID()));
        return RetrofitClient.faceApIs(mConetxt).requestFaceAttr("deduct_face.php", map);
    }

    public static Call<FaceUrlPOJO> apiFaceUrl(Context mConetxt, String face_color, String eye_color, String eyebrow_color, String beared, String gender) {
        HashMap<String, String> map = new HashMap<>();
        map.put("face_color", face_color);
        map.put("eye_color", eye_color);
        map.put("eyebrow_color", eyebrow_color);
        map.put("beared", beared);
        map.put("gender", gender);
        map.put("timezone", Calendar.getInstance().getTimeZone().getID());
        return RetrofitClient.apIs(mConetxt).requestFaceUrl("face_match", map);
    }

    public static Call<UsersListPOJO> apiLocationUser(Context mConetxt) {
        return RetrofitClient.apIs(mConetxt).requestUserList("followed_user_list?user_id=" + getLoggedInUserID(mConetxt)
                + "&timezone=" + Calendar.getInstance().getTimeZone().getID());
    }

    public static Call<UserPOJO> apiUpdateSetting(Context mConetxt, String location, String block_users) {
        HashMap<String, String> map = new HashMap<>();
        map.put("user_id", getLoggedInUserID(mConetxt));
        map.put("location", location);
        map.put("block_users", block_users);
        map.put("timezone", Calendar.getInstance().getTimeZone().getID());
        return RetrofitClient.apIs(mConetxt).requestUserPost("update_location", map);
    }

    public static Call<UsersListPOJO> apiBashUsersList(Context mConetxt, String bash_id) {
        return RetrofitClient.apIs(mConetxt).requestUserList("bash_detailed_data?user_id=" + getLoggedInUserID(mConetxt) +
                "&bash_id=" + bash_id + "&timezone=" + Calendar.getInstance().getTimeZone().getID());
    }

    public static Call<UsersListPOJO> apiProfileUsersList(Context mConetxt, String type) {
        return RetrofitClient.apIs(mConetxt).requestUserList("followes_following_user?user_id=" + getLoggedInUserID(mConetxt) +
                "&type=" + type + "&timezone=" + Calendar.getInstance().getTimeZone().getID());
    }

    public static Call<BashListPOJO> apiProfileEventsList(Context mConetxt, String type) {
        return RetrofitClient.apIs(mConetxt).requestBash("hosted_attendant_bash?user_id=" + getLoggedInUserID(mConetxt) +
                "&type=" + type + "&timezone=" + Calendar.getInstance().getTimeZone().getID());
    }

    //Uber apis
    public static Call<UberProductsPOJO> apiUberProducts(Context mConetxt, String token, String latitude, String longitude) {
        return RetrofitClient.uberApIs(token).requestUberProducts("products?latitude=" +
                latitude + "&longitude=" + longitude);
    }

    public static Call<UberEstimatePOJO> apiUberEstimates(Context mConetxt, String token, String start_latitude, String start_longitude,
                                                          String end_latitude, String end_longitude) {
        HashMap<String, String> map = new HashMap<>();
        map.put("start_latitude", start_latitude);
        map.put("start_longitude", start_longitude);
        map.put("end_latitude", end_latitude);
        map.put("end_longitude", end_longitude);
        return RetrofitClient.uberApIs(token).requestUberEstimates("requests/estimate", map);
    }

    public static Call<UberRequestPOJO> apiUberMakeRequest(Context mConetxt, String token, String start_latitude, String start_longitude,
                                                           String end_latitude, String end_longitude, String product_id,
                                                           String fare_id) {
        HashMap<String, String> map = new HashMap<>();
        map.put("start_latitude", start_latitude);
        map.put("start_longitude", start_longitude);
        map.put("end_latitude", end_latitude);
        map.put("end_longitude", end_longitude);
        map.put("product_id", product_id);
        map.put("fare_id", fare_id);
        return RetrofitClient.uberApIs(token).requestUberRequest("requests", map);
    }

    public static Call<UberRequestPOJO> apiUberCheckRequest(Context mConetxt, String token) {
        return RetrofitClient.uberApIs(token).requestUberCurrentReq("requests/current");
    }

    public static Call<UberRequestPOJO> apiUberCurrentRequest(Context mConetxt, String token, String request_id) {
        return RetrofitClient.uberApIs(token).requestUberCurrentReq("requests/" + request_id);
    }

    public static Call<UberReceiptPOJO> apiUberReceipt(Context mConetxt, String token, String request_id) {
        return RetrofitClient.uberApIs(token).requestUberReceipt("requests/" + request_id + "/receipt");
    }

    //<-------------------------------------------------------------------------------->\\
    //Other methods
    //<-------------------------------------------------------------------------------->\\

    //Not Used
    public static BitmapDescriptor getDefaultBitmap(Context mContext) {
        BitmapDrawable bitmap = (BitmapDrawable) mContext.getResources().getDrawable(R.drawable.ic_avatar_placeholder);
        return BitmapDescriptorFactory.fromBitmap(Bitmap.createScaledBitmap(bitmap.getBitmap(),
                (int) (mContext.getResources().getDimension(R.dimen._70sdp)), (int) mContext.getResources().getDimension(R.dimen._70sdp), false));
    }

//    public static Bitmap overlay(Context mContext, Bitmap bmp1, Bitmap bmp2, String type) {
//        Bitmap bmOverlay = Bitmap.createBitmap(bmp1.getWidth(), bmp1.getHeight(), bmp1.getConfig());
//        Canvas canvas = new Canvas(bmOverlay);
//        canvas.drawBitmap(bmp1, new Matrix(), null);
//        float density = mContext.getResources().getDisplayMetrics().density;
////        Toast.makeText(mContext, ""+density, Toast.LENGTH_SHORT).show();
//        if (type.equals(EVENT_BAR))
//            canvas.drawBitmap(bmp2, (int) (mContext.getResources().getDimension(R.dimen._40sdp)), 0, null);
//        else {
//            if (density < 3) {
//                canvas.drawBitmap(bmp2, (int) (mContext.getResources().getDimension(R.dimen._40sdp)), 0, null);
//            } else {
//                canvas.drawBitmap(bmp2, (int) (mContext.getResources().getDimension(R.dimen._80sdp)), 0, null);
//            }
//        }
//        return bmOverlay;
//    }

    //    public static BitmapDescriptor getBashPin(Context mContext, String type, String category) {
//
//
//
//        BitmapDrawable bitmapMy = (BitmapDrawable) mContext.getResources().getDrawable(type.equals(EVENT_RESTAURANT) ? R.drawable.ic_restaurant_selected :
//                (type.equals(EVENT_CLUB) ? R.drawable.ic_club_selected : R.drawable.ic_bar_selected));
//
//        BitmapDrawable bitmapCategory = (BitmapDrawable) mContext.getResources().getDrawable(category.equals(Const.POOL_PARTY) ? R.drawable.bikini : category.equals(Const.QUEER_FRIENDLY) ? R.drawable.gay : category.equals(Const.DESI_PARTY) ? R.drawable.flag : category.equals(Const.GONE_COUNTRY) ? R.drawable.cowboy : (category.equals(Const.KARAOKE_TIME) ? R.drawable.mic_white : R.drawable.transparent));
//        Bitmap newBitmp;
//        if (category.equals(Const.POOL_PARTY) || category.equals(Const.GONE_COUNTRY))
//            newBitmp = Bitmap.createScaledBitmap(bitmapCategory.getBitmap(), (int) (mContext.getResources().getDimension(R.dimen._90sdp)), (int) mContext.getResources().getDimension(R.dimen._90sdp), false);
//        else
//            newBitmp = Bitmap.createScaledBitmap(bitmapCategory.getBitmap(), (int) (mContext.getResources().getDimension(R.dimen._70sdp)), (int) mContext.getResources().getDimension(R.dimen._70sdp), false);
//        Bitmap icon = overlay(mContext, bitmapMy.getBitmap(), newBitmp, type);
//        if (type.equals(EVENT_BAR))
//            return BitmapDescriptorFactory.fromBitmap(Bitmap.createScaledBitmap(icon,
//                    (int) (mContext.getResources().getDimension(R.dimen._30sdp)),
//                    (int) mContext.getResources().getDimension(R.dimen._30sdp), false));
//        else if (type.equals(EVENT_CLUB))
//            return BitmapDescriptorFactory.fromBitmap(Bitmap.createScaledBitmap(icon,
//                    (int) (mContext.getResources().getDimension(R.dimen._30sdp)),
//                    (int) mContext.getResources().getDimension(R.dimen._25sdp), false));
//        else
//            return BitmapDescriptorFactory.fromBitmap(Bitmap.createScaledBitmap(icon,
//                    (int) (mContext.getResources().getDimension(R.dimen._25sdp)),
//                    (int) mContext.getResources().getDimension(R.dimen._25sdp), false));
//    }
    public static BitmapDescriptor getBashPin(Context mContext, String type, String category) {

        // Creating a new RelativeLayout
        RelativeLayout relativeLayout = new RelativeLayout(mContext);
        LayoutInflater mInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        //Inflate the layout into a view and configure it the way you like
        View view = mInflater.inflate(R.layout.bash_pin, relativeLayout, true);
        ImageView event = view.findViewById(R.id.mEvent);
        ImageView eventCategory = view.findViewById(R.id.mEventCategory);

        event.setImageDrawable(mContext.getResources().getDrawable(type.equals(EVENT_RESTAURANT) ? R.drawable.ic_restaurant_selected :
                (type.equals(EVENT_CLUB) ? R.drawable.ic_club_selected : R.drawable.ic_bar_selected)));
        eventCategory.setImageDrawable(mContext.getResources().getDrawable(category.equals(Const.VIRAL) ? R.drawable.viral : category.equals(Const.POOL_PARTY) ? R.drawable.bikini : category.equals(Const.QUEER_FRIENDLY) ? R.drawable.gay : category.equals(Const.DESI_PARTY) ? R.drawable.flag : category.equals(Const.GONE_COUNTRY) ? R.drawable.cowboy : category.equals(Const.VIRAL) ? R.drawable.viral : (category.equals(Const.KARAOKE_TIME) ? R.drawable.mic_white : R.drawable.transparent)));

        relativeLayout.measure(View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED),
                View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));

        //Assign a size and position to the view and all of its descendants
        relativeLayout.layout(0, 0, relativeLayout.getMeasuredWidth(), relativeLayout.getMeasuredHeight());

        // Adding the TextView to the RelativeLayout as a child
        relativeLayout.setDrawingCacheEnabled(true);
        relativeLayout.buildDrawingCache();
        //Create the bitmap
        Bitmap bitmap = Bitmap.createBitmap(relativeLayout.getMeasuredWidth(),
                relativeLayout.getMeasuredHeight(),
                Bitmap.Config.ARGB_8888);
        //Create a canvas with the specified bitmap to draw into
        Canvas c = new Canvas(bitmap);

        //Render this view (and all of its children) to the given Canvas
        relativeLayout.draw(c);
        Bitmap icon = relativeLayout.getDrawingCache();
        return BitmapDescriptorFactory.fromBitmap(icon);
    }

    public static BitmapDescriptor getVenuePin(Context mContext, String pin) {

        // Creating a new RelativeLayout
        RelativeLayout relativeLayout = new RelativeLayout(mContext);
        LayoutInflater mInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        //Inflate the layout into a view and configure it the way you like
        View view = mInflater.inflate(R.layout.bash_pin, relativeLayout, true);
        ImageView event = view.findViewById(R.id.mEvent);
//        ImageView eventCategory = view.findViewById(R.id.mEventCategory);
        Glide.with(mContext).load(Const.IMAGE_BASE_EVENT + pin).into(event);

        Log.e("venue_image", Const.IMAGE_BASE_EVENT + pin);
//        event.setImageDrawable(mContext.getResources().getDrawable(R.drawable.venue_marker));
        relativeLayout.measure(View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED),
                View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));

        //Assign a size and position to the view and all of its descendants
        relativeLayout.layout(0, 0, relativeLayout.getMeasuredWidth(), relativeLayout.getMeasuredHeight());

        // Adding the TextView to the RelativeLayout as a child
        relativeLayout.setDrawingCacheEnabled(true);
        relativeLayout.buildDrawingCache();
        //Create the bitmap
        Bitmap bitmap = Bitmap.createBitmap(relativeLayout.getMeasuredWidth(),
                relativeLayout.getMeasuredHeight(),
                Bitmap.Config.ARGB_8888);
        //Create a canvas with the specified bitmap to draw into
        Canvas c = new Canvas(bitmap);

        //Render this view (and all of its children) to the given Canvas
        relativeLayout.draw(c);
        Bitmap icon = relativeLayout.getDrawingCache();
        return BitmapDescriptorFactory.fromBitmap(icon);
    }

    public static BitmapDescriptor getUberPin(Context mContext, boolean isSource) {
        BitmapDrawable bitmap = (BitmapDrawable) mContext.getResources().getDrawable(isSource ? R.drawable.ic_marker_from_white : R.drawable.ic_marker_to_white);
        return BitmapDescriptorFactory.fromBitmap(Bitmap.createScaledBitmap(bitmap.getBitmap(),
                (int) (mContext.getResources().getDimension(R.dimen._40sdp)), (int) mContext.getResources().getDimension(R.dimen._40sdp), false));
    }

    public static BitmapDescriptor getSmallBitmapPin(Context mContext, Bitmap bitmap) {
        return BitmapDescriptorFactory.fromBitmap(Bitmap.createScaledBitmap(bitmap,
                (int) (mContext.getResources().getDimension(R.dimen._35sdp)),
                (int) mContext.getResources().getDimension(R.dimen._80sdp), false));
    }

    public static BitmapDescriptor getBigBitmapPin(Context mContext, Bitmap bitmap) {
        return BitmapDescriptorFactory.fromBitmap(Bitmap.createScaledBitmap(bitmap,
                (int) (mContext.getResources().getDimension(R.dimen._50sdp)),
                (int) mContext.getResources().getDimension(R.dimen._110sdp), false));
    }

    //Not Used
    public static BitmapDescriptor getZoomBitmapPin(Context mContext, Bitmap bitmap, float zoom) {
        return BitmapDescriptorFactory.fromBitmap(Bitmap.createScaledBitmap(bitmap,
                (int) (mContext.getResources().getDimension(R.dimen._4sdp) * zoom), (int) (mContext.getResources().getDimension(R.dimen._9sdp) * zoom), false));
    }

    public static String getCost(String value) {
        return DEFAULT_CURRENCY + Utils.getTwoDigitsAfterDecimal(value);
    }

    public static String getInviteLink(Context mContext, String bash_id) {
        return MAIN_BASE + "apps/" + Utils.convertBase64Encode(Utils.convertBase64Encode(getLoggedInUserID(mContext)) + "_" + Utils.convertBase64Encode(bash_id));
    }

    public static String getShareLink(Context mContext, String bash_id) {
        return MAIN_BASE + "eventapps/" + Utils.convertBase64Encode(Utils.convertBase64Encode(getLoggedInUserID(mContext)) + "_" + Utils.convertBase64Encode(bash_id));
    }

    public static String getvenueShareLink(Context mContext, String bash_id) {
        return MAIN_BASE + "venueapps/" + Utils.convertBase64Encode(Utils.convertBase64Encode(getLoggedInUserID(mContext)) + "_" + Utils.convertBase64Encode(bash_id));
    }

    public static List<Integer> getCostumes() {
        List<Integer> list = new ArrayList<>();
        list.add(R.drawable.ic_costumes_1);
        list.add(R.drawable.ic_costumes_2);
        list.add(R.drawable.ic_costumes_3);
        list.add(R.drawable.ic_costumes_4);
        list.add(R.drawable.ic_costumes_5);
        list.add(R.drawable.ic_costumes_6);
        list.add(R.drawable.ic_costumes_7);
        list.add(R.drawable.ic_costumes_8);
        list.add(R.drawable.ic_costumes_9);
        list.add(R.drawable.ic_costumes_10);
        list.add(R.drawable.ic_costumes_11);
        list.add(R.drawable.ic_costumes_12);
        list.add(R.drawable.ic_costumes_13);
        list.add(R.drawable.ic_costumes_14);
        list.add(R.drawable.ic_costumes_15);
        return list;
    }

    public static int getCostumeBody(int id) {
        List<Integer> list = getCostumes();
        if (id > 0 & id <= list.size()) return list.get(id - 1);
        else return list.get(0);
    }

    public static List<Integer> getComplementIcons() {
        List<Integer> list = new ArrayList<>();
        list.add(R.drawable.ic_c_music);
        list.add(R.drawable.ic_c_dance);
        list.add(R.drawable.ic_c_bashper);
        list.add(R.drawable.ic_c_drink);
        list.add(R.drawable.ic_c_happy);
        return list;
    }

    public static void showCustomMessageDialog(Context mContext, String title, String message, String pText,
                                               String nText, View.OnClickListener pListener, View.OnClickListener nListener) {
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setCancelable(false);
        LayoutInflater inflater = ((Activity) mContext).getLayoutInflater();
        DialogCustomConfirmationBinding binding = DataBindingUtil.inflate(inflater, R.layout.dialog_custom_confirmation, null, false);
        builder.setView(binding.getRoot());
        AlertDialog dialog = builder.create();
        Utils.makeDialogTransparent(dialog);
        binding.tvTitle.setVisibility(title.isEmpty() ? View.GONE : View.VISIBLE);
        binding.tvTitle.setText(title);
        binding.tvMessage.setText(message);
        binding.tvPositive.setText(pText);
        binding.tvNegative.setText(nText);
        binding.tvPositive.setOnClickListener(v -> {
            if (pListener != null)
                pListener.onClick(v);
            dialog.dismiss();
        });
        binding.tvNegative.setOnClickListener(v -> {
            if (nListener != null)
                nListener.onClick(v);
            dialog.dismiss();
        });
        try {
            dialog.show();
        } catch (Exception ignored) {

        }
    }

    public static void onUberClick(Context mContext) {
        SessionConfiguration config = new SessionConfiguration.Builder()
                .setClientId(Const.UBER_CLIENT_ID)
                .setRedirectUri(Const.UBER_REDIRECT_URL)
                .setEnvironment(SessionConfiguration.Environment.SANDBOX)
                .setScopes(Arrays.asList(Scope.PROFILE, Scope.RIDE_WIDGETS, Scope.REQUEST_RECEIPT, Scope.REQUEST))
                .build();
        AccessTokenManager accessTokenStorage = new AccessTokenManager(mContext);
        LoginManager loginManager = new LoginManager(accessTokenStorage, null, config, 10);
        Utils.goToFragment(mContext, loginManager.isAuthenticated() && Const.getLoggedInUser(mContext).safe_ride.id != null ? new UberRequestFragment() : new SafeRideFragment(), R.id.fragment_container);
    }

    public static List<PayMethodPOJO> getPayMethods(Context mContext) {
        List<PayMethodPOJO> list = new ArrayList<>();
        list.add(new PayMethodPOJO(mContext.getString(R.string.google_pay), Const.PAY_GOOGLE, R.drawable.ic_google_pay_logo_new));
        list.add(new PayMethodPOJO(mContext.getString(R.string.venmo), Const.PAY_VENMO, R.drawable.ic_venmo_logo_new));
        list.add(new PayMethodPOJO(mContext.getString(R.string.bash_cash), Const.PAY_WALLET, R.drawable.ic_bash_cash_new));
        list.add(new PayMethodPOJO(mContext.getString(R.string.card), Const.PAY_CARD, R.drawable.ic_card_logoo_new));
        return list;
    }
}