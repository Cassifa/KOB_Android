package com.example.kob_android.fragment;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.kob_android.MainActivity;
import com.example.kob_android.R;
import com.example.kob_android.activity.LoginActivity;
import com.example.kob_android.database.UserSharedPreferences;
import com.example.kob_android.net.ApiService;
import com.example.kob_android.utils.Constant;

import java.io.IOException;
import java.util.HashMap;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;
import retrofit2.Call;
import retrofit2.Response;

/**
 * @Author: Cassifa
 * @CreateTime: 2024-04-23  00:51
 * @Description:
 */
@AndroidEntryPoint
public class UserInfoFragment extends Fragment implements View.OnClickListener {
    @Inject
    ApiService apiService;

    HashMap<String, String> userInfo;
    View view;
    ImageView userImage;
    TextView userName;
    TextView userRating;
    TextView botList;
    TextView logout;
    TextView changeColor;

    @androidx.annotation.Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @androidx.annotation.Nullable ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_userinfo, container, false);
        initView();
        initListener();

        //加载个人信息
        final Context context = requireActivity();
        new Thread(new Runnable() {
            @Override
            public void run() {
                Call<HashMap<String, String>> call = apiService.getinfo();
                try {
                    Response<HashMap<String, String>> response = call.execute();
                    if (response.isSuccessful() && response.body() != null) {
                        userInfo = response.body();
                        // 回到主线程更新UI
                        new Handler(Looper.getMainLooper()).post(() -> {
                            Constant.setHttpImg(userImage, userInfo.get("photo"), context);
                            userName.setText(userInfo.get("username"));
                            userRating.setText(userInfo.get("rating"));
                        });
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();

        return view;
    }

    private void initView() {
        userImage = view.findViewById(R.id.info_userImage);
        userName = view.findViewById(R.id.info_userName);
        userRating = view.findViewById(R.id.info_userRating);
        botList = view.findViewById(R.id.info_botList);
        logout = view.findViewById(R.id.info_logout);
        changeColor = view.findViewById(R.id.info_changeColor);
    }

    private void initListener() {
        botList.setOnClickListener(this);
        logout.setOnClickListener(this);
        changeColor.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        MainActivity nowActivity = (MainActivity) getActivity();
        int id = v.getId();
        if (id == R.id.info_botList) {
            if (nowActivity != null) {
                nowActivity.replaceFragment(new BotListFragment());
            }
        } else if (id == R.id.info_logout) {
            // 点击注销按钮，弹出对话框询问用户是否确定退出登录
            new AlertDialog.Builder(requireContext())
                    .setTitle("确认退出登录")
                    .setMessage("确定要退出登录吗？")
                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            //清除数据
                            UserSharedPreferences.getInstance().refreshUser(null);
                            UserSharedPreferences.getInstance().refreshToken(null);
                            //回到登录页面
                            Intent intent = new Intent(requireContext(), LoginActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);
                        }
                    })
                    .setNegativeButton("取消", null)
                    .show();
        } else {
            Log.i("aaa", "点了修改主题色");
        }
    }

}
