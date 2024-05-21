package com.example.kob_android.fragment.subFragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;

import com.example.kob_android.R;
import com.example.kob_android.fragment.PlayGroundFragment;

/**
 * @Author: Cassifa
 * @CreateTime: 2024-05-22
 * @Description: 向 PlayGroundFragment 返回用户移动信息
 */
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
        view.setVisibility(View.GONE);
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
        //上-0 下-2 左-3 右-1
        if (id == R.id.up)
            father.setDirection(0);
        else if (id == R.id.down)
            father.setDirection(2);
        else if (id == R.id.left)
            father.setDirection(3);
        else if (id == R.id.right)
            father.setDirection(1);
    }
}