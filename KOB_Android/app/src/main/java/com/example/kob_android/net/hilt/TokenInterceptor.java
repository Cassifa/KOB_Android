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
                        "eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiIxN2ZkZGFlNzFhMTA0YmE5YWIyMDhkNzhlNjFiMTZmNCIsInN1YiI6IjEiLCJpc3MiOiJzZyIsImlhdCI6MTcxMzM4ODYyNiwiZXhwIjoxNzE0NTk4MjI2fQ.YzXvae5tfiheOJU5F50AqFJdT-ZzoBEgAQ6t0RIZxXk");

        Request requestWithAuth = requestBuilder.build();

        // 继续请求链
        Response response = chain.proceed(requestWithAuth);

        return response;
    }
}
