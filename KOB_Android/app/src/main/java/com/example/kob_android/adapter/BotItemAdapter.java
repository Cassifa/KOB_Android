package com.example.kob_android.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.example.kob_android.R;
import com.example.kob_android.net.responseData.pojo.Bot;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

/**
 * @Author: Cassifa
 * @CreateTime: 2024-05-20  09:08
 * @Description:
 */
public class BotItemAdapter extends BaseAdapter {

    private Context mContext;
    private List<Bot> userBotList;

    public BotItemAdapter(Context mContext, List<Bot> userRank) {
        this.mContext = mContext;
        this.userBotList = userRank;
        userBotList = new LinkedList<>();
        userBotList.add(new Bot(1, 1, "向上走", "new 描述", "aaa", new Date(), new Date()));
    }

    @Override
    public int getCount() {
        return userBotList.size();
    }

    @Override
    public Object getItem(int position) {
        return userBotList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        @SuppressLint("ViewHolder") View view = LayoutInflater.from(mContext).inflate(R.layout.item_bot, null);
        TextView botName = view.findViewById(R.id.botItemName);
        TextView botCreateTime = view.findViewById(R.id.botItemCreateTime);
        Button botModify = view.findViewById(R.id.botItemModify);
        Button botDelete = view.findViewById(R.id.botItemDelete);

        Bot bot = userBotList.get(position);
        botName.setText(bot.getTitle());
        botCreateTime.setText(new SimpleDateFormat("yyyy-MM-dd").format(bot.getCreatetime()));
        return view;

    }
}