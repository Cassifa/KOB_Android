package com.example.kob_android;

import android.graphics.Canvas;
import android.util.Log;

import com.example.kob_android.gameObjects.GameMap;
import com.example.kob_android.gameObjects.GameObject;
import com.example.kob_android.gameObjects.Snake;

import java.util.Timer;
import java.util.TimerTask;

/**
 * @Author: Cassifa
 * @CreateTime: 2024-04-20  02:09
 * @Description:
 */
public class TimeThread extends Thread {
    private final MySurfaceView mySurfaceView;
    private boolean flag = true;


    public TimeThread(MySurfaceView SurfaceView, GameMap gameMap) {
        mySurfaceView = SurfaceView;
        if(gameMap.gameMapInfo.isRecord)
            analysisRecord(gameMap);
    }

    private int[] parseSteps(String stepsString) {
        int[] steps = new int[stepsString.length()];
        for (int i = 0; i < stepsString.length(); i++) {
            steps[i] = Integer.parseInt(String.valueOf(stepsString.charAt(i)));
        }
        return steps;
    }

    public void analysisRecord(GameMap gameMap) {
        int[] k = new int[]{0};
        Snake snake0 = gameMap.snakes.get(0);
        Snake snake1 = gameMap.snakes.get(1);
        String loser = gameMap.gameMapInfo.record.getLoser();
        int[] a_step = parseSteps(gameMap.gameMapInfo.record.getAsteps());
        int[] b_step = parseSteps(gameMap.gameMapInfo.record.getBsteps());
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                if (k[0] >= (a_step.length - 1)) {
                    if (loser.equals("all") || loser.equals("a"))
                        snake0.setStatus("die");
                    if (loser.equals("all") || loser.equals("b"))
                        snake1.setStatus("die");
                    timer.cancel();
                } else {
                    snake0.setDirection(a_step[k[0]]);
                    snake1.setDirection(b_step[k[0]]);
                    k[0]++;
                }
            }
        }, 0, 350);
    }

    @Override
    public void run() {
        while (flag) {
            GameObject.step(System.currentTimeMillis());
            try {
                //每秒刷新60次动画
                sleep(1000 / 60);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void render(Canvas canvas) {
        GameObject.renderAll(canvas);
    }


    public void setFlag(boolean flag) {
        this.flag = flag;
    }
}
