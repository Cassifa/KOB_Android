package com.example.kob_android.activity;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.Nullable;

import com.example.kob_android.R;
import com.example.kob_android.net.responseData.pojo.Bot;
import com.google.gson.GsonBuilder;

/**
 * @Author: Cassifa
 * @CreateTime: 2024-04-24  06:09
 * @Description: 用于编辑修改Bot列表
 */
public class ModifyMyBotActivity extends Activity implements View.OnClickListener {
    EditText botName;
    EditText botDescription;
    EditText botContent;
    Button ensureBtn;
    Button cancelBtn;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modifybot);
        initView();
        initData();
    }

    private void initView() {
        botName = findViewById(R.id.botName);
        botDescription = findViewById(R.id.botDescription);
        botContent = findViewById(R.id.botCodeContent);
        ensureBtn = findViewById(R.id.modifyBotEnsure);
        cancelBtn = findViewById(R.id.modifyBotCancel);

        cancelBtn.setOnClickListener(this);
        ensureBtn.setOnClickListener(this);
    }

    private void initData() {

        String botJson = getIntent().getStringExtra("bot");
        Bot bot = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss")
                .create().fromJson(botJson, Bot.class);

        if (bot != null) {
            botName.setText(bot.getTitle());
            botDescription.setText(bot.getDescription());
            botContent.setText(bot.getContent());
        }
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.modifyBotEnsure) {
            Log.i("aaa", "确认了");
        } else {
            Log.i("aaa", "取消");
            finish();
        }
    }
}
