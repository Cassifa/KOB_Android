package com.example.kob_android;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.kob_android.fragment.PlayGroundFragment;
import com.example.kob_android.fragment.RankListFragment;
import com.example.kob_android.fragment.RecordListFragment;
import com.example.kob_android.fragment.UserInfoFragment;

import dagger.hilt.android.AndroidEntryPoint;

//声明可注入
@AndroidEntryPoint
public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    Button playGroundBtn;
    Button rankListBtn;
    Button recordListBtn;
    Button userInfoBtn;
    PlayGroundFragment playGroundFragment;
    RankListFragment rankListFragment;
    RecordListFragment recordListFragment;
    UserInfoFragment userInfoFragment;
    FragmentTransaction fragmentTransaction;
    FrameLayout mainFiled;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initListener();
    }

    private void initListener() {
        playGroundBtn.setOnClickListener(this);
        rankListBtn.setOnClickListener(this);
        userInfoBtn.setOnClickListener(this);
        recordListBtn.setOnClickListener(this);
        replaceFragment(playGroundFragment);
    }

    private void initView() {
        //初始化按钮
        mainFiled = (FrameLayout) findViewById(R.id.main_filed);
        playGroundBtn = findViewById(R.id.playgroundBtn);
        rankListBtn = findViewById(R.id.rankListBtn);
        recordListBtn = findViewById(R.id.recordListBtn);
        userInfoBtn = findViewById(R.id.userinfoBtn);

        //初始化Fragment
        playGroundFragment = new PlayGroundFragment();
        userInfoFragment = new UserInfoFragment();
        rankListFragment = new RankListFragment();
        recordListFragment = new RecordListFragment();
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.playgroundBtn) {
            replaceFragment(playGroundFragment);
        } else if (id == R.id.rankListBtn) {
            replaceFragment(rankListFragment);
        } else if (id == R.id.recordListBtn) {
            replaceFragment(recordListFragment);
        } else replaceFragment(userInfoFragment);
    }

    private void replaceFragment(Fragment fragment) {
        //必须全局
        fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.main_filed, fragment);
        //不在尝试利用
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }


}