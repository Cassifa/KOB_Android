package com.example.kob_android.gameObjects;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;

import com.example.kob_android.MySurfaceView;
import com.example.kob_android.gameObjects.infoUtils.Cell;
import com.example.kob_android.gameObjects.infoUtils.SnakeInfo;
import com.google.gson.Gson;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @Author: Cassifa
 * @CreateTime: 2024-04-20  05:50
 * @Description:
 */
public class Snake extends GameObject {
    GameMap gameMap;
    SnakeInfo snakeInfo;

    public static int dr[] = {-1, 0, 1, 0};
    public static int dc[] = {0, 1, 0, -1};
    public static double eps = 1e-2;
    public static int eye_dx[][] = {{-1, 1}, {1, 1}, {1, -1}, {-1, -1}};
    public static int eye_dy[][] = {{-1, -1}, {-1, 1}, {1, 1}, {1, -1}};
    int eye_direction;
    int step = 0;
    String status = "idle";
    int direction = -1;
    int speed = 3;
    CopyOnWriteArrayList<Cell> cells = new CopyOnWriteArrayList<>();
    Cell nextCell = null;

    public void setDirection(int x) {
        direction = x;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Snake(SnakeInfo info, GameMap gameMap) {
        super();
        this.snakeInfo = info;
        this.gameMap = gameMap;
        //红色右上1 蓝色左下0
        if (info.id == 0) this.eye_direction = 0;
        else this.eye_direction = 2;
        this.cells.add(new Cell(info.r, info.c));
    }

    @Override
    public void update() {
        if (!gameMap.gameMapInfo.isRecord && gameMap.gameMapInfo.placeId == snakeInfo.id)
            this.updateWallColor();
        if (this.status.equals("move")) {//移动
            this.update_move();
        }
    }

    private void updateWallColor() {
        for (Wall wall : gameMap.walls) {
            if (Math.abs(wall.r - snakeInfo.r) + Math.abs(wall.c - snakeInfo.c) > 2) continue;
            if (wall.r != 0 && wall.c != 0 && wall.c != gameMap.cols - 1 && wall.r != gameMap.rows - 1)
                continue;
            wall.color = snakeInfo.color;
        }
    }

    private void update_move() {
        Log.i("BB", direction + " " + snakeInfo.id);
        //允许的位移
        double move_dis = (double) (this.speed * this.timeDelta) / 1000;
        //x y 方向距离
        double dx = nextCell.x - cells.get(0).x;
        double dy = nextCell.y - cells.get(0).y;
        //与目的地的距离
        double dis = Math.sqrt(dx * dx + dy * dy);
        //        Log.i("showtimeDelta", timeDelta + "");
        //        Log.i("showMove_dis", move_dis + "");
        //        Log.i("showDx", dx + "");
        //        Log.i("showDis", dis + "");

        if (dis < Snake.eps) {
            this.status = "idle";
            cells.set(0, nextCell);
            nextCell = null;
        } else {
            //            double t = (( dx) );
            //            double k = (( dy) );
            double t = 0.1 * ((dx) / dis);
            double k = 0.1 * ((dy) / dis);
            cells.get(0).x += t;
            //            Log.i("show", t + "");
            cells.get(0).y += k;
            //            Log.i("show", "");
        }
        if (!this.check_tail_increasing()) {//不增长则尾巴缩短
            int k = this.cells.size();
            Cell tail = cells.get(k - 1);
            Cell tail_target = cells.get(k - 2);
            double tdx = tail_target.x - tail.x;
            double tdy = tail_target.y - tail.y;

            if (dis < Snake.eps) {
                this.cells.remove(cells.size() - 1);//尾巴已经重叠，删去
            } else {
                tail.x += move_dis * tdx / dis;
                tail.y += move_dis * tdy / dis;
            }
        }
    }

    private boolean check_tail_increasing() {
        if (this.step <= 10) return true;
        return this.step % 3 == 1;
    }

    public void next_step() {
        int d = this.direction;
        this.eye_direction = d;
        nextCell = new Cell(cells.get(0).r + Snake.dr[d], cells.get(0).c + Snake.dc[d]);
        Log.i("next", nextCell.toString());
        this.direction = -1;
        this.status = "move";//更新移动状态
        this.step++;

        int k = this.cells.size();
        for (int i = k; i != 0; i--) {
            if (i == k) {
                cells.add(new Gson().fromJson(new Gson().toJson(cells.get(i - 1)), Cell.class));
            } else
                cells.set(i, new Gson().fromJson(new Gson().toJson(cells.get(i - 1)), Cell.class));
        }
        //        cells.push(new Gson().fromJson(new Gson().toJson(cells.get(0)), Cell.class));

    }

    @Override
    public void render(Canvas canvas) {
        Paint paint = new Paint();
        paint.setColor(this.snakeInfo.color);
        if ("die".equals(this.status)) {
            paint.setColor(Color.WHITE);
        }
        float L = (float) gameMap.L;
        for (Cell cell : this.cells) {
            canvas.drawCircle((float) (cell.x * L), (float) (cell.y * L), (float) (L / 2 * 0.8), paint);
        }

        for (int i = 1; i < this.cells.size(); i++) {
            Cell a = this.cells.get(i - 1);
            Cell b = this.cells.get(i);
            if (Math.abs(a.x - b.x) < Snake.eps && Math.abs(a.y - b.y) < Snake.eps) {
                continue;
            }
            if (Math.abs(a.x - b.x) < Snake.eps) {
                // 竖向矩形
                float left = (float) (a.x - 0.5 + 0.1) * L;
                float top = (float) Math.min(a.y, b.y) * L;
                float right = left + L * 0.8f;
                float bottom = (float) Math.max(a.y, b.y) * L;
                canvas.drawRect(left, top, right, bottom, paint);
            } else {
                // 横向矩形
                float left = (float) Math.min(a.x, b.x) * L;
                float top = (float) (a.y - 0.5 + 0.1) * L;
                float right = (float) Math.max(a.x, b.x) * L;
                float bottom = top + L * 0.8f;
                canvas.drawRect(left, top, right, bottom, paint);
            }
        }

        paint.setColor(Color.BLACK);
        for (int i = 0; i < 2; i++) {
            int eye_x = (int) ((this.cells.get(0).x + Snake.eye_dx[this.eye_direction][i] * 0.15) * L);
            int eye_y = (int) ((this.cells.get(0).y + Snake.eye_dy[this.eye_direction][i] * 0.15) * L);
            canvas.drawCircle(eye_x, eye_y, (L * 0.05f), paint);
        }
    }
}
