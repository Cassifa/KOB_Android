package com.example.kob_android;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.example.kob_android.net.ApiService;

import java.util.HashMap;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

//声明可注入
@AndroidEntryPoint
public class MainActivity extends AppCompatActivity {
    @Inject
    ApiService apiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //        new Thread(new Runnable() {
        //            @Override
        //            public void run() {
        //                try {
        //                    Log.i("aaa", "进入");
        //                    Response<String> response = apiService.login("lff", "123")
        //                            .execute();
        //                    Log.i("aaa", "结果" + response.body());
        //                } catch (IOException e) {
        //                    e.printStackTrace();
        //                    ;
        //                }
        //            }
        //        }).start();
//        apiService.login("lff", "123").enqueue(new Callback<HashMap<String, String>>() {
//            @Override
//            public void onResponse(Call<HashMap<String, String>> call, Response<HashMap<String, String>> response) {
//                Log.i("aaa", "结果" + response.body().toString());
//            }
//
//            @Override
//            public void onFailure(Call<HashMap<String, String>> call, Throwable t) {
//
//            }
//        });
        apiService.getinfo().enqueue(new Callback<HashMap<String, String>>() {
            @Override
            public void onResponse(Call<HashMap<String, String>> call, Response<HashMap<String, String>> response) {
                Log.i("aaa", "结果" + response.body().toString());
            }

            @Override
            public void onFailure(Call<HashMap<String, String>> call, Throwable t) {

            }
        });
    }
}