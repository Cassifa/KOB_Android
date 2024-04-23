package com.example.kob_android.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.example.kob_android.net.responseData.pojo.User;
import com.example.kob_android.pojo.Record;

import java.util.List;

/**
 * @Author: Cassifa
 * @CreateTime: 2024-04-24  04:51
 * @Description:
 */
public class RecordItemAdapter extends BaseAdapter {

    private Context mContext;
    private List<Record> recordList;
    @Override
    public int getCount() {
        return recordList.size();
    }

    @Override
    public Object getItem(int position) {
        return recordList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return recordList.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return null;
    }
}
