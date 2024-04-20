package com.example.kob_android.net;

import com.example.kob_android.net.responseData.pojo.Bot;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * @Author: Cassifa
 * @CreateTime: 2024-04-18  03:54
 * @Description:
 * 1.添加
 * 2.获取所有
 * 3.移除Bot
 * 4.修改Bot
 */
public interface BotApiService {

    @POST("user/bot/add/")
    Call<HashMap<String,String>>
    add(@Query("title") String title,
             @Query("description") String description,
             @Query("content") String content);


    @GET("user/bot/getlist/")
    Call<List<Bot>> getlist();

    @POST("user/bot/remove/")
    Call<HashMap<String,String>> remove(@Query("bot_id") String bot_id);

    @POST("user/bot/update/")
    Call<HashMap<String,String>>
    update(@Query("bot_id") String bot_id,
            @Query("title") String title,
            @Query("description") String description,
            @Query("content") String content);
}
