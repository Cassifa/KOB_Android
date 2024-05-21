package com.example.kob_android.gameObjects;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.kob_android.gameObjects.infoUtils.GameMapInfo;
import com.example.kob_android.gameObjects.infoUtils.StartGameInfo;
import com.example.kob_android.pojo.Record;
import com.google.gson.GsonBuilder;

/**
 * @Author: Cassifa
 * @CreateTime: 2024-04-20  01:47
 * @Description: 暴露接口：
 *               1.以recordData创建
 *               2.以newGameInfo（String map,int myPlaceId）创建游戏
 *               3.设置当前运动方向setDirection
 *               4.设置游戏状态
 */
public class MySurfaceView extends SurfaceView implements SurfaceHolder.Callback {
    private boolean isRender;//控制绘画线程的标志位
    private final TimeThread timeThread;
    Activity activity;

    GameMap gameMap;

    public MySurfaceView(Context context, String recordData, String newGameInfo) {
        super(context);
        setMeasuredDimension(50, 50);
        getHolder().addCallback(this);
        activity=(Activity) context;

        int rows = 13; // 行数
        int cols = 14; // 列数
        int[][] array = new int[rows][cols];

        //初始化GameMapInfo
        if (recordData != null) {
            Record record = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss")
                    .create().fromJson(recordData, Record.class);
            for (int i = 0; i < rows; i++) {
                for (int j = 0; j < cols; j++) {
                    int index = i * cols + j; // 计算当前字符在字符串中的索引
                    array[i][j] = Character.getNumericValue(record.getMap().charAt(index)); // 将字符转换为整数并存入二维数组
                }
            }
            gameMap = new GameMap(
                    new GameMapInfo(true, record, 1, array, 900 * 1.0, 900 * 1.0));
        }
        //新游戏
        else {
            StartGameInfo nowInfo = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss")
                    .create().fromJson(newGameInfo, StartGameInfo.class);
            for (int i = 0; i < rows; i++) {
                for (int j = 0; j < cols; j++) {
                    int index = i * cols + j;
                    array[i][j] = Character.getNumericValue(nowInfo.getMap().charAt(index));
                }
            }
            gameMap = new GameMap(
                    new GameMapInfo(false, null, nowInfo.getMyPlaceId(), array, 900 * 1.0, 900 * 1.0));
        }
        timeThread = new TimeThread(this, gameMap,activity);
    }

    @Override
    public void surfaceCreated(@NonNull SurfaceHolder holder) {
        if (!timeThread.isAlive()) {
            timeThread.start();
        }
        isRender = true;
        RenderThread renderThread = new RenderThread();
        renderThread.start();

    }

    @Override
    public void surfaceChanged(@NonNull SurfaceHolder holder, int format, int width, int height) {
    }

    @Override
    public void surfaceDestroyed(@NonNull SurfaceHolder holder) {
        isRender = false;
        timeThread.setFlag(false);
        GameObject.destroyAll();
        try {
            timeThread.join(); // 等待线程结束
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt(); // 恢复中断状态
        }
    }

    public void setDirection(int direction,int player) {
        timeThread.addDirectionToQueue(direction,player);
    }
    public void setGameStatus(String status){
        if(status.equals("a"))timeThread.setGameStatus(1);
        else if(status.equals("b"))timeThread.setGameStatus(2);
        else timeThread.setGameStatus(3);
    }

    class RenderThread extends Thread {
        @Override
        public void run() {
            while (isRender) {
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        doDraw();
                    }
                });
                try {
                    //每秒绘制60次
                    sleep(1000 / 60);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }


    private void doDraw() {
        Canvas canvas = getHolder().lockCanvas();
        if (canvas != null) {
            try {
                timeThread.render(canvas);
            } finally {
                // 释放画布并提交绘制内容
                getHolder().unlockCanvasAndPost(canvas);
            }
        }
    }

}
