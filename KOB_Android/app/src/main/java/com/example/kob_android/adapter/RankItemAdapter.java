package com.example.kob_android.adapter;

import android.annotation.SuppressLint;
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
import com.example.kob_android.utils.Constant;

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

    @SuppressLint("SetTextI18n")
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_rank, null);
            viewHolder = new ViewHolder();

            viewHolder.userRank = convertView.findViewById(R.id.rankItemUserRank);
            viewHolder.userImage = convertView.findViewById(R.id.rankItemUserImage);
            viewHolder.userName = convertView.findViewById(R.id.rankItemUserName);
            viewHolder.userRating = convertView.findViewById(R.id.rankItemUserRating);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        User user = userRankList.get(position);
        viewHolder.userRank.setText(user.getPassword());
        viewHolder.userName.setText(user.getUsername());
        viewHolder.userRating.setText(user.getRating().toString());
        Constant.setHttpImg(viewHolder.userImage, user.getPhoto(), mContext);
        return convertView;

    }

    public final class ViewHolder {
        public TextView userRank;
        public ImageView userImage;
        public TextView userName;
        public TextView userRating;
    }

}
