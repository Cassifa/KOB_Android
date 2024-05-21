package com.example.kob_android.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.Spinner;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.kob_android.R;
import com.example.kob_android.fragment.subFragment.MatchFragment;
import com.example.kob_android.fragment.subFragment.UserActionFragment;
import com.example.kob_android.gameObjects.MySurfaceView;
import com.example.kob_android.utils.Constant;

import java.util.Objects;

/**
 * @Author: Cassifa
 * @CreateTime: 2024-04-23  00:34
 * @Description:
 */
public class PlayGroundFragment extends Fragment {
    View view;
    MatchFragment matchFragment;
    UserActionFragment userActionFragment;
    MySurfaceView surfaceView;
    FrameLayout showingLayout;
    FrameLayout actionLayout;
    FragmentTransaction fragmentTransaction;

    @androidx.annotation.Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @androidx.annotation.Nullable ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_playground, container, false);
        initView();
        return view;
    }

    private void initView() {
        showingLayout = view.findViewById(R.id.showingArea);
        actionLayout = view.findViewById(R.id.actionArea);

        //准备资源
        matchFragment = new MatchFragment(this);
        userActionFragment = new UserActionFragment(this);

        updateMainArea(true);
    }

    private void updateMainArea(boolean isMatch) {
        //isMatch表示要匹配页面
        if (isMatch) {
            //展示匹配Fragment
            fragmentTransaction = requireActivity().getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.showingArea, matchFragment);
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();

            showActionArea(false);
        } else {

        }
    }

    //修改是否展示上下左右移动键
    private void showActionArea(boolean isShow) {

    }

    public void startGame() {
    }

    public void setDirection(int direction) {
    }
}
