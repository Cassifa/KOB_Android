package com.example.kob_android.database;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import com.example.kob_android.net.responseData.pojo.User;

import java.util.Objects;

/**
 * @Author: Cassifa
 * @CreateTime: 2024-06-03  22:32
 * @Description: 做数据存贮与保存配置的工作
 */
public class UserSharedPreferences {
    private static final UserSharedPreferences mPreferences = new UserSharedPreferences();
    private SharedPreferences preferences;

    public static void initInstance(Application application) {
        mPreferences.preferences = application.getSharedPreferences("config", Context.MODE_PRIVATE);
    }

    public static UserSharedPreferences getInstance() {
        return mPreferences;
    }

    //更新储存数据
    public void refreshUser(User user) {
        SharedPreferences.Editor editor = preferences.edit();
        if (user == null) {
            editor.remove("id");
            editor.remove("userName");
            editor.remove("userPassword");
            editor.remove("rating");
            editor.remove("photo");
        } else {
            editor.putString("id", user.getId().toString());
            editor.putString("userName", user.getUsername());
            editor.putString("userPassword", user.getPassword());
            editor.putString("rating", user.getRating().toString());
            editor.putString("photo", user.getPhoto());
        }
        editor.apply();
    }

    public void refreshToken(String token) {
        SharedPreferences.Editor editor = preferences.edit();
        if (token == null)
            editor.remove("token");
        else
            editor.putString("token", token);
        editor.apply();
    }

    public void refreshBotId(int botId) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt("botId", botId);
        editor.apply();
    }

    public User getUser() {
        Integer id = preferences.getString("id", null) == null ? null : Integer.getInteger(Objects.requireNonNull(preferences.getString("id", null)));
        Integer rating = preferences.getString("rating", null) == null ? null : Integer.getInteger(Objects.requireNonNull(preferences.getString("rating", null)));

        return new User(
                id,
                preferences.getString("userName", null),
                preferences.getString("userPassword", null),
                rating,
                preferences.getString("photo", null)
        );
    }

    public String getToken() {
        return preferences.getString("token", null);
    }

    public int getBotId() {
        return preferences.getInt("botId", -1);
    }
}
