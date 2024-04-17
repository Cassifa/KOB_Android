package com.example.kob_android.net.hilt;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.kob_android.net.ApiService;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.android.components.ApplicationComponent;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * @Author: Cassifa
 * @CreateTime: 2024-04-18  04:59
 * @Description:
 */
@InstallIn(ApplicationComponent.class)
@Module
public class NetWorkModule {
    @Singleton
    @Provides
    OkHttpClient provideOkHttpClient() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
            @Override
            public void log(@NonNull String s) {
                Log.i("aaa", s);
            }
        }).setLevel(HttpLoggingInterceptor.Level.BODY);

        return new OkHttpClient()
                .newBuilder()
                .addInterceptor(new TokenInterceptor())
                .addInterceptor(interceptor)
                .build();
    }

    @Singleton
    @Provides
    Retrofit provideRetrofit(OkHttpClient okHttpClient) {//自己会找上面的
        return new Retrofit.Builder().baseUrl("http://192.168.187.88:3000/api/")
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    @Singleton
    @Provides
    ApiService provideApiService(Retrofit retrofit) {
        return retrofit.create(ApiService.class);
    }
}
