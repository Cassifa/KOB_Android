package com.example.kob_android.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.kob_android.R;
import com.example.kob_android.activity.ShowRecordActivity;
import com.example.kob_android.gameObjects.infoUtils.Cell;
import com.example.kob_android.net.ListApiService;
import com.example.kob_android.net.responseData.RecordItemsData;
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

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * @Author: Cassifa
 * @CreateTime: 2024-04-24  04:51
 * @Description:
 */
public class RecordItemAdapter extends BaseAdapter {
    private Context mContext;
    private List<RecordItem> recordItemList;

    public RecordItemAdapter(Context mContext, List<RecordItem> recordItemList) {
        this.mContext = mContext;
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
        ViewHolder viewHolder;
        if(convertView==null){
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_record, null);
            viewHolder=new ViewHolder();

            viewHolder.recordAImage = convertView.findViewById(R.id.recordItemAImage);
            viewHolder.recordAName = convertView.findViewById(R.id.recordItemAName);

            viewHolder.recordVS = convertView.findViewById(R.id.recordItemVS);

            viewHolder.recordBImage = convertView.findViewById(R.id.recordItemBImage);
            viewHolder.recordBName = convertView.findViewById(R.id.recordItemBName);

            viewHolder.recordTime = convertView.findViewById(R.id.recordItemTime);
            convertView.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        RecordItem recordItem = recordItemList.get(position);

        Constant.setHttpImg(viewHolder.recordAImage, recordItem.getA_photo(), mContext);
        viewHolder.recordAName.setText(recordItem.getA_username());
        viewHolder.recordVS.setText("VS");
        Constant.setHttpImg(viewHolder.recordBImage, recordItem.getB_photo(), mContext);
        viewHolder.recordBName.setText(recordItem.getB_username());
        Date date = recordItem.getRecord().getCreateTime();
        viewHolder.recordTime.setText(new SimpleDateFormat("yyyy-MM-dd").format(date));

        String recordJson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd HH:mm:ss")  // 确保日期格式匹配
                .create().toJson(recordItem.getRecord());
        // 设置点击事件
        convertView.setOnClickListener(v -> {
            Intent intent = new Intent(v.getContext(), ShowRecordActivity.class);
            // 将 JSON 字符串作为意图的 Extra
            intent.putExtra("record", recordJson);
            v.getContext().startActivity(intent); // 启动新的 Activity
        });
        return convertView;
    }

    public final class ViewHolder {
        public ImageView recordAImage;
        public TextView recordAName;
        public TextView recordVS;
        public ImageView recordBImage;
        public TextView recordBName;
        public TextView recordTime;
    }

    //    @SuppressLint({"SetTextI18n", "SimpleDateFormat"})
    //    @Override
    //    public View getView(int position, View convertView, ViewGroup parent) {
    //        @SuppressLint("ViewHolder") View view = LayoutInflater.from(mContext).inflate(R.layout.item_record, null);
    //        ImageView recordAImage = view.findViewById(R.id.recordItemAImage);
    //        TextView recordAName = view.findViewById(R.id.recordItemAName);
    //
    //        TextView recordVS = view.findViewById(R.id.recordItemVS);
    //
    //        ImageView recordBImage = view.findViewById(R.id.recordItemBImage);
    //        TextView recordBName = view.findViewById(R.id.recordItemBName);
    //
    //        TextView recordTime = view.findViewById(R.id.recordItemTime);
    //        RecordItem recordItem = recordItemList.get(position);
    //        Constant.setHttpImg(recordAImage, recordItem.getA_photo(), mContext);
    //        recordAName.setText(recordItem.getA_username());
    //        recordVS.setText("VS");
    //        Constant.setHttpImg(recordBImage, recordItem.getB_photo(), mContext);
    //        recordBName.setText(recordItem.getB_username());
    //        Date date = recordItem.getRecord().getCreateTime();
    //        recordTime.setText(new SimpleDateFormat("yyyy-MM-dd").format(date));
    //
    //        String recordJson = new GsonBuilder()
    //                .setDateFormat("yyyy-MM-dd HH:mm:ss")  // 确保日期格式匹配
    //                .create().toJson(recordItem.getRecord());
    //        // 设置点击事件
    //        view.setOnClickListener(v -> {
    //            Intent intent = new Intent(v.getContext(), ShowRecordActivity.class);
    //            // 将 JSON 字符串作为意图的 Extra
    //            intent.putExtra("record", recordJson);
    //            v.getContext().startActivity(intent); // 启动新的 Activity
    //        });
    //        return view;
    //    }
}
