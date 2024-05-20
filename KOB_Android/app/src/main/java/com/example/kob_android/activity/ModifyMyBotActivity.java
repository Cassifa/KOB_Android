package com.example.kob_android.activity;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.kob_android.R;
import com.example.kob_android.net.BotApiService;
import com.example.kob_android.net.responseData.pojo.Bot;
import com.google.gson.GsonBuilder;

import java.util.HashMap;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * @Author: Cassifa
 * @CreateTime: 2024-04-24  06:09
 * @Description: 用于编辑修改Bot列表
 */
@AndroidEntryPoint
public class ModifyMyBotActivity extends AppCompatActivity implements View.OnClickListener {
    @Inject
    BotApiService botApiService;
    EditText botName;
    EditText botDescription;
    EditText botContent;
    Integer botId;
    Button ensureBtn;
    Button cancelBtn;
    Boolean isNew;

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
            botId = bot.getId();
        }
        isNew = getIntent().getBooleanExtra("isNew", false);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.modifyBotEnsure) {
            String title = botName.getText().toString();
            String description = botDescription.getText().toString();
            String content = botContent.getText().toString();
            if (isNew) {
                botApiService.add(title, description, content).enqueue(new Callback<HashMap<String, String>>() {
                    @Override
                    public void onResponse(Call<HashMap<String, String>> call, Response<HashMap<String, String>> response) {
                        HashMap<String, String> msg = response.body();
                        final String errorMsg;
                        if (msg != null) {
                            errorMsg = msg.get("error_message");
                        } else errorMsg = null;
                        if (errorMsg != null && errorMsg.equals("success")) {
                            runOnUiThread(() -> {
                                Toast.makeText(ModifyMyBotActivity.this, "成功添加一个Ai~", Toast.LENGTH_SHORT).show();
                            });
                            finish();
                        } else {
                            runOnUiThread(() -> {
                                Toast.makeText(ModifyMyBotActivity.this, errorMsg, Toast.LENGTH_SHORT).show();
                            });
                        }
                    }

                    @Override
                    public void onFailure(Call<HashMap<String, String>> call, Throwable t) {
                        runOnUiThread(() -> {
                            Toast.makeText(ModifyMyBotActivity.this, "网络异常", Toast.LENGTH_SHORT).show();
                        });
                    }
                });
            } else {
                botApiService.update(botId.toString(), title, description, content).enqueue(new Callback<HashMap<String, String>>() {
                    @Override
                    public void onResponse(Call<HashMap<String, String>> call, Response<HashMap<String, String>> response) {
                        HashMap<String, String> msg = response.body();
                        final String errorMsg;
                        if (msg != null) {
                            errorMsg = msg.get("error_message");
                        } else errorMsg = null;
                        runOnUiThread(() -> {
                            Toast.makeText(ModifyMyBotActivity.this, errorMsg, Toast.LENGTH_SHORT).show();
                        });
                        if (errorMsg != null && errorMsg.equals("更新成功")) {
                            finish();
                        }
                    }

                    @Override
                    public void onFailure(Call<HashMap<String, String>> call, Throwable t) {
                        runOnUiThread(() -> {
                            Toast.makeText(ModifyMyBotActivity.this, "网络异常", Toast.LENGTH_SHORT).show();
                        });
                    }
                });

            }
        } else {
            finish();
        }
    }
}
