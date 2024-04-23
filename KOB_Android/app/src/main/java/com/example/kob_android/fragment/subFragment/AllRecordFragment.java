package com.example.kob_android.fragment.subFragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.fragment.app.Fragment;

import com.example.kob_android.R;
import com.example.kob_android.adapter.RankItemAdapter;
import com.example.kob_android.adapter.RecordItemAdapter;

/**
 * @Author: Cassifa
 * @CreateTime: 2024-04-24  04:18
 * @Description:
 */
public class AllRecordFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.sub_fragment_all_record, container, false);
        ListView rankList=view.findViewById(R.id.all_record_list);
        RecordItemAdapter adapter=new RecordItemAdapter(this.getContext(),null);
        rankList.setAdapter(adapter);
        return view;
    }
}
