package com.example.kob_android;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.example.kob_android.fragment.PlayGroundFragment;
import com.example.kob_android.fragment.UserInfoFragment;

import dagger.hilt.android.AndroidEntryPoint;

//声明可注入
@AndroidEntryPoint
public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    Button playGround;
    Button rankList;
    Button recordList;
    Button userInfo;
    PlayGroundFragment playGroundFragment;
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
        playGround.setOnClickListener(this);
        userInfo.setOnClickListener(this);
    }

    private void initView() {
        mainFiled=(FrameLayout)findViewById(R.id.main_filed);
        playGround = findViewById(R.id.playground);
        rankList = findViewById(R.id.ranklist);
        recordList = findViewById(R.id.record);
        userInfo = findViewById(R.id.userinfo);
        playGroundFragment = new PlayGroundFragment();
        userInfoFragment = new UserInfoFragment();
    }

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.playground){
            replaceFragment(playGroundFragment);
        }
        else replaceFragment(userInfoFragment);
    }

    private void replaceFragment(Fragment fragment) {
        //必须全局
        fragmentTransaction =getFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.main_filed,fragment);
        fragmentTransaction.commit();
    }

}


//{"id":184,"map":"11111111111111101000000001011000001100000110100000000101100000000000011000001100000110001000010001100000110000011000000000000110100000000101100000110000011010000000010111111111111111","loser":"b","createTime":"2024-01-20 13:56:32","bid":3,"aid":1,"asx":11,"bsx":1,"asy":1,"bsy":12,"asteps":"000100301011030012","bsteps":"222333033323221111"},"result":"A胜"
