package com.example.kob_android.net.responseData;

import com.example.kob_android.pojo.RecordItem;

import java.util.LinkedList;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author: Cassifa
 * @CreateTime: 2024-04-17  21:42
 * @Description:
 *//**
 * @Author: Cassifa
 * @CreateTime: 2024-04-17  21:30
 * @Description:
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RecordItemsData {
    private LinkedList<RecordItem> records;
    private Integer record_count;
}
