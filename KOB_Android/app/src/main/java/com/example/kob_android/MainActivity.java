package com.example.kob_android;

import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.kob_android.net.ApiService;

import java.io.IOException;
import java.util.HashMap;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class MainActivity extends AppCompatActivity {
    Retrofit retrofit;
    OkHttpClient okHttpClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
            @Override
            public void log(@NonNull String s) {
                Log.i("aaa", s);
            }
        }).setLevel(HttpLoggingInterceptor.Level.BODY);

        okHttpClient = new OkHttpClient()
                .newBuilder()
                .addInterceptor(interceptor)
                .build();
        retrofit = new Retrofit.Builder().baseUrl("http://10.200.51.93:3000/api/")
                .client(okHttpClient)
                .addConverterFactory(ScalarsConverterFactory.create())
                .build();

        ApiService apiService = retrofit.create(ApiService.class);
        HashMap<String, String> data = new HashMap<>();
        data.put("username", "lff");
        data.put("password", "123");

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Log.i("aaa", "进入");
                    Response<String> response = apiService.login("lff","123")
                            .execute();
                    Log.i("aaa", "结果" + response.body());
                } catch (IOException e) {
                    e.printStackTrace();;
                }
            }
        }).start();
    }
}