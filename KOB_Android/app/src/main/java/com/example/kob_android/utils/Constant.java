package com.example.kob_android.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.widget.ImageView;

import com.example.kob_android.net.responseData.pojo.User;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.UnknownHostException;

/**
 * @Author: Cassifa
 * @CreateTime: 2024-04-24  04:47
 * @Description:
 */
public class Constant {
    public static void setHttpImg(ImageView userImage, String imagePath, Context context) {
        Log.i("aaa", imagePath);
        new Thread() {
            @Override
            public void run() {
                try {
                    URL url = new URL(imagePath);
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    connection.setRequestMethod("GET");
                    connection.setConnectTimeout(5000);
                    InputStream inputStream = connection.getInputStream();
                    // 将输入流转化为Bitmap
                    final Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                    // 使用ImageView的post方法在UI线程中更新UI
                    userImage.post(new Runnable() {
                        @Override
                        public void run() {
                            userImage.setImageBitmap(bitmap);
                        }
                    });

                } catch (UnknownHostException e) {
                    Log.e("RankItemAdapter", "Failed to resolve host: " + imagePath, e);
                } catch (IOException e) {
                    Log.e("RankItemAdapter", "Failed to get image from: " + imagePath, e);
                }
            }
        }.start();
    }

    public static User getMyInfo() {
        User user = new User();
        user.setUsername("飞飞");
        user.setPhoto("https://cdn.acwing.com/media/user/profile/photo/144446_md_4da0fc7c25.jpg");
        Log.i("aaa", user.toString());
        return user;
    }
}
