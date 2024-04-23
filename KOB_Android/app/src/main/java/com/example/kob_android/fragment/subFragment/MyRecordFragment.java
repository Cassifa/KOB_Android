package com.example.kob_android.fragment.subFragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.example.kob_android.R;

/**
 * @Author: Cassifa
 * @CreateTime: 2024-04-24  04:18
 * @Description:
 */
public class MyRecordFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.sub_fragment_my_record, container, false);
    }
}
