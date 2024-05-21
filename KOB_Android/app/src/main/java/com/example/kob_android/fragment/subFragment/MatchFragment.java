package com.example.kob_android.fragment.subFragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.kob_android.R;
import com.example.kob_android.fragment.PlayGroundFragment;

public class MatchFragment extends Fragment {


    PlayGroundFragment father;

    public MatchFragment(PlayGroundFragment father) {
        this.father = father;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_match, container, false);
    }
}