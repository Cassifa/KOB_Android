package com.example.kob_android.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.kob_android.R;
import com.example.kob_android.gameObjects.infoUtils.Cell;
import com.example.kob_android.net.responseData.pojo.User;
import com.example.kob_android.pojo.Record;
import com.example.kob_android.pojo.RecordItem;
import com.example.kob_android.utils.Constant;
import com.fasterxml.jackson.core.JsonFactoryBuilder;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

/**
 * @Author: Cassifa
 * @CreateTime: 2024-04-24  04:51
 * @Description:
 */
public class RecordItemAdapter extends BaseAdapter {

    private Context mContext;
    private List<RecordItem> recordItemList;
    private final String data="{\"a_photo\":\"https://cdn.acwing.com/media/user/profile/photo/144446_md_4da0fc7c25.jpg\",\"a_username\":\"lff\",\"b_photo\":\"https://ts1.cn.mm.bing.net/th?id=OIP-C.gNGfGtBM4P6hsh7taj9AqwEsEs&w=175&h=185&c=8&rs=1&qlt=90&o=6&dpr=1.5&pid=3.1&rm=2\",\"b_username\":\"麦麦\",\"record\":{\"id\":60,\"map\":\"11111111111111101000000001011000001100000111000000000011110000000000111000000000000110010000001001100000000000011100000000001111000000000011100000110000011010000000010111111111111111\",\"loser\":\"a\",\"createTime\":\"2023-04-26 21:47:32\",\"aid\":1,\"bid\":2,\"asy\":1,\"bsx\":1,\"bsteps\":\"23222330322323333\",\"asteps\":\"01000300101030300\",\"bsy\":12,\"asx\":11},\"result\":\"B胜\"}";
    public RecordItemAdapter(Context mContext, List<RecordItem> recordItemList) {
        this.mContext = mContext;
        recordItemList = new LinkedList<>();

        recordItemList.add(new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create().fromJson(data, RecordItem.class));

        this.recordItemList = recordItemList;
    }
    @Override
    public int getCount() {
        return recordItemList.size();
    }

    @Override
    public Object getItem(int position) {
        return recordItemList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @SuppressLint({"SetTextI18n", "SimpleDateFormat"})
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        @SuppressLint("ViewHolder") View view = LayoutInflater.from(mContext).inflate(R.layout.item_record, null);
        ImageView recordAImage = view.findViewById(R.id.recordItemAImage);
        TextView recordAName = view.findViewById(R.id.recordItemAName);

        TextView recordVS = view.findViewById(R.id.recordItemVS);

        ImageView recordBImage = view.findViewById(R.id.recordItemBImage);
        TextView recordBName = view.findViewById(R.id.recordItemBName);

        TextView recordTime = view.findViewById(R.id.recordItemTime);
        RecordItem recordItem = recordItemList.get(position);
        Constant.setHttpImg(recordAImage,recordItem.getA_photo(),mContext);
        recordAName.setText(recordItem.getA_username());
        recordVS.setText("VS");
        Constant.setHttpImg(recordBImage, recordItem.getB_photo(),mContext);
        recordBName.setText(recordItem.getA_username());
        Date date=recordItem.getRecord().getCreateTime();
        recordTime.setText(new SimpleDateFormat("yyyy-MM-dd").format(date));
        return view;
    }
}
