package com.example.kob_android.pojo;

import java.util.Date;

/**
 * @Author: Cassifa
 * @CreateTime: 2024-04-17  21:34
 * @Description:
 */
public class Record {
    private Integer id;
    private Integer aId;
    private Integer aSx;
    private Integer aSy;
    private Integer bId;
    private Integer bSx;
    private Integer bSy;
    private String aSteps;
    private String bSteps;
    private String map;
    private String loser;
//    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "Asia/Shanghai")
    private Date createTime;
}
