package com.example.kob_android.net;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * @Author: Cassifa
 * @CreateTime: 2024-04-18  00:01
 * @Description:
 * 1.登录
 * 2.注册
 * 3.个人信息
 */
public interface ApiService {
    @POST("user/account/token/")
    Call<HashMap<String,String>> login(@Query("username") String username,
                                       @Query("password") String password);

    @POST("user/account/register/")
    Call<String> register(@Query("username") String username,
                          @Query("password") String password,
                          @Query("confirmedPassword") String confirmedPassword);
    @GET("user/account/info/")
    Call<HashMap<String,String>> getinfo();
}
