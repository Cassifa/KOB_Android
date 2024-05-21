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
//        surfaceView = new MySurfaceView(getContext(), null);

        updateMainArea(true);
    }

    private void updateMainArea(boolean isMatch) {
        //isMatch表示要匹配页面
        if (isMatch) {
            //必须全局
            fragmentTransaction = requireActivity().getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.showingArea, matchFragment);
            //不在尝试利用
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();
            showActionArea(false);
            //TODO:刷新自己信息
        } else {

        }
    }

    //修改是否展示上下左右移动键
    private void showActionArea(boolean isShow) {

    }
}
