package com.example.kob_android.net.hilt;

import android.content.SharedPreferences;

import androidx.annotation.NonNull;

import java.io.IOException;
import java.util.HashSet;

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
        Request.Builder requestBuilder = originalRequest.newBuilder()
                .header("Authorization", "Bearer " +
                        "eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiJhZTYwYTViNTMyNTY0OWUxYTVhN2M4YzM5ZDk5NjE4MCIsInN1YiI6IjEiLCJpc3MiOiJzZyIsImlhdCI6MTcxNjIyNTI5MCwiZXhwIjoxNzE3NDM0ODkwfQ.hK3ue0xbFlN4yXhC4iaBwvtzmQUINNcjWbkTTh_IwzE");

        Request requestWithAuth = requestBuilder.build();

        // 继续请求链
        Response response = chain.proceed(requestWithAuth);

        return response;
    }
}
