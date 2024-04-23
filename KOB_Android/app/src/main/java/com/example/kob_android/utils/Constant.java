package com.example.kob_android.utils;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.widget.ImageView;

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
                    //将输入流转化为BitMap
                    final Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                    ((Activity) context).runOnUiThread(new Runnable() {
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
}