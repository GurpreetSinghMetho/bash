package com.orem.bashhub.utils.apiinterface;

import android.annotation.SuppressLint;
import android.content.Context;

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
import com.orem.bashhub.utils.Const;
import com.orem.bashhub.utils.Utils;

import org.greenrobot.eventbus.EventBus;

import retrofit2.Call;
import retrofit2.Response;


/**
 * Created by Dawinder on 09/02/2018.
 */

@SuppressLint("NewApi")
public class Events {
    public static void showMessage(Context mContext, String message, boolean isDialog, boolean isError) {
        if (isDialog && isError)
            EventBus.getDefault().post(new Events.ErrorResult(Const.ERROR_NO_DATA, message));
        if (!isDialog && isError)
            EventBus.getDefault().post(new Events.ErrorResult(Const.ERROR_NO_DATA, message));
        if (isDialog && !isError)
            Utils.showMessageDialog(mContext, message, null);
    }

    public static class ImagePickerResult {
        String path;

        public ImagePickerResult(String path) {
            this.path = path;
        }

        public String getPath() {
            return path;
        }
    }

    public static class PermissionCheckerResult {
        boolean result;

        public PermissionCheckerResult(boolean result) {
            this.result = result;
        }

        public boolean getResult() {
            return result;
        }
    }

    public static class LocationPickerResult {
        double latitude, longitude;

        public LocationPickerResult(double latitude, double longitude) {
            this.latitude = latitude;
            this.longitude = longitude;
        }

        public double getLatitude() {
            return latitude;
        }

        public double getLongitude() {
            return longitude;
        }
    }

    public static class ErrorResult {
        String errorType, errorMessage;

        ErrorResult(String errorType, String errorMessage) {
            this.errorType = errorType;
            this.errorMessage = errorMessage;
        }

        public String getErrorType() {
            return errorType;
        }

        public String getErrorMessage() {
            return errorMessage;
        }
    }

    public static class RequestUser {
        public RequestUser(Context mContext, Call<UserPOJO> call, final boolean isDialog, final boolean isError) {
            new ServerRequest<UserPOJO>(mContext, call, isDialog, isError) {
                @Override
                public void onCompletion(Response<UserPOJO> response) {
                    EventBus.getDefault().post(new GetUserData(response.body()));
                }
            };
        }
    }

    public static class GetUserData {
        UserPOJO data;

        GetUserData(UserPOJO data) {
            this.data = data;
        }

        public UserPOJO getData() {
            return data;
        }
    }

    public static class RequestBasic {
        public RequestBasic(Context mContext, Call<BasicPOJO> call, final boolean isDialog, final boolean isError) {
            new ServerRequest<BasicPOJO>(mContext, call, isDialog, isError) {
                @Override
                public void onCompletion(Response<BasicPOJO> response) {
                    EventBus.getDefault().post(new GetBasicData(response.body()));
                }
            };
        }
    }

    public static class GetBasicData {
        BasicPOJO data;

        GetBasicData(BasicPOJO data) {
            this.data = data;
        }

        public BasicPOJO getData() {
            return data;
        }
    }

    public static class RequestLogout {
        public RequestLogout(Context mContext, Call<BasicPOJO> call, final boolean isDialog, final boolean isError) {
            new ServerRequest<BasicPOJO>(mContext, call, isDialog, isError) {
                @Override
                public void onCompletion(Response<BasicPOJO> response) {
                    EventBus.getDefault().post(new GetLogoutData(response.body()));
                }
            };
        }
    }

    public static class GetLogoutData {
        BasicPOJO data;

        GetLogoutData(BasicPOJO data) {
            this.data = data;
        }

        public BasicPOJO getData() {
            return data;
        }
    }

    public static class RequestUpdateToken {
        public RequestUpdateToken(Context mContext, Call<UserPOJO> call, final boolean isDialog, final boolean isError) {
            new ServerRequest<UserPOJO>(mContext, call, isDialog, isError) {
                @Override
                public void onCompletion(Response<UserPOJO> response) {
                    EventBus.getDefault().post(new GetUpdateTokenData(response.body()));
                }
            };
        }
    }

    public static class RequestNotification {
        public RequestNotification(Context mContext, Call<NotificationPOJO> call, final boolean isDialog, final boolean isError) {
            new ServerRequest<NotificationPOJO>(mContext, call, isDialog, isError) {
                @Override
                public void onCompletion(Response<NotificationPOJO> response) {
                    EventBus.getDefault().post(new GetNotification(response.body()));
                }
            };
        }
    }

    public static class GetUpdateTokenData {
        UserPOJO data;

        GetUpdateTokenData(UserPOJO data) {
            this.data = data;
        }

        public UserPOJO getData() {
            return data;
        }
    }

    public static class GetNotification {
        NotificationPOJO data;

        GetNotification(NotificationPOJO data) {
            this.data = data;
        }

        public NotificationPOJO getData() {
            return data;
        }
    }

    public static class RequestUserSuggestions {
        public RequestUserSuggestions(Context mContext, Call<UserNamesPOJO> call, final boolean isDialog, final boolean isError) {
            new ServerRequest<UserNamesPOJO>(mContext, call, isDialog, isError) {
                @Override
                public void onCompletion(Response<UserNamesPOJO> response) {
                    EventBus.getDefault().post(new GetUserSuggestionsData(response.body()));
                }
            };
        }
    }

    public static class GetUserSuggestionsData {
        UserNamesPOJO data;

        GetUserSuggestionsData(UserNamesPOJO data) {
            this.data = data;
        }

        public UserNamesPOJO getData() {
            return data;
        }
    }

    public static class GetEmojiData {
        String url;

        public GetEmojiData(String url) {
            this.url = url;
        }

        public String getUrl() {
            return url;
        }
    }

    public static class GetOtpCallBack {

        public GetOtpCallBack() {

        }
    }

    public static class RequestBashList {
        public RequestBashList(Context mContext, Call<BashListPOJO> call, final boolean isDialog, final boolean isError) {
            new ServerRequest<BashListPOJO>(mContext, call, isDialog, isError) {
                @Override
                public void onCompletion(Response<BashListPOJO> response) {
                    EventBus.getDefault().post(new GetBashListData(response.body()));
                }
            };
        }
    }

    public static class RequestVenueList {
        public RequestVenueList(Context mContext, Call<VenueListPOJO> call, final boolean isDialog, final boolean isError) {
            new ServerRequest<VenueListPOJO>(mContext, call, isDialog, isError) {
                @Override
                 public void onCompletion(Response<VenueListPOJO> response) {
                    EventBus.getDefault().post(new GetVenuListData(response.body()));
                }
            };
        }
    }


    public static class GetBashListData {
        BashListPOJO data;

        GetBashListData(BashListPOJO data) {
            this.data = data;
        }

        public BashListPOJO getData() {
            return data;
        }
    }

    public static class GetVenuListData {
        VenueListPOJO data;

        GetVenuListData(VenueListPOJO data) {
            this.data = data;
        }

        public VenueListPOJO getData() {
            return data;
        }
    }

    public static class RequestUpdateLocation {
        public RequestUpdateLocation(Context mContext, Call<BasicPOJO> call, final boolean isDialog, final boolean isError) {
            new ServerRequest<BasicPOJO>(mContext, call, isDialog, isError) {
                @Override
                public void onCompletion(Response<BasicPOJO> response) {
                    Utils.showLog("Location Update Success");
                }
            };
        }
    }

    public static class RequestCrashBash {
        public RequestCrashBash(Context mContext, Call<CrashBashPOJO> call, final boolean isDialog, final boolean isError) {
            new ServerRequest<CrashBashPOJO>(mContext, call, isDialog, isError) {
                @Override
                public void onCompletion(Response<CrashBashPOJO> response) {
                    EventBus.getDefault().post(new GetCrashBashData(response.body()));
                }
            };
        }
    }

    public static class GetCrashBashData {
        CrashBashPOJO data;

        GetCrashBashData(CrashBashPOJO data) {
            this.data = data;
        }

        public CrashBashPOJO getData() {
            return data;
        }
    }

    public static class RequestCrashBash1 {
        public RequestCrashBash1(Context mContext, Call<CrashBashPOJO> call, final boolean isDialog, final boolean isError) {
            new ServerRequest<CrashBashPOJO>(mContext, call, isDialog, isError) {
                @Override
                public void onCompletion(Response<CrashBashPOJO> response) {
                    EventBus.getDefault().post(new GetCrashBashData1(response.body()));
                }
            };
        }
    }

    public static class GetCrashBashData1 {
        CrashBashPOJO data;

        GetCrashBashData1(CrashBashPOJO data) {
            this.data = data;
        }

        public CrashBashPOJO getData() {
            return data;
        }
    }

    public static class RequestMyBash {
        public RequestMyBash(Context mContext, Call<MyBashPOJO> call, final boolean isDialog, final boolean isError) {
            new ServerRequest<MyBashPOJO>(mContext, call, isDialog, isError) {
                @Override
                public void onCompletion(Response<MyBashPOJO> response) {
                    EventBus.getDefault().post(new GetMyBashData(response.body()));
                }
            };
        }
    }

    public static class GetMyBashData {
        MyBashPOJO data;

        GetMyBashData(MyBashPOJO data) {
            this.data = data;
        }

        public MyBashPOJO getData() {
            return data;
        }
    }

    public static class RequestFbFriendsLoc {
        public RequestFbFriendsLoc(Context mContext, Call<FbFriendsLocPOJO> call, final boolean isDialog, final boolean isError) {
            new ServerRequest<FbFriendsLocPOJO>(mContext, call, isDialog, isError) {
                @Override
                public void onCompletion(Response<FbFriendsLocPOJO> response) {
                    EventBus.getDefault().post(new GetFbFriendsLocData(response.body()));
                }
            };
        }
    }

    public static class GetFbFriendsLocData {
        FbFriendsLocPOJO data;

        GetFbFriendsLocData(FbFriendsLocPOJO data) {
            this.data = data;
        }

        public FbFriendsLocPOJO getData() {
            return data;
        }
    }

    public static class RequestFbFriends {
        public RequestFbFriends(Context mContext, Call<FbFriendsPOJO> call, final boolean isDialog, final boolean isError) {
            new ServerRequest<FbFriendsPOJO>(mContext, call, isDialog, isError) {
                @Override
                public void onCompletion(Response<FbFriendsPOJO> response) {
                    EventBus.getDefault().post(new GetFbFriendsData(response.body()));
                }
            };
        }
    }

    public static class GetFbFriendsData {
        FbFriendsPOJO data;

        GetFbFriendsData(FbFriendsPOJO data) {
            this.data = data;
        }

        public FbFriendsPOJO getData() {
            return data;
        }
    }

    public static class RequestUsersList {
        public RequestUsersList(Context mContext, Call<UsersListPOJO> call, final boolean isDialog, final boolean isError) {
            new ServerRequest<UsersListPOJO>(mContext, call, isDialog, isError) {
                @Override
                public void onCompletion(Response<UsersListPOJO> response) {
                    EventBus.getDefault().post(new GetUsersListData(response.body()));
                }
            };
        }
    }

    public static class GetUsersListData {
        UsersListPOJO data;

        GetUsersListData(UsersListPOJO data) {
            this.data = data;
        }

        public UsersListPOJO getData() {
            return data;
        }
    }

    public static class RequestOtherUser {
        public RequestOtherUser(Context mContext, Call<OtherUserPOJO> call, final boolean isDialog, final boolean isError) {
            new ServerRequest<OtherUserPOJO>(mContext, call, isDialog, isError) {
                @Override
                public void onCompletion(Response<OtherUserPOJO> response) {
                    EventBus.getDefault().post(new GetOtherUserData(response.body()));
                }
            };
        }
    }

    public static class GetOtherUserData {
        OtherUserPOJO data;

        GetOtherUserData(OtherUserPOJO data) {
            this.data = data;
        }

        public OtherUserPOJO getData() {
            return data;
        }
    }

    public static class RequestBashSearch {
        public RequestBashSearch(Context mContext, Call<BashListPOJO> call, final boolean isDialog, final boolean isError) {
            new ServerRequest<BashListPOJO>(mContext, call, isDialog, isError) {
                @Override
                public void onCompletion(Response<BashListPOJO> response) {
                    EventBus.getDefault().post(new GetBashSearchData(response.body()));
                }
            };
        }
    }

    public static class GetBashSearchData {
        BashListPOJO data;

        GetBashSearchData(BashListPOJO data) {
            this.data = data;
        }

        public BashListPOJO getData() {
            return data;
        }
    }

    public static class RequestCalenderBash {
        public RequestCalenderBash(Context mContext, Call<CalendarBashPOJO> call, final boolean isDialog, final boolean isError) {
            new ServerRequest<CalendarBashPOJO>(mContext, call, isDialog, isError) {
                @Override
                public void onCompletion(Response<CalendarBashPOJO> response) {
                    EventBus.getDefault().post(new GetCalendarBashData(response.body()));
                }
            };
        }
    }

    public static class GetCalendarBashData {
        CalendarBashPOJO data;

        GetCalendarBashData(CalendarBashPOJO data) {
            this.data = data;
        }

        public CalendarBashPOJO getData() {
            return data;
        }
    }

    public static class RequestCheckIn {
        public RequestCheckIn(Context mContext, Call<CheckInPOJO> call, final boolean isDialog, final boolean isError) {
            new ServerRequest<CheckInPOJO>(mContext, call, isDialog, isError) {
                @Override
                public void onCompletion(Response<CheckInPOJO> response) {
                    EventBus.getDefault().post(new GetCheckInData(response.body()));
                }
            };
        }
    }

    public static class GetCheckInData {
        CheckInPOJO data;

        GetCheckInData(CheckInPOJO data) {
            this.data = data;
        }

        public CheckInPOJO getData() {
            return data;
        }
    }

    public static class RequestBashUsers {
        public RequestBashUsers(Context mContext, Call<BashUsersPOJO> call, final boolean isDialog, final boolean isError) {
            new ServerRequest<BashUsersPOJO>(mContext, call, isDialog, isError) {
                @Override
                public void onCompletion(Response<BashUsersPOJO> response) {
                    EventBus.getDefault().post(new GetBashUsersData(response.body()));
                }
            };
        }
    }

    public static class GetBashUsersData {
        BashUsersPOJO data;

        GetBashUsersData(BashUsersPOJO data) {
            this.data = data;
        }

        public BashUsersPOJO getData() {
            return data;
        }
    }

    public static class RequestSearchUser {
        public RequestSearchUser(Context mContext, Call<SearchUserPOJO> call, final boolean isDialog, final boolean isError) {
            new ServerRequest<SearchUserPOJO>(mContext, call, isDialog, isError) {
                @Override
                public void onCompletion(Response<SearchUserPOJO> response) {
                    EventBus.getDefault().post(new GetSearchUserData(response.body()));
                }
            };
        }
    }

    public static class GetSearchUserData {
        SearchUserPOJO data;

        GetSearchUserData(SearchUserPOJO data) {
            this.data = data;
        }

        public SearchUserPOJO getData() {
            return data;
        }
    }

    public static class RequestSingleBash {
        public RequestSingleBash(Context mContext, Call<SingleBashPOJO> call, final boolean isDialog, final boolean isError) {
            new ServerRequest<SingleBashPOJO>(mContext, call, isDialog, isError) {
                @Override
                public void onCompletion(Response<SingleBashPOJO> response) {
                    EventBus.getDefault().post(new GetSingleBashData(response.body()));
                }
            };
        }
    }
   public static class RequestSingleVenue {
        public RequestSingleVenue(Context mContext, Call<SingleVenueDetails> call, final boolean isDialog, final boolean isError) {
            new ServerRequest<SingleVenueDetails>(mContext, call, isDialog, isError) {
                @Override
                public void onCompletion(Response<SingleVenueDetails> response) {
                    EventBus.getDefault().post(new GetSingleVenueData(response.body()));
                }
            };
        }
    }

    public static class GetSingleBashData {
        SingleBashPOJO data;

        GetSingleBashData(SingleBashPOJO data) {
            this.data = data;
        }

        public SingleBashPOJO getData() {
            return data;
        }
    } public static class GetSingleVenueData {
        SingleVenueDetails data;

        GetSingleVenueData(SingleVenueDetails data) {
            this.data = data;
        }

        public SingleVenueDetails getData() {
            return data;
        }
    }

    public static class RequestBraintreeToken {
        public RequestBraintreeToken(Context mContext, Call<BraintreeTokenPOJO> call, final boolean isDialog, final boolean isError) {
            new ServerRequest<BraintreeTokenPOJO>(mContext, call, isDialog, isError) {
                @Override
                public void onCompletion(Response<BraintreeTokenPOJO> response) {
                    EventBus.getDefault().post(new GetBraintreeTokenData(response.body()));
                }
            };
        }
    }

    public static class GetBraintreeTokenData {
        BraintreeTokenPOJO data;

        GetBraintreeTokenData(BraintreeTokenPOJO data) {
            this.data = data;
        }

        public BraintreeTokenPOJO getData() {
            return data;
        }
    }

    public static class RequestHostHub {
        public RequestHostHub(Context mContext, Call<HostHubPOJO> call, final boolean isDialog, final boolean isError) {
            new ServerRequest<HostHubPOJO>(mContext, call, isDialog, isError) {
                @Override
                public void onCompletion(Response<HostHubPOJO> response) {
                    EventBus.getDefault().post(new GetHostHubData(response.body()));
                }
            };
        }
    }

    public static class GetHostHubData {
        HostHubPOJO data;

        GetHostHubData(HostHubPOJO data) {
            this.data = data;
        }

        public HostHubPOJO getData() {
            return data;
        }
    }

    public static class RequestHostHubDetail {
        public RequestHostHubDetail(Context mContext, Call<HostHubDetailPOJO> call, final boolean isDialog, final boolean isError) {
            new ServerRequest<HostHubDetailPOJO>(mContext, call, isDialog, isError) {
                @Override
                public void onCompletion(Response<HostHubDetailPOJO> response) {
                    EventBus.getDefault().post(new GetHostHubDetailData(response.body()));
                }
            };
        }
    }

    public static class GetHostHubDetailData {
        HostHubDetailPOJO data;

        GetHostHubDetailData(HostHubDetailPOJO data) {
            this.data = data;
        }

        public HostHubDetailPOJO getData() {
            return data;
        }
    }

    public static class RequestFaceAttr {
        public RequestFaceAttr(Context mContext, Call<FacePOJO> call, final boolean isDialog, final boolean isError) {
            new ServerRequest<FacePOJO>(mContext, call, isDialog, isError) {
                @Override
                public void onCompletion(Response<FacePOJO> response) {
                    EventBus.getDefault().post(new GetFaceAttrData(response.body()));
                }
            };
        }
    }

    public static class GetFaceAttrData {
        FacePOJO data;

        GetFaceAttrData(FacePOJO data) {
            this.data = data;
        }

        public FacePOJO getData() {
            return data;
        }
    }

    public static class RequestFaceUrl {
        public RequestFaceUrl(Context mContext, Call<FaceUrlPOJO> call, final boolean isDialog, final boolean isError) {
            new ServerRequest<FaceUrlPOJO>(mContext, call, isDialog, isError) {
                @Override
                public void onCompletion(Response<FaceUrlPOJO> response) {
                    EventBus.getDefault().post(new GetFaceUrlData(response.body()));
                }
            };
        }
    }

    public static class GetFaceUrlData {
        FaceUrlPOJO data;

        GetFaceUrlData(FaceUrlPOJO data) {
            this.data = data;
        }

        public FaceUrlPOJO getData() {
            return data;
        }
    }

    //Uber Products
    public static class RequestUberProducts {
        public RequestUberProducts(Context mContext, Call<UberProductsPOJO> call, final boolean isDialog, final boolean isError) {
            new ServerRequest<UberProductsPOJO>(mContext, call, isDialog, isError) {
                @Override
                public void onCompletion(Response<UberProductsPOJO> response) {
                    EventBus.getDefault().post(new GetUberProductsData(response.body()));
                }
            };
        }
    }

    public static class GetUberProductsData {
        UberProductsPOJO data;

        GetUberProductsData(UberProductsPOJO data) {
            this.data = data;
        }

        public UberProductsPOJO getData() {
            return data;
        }
    }

    public static class RequestUberEstimates {
        public RequestUberEstimates(Context mContext, Call<UberEstimatePOJO> call, final boolean isDialog, final boolean isError) {
            new ServerRequest<UberEstimatePOJO>(mContext, call, isDialog, isError) {
                @Override
                public void onCompletion(Response<UberEstimatePOJO> response) {
                    EventBus.getDefault().post(new GetUberEstimatesData(response.body()));
                }
            };
        }
    }

    public static class GetUberEstimatesData {
        UberEstimatePOJO data;

        GetUberEstimatesData(UberEstimatePOJO data) {
            this.data = data;
        }

        public UberEstimatePOJO getData() {
            return data;
        }
    }

    public static class RequestUberRequest {
        public RequestUberRequest(Context mContext, Call<UberRequestPOJO> call, final boolean isDialog, final boolean isError) {
            new ServerRequest<UberRequestPOJO>(mContext, call, isDialog, isError) {
                @Override
                public void onCompletion(Response<UberRequestPOJO> response) {
                    EventBus.getDefault().post(new GetUberRequestData(response.body()));
                }
            };
        }
    }

    public static class GetUberRequestData {
        UberRequestPOJO data;

        GetUberRequestData(UberRequestPOJO data) {
            this.data = data;
        }

        public UberRequestPOJO getData() {
            return data;
        }
    }

    public static class RequestUberReceipt {
        public RequestUberReceipt(Context mContext, Call<UberReceiptPOJO> call, final boolean isDialog, final boolean isError) {
            new ServerRequest<UberReceiptPOJO>(mContext, call, isDialog, isError) {
                @Override
                public void onCompletion(Response<UberReceiptPOJO> response) {
                    EventBus.getDefault().post(new GetUberReceiptData(response.body()));
                }
            };
        }
    }

    public static class GetUberReceiptData {
        UberReceiptPOJO data;

        GetUberReceiptData(UberReceiptPOJO data) {
            this.data = data;
        }

        public UberReceiptPOJO getData() {
            return data;
        }
    }

    public static class GetUberError {
        GetUberError() {

        }
    }
}
