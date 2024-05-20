package com.example.kob_android.fragment;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.kob_android.MainActivity;
import com.example.kob_android.R;
import com.example.kob_android.net.ApiService;
import com.example.kob_android.utils.Constant;

import java.io.IOException;
import java.util.HashMap;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;
import retrofit2.Call;
import retrofit2.Callback;
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
                        new Handler(Looper.getMainLooper()).post(new Runnable() {
                            @Override
                            public void run() {
                                Constant.setHttpImg(userImage, userInfo.get("photo"), context);
                                userName.setText(userInfo.get("username"));
                                userRating.setText(userInfo.get("rating"));
                            }
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
        MainActivity nowActivity=(MainActivity) getActivity();
        int id = v.getId();
        if (id == R.id.info_botList) {
                nowActivity.replaceFragment(new BotListFragment());
        } else if (id == R.id.info_logout) {
            Log.i("aaa", "b");
        } else {
            Log.i("aaa", "c");
        }
    }
}
