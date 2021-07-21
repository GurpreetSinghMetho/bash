package com.orem.bashhub.utils.apiinterface;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.view.Window;

import androidx.annotation.NonNull;

import com.google.gson.Gson;
import com.orem.bashhub.R;
import com.orem.bashhub.activity.BaseActivity;
import com.orem.bashhub.activity.SplashActivity;
import com.orem.bashhub.data.ErrorPOJO;
import com.orem.bashhub.data.UberErrorPOJO;
import com.orem.bashhub.utils.Const;
import com.orem.bashhub.utils.TinyDB;
import com.orem.bashhub.utils.Utils;

import org.greenrobot.eventbus.EventBus;

import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Dawinder on 11/22/2016.
 */

public abstract class ServerRequest<T> {

    ServerRequest(final Context mContext, Call<T> call, final boolean isPdShow, final boolean isError) {
        if (Utils.isNetworkConnected(mContext)) {
            final Dialog pd = getProgressDialog(mContext);
            if (isPdShow)
                pd.show();

            call.enqueue(new Callback<T>() {
                @Override
                public void onResponse(@NonNull Call<T> call, @NonNull Response<T> response) {
                    /*if (isPdShow && pd.isShowing())
                        pd.dismiss();*/
                    dismissDialog(mContext, isPdShow, pd);
                    if (response.isSuccessful()) {
                        onCompletion(response);
                    } else {
                        if (response.code() == 404 && isPdShow) {
                            String res = "";
                            try {
                                res = Objects.requireNonNull(response.errorBody()).string();
                            } catch (Exception e) {
                                Utils.showLog("Error: " + e.getMessage());
                            }
                            ErrorPOJO myItem = null;
                            UberErrorPOJO item = null;
                            if (!res.isEmpty()) {
                                item = new Gson().fromJson(res, UberErrorPOJO.class);
                                myItem = new Gson().fromJson(res, ErrorPOJO.class);
                            }
                            if (item != null && item.errors != null && item.errors.size() > 0 && item.errors.get(0).code.equals("no_current_trip")) {
                                EventBus.getDefault().post(new Events.GetUberError());
                            } else if (myItem != null) {
                                showMessage(mContext, myItem.mesg, Const.ERROR_SOME_WRONG, isPdShow, isError);
                            } else {
                                showMessage(mContext, response.message(), Const.ERROR_SOME_WRONG, isPdShow, isError);
                            }
                        } else if (response.code() == 401) {
                            Utils.showMessageDialog(mContext, "", mContext.getString(R.string.session_expired), (dialog, which) -> {
                                new TinyDB(mContext).clear();
                                ((BaseActivity) mContext).startNewActivity(SplashActivity.class, true, true, null);
                            });
                        } else if (response.code() == 422 || response.code() == 503) {
                            showMessage(mContext, response.message(), Const.ERROR_SOME_WRONG, isPdShow, isError);
                        } else if (response.code() == 409) {
                            showMessage(mContext, mContext.getString(R.string.already_in_ride), Const.ERROR_SOME_WRONG, isPdShow, isError);
                        } else {
                            showMessage(mContext, mContext.getString(R.string.invalid_response_error), Const.ERROR_SOME_WRONG, isPdShow, isError);
                        }
                    }
                }

                @Override
                public void onFailure(@NonNull Call<T> call, @NonNull Throwable t) {
                    /*if (isPdShow && pd.isShowing())
                        pd.dismiss();*/
                    dismissDialog(mContext, isPdShow, pd);
                    Utils.showLog("Exp : " + t.getMessage());
                    showMessage(mContext, mContext.getString(R.string.something_went_wrong), Const.ERROR_SOME_WRONG, isPdShow, isError);
                }
            });
        } else {
            showMessage(mContext, mContext.getString(R.string.no_internet_message), Const.ERROR_NO_INTERNET, isPdShow, isError);
        }
    }

    private static void showMessage(Context mContext, String message, String errorType, boolean isDialog, boolean isError) {
        if (isDialog && isError)
            EventBus.getDefault().post(new Events.ErrorResult(errorType, message));
        if (!isDialog && isError)
            EventBus.getDefault().post(new Events.ErrorResult(errorType, message));
        if (isDialog && !isError)
            Utils.showMessageDialog(mContext, "", message);
    }

    private void dismissDialog(Context mContext, boolean isPdShow, Dialog dialog) {
        Window window = ((BaseActivity) mContext).getWindow();
        if (window != null) {
            View decor = window.getDecorView();
            if (decor.getParent() != null && isPdShow && dialog.isShowing()) {
                dialog.dismiss();
            }
        }
    }

    public abstract void onCompletion(Response<T> response);

    private Dialog getProgressDialog(Context mContext) {
        Dialog dialog = new Dialog(mContext);
        View view = View.inflate(mContext, R.layout.progress_dialog, null);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(view);
        Objects.requireNonNull(dialog.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setCancelable(false);
        return dialog;
    }
}