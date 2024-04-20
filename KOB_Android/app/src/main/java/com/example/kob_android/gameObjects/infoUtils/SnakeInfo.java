package com.example.kob_android.gameObjects.infoUtils;

import android.graphics.Color;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author: Cassifa
 * @CreateTime: 2024-04-20  05:46
 * @Description:
 */
@Data
//@AllArgsConstructor
@NoArgsConstructor
public class SnakeInfo {
    public int id;
    public double r;
    public double c;
    public int color;
    public SnakeInfo(double r,double c,int id,int color){
        this.r=r;
        this.c=c;
        this.id=id;
        this.color=color;
    }
}
