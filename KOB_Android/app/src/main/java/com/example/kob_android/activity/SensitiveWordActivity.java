package com.example.kob_android.activity;

import android.app.Activity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kob_android.R;

/**
 * @Author: Cassifa
 * @CreateTime: 2024-06-08  21:46
 * @Description:
 */
public class SensitiveWordActivity extends Activity implements View.OnClickListener {

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
            // 发送文本到后端并接收返回的文本 (模拟)
            String returnedText = sendTextToBackend(inputText);
            sensitiveWordOutput.setText(returnedText);
        } else if (id == R.id.sensitiveWord_copy_btn) {
            copyTextToClipboard(sensitiveWordOutput.getText().toString());
        }
    }

    private String sendTextToBackend(String inputText) {
        // 在这里实现发送文本到后端并接收返回文本的逻辑
        // 目前只是模拟返回相同的文本
        return inputText;
    }

    private void copyTextToClipboard(String text) {
        ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData clip = ClipData.newPlainText("sensitiveWordOutput", text);
        clipboard.setPrimaryClip(clip);
        Toast.makeText(this, "已复制到剪贴板", Toast.LENGTH_SHORT).show();
    }
}
