package com.example.kob_android.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.kob_android.R;

/**
 * @Author: Cassifa
 * @CreateTime: 2024-04-23  00:51
 * @Description:
 */
public class UserInfoFragment extends Fragment {
    @androidx.annotation.Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @androidx.annotation.Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_userinfo, container, false);
        return view;
    }

}
