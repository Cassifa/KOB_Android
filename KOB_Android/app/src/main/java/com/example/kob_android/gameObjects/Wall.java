package com.example.kob_android.gameObjects;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import com.example.kob_android.MySurfaceView;

/**
 * @Author: Cassifa
 * @CreateTime: 2024-04-20  05:55
 * @Description:
 */
public class Wall extends GameObject{
    double r,c;
    GameMap gameMap;
    int color= Color.parseColor("#B37226");
    public Wall(double r,double c,GameMap gameMap) {
        super();
        this.r=r;
        this.c=c;
        this.gameMap=gameMap;
    }

    @Override
    public void render(Canvas canvas) {
        float L = (float) this.gameMap.L;
        final Paint paint = new Paint();
        paint.setColor(this.color);
        canvas.drawRect((float) (this.c * L), (float) (this.r * L),
                (float) (this.c * L + L), (float) (this.r * L + L), paint);
    }
}
