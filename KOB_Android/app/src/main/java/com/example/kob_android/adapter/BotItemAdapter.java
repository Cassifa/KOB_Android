package com.example.kob_android.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kob_android.R;
import com.example.kob_android.activity.ModifyMyBotActivity;
import com.example.kob_android.net.BotApiService;
import com.example.kob_android.net.responseData.pojo.Bot;
import com.google.gson.GsonBuilder;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * @Author: Cassifa
 * @CreateTime: 2024-05-20  09:08
 * @Description:
 */
public class BotItemAdapter extends BaseAdapter {

    private Context mContext;
    private List<Bot> userBotList;
    @Inject
    BotApiService botApiService;

    public BotItemAdapter(Context mContext, List<Bot> userRank, BotApiService botApiService) {
        this.mContext = mContext;
        this.userBotList = userRank;
        this.botApiService = botApiService;
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
        String botJson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd HH:mm:ss")  // 确保日期格式匹配
                .create().toJson(bot);

        botModify.setOnClickListener(v -> {
            Intent intent = new Intent(mContext, ModifyMyBotActivity.class);
            intent.putExtra("bot", botJson);
            mContext.startActivity(intent);
        });

        botDelete.setOnClickListener(v -> {
            botApiService.remove(bot.getId().toString()).enqueue(new Callback<HashMap<String, String>>() {
                @Override
                public void onResponse(Call<HashMap<String, String>> call, Response<HashMap<String, String>> response) {
                    HashMap<String, String> msg = response.body();
                    final String errorMsg;
                    if (msg != null) {
                        errorMsg = msg.get("error_message");
                    } else errorMsg = null;
                    ((Activity) mContext).runOnUiThread(() -> {
                        Toast.makeText(mContext, errorMsg, Toast.LENGTH_SHORT).show();
                        if (errorMsg != null && errorMsg.equals("删除成功")) {
                            userBotList.remove(position);
                            notifyDataSetChanged();
                        }
                    });
                }

                @Override
                public void onFailure(Call<HashMap<String, String>> call, Throwable t) {
                    ((Activity) mContext).runOnUiThread(() -> {
                        Toast.makeText(mContext, "网络异常", Toast.LENGTH_SHORT).show();
                    });
                }
            });
        });

        botName.setText(bot.getTitle());
        botCreateTime.setText(new SimpleDateFormat("yyyy-MM-dd").format(bot.getCreatetime()));
        return view;

    }
}