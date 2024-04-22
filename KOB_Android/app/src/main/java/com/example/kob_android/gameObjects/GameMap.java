package com.example.kob_android.gameObjects;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;

import com.example.kob_android.gameObjects.infoUtils.GameMapInfo;
import com.example.kob_android.gameObjects.infoUtils.SnakeInfo;

import java.util.LinkedList;

/**
 * @Author: Cassifa
 * @CreateTime: 2024-04-20  05:51
 * @Description:
 */
public class GameMap extends GameObject {
    double L = 0;
    int cols = 14, rows = 13;
    public GameMapInfo gameMapInfo;
    LinkedList<Wall> walls = new LinkedList<>();
    public LinkedList<Snake> snakes = new LinkedList<>();

    public GameMap(GameMapInfo info) {
        super();
        gameMapInfo = info;
        snakes.add(new Snake(new SnakeInfo(rows - 2, 1, 0, Color.parseColor("#4876EC")), this));
        snakes.add(new Snake(new SnakeInfo(1, cols - 2, 1, Color.parseColor("#F94848")), this));
    }

    @Override
    public void start() {
        this.create_walls();
    }

    private void create_walls() {
        int[][] map = gameMapInfo.map;
        for (int i = 0; i < rows; i++)
            for (int j = 0; j < cols; j++)
                if (map[i][j] == 1)
                    walls.add(new Wall(i, j, this));
    }


    @Override
    public void update() {
        this.update_size();//更新地图大小
        if (this.check_ready()) {//准备好下一回合就移动
            Log.i("xxx", 1 + "");
            this.next_step();
        }
    }

    private void next_step() {
        for (Snake snake : snakes)
            snake.next_step();
    }

    private boolean check_ready() {
        for (Snake snake : snakes) {
            if (!snake.status.equals("idle")) return false;
            if (snake.direction == -1) return false;
        }
        return true;
    }

    private void update_size() {
        L = (int) Math.min(gameMapInfo.width / this.cols, gameMapInfo.height / this.rows);
    }

    @Override
    public void render(Canvas canvas) {
        final String color_even = "#AAD751";
        final String color_odd = "#A2D149";
        Paint paint = new Paint();
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                if ((r + c) % 2 == 0) {
                    paint.setColor(Color.parseColor(color_even));
                } else {
                    paint.setColor(Color.parseColor(color_odd));
                }
                canvas.drawRect((float) (c * L), (float) (r * L), (float) (c * L + L), (float) (r * L + L), paint);
            }
        }
    }
}
