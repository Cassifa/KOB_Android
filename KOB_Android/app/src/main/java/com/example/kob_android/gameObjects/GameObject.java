package com.example.kob_android.gameObjects;

import android.graphics.Canvas;

import com.example.kob_android.MySurfaceView;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * @Author: Cassifa
 * @CreateTime: 2024-04-19  02:54
 * @Description:
 */
public class GameObject {
    private static Queue<GameObject> list = new ConcurrentLinkedQueue<>();
    public static long lastTimeStamp;
    public boolean hasCalled = false;
    public long timeDelta;

    public void start() {}

    ;

    public void update() {}

    ;

    public void onDestroy() {}

    ;

    public GameObject() {
        synchronized (GameObject.class) {
            list.add(this);
        }
    }

    public void destroy() {
        this.onDestroy();
        for (GameObject object : list) {
            if (object.equals(this)) {
                list.remove(this);
                return;
            }
        }
    }

    ;

    public void render(Canvas canvas) {
    }

    public static void step(long timeStamp) {
        for (GameObject now : list) {
            if (!now.hasCalled) {
                now.hasCalled = true;
                now.start();
            } else {
                now.timeDelta = timeStamp - lastTimeStamp;
                now.update();
            }
        }
        GameObject.lastTimeStamp = timeStamp;
    }


    public static void renderAll(Canvas canvas) {
        //保证蛇在顶层
        for (GameObject now : GameObject.list)
            if (!(now instanceof Snake))
                now.render(canvas);
        for (GameObject now : GameObject.list)
            if (now instanceof Snake)
                now.render(canvas);
        //        for(GameObject now:GameObject.list)
        //                now.render(canvas);
    }

}
