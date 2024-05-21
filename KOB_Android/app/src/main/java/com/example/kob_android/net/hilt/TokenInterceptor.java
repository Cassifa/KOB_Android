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
                        "eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiIzMjNmYjNjMDc0N2I0OTdhYmQ3ZjgwNDdhMGYzMjhhMyIsInN1YiI6IjEiLCJpc3MiOiJzZyIsImlhdCI6MTcxNjI3OTQwMiwiZXhwIjoxNzE3NDg5MDAyfQ.-PTXxXsrhGByTrr_t5MKqd82HzHyGProg1_MRLxJFzI");

        Request requestWithAuth = requestBuilder.build();

        // 继续请求链
        Response response = chain.proceed(requestWithAuth);

        return response;
    }
}
