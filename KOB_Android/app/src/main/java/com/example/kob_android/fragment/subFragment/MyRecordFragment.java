package com.example.kob_android.fragment.subFragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.kob_android.R;
import com.example.kob_android.adapter.RecordItemAdapter;
import com.example.kob_android.database.RecordItemDBHelper;
import com.example.kob_android.pojo.RecordItem;

import java.util.ArrayList;

/**
 * @Author: Cassifa
 * @CreateTime: 2024-04-24  04:18
 * @Description:
 */
public class MyRecordFragment extends Fragment {
    ListView listView;
    RecordItemDBHelper mHelper;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.sub_fragment_my_record, container, false);
        listView = view.findViewById(R.id.my_record_list);
        mHelper = RecordItemDBHelper.getInstance(getContext());
        mHelper.openReadLink();
        ArrayList<RecordItem> recordItemList = mHelper.select(1);
        RecordItemAdapter adapter = new RecordItemAdapter(getContext(), recordItemList);
        listView.setAdapter(adapter);
        return view;
    }
    @Override
    public void onStop() {
        super.onStop();
        mHelper.closeLink();
    }
}
