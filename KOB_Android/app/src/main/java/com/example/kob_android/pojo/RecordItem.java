package com.example.kob_android.pojo;

/**
 * @Author: Cassifa
 * @CreateTime: 2024-04-17  21:41
 * @Description:
 */

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author: Cassifa
 * @CreateTime: 2024-04-17  21:30
 * @Description: 回放列表类
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RecordItem {
    private String a_photo;
    private String a_username;
    private String b_photo;
    private String b_username;
    private Record record;
    private String result;
}
