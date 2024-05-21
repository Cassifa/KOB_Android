package com.example.kob_android.gameObjects;

import android.graphics.Canvas;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ArrayBlockingQueue;

/**
 * @Author: Cassifa
 * @CreateTime: 2024-04-20  02:09
 * @Description:
 */
public class TimeThread extends Thread {
    final MySurfaceView mySurfaceView;
    GameMap gameMap;
    boolean flag = true;
    ArrayBlockingQueue<Integer> nowAim;

    public TimeThread(MySurfaceView SurfaceView, GameMap gameMap) {
        mySurfaceView = SurfaceView;
        this.gameMap = gameMap;
        //解析回放
        if (gameMap.gameMapInfo.isRecord)
            analysisRecord();

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


    @Override
    //执行渲染并在新游戏时更新方向
    public void run() {
        while (flag) {
            GameObject.step(System.currentTimeMillis());
            try {
                if (!gameMap.gameMapInfo.isRecord) {
                    processNowAimQueue();
                }
                //每秒刷新60次动画
                sleep(1000 / 60);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private void processNowAimQueue() {
        try {
            // 从队列中获取方向
            Integer direction = nowAim.poll();
            if (direction != null) {
                // 控制自己的方向
                gameMap.snakes.get(gameMap.gameMapInfo.placeId).setDirection(direction);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void addDirectionToQueue(int direction) {
        try {
            nowAim.put(direction); // 使用 put 方法来添加元素
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
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
