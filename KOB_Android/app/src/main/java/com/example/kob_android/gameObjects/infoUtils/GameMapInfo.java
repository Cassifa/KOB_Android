package com.example.kob_android.gameObjects.infoUtils;

import com.example.kob_android.pojo.Record;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author: Cassifa
 * @CreateTime: 2024-04-20  05:42
 * @Description:
 */
@Data
//@AllArgsConstructor
@NoArgsConstructor
public class GameMapInfo {
    public boolean isRecord;
    public Record record;
    public int placeId;
    public int[][] map;

    public double width;
    public double height;

    public GameMapInfo(boolean b, Record record, int i, int[][] array, double v, double v1){
        isRecord=b;
        this.record=record;
        placeId=i;
        map=array;
        width=v;
        height=v1;
    }
}
