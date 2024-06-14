package com.example.kob_android.net;

import com.example.kob_android.pojo.Bot;

import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

/**
 * @Author: Cassifa
 * @CreateTime: 2024-04-18  03:54
 * @Description: 1.添加
 * 2.获取所有
 * 3.移除Bot
 * 4.修改Bot
 */
public interface BotApiService {
    @FormUrlEncoded
    @POST("user/bot/add/")
    Call<HashMap<String, String>>
    add(@Field("title") String title,
        @Field("description") String description,
        @Field("content") String content);


    @GET("user/bot/getlist/")
    Call<List<Bot>> getlist();

    @FormUrlEncoded
    @POST("user/bot/remove/")
    Call<HashMap<String, String>> remove(@Field("bot_id") String bot_id);

    @FormUrlEncoded
    @POST("user/bot/update/")
    Call<HashMap<String, String>>
    update(@Field("bot_id") String bot_id,
           @Field("title") String title,
           @Field("description") String description,
           @Field("content") String content);
}
