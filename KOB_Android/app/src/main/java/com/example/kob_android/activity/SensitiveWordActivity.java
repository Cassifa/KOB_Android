package com.example.kob_android.activity;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.kob_android.R;
import com.example.kob_android.net.ApiService;

import java.util.Map;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@AndroidEntryPoint
public class SensitiveWordActivity extends AppCompatActivity implements View.OnClickListener {

    @Inject
    protected ApiService apiService;
    private EditText sensitiveWordInput;
    private TextView sensitiveWordOutput;
    private Button sensitiveWordBtn;
    private Button sensitiveWordCopyBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sensitivewords);

        // 初始化视图
        initializeViews();

        // 设置按钮点击事件
        sensitiveWordBtn.setOnClickListener(this);
        sensitiveWordCopyBtn.setOnClickListener(this);

    }

    private void initializeViews() {
        sensitiveWordInput = findViewById(R.id.sensitiveWord_input);
        sensitiveWordOutput = findViewById(R.id.sensitiveWord_output);
        sensitiveWordBtn = findViewById(R.id.sensitiveWord_btn);
        sensitiveWordCopyBtn = findViewById(R.id.sensitiveWord_copy_btn);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.sensitiveWord_btn) {
            String inputText = sensitiveWordInput.getText().toString();
            sendTextToBackend(inputText);
        } else if (id == R.id.sensitiveWord_copy_btn) {
            copyTextToClipboard(sensitiveWordOutput.getText().toString());
        }
    }

    private void sendTextToBackend(String inputText) {
        apiService.sensitiveWord(inputText).enqueue(new Callback<Map<String, String>>() {
            @Override
            public void onResponse(Call<Map<String, String>> call, Response<Map<String, String>> response) {
                if (response.body() != null) {
                    getResponseCallback(response.body().get("msg"));
                }
            }

            @Override
            public void onFailure(Call<Map<String, String>> call, Throwable t) {

            }
        });
    }

    private void getResponseCallback(String responseText) {
        Log.i("aaa", responseText + "接收到了");
        sensitiveWordOutput.setText(responseText);
    }

    private void copyTextToClipboard(String text) {
        ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData clip = ClipData.newPlainText("sensitiveWordOutput", text);
        clipboard.setPrimaryClip(clip);
        Toast.makeText(this, "已复制到剪贴板", Toast.LENGTH_SHORT).show();
    }


}
