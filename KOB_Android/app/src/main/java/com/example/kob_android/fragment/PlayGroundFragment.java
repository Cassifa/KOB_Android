package com.example.kob_android.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.kob_android.R;
import com.example.kob_android.database.RecordItemDBHelper;
import com.example.kob_android.database.UserSharedPreferences;
import com.example.kob_android.fragment.subFragment.MatchFragment;
import com.example.kob_android.fragment.subFragment.UserActionFragment;
import com.example.kob_android.gameObjects.MySurfaceView;
import com.example.kob_android.gameObjects.infoUtils.StartGameInfo;
import com.example.kob_android.pojo.User;
import com.example.kob_android.pojo.Record;
import com.example.kob_android.pojo.RecordItem;
import com.example.kob_android.utils.Constant;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;
import okio.ByteString;

/**
 * @Author: Cassifa
 * @CreateTime: 2024-04-23  00:34
 * @Description: 管理 匹配框, 游戏框, 移动按钮框 三个栏目
 */
public class PlayGroundFragment extends Fragment {
    View view;
    public MatchFragment matchFragment;
    UserActionFragment userActionFragment;
    MySurfaceView surfaceView;
    FrameLayout showingLayout;
    FrameLayout actionLayout;
    FragmentTransaction fragmentTransaction;

    WebSocket mWebSocket;
    RecordItemDBHelper mHelper;

    @Override
    public void onPause() {
        super.onPause();
        if (mWebSocket != null) {
            mWebSocket.cancel();
            mWebSocket = null;
        }
    }

    @androidx.annotation.Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @androidx.annotation.Nullable ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_playground, container, false);
        initView();
        Bundle bundle = getArguments();
        if (bundle != null && "MATCH_GAME".equals(bundle.getString("ACTION"))) {
            // 开始匹配游戏
            matchFragment.setQuickStart();
            setArguments(null);
        }
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        //获取数据库实例
        mHelper = RecordItemDBHelper.getInstance(getContext());
        mHelper.openWriteLink();
    }

    @Override
    public void onStop() {
        super.onStop();
        mHelper.closeLink();
    }

    private void initView() {
        showingLayout = view.findViewById(R.id.showingArea);
        actionLayout = view.findViewById(R.id.actionArea);
        //准备资源
        matchFragment = new MatchFragment(this);
        userActionFragment = new UserActionFragment(this);

        updateMainArea(true);

        //建立链接
        OkHttpClient client = new OkHttpClient.Builder()
                .writeTimeout(5, TimeUnit.SECONDS)
                .readTimeout(5, TimeUnit.SECONDS)
                .connectTimeout(10, TimeUnit.SECONDS)
                .build();
        Request request = new Request.Builder().url(Constant.getWebSocketUrl()).build();
        client.newWebSocket(request, createListener());
    }

    private WebSocketListener createListener() {
        return new WebSocketListener() {
            @Override
            public void onOpen(@NonNull WebSocket webSocket, @NonNull Response response) {
                super.onOpen(webSocket, response);
                mWebSocket = webSocket;
            }

            @Override
            public void onMessage(@NonNull WebSocket webSocket, @NonNull String text) {
                super.onMessage(webSocket, text);
                JSONObject jsonObject;
                try {
                    jsonObject = new JSONObject(text);
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
                String msg;
                try {
                    msg = jsonObject.getString("event");
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }

                Log.i("kkk", msg);
                //判断消息类型
                switch (msg) {
                    case "start-matching":
                        JSONObject data;
                        String opponent_username, opponent_photo;
                        //解析对手个人信息
                        try {
                            data = jsonObject.getJSONObject("game");
                            opponent_photo = jsonObject.getString("opponent_photo");
                            opponent_username = jsonObject.getString("opponent_username");
                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }
                        //更新对手信息
                        User opponent = new User();
                        opponent.setPhoto(opponent_photo);
                        opponent.setUsername((opponent_username));
                        //matchFragment.updateInfo(opponent, false);
                        //开始游戏
                        int a_id;
                        String map;
                        try {
                            a_id = data.getInt("a_id");
                            map = data.getString("game_map");
                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }
                        String newMap = "";
                        for (int i = 0; i < map.length(); i++) {
                            char c = map.charAt(i);
                            if (c == '0' || c == '1') newMap += c;
                        }
                        int id = Constant.getMyInfo().getId();
                        if (a_id == Constant.getMyInfo().getId())
                            startGame(newMap, 0);
                        else
                            startGame(newMap, 1);

                        break;
                    //接到移动信息
                    case "move":

                        int a_direction, b_direction;
                        try {
                            a_direction = jsonObject.getInt("a_direction");
                            b_direction = jsonObject.getInt("b_direction");
                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }
                        surfaceView.setDirection(a_direction, 0);
                        surfaceView.setDirection(b_direction, 1);

                        Log.i("WebSocket", "a_direction:" + a_direction + " " + b_direction);
                        break;
                    //拿到游戏结果
                    case "result":
                        String loser;
                        JSONObject item;
                        try {
                            //获取失败方与对局记录
                            loser = jsonObject.getString("loser");
                            item = jsonObject.getJSONObject("recordItem");
                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }
                        Log.i("WebSocket", "loser:" + loser);
                        surfaceView.setGameStatus(loser);

                        try {
                            if (item.getBoolean("msg")) saveToSQLLite(item);
                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }
                        break;
                }
            }

            @Override
            public void onMessage(@NonNull WebSocket webSocket, @NonNull ByteString bytes) {
                super.onMessage(webSocket, bytes);
            }

            @Override
            public void onClosing(@NonNull WebSocket webSocket, int code, @NonNull String reason) {
                super.onClosing(webSocket, code, reason);
                Log.d("WebSocket", "Closing: " + code + " / " + reason);
            }

            @Override
            public void onClosed(@NonNull WebSocket webSocket, int code, @NonNull String reason) {
                super.onClosed(webSocket, code, reason);
                Log.d("WebSocket", "Closed: " + code + " / " + reason);
            }

            @Override
            public void onFailure(@NonNull WebSocket webSocket, @NonNull Throwable t, Response response) {
                super.onFailure(webSocket, t, response);
                Log.e("WebSocket", "Error: " + t.getMessage(), t);
            }
        };
    }

    private void saveToSQLLite(JSONObject item) {

        try {
            // 提取 record 字段
            String recordJsonString = item.getString("record");
            Log.i("RecordJson", recordJsonString); // 打印 record 字段的 JSON 数据

            // 解析 record 字段
            Record record = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss")
                    .create().fromJson(recordJsonString, Record.class);
            JSONObject jsonObject = new JSONObject(recordJsonString);
            record.setAid(jsonObject.getInt("AId"));
            record.setAsx(jsonObject.getInt("ASx"));
            record.setAsy(jsonObject.getInt("ASy"));
            record.setAsteps(jsonObject.getString("ASteps"));

            record.setBid(jsonObject.getInt("BId"));
            record.setBsx(jsonObject.getInt("BSx"));
            record.setBsy(jsonObject.getInt("BSy"));
            record.setBsteps(jsonObject.getString("BSteps"));

            // 创建 RecordItem 对象
            RecordItem recordItem = new RecordItem();
            recordItem.setA_photo(item.getString("a_photo"));
            recordItem.setA_username(item.getString("a_username"));
            recordItem.setB_photo(item.getString("b_photo"));
            recordItem.setB_username(item.getString("b_username"));

            recordItem.setRecord(record);
            recordItem.setResult(item.getString("result"));

            // 保存到数据库
            mHelper.insert(recordItem);
        } catch (JsonSyntaxException e) {
            Log.e("GsonError", "JSON 解析错误: " + e.getMessage());
        } catch (JSONException e) {
            Log.e("JsonError", "JSON 解析错误: " + e.getMessage());
        }
    }

    //开始游戏
    private void startGame(String map, int myPlaceId) {
        StartGameInfo startGameInfo = new StartGameInfo(map, myPlaceId);
        surfaceView = new MySurfaceView(getActivity(), null, new Gson().toJson(startGameInfo));
        updateMainArea(false);
    }

    //切换匹配页面与游戏页面
    private void updateMainArea(boolean isMatch) {
        //isMatch表示要匹配页面
        if (isMatch) {
            //展示匹配Fragment
            fragmentTransaction = requireActivity().getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.showingArea, matchFragment);
            fragmentTransaction.replace(R.id.actionArea, userActionFragment);
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();
            showActionArea(false);
        }
        //要进入游戏页面
        else {
            requireActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    showingLayout.removeAllViews();
                    showingLayout.addView(surfaceView);
                }
            });

            if (matchFragment.getCheckedBotId() == -1)
                showActionArea(true);
        }
    }


    //修改是否展示上下左右移动键
    private void showActionArea(boolean isShow) {
        if (userActionFragment != null && userActionFragment.getView() != null) {
            requireActivity().runOnUiThread(() -> {
                if (isShow) {
                    // 显示 userActionFragment
                    userActionFragment.getView().setVisibility(View.VISIBLE);
                } else {
                    // 隐藏 userActionFragment
                    userActionFragment.getView().setVisibility(View.GONE);
                }
            });
        }
    }


    //开始匹配
    public void startMatch() {
        //切换状态
        matchFragment.shift();
        //发送匹配请求
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("event", "start-matching");
            jsonObject.put("bot_id", matchFragment.getCheckedBotId());
            UserSharedPreferences.getInstance().refreshBotId(matchFragment.getCheckedBotId());
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        mWebSocket.send(jsonObject.toString());
    }

    //玩家按方向键
    public void setDirection(int direction) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("event", "move");
            jsonObject.put("direction", direction);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        mWebSocket.send(jsonObject.toString());
    }

    //取消匹配
    public void cancelMatch() {
//        updateMainArea(false);
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("event", "stop-matching");
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        mWebSocket.send(jsonObject.toString());
        matchFragment.shift();
    }
}
