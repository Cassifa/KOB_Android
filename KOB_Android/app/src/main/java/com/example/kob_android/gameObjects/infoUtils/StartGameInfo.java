package com.example.kob_android.gameObjects.infoUtils;

import lombok.ToString;

/**
 * @Author: Cassifa
 * @CreateTime: 2024-05-22  00:10
 * @Description:
 */
@ToString
public class StartGameInfo {
    String map;
    int myPlaceId;

    public StartGameInfo(String map, int myPlaceId) {
        this.map = map;
        this.myPlaceId = myPlaceId;
    }

    public String getMap() {
        return map;
    }

    public void setMap(String map) {
        this.map = map;
    }

    public int getMyPlaceId() {
        return myPlaceId;
    }

    public void setMyPlaceId(int myPlaceId) {
        this.myPlaceId = myPlaceId;
    }
}
