package com.example.kob_android.net;

import com.example.kob_android.net.responseData.RankListData;
import com.example.kob_android.net.responseData.RecordItemsData;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * @Author: Cassifa
 * @CreateTime: 2024-04-18  03:55
 * @Description:
 * 1.rankList
 * 2.recordList
 */
public interface ListApiService {
    @GET("ranklist/getlist/")
    Call<RankListData> getRankList(@Query("page") Integer page);
    @GET("record/getlist/")
    Call<RecordItemsData> getRecordList(@Query("page") Integer page);
}
