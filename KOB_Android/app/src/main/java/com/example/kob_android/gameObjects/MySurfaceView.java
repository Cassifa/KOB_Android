package com.example.kob_android.gameObjects;

import android.content.Context;
import android.graphics.Canvas;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import androidx.annotation.NonNull;

import com.example.kob_android.gameObjects.infoUtils.GameMapInfo;
import com.example.kob_android.pojo.Record;
import com.google.gson.GsonBuilder;

/**
 * @Author: Cassifa
 * @CreateTime: 2024-04-20  01:47
 * @Description:
 */
public class MySurfaceView extends SurfaceView implements SurfaceHolder.Callback {
    private boolean isRender;//控制绘画线程的标志位
    private final TimeThread timeThread;

    String recordData = "{\"id\":184,\"map\":\"11111111111111101000000001011000001100000110100000000101100000000000011000001100000110001000010001100000110000011000000000000110100000000101100000110000011010000000010111111111111111\",\"loser\":\"b\",\"createTime\":\"2024-01-20 13:56:32\",\"bid\":3,\"aid\":1,\"asx\":11,\"bsx\":1,\"asy\":1,\"bsy\":12,\"asteps\":\"000100301011030012\",\"bsteps\":\"222333033323221111\"}";
    GameMap gameMap = null;
//    {"aid":1,"asteps":"01000300101030300","asx":11,"asy":1,"bid":2,"bsteps":"23222330322323333","bsx":1,"bsy":12,"createTime":"Apr 26, 2023 9:47:32 PM","id":60,"loser":"a","map":"11111111111111101000000001011000001100000111000000000011110000000000111000000000000110010000001001100000000000011100000000001111000000000011100000110000011010000000010111111111111111"}
//    {"id":184,"map":"11111111111111101000000001011000001100000110100000000101100000000000011000001100000110001000010001100000110000011000000000000110100000000101100000110000011010000000010111111111111111","loser":"b","createTime":"2024-01-20 13:56:32","bid":3,"aid":1,"asx":11,"bsx":1,"asy":1,"bsy":12,"asteps":"000100301011030012","bsteps":"222333033323221111"}
    public MySurfaceView(Context context, String recordData) {
        super(context);
        setMeasuredDimension(50, 50);
        getHolder().addCallback(this);
        this.recordData = recordData;

        Record record = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss")
                .create().fromJson(recordData, Record.class);
        int rows = 13; // 行数
        int cols = 14; // 列数
        int[][] array = new int[rows][cols];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                int index = i * cols + j; // 计算当前字符在字符串中的索引
                array[i][j] = Character.getNumericValue(record.getMap().charAt(index)); // 将字符转换为整数并存入二维数组
            }
        }
        gameMap = new GameMap(
                new GameMapInfo(true, record, 1, array, 900 * 1.0, 900 * 1.0));

        timeThread = new TimeThread(this, gameMap);
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

    class RenderThread extends Thread {
        @Override
        public void run() {
            while (isRender) {
                doDraw();
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
        timeThread.render(canvas);
        getHolder().unlockCanvasAndPost(canvas);
    }
}
