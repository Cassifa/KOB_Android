package com.example.kob_android.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.fragment.app.Fragment;

import com.example.kob_android.R;
import com.example.kob_android.adapter.BotItemAdapter;
import com.example.kob_android.adapter.RankItemAdapter;

/**
 * @Author: Cassifa
 * @CreateTime: 2024-04-24  07:40
 * @Description:
 */
public class BotListFragment extends Fragment {
    @androidx.annotation.Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @androidx.annotation.Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_mybots, container, false);
        ListView rankList=view.findViewById(R.id.myBotsView);
        BotItemAdapter adapter=new BotItemAdapter(this.getContext(),null);
        rankList.setAdapter(adapter);
        return view;
    }
}
