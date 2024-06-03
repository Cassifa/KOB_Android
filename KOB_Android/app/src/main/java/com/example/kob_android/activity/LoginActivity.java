package com.example.kob_android.activity;

import com.example.kob_android.MyApplication;
import com.example.kob_android.R;
import com.example.kob_android.net.responseData.pojo.User;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView imageView;
    private EditText etUsername;
    private EditText etPassword;
    private Button btnRegiste;
    private Button btnUp;
    private TextView textView;
    User user;
    String token;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        user= MyApplication.getInstance().user;
        if(user.getUsername()!=null&&user.getPassword()!=null)
            tryLogin(user.getUsername(),user.getPassword());
        setContentView(R.layout.activity_login);
        initView();
    }

    private void tryLogin(String username, String password) {
    }

    private void successfulLoginCallBack(){

    }
    private void initView() {
        imageView = (ImageView) findViewById(R.id.imageView);
        etUsername = (EditText) findViewById(R.id.et_username);
        etPassword = (EditText) findViewById(R.id.et_password);
        btnRegiste = (Button) findViewById(R.id.btn_registe);
        btnUp = (Button) findViewById(R.id.btn_up);
        textView = (TextView) findViewById(R.id.textView);
        btnRegiste.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.btn_registe) {
            //点击注册按钮，跳转进入注册页面
            startActivity(new Intent(this, RegisterActivity.class));
        }
    }
}