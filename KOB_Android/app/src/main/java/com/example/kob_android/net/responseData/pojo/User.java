package com.example.kob_android.net.responseData.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author: Cassifa
 * @CreateTime: 2024-04-17  21:43
 * @Description:
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
    //表映射成class
    private Integer id;
    private String username;
    private String password;

    private Integer rating;
    private String photo;
}