package com.example.kob_android.gameObjects.infoUtils;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author: Cassifa
 * @CreateTime: 2024-04-20  05:55
 * @Description:
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Cell {
    public double r,c,x,y;
    public Cell(double r,double c){
        this.r=r;
        this.c=c;
        this.x=c+0.5;
        this.y=r+0.5;
    }
}
