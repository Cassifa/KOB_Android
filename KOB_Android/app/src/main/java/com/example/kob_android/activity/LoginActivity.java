package com.example.kob_android.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.kob_android.R;
import com.example.kob_android.database.UserSharedPreferences;
import com.example.kob_android.net.ApiService;
import com.example.kob_android.pojo.User;

import java.util.HashMap;
import java.util.Objects;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@AndroidEntryPoint
public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    @Inject
    protected ApiService apiService;

    private ImageView imageView;
    private EditText etUsername;
    private EditText etPassword;
    private Button btnRegiste;
    private Button btnUp;
    private TextView textView;
    User user = new User();
    String token;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //如果是从注册跳转来的
        Intent intent = getIntent();
        if (intent != null && intent.getStringExtra("username") != null) {
            String username = intent.getStringExtra("username");
            String password = intent.getStringExtra("password");
            if (username != null && password != null) {
                tryLogin(username, password);
            }
        } else {
            //使用存储数据尝试登录
            if (UserSharedPreferences.getInstance().getToken() != null) {
                updateUserInfo();
            } else {
                //没有token
                setContentView(R.layout.activity_login);
                initView();
            }
        }
    }

    private void tryLogin(String username, String password) {
        //通过用户名密码登录并刷新token
        apiService.login(username, password).enqueue(new Callback<HashMap<String, String>>() {
            @Override
            public void onResponse(@NonNull Call<HashMap<String, String>> call, @NonNull Response<HashMap<String, String>> response) {
                HashMap<String, String> data = response.body();
                String msg = null;
                if (data != null) {
                    msg = data.get("error_message");
                }
                //登录成功跳转转页面并且刷新数据
                if (msg != null && msg.equals("success")) {
                    String token = data.get("token");
                    successfulLoginCallBack(token);

                    // 登录成功，跳转到 MainActivity 并清除当前任务栈
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                    finish(); // 确保当前活动被销毁
                } else {
                    Toast.makeText(LoginActivity.this, "登录失败,用户名或密码错误", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<HashMap<String, String>> call, @NonNull Throwable t) {}
        });
    }

    private void successfulLoginCallBack(String token) {
        //登录成功，更新token和userInfo
        UserSharedPreferences preferences = UserSharedPreferences.getInstance();
        preferences.refreshToken(token);
        //更新用户信息
        updateUserInfo();
    }

    private void updateUserInfo() {
        apiService.getinfo().enqueue(new Callback<HashMap<String, String>>() {
            @Override
            public void onResponse(@NonNull Call<HashMap<String, String>> call, @NonNull Response<HashMap<String, String>> response) {
                HashMap<String, String> data = response.body();
                if (data != null) {
                    user.setId(Integer.parseInt(Objects.requireNonNull(data.get("id"))));
                    user.setUsername(data.get("username"));
                    user.setPassword("保密~");
                    user.setPhoto(data.get("photo"));
                    user.setRating(Integer.parseInt(Objects.requireNonNull(data.get("rating"))));
                    UserSharedPreferences preferences = UserSharedPreferences.getInstance();
                    preferences.refreshUser(user);

                    // 登录成功，跳转到 MainActivity 并清除当前任务栈
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                    finish(); // 确保当前活动被销毁
                }
            }

            @Override
            public void onFailure(@NonNull Call<HashMap<String, String>> call, @NonNull Throwable t) {
                //token失效
                UserSharedPreferences.getInstance().refreshToken(null);
                //自动认证失败，渲染登录页面
                setContentView(R.layout.activity_login);
                initView();
            }
        });
    }

    private void initView() {
        imageView = (ImageView) findViewById(R.id.imageView);
        etUsername = (EditText) findViewById(R.id.et_username);
        etPassword = (EditText) findViewById(R.id.et_password);
        btnRegiste = (Button) findViewById(R.id.btn_registe);
        btnUp = (Button) findViewById(R.id.btn_up);
        textView = (TextView) findViewById(R.id.textView);

        btnRegiste.setOnClickListener(this);
        btnUp.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.btn_registe) {
            //点击注册按钮，跳转进入注册页面
            startActivity(new Intent(this, RegisterActivity.class));
        } else if (id == R.id.btn_up) {
            if (etPassword.getText().toString().trim().isEmpty() | etUsername.getText().toString().trim().isEmpty()) {
                Toast.makeText(this, "用户名密码不能为空", Toast.LENGTH_LONG).show();
                return;
            }
            tryLogin(etUsername.getText().toString().trim(), etPassword.getText().toString().trim());
        }
    }
}