package com.example.kob_android.net;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * @Author: Cassifa
 * @CreateTime: 2024-04-18  00:01
 * @Description:
 */
public interface ApiService {
        @POST("user/account/token/")
        Call<String> login(@Query("username") String username,
                           @Query("password") String password);
}
