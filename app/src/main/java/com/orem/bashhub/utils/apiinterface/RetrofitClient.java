package com.orem.bashhub.utils.apiinterface;

import android.content.Context;

import com.orem.bashhub.utils.Const;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {

    public static APIs apIs(Context mContext) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Const.BASE_URL)
                .client(Const.isLoggedIn(mContext) ? getOkHttpClientHeader(mContext) : getOkHttpClient())
                .addConverterFactory(GsonConverterFactory.create()).build();
        return retrofit.create(APIs.class);
    }

    public static APIs faceApIs(Context mContext) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Const.FACE_URL)
                .client(Const.isLoggedIn(mContext) ? getOkHttpClientHeader(mContext) : getOkHttpClient())
                .addConverterFactory(GsonConverterFactory.create()).build();
        return retrofit.create(APIs.class);
    }

    public static APIs uberApIs(String token) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Const.UBER_BASE_URl)
                .client(getUberOkHttpClientHeader(token))
                .addConverterFactory(GsonConverterFactory.create()).build();
        return retrofit.create(APIs.class);
    }

    private static OkHttpClient getOkHttpClient() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.level(HttpLoggingInterceptor.Level.BODY);
        return new OkHttpClient.Builder().connectTimeout(40, TimeUnit.SECONDS)
                .writeTimeout(40, TimeUnit.SECONDS)
                .readTimeout(40, TimeUnit.SECONDS)
                .addInterceptor(interceptor)
                .build();
    }

    private static OkHttpClient getOkHttpClientHeader(Context mContext) {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.level(HttpLoggingInterceptor.Level.BODY);
        return new OkHttpClient.Builder().connectTimeout(40, TimeUnit.SECONDS)
                .writeTimeout(40, TimeUnit.SECONDS)
                .readTimeout(40, TimeUnit.SECONDS)
                .addInterceptor(chain -> {
                    Request request = chain.request().newBuilder()
                            .addHeader(Const.HEADER_KEY, Const.getToken(mContext))
                            .build();
                    return chain.proceed(request);
                })
                .addInterceptor(interceptor)
                .build();
    }

    private static OkHttpClient getUberOkHttpClientHeader(String token) {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.level(HttpLoggingInterceptor.Level.BODY);
        return new OkHttpClient.Builder().connectTimeout(40, TimeUnit.SECONDS)
                .writeTimeout(40, TimeUnit.SECONDS)
                .readTimeout(40, TimeUnit.SECONDS)
                .addInterceptor(chain -> {
                    Request request = chain.request().newBuilder()
                            .addHeader(Const.HEADER_KEY, "Bearer " + token)
                            .build();
                    return chain.proceed(request);
                })
                .addInterceptor(interceptor)
                .build();
    }

    private static OkHttpClient getSpotifyaHeader(String token) {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.level(HttpLoggingInterceptor.Level.BODY);
        return new OkHttpClient.Builder().connectTimeout(40, TimeUnit.SECONDS)
                .writeTimeout(40, TimeUnit.SECONDS)
                .readTimeout(40, TimeUnit.SECONDS)
                .addInterceptor(chain -> {
                    Request request = chain.request().newBuilder()
                            .addHeader(Const.HEADER_KEY, "Bearer " + token)
                            .build();
                    return chain.proceed(request);
                })
                .addInterceptor(interceptor)
                .build();
    }
}
