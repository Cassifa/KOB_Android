package com.example.kob_android.gameObjects;

import android.app.Activity;
import android.graphics.Canvas;
import android.util.Log;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @Author: Cassifa
 * @CreateTime: 2024-04-20  02:09
 * @Description:
 */
public class TimeThread extends Thread {
    final MySurfaceView mySurfaceView;
    GameMap gameMap;
    boolean flag = true;
    ConcurrentLinkedQueue<Integer> nowAimA;
    ConcurrentLinkedQueue<Integer> nowAimB;
    Activity activity;

    //仅在新游戏中生效，用于检测游戏胜负 0-未结束 1-a输了 2-b输了 3-全输了
    private final AtomicInteger gameStatus = new AtomicInteger(0);

    public TimeThread(MySurfaceView SurfaceView, GameMap gameMap, Activity activity) {
        mySurfaceView = SurfaceView;
        this.activity = activity;
        this.gameMap = gameMap;
        //解析回放
        if (gameMap.gameMapInfo.isRecord)
            analysisRecord();
        else {
            nowAimA = new ConcurrentLinkedQueue<>();
            nowAimB = new ConcurrentLinkedQueue<>();
        }
    }


    public void analysisRecord() {
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


    //执行渲染并在新游戏时更新方向
    @Override
    public void run() {
        while (flag) {
            activity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    GameObject.step(System.currentTimeMillis());
                }
            });

            try {
                if (!gameMap.gameMapInfo.isRecord) {
                    processNowAimQueue();
                    int status = gameStatus.get();
                    if (status != 0) {
                        if (status == 1 || status == 3)
                            gameMap.snakes.get(0).setStatus("die");
                        if (status == 2 || status == 3)
                            gameMap.snakes.get(1).setStatus("die");
                    }
                }
                //每秒刷新60次动画
                sleep(1000 / 60);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }


    // 使用 poll() 方法从队列中获取元素
    private void processNowAimQueue() {
        Integer direction = nowAimA.poll();
        if (direction != null) {
            gameMap.snakes.get(0).setDirection(direction);
        }
        Integer direction2 = nowAimB.poll();
        if (direction2 != null) {
            gameMap.snakes.get(1).setDirection(direction2);
        }
    }

    public void setGameStatus(int status) {
        gameStatus.set(status);
    }

    public void addDirectionToQueue(int direction, int player) {
            if (player == 0) nowAimA.offer(direction); // 使用 put 方法来添加元素
            else nowAimB.offer(direction);;
            Log.i("WebSocket", "ADD " + player + " " + direction + " ");
    }


    private int[] parseSteps(String stepsString) {
        int[] steps = new int[stepsString.length()];
        for (int i = 0; i < stepsString.length(); i++) {
            steps[i] = Integer.parseInt(String.valueOf(stepsString.charAt(i)));
        }
        return steps;
    }

    public void render(Canvas canvas) {
        GameObject.renderAll(canvas);
    }


    public void setFlag(boolean flag) {
        this.flag = flag;
    }
}
