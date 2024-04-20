package com.example.kob_android;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.kob_android.net.ListApiService;
import com.example.kob_android.net.responseData.RecordItemsData;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

//声明可注入
@AndroidEntryPoint
public class MainActivity extends AppCompatActivity {
    MySurfaceView view;
//    @Inject
//ListApiService listApiService;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
            view=new MySurfaceView(this);
            setContentView(view);
//                listApiService.getRecordList(1).enqueue(new Callback<RecordItemsData>() {
//                @Override
//                public void onResponse(Call<RecordItemsData> call, Response<RecordItemsData> response) {
//                    RecordItemsData rankListData = response.body();
//                    Log.i("aaa",rankListData.toString());
//                }
//
//                @Override
//                public void onFailure(Call<RecordItemsData> call, Throwable t) {
//
//                }
//            });
    }

}
//{"id":184,"map":"11111111111111101000000001011000001100000110100000000101100000000000011000001100000110001000010001100000110000011000000000000110100000000101100000110000011010000000010111111111111111","loser":"b","createTime":"2024-01-20 13:56:32","bid":3,"aid":1,"asx":11,"bsx":1,"asy":1,"bsy":12,"asteps":"000100301011030012","bsteps":"222333033323221111"},"result":"A胜"
