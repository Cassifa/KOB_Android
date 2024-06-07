package com.example.kob_android;

import android.app.Application;
import android.content.res.Configuration;

import androidx.annotation.NonNull;

import com.example.kob_android.database.UserSharedPreferences;
import com.example.kob_android.net.responseData.pojo.User;

import java.util.HashMap;

import dagger.hilt.android.HiltAndroidApp;

/**
 * @Author: Cassifa
 * @CreateTime: 2024-06-03  22:51
 * @Description: 维护全局数据-User
 */
@HiltAndroidApp
public class MyApplication extends Application {
    private static MyApplication mApp = null;
    public HashMap<String, String> infoMap = new HashMap<>();

    public User user;
    public String token;
    public String theme;

    public static MyApplication getInstance() {
        return mApp;
    }

    public Integer getUserId() {
        int intId = -1;
        String id = infoMap.get("id");
        if (id != null)
            intId = Integer.parseInt(id);
        return intId;
    }

    @Override
    //APP启动时
    public void onCreate() {
        UserSharedPreferences.initInstance(this);
        theme = UserSharedPreferences.getInstance().getTheme();
        if (theme.equals("red")) setTheme(R.style.RedTheme);
        else if (theme.equals("blue")) setTheme(R.style.BlueTheme);

        super.onCreate();
        mApp = this;
        //初始化数据
        token = UserSharedPreferences.getInstance().getToken();
        user = UserSharedPreferences.getInstance().getUser();
    }

    @Override
    //APP终止时-仅在开发环境生效
    public void onTerminate() {
        super.onTerminate();
    }

    @Override
    //配置改变-横屏变竖屏
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }
}
