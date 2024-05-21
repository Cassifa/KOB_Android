package com.example.kob_android.activity;

import android.app.Activity;
import android.os.Bundle;
import android.widget.FrameLayout;

import androidx.annotation.Nullable;

import com.example.kob_android.R;
import com.example.kob_android.gameObjects.MySurfaceView;

/**
 * @Author: Cassifa
 * @CreateTime: 2024-04-24  07:01
 * @Description:
 */
public class ShowRecordActivity extends Activity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_record);
        String recordData = getIntent().getStringExtra("record");

        // 找到布局中的 FrameLayout
        FrameLayout surfaceViewContainer = findViewById(R.id.surface_view_container);

        // 创建 MySurfaceView 并添加到容器中
        MySurfaceView mySurfaceView = new MySurfaceView(this,recordData,null);
        surfaceViewContainer.addView(mySurfaceView);

    }
}
