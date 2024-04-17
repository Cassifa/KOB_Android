package com.example.kob_android.net.responseData.pojo;

/**
 * @Author: Cassifa
 * @CreateTime: 2024-04-17  21:43
 * @Description:
 */
public class User {
    //表映射成class
//    @TableId(type = IdType.AUTO)
    private Integer id;
    private String username;
    private String password;

    private Integer rating;
    private String photo;
}