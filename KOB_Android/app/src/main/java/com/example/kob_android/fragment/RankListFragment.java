package com.example.kob_android.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.kob_android.R;
import com.example.kob_android.adapter.RankItemAdapter;

/**
 * @Author: Cassifa
 * @CreateTime: 2024-04-23  19:39
 * @Description:
 */
public class RankListFragment extends Fragment {
    @androidx.annotation.Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @androidx.annotation.Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_ranklist, container, false);
        ListView rankList=view.findViewById(R.id.rankListView);
        RankItemAdapter adapter=new RankItemAdapter(this.getContext(),null);
        rankList.setAdapter(adapter);
        return view;
    }

}
