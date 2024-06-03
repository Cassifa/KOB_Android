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

    final String token="eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiJkNDUxM2I1MDJkZjA0NTZkYWE4MTgyYzAzYWNjNTU3MyIsInN1YiI6IjEiLCJpc3MiOiJzZyIsImlhdCI6MTcxNzM5ODI2OCwiZXhwIjoxNzE4NjA3ODY4fQ.pFG_F-YFCgKw49hghIaF0t2wi3__TLfmT8ebovn3nIA";
    @NonNull
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request originalRequest = chain.request();

        // 添加 Authorization 头部
        Request.Builder requestBuilder = originalRequest.newBuilder()
                .header("Authorization", "Bearer " + token);

        Request requestWithAuth = requestBuilder.build();

        // 继续请求链
        Response response = chain.proceed(requestWithAuth);

        return response;
    }
}
