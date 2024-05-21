package com.example.kob_android.fragment.subFragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.kob_android.R;
import com.example.kob_android.fragment.PlayGroundFragment;

public class UserActionFragment extends Fragment implements View.OnClickListener {
    Button up, down, left, right;
    View view;
    PlayGroundFragment father;

    public UserActionFragment(PlayGroundFragment father) {
        this.father = father;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_user_action, container, false);
        initBtn();
        return view;
    }

    private void initBtn() {
        up = view.findViewById(R.id.up);
        down = view.findViewById(R.id.down);
        left = view.findViewById(R.id.left);
        right = view.findViewById(R.id.right);
        up.setOnClickListener(this);
        down.setOnClickListener(this);
        left.setOnClickListener(this);
        right.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.up) {

        } else if (id == R.id.down) {

        } else if (id == R.id.left) {

        } else if (id == R.id.left) {

        }
    }
}