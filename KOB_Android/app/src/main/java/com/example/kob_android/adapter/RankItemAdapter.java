package com.example.kob_android.adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.kob_android.R;
import com.example.kob_android.net.responseData.pojo.User;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.LinkedList;
import java.util.List;

/**
 * @Author: Cassifa
 * @CreateTime: 2024-04-23  20:40
 * @Description:
 */
public class RankItemAdapter extends BaseAdapter {

    private Context mContext;
    private List<User> userRankList;

    public RankItemAdapter(Context mContext, List<User> userRank) {
        this.mContext = mContext;
        this.userRankList = userRank;
        userRankList = new LinkedList<>();
        userRankList.add(new User(1, "lff", "1", 1500, "https://cdn.acwing.com/media/user/profile/photo/144446_md_4da0fc7c25.jpg"));
    }

    @Override
    public int getCount() {
        return userRankList.size();
    }

    @Override
    public Object getItem(int position) {
        return userRankList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_rank, null);
        TextView userRank = view.findViewById(R.id.rankItemUserRank);
        ImageView userImage = view.findViewById(R.id.rankItemUserImage);
        TextView userName = view.findViewById(R.id.rankItemUserName);
        TextView userRating = view.findViewById(R.id.rankItemUserRating);
        User user = userRankList.get(position);
        userRank.setText(user.getPassword());
        userName.setText(user.getUsername());
        userRating.setText(user.getRating().toString());
//        getHttpBitmap(userImage, user.getPhoto());

        Log.i("aaa", user.getPhoto());
        new Thread() {
            @Override
            public void run() {
                try {
                    URL url = new URL(user.getPhoto());
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    connection.setRequestMethod("GET");
                    connection.setConnectTimeout(5000);
                    InputStream inputStream = connection.getInputStream();
                    //将输入流转化为BitMap
                    final Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                    ((Activity) mContext).runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            userImage.setImageBitmap(bitmap);
                        }
                    });

                } catch (UnknownHostException e) {
                    Log.e("RankItemAdapter", "Failed to resolve host: " + user.getPhoto(), e);
                } catch (IOException e) {
                    Log.e("RankItemAdapter", "Failed to get image from: " + user.getPhoto(), e);
                }
            }
        }.start();
        return view;

    }

    private void getHttpBitmap(ImageView userImage, String imagePath) {
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
                    ((Activity) mContext).runOnUiThread(new Runnable() {
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
