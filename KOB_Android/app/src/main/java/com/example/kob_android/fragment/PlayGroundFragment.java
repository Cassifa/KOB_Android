package com.example.kob_android.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.kob_android.R;
import com.example.kob_android.fragment.subFragment.MatchFragment;
import com.example.kob_android.fragment.subFragment.UserActionFragment;
import com.example.kob_android.gameObjects.MySurfaceView;
import com.example.kob_android.gameObjects.infoUtils.StartGameInfo;
import com.example.kob_android.net.responseData.pojo.User;
import com.example.kob_android.utils.Constant;
import com.google.gson.Gson;

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
    MatchFragment matchFragment;
    UserActionFragment userActionFragment;
    MySurfaceView surfaceView;
    FrameLayout showingLayout;
    FrameLayout actionLayout;
    FragmentTransaction fragmentTransaction;
    final String url = "ws://192.168.10.88:3000/websocket/" + "eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiIzMjNmYjNjMDc0N2I0OTdhYmQ3ZjgwNDdhMGYzMjhhMyIsInN1YiI6IjEiLCJpc3MiOiJzZyIsImlhdCI6MTcxNjI3OTQwMiwiZXhwIjoxNzE3NDg5MDAyfQ.-PTXxXsrhGByTrr_t5MKqd82HzHyGProg1_MRLxJFzI/";
    WebSocket mWebSocket;

    @androidx.annotation.Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @androidx.annotation.Nullable ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_playground, container, false);
        initView();
        return view;
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
        Request request = new Request.Builder().url(url).build();
        client.newWebSocket(request, createListener());
    }

    private WebSocketListener createListener() {
        return new WebSocketListener() {
            @Override
            public void onOpen(WebSocket webSocket, Response response) {
                super.onOpen(webSocket, response);
                Log.d("WebSocket", "open:" + response.toString());
                mWebSocket = webSocket;
            }

            @Override
            public void onMessage(WebSocket webSocket, String text) {
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
                Log.i("WebSocket", msg);
                //判断消息类型
                if (msg.equals("start-matching")) {
                    JSONObject data;
                    String opponent_username, opponent_photo;
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
                    matchFragment.updateInfo(opponent, false);
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
                    if (a_id == Constant.getMyInfo().getId()) startGame(newMap, 0);
                    else startGame(newMap, 1);

                    Log.i("WebSocket", "a_id: " + a_id + " map " + map);
                }//接到移动信息
                else if (msg.equals("move")) {

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
                } else if (msg.equals("result")) {
                    String loser;
                    try {
                        loser = jsonObject.getString("loser");
                    } catch (JSONException e) {
                        throw new RuntimeException(e);
                    }

                    Log.i("WebSocket", "loser:" + loser);
                    surfaceView.setGameStatus(loser);
                }
            }

            @Override
            public void onMessage(WebSocket webSocket, ByteString bytes) {
                super.onMessage(webSocket, bytes);
            }

            @Override
            public void onClosing(WebSocket webSocket, int code, String reason) {
                super.onClosing(webSocket, code, reason);
                Log.d("WebSocket", "Closing: " + code + " / " + reason);
            }

            @Override
            public void onClosed(WebSocket webSocket, int code, String reason) {
                super.onClosed(webSocket, code, reason);
                Log.d("WebSocket", "Closed: " + code + " / " + reason);
            }

            @Override
            public void onFailure(WebSocket webSocket, Throwable t, Response response) {
                super.onFailure(webSocket, t, response);
                Log.e("WebSocket", "Error: " + t.getMessage(), t);
            }
        };
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
        } else {
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
            requireActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (isShow) {
                        // 显示 userActionFragment
                        userActionFragment.getView().setVisibility(View.VISIBLE);
                    } else {
                        // 隐藏 userActionFragment
                        userActionFragment.getView().setVisibility(View.GONE);
                    }
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
        updateMainArea(false);
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
