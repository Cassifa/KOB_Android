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
    String myPlaceId;

    public StartGameInfo(String map, String myPlaceId) {
        this.map = map;
        this.myPlaceId = myPlaceId;
    }

    public String getMap() {
        return map;
    }

    public void setMap(String map) {
        this.map = map;
    }

    public String getMyPlaceId() {
        return myPlaceId;
    }

    public void setMyPlaceId(String myPlaceId) {
        this.myPlaceId = myPlaceId;
    }
}
