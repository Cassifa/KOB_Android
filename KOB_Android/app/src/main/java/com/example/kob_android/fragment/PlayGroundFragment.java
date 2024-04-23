package com.example.kob_android.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.example.kob_android.R;

/**
 * @Author: Cassifa
 * @CreateTime: 2024-04-23  00:34
 * @Description:
 */
public class PlayGroundFragment extends Fragment {
    @androidx.annotation.Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @androidx.annotation.Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_playground, container, false);
        return view;
    }
}
