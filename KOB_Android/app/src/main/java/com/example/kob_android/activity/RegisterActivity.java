package com.example.kob_android.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.kob_android.R;
import com.example.kob_android.net.ApiService;

import java.util.HashMap;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@AndroidEntryPoint
public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {
    @Inject
    protected ApiService apiService;
    private EditText etUsername;
    private EditText etPasswordSet;
    private EditText etPasswordComfirm;
    private Button btnConfirm;
    String name = null;
    String password = null;
    String password_confirm = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        initView();
    }

    private void initView() {
        etUsername = (EditText) findViewById(R.id.et_username);
        etPasswordSet = (EditText) findViewById(R.id.et_password_set);
        etPasswordComfirm = (EditText) findViewById(R.id.et_password_comfirm);
        btnConfirm = (Button) findViewById(R.id.btn_confirm);
        btnConfirm.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.btn_confirm) {
            Log.i("aaa", "申请注册1");
            if (isEmptyForText()) {
                Log.i("aaa", "申请注册2");
                if (isSamePassword()) {
                    Log.i("aaa", "申请注册");
                    //尝试注册
                    apiService.register(name, password, password_confirm).enqueue(new Callback<HashMap<String, String>>() {
                        @Override
                        public void onResponse(Call<HashMap<String, String>> call, Response<HashMap<String, String>> response) {
                            Log.i("aaa", response.body().get("error_message"));
                            registerCallback(response.body().get("error_message"));
                        }
                        @Override
                        public void onFailure(Call<HashMap<String, String>> call, Throwable t) {}});
                }

            }
        }
    }

    private void registerCallback(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
        if ("注册成功".equals(msg)) {
            // 注册成功，返回登录页面并携带用户名密码
            Intent intent = new Intent(this, LoginActivity.class);
            intent.putExtra("username", name);
            intent.putExtra("password", password);
            // 启动一个新的任务栈并清除当前的任务栈
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish(); // 确保当前活动被销毁
        }
    }


    //判断输入内容是否为空
    public boolean isEmptyForText() {
        name = etUsername.getText().toString().trim();
        password = etPasswordSet.getText().toString().trim();
        password_confirm = etPasswordComfirm.getText().toString().trim();
        if (name.isEmpty() | password.isEmpty() | password_confirm.isEmpty()) {
            Toast.makeText(this, "输入内容为空", Toast.LENGTH_LONG).show();
            return false;
        }

        return true;
    }

    public boolean isSamePassword() {
        password = etPasswordSet.getText().toString().trim();
        password_confirm = etPasswordComfirm.getText().toString().trim();
        return password.equals(password_confirm);
    }
}