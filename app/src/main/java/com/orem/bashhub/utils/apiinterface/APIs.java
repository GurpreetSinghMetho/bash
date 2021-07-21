package com.orem.bashhub.utils.apiinterface;


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
import com.orem.bashhub.data.SearchUserPOJO;
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

import java.util.HashMap;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PartMap;
import retrofit2.http.Url;

public interface APIs {

    @FormUrlEncoded
    @POST
    Call<BasicPOJO> requestBasicPost(@Url String url_part, @FieldMap HashMap<String, String> map);

    @POST
    Call<BasicPOJO> requestBasicPost(@Url String url_part);

    @FormUrlEncoded
    @POST
    Call<UserPOJO> requestUserPost(@Url String url_part, @FieldMap HashMap<String, String> map);


    @GET
    Call<NotificationPOJO> notification(@Url String url_part);

    @POST
    Call<UserPOJO> requestUserPost(@Url String url_part);

    @GET
    Call<UserPOJO> requestUserGet(@Url String url_part);

    @Multipart
    @POST
    Call<UserPOJO> requestUserMultipart(@Url String url_part, @PartMap HashMap<String, RequestBody> map);

    @Multipart
    @POST
    Call<BasicPOJO> requestBasicMultipart(@Url String url_part, @PartMap HashMap<String, RequestBody> map);

    @GET
    Call<UserNamesPOJO> requestUserSuggestions(@Url String url_part);

    @GET
    Call<BasicPOJO> requestBasicGET(@Url String url_part);

    @GET
    Call<UserPOJO> requestGetUser(@Header("Authorization") String token, @Url String url_part);

    @FormUrlEncoded
    @POST
    Call<BashListPOJO> requestBash(@Url String url_part, @FieldMap HashMap<String, String> map);

    @GET
    Call<BashListPOJO> requestBash(@Url String url_part);
   @GET
    Call<VenueListPOJO> requestVenue(@Url String url_part);
   @GET
    Call<SingleVenueDetails> requestSingleVenue(@Url String url_part);

    @FormUrlEncoded
    @POST
    Call<CrashBashPOJO> requestCrashBash(@Url String url_part, @FieldMap HashMap<String, String> map);

    @FormUrlEncoded
    @POST
    Call<MyBashPOJO> requestMyBash(@Url String url_part, @FieldMap HashMap<String, String> map);

    @GET
    Call<FbFriendsPOJO> requestFbFriends(@Url String url_part);

    @GET
    Call<FbFriendsLocPOJO> requestFbFriendsLoc(@Url String url_part);

    @GET
    Call<UsersListPOJO> requestUserList(@Url String url_part);

    @FormUrlEncoded
    @POST
    Call<UsersListPOJO> requestUserListPOST(@Url String url_part, @FieldMap HashMap<String, String> map);

    @GET
    Call<OtherUserPOJO> requestOtherUser(@Url String url_part);

    @FormUrlEncoded
    @POST
    Call<OtherUserPOJO> requestOtherUser(@Url String url_part, @FieldMap HashMap<String, String> map);

    @FormUrlEncoded
    @POST
    Call<CalendarBashPOJO> requestCalendarBash(@Url String url_part, @FieldMap HashMap<String, String> map);

    @GET
    Call<CheckInPOJO> requestCheckInList(@Url String url_part);

    @GET
    Call<BashUsersPOJO> requestBashUsers(@Url String url_part);

    @GET
    Call<SearchUserPOJO> requestSearchUser(@Url String url_part);

    @POST
    Call<BraintreeTokenPOJO> requestBraintreeToken(@Url String url_part);

    @GET
    Call<SingleBashPOJO> requestSingleBash(@Url String url_part);

    @Multipart
    @POST
    Call<FacePOJO> requestFaceAttr(@Url String url_part, @PartMap HashMap<String, RequestBody> map);

    @FormUrlEncoded
    @POST
    Call<FaceUrlPOJO> requestFaceUrl(@Url String url_part, @FieldMap HashMap<String, String> map);

    //Uber api's
    @GET
    Call<UberProductsPOJO> requestUberProducts(@Url String url_part);

    @Headers({"Content-Type:application/json"})
    @POST
    Call<UberEstimatePOJO> requestUberEstimates(@Url String url_part, @Body HashMap<String, String> map);

    @Headers({"Content-Type:application/json"})
    @POST
    Call<UberRequestPOJO> requestUberRequest(@Url String url_part, @Body HashMap<String, String> map);

    @GET
    Call<UberRequestPOJO> requestUberCurrentReq(@Url String url_part);

    @GET
    Call<UberReceiptPOJO> requestUberReceipt(@Url String url_part);

    @GET
    Call<HostHubPOJO> requestHostHub(@Url String url_part);

    @GET
    Call<HostHubDetailPOJO> requestHostHubDetail(@Url String url_part);
}
