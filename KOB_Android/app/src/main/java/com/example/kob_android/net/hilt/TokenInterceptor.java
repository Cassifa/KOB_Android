package com.example.kob_android.net.hilt;

import androidx.annotation.NonNull;

import com.example.kob_android.database.UserSharedPreferences;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * @Author: Cassifa
 * @CreateTime: 2024-04-18  05:22
 * @Description:
 */
public class TokenInterceptor implements Interceptor {

    @NonNull
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request originalRequest = chain.request();

        // 添加 Authorization 头部
        Request.Builder requestBuilder = originalRequest.newBuilder();
        if (UserSharedPreferences.getInstance().getToken() != null)
            requestBuilder.header("Authorization", "Bearer " + UserSharedPreferences.getInstance().getToken());

        Request requestWithAuth = requestBuilder.build();

        // 继续请求链
        Response response = chain.proceed(requestWithAuth);

        return response;
    }
}
