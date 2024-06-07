package com.example.kob_android;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.kob_android.activity.LoginActivity;
import com.example.kob_android.database.UserSharedPreferences;
import com.example.kob_android.fragment.PlayGroundFragment;
import com.example.kob_android.fragment.RankListFragment;
import com.example.kob_android.fragment.RecordListFragment;
import com.example.kob_android.fragment.UserInfoFragment;
import com.example.kob_android.fragment.subFragment.MatchFragment;
import com.example.kob_android.net.ListApiService;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;

//声明可注入
@AndroidEntryPoint
public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    @Inject
    ListApiService listApiService;
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
        MyApplication app = MyApplication.getInstance();
        String theme = app.theme;
        if (theme.equals("red")) {
            setTheme(R.style.RedTheme);
        } else if (theme.equals("blue")) {
            setTheme(R.style.BlueTheme);
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
        initListener();

        //是快捷方式进来
        Intent intent = getIntent();
        if (intent != null && "MATCH_GAME".equals(intent.getStringExtra("ACTION"))) {
            Log.i("aaakkk", "意图");
            // 检查用户是否已登录
            if (UserSharedPreferences.getInstance().getToken() == null) {
                // 未登录，重定向到 LoginActivity
                Intent intent1 = new Intent(this, LoginActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent1);
                finish(); // 结束 MainActivity
                return;
            }

            Bundle bundle = new Bundle();
            bundle.putString("ACTION", "MATCH_GAME");
            playGroundFragment.setArguments(bundle);
        }
        replaceFragment(playGroundFragment);
    }

    private void initListener() {
        playGroundBtn.setOnClickListener(this);
        rankListBtn.setOnClickListener(this);
        userInfoBtn.setOnClickListener(this);
        recordListBtn.setOnClickListener(this);
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

    public void replaceFragment(Fragment fragment) {
        //必须全局
        fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.main_filed, fragment);
        //不在尝试利用
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }


}